package com.master.design.therapist.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.JsonObject;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.master.design.therapist.Adapter.MessageChatAdapter;
import com.master.design.therapist.Controller.AppController;
import com.master.design.therapist.Adapter.DataModel.All_messages;
import com.master.design.therapist.Adapter.DataModel.ChatHistoryDM;
import com.master.design.therapist.Adapter.DataModel.SendingImageDM;
import com.master.design.therapist.Adapter.DataModel.TokenRoot;
import com.master.design.therapist.Helper.DialogUtil;
import com.master.design.therapist.Helper.User;
import com.master.design.therapist.R;
import com.master.design.therapist.Utils.ConnectionDetector;
import com.master.design.therapist.Utils.Helper;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import dev.gustavoavila.websocketclient.WebSocketClient;
import me.echodev.resizer.Resizer;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.MultipartTypedOutput;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedString;

public class Conversation_Activity extends AppCompatActivity {

//    private String SERVER_PATH = "";
//    private WebSocket webSocket;

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
    @BindView(R.id.messageRL)
    RelativeLayout messageRL;

    @BindView(R.id.addRL)
    RelativeLayout addRL;

    @BindView(R.id.textRL)
    RelativeLayout textRL;
    public MessageChatAdapter adapter;
    String name, image;
    LinearLayoutManager lm;

    AppController appController;

    Dialog progress;
    ConnectionDetector connectionDetector;
    User user;
    DialogUtil dialogUtil;
    String FriendsId, chatRoomID;
    String user_id;
    String message, Id, status, messageId, timestamp, type, image_url = "";
    String statusCheck = "send";
    @BindView(R.id.sendImg)
    ImageView sendImg;
    @BindView(R.id.addImg)
    ImageView addImg;

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

        name = getIntent().getStringExtra("Name");
        image = getIntent().getStringExtra("image");
        FriendsId = getIntent().getStringExtra("FriendId");
        chatRoomID = getIntent().getStringExtra("chatRoomID");
        userNameTxt.setText(name);
        Picasso.with(context).load(image).into(profileCircleImg);

        setChatData();
        updateOnline();
        createWebSocketClient();

        messageRL.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                rcvRcv.scrollToPosition(adapter.getItemCount());

