package com.oultoncollege.licenseplateocr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.oultoncollege.licenseplateocr.data.AppDatabase;
import com.oultoncollege.licenseplateocr.data.DataSource;
import com.oultoncollege.licenseplateocr.data.Student;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class MainActivity extends Activity {

    private static AppDatabase db;
    private TextView updateStatus;
    private ProgressBar updateProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        updateStatus = findViewById(R.id.update_status);
        updateStatus.setText(getString(R.string.update_success, readLastUpdated()));
        updateProgress = findViewById(R.id.update_progress);
        db = AppDatabase.getInstance(getApplicationContext());
    }

    public void startScanning(View view) {
        Intent scanner = new Intent(this, OcrCaptureActivity.class);
        startActivity(scanner);
    }

    public void refreshData(View view) {
        try {
            DataSource data = new DataSource();
            new DataUpdater().execute(data);
        } catch (Exception e) {
            e.printStackTrace();
            updateStatus.setText(R.string.update_fail);
        }
    }

    public String readLastUpdated() {
        SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.last_updated), Context.MODE_PRIVATE);
        return sharedPref.getString(getString(R.string.last_updated), "a while ago");
    }

    public void writeLastUpdated(String date) {
        SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.last_updated), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.last_updated), date);
        editor.apply();
    }

    private class DataUpdater extends AsyncTask<DataSource, Void, Boolean> {

        private List<Student> students = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            updateStatus.setVisibility(View.GONE);
            updateProgress.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            updateStatus.setVisibility(View.VISIBLE);
            updateProgress.setVisibility(View.GONE);

            if (result) {
                String date = new SimpleDateFormat("hh:mm a MMM dd, yyyy", Locale.CANADA).format(new Date());
                writeLastUpdated(date);
                updateStatus.setText(getString(R.string.update_success, readLastUpdated()));
            } else {
                updateStatus.setText(R.string.update_fail);
            }

            // ********** Test to make sure data gets fetched **********
            for (Student student : db.studentDao().getAllStudents()) {
                Log.i("TEST", student.toString());
            }
            // *********************************************************
        }

        @Override
        protected Boolean doInBackground(DataSource... dataSources) {
            int offset = 0;
            boolean hasStudents = true;

            do {
                String json = requestResources(dataSources[0], offset);
                if (!json.isEmpty()) {
                    List<Student> fetchedStudents = dataSources[0].parseStudentData(json);
                    if (fetchedStudents.size() > 0) {
                        students.addAll(fetchedStudents);
                        offset += 100;
                    } else {
                        hasStudents = false;
                    }
                }
            } while (hasStudents);

            if (students.size() > 0) {
                db.studentDao().flush();
                db.studentDao().fill(students);
                return true;
            }

            return false;
        }

        private String requestResources(DataSource data, int offset) {
            try {
                URL requestURL = new URL(data.getUrl() + offset);
                InputStream inputStream = requestURL.openStream();
                Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
                String json = scanner.hasNext() ? scanner.next() : "";

                inputStream.close();
                scanner.close();

                return json;

            } catch (IOException e) {
                e.printStackTrace();
            }

            return "";
        }
    }
}
