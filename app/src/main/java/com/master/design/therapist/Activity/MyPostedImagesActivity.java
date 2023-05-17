package com.master.design.therapist.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.master.design.therapist.Adapter.Adapter_Posted_Image;
import com.master.design.therapist.Controller.AppController;
import com.master.design.therapist.DataModel.GetAll_Image.GetAllImageRoot;
import com.master.design.therapist.DataModel.RemoveImageRoot;
import com.master.design.therapist.DataModel.UnfriendDM;
import com.master.design.therapist.Fragments.Fragment_Friends_Request;
import com.master.design.therapist.Helper.Helper;
import com.master.design.therapist.Helper.User;
import com.master.design.therapist.R;
import com.master.design.therapist.Utils.ConnectionDetector;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MyPostedImagesActivity extends AppCompatActivity {

    private Context context;
    private Activity activity;
    AppController appController;
    ConnectionDetector connectionDetector;
    ProgressDialog progressDialog;
    User user;

    @BindView(R.id.recycleView)
    RecyclerView recycleView;

    @BindView(R.id.backImg)
    ImageView backImg;

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
        progressDialog = new ProgressDialog(MyPostedImagesActivity.this);
        progressDialog.setMessage(getResources().getString(R.string.please_wait));
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);

        getallPostedImage();

    }

    @OnClick(R.id.backImg)
    public void clickBack() {
        finish();
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

    private void deleteImageAPI(String id) {
        if (connectionDetector.isConnectingToInternet()) {
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
//            progress = dialogUtil.showProgressDialog(context, context.getString(R.string.please_wait));
            appController.paServices.RemoveImage(String.valueOf(user.getId()), id, new Callback<RemoveImageRoot>() {
                @Override
                public void success(RemoveImageRoot removeImageRoot, Response response) {
                    if (removeImageRoot.getStatus().equalsIgnoreCase("1")) {

                        getallPostedImage();
                    }
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    Log.e("error", retrofitError.toString());
                }
            });
        } else {
            Helper.showToast(context, String.valueOf(R.string.no_internet_connection));
        }
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