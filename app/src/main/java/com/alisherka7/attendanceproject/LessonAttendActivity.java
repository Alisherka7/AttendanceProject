package com.alisherka7.attendanceproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class LessonAttendActivity extends AppCompatActivity {

    String lectureName, getLectureJsonData, professorResponse, lectureResponse, pName;
    Button backButton;
    TextView nameLecture, professorName, attendanceStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_attend);

        Bundle bundle = getIntent().getExtras();
        professorResponse = bundle.getString("professorResponse");
        lectureResponse = bundle.getString("lectureResponse");
        lectureName = bundle.getString("lectureName");
        getLectureJsonData = bundle.getString("getLectureJsonData");
        try {
            jsonDataResponse(getLectureJsonData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        response = bundle.getString("response");
        //back Activity
        backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LessonAttendActivity.this, ProfessorLecturesActivity.class);
                intent.putExtra("professorResponse", professorResponse);
                intent.putExtra("lectureResponse", lectureResponse);
                startActivity(intent);
                finish();
            }
        });
        nameLecture = (TextView) findViewById(R.id.nameOfLecture);
        nameLecture.setText(lectureName);
        professorName.setText(">교수: " + pName);

    }


    public void jsonDataResponse(String data) throws JSONException {
        JSONObject jsObj = new JSONObject(professorResponse);
        pName = jsObj.getString("name");
        JSONObject jsonObject = new JSONObject(data);
        TableLayout stk = (TableLayout) findViewById(R.id.tabLayout);
        Iterator<String> keys = jsonObject.keys();
        String[] times = new String[jsonObject.length()];
        // attendance int array
        int[] studentAttend = new int[30];
        int count = 0;
        while (keys.hasNext()) {
            String key = keys.next();
            times[count] = key;
            if (jsonObject.get(key) instanceof JSONObject) {

            }
        }
    }
}