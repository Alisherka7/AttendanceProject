package com.alisherka7.attendanceproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class LectureActivity extends AppCompatActivity {


//    intent.putExtra("nameOfLecture", key);
//    intent.putExtra("LectureDataJsonObject", dataLecture.toString());

    String lectureName, getLectureJsonData, studentResponse, lectureResponse;
    Button backButton;
    TextView nameLecture, professorName, attendanceStatus;
    int[] textIds = new int[] {R.id.f1, R.id.f2, R.id.f3, R.id.f4, R.id.f5, R.id.f6, R.id.f7, R.id.f8, R.id.f9, R.id.f10, R.id.f11, R.id.f12, R.id.f13, R.id.f14, R.id.f15 };
    int[] dateIds = new int[] {R.id.data1, R.id.data2, R.id.data3, R.id.data4, R.id.data5, R.id.data6, R.id.data7, R.id.data8, R.id.data9, R.id.data10, R.id.data11, R.id.data12, R.id.data13, R.id.data14, R.id.data15 };
    public static int attendance = 0, absence =0, tardy =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture);
        Bundle bundle = getIntent().getExtras();
        studentResponse = bundle.getString("studentResponse");
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
                Intent intent = new Intent(LectureActivity.this, AttendanceActivity.class);
                intent.putExtra("studentResponse", studentResponse);
                intent.putExtra("lectureResponse", lectureResponse);
                startActivity(intent);
                finish();
            }
        });
        nameLecture = (TextView) findViewById(R.id.nameOfLecture);
        nameLecture.setText(lectureName);
//        professorName.setText(">교수: " + professor);
        attendanceStatus = (TextView)findViewById(R.id.lectureAttendance);
        attendanceStatus.setText(">출석: " + attendance + "\t지각: " + tardy + "\t결석: " + absence);
        attendance = 0;
        tardy = 0;
        absence = 0;


//
    }

   public void jsonDataResponse(String data) throws JSONException {
        JSONObject jsObj = new JSONObject(studentResponse);
        String stName = jsObj.getString("name");
        JSONObject jsonObject = new JSONObject(data);
        Iterator<String> keys = jsonObject.keys();
        String[] times = new String[jsonObject.length()];
        // attendance int array
       int[] studentAttend = new int[30];
        int count= 0;
        while(keys.hasNext()) {
           String key = keys.next();
           times[count] = key;
           if (jsonObject.get(key) instanceof JSONObject) {
               JSONObject students = jsonObject.getJSONObject(key);
               if(students.getString(stName).equals("1")){
                   attendance++;
               }if(students.getString(stName).equals("0")){
                   absence++;
               }if(students.getString(stName).equals("-1")){
                   tardy++;
               }
               studentAttend[count] = students.getInt(stName);
           }
           count++;
        }
        for(int i =0; i<15; i++){
            int abs =0, att =0, tar =0;
            if(studentAttend[i] == 0){
                abs++;
            }if(studentAttend[i+15] == 0){
                abs++;
            }if(studentAttend[i] == 1){
                att++;
            }if(studentAttend[i+15] == 1) {
                att++;
            }
            if(studentAttend[i] == -1){
                tar++;
            }if(studentAttend[i+15] == -1){
                tar++;
            }

            TextView txView = (TextView)findViewById(dateIds[i]);
            TextView attendText = (TextView)findViewById(textIds[i]);
            String[] splitTime = times[i].split("-");
            String[] splitDate = splitTime[2].split("T");
            txView.setText((i+1) + "주 (" + splitTime[1] + "월 - " + splitDate[0] + "일)");
            attendText.setText("출석 : " + att + "  지각: " + tar + "   결석: " + abs);

        }
    }
}