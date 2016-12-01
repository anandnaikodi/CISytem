package com.example.anand.cisytem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

public class actstudent_category_files extends AppCompatActivity {
    String[] id_array;
    String[] paths;
    String db_id;
    //String[] classroomid;
    String[] name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actstudent_category_files);
    loaddata();
        db_id=this.getIntent().getExtras().getString("db_id");
    }

    private void loaddata()
    {
// TODO: 12-11-2016 proper url with cr id[done]
        String query="select * from files where categoryid='"+db_id+"'";
        try{
            query= URLEncoder.encode(query,"UTF-8");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        String url= constants.url+"/CIS/fetchrow.php?q="+query;

        StringRequest stringRequest = new StringRequest(Request.Method.GET,url ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("inside response");
                        makeJSON(response);
                        System.out.println("coming out of response");

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        // loading.dismiss();
                        // Toast.makeText(actlogin.this,volleyError.getMessage().toString(),Toast.LENGTH_LONG ).show();
                        if(volleyError.getMessage()!=null) {
                            Toast.makeText(actstudent_category_files.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(actstudent_category_files.this, "server connection failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(actstudent_category_files.this);
        requestQueue.add(stringRequest);
    }

    private void makeJSON(String response){
        String temp="";
        System.out.println("inside makeJson");


        try {
            System.out.println("inside makejason try");


            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("result");
            int size=result.length();
            id_array=new String[size];
            paths=new String[size];
            //classroomid=new String[size];
            name= new  String[size];


            for(int i=0;i<result.length();i++) {
                JSONObject collegeData = result.getJSONObject(i);
                id_array[i] = collegeData.getString("id");
                paths[i] = collegeData.getString("image");
                name[i] = collegeData.getString("name");
                //classroomid[i] = collegeData.getString("classroomid");
                //temp=temp+" "+id_array[i]+crid[0]+name[i]+classroomid[i];
            }
            loadlist(name);

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.print(temp);
    }
    private void loadlist(String[] names)
    {
        ListView listView = (ListView)findViewById(R.id.lstcategory);


        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(actstudent_category_files.this, android.R.layout.simple_list_item_1, names);
        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                //String item = ((TextView) view).getText().toString();
                //String str="positoin="+position+"id="+id+"item="+item;
                //Toast.makeText(getActivity(), str, Toast.LENGTH_LONG).show();
                download(position);
            }
        });
    }
    void download(int itemid)
    {
        //String db_id=id_array[itemid];
        //System.out.println(db_id);
        System.out.println("inside download");

        System.out.println(name[itemid]+" "+paths[itemid]);
        Intent in = new Intent(this,actstudent_file_download.class);
        in.putExtra("name",name[itemid]);
        in.putExtra("url",paths[itemid]);
        startActivity(in);
        //Toast.makeText(actstudent_category_files.this,"download image of id "+db_id,Toast.LENGTH_SHORT).show();
    }


}
