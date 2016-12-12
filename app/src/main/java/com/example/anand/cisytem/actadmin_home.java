package com.example.anand.cisytem;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.ArrayList;
import java.util.List;

import MyCustomPackage.CustomAdapter;
import MyCustomPackage.RowItem;
import MyCustomPackage.constants;

/**
 * Created by anand on 03-11-2016.
 */



/**
 * Created by Belal on 18/09/16.
 */


public class actadmin_home extends Fragment {

    String[] id_array;

////    String[] member_names;
////    TypedArray profile_pics;
    String[] status;
    String[] contactType;

    List<RowItem> rowItems;
    ListView mylistview;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.activity_adminfrag_home, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        //getActivity().setTitle("home");
        loaddata();

        //start_adapter();
    }


    private void loaddata()
    {
// TODO: 12-11-2016 proper url with cr id[done]
        String query="select * from announcement where crid='"+constants.id+"' order by id DESC";
        try{
            query= URLEncoder.encode(query,"UTF-8");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        String url= constants.url+"/CIS/fetchrow.php?q="+query;
        System.out.println(url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,url ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("inside response");
                        makeJSON(response);
                        System.out.println("coming out of response"+response);

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

    private void makeJSON(String response)
    {
        String temp="";
        System.out.println("inside makeJson");

        String[] data;
        String[] time;
        //String[] name;
        try {
            System.out.println("inside makejason try");


            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("result");
            int size=result.length();
            id_array=new String[size];
            status=new String[size];
            contactType = new String[size];
//            crid=new String[size];
//            classroomid=new String[size];
//            name= new  String[size];


            for(int i=0;i<result.length();i++) {
                JSONObject collegeData = result.getJSONObject(i);
                id_array[i] = collegeData.getString("id");
                status[i] = collegeData.getString("data");
                contactType[i] = collegeData.getString("time");
                //classroomid[i] = collegeData.getString("classroomid");
                //temp=temp+" "+id_array[i]+crid[0]+name[i]+classroomid[i];
            }
            //loadlist(name);

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.print(temp);

        start_adapter();
    }
    private void start_adapter()
    {
        rowItems = new ArrayList<RowItem>();

//        member_names = getResources().getStringArray(R.array.Member_names);

//        profile_pics = getResources().obtainTypedArray(R.array.profile_pics);

//        status = getResources().getStringArray(R.array.statues);

//        contactType = getResources().getStringArray(R.array.contactType);

//        member_names = getResources().getStringArray(R.array.Member_names);
//
//        profile_pics = getResources().obtainTypedArray(R.array.profile_pics);
//
//        statues = getResources().getStringArray(R.array.statues);
//
//        contactType = getResources().getStringArray(R.array.contactType);

        for (int i = 0; i < status.length; i++) {
            RowItem item = new RowItem( status[i],
                    contactType[i]);
            rowItems.add(item);
        }

        mylistview = (ListView) getView().findViewById(R.id.list);
        CustomAdapter adapter = new CustomAdapter(getActivity(), rowItems);
        mylistview.setAdapter(adapter);

        //mylistview.setOnItemClickListener(this);
    }
}