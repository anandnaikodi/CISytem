package com.example.anand.cisytem;

/**
 * Created by anand on 08-11-2016.
 */

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import MyCustomPackage.constants;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextId;
    private Button buttonGet;
    private TextView textViewResult;

    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datafetch);

        editTextId = (EditText) findViewById(R.id.editTextId);
        buttonGet = (Button) findViewById(R.id.buttonGet);
        textViewResult = (TextView) findViewById(R.id.textViewResult);

        buttonGet.setOnClickListener(this);
    }

    private void getData() {
        String id = editTextId.getText().toString().trim();
        if (id.equals("")) {
            Toast.makeText(this, "Please enter an id", Toast.LENGTH_LONG).show();
            return;
        }
        loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);

        String url = constants.url+"/CIS/getData.php?id="+editTextId.getText().toString().trim();
        url="http://192.168.43.53/CIS/fetchrow.php?q=SELECT+*+FROM+student";
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                System.out.println(response);
                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
void  listing() {
    try {
        String[] items = {"abc", "def", "hjfhf"};
        ListView testing = (ListView) findViewById(R.id.lsttrying);
        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        testing.setAdapter(itemsAdapter);

        testing.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                String item = ((TextView) view).getText().toString();

                Toast.makeText(getBaseContext(), item, Toast.LENGTH_LONG).show();

            }
        });
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    private void showJSON(String response){
        String temp="";
        String name="";
        String email="";
        String password = "";
        listing();
        try {

            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("result");
            for(int i=0;i<result.length();i++) {
                JSONObject collegeData = result.getJSONObject(i);
                name = collegeData.getString("name");
                email = collegeData.getString("email");
                password = collegeData.getString("password");
                temp=temp+" "+name+email+password;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //textViewResult.setText("Name:\t"+name+"\nEmail:\t" +email+ "\nPassword:\t"+ password);
        textViewResult.setText(temp);
    }

    @Override
    public void onClick(View v) {
        getData();
    }
}