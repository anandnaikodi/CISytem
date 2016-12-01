package com.example.anand.cisytem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.net.URLEncoder;

import MyCustomPackage.constants;

public class actstudent_add_class extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actstudent_add_class);
        check();
    }

    void check()
    {
// TODO: 12-11-2016 nothing[done]
        String query="select * from class where studentid ='"+constants.id+"'";
        try{
            query= URLEncoder.encode(query,"UTF-8");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        String url= constants.url+"/CIS/checkrow.php?q="+query;

        StringRequest stringRequest = new StringRequest(Request.Method.GET,url ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("inside response");
                        open_page(response);
                        //System.out.print(response);
                        System.out.println("coming out of response");

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        // loading.dismiss();
                        // Toast.makeText(actlogin.this,volleyError.getMessage().toString(),Toast.LENGTH_LONG ).show();
                        if(volleyError.getMessage()!=null) {
                            Toast.makeText(actstudent_add_class.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(actstudent_add_class.this, "server connection failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    void open_page(String response)
    {
        if(response.trim().equals("1"))
        {
            Intent in=new Intent(this,actstudent_profile.class);
            startActivity(in);
        }


    }

    public void join_class(View v)
    {
        EditText classid=(EditText)findViewById(R.id.txt_classroomid);
        String str_id=classid.getText().toString();
        str_id=str_id.trim();
//        String query="select * from class where studentid ='"+constants.id+"'";
//        try{
//            query= URLEncoder.encode(query,"UTF-8");
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }

        String url= constants.url+"/CIS/addclass.php?class_id="+str_id+"&student_id="+constants.id;
        System.out.println(url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,url ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("inside response");
                        //Toast.makeText(actstudent_add_class.this,response,Toast.LENGTH_LONG).show();
                        if(response.trim().equals("0"))
                            Toast.makeText(actstudent_add_class.this,"invalid id",Toast.LENGTH_LONG).show();
                        else
                            open_page(response);
                        System.out.println("coming out of response");

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        // loading.dismiss();
                        // Toast.makeText(actlogin.this,volleyError.getMessage().toString(),Toast.LENGTH_LONG ).show();
                        if(volleyError.getMessage()!=null) {
                            Toast.makeText(actstudent_add_class.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(actstudent_add_class.this, "server connection failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
