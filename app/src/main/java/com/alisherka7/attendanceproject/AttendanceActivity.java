package com.alisherka7.attendanceproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AttendanceActivity extends AppCompatActivity {


    Button backButton, lecture1;
    Button lectureButton;
    String response;
    Map<Integer, String> listOfLectures = new HashMap<Integer, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);


        //get lectures
        Bundle bundle = getIntent().getExtras();
        response = bundle.getString("response");
        try {
            addLecturesFromJson(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AttendanceActivity.this, ProfileActivity.class);
                intent.putExtra("response", response);
                startActivity(intent);
            }
        });

//
//        lecture1 = (Button) findViewById(R.id.lecture1);
//        lecture1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(AttendanceActivity.this, LectureActivity.class);
//                startActivity(intent);
//            }
//        });
    }

//    private Map<String, String> getLecturesfromJson(String response) throws JSONException {
////        JSONObject jsonObject = new JSONObject(response);
////        JSONObject studentDataObject = jsonObject.getJSONObject("studentData");
////        JSONObject lectures = studentDataObject.getJSONObject("lectures");
////        Iterator<String> keys = lectures.keys();
////        while(keys.hasNext()){
////            String key = keys.next();
////            listOfLectures.add(key);
////        }
////        return listOfLectures;
//    }

    public void addLecturesFromJson(String response) throws JSONException {
        int count = 1;

        LinearLayout layout = (LinearLayout) findViewById(R.id.lecturesLayout);
        LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        buttonLayoutParams.setMargins(20,25,20,10);


        // dawable component
        Drawable d = getResources().getDrawable(R.drawable.custom_lecture_buttons);


        JSONObject jsonObject = new JSONObject(response);
        JSONObject stdata = jsonObject.getJSONObject("studentdata");
        JSONObject stLectures = stdata.getJSONObject("lectures");
        Iterator<String> keys = stLectures.keys();


        while(keys.hasNext()){
            int i = count;
            String key = keys.next();
            // Add keys and values in Map
            listOfLectures.put(count, key);

            lectureButton = new Button(this);
            lectureButton.setText(key);
            lectureButton.setId(count);
            lectureButton.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            lectureButton.setPadding(40,0,0,0);
            lectureButton.setLayoutParams(buttonLayoutParams);
            lectureButton.setBackground(d);
            lectureButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_gotoicon, 0);
            layout.addView(lectureButton);

            // get Json attendance boolean List
            JSONObject dataLecture = stLectures.getJSONObject(key);
            lectureButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AttendanceActivity.this, LectureActivity.class);
                    intent.putExtra("response", response);
                    intent.putExtra("nameOfLecture", key);
                    intent.putExtra("LectureDataJsonObject", dataLecture.toString());
                    startActivity(intent);
                    System.out.println(key);
                    System.out.println(dataLecture.toString());
                }
            });
            count++;
        }

    }
//    <Button
//    android:id="@+id/lecture1"
//    android:layout_width="380dp"
//    android:layout_height="50dp"
//    android:background="@drawable/custom_lecture_buttons"
//    android:gravity="left|center_vertical"
//    android:paddingLeft="25dp"
//    android:paddingRight="15dp"
//    android:layout_marginTop="15dp"
//    android:drawableEnd="@drawable/ic_gotoicon"
//    android:text="모바일응용" />
}