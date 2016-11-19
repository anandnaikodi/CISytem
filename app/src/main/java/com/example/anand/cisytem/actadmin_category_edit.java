package com.example.anand.cisytem;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

public class actadmin_category_edit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actadmin_category_edit);
        TextView textView=(TextView)findViewById(R.id.textView2);
        //textView.setText(getIntent().getExtras().getString("itemid"));
        //String data = getIntent().getExtras().getString("itemid").toString();
        Toast.makeText(this,getIntent().getExtras().getString("db_id"),Toast.LENGTH_LONG).show();
    }
}
