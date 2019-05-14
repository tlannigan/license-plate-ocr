package com.oultoncollege.licenseplateocr.data;

import android.content.Context;
import android.util.Log;

import com.oultoncollege.licenseplateocr.R;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DataSource {

    private static final String DATA_SOURCE = "";
    private static final String TAG = "Data Source Update";
    private Context context;

    public DataSource() {}

    public DataSource(Context context) {
        this.context = context;
    }

    public boolean update(AppDatabase db) {
        List<Student> newData = fetchNewData();
        if (newData.size() > 0) {
            removeLocalData(db);
            addNewData(db, newData);
            return true;
        }
        return false;
    }

    private List<Student> fetchNewData() {
        List<Student> students = new ArrayList<>();

        try {
            JSONParser jsonParser = new JSONParser();
            InputStreamReader json = new InputStreamReader(context.getResources().openRawResource(R.raw.sample));
            JSONObject jsonObject = (JSONObject) jsonParser.parse(json);

            if (jsonObject != null) {
                JSONArray studentArray = (JSONArray) jsonObject.get("data");

                for (Object obj : studentArray) {
                    JSONObject studentData = (JSONObject) obj;
                    String id = (String) studentData.get("studentAssignedID");
                    String firstName = (String) studentData.get("firstName");
                    String lastName = (String) studentData.get("lastName");
                    String email = (String) studentData.get("email");
                    String licensePlate = (String) studentData.get("customField14");
                    String vehicleMakeModel = (String) studentData.get("customField16");
                    String stickerNumber = (String) studentData.get("customField15");

                    students.add(new Student(id, firstName, lastName, email, licensePlate, vehicleMakeModel, stickerNumber));
                }
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

        return students;
    }

    private void removeLocalData(AppDatabase db) {
        db.studentDao().flush();
    }

    private void addNewData(AppDatabase db, List<Student> students) {
        db.studentDao().fill(students);
    }
}
