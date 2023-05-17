package com.master.design.therapist.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.makeramen.roundedimageview.RoundedImageView;
import com.master.design.therapist.Adapter.TherapistEducationDM;
import com.master.design.therapist.Controller.AppController;
import com.master.design.therapist.DataModel.ProfileDM;
import com.master.design.therapist.DataModel.Update_Pic_ProfileDM;
import com.master.design.therapist.Helper.DialogUtil;
import com.master.design.therapist.Helper.Helper;
import com.master.design.therapist.Helper.User;
import com.master.design.therapist.R;
import com.master.design.therapist.Utils.ConnectionDetector;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.echodev.resizer.Resizer;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.MultipartTypedOutput;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedString;

public class My_ProfileActivity extends AppCompatActivity {



    AppController appController;
    ConnectionDetector connectionDetector;
    User user;
    DialogUtil dialogUtil;
    Dialog progress;
    Context context;

    @BindView(R.id.editImg)
    ImageView editImg;

    @BindView(R.id.userNameET)
    EditText userNameET;

    @BindView(R.id.emailET)
    EditText emailET;

    @BindView(R.id.phoneET)
    EditText phoneET;

    @BindView(R.id.genderET)
    EditText genderET;

    @BindView(R.id.dobET)
    EditText dobET;

    @BindView(R.id.profileImgRIV)
    ImageView profileImgRIV;


//    @OnClick(R.id. editProfileTxt)
//    public void  editProfileTxt() {
//       UpdateImageProfileAPI();
//    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        ButterKnife.bind(this);
        appController = (AppController)  getApplicationContext();
        connectionDetector = new ConnectionDetector(My_ProfileActivity.this);
        user = new User(My_ProfileActivity.this);
        dialogUtil = new DialogUtil();
        context=getApplicationContext();
        Binding();

    }

    boolean ifimg1 = false;
    @OnClick(R.id.editImg)
    public void clickEditImg() {
        ifimg1 = true;
        OpenImage();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getParcelableExtra("path");
                try {
                    // You can update this bitmap to your server
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(My_ProfileActivity.this.getContentResolver(), uri);

                    profileImgRIV.setImageBitmap(bitmap);
                    ifimg1 = true;
                    UpdateImageProfileAPI();

                    // loading profile image from local cache

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void OpenImage() {
        Dexter.withActivity(this)
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
        ImagePickerActivity.showImagePickerOptions(My_ProfileActivity.this, new ImagePickerActivity.PickerOptionListener() {
            @Override
            public void onTakeCameraSelected() {
                launchCameraIntent();
            }

            @Override
            public void onTakeCameraSelectedVideo() {

            }

            @Override
            public void onChooseGallerySelected() {
                launchGalleryIntent();
            }

            @Override
            public void selectVideoFromGallery() {

            }
        },false);
    }

    private void launchCameraIntent() {
        Intent intent = new Intent(My_ProfileActivity.this, ImagePickerActivity.class);
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

    int REQUEST_IMAGE = 999;

    private void launchGalleryIntent() {
        Intent intent = new Intent(My_ProfileActivity.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);
        startActivityForResult(intent, REQUEST_IMAGE);
    }


    /**
     * Showing Alert Dialog with Settings option
     * Navigates user to app settings
     * NOTE: Keep proper title and message depending on your app
     */
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(My_ProfileActivity.this);
        builder.setTitle(getString(R.string.dialog_permission_title));
        builder.setMessage(getString(R.string.dialog_permission_message));
        builder.setPositiveButton(getString(R.string.go_to_settings), (dialog, which) -> {
            dialog.cancel();
//            openSettings();
        });
        builder.setNegativeButton(getString(android.R.string.cancel), (dialog, which) -> dialog.cancel());
        builder.show();

    }


    @OnClick(R.id.editProfileTxt)
    public void clickEditProfile() {
        startActivity(new Intent(My_ProfileActivity.this, Edit_ProfileActivity.class));
        overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);
    }

    @OnClick(R.id.backImg)
    public void clickBack() {
        finish();
    }

    @Override
    public void onBackPressed() {
        overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);
        super.onBackPressed();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);
    }

    public void Binding()
    {
        if (connectionDetector.isConnectingToInternet()) {
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
            progress = dialogUtil.showProgressDialog(My_ProfileActivity.this, getString(R.string.please_wait));
            appController.paServices.TherapistProfile(String.valueOf(user.getId()),new Callback<ProfileDM>() {
                @Override
                public void success(ProfileDM profileDM, Response response) {
                    progress.dismiss();
                    if (profileDM.getStatus().equalsIgnoreCase("1")) {

                     userNameET.setText(profileDM.getUser_data().get(0).getName());
                     emailET.setText(profileDM.getUser_data().get(0).getEmail());
                     phoneET.setText(profileDM.getUser_data().get(0).getPhone());
                     genderET.setText(profileDM.getUser_data().get(0).getGender());
                     dobET.setText(profileDM.getUser_data().get(0).getDob());

                  Picasso.with(context).load(AppController.THERAPIST_IMAGE+profileDM.getUser_data().get(0).getImage()).into(profileImgRIV);


                    } else
                        Helper.showToast(My_ProfileActivity.this, getString(R.string.Api_data_not_found));
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Log.e("error", retrofitError.toString());
            }
        });
    } else
            Helper.showToast(My_ProfileActivity.this, getString(R.string.no_internet_connection));
}



  public void UpdateImageProfileAPI()
        {
            if (connectionDetector.isConnectingToInternet()) {

                MultipartTypedOutput multipartTypedOutput = new MultipartTypedOutput();
                multipartTypedOutput.addPart("id", new TypedString(String.valueOf(user.getId())));

                try {
                    if (ifimg1)
                    {
                        File f = new File(context.getCacheDir(), "temp.jpg");
                        f.createNewFile();

                        Bitmap one = ((BitmapDrawable) profileImgRIV.getDrawable()).getBitmap();
//Convert bitmap to byte array
                        Bitmap bitmap = one;
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
                        byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
                        FileOutputStream fos = new FileOutputStream(f);
                        fos.write(bitmapdata);
                        fos.flush();
                        fos.close();
                        File resizedImage = new Resizer(context)
                                .setTargetLength(512)
                                .setQuality(80)
                                .setOutputFormat("JPEG")
                                .setOutputFilename("resized_image1")
                                .setSourceImage(f)
                                .getResizedFile();
                        multipartTypedOutput.addPart("image", new TypedFile("image/jpg", resizedImage));
                    }


                } catch (Exception e) {
                    Log.e("Error", e.toString());
                }
                progress = dialogUtil.showProgressDialog(My_ProfileActivity.this, getString(R.string.please_wait));

                appController.paServices.TherapistUpdate_Pic(multipartTypedOutput, new Callback<Update_Pic_ProfileDM>() {
                    @Override
                    public void success(Update_Pic_ProfileDM update_pic_profileDM, Response response) {
                        progress.dismiss();

                        if (update_pic_profileDM.getStatus().equalsIgnoreCase("1")) {

                            Helper.showToast(My_ProfileActivity.this, update_pic_profileDM.getMsg());
                        } else
                            Helper.showToast(My_ProfileActivity.this, update_pic_profileDM.getMsg());
                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {
                        progress.dismiss();

                        Log.e("error", retrofitError.toString());

                    }
                });
            } else
                Helper.showToast(My_ProfileActivity.this, getString(R.string.no_internet_connection));
         }

}
