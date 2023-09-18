package com.master.design.therapist.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.iid.FirebaseInstanceId;
import com.master.design.therapist.Adapter.Adapter_Blocked_Accounts;
import com.master.design.therapist.Adapter.Adapter_Friends;
import com.master.design.therapist.Controller.AppController;
import com.master.design.therapist.DataModel.BlockedUserDetail;
import com.master.design.therapist.DataModel.BlockedUserRoot;
import com.master.design.therapist.DataModel.CommonReasonRoot;
import com.master.design.therapist.DataModel.ReportUserRoot;
import com.master.design.therapist.DataModel.TokenRoot;
import com.master.design.therapist.DataModel.UserBlockUnblockRoot;
import com.master.design.therapist.Helper.DialogUtil;
import com.master.design.therapist.Helper.User;
import com.master.design.therapist.R;
import com.master.design.therapist.Utils.ConnectionDetector;
import com.master.design.therapist.Utils.Helper;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.MultipartTypedOutput;
import retrofit.mime.TypedString;

public class BlockedAccountActivity extends AppCompatActivity {

    private Context context;
    private Activity activity;
    AppController appController;
    ConnectionDetector connectionDetector;
    ProgressDialog progressDialog;
    User user;
    DialogUtil dialogUtil;
    Dialog progress;


    @BindView(R.id.backImg)
    ImageView backImg;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @OnClick(R.id.backImg)
    public void backCLick() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blocked_account);
        ButterKnife.bind(this);
        context = BlockedAccountActivity.this;
        activity = BlockedAccountActivity.this;
        appController = (AppController) BlockedAccountActivity.this.getApplicationContext();
        user = new User(context);
        connectionDetector = new ConnectionDetector(BlockedAccountActivity.this);
        dialogUtil = new DialogUtil();
        progressDialog = new ProgressDialog(BlockedAccountActivity.this);
        progressDialog.setMessage(getResources().getString(R.string.please_wait));
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);

        setBlockedAccounts();

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);

    }


    private void setBlockedAccounts() {
        if (connectionDetector.isConnectingToInternet()) {
            MultipartTypedOutput multipartTypedOutput = new MultipartTypedOutput();
            String id = String.valueOf(user.getId());
            multipartTypedOutput.addPart("user_id", new TypedString(id));

//        progress = dialogUtil.showProgressDialog(MainActivity.this, getString(R.string.please_wait));
            appController.paServices.AllBlockedUsers(multipartTypedOutput, new Callback<BlockedUserRoot>() {
                @Override
                public void success(BlockedUserRoot blockedUserRoot, Response response) {
                    if (blockedUserRoot.getStatus().equalsIgnoreCase("1")) {
//                        progress.dismiss();
                        try {

                            if (blockedUserRoot.getDetails().size() > 0) {
                                setAccountList(blockedUserRoot.getDetails());
                            }else{
                                setAccountList(blockedUserRoot.getDetails());
                                Helper.showToast(BlockedAccountActivity.this,getString(R.string.nobody_blocked_in_your_block_list));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


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
            com.master.design.therapist.Helper.Helper.showToast(BlockedAccountActivity.this, String.valueOf(R.string.no_internet_connection));
        }
    }

    private void setAccountList(ArrayList<BlockedUserDetail> blockedUserDetailArrayList) {
        Adapter_Blocked_Accounts adapter_blocked_accounts = new Adapter_Blocked_Accounts(context, blockedUserDetailArrayList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter_blocked_accounts);
        adapter_blocked_accounts.notifyDataSetChanged();

        adapter_blocked_accounts.setOnItemClickListener(new Adapter_Blocked_Accounts.OnItemClickListener() {
            @Override
            public void unblockFriend(int position, String unblock_user_id) {
                showDialogToBlockUser(unblock_user_id);
            }
        });
    }

    public void showDialogToBlockUser(String unblock_user_id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(BlockedAccountActivity.this);
        builder.setMessage(getString(R.string.unblock_user))
                .setIcon(getDrawable(R.drawable.ic_baseline_block_24))
                .setCancelable(true)
                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        unblockUser(unblock_user_id);
                    }
                });
        final AlertDialog alert = builder.create();

        alert.show();
    }


    private void unblockUser(String unblock_user_id) {
        if (connectionDetector.isConnectingToInternet()) {
            MultipartTypedOutput multipartTypedOutput = new MultipartTypedOutput();
            String id = String.valueOf(user.getId());
            multipartTypedOutput.addPart("user_id", new TypedString(id));
            multipartTypedOutput.addPart("unblock_user_id", new TypedString(unblock_user_id));

//        progress = dialogUtil.showProgressDialog(MainActivity.this, getString(R.string.please_wait));
            appController.paServices.UnblockUser(multipartTypedOutput, new Callback<UserBlockUnblockRoot>() {
                @Override
                public void success(UserBlockUnblockRoot userBlockUnblockRoot, Response response) {
                    if (userBlockUnblockRoot.getStatus().equalsIgnoreCase("1")) {
//                        progress.dismiss();
                        Helper.showToast(BlockedAccountActivity.this,getString(R.string.user_has_been_unblocked));

                        setBlockedAccounts();

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
            com.master.design.therapist.Helper.Helper.showToast(BlockedAccountActivity.this, String.valueOf(R.string.no_internet_connection));
        }
    }


}