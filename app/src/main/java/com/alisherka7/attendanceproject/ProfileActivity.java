package com.alisherka7.attendanceproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class ProfileActivity extends AppCompatActivity {

    Button logOutButton, attendanceButton;
    String response;
    TextView textViewStudentName;
    private String StudentName = "성명";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        logOutButton = (Button) findViewById(R.id.logOutButton);

        textViewStudentName  = (TextView) findViewById(R.id.textViewStudentName);

        // Json response
        Bundle bundle = getIntent().getExtras();
        response = bundle.getString("response");
        try {
            parseJSON(response);
            textViewStudentName.setText(StudentName);
        } catch (JSONException e) {
            e.printStackTrace();
        }




        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });


        attendanceButton = (Button) findViewById(R.id.attendanceButton);
        attendanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, AttendanceActivity.class);
                intent.putExtra("response", response);
                startActivity(intent);
            }
        });
    }

    private void parseJSON(String response) throws JSONException {
        JSONObject jsonObject = new JSONObject(response);
        StudentName = jsonObject.getString("name");
    }
}