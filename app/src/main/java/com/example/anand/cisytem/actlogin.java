package com.example.anand.cisytem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.CheckBox;
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

import MyCustomPackage.constants;
import customfonts.MyEditText;
import customfonts.MyTextView;

public class actlogin extends AppCompatActivity implements View.OnClickListener{
    public static final String LOGIN_URL = constants.url+"/CIS/login.php";

    public static final String KEY_EMAIL="email";
    public static final String KEY_PASSWORD="password";
    public static final String KEY_TABLE = "table";

    private MyEditText  TextUsername;
    private MyEditText  TextPassword;
    private CheckBox TextCheckbox;
    private MyTextView buttonLogin;

    private String username;
    private String password;
    private String tab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actlogin);
        TextUsername = (MyEditText) findViewById(R.id.txt_email);
        TextPassword = (MyEditText ) findViewById(R.id.txt_password);
        TextCheckbox=(CheckBox) findViewById(R.id.chk_admin);

        buttonLogin = (MyTextView) findViewById(R.id.btn_login);
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
                        if(response.trim().equals("failure")||response.trim().equals("error"))
                        {
                            //loading.dismiss();
                            Toast.makeText(actlogin.this,response,Toast.LENGTH_LONG).show();
                        }else{
                            System.out.println("inside success");
                            constants.id=Integer.parseInt(response.trim());
                            openProfile(table);
                            System.out.println("inside success2");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                       // loading.dismiss();
                        if(volleyError.getMessage()!=null) {
                            Toast.makeText(actlogin.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                        }
                        else
                            Toast.makeText(actlogin.this, "server connection problem", Toast.LENGTH_LONG).show();

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
void clearfields()
{
    TextUsername.setText("");
    TextPassword.setText("");
    TextCheckbox.setChecked(false);
}
    public void openProfile(String tablelocal){
        final String table2=tablelocal;
        if(table2.equals("classrepresentative")) {
            constants.classroom_id=constants.id;
            Intent inn = new Intent(this, actadmin_profile.class);
            System.out.println("inside success22");
            inn.putExtra(KEY_EMAIL, username);
            System.out.println(username);
            System.out.println("inside success222");
            clearfields();
            startActivity(inn);
            System.out.println("inside success2223");
        }
        else
        {
            //Intent inn2 = new Intent(this, actstudent_profile.class);
            Intent inn2 = new Intent(this, actstudent_add_class.class);
            System.out.println("inside success22");
            inn2.putExtra(KEY_EMAIL, username);
            System.out.println(username);
            System.out.println("inside success222");
            clearfields();
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
        // TODO: 12-11-2016 add proper redirect
        //Intent in= new Intent(this,MainActivity.class);
        Intent in= new Intent(this,actsignup.class);
        //Intent in= new Intent(this,actstudent_profile.class);
        //Intent in= new Intent(this,actadmin_profile.class);
        //Intent in= new Intent(this,actsignup.class);
        startActivity(in);
        //bbaah
    }
}
