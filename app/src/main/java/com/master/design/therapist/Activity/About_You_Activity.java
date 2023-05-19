package com.master.design.therapist.Activity;

import static com.master.design.therapist.Activity.MyPostedImagesActivity.ADD_MORE_IMAGE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.makeramen.roundedimageview.RoundedImageView;
import com.master.design.therapist.Adapter.AddImageAdapter;
import com.master.design.therapist.Adapter.Education_details;
import com.master.design.therapist.Adapter.TherapistEducationDM;
import com.master.design.therapist.Controller.AppController;
import com.master.design.therapist.DataModel.AddMultipleImageRoot;
import com.master.design.therapist.DataModel.Ethnic_details;
import com.master.design.therapist.DataModel.TherapistAgeDM;
import com.master.design.therapist.DataModel.TherapistEthnicDM;
import com.master.design.therapist.DataModel.TherapistRegisterDM;
import com.master.design.therapist.Helper.BottomForAll;
import com.master.design.therapist.Helper.DataChangeDM;
import com.master.design.therapist.Helper.DialogUtil;
import com.master.design.therapist.Helper.Helper;
import com.master.design.therapist.Helper.ResponseListener;
import com.master.design.therapist.Helper.User;
import com.master.design.therapist.R;
import com.master.design.therapist.Utils.ConnectionDetector;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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
    String multipartTypedOutput;
    ArrayList<Uri> list;
    String colum[] = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};
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
    String name, id, age;

    @BindView(R.id.aboutYouET)
    EditText aboutYouET;

    @BindView(R.id.educationET)
    TextView educationET;

    @BindView(R.id.profileCircleImg)
    RoundedImageView profileCircleImg;

    @BindView(R.id.multipleImageRcv)
    RecyclerView multipleImageRcv;

    @BindView(R.id.addMultipleImgRL)
    RelativeLayout addMultipleImgRL;


    ArrayList<DataChangeDM> arrayList = new ArrayList();
    BottomForAll bottomForAll;

    @OnClick(R.id.educationET)
    public void educationET() {
        bottomForAll = new BottomForAll();
        bottomForAll.arrayList = arrayList;

        bottomForAll.setResponseListener(new ResponseListener() {
            @Override
            public void response(Object object) {

                name = ((DataChangeDM) object).getName();
                id = ((DataChangeDM) object).getId();
//                                    user.setAreaId(AreaID);
                educationET.setText(name);
            }
        });
        bottomForAll.show(About_You_Activity.this.getSupportFragmentManager(), "bottomSheetCountry");
    }


    @OnClick(R.id.addMultipleImgRL)
    public void clickaddMultipleImgRL() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.add_multiple_images)), ADD_MORE_IMAGE);
    }

    AddImageAdapter adaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_you);
        ButterKnife.bind(this);
        appController = (AppController) getApplicationContext();
        connectionDetector = new ConnectionDetector(About_You_Activity.this);
        user = new User(About_You_Activity.this);
        dialogUtil = new DialogUtil();
        context = this.getApplicationContext();
        activity = this;
        if ((ActivityCompat.checkSelfPermission(
                this, colum[0]) != PackageManager.PERMISSION_GRANTED) &&
                (ActivityCompat.checkSelfPermission(
                        this, colum[1]) != PackageManager.PERMISSION_GRANTED)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(colum, ADD_MORE_IMAGE);
            }
        }
        username = getIntent().getStringExtra("userName");
        date = getIntent().getStringExtra("date");
        selectCountry = getIntent().getStringExtra("selectCountry");
        gender = getIntent().getStringExtra("gender");
        ethenicity = getIntent().getStringExtra("ethnicity");
        mobilenumber = getIntent().getStringExtra("mobileNumber");
        email = getIntent().getStringExtra("email");
        password = getIntent().getStringExtra("password");
        confirmPassword = getIntent().getStringExtra("confirmPassword");
        InterestIdList = getIntent().getStringExtra("InterestIdlist");
        age = getIntent().getStringExtra("age");

        BindingEducation();
        list = new ArrayList<>();
        adaptor = new AddImageAdapter(list, "1");
        multipleImageRcv.setLayoutManager(new LinearLayoutManager(About_You_Activity.this, LinearLayoutManager.HORIZONTAL, false));
        multipleImageRcv.setAdapter(adaptor);

    }


    @OnClick(R.id.backImg)
    public void clickBack() {
        finish();
    }


    @SuppressLint("SuspiciousIndentation")
    @OnClick(R.id.signUpTxt)
    public void clicksignUpTxt() {

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
            Helper.showToast(About_You_Activity.this, getString(R.string.kindly_tell_me_about_u));
        } else if (educationET.getText().toString().equalsIgnoreCase("")) {
            correct = false;
            Helper.showToast(About_You_Activity.this, getString(R.string.kindly_select_education));
        }
        if (correct) {

            if (connectionDetector.isConnectingToInternet()) {
                String refreshedToken = FirebaseInstanceId.getInstance().getToken();

                progress = dialogUtil.showProgressDialog(About_You_Activity.this, getString(R.string.please_wait));


                MultipartTypedOutput multipartTypedOutput = new MultipartTypedOutput();
                multipartTypedOutput.addPart("name", new TypedString(username));
                multipartTypedOutput.addPart("dob", new TypedString(date));
                multipartTypedOutput.addPart("country", new TypedString(selectCountry));
                multipartTypedOutput.addPart("gender", new TypedString(gender));
                multipartTypedOutput.addPart("ethnicity", new TypedString(ethenicity));
                multipartTypedOutput.addPart("email", new TypedString(email));
                multipartTypedOutput.addPart("password", new TypedString(password));
                multipartTypedOutput.addPart("confirm_password", new TypedString(confirmPassword));
                multipartTypedOutput.addPart("interests", new TypedString(InterestIdList));
                multipartTypedOutput.addPart("device_type", new TypedString("2"));
                multipartTypedOutput.addPart("device_token", new TypedString(refreshedToken));
                multipartTypedOutput.addPart("age", new TypedString(age));

//            try
//            {
//                if (ifimg1) {
//                    File f = new File(context.getCacheDir(), "temp.png");
//                    f.createNewFile();
//
//                    Bitmap one = ((BitmapDrawable) profileCircleImg.getDrawable()).getBitmap();
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
                try {
                    // You can update this bitmap to your server

//                Bitmap bitmapMainImg = MediaStore.Images.Media.getBitmap(About_You_Activity.this.getContentResolver(), Uri.parse(String.valueOf(profileCircleImg.getDrawable())));
                    Bitmap bitmapMainImg = bitmap;

                    File f = new File(About_You_Activity.this.getCacheDir(), "temp.jpg");
                    f.createNewFile();

//                    Bitmap one = ((BitmapDrawable) profile_RoundedImgView.getDrawable()).getBitmap();
//Convert bitmap to byte array
                    Bitmap bitmap = bitmapMainImg;
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
                    byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
                    FileOutputStream fos = new FileOutputStream(f);
                    fos.write(bitmapdata);
                    fos.flush();
                    fos.close();
                    File resizedImage = new Resizer(About_You_Activity.this)
//                        .setTargetLength(200)
//                        .setQuality(100)
                            .setOutputFormat("JPEG")
                            .setOutputFilename("resized_image1")
                            .setSourceImage(f)
                            .getResizedFile();
                    multipartTypedOutput.addPart("image", new TypedFile("image/jpg", resizedImage));


                } catch (Exception e) {
                    Log.e("Error", e.toString());
                }

                multipartTypedOutput.addPart("aboutyou", new TypedString(aboutYouET.getText().toString()));
                multipartTypedOutput.addPart("education", new TypedString(id));
                multipartTypedOutput.addPart("phone", new TypedString(mobilenumber));


                appController.paServices.TherapistRegister(multipartTypedOutput, new Callback<TherapistRegisterDM>() {

                    @Override

                    public void success(TherapistRegisterDM therapistRegisterDM, Response response) {
                        progress.dismiss();
                        if (therapistRegisterDM.getStatus().equalsIgnoreCase("1")) {


                            user.setId(Integer.valueOf(therapistRegisterDM.getUser_id()));

                            startActivity(new Intent(About_You_Activity.this, ThankYouActivity.class));

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_in);
                            }
                            String userID = therapistRegisterDM.getUser_id();

                            if (list.size() > 0) {
                                addMultipleImageAPI(userID);
                            }

                        } else
                            Helper.showToast(About_You_Activity.this, therapistRegisterDM.getMsg());
                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {
                        progress.dismiss();
                        Log.e("error", retrofitError.toString());

                    }
                });

            } else
                Helper.showToast(About_You_Activity.this, getString(R.string.no_internet_connection));


        }


