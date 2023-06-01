package com.master.design.therapist.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.makeramen.roundedimageview.RoundedImageView;
import com.master.design.therapist.Adapter.Adapter_Posted_Image;
import com.master.design.therapist.Controller.AppController;
import com.master.design.therapist.DataModel.AddMultipleImageRoot;
import com.master.design.therapist.DataModel.GetAll_Image.GetAllImageRoot;
import com.master.design.therapist.DataModel.MyProfile.Root;
import com.master.design.therapist.DataModel.ProfileDM;
import com.master.design.therapist.DataModel.RemoveImageRoot;
import com.master.design.therapist.DataModel.UnfriendDM;
import com.master.design.therapist.DataModel.Update_Pic_ProfileDM;
import com.master.design.therapist.Fragments.Fragment_Friends_Request;
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
import java.util.ArrayList;

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

public class MyPostedImagesActivity extends AppCompatActivity {

    private Context context;
    private Activity activity;
    AppController appController;
    ConnectionDetector connectionDetector;
    ProgressDialog progressDialog;
    User user;
    Dialog progress;
    DialogUtil dialogUtil;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;

    @BindView(R.id.backImg)
    ImageView backImg;

    @BindView(R.id.deleteMainImgBtn)
    ImageButton deleteMainImgBtn;

