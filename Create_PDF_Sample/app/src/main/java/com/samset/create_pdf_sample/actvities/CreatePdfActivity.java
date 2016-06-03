package com.samset.create_pdf_sample.actvities;

import android.annotation.SuppressLint;
import android.graphics.pdf.PdfDocument;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.samset.create_pdf_sample.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CreatePdfActivity extends AppCompatActivity {
    // Pdf content will be generated with User Input Text
    private EditText et_inputcontent;
    private AppCompatButton btncreate;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pdf);
        toolbar=(Toolbar)findViewById(R.id.tool1);
        toolbar.setTitle(R.string.app_name);
        et_inputcontent =(EditText)findViewById(R.id.pdf_content);
        btncreate=(AppCompatButton)findViewById(R.id.btn_genrate);

        btncreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String strcontent= et_inputcontent.getText().toString();

                if (strcontent.isEmpty())
                {
                    Toast.makeText(CreatePdfActivity.this,"Enter some text",Toast.LENGTH_SHORT).show();
                }else
                {
                    new PdfGenerationTask().execute();
                }
            }
        });

    }
    @SuppressLint("NewApi")
    private class PdfGenerationTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
             final PdfDocument document  = new PdfDocument();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    // repaint the user's text into the page
                    View content = findViewById(R.id.pdf_content);

                    // crate a page description
                    int pageNumber = 1;

                    PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(content.getWidth(),content.getHeight() - 20, pageNumber).create();

                    // create a new page from the PageInfo
                    PdfDocument.Page page = document.startPage(pageInfo);

                    content.draw(page.getCanvas());

                    // do final processing of the page
                    document.finishPage(page);

                }
            });



            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyhhmmss");
            String pdfName = "sampdfdemo"+ sdf.format(Calendar.getInstance().getTime()) + ".pdf";
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File outputFile = new File(path, pdfName);

            try {
                outputFile.createNewFile();
                OutputStream out = new FileOutputStream(outputFile);
                document.writeTo(out);
                document.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return outputFile.getPath();
        }

        @Override
        protected void onPostExecute(String filePath) {
            if (filePath != null) {
                et_inputcontent.setText("");
                Toast.makeText(getApplicationContext(),
                        "Pdf saved at " + filePath, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Error in Pdf creation" + filePath, Toast.LENGTH_SHORT)
                        .show();
            }

        }

    }
}
