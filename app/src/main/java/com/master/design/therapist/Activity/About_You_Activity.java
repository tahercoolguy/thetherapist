package com.master.design.therapist.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
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
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.iid.FirebaseInstanceId;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.makeramen.roundedimageview.RoundedImageView;
import com.master.design.therapist.Controller.AppController;
import com.master.design.therapist.DataModel.TherapistAgeDM;
import com.master.design.therapist.DataModel.TherapistRegisterDM;
import com.master.design.therapist.Helper.DialogUtil;
import com.master.design.therapist.Helper.Helper;
import com.master.design.therapist.Helper.User;
import com.master.design.therapist.R;
import com.master.design.therapist.Utils.ConnectionDetector;

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

public class About_You_Activity extends AppCompatActivity {

    private Activity activity;
    private Context context;
    AppController appController;
    ConnectionDetector connectionDetector;
    User user;
    DialogUtil dialogUtil;
    Dialog progress;
    String multipartTypedOutput ;

    String username;
    String date;
    String selectCountry;
    String gender;
    String ethenicity;
    String mobilenumber;
    String email;
    String password;
    String confirmPassword;
    String InterestIdList;

    @BindView(R.id.aboutYouET)
    EditText aboutYouET;

    @BindView(R.id. educationET)
    EditText  educationET;

    @BindView(R.id.profileCircleImg)
    RoundedImageView profileCircleImg;

