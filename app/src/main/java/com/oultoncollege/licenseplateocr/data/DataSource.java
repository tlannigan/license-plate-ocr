package com.oultoncollege.licenseplateocr.data;

import android.content.Context;
import android.util.Log;

import com.oultoncollege.licenseplateocr.R;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataSource {

    private static final String DATA_SOURCE = "";
    private static final String TAG = "Data Source Update";
    private Context context;
    private AppDatabase db;

    public DataSource() {
    }

    public DataSource(Context context, AppDatabase db) {
        this.context = context;
        this.db = db;
    }

    public boolean update() {
        List<Student> newData = fetchNewData();
        if (newData.size() > 0) {
            removeLocalData();
            addNewData(newData);
            return true;
        }
        return false;
    }

    private List<Student> fetchNewData() {
        List<Student> students = new ArrayList<>();

        try {
            InputStream inputStream = context.getResources().openRawResource(R.raw.sample);

            Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
            String json = scanner.hasNext() ? scanner.next() : "";

            JSONTokener tokener = new JSONTokener(json);
            JSONObject jsonObject = new JSONObject(tokener);

            JSONArray studentArray = jsonObject.getJSONArray("data");

            for (int i = 0; i < studentArray.length(); i++) {
                String id = studentArray.getJSONObject(i).getString("studentAssignedID");
                String firstName = studentArray.getJSONObject(i).getString("firstName");
                String lastName = studentArray.getJSONObject(i).getString("lastName");
                String email = studentArray.getJSONObject(i).getString("email");
                String licensePlate = studentArray.getJSONObject(i).getString("customField14");
                String vehicleMakeModel = studentArray.getJSONObject(i).getString("customField16");
                String stickerNumber = studentArray.getJSONObject(i).getString("customField15");

                students.add(new Student(id, firstName, lastName, email, licensePlate, vehicleMakeModel, stickerNumber));
            }

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

        return students;
    }

    private void removeLocalData() {
        db.studentDao().flush();
    }

    private void addNewData(List<Student> students) {
        db.studentDao().fill(students);
    }
}
