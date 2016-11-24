package com.example.anand.cisytem;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class actadmin_category_edit extends AppCompatActivity {
    String db_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actadmin_category_edit);
        TextView textView=(TextView)findViewById(R.id.textView2);
        //textView.setText(getIntent().getExtras().getString("itemid"));
        //String data = getIntent().getExtras().getString("itemid").toString();

        //Toast.makeText(this,db_id=getIntent().getExtras().getString("db_id"),Toast.LENGTH_LONG).show();
        db_id=getIntent().getExtras().getString("db_id");
        TextView name=(TextView) findViewById(R.id.txtname);
        name.setText(db_id);
        loaddata();
    }


    private void loaddata() {
        // TODO: 12-11-2016 proper url with cr id
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
                        Toast.makeText(actadmin_category_edit.this,response,Toast.LENGTH_LONG).show();

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


}