////        startActivity(new Intent(About_You_Activity.this, ThankYouActivity.class));
////        overridePendingTransition(R.anim.fade_in, R.anim.fade_in);
    }


    public void addMultipleImageAPI(String userID) {

        for (int i = 0; i < list.size(); i++) {
            if (connectionDetector.isConnectingToInternet()) {

                MultipartTypedOutput multipartTypedOutput = new MultipartTypedOutput();
                multipartTypedOutput.addPart("the_user", new TypedString(userID));

                try {
                    // You can update this bitmap to your server

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(About_You_Activity.this.getContentResolver(), list.get(i));
//                    Bitmap bitmapMainImg = bitmap;

                    File f = new File(About_You_Activity.this.getCacheDir(), "temp.jpg" + i);
                    f.createNewFile();

//                    Bitmap one = ((BitmapDrawable) profile_RoundedImgView.getDrawable()).getBitmap();
//Convert bitmap to byte array
//                Bitmap bitmap = bitmapMainImg;
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
                    byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
                    FileOutputStream fos = new FileOutputStream(f);
                    fos.write(bitmapdata);
                    fos.flush();
                    fos.close();
                    File resizedImage = new Resizer(About_You_Activity.this)
//                        .setTargetLength(200)
//                        .setQuality(100)
                            .setOutputFormat("JPEG")
                            .setOutputFilename("resized_image1" + i)
                            .setSourceImage(f)
                            .getResizedFile();
                    multipartTypedOutput.addPart("other_image[]", new TypedFile("image/jpg", resizedImage));


                } catch (Exception e) {
                    Log.e("Error", e.toString());
                }
                progress = dialogUtil.showProgressDialog(About_You_Activity.this, getString(R.string.please_wait));

                appController.paServices.Add_Multiple_Images(multipartTypedOutput, new Callback<AddMultipleImageRoot>() {
                    @Override
                    public void success(AddMultipleImageRoot addMultipleImageRoot, Response response) {
                        progress.dismiss();

                        if (addMultipleImageRoot.getStatus().equalsIgnoreCase("1")) {
                            progress.dismiss();

                        } else {

                            progress.dismiss();
                        }
                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {
                        progress.dismiss();

                        Log.e("error", retrofitError.toString());

                    }
                });
            } else {
                Helper.showToast(About_You_Activity.this, getString(R.string.no_internet_connection));
            }
        }


    }


    boolean ifimg1 = false;

    @OnClick(R.id.addImgRL)
    public void clickAddImgRL() {
        ifimg1 = true;
        OpenImage();
    }

    Bitmap bitmap;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getParcelableExtra("path");
                try {
                    // You can update this bitmap to your server
                    bitmap = MediaStore.Images.Media.getBitmap(About_You_Activity.this.getContentResolver(), uri);

                    profileCircleImg.setVisibility(View.VISIBLE);
                    profileCircleImg.setImageBitmap(bitmap);
                    ifimg1 = true;
//                    EditProfileImageAPI();

                    // loading profile image from local cache

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        if (requestCode == ADD_MORE_IMAGE && resultCode == RESULT_OK) {
            if (data.getClipData() != null && data.getClipData().getItemCount() > 1) {
                int x = data.getClipData().getItemCount();

                for (int i = 0; i < x; i++) {
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(About_You_Activity.this.getContentResolver(), data.getClipData().getItemAt(i).getUri());
                        list.add(data.getClipData().getItemAt(i).getUri());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (i > x) {

                    }
                }
                adaptor.notifyDataSetChanged();


            } else {
                showdialogNoData(context, getString(R.string.select_multiiple_images), getString(R.string.select_multiple_images_to_upload));
            }

        }


        super.onActivityResult(requestCode, resultCode, data);
    }

    public void showdialogNoData(Context context, String tittle, String msg) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setTitle(tittle)
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        final android.app.AlertDialog alert = builder.create();

        alert.show();
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
        }, false);
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
            activity.overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);
        }
        super.onBackPressed();
    }

    @Override
    public void finish() {
        super.finish();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
            activity.overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);
        }
    }

    public void BindingEducation() {
        if (connectionDetector.isConnectingToInternet()) {
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
            progress = dialogUtil.showProgressDialog(About_You_Activity.this, getString(R.string.please_wait));
            appController.paServices.TherapistEducation(new Callback<TherapistEducationDM>() {
                @Override
                public void success(TherapistEducationDM therapistEducationDM, Response response) {
                    progress.dismiss();
                    if (therapistEducationDM.getStatus().equalsIgnoreCase("1")) {

                        for (Education_details obj : therapistEducationDM.getEducation_details()) {
                            DataChangeDM s = new DataChangeDM();
                            s.setName(obj.getEducation());
                            s.setId(obj.getId());
                            arrayList.add(s);
                        }
                    } else
                        Helper.showToast(About_You_Activity.this, getString(R.string.Api_data_not_found));
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    Log.e("error", retrofitError.toString());
                }
            });
        } else
            Helper.showToast(About_You_Activity.this, getString(R.string.no_internet_connection));

    }
}