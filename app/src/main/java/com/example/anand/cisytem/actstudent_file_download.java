package com.example.anand.cisytem;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import MyCustomPackage.constants;

public class actstudent_file_download extends AppCompatActivity {
    Button button;
    ImageView imageview;
    String image_name;
    String image_url;
    String image_extension;
    //String image_url = "http://192.168.43.163:8080/CIS/uploads/1.png";
    //db_id=getIntent().getExtras().getString("db_id");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actstudent_file_download);
        button = (Button) findViewById(R.id.button);
        imageview = (ImageView) findViewById(R.id.image_view);

        image_url = getIntent().getExtras().getString("url");
        image_url= constants.url+"/CIS/uploads/"+image_url;
        image_name=getIntent().getExtras().getString("name");
        image_extension=image_url.substring(image_url.lastIndexOf("."));
        //System.out.println(image_url+image_name+image_extension);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                DownloadTask downloadTask= new DownloadTask();
                downloadTask.execute(image_url);

            }
        });
    }


    public class DownloadTask extends AsyncTask<String, Integer, String> {

        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {

            progressDialog = new ProgressDialog(actstudent_file_download.this);
            progressDialog.setTitle("Download In progress ...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setMax(100);
            progressDialog.setProgress(0);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            String path = params[0];
            int file_length=0;
            try {
                URL url= new URL(path);
                URLConnection urlConnection = url.openConnection();
                urlConnection.connect();
                file_length=urlConnection.getContentLength();
                File new_folder= new File("/storage/emulated/0/#Images/");
                if(!new_folder.exists())
                {
                    new_folder.mkdir();
                    System.out.println("in create directory");
                }
                System.out.println(new_folder);
                // TODO: 22-11-2016 file name
                File input_file= new File(new_folder,image_name+""+image_extension);
                InputStream inputStream = new BufferedInputStream(url.openStream(),8192);
                byte[] data = new byte[1024];
                int total =0;
                int count =0;
                OutputStream outputStream = new FileOutputStream(input_file);
                System.out.println(outputStream);
                while((count=inputStream.read(data))!=-1)
                {
                    total+= count;
                    outputStream.write(data,0,count);
                    int progress= (int)total*100/file_length;
                    publishProgress(progress);


                }
                inputStream.close();
                outputStream.close();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "Download Complete...";

        }

        protected void onProgressUpdate(Integer... values) {

            progressDialog.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String result) {

            progressDialog.hide();
            Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
            // TODO: 22-11-2016 image name
            String path="/storage/emulated/0/#Images/"+image_name+""+image_extension;
            imageview.setImageDrawable(Drawable.createFromPath(path));
        }
    }
}
