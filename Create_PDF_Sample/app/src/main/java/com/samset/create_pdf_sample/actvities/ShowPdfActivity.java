package com.samset.create_pdf_sample.actvities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;

import com.samset.create_pdf_sample.R;
import com.samset.create_pdf_sample.listeners.CurrentView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ShowPdfActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ParcelFileDescriptor mFileDescriptor;
    private PdfRenderer mPdfRenderer;
    private Intent intent;
    private String strinput;
    private ImageView pdfView;
    private static int currentView;

    private PdfRenderer.Page mCurrentPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_pdf);
        pdfView=(ImageView)findViewById(R.id.iv_pdfview);
        toolbar=(Toolbar)findViewById(R.id.tool);
        toolbar.setTitle(R.string.app_name);
        intent=getIntent();
        strinput=intent.getStringExtra("data");
        if (!strinput.isEmpty())
        {
            openRenderer(strinput);
            showPage(1);

            Log.v("Show"," data "+strinput);
        }else{
            Log.v("Show"," data null ");
        }

    }



    @SuppressLint("NewApi")
    private void openRenderer(String filePath) {
        File file = new File(filePath);
        try {
            mFileDescriptor = ParcelFileDescriptor.open(file,ParcelFileDescriptor.MODE_READ_ONLY);
            mPdfRenderer = new PdfRenderer(mFileDescriptor);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * API for cleanup of objects used in rendering
     */
    @SuppressLint("NewApi")
    private void closeRenderer() {

        try {
            if (mCurrentPage != null)
                mCurrentPage.close();
            if (mPdfRenderer != null)
                mPdfRenderer.close();
            if (mFileDescriptor != null)
                mFileDescriptor.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @SuppressLint("NewApi")
    private void showPage(int index) {
        if (mPdfRenderer == null || mPdfRenderer.getPageCount() <= index
                || index < 0) {
            return;
        }
        // For closing the current page before opening another one.
        try {
            if (mCurrentPage != null) {
                mCurrentPage.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Open page with specified index
        mCurrentPage = mPdfRenderer.openPage(index);
        Bitmap bitmap = Bitmap.createBitmap(mCurrentPage.getWidth(),
                mCurrentPage.getHeight(), Bitmap.Config.ARGB_8888);

        // Pdf page is rendered on Bitmap
        mCurrentPage.render(bitmap, null, null,
                PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
        // Set rendered bitmap to ImageView
         pdfView.setImageBitmap(bitmap);

    }


}
