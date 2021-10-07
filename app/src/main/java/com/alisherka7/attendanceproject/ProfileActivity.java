package com.alisherka7.attendanceproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ProfileActivity extends AppCompatActivity {

    Button logOutButton, attendanceButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        logOutButton = (Button) findViewById(R.id.logOutButton);
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
                startActivity(intent);
            }
        });
    }
}