package com.example.anand.cisytem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class actlogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actlogin);

    }
    public void opensigup(View v)
    {
        Intent in= new Intent(this,actsignup.class);
        startActivity(in);
    }
}
