package com.example.anand.cisytem;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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

/**
 * Created by anand on 03-11-2016.
 */



/**
 * Created by Belal on 18/09/16.
 */


public class actadmin_category extends Fragment {

    String[] id_array;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments

        return inflater.inflate(R.layout.activity_adminfrag_listcategory, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles

        getActivity().setTitle("List Category");
        loaddata();
        Button add_category=(Button)getView().findViewById(R.id.btnaddcategory);
        add_category.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent in=new Intent(getActivity(),actadmin_caregory_add.class);
                startActivity(in);



            }
        });

    }
private void loaddata()
{
// TODO: 12-11-2016 proper url with cr id [done]
    String query="select * from category where crid='"+constants.id+"'";
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
                        Toast.makeText(getActivity(), volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "server connection failed", Toast.LENGTH_LONG).show();
                    }
                }
            });

    RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
    requestQueue.add(stringRequest);
}

    private void makeJSON(String response){
        String temp="";
        System.out.println("inside makeJson");

        String[] crid;
        String[] classroomid;
        String[] name;
        try {
            System.out.println("inside makejason try");


            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("result");
            int size=result.length();
            id_array=new String[size];
            crid=new String[size];
            classroomid=new String[size];
            name= new  String[size];


            for(int i=0;i<result.length();i++) {
                JSONObject collegeData = result.getJSONObject(i);
                id_array[i] = collegeData.getString("id");
                crid[i] = collegeData.getString("crid");
                name[i] = collegeData.getString("name");
                classroomid[i] = collegeData.getString("classroomid");
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
        ListView listView = (ListView) getView().findViewById(R.id.lstcategory);


            ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, names);
            listView.setAdapter(itemsAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {

                    String item = ((TextView) view).getText().toString();
                    String str="positoin="+position+"id="+id+"item="+item;
                    //Toast.makeText(getActivity(), str, Toast.LENGTH_LONG).show();
                    openedit(position);
                }
            });
    }
     void openedit(int itemid)
    {
        String db_id=id_array[itemid];
        //System.out.println(cat_id);
        Intent in=new Intent(getActivity(),actadmin_category_edit.class);
        in.putExtra("cat_id",db_id);
        startActivity(in);
    }



}

