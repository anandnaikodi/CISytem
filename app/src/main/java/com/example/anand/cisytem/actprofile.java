package com.example.anand.cisytem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by anand on 15-10-2016.
 */

public class actprofile extends AppCompatActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actprofile);

        textView = (TextView) findViewById(R.id.textViewUsername);

        Intent intent = getIntent();

        textView.setText("Welcome User " + intent.getStringExtra(actlogin.KEY_EMAIL));
    }
    public void openimage(View v)
    {
        //Intent in= new Intent(this,actadmin_addannouncement.class);
        Intent in= new Intent(this,actuploadimg.class);
        startActivity(in);
        //bbaah
    }
}
