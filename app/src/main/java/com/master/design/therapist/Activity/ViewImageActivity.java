package com.master.design.therapist.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageView;

import com.master.design.therapist.Helper.Helper;
import com.master.design.therapist.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ViewImageActivity extends AppCompatActivity {

    Context context;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.downlaodImg)
    ImageView downlaodImg;
    String imgLink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);
        ButterKnife.bind(this);
        context = getApplicationContext();

        if (getIntent() != null) {
              imgLink = getIntent().getStringExtra("img");
            if (imgLink != null) {
                Picasso.with(context).load(imgLink).placeholder(R.drawable.black_transparent_gradient).into(img);

            }
        }
    }

    @OnClick(R.id.downlaodImg)
    public  void clickdownlaodImg(){
        DownloadImage(imgLink);
    }
    void DownloadImage(String ImageUrl) {

        if (ContextCompat.checkSelfPermission(ViewImageActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(ViewImageActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ViewImageActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 123);
            ActivityCompat.requestPermissions(ViewImageActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 123);
             Helper.showToast(ViewImageActivity.this,getString(R.string.need_permision_download_img));
        } else {
//            showToast("Downloading Image...");
            Helper.showToast(ViewImageActivity.this,getString(R.string.downloading_image));

            //Asynctask to create a thread to downlaod image in the background
            new DownloadsImage().execute(ImageUrl);
        }
    }

    class DownloadsImage extends AsyncTask<String, Void,Void> {

        @Override
        protected Void doInBackground(String... strings) {
            URL url = null;
            try {
                url = new URL(strings[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Bitmap bm = null;
            try {
                bm =    BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Create Path to save Image
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES+ "/AndroidDvlpr"); //Creates app specific folder

            if(!path.exists()) {
                path.mkdirs();
            }

            File imageFile = new File(path, String.valueOf(System.currentTimeMillis())+".png"); // Imagename.png
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(imageFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try{
                bm.compress(Bitmap.CompressFormat.PNG, 100, out); // Compress Image
                out.flush();
                out.close();
                // Tell the media scanner about the new file so that it is
                // immediately available to the user.
                MediaScannerConnection.scanFile(ViewImageActivity.this,new String[] { imageFile.getAbsolutePath() }, null,new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        // Log.i("ExternalStorage", "Scanned " + path + ":");
                        //    Log.i("ExternalStorage", "-> uri=" + uri);
                    }
                });
            } catch(Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
//            showToast("Image Saved!");
            Helper.showToast(ViewImageActivity.this,getString(R.string.image_saved));
        }
    }
}