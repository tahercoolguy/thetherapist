package com.master.design.therapist.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.master.design.therapist.Adapter.MessageChatAdapter;
import com.master.design.therapist.Controller.AppController;
import com.master.design.therapist.DM.MessageChatModel;
import com.master.design.therapist.DataModel.ChatHistoryDM;
import com.master.design.therapist.DataModel.TherapistInterestDM;
import com.master.design.therapist.Helper.DialogUtil;
import com.master.design.therapist.Helper.User;
import com.master.design.therapist.R;
import com.master.design.therapist.Utils.ConnectionDetector;
import com.master.design.therapist.Utils.Helper;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Conversation_Activity extends AppCompatActivity {

    private Context context;
    private Activity activity;
    @BindView(R.id.rcvRcv)
    RecyclerView rcvRcv;
    @BindView(R.id.profileCircleImg)
    CircleImageView profileCircleImg;
    @BindView(R.id.userNameTxt)
    TextView userNameTxt;
    @BindView(R.id.backImg)
    ImageView backImg;
    @BindView(R.id.notificationImg)
    ImageView notificationImg;
    @BindView(R.id.messageET)
    EditText messageET;
    MessageChatAdapter adapter;
    String name,image;

    AppController appController;

    Dialog progress;
    ConnectionDetector connectionDetector;
    User user;
    DialogUtil dialogUtil;
    String FriendsId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        ButterKnife.bind(this);
        context = getApplicationContext();
        activity = Conversation_Activity.this;
        dialogUtil = new DialogUtil();
        appController = (AppController) getApplicationContext();
        connectionDetector = new ConnectionDetector(getApplicationContext());
        user = new User(Conversation_Activity.this);
        LinearLayoutManager manager = new LinearLayoutManager(Conversation_Activity.this, RecyclerView.VERTICAL, false);
        rcvRcv.setLayoutManager(manager);
        setChatData();
        name=getIntent().getStringExtra("Name");
        image=getIntent().getStringExtra("image");
        FriendsId=getIntent().getStringExtra("FriendId");
        userNameTxt.setText(name);
        Picasso.with(context).load(image).into(profileCircleImg);
    }


    List<MessageChatModel> messageChatModelList = new ArrayList<>();

    private void setChatData() {
//        MessageChatModel model1 = new MessageChatModel(
//                "Hello. How are you today?",
//                "10:00 PM", 0, R.drawable.marshall_img
//
//        );
//        MessageChatModel model2 = new MessageChatModel(
//                "Hey! I'm fine. Thanks for asking!",
//                "10:00 PM",
//                1, R.drawable.marshall_img
//        );
//        MessageChatModel model3 = new MessageChatModel(
//                "Sweet! So, what do you wanna do today?",
//                "10:00 PM",
//                0, R.drawable.marshall_img
//        );
//        MessageChatModel model4 = new MessageChatModel(
//                "Nah, I dunno. Play soccer.. or learn more coding perhaps?",
//                "10:00 PM",
//                1, R.drawable.marshall_img
//        );
//
//
//        messageChatModelList.add(model1);
//        messageChatModelList.add(model2);
//        messageChatModelList.add(model3);
//        messageChatModelList.add(model4);
//        messageChatModelList.add(model1);
//        messageChatModelList.add(model2);
//        messageChatModelList.add(model3);
//        messageChatModelList.add(model4);
//        messageChatModelList.add(model1);
//        messageChatModelList.add(model2);
//        messageChatModelList.add(model3);
//        messageChatModelList.add(model4);



        if(connectionDetector.isConnectingToInternet())
        {

            String refreshedToken = FirebaseInstanceId.getInstance().getToken();

            progress = dialogUtil.showProgressDialog(Conversation_Activity.this, getString(R.string.please_wait));

            appController.paServices.TherapistChatHistory(String.valueOf(user.getId()),FriendsId, new Callback<ChatHistoryDM>() {

                @Override

                public void success ( ChatHistoryDM chatHistoryDM, Response response ) {
                    progress.dismiss();
                    if (chatHistoryDM.getStatus().equalsIgnoreCase("1")) {


        rcvRcv.smoothScrollToPosition(messageChatModelList.size());
        adapter = new MessageChatAdapter(chatHistoryDM.getAll_messages(), context,FriendsId);
        rcvRcv.setAdapter(adapter);

                    } else
                        Helper.showToast(Conversation_Activity.this, chatHistoryDM.getMsg());
                }

                @Override
                public void failure ( RetrofitError retrofitError ) {
                    progress.dismiss();

                    Log.e("error", retrofitError.toString());

                }
            });

        }else
            Helper.showToast(Conversation_Activity.this,getString(R.string.no_internet_connection));



    }

    @OnClick(R.id.sendImg)
    public void setsendImg() {
        String msg = messageET.getText().toString();
        if (!msg.equalsIgnoreCase("")){
            MessageChatModel model = new MessageChatModel(
                    msg,
                    "10:00 PM",
                    0, R.drawable.marshall_img
            );
            messageChatModelList.add(model);
            rcvRcv.smoothScrollToPosition(messageChatModelList.size());
            adapter.notifyDataSetChanged();
            messageET.setText("");
        }


        if (imageFromgallery != null) {
            MessageChatModel imagemodel = new MessageChatModel(
                    msg,
                    "10:00 PM",
                    2, R.drawable.marshall_img);
            messageChatModelList.add(imagemodel);
            rcvRcv.smoothScrollToPosition(messageChatModelList.size());
            adapter.notifyDataSetChanged();
            messageET.setText("");
        }
        ;

    }

    @OnClick(R.id.backImg)
    public void clickBack() {
        finish();
    }


    @OnClick(R.id.addImg)
    public void clickAddImg() {
        OpenImage();
    }

    Bitmap bitmap;
    Drawable imageFromgallery;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getParcelableExtra("path");
                try {
                    // You can update this bitmap to your server
                    bitmap = MediaStore.Images.Media.getBitmap(Conversation_Activity.this.getContentResolver(), uri);

//               /////////////////////////////////////////////////////////////////////////     profileImgRIV.setImageBitmap(bitmap);

                    imageFromgallery = new BitmapDrawable(getResources(), bitmap);

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
        ImagePickerActivity.showImagePickerOptions(Conversation_Activity.this, new ImagePickerActivity.PickerOptionListener() {
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
        Intent intent = new Intent(Conversation_Activity.this, ImagePickerActivity.class);
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
        Intent intent = new Intent(Conversation_Activity.this, ImagePickerActivity.class);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(Conversation_Activity.this);
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