package com.alisherka7.attendanceproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LectureActivity extends AppCompatActivity {


//    intent.putExtra("nameOfLecture", key);
//    intent.putExtra("LectureDataJsonObject", dataLecture.toString());

    private String LectureDataJsonObject, NameOfLecture, response;
    String professor;
    private int fcount, scount, total, getCountAbsent;
    Button backButton;
    TextView nameLecture, professorName, attendanceStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture);
        Bundle bundle = getIntent().getExtras();
        NameOfLecture = bundle.getString("nameOfLecture");
        LectureDataJsonObject = bundle.getString("LectureDataJsonObject");
        try {
            jsonDataResponse(LectureDataJsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        //back Activity
        response = bundle.getString("response");
        backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LectureActivity.this, AttendanceActivity.class);
                intent.putExtra("response", response);
                startActivity(intent);
                finish();
            }
        });



        nameLecture = (TextView) findViewById(R.id.nameOfLecture);
        professorName = (TextView) findViewById(R.id.professor);
        attendanceStatus = (TextView) findViewById(R.id.lectureAttendance);
        nameLecture.setText(NameOfLecture);
        professorName.setText(">교수: " + professor);
        attendanceStatus.setText(">출석: " + total + "\t지각: 0" + "\t결석: " + getCountAbsent);
    }

   public void jsonDataResponse(String data) throws JSONException {
        JSONObject jsonObject = new JSONObject(data);
        professor = jsonObject.getString("professor");
        JSONArray fAttendance = jsonObject.getJSONArray("fattendance");
        JSONArray sAttendance = jsonObject.getJSONArray("sattendance");
        Boolean FAtd[] = new Boolean[fAttendance.length()];
        Boolean SAtd[] = new Boolean[sAttendance.length()];
        for(int i =0; i<fAttendance.length(); i++){
            if(fAttendance.getBoolean(i) == true){
                fcount++;
            }
            FAtd[i] = fAttendance.getBoolean(i);
        }
        for(int i =0; i<sAttendance.length(); i++){
            if(sAttendance.getBoolean(i) == true){
                scount++;
            }
            SAtd[i] = sAttendance.getBoolean(i);
        }

        total = scount + fcount;
        getCountAbsent = (FAtd.length + SAtd.length) - total;
    }
}