    @BindView(R.id.mainRoundedImg)
    RoundedImageView mainRoundedImg;
    @BindView(R.id.mainImgLL)
    LinearLayout mainImgLL;
    @BindView(R.id.otherImageLL)
    LinearLayout otherImageLL;
    @BindView(R.id.addMultipleImageButton)
    Button addMultipleImageButton;
    @BindView(R.id.otherImageTxt)
    TextView otherImageTxt;
    ArrayList<Uri> list;
    String colum[] = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_posted_images);
        ButterKnife.bind(this);

        context = MyPostedImagesActivity.this;
        activity = MyPostedImagesActivity.this;
        appController = (AppController) getApplicationContext();
        user = new User(context);
        connectionDetector = new ConnectionDetector(MyPostedImagesActivity.this);
        dialogUtil = new DialogUtil();

        progressDialog = new ProgressDialog(MyPostedImagesActivity.this);
        progressDialog.setMessage(getResources().getString(R.string.please_wait));
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        if ((ActivityCompat.checkSelfPermission(
                this, colum[0]) != PackageManager.PERMISSION_GRANTED) &&
                (ActivityCompat.checkSelfPermission(
                        this, colum[1]) != PackageManager.PERMISSION_GRANTED)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(colum, ADD_MORE_IMAGE);
            }
        }
        getallPostedImage();
        Binding();

    }

    @OnClick(R.id.backImg)
    public void clickBack() {
        finish();
    }

    String mainImgDeleteId;

    @OnClick(R.id.deleteMainImgBtn)
    public void clickdeleteMainImgBtn() {
        deletemMainImagedialog(context, getString(R.string.sure_you_want_to_delete_this_image), mainImgDeleteId);

    }

    private void getallPostedImage() {

        if (connectionDetector.isConnectingToInternet()) {
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
//            progress = dialogUtil.showProgressDialog(context, context.getString(R.string.please_wait));
            appController.paServices.ShowImages(String.valueOf(user.getId()), new Callback<GetAllImageRoot>() {
                @Override
                public void success(GetAllImageRoot getAllImageRoot, Response response) {
                    if (getAllImageRoot.getStatus().equalsIgnoreCase("1")) {

                        try {
                            otherImageTxt.setText(getString(R.string.other_images));
                            Adapter_Posted_Image adapter_posted_image = new Adapter_Posted_Image(context, getAllImageRoot.getAll_images());
                            GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2);
                            recycleView.setLayoutManager(gridLayoutManager);
                            recycleView.setAdapter(adapter_posted_image);

                            adapter_posted_image.setOnItemClickListener(new Adapter_Posted_Image.OnItemClickListener() {
                                @Override
                                public void deleteImage(int position, String id) {

                                    deleteImagedialog(context, getString(R.string.sure_you_want_to_delete_this_image), id);
                                }
                            });

                        } catch (Exception e) {
                            e.printStackTrace();
                            showdialogNoData(context, getString(R.string.my_posted_images), e.toString());
                        }

                    } else {
                        otherImageTxt.setText("");
                        showdialogNoData(context, getString(R.string.my_posted_images), getString(R.string.no_images));
                    }
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    showdialogNoData(context, getString(R.string.my_posted_images), retrofitError.toString());
                    Log.e("error", retrofitError.toString());
                }
            });
        } else {
            Helper.showToast(context, String.valueOf(R.string.no_internet_connection));
        }

    }

    public void showdialogNoData(Context context, String tittle, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(tittle)
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();

        alert.show();
    }

    public void deleteImagedialog(Context context, String tittle, String userid) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(tittle)
                .setCancelable(false)
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        deleteImageAPI(userid);

                    }


                });
        final AlertDialog alert = builder.create();

        alert.show();
    }


    public void deletemMainImagedialog(Context context, String tittle, String userid) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(tittle)
                .setCancelable(false)
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                        deleteMainImageAPI(userid);
                        mainImgDeleteId = "";

                    }


                });
        final AlertDialog alert = builder.create();

        alert.show();
    }


    private void deleteImageAPI(String id) {
        if (connectionDetector.isConnectingToInternet()) {
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
            progress = dialogUtil.showProgressDialog(context, context.getString(R.string.please_wait));
            appController.paServices.RemoveImage(String.valueOf(user.getId()), id, new Callback<RemoveImageRoot>() {
                @Override
                public void success(RemoveImageRoot removeImageRoot, Response response) {
                    if (removeImageRoot.getStatus().equalsIgnoreCase("1")) {
                        progress.dismiss();

                        getallPostedImage();
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
            Helper.showToast(context, String.valueOf(R.string.no_internet_connection));
        }
    }


    public void Binding() {
        if (connectionDetector.isConnectingToInternet()) {
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
            progress = dialogUtil.showProgressDialog(MyPostedImagesActivity.this, getString(R.string.please_wait));
            MultipartTypedOutput multipartTypedOutput = new MultipartTypedOutput();
            multipartTypedOutput.addPart("id", new TypedString(String.valueOf(user.getId())));
            appController.paServices.TherapistProfileNew(multipartTypedOutput, new Callback<Root>() {
                @Override
                public void success(Root profileDM, Response response) {
                    progress.dismiss();
                    if (profileDM.getStatus().equalsIgnoreCase("1")) {

                        if (profileDM.getUser_data().get(0).getImage() != null) {
                            mainImgDeleteId = String.valueOf(profileDM.getUser_data().get(0).getId());

                            progress.dismiss();

                            Picasso.with(context).load(AppController.THERAPIST_IMAGE + profileDM.getUser_data().get(0).getImage()).placeholder(R.drawable.black_transparent_gradient).into(mainRoundedImg);
                            mainImgLL.setVisibility(View.VISIBLE);
                        } else {
                            progress.dismiss();
                            mainImgLL.setVisibility(View.GONE);

                        }
                    } else {
                        progress.dismiss();
//                        Helper.showToast(MyPostedImagesActivity.this, getString(R.string.Api_data_not_found));
                        mainImgLL.setVisibility(View.GONE);
                    }

                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    progress.dismiss();
                    Log.e("error", retrofitError.toString());
                }
            });
        } else {
            Helper.showToast(MyPostedImagesActivity.this, getString(R.string.no_internet_connection));
        }

    }

    private void deleteMainImageAPI(String id) {
        if (connectionDetector.isConnectingToInternet()) {
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
            progress = dialogUtil.showProgressDialog(context, context.getString(R.string.please_wait));
            appController.paServices.Remove_Pic(id, new Callback<RemoveImageRoot>() {
                @Override
                public void success(RemoveImageRoot removeImageRoot, Response response) {
                    if (removeImageRoot.getStatus().equalsIgnoreCase("1")) {
                        progress.dismiss();
                        mainImgDeleteId = "";
                        mainImgLL.setVisibility(View.GONE);
                        Binding();
                    }
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    progress.dismiss();
                    Log.e("error", retrofitError.toString());
                }
            });
        } else {
            Helper.showToast(context, String.valueOf(R.string.no_internet_connection));
        }
    }

    public final static int ADD_MORE_IMAGE = 123;
    public final static int EDIT_DELETE_MORE_IMAGE = 456;
    public final static int ADD_MAIN_IMAGE = 789;

    @OnClick(R.id.addMultipleImageButton)
    public void addMultipleImage() {
        openGalley();
    }

    private void openGalley() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.add_multiple_images)), ADD_MORE_IMAGE);
    }

    Bitmap bitmap;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_MORE_IMAGE && resultCode == RESULT_OK) {
            if (data.getClipData() != null && data.getClipData().getItemCount() > 1) {
                int x = data.getClipData().getItemCount();

                for (int i = 0; i < x; i++) {
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(MyPostedImagesActivity.this.getContentResolver(), data.getClipData().getItemAt(i).getUri());
//                        list.add(data.getClipData().getItemAt(i).getUri());
                        addMultipleImageAPI(bitmap, i);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }


            }else{
                showdialogNoData(context, getString(R.string.select_multiiple_images),getString(R.string.select_multiple_images_to_upload));
            }

        }


    }


    public void addMultipleImageAPI(Bitmap bitmap, int i) {
        if (connectionDetector.isConnectingToInternet()) {

            MultipartTypedOutput multipartTypedOutput = new MultipartTypedOutput();
            multipartTypedOutput.addPart("the_user", new TypedString(String.valueOf(user.getId())));

            try {
                // You can update this bitmap to your server

//                Bitmap bitmapMainImg = MediaStore.Images.Media.getBitmap(About_You_Activity.this.getContentResolver(), Uri.parse(String.valueOf(profileCircleImg.getDrawable())));
                Bitmap bitmapMainImg = bitmap;

                File f = new File(MyPostedImagesActivity.this.getCacheDir(), "temp.jpg" + i);
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
                File resizedImage = new Resizer(MyPostedImagesActivity.this)
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
//            progress = dialogUtil.showProgressDialog(My_ProfileActivity.this, getString(R.string.please_wait));

            appController.paServices.Add_Multiple_Images(multipartTypedOutput, new Callback<AddMultipleImageRoot>() {
                @Override
                public void success(AddMultipleImageRoot addMultipleImageRoot, Response response) {
//                    progress.dismiss();

                    if (addMultipleImageRoot.getStatus().equalsIgnoreCase("1")) {
                        getallPostedImage();
                    } else {

                    }
                }

                @Override
                public void failure(RetrofitError retrofitError) {
//                    progress.dismiss();

                    Log.e("error", retrofitError.toString());

                }
            });
        } else
            Helper.showToast(MyPostedImagesActivity.this, getString(R.string.no_internet_connection));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        activity.overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);
    }

    @Override
    public void finish() {
        super.finish();
        activity.overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);
    }
}