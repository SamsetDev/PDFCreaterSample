/*
        Copyright 2016 Sanjay Singh.

        Licensed under the Apache License, Version 2.0 (the "License");
        you may not use this file except in compliance with the License.
        You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

        Unless required by applicable law or agreed to in writing, software
        distributed under the License is distributed on an "AS IS" BASIS,
        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
        See the License for the specific language governing permissions and
        limitations under the License.*/

package com.samset.create_pdf_sample.actvities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.samset.create_pdf_sample.R;

public class SplashActivity extends AppCompatActivity {
    public static final int REQUEST_WRITE_STORAGE_PERMISSION = 225;
    public static final int REQUEST_READ_STORAGE_PERMISSION = 226;
    private AppCompatActivity activity;
    private String TAG=SplashActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        this.activity=this;
        checkPermission();
    }

    public void checkPermission() {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, " Permission Already given ");

            splashOperation();
        } else {
            Log.d(TAG, "Current app does not have READ_PHONE_STATE permission");
            ActivityCompat.requestPermissions(activity, new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_WRITE_STORAGE_PERMISSION);

        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Log.e(TAG, "Permission request" + requestCode);

        switch (requestCode) {

            case REQUEST_WRITE_STORAGE_PERMISSION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e(TAG, "Permission Granted");

                }
                else {
                    Log.e(TAG, "Permission Denied");
                }
                return;
            }
            case REQUEST_READ_STORAGE_PERMISSION:
            {
                if (grantResults.length > 0 && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    Log.e(TAG, "Permission Granted");


                }
                else {
                    Log.e(TAG, "Permission Denied");
                }
                return;
            }

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void splashOperation()
    {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                        Thread.sleep(2000);

                }catch (Exception ex){ex.printStackTrace();}

                startActivity(new Intent(SplashActivity.this,MainActivity.class));
            }
        }).start();

    }
}
