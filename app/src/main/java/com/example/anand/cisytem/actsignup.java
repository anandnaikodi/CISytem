package com.example.anand.cisytem;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class actsignup extends AppCompatActivity implements View.OnClickListener {
    private static final String REGISTER_URL = "http://192.168.0.107/CIS/student_signup.php";

    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";



    private EditText Textname;
    private EditText TextEmail;
    private EditText TextPassword;

    private Button buttonSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actsignup);
        Textname = (EditText) findViewById(R.id.txt_name);
        TextEmail = (EditText) findViewById(R.id.txt_email);
        TextPassword = (EditText) findViewById(R.id.txt_password);


        buttonSubmit = (Button) findViewById(R.id.btn_submit);

        buttonSubmit.setOnClickListener(this);
    }

    private void registerUser(){
        final String username = Textname.getText().toString().trim();
        final String email = TextEmail.getText().toString().trim();
        final String password = TextPassword.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(actsignup.this,response,Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(actsignup.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(KEY_NAME,username);
                params.put(KEY_PASSWORD,password);
                params.put(KEY_EMAIL, email);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    public void onClick(View v) {
        if(v == buttonSubmit){
            registerUser();
        }
    }
}