    @BindView(R.id.my_account_img)
    ImageView my_account_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_you);
        ButterKnife.bind(this);
        appController = (AppController)  getApplicationContext();
        connectionDetector = new ConnectionDetector(About_You_Activity.this);
        user = new User(About_You_Activity.this);
        dialogUtil = new DialogUtil();
        context = this.getApplicationContext();
        activity = this;

        username=getIntent().getStringExtra("userName");
        date=getIntent().getStringExtra("date");
        selectCountry=getIntent().getStringExtra("selectCountry");
        gender=getIntent().getStringExtra("gender");
        ethenicity=getIntent().getStringExtra("ethnicity");
        mobilenumber=getIntent().getStringExtra("mobileNumber");
        email=getIntent().getStringExtra("email");
        password=getIntent().getStringExtra("password");
        confirmPassword=getIntent().getStringExtra("confirmPassword");
        InterestIdList=getIntent().getStringExtra("InterestIdlist");
    }


    @OnClick(R.id.backImg)
    public void clickBack() {
        finish();
    }


    @OnClick(R.id.signUpTxt)
    public void clicksignUpTxt()
    {

//        if(connectionDetector.isConnectingToInternet())
//        {
//            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
//            progress = dialogUtil.showProgressDialog(About_You_Activity.this,getString(R.string.please_wait));
//
//                appController.paServices.TherapistRegister(userNameET.getText().toString(), date.getText().toString(), selectCountryET.getText().toString(), gender.getText().toString(), ethnicityyET.getText().toString(), emailEt.getText().toString(), passwordET.getText().toString(), confirmPasswordET.getText().toString(),, new Callback<TherapistRegisterDM>() {
//                    @Override
//
//                    public void success (TherapistRegisterDM therapistRegisterDM, Response response ) {
//                        progress.dismiss();
//                        if (therapistRegisterDM.getStatus().equalsIgnoreCase("1") ){
//
//
//                            user.setId(Integer.valueOf(therapistRegisterDM.getCustomerID()));
//
//
//                            startActivity(new Intent(About_You_Activity.this, ThankYouActivity.class));
//                            overridePendingTransition(R.anim.fade_in, R.anim.fade_in);
//
//                        } else
//                            Helper.showToast(About_You_Activity.this, customerRegisterDM.getMessage());
//                    }
//
//                    @Override
//                    public void failure ( RetrofitError retrofitError ) {
//                        progress.dismiss();
//                        Log.e("error", retrofitError.toString());
//
//                    }
//                });
//
//        }else
//            Helper.showToast(Create_Account_Activity.this,getString(R.string.no_internet_connection));
//

        boolean correct = true;
        if (aboutYouET.getText().toString().equalsIgnoreCase("")) {
            correct = false;
            Helper.showToast(About_You_Activity.this, "kindly tell us about you");
        } else if (educationET.getText().toString().equalsIgnoreCase("")) {
            correct = false;
            Helper.showToast(About_You_Activity.this, "kindly enter education");
        }
        if (correct) {
//
//            if(connectionDetector.isConnectingToInternet())
//        {
//            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
//
//            progress = dialogUtil.showProgressDialog(About_You_Activity.this, getString(R.string.please_wait));
//
//
//
//            MultipartTypedOutput multipartTypedOutput = new MultipartTypedOutput();
//            multipartTypedOutput.addPart("name", new TypedString(username));
//            multipartTypedOutput.addPart("dob", new TypedString(date));
//            multipartTypedOutput.addPart("country", new TypedString(selectCountry));
//            multipartTypedOutput.addPart("gender", new TypedString(gender));
//            multipartTypedOutput.addPart("ethnicity", new TypedString(ethenicity));
//            multipartTypedOutput.addPart("email", new TypedString(email));
//            multipartTypedOutput.addPart("password", new TypedString(password));
//            multipartTypedOutput.addPart("confirm_password", new TypedString(confirmPassword));
//            multipartTypedOutput.addPart("interests", new TypedString(InterestIdList));
//
//            try {
//                if (ifimg1) {
//                    File f = new File(context.getCacheDir(), "temp.jpg");
//                    f.createNewFile();
//
//                    Bitmap one = ((BitmapDrawable) my_account_img.getDrawable()).getBitmap();
////Convert bitmap to byte array
//                    Bitmap bitmap = one;
//                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
//                    bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
//                    byte[] bitmapdata = bos.toByteArray();
//
////write the bytes in file
//                    FileOutputStream fos = new FileOutputStream(f);
//                    fos.write(bitmapdata);
//                    fos.flush();
//                    fos.close();
//                    File resizedImage = new Resizer(context)
//                            .setTargetLength(512)
//                            .setQuality(80)
//                            .setOutputFormat("JPEG")
//                            .setOutputFilename("resized_image1")
//                            .setSourceImage(f)
//                            .getResizedFile();
//                    multipartTypedOutput.addPart("image", new TypedFile("image/jpg", resizedImage));
//                }
//
//
//            } catch (Exception e) {
//                Log.e("Error", e.toString());
//            }
//
//            multipartTypedOutput.addPart("aboutyou", new TypedString(aboutYouET.getText().toString()));
//            multipartTypedOutput.addPart("education", new TypedString(educationET.getText().toString()));
//
//
//
//
//
//            appController.paServices.TherapistRegister(multipartTypedOutput,new Callback<TherapistRegisterDM>() {
//
//                @Override
//
//                public void success ( TherapistRegisterDM therapistRegisterDM, Response response ) {
//                    progress.dismiss();
//                    if (therapistRegisterDM.getStatus().equalsIgnoreCase("1")) {
//
//
//
//
//                            user.setId(Integer.valueOf(therapistRegisterDM.getDetails().get(0).getId()));
//
//
//                            startActivity(new Intent(About_You_Activity.this, ThankYouActivity.class));
//                            overridePendingTransition(R.anim.fade_in, R.anim.fade_in);
//
//                        } else
//                            Helper.showToast(About_You_Activity.this, "registration failed");
//                    }
//
//                    @Override
//                    public void failure ( RetrofitError retrofitError ) {
//                        progress.dismiss();
//                        Log.e("error", retrofitError.toString());
//
//                    }
//                });
//
//        }else
//            Helper.showToast(About_You_Activity.this,getString(R.string.no_internet_connection));
//
//
//
        }


//        startActivity(new Intent(About_You_Activity.this, ThankYouActivity.class));
//        overridePendingTransition(R.anim.fade_in, R.anim.fade_in);
    }

    boolean ifimg1 = false;

    @OnClick(R.id.addImgRL)
    public void clickAddImgRL() {
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
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(About_You_Activity.this.getContentResolver(), uri);

                    profileCircleImg.setVisibility(View.VISIBLE);
                   profileCircleImg.setImageBitmap(bitmap);
//                    ifimg1 = true;
//                    EditProfileImageAPI();

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
        ImagePickerActivity.showImagePickerOptions(About_You_Activity.this, new ImagePickerActivity.PickerOptionListener() {
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
        Intent intent = new Intent(About_You_Activity.this, ImagePickerActivity.class);
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
        Intent intent = new Intent(About_You_Activity.this, ImagePickerActivity.class);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(About_You_Activity.this);
        builder.setTitle(getString(R.string.dialog_permission_title));
        builder.setMessage(getString(R.string.dialog_permission_message));
        builder.setPositiveButton(getString(R.string.go_to_settings), (dialog, which) -> {
            dialog.cancel();
//            openSettings();
        });
        builder.setNegativeButton(getString(android.R.string.cancel), (dialog, which) -> dialog.cancel());
        builder.show();

    }

    @Override
    public void onBackPressed() {
        overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_in);
        super.onBackPressed();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_in);
    }
}