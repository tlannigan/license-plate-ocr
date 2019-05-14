package com.oultoncollege.licenseplateocr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.oultoncollege.licenseplateocr.data.AppDatabase;
import com.oultoncollege.licenseplateocr.data.DataSource;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends Activity {

    private static AppDatabase db;
    private TextView updateStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        updateStatus = findViewById(R.id.update_status);

        db = AppDatabase.getInstance(getApplicationContext());
    }

    public void startScanning(View view) {
        Intent scanner = new Intent(this, OcrCaptureActivity.class);
        startActivity(scanner);
    }

    public void refreshData(View view) {
        DataSource data = new DataSource(this, db);
        if (data.update()) {
            String date = new SimpleDateFormat("hh:mm a MMM dd, yyyy", Locale.CANADA).format(new Date());
            updateStatus.setText("Last updated: " + date);
        } else {
            updateStatus.setText("Unable to update, using offline data.");
        }
    }
}
