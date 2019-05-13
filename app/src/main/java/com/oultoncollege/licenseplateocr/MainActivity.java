package com.oultoncollege.licenseplateocr;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.oultoncollege.licenseplateocr.data.AppDatabase;
import com.oultoncollege.licenseplateocr.data.Student;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private static AppDatabase db;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_layout);

        db = AppDatabase.getDatabase(getApplicationContext());

        List<Student> newStudents = new ArrayList<>();

        Student student1 = new Student("id1", "Khoa", "Dien", "khoa.dien@hotmail.com", "ABC123", "Toyota Corolla", "1234");
        Student student2 = new Student("id2", "John", "Doe", "jdoe@mail.com", "DEF456", "Honda Civic", "6789");

        newStudents.add(student1);
        newStudents.add(student2);

//         db.studentDao().fill(newStudents);
//         Student student3 = new Student("id3", "Jane", "Smith", "jsmith@mail.com", "JSM987", "Mazda 3", "9867");
//         db.studentDao().add(student3);

        List<Student> studentList = db.studentDao().getAllStudents();

        String print = "";
        for (Student student : studentList) {
            print += student.toString() + "\n";
        }

        textView = findViewById(R.id.textView);
        textView.setText(print);
    }
}
