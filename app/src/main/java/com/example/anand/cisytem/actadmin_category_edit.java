package com.example.anand.cisytem;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;

import MyCustomPackage.constants;

public class actadmin_category_edit extends AppCompatActivity {
    String db_id;
    String db_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actadmin_category_edit);
        //TextView textView=(TextView)findViewById(R.id.textView2);
        //textView.setText(getIntent().getExtras().getString("itemid"));
        //String data = getIntent().getExtras().getString("itemid").toString();

        //Toast.makeText(this,cat_id=getIntent().getExtras().getString("cat_id"),Toast.LENGTH_LONG).show();
        db_id=getIntent().getExtras().getString("cat_id");

        loaddata();
    }


    private void loaddata() {
        // TODO: 12-11-2016 load txt box with proper id name, code update button
        String query="select * from category where id="+db_id;
        try{
            query= URLEncoder.encode(query,"UTF-8");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        String URL= constants.url+"/CIS/fetchrow.php?q="+query;
        System.out.println(URL);

        //final String table=tablelocal;
        //final ProgressDialog loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override

                    public void onResponse(String response) {
                        //Toast.makeText(actadmin_category_edit.this,response,Toast.LENGTH_LONG).show();
                        makeJSON(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        // loading.dismiss();
                        if(volleyError.getMessage()!=null) {
                            Toast.makeText(actadmin_category_edit.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(actadmin_category_edit.this, "server connection failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void makeJSON(String response){
        String temp="";
        System.out.println("inside makeJson");


        try {
            System.out.println("inside makejason try");


            JSONObject jsonObject = new JSONObject(response);

            JSONArray result = jsonObject.getJSONArray("result");
            JSONObject collegeData = result.getJSONObject(0);
            db_name=collegeData.getString("name");
            TextView name=(TextView) findViewById(R.id.txtname);
            name.setText(db_name);


        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.print(temp);
    }

    private void send(String query,String file){
        //TextView cat_name=(TextView)findViewById(R.id.txtcatadd);
        //String query="INSERT INTO category (crid, classroomid, name) VALUES ('"+ constants.id+"','"+constants.classroom_id+"','"+cat_name.getText()+"')";
        // TODO: convert to post if possible[invalid]
        try{
            query= URLEncoder.encode(query,"UTF-8");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        String url= constants.url+"/CIS/"+file+"?q="+query;
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
                        Toast.makeText(actadmin_category_edit.this,response,Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //loading.dismiss();
                        if(volleyError.getMessage()!=null) {
                            Toast.makeText(actadmin_category_edit.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(actadmin_category_edit.this, "server connection failed", Toast.LENGTH_LONG).show();
                        }
                        //Toast.makeText(actadmin_addannouncement.this, volleyError.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }

                }){


        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public void bt_update(View v)
    {
        EditText txtname=(EditText)findViewById(R.id.txtname);

        String query="UPDATE category SET name='"+txtname.getText()+"' WHERE id='"+db_id+"'";
        send(query,"insert.php");
    }
    public void bt_delete(View v)
    {
        String query="DELETE FROM category WHERE id='"+db_id+"'";
        send(query,"insert.php");
    }
}