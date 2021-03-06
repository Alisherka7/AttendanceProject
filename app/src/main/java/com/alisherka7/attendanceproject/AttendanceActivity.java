package com.alisherka7.attendanceproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AttendanceActivity extends AppCompatActivity {


    Button backButton, lecture1, lectureButton;
    String studentResponse, lectureResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        //get lectures
        Bundle bundle = getIntent().getExtras();
        studentResponse = bundle.getString("studentResponse");
        lectureResponse = bundle.getString("lectureResponse");
        try {
            addLecturesFromJson(studentResponse, lectureResponse);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AttendanceActivity.this, ProfileActivity.class);
                intent.putExtra("studentResponse", studentResponse);
                intent.putExtra("lectureResponse", lectureResponse);
                startActivity(intent);
            }
        });
    }


    public void addLecturesFromJson(String sData, String lData) throws JSONException {
        int count = 1;
        // compare lectures
        JSONObject Objects = new JSONObject(lData);
        JSONObject lectureObj = Objects.getJSONObject("trackingData");

        LinearLayout layout = (LinearLayout) findViewById(R.id.lecturesLayout);
        LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        buttonLayoutParams.setMargins(20, 25, 20, 10);
        // dawable component
        Drawable d = getResources().getDrawable(R.drawable.custom_lecture_buttons);


        JSONObject jsonObject = new JSONObject(sData);
        JSONArray lectureArray = jsonObject.getJSONArray("lectures");// [1,2,3]
        for (int i = 0; i < lectureArray.length(); i++) {
            // Add keys and values in Map
            lectureButton = new Button(this);
            lectureButton.setText(lectureArray.getString(i));
            lectureButton.setId(count);
            lectureButton.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            lectureButton.setPadding(40, 0, 0, 0);
            lectureButton.setLayoutParams(buttonLayoutParams);
            lectureButton.setBackground(d);
            lectureButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_gotoicon, 0);
            layout.addView(lectureButton);
            String lectureName = lectureArray.getString(i);
            String dateLecture = lectureObj.getJSONObject(lectureName).toString();
            System.out.println(dateLecture);
            lectureButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AttendanceActivity.this, LectureActivity.class);
                    intent.putExtra("lectureName", lectureName);
                    intent.putExtra("getLectureJsonData", dateLecture);
                    intent.putExtra("studentResponse", studentResponse);
                    intent.putExtra("lectureResponse", lectureResponse);
                    startActivity(intent);
                }
            });
            count++;
        }

    }



}