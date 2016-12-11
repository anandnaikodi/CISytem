package com.example.anand.cisytem;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import MyCustomPackage.constants;

/**
 * Created by anand on 03-11-2016.
 */



/**
 * Created by Belal on 18/09/16.
 */


public class actadmin_upload extends Fragment {
    private Button buttonChoose;
    private Button buttonUpload;

    private ImageView imageView;

    private EditText editTextName;

    private Bitmap bitmap;

    private int PICK_IMAGE_REQUEST = 1;

    private String UPLOAD_URL = constants.url+"/CIS/upload.php";

    private String KEY_IMAGE = "image";
    private String KEY_NAME = "name";
    String categoryid;
    View myView;
    String[] id_array;
    String[] category_array;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        myView = inflater.inflate(R.layout.activity_adminfrag_upload, container, false);
       // myView = (ViewGroup) inflater.inflate(R.layout.fragment, container, false);

        return inflater.inflate(R.layout.activity_adminfrag_upload, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("upload");
        loaddata();


        editTextName = (EditText) getView().findViewById(R.id.editText);

        imageView  = (ImageView) getView().findViewById(R.id.imageView);
        buttonChoose = (Button) getView().findViewById(R.id.buttonChoose);
        buttonUpload = (Button) getView().findViewById(R.id.buttonUpload);

        buttonChoose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showFileChooser();
            }
        });

        buttonUpload.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                uploadImage();
            }
        });
//        buttonChoose.setOnClickListener(this);
//        buttonUpload.setOnClickListener(this);
    }


    private void loaddata()
    {
// TODO: 12-11-2016 proper url with cr id[done]
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

//        String[] crid;
//        String[] classroomid;
        String[] name;
        try {
            System.out.println("inside makejason try");


            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("result");
            int size=result.length();
            id_array=new String[size];
//            crid=new String[size];
//            classroomid=new String[size];
            name= new  String[size];


            for(int i=0;i<result.length();i++) {
                JSONObject collegeData = result.getJSONObject(i);
                id_array[i] = collegeData.getString("id");
                //crid[i] = collegeData.getString("crid");
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
    void loadlist(String[] names)
    {
       Spinner spinner2 = (Spinner) getView().findViewById(R.id.selcategory);
        List<String> list = new ArrayList<String>();
        for(int i=0;i<names.length;i++)
        {
            list.add(names[i]);
        }


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter);
    }
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void uploadImage(){
        //Showing the progress dialog
        Spinner spinner2 = (Spinner) getView().findViewById(R.id.selcategory);
        categoryid=id_array[((int) spinner2.getSelectedItemId())];
        final ProgressDialog loading = ProgressDialog.show(getActivity(),"Uploading...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        //Showing toast message of the response
                        Toast.makeText(getActivity(), s , Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(getActivity(), volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
                String image = getStringImage(bitmap);

                //Getting Image Name
                String name = editTextName.getText().toString().trim();

                //Creating parameters
                Map<String,String> params = new Hashtable<String, String>();

                //Adding parameters
                params.put(KEY_IMAGE, image);
                params.put(KEY_NAME, name);
                params.put("crid", ""+constants.id);
                params.put("categoryid", ""+categoryid);


                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

//    @Override
//    public void onClick(View v) {
//
//        if(v == buttonChoose){
//            showFileChooser();
//        }
//
//        if(v == buttonUpload){
//            uploadImage();
//        }
//    }

}

