package com.example.anand.cisytem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.net.URLEncoder;

import MyCustomPackage.constants;

public class actadmin_caregory_add extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actadmin_caregory_add);
    }

    private void send(){
        TextView cat_name=(TextView)findViewById(R.id.txtcatadd);
        String query="INSERT INTO category (crid, classroomid, name) VALUES ('"+ constants.id+"','"+constants.classroom_id+"','"+cat_name.getText()+"')";
        // TODO: convert to post if possible
        try{
            query= URLEncoder.encode(query,"UTF-8");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        String url= constants.url+"/CIS/insert.php?q="+query;
        System.out.println(url);
//        final String table=tablelocal;
//        final String username = Textname.getText().toString().trim();
//        final String email = TextEmail.getText().toString().trim();
//        final String password = TextPassword.getText().toString().trim();
        //final ProgressDialog loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //loading.dismiss();
                        Toast.makeText(actadmin_caregory_add.this,response,Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //loading.dismiss();
                        if(volleyError.getMessage()!=null) {
                            Toast.makeText(actadmin_caregory_add.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(actadmin_caregory_add.this, "server connection failed", Toast.LENGTH_LONG).show();
                        }
                        //Toast.makeText(actadmin_addannouncement.this, volleyError.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }

                }){


        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    void button_send(View v)
    {
        send();
    }

}