                return false;
            }
        });

        setListeners();

        ActivityCompat.requestPermissions(Conversation_Activity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                1);
    }


    List<All_messages> messageChatModelList = new ArrayList<>();
    List<All_messages> refreshmessageChatModelList = new ArrayList<>();

    private void setChatData() {
         if (connectionDetector.isConnectingToInternet()) {

            String refreshedToken = FirebaseInstanceId.getInstance().getToken();

            progress = dialogUtil.showProgressDialog(Conversation_Activity.this, getString(R.string.please_wait));

            user_id = String.valueOf(user.getId());
            MultipartTypedOutput multipartTypedOutput = new MultipartTypedOutput();
            multipartTypedOutput.addPart("user_1", new TypedString(String.valueOf(user.getId())));
            multipartTypedOutput.addPart("user_2", new TypedString(FriendsId));
            multipartTypedOutput.addPart("room_id", new TypedString(chatRoomID));
            multipartTypedOutput.addPart("user_id", new TypedString(String.valueOf(user.getId())));

            appController.paServices.TherapistChatHistory(multipartTypedOutput, new Callback<ChatHistoryDM>() {
                @Override
                public void success(ChatHistoryDM chatHistoryDM, Response response) {
                    progress.dismiss();
                    if (chatHistoryDM.getStatus().equalsIgnoreCase("1")) {

                        messageChatModelList = chatHistoryDM.getAll_messages();

                        if(chatHistoryDM.getFriends()==true)
                        {
                            messageRL.setVisibility(View.VISIBLE);
                            addRL.setVisibility(View.VISIBLE);
                            textRL.setVisibility(View.GONE);
                        }
                        else {
                            messageRL.setVisibility(View.GONE);
                            addRL.setVisibility(View.GONE);
                            textRL.setVisibility(View.VISIBLE);
                        }


                        adapter = new MessageChatAdapter(messageChatModelList, context, FriendsId, status);
                        lm = new LinearLayoutManager(Conversation_Activity.this, LinearLayoutManager.VERTICAL, false);

                        rcvRcv.setAdapter(adapter);
                        rcvRcv.setLayoutManager(lm);
                        rcvRcv.smoothScrollToPosition(messageChatModelList.size());
                        lm.setReverseLayout(false);
                        lm.setStackFromEnd(true);
                        rcvRcv.scrollToPosition(messageChatModelList.size() - 1);
                        rcvRcv.scrollToPosition(adapter.getItemCount());
                        setListeners();

                        adapter.setOnitemClickListener(new MessageChatAdapter.Onitemclicklistener() {
                            @Override
                            public void openImage(String message) {

                                startActivity(new Intent(Conversation_Activity.this, ViewImageActivity.class).putExtra("img", message));

                            }
                        });


                    } else {
                        Helper.showToast(Conversation_Activity.this, chatHistoryDM.getMsg());
                    }
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    progress.dismiss();

                    Log.e("error", retrofitError.toString());

                }
            });

        } else
            Helper.showToast(Conversation_Activity.this, getString(R.string.no_internet_connection));


    }

    boolean sendBoolean = false;

    @OnClick(R.id.sendImg)
    public void setsendImg() {
        String msg = messageET.getText().toString();
        if (!msg.equalsIgnoreCase("")) {
//            MessageChatModel model = new MessageChatModel(
//                    msg,
//                    "10:00 PM",
//                    0, R.drawable.marshall_img
//
//            );
//            setChatData();
            // First, create a new JsonObject
            JsonObject jsonObject = new JsonObject();

// Add properties to the JsonObject
            jsonObject.addProperty("type", "chat_message");
            jsonObject.addProperty("message", msg);
            jsonObject.addProperty("status", "send");
            jsonObject.addProperty("sender_id", user_id);
//            jsonObject.addProperty("sender_id", user_id);


            String ne = "{\"type\":\"chat_message\",\"message\":\"" + msg + "\",\"status\":\"send\",\"sender_id\":" + user_id + "}";
            webSocketClient.send(ne);
            messageET.setText("");
            setListeners();
            sendBoolean = true;
        }


//        if (imageFromgallery != null) {
//            MessageChatModel imagemodel = new MessageChatModel(
//                    msg,
//                    "10:00 PM",
//                    2, R.drawable.marshall_img);
//            messageChatModelList.add(imagemodel);
//            rcvRcv.smoothScrollToPosition(messageChatModelList.size());
//            adapter.notifyDataSetChanged();
//            messageET.setText("");
//        }
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

//                   profileImgRIV.setImageBitmap(bitmap);

                    imageFromgallery = new BitmapDrawable(getResources(), bitmap);

                    sendingImageChat(bitmap);
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
        activity.overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);
        webSocketClient.close(1,1,"");
        super.onBackPressed();
    }

    @Override
    public void finish() {
        super.finish();
        activity.overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);
    }


    private WebSocketClient webSocketClient;

    public void createWebSocketClient() {
        URI uri;
         try {
            uri = new URI("ws://"+ AppController.WebSocketURL+"/ws/chat/" + chatRoomID + "/");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        webSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen() {
                System.out.println("onOpen");
//                webSocketClient.send("Hello, World!");
//                Helper.showToast(Conversation_Activity.this,message);
            }

            @Override
            public void onTextReceived(String msg) {
                System.out.println("onTextReceived");
                JSONObject obj = null;
                try {
                    obj = new JSONObject(msg);
                    Log.d("My App", obj.toString());
//                    Log.d("phonetype value ", obj.getString("phonetype"));
                    status = obj.getString("status");
                    if (status.equalsIgnoreCase("send")) {
                        Id = obj.getString("sender_id");
                        status = obj.getString("status");
                        messageId = obj.getString("message_id");
                        timestamp = obj.getString("timestamp");
                        type = obj.getString("type");

                        if (type.equalsIgnoreCase("chat_message"))
                            message = obj.getString("message");
                        else {
                            message = "";
                            image_url = obj.getString("image_url");
                        }
                        if (Id.equalsIgnoreCase(user_id)) {

                            messageId = obj.getString("message_id");
//                            All_messages model = new All_messages();
//                            model.setStatus(status);
//                            messageChatModelList.add(model);

                            JsonObject jsonObject = new JsonObject();

// Add properties to the JsonObject
                            jsonObject.addProperty("type", "delivered_message");
                            jsonObject.addProperty("message_id", messageId);
                            jsonObject.addProperty("status", "delivered");
                            jsonObject.addProperty("sender_id", user_id);
//            jsonObject.addProperty("sender_id", user_id);


                            All_messages model = new All_messages();
                            model.setMessage(message);
                            model.setReceiver_user_id(FriendsId);
                            model.setSender_user_id(user_id);
                            model.setStatus(status);
                            model.setTimestamp(timestamp);
                            model.setType(type);
                            model.setId(messageId);
                            if (type.equalsIgnoreCase("chat_image")) {
                                model.setImage_url(image_url);
                            } else {

                            }
                            messageChatModelList.add(model);

                            rcvRcv.smoothScrollToPosition(messageChatModelList.size());
//                              messageET.setText("");
                            adapter.notifyDataSetChanged();
                            rcvRcv.scrollToPosition(messageChatModelList.size() - 1);
                            rcvRcv.scrollToPosition(adapter.getItemCount());
                            setListeners();
                            adapter.setOnitemClickListener(new MessageChatAdapter.Onitemclicklistener() {
                                @Override
                                public void openImage(String message) {

                                    startActivity(new Intent(Conversation_Activity.this, ViewImageActivity.class).putExtra("img", message));

                                }
                            });
                            if (sendBoolean) {
//                                sendPlayRingtone();
                            } else {
                                sendBoolean = false;
                            }

                        } else {

                            All_messages model = new All_messages();
                            model.setMessage(message);
                            model.setReceiver_user_id(user_id);
                            model.setSender_user_id(Id);
                            model.setStatus(status);
                            model.setTimestamp(timestamp);
                            model.setType(type);

                            if (type.equalsIgnoreCase("chat_image")) {
                                model.setImage_url(image_url);

                            } else {

                            }
                            messageChatModelList.add(model);

                            if (type.equalsIgnoreCase("chat_message")) {
                                String ne = "{\"type\":\"chat_message\",\"message_id\":\"" + messageId + "\",\"status\":\"delivered\",\"sender_id\":" + user_id + "}";
                                webSocketClient.send(ne);
                            } else {
                                String ne = "{\"type\":\"chat_image\",\"message_id\":\"" + messageId + "\",\"status\":\"delivered\",\"sender_id\":" + user_id + "}";
                                webSocketClient.send(ne);
                            }

                            rcvRcv.smoothScrollToPosition(messageChatModelList.size());
//                              messageET.setText("");
                            adapter.notifyDataSetChanged();
                            rcvRcv.scrollToPosition(messageChatModelList.size() - 1);
                            rcvRcv.scrollToPosition(adapter.getItemCount());
                            setListeners();
                            adapter.setOnitemClickListener(new MessageChatAdapter.Onitemclicklistener() {
                                @Override
                                public void openImage(String message) {

                                    startActivity(new Intent(Conversation_Activity.this, ViewImageActivity.class).putExtra("img", message));

                                }
                            });
                            readPlayRingtone();
                        }
                    } else {
                        status = obj.getString("status");
                        messageId = obj.getString("message_id");
                        int i = 0;
                        for (All_messages message : messageChatModelList
                        ) {
//                            if (Integer.parseInt(message.getId()) == Integer.parseInt(messageId)) {
                                messageChatModelList.get(i).setStatus("delivered");
//                            }
                            i++;
                        }
                        adapter.notifyDataSetChanged();

//                        rcvRcv.smoothScrollToPosition(messageChatModelList.size());
//                        lm.setReverseLayout(false);
//                        lm.setStackFromEnd(true);
//                        rcvRcv.scrollToPosition(messageChatModelList.size() - 1);
//                        rcvRcv.scrollToPosition(adapter.getItemCount());
//                        adapter.notifyDataSetChanged();
//                        sendPlayRingtone();
                        setListeners();

                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                }


//                JSONObject foo = msg;
//                String id = foo.get("CNo").isString().toString();
//                String message= foo.get("CName").isString().toString();
//               Helper.showToast(Conversation_Activity.this,msg);

            }


            @Override
            public void onBinaryReceived(byte[] data) {

                System.out.println("onBinaryReceived");
            }

            @Override
            public void onPingReceived(byte[] data) {
                System.out.println("onPingReceived");
            }

            @Override
            public void onPongReceived(byte[] data) {
                System.out.println("onPongReceived");
            }

            @Override
            public void onException(Exception e) {
                System.out.println(e.getMessage());
//                rcvRcv.smoothScrollToPosition(messageChatModelList.size());
//                adapter.notifyDataSetChanged();
//                rcvRcv.scrollToPosition(messageChatModelList.size());
                createWebSocketClient();
            }

            @Override
            public void onCloseReceived(int reason, String description) {
                System.out.println("onCloseReceived" + description);
                Helper.showToast(Conversation_Activity.this, description);
            }

            @Override
            public void sendPong(byte[] data) {
                super.sendPong(data);
            }
        };

        webSocketClient.setConnectTimeout(10000);
        webSocketClient.setReadTimeout(60000);
//        webSocketClient.addHeader("Origin", "http://developer.example.com");
        webSocketClient.enableAutomaticReconnection(5000);
        webSocketClient.connect();
    }

    public void setListeners() {

        rcvRcv.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom,
                                       int oldLeft, int oldTop, int oldRight, int oldBottom) {

                if (messageChatModelList.size() == 0) {
                    rcvRcv.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            rcvRcv.smoothScrollToPosition(messageChatModelList.size());
                        }
                    }, 100);
                } else {
                    rcvRcv.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            rcvRcv.smoothScrollToPosition(messageChatModelList.size() + 1);
                        }
                    }, 100);
                }
            }
        });
        if (messageChatModelList.size() == 0) {
            rcvRcv.postDelayed(new Runnable() {
                @Override
                public void run() {
                    rcvRcv.smoothScrollToPosition(messageChatModelList.size());
                }
            }, 100);
        } else {
            rcvRcv.postDelayed(new Runnable() {
                @Override
                public void run() {
                    rcvRcv.smoothScrollToPosition(messageChatModelList.size() + 1);
                }
            }, 100);
        }
    }


    public void sendingImageChat(Bitmap bitmapImg) {
        if (connectionDetector.isConnectingToInternet()) {

            MultipartTypedOutput multipartTypedOutput = new MultipartTypedOutput();
            multipartTypedOutput.addPart("image_sender", new TypedString(String.valueOf(user.getId())));
            multipartTypedOutput.addPart("image_receiver", new TypedString(FriendsId));


            try {
                // You can update this bitmap to your server

//                Bitmap bitmapMainImg = MediaStore.Images.Media.getBitmap(About_You_Activity.this.getContentResolver(), Uri.parse(String.valueOf(profileCircleImg.getDrawable())));
                Bitmap bitmapMainImg = bitmapImg;

                File f = new File(Conversation_Activity.this.getCacheDir(), "temp.jpg");
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
                File resizedImage = new Resizer(Conversation_Activity.this)
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


            progress = dialogUtil.showProgressDialog(Conversation_Activity.this, getString(R.string.please_wait));

            appController.paServices.SendingImageChat(multipartTypedOutput, new Callback<SendingImageDM>() {
                @Override
                public void success(SendingImageDM sendingImageDM, Response response) {

                    if (sendingImageDM.getStatus().equalsIgnoreCase("1")) {
                        progress.dismiss();
                        String imgLink = sendingImageDM.getImage();
                        if (!imgLink.equalsIgnoreCase("")) {
//            MessageChatModel model = new MessageChatModel(
//                    msg,
//                    "10:00 PM",
//                    0, R.drawable.marshall_img
//
//            );
//            setChatData();
                            // First, create a new JsonObject
                            JsonObject jsonObject = new JsonObject();

// Add properties to the JsonObject
                            jsonObject.addProperty("type", "chat_message");
                            jsonObject.addProperty("message", imgLink);
                            jsonObject.addProperty("status", "send");
                            jsonObject.addProperty("sender_id", user_id);
//            jsonObject.addProperty("sender_id", user_id);


                            String ne = "{\"type\":\"chat_image\",\"image_url\":\"" + imgLink + "\",\"status\":\"send\",\"sender_id\":" + user_id + "}";
                            webSocketClient.send(ne);
                            messageET.setText("");
                            rcvRcv.smoothScrollToPosition(messageChatModelList.size());
//                              messageET.setText("");
                            adapter.notifyDataSetChanged();
                            rcvRcv.scrollToPosition(messageChatModelList.size() - 1);
                            rcvRcv.scrollToPosition(adapter.getItemCount());
                            setListeners();
                            adapter.setOnitemClickListener(new MessageChatAdapter.Onitemclicklistener() {
                                @Override
                                public void openImage(String message) {

                                    startActivity(new Intent(Conversation_Activity.this, ViewImageActivity.class).putExtra("img", message));

                                }
                            });
                            setListeners();
                        }

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
            com.master.design.therapist.Helper.Helper.showToast(Conversation_Activity.this, getString(R.string.no_internet_connection));
        }
    }

    public void sendPlayRingtone() {
        try {
//            Uri path = Uri.parse("android.resource://" + getPackageName() + "/raw/read_messege_sound.mp3");
            Uri path = Uri.parse("android.resource://"
                    + context.getPackageName() + "/"
                    + R.raw.read_messege_sound);
//            RingtoneManager.setActualDefaultRingtoneUri(
//                    getApplicationContext(), RingtoneManager.TYPE_RINGTONE, path
//            );
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), path);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readPlayRingtone() {
        try {
//            Uri path = Uri.parse("android.resource://" + getPackageName() + "/raw/recieve_message_sound.mp3");
            Uri path = Uri.parse("android.resource://"
                    + context.getPackageName() + "/"
                    + R.raw.recieve_message_sound);
//            RingtoneManager.setActualDefaultRingtoneUri(
//                    getApplicationContext(), RingtoneManager.TYPE_RINGTONE, path
//            );
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), path);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(Conversation_Activity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void updateOnline() {
        if (connectionDetector.isConnectingToInternet()) {
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
            MultipartTypedOutput multipartTypedOutput = new MultipartTypedOutput();
            multipartTypedOutput.addPart("id", new TypedString(String.valueOf(user.getId())));

//            progress = dialogUtil.showProgressDialog(context, context.getString(R.string.please_wait));
            appController.paServices.Online(multipartTypedOutput, new Callback<TokenRoot>() {
                @Override
                public void success(TokenRoot tokenRoot, Response response) {
                    if (tokenRoot.getStatus().equalsIgnoreCase("1")) {
//                        progress.dismiss();

                    }else{
//                        progress.dismiss();
                    }
                }

                @Override
                public void failure(RetrofitError retrofitError) {
//                    progress.dismiss();
                    Log.e("error", retrofitError.toString());
                }
            });
        } else {
            com.master.design.therapist.Helper.Helper.showToast(Conversation_Activity.this, String.valueOf(R.string.no_internet_connection));
        }
    }

    @Override
    protected void onPause() {
//        updateOffline();
        super.onPause();
    }

    @Override
    protected void onRestart() {
        updateOnline();
        super.onRestart();
    }
    private void updateOffline() {
        if (connectionDetector.isConnectingToInternet()) {
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
            MultipartTypedOutput multipartTypedOutput = new MultipartTypedOutput();
            multipartTypedOutput.addPart("id", new TypedString(String.valueOf(user.getId())));

//            progress = dialogUtil.showProgressDialog(MainActivity.this, getString(R.string.please_wait));
            appController.paServices.Offline(multipartTypedOutput, new Callback<TokenRoot>() {
                @Override
                public void success(TokenRoot tokenRoot, Response response) {
                    if (tokenRoot.getStatus().equalsIgnoreCase("1")) {
//                        progress.dismiss();

                    } else {
//                        progress.dismiss();
                    }
                }

                @Override
                public void failure(RetrofitError retrofitError) {
//                    progress.dismiss();
                    Log.e("error", retrofitError.toString());
                }
            });
        } else {
            com.master.design.therapist.Helper.Helper.showToast(Conversation_Activity.this, String.valueOf(R.string.no_internet_connection));
        }
    }
}

