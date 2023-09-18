package com.master.design.therapist.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.master.design.therapist.Adapter.Adapter_Blocked_Accounts;
import com.master.design.therapist.Adapter.Adapter_Common_Reason;
import com.master.design.therapist.Controller.AppController;
import com.master.design.therapist.DataModel.CommonReasonDetail;
import com.master.design.therapist.DataModel.CommonReasonRoot;
import com.master.design.therapist.Helper.DialogUtil;
import com.master.design.therapist.Helper.User;
import com.master.design.therapist.R;
import com.master.design.therapist.Utils.ConnectionDetector;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CommonReasonActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_common_reason);
        ButterKnife.bind(this);
        context = CommonReasonActivity.this;
        activity = CommonReasonActivity.this;
        appController = (AppController) CommonReasonActivity.this.getApplicationContext();
        user = new User(context);
        connectionDetector = new ConnectionDetector(CommonReasonActivity.this);
        dialogUtil = new DialogUtil();
        progressDialog = new ProgressDialog(CommonReasonActivity.this);
        progressDialog.setMessage(getResources().getString(R.string.please_wait));
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        commonReasons();

    }

    ArrayList<CommonReasonDetail> commonReasonDetailArrayList = new ArrayList<>();

    private void commonReasons() {
        if (connectionDetector.isConnectingToInternet()) {

//        progress = dialogUtil.showProgressDialog(MainActivity.this, getString(R.string.please_wait));
            appController.paServices.CommonReasons(new Callback<CommonReasonRoot>() {
                @Override
                public void success(CommonReasonRoot commonReasonRoot, Response response) {
                    if (commonReasonRoot.getStatus().equalsIgnoreCase("1")) {
//                        progress.dismiss();
                        commonReasonDetailArrayList = commonReasonRoot.getDetails();
                        try {

                            if (commonReasonDetailArrayList.size() > 0) {
                                commonReasonListData(commonReasonDetailArrayList);
                            } else {

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
            com.master.design.therapist.Helper.Helper.showToast(CommonReasonActivity.this, String.valueOf(R.string.no_internet_connection));
        }
    }

    private void commonReasonListData(ArrayList<CommonReasonDetail> commonReasonDetailArrayList) {

        Adapter_Common_Reason adapter_blocked_accounts = new Adapter_Common_Reason(context, commonReasonDetailArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter_blocked_accounts);
        adapter_blocked_accounts.setOnItemClickListener(new Adapter_Common_Reason.OnItemClickListener() {
            @Override
            public void onClickThis(int position, String reasonId, String reasonEng, String reasonAR) {
                Intent intent = new Intent();
                intent.putExtra("reasonId", reasonId);
                intent.putExtra("reasonEng", reasonEng);
                intent.putExtra("reasonAR", reasonAR);
                setResult(5000, intent);
                finish();//finishing activity
            }
        });
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
        overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);
        super.onBackPressed();
    }

    @Override
    public void finish() {
        overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);
        super.finish();
    }
}