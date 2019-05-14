package com.oultoncollege.licenseplateocr;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.oultoncollege.licenseplateocr.data.AppDatabase;
import com.oultoncollege.licenseplateocr.data.DataSource;
import com.oultoncollege.licenseplateocr.data.Student;

import java.util.List;

public class MainActivity extends Activity {

    private static AppDatabase db;
    private TextView updateStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        db = AppDatabase.getDatabase(getApplicationContext());
        DataSource data = new DataSource(this, db);

        String print = "";
        if (data.update()) {
            List<Student> studentList = db.studentDao().getAllStudents();
            for (Student student : studentList) {
                print += student.toString() + "\n";
            }
        }

        updateStatus = findViewById(R.id.update_status);
        updateStatus.setText(print);
    }
}
