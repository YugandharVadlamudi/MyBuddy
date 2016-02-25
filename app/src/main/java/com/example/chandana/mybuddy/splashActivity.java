package com.example.chandana.mybuddy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by chandana on 27-01-2016.
 */
public class splashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        Thread d=new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    File myDirectory = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "/images_folder_creation_1");
                    Log.d("asbolutePath", "" + myDirectory.exists());
                    if(!myDirectory.exists())
                    {
                        Log.d("asbolutePath", "directoryCreated");
                        myDirectory.mkdir();
                    }
                    else
                    {
                        Log.d("asbolutePath", "directory didn't Created");
                    }
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            }
        };


        d.start();
    }
}
