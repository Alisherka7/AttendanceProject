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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ProfessorLoginActivity extends AppCompatActivity {

    Button loginButton;
    EditText professorID, passwordID;
    String lectureResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor_login);

        loginButton = (Button) findViewById(R.id.loginButton);
        professorID = (EditText)findViewById(R.id.professorID);
        passwordID = (EditText)findViewById(R.id.passwordID);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser(professorID.getText().toString(), passwordID.getText().toString());
            }
        });
    }

    private void loginUser(String professorID, String pPassword) {
        RequestQueue requestQueue = Volley.newRequestQueue(ProfessorLoginActivity.this);
        String url = "http://3.142.94.39:3040/api/loginPofessor";
        if(TextUtils.isEmpty(professorID)){
            Toast.makeText(this, "교수ID 입력하세", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(pPassword)){
            Toast.makeText(this, "비밀번호를 입력하세", Toast.LENGTH_SHORT).show();
            return;
        }
        // db connection
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.length() < 20){
                    Toast.makeText(ProfessorLoginActivity.this, "아이디 또는 패스워드가 잘못 입력되었습니다.", Toast.LENGTH_SHORT).show();
                }else{
                    jsonGetRequest(response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("professorID", professorID);
                params.put("password", pPassword);


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
        // db connection end
    }

    public void jsonGetRequest(String studentResponse){
        RequestQueue requestQueue = Volley.newRequestQueue(ProfessorLoginActivity.this);
        String url = "http://3.142.94.39:3040/api/trackingdata";

        // db connection
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                intentProfileActivity(studentResponse, response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("error volley connection");
            }
        });

        requestQueue.add(stringRequest);
        // db connection end
    }

    private void intentProfileActivity(String professorResponse, String lectureResponse){
        Intent intent = new Intent(ProfessorLoginActivity.this, ProfessorLecturesActivity.class);
        intent.putExtra("professorResponse", professorResponse);
        intent.putExtra("lectureResponse", lectureResponse);
        System.out.println(professorResponse + "\n\n\n");
        System.out.println(lectureResponse + "\n\n\n");
        startActivity(intent);
        finish();
    }
}