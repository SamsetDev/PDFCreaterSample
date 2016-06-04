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

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.samset.create_pdf_sample.R;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private AppCompatButton btn_write;
    private Toolbar toolbar;
    // File Descriptor for rendered Pdf file
    private ParcelFileDescriptor mFileDescriptor;
    private File[] filelist;
    private ArrayAdapter<String> adapter;
    ListView listView;
    String fileName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=(Toolbar)findViewById(R.id.tool0);
        toolbar.setTitle(R.string.app_name);

        btn_write=(AppCompatButton)findViewById(R.id.btn_create_);
        listView =(ListView)findViewById(R.id.pdfList);


        btn_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,CreatePdfActivity.class));
            }
        });
        new PdfListLoadTask().execute();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                fileName = adapter.getItem(position);
                startActivity(new Intent(MainActivity.this,ShowPdfActivity.class).putExtra("data", fileName));

            }
        });
    }

    private class PdfListLoadTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File files = new File(path+"");
            filelist = files.listFiles(new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    return ((name.endsWith(".pdf")));
                }
            });

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub

            if (filelist != null && filelist.length >= 1) {
                ArrayList<String> fileNameList = new ArrayList<>();
                for (int i = 0; i < filelist.length; i++)
                    fileNameList.add(filelist[i].getPath());
                adapter = new ArrayAdapter<>(getApplicationContext(),
                        R.layout.list_item, fileNameList);
                listView.setAdapter(adapter);
            } else {
                Toast.makeText(getApplicationContext(),
                        "No pdf file found, Please create new Pdf file",
                        Toast.LENGTH_LONG).show();

            }
        }

    }
}
