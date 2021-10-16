package com.alisherka7.attendanceproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {

    Button loginButton;
    EditText studentID, passwordID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = (Button) findViewById(R.id.loginButton);
        studentID = (EditText)findViewById(R.id.studentID);
        passwordID = (EditText)findViewById(R.id.passwordID);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser(studentID.getText().toString(), passwordID.getText().toString());
            }
        });
    }

    private void loginUser(String stID, String stPassword) {
        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        String url = "http://10.0.2.2:10320/login";
        if(TextUtils.isEmpty(stID)){
            Toast.makeText(this, "email cannot be null", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(stPassword)){
            Toast.makeText(this, "password cannot be null", Toast.LENGTH_SHORT).show();
            return;
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);
                intentProfileActivity(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", stID);
                params.put("password", stPassword);
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void intentProfileActivity(String response){
        Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
        intent.putExtra("response", response);
        startActivity(intent);

        finish();
    }
}