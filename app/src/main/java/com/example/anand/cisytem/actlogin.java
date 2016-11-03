package com.example.anand.cisytem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

public class actlogin extends AppCompatActivity implements View.OnClickListener{
    public static final String LOGIN_URL = constants.url+"/CIS/login.php";

    public static final String KEY_EMAIL="email";
    public static final String KEY_PASSWORD="password";
    public static final String KEY_TABLE = "table";

    private EditText TextUsername;
    private EditText TextPassword;
    private CheckBox TextCheckbox;
    private Button buttonLogin;

    private String username;
    private String password;
    private String tab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actlogin);
        TextUsername = (EditText) findViewById(R.id.txt_email);
        TextPassword = (EditText) findViewById(R.id.txt_password);
        TextCheckbox=(CheckBox) findViewById(R.id.chk_admin);

        buttonLogin = (Button) findViewById(R.id.btn_login);
        TextCheckbox.setOnClickListener(this);
        buttonLogin.setOnClickListener(this);

    }
    private void userLogin(String tablelocal) {
        username = TextUsername.getText().toString().trim();
        password = TextPassword.getText().toString().trim();
        final String table=tablelocal;
        //final ProgressDialog loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("success")){
                            System.out.println("inside success");
                            openProfile(table);
                            System.out.println("inside success2");
                        }else{
                            //loading.dismiss();
                            Toast.makeText(actlogin.this,response,Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                       // loading.dismiss();
                        Toast.makeText(actlogin.this,volleyError.getMessage().toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put(KEY_EMAIL,username);
                map.put(KEY_PASSWORD,password);
                map.put(KEY_TABLE,table);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void openProfile(String tablelocal){
        final String table2=tablelocal;
        if(table2.equals("classrepresentative")) {
            Intent inn = new Intent(this, actadminprofile.class);
            System.out.println("inside success22");
            inn.putExtra(KEY_EMAIL, username);
            System.out.println(username);
            System.out.println("inside success222");
            startActivity(inn);
            System.out.println("inside success2223");
        }
        else
        {
            Intent inn2 = new Intent(this, actprofile.class);
            System.out.println("inside success22");
            inn2.putExtra(KEY_EMAIL, username);
            System.out.println(username);
            System.out.println("inside success222");
            startActivity(inn2);
            System.out.println("inside success2223");
        }
    }

    //@Override
    public boolean validate(){
        boolean valid =true;

        if(TextUsername.getText().toString().trim().isEmpty()|| !Patterns.EMAIL_ADDRESS.matcher(TextUsername.getText().toString().trim()).matches()) {
            TextUsername.setError("Please enter a valid email address");
            valid= false;
        }
        if(TextPassword.getText().toString().trim().isEmpty()){
            TextPassword.setError("Please Enter password");
            valid=false;
        }

        return valid;
    }
    public void onClick(View v) {
        if (v == buttonLogin) {
            System.out.println("inside onclick");
            //CheckBox checkBox = (CheckBox) v;
            //String chk=TextCheckbox.getText().toString().trim();
            if (TextCheckbox.isChecked()) {
                System.out.println("admin");
                 tab = "classrepresentative";
            } else {
                System.out.println("student");
                 tab = "student";

            }
            if(!validate())
            {
                Toast.makeText(this,"Sign Up Failed ",Toast.LENGTH_LONG).show();
            }
            else
            {
                userLogin(tab);
            }
        }

    }


    public void opensigup(View v)
    {
        Intent in= new Intent(this,actsignup.class);
        startActivity(in);
        //bbaah
    }
}
