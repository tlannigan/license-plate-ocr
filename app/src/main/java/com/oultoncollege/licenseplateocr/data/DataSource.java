package com.oultoncollege.licenseplateocr.data;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataSource {

    private static final String TAG = "Data Source Update";
    private static final String API_ENDPOINT = "https://oulton.ampeducator.com/api/student/get";
    private static String apiKey = "";
    private static String apiQuery = "?apiKey=" + apiKey + "&currentProgramStatus=enrolled&limit=100&offset=";
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
        int offset = 0;
        boolean hasStudents = true;

        do {
            try {
                InputStream inputStream = new URL(API_ENDPOINT + apiQuery + offset).openStream();

                Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
                String json = scanner.hasNext() ? scanner.next() : "";

                inputStream.close();
                scanner.close();

                JSONTokener tokener = new JSONTokener(json);
                JSONObject jsonObject = new JSONObject(tokener);

                JSONArray studentArray = jsonObject.getJSONArray("data");

                if (studentArray.length() == 0) {
                    hasStudents = false;
                } else {
                    for (int i = 0; i < studentArray.length(); i++) {
                        if (!studentArray.getJSONObject(i).has("customField14"))
                            break;

                        String id = studentArray.getJSONObject(i).getString("studentAssignedID");
                        String firstName = studentArray.getJSONObject(i).getString("firstName");
                        String lastName = studentArray.getJSONObject(i).getString("lastName");
                        String email = studentArray.getJSONObject(i).getString("email");
                        String programCode = studentArray.getJSONObject(i).getString("currentProgramCode");
                        String programName = studentArray.getJSONObject(i).getString("currentProgramName");
                        String licensePlate = studentArray.getJSONObject(i).getString("customField14");
                        String vehicleMakeModel = (studentArray.getJSONObject(i).has("customField16")) ? studentArray.getJSONObject(i).getString("customField16") : "";
                        String stickerNumber = (studentArray.getJSONObject(i).has("customField15")) ? studentArray.getJSONObject(i).getString("customField15") : "";

                        students.add(new Student(id, firstName, lastName, email, programCode, programName, licensePlate, vehicleMakeModel, stickerNumber));
                    }

                    offset += 100;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } while (hasStudents);

        return students;
    }

    private void removeLocalData() {
        db.studentDao().flush();
    }

    private void addNewData(List<Student> students) {
        db.studentDao().fill(students);
    }
}
