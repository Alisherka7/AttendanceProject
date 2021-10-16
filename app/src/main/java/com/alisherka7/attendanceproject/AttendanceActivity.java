package com.alisherka7.attendanceproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class AttendanceActivity extends AppCompatActivity {


    Button backButton, lecture1;
    Button lectureButton;
    String response;
    ArrayList<String> listOfLectures = new ArrayList<>();

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
                startActivity(intent);
                finish();
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

    private ArrayList<String> getLecturesfromJson(String response) throws JSONException {
        JSONObject jsonObject = new JSONObject(response);
        JSONObject studentDataObject = jsonObject.getJSONObject("studentData");
        JSONObject lectures = studentDataObject.getJSONObject("lectures");
        Iterator<String> keys = lectures.keys();
        while(keys.hasNext()){
            String key = keys.next();
            listOfLectures.add(key);
        }
        return listOfLectures;
    }

    public void addLecturesFromJson(String response) throws JSONException {
        int count = 1;

        LinearLayout layout = (LinearLayout) findViewById(R.id.lecturesLayout);
        Drawable d = getResources().getDrawable(R.drawable.custom_lecture_buttons);
        JSONObject jsonObject = new JSONObject(response);
        JSONObject stdata = jsonObject.getJSONObject("studentdata");
        JSONObject stLectures = stdata.getJSONObject("lectures");
        Iterator<String> keys = stLectures.keys();
        while(keys.hasNext()){
            String key = keys.next();
            lectureButton = new Button(this);
            lectureButton.setText(key);
            lectureButton.setId(count);
            lectureButton.setBackground(d);
            layout.addView(lectureButton);

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