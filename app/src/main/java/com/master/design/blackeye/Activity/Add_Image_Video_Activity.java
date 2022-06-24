package com.master.design.blackeye.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.master.design.blackeye.Helper.MyVideoView;
import com.master.design.blackeye.R;
import com.master.design.blackeye.Utils.CameraUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Add_Image_Video_Activity extends AppCompatActivity {

    ImageView img1, img2;
    MyVideoView vd1, vd2;
    int imgClicked, videoclicked;
    int REQUEST_IMAGE = 999;
    private static final int IMAGE_VIDEO_ACTIVITY_PICKER = 4;
    Uri Video1, Video2;
    boolean ifimg1 = false;
    boolean ifimg2 = false;
    boolean v1 = false, v2 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_image_video);
        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        vd1 = findViewById(R.id.vd1);
        vd2 = findViewById(R.id.vd2);

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgClicked = 1;

                OpenImage();
            }
        });
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgClicked = 2;
                OpenImage();
            }
        });
        vd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imgClicked = 1;
                OpenImage();
            }
        });
        vd2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgClicked = 2;
                OpenImage();
            }
        });
    }

    public void OpenImage() {
        Dexter.withActivity(Add_Image_Video_Activity.this)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            showImagePickerOptions();
                        }

                        if (report.isAnyPermissionPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void showImagePickerOptions() {
        ImagePickerActivity.showImagePickerOptions(Add_Image_Video_Activity.this, new ImagePickerActivity.PickerOptionListener() {
            @Override
            public void onTakeCameraSelected() {
                launchCameraIntent();
            }

            @Override
            public void onTakeCameraSelectedVideo() {
//                launchCameraIntent();
                launchCameraIntentVideo();
            }

            @Override
            public void onChooseGallerySelected() {
                launchGalleryIntent();
            }
        }, true);
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Add_Image_Video_Activity.this);
        builder.setTitle(getString(R.string.dialog_permission_title));
        builder.setMessage(getString(R.string.dialog_permission_message));
        builder.setPositiveButton(getString(R.string.go_to_settings), (dialog, which) -> {
            dialog.cancel();
//            openSettings();
        });
        builder.setNegativeButton(getString(android.R.string.cancel), (dialog, which) -> dialog.cancel());
        builder.show();

    }


    private void launchCameraIntent() {

        Intent intent = new Intent(Add_Image_Video_Activity.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);

        // setting maximum bitmap width and height
        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000);

        startActivityForResult(intent, REQUEST_IMAGE);
    }

    private void launchCameraIntentVideo() {


        Intent intent = new Intent(Add_Image_Video_Activity.this, CameraHandling.class);
        intent.putExtra("mode", "video");
        startActivityForResult(intent, IMAGE_VIDEO_ACTIVITY_PICKER);
    }

    private void launchGalleryIntent() {
        Intent intent = new Intent(Add_Image_Video_Activity.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);
        startActivityForResult(intent, REQUEST_IMAGE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getParcelableExtra("path");
                try {
                    // You can update this bitmap to your server
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(Add_Image_Video_Activity.this.getContentResolver(), uri);
                    if (imgClicked == 1) {
                        img1.setImageBitmap(bitmap);
                        ifimg1 = true;
                    } else if (imgClicked == 2) {
                        img2.setImageBitmap(bitmap);
                        ifimg2 = true;
                    }
                    // loading profile image from local cache

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (requestCode == IMAGE_VIDEO_ACTIVITY_PICKER) {
            if (data != null) {
                if (data.getStringExtra("mode").equalsIgnoreCase("photo")) {
                    Uri.fromFile(new File(data.getStringExtra("uri")));

                } else {


                    if (imgClicked == 1) {
                        Video1 = Uri.fromFile(new File(data.getStringExtra("uri")));
                        String path = Video1.getPath();
                        CameraUtils.refreshGallery(getApplicationContext(), path);
                        vd1.setVideoPath(path);
                        // start playing
                        vd1.start();
                        ifimg1 = false;
                        v1 = true;
                    } else if (imgClicked == 2) {

                        Video2 = Uri.fromFile(new File(data.getStringExtra("uri")));
                        String path = Video2.getPath();
                        CameraUtils.refreshGallery(getApplicationContext(), path);

                        vd2.setVideoPath(path);
                        // start playing
                        vd2.start();
                        ifimg2 = false;
                        v2 = true;

                    }
                }

            }
        }

        super.onActivityResult(requestCode, resultCode, data);

    }
}
