package com.oultoncollege.licenseplateocr.data;

import com.oultoncollege.licenseplateocr.BuildConfig;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

public class DataSource {

    private static final String API_ENDPOINT = "https://oulton.ampeducator.com/api/student/get";
    private String apiKey = BuildConfig.apiKey;
    private String apiQuery = "?apiKey=" + apiKey + "&currentProgramStatus=enrolled&limit=100&offset=";

    public String getUrl() {
        return API_ENDPOINT + apiQuery;
    }

    public List<Student> parseStudentData(String json) {
        List<Student> students = new ArrayList<>();

        try {
            JSONTokener tokener = new JSONTokener(json);
            JSONObject jsonObject = new JSONObject(tokener);

            JSONArray studentArray = jsonObject.getJSONArray("data");

            if (studentArray.length() > 0) {
                for (int i = 0; i < studentArray.length(); i++) {
                    if (!studentArray.getJSONObject(i).has("customField14"))
                        continue;

                    String id = studentArray.getJSONObject(i).getString("studentAssignedID");
                    String firstName = studentArray.getJSONObject(i).getString("firstName");
                    String lastName = studentArray.getJSONObject(i).getString("lastName");
                    String email = studentArray.getJSONObject(i).getString("email");
                    String programCode = studentArray.getJSONObject(i).getString("currentProgramCode");
                    String programName = studentArray.getJSONObject(i).getString("currentProgramName");
                    String licensePlate = studentArray.getJSONObject(i).getString("customField14").replaceAll("[^a-zA-Z0-9]", "");
                    String vehicleMakeModel = (studentArray.getJSONObject(i).has("customField16")) ? studentArray.getJSONObject(i).getString("customField16") : "";
                    String stickerNumber = (studentArray.getJSONObject(i).has("customField15")) ? studentArray.getJSONObject(i).getString("customField15") : "";

                    students.add(new Student(id, firstName, lastName, email, programCode, programName, licensePlate, vehicleMakeModel, stickerNumber));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return students;
    }
}
