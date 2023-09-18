package com.master.design.therapist.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.common.Common;
import com.master.design.therapist.Controller.AppController;
import com.master.design.therapist.DataModel.CommonReasonDetail;
import com.master.design.therapist.DataModel.CommonReasonRoot;
import com.master.design.therapist.DataModel.ReportUserRoot;
import com.master.design.therapist.Helper.DialogUtil;
import com.master.design.therapist.Helper.Helper;
import com.master.design.therapist.Helper.User;
import com.master.design.therapist.R;
import com.master.design.therapist.Utils.ConnectionDetector;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.annotations.Nullable;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.MultipartTypedOutput;
import retrofit.mime.TypedString;

public class ReportUserActivity extends AppCompatActivity {

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

    @BindView(R.id.selectReasonTxt)
    TextView selectReasonTxt;

    @BindView(R.id.descriptionET)
    EditText descriptionET;
    @BindView(R.id.submitReportButton)
    Button submitReportButton;

    @OnClick(R.id.backImg)
    public void backCLick() {
        finish();
    }

    @OnClick(R.id.selectReasonTxt)
    public void clickselectReasonTxt() {
        Intent intent = new Intent(ReportUserActivity.this, CommonReasonActivity.class);
        startActivityForResult(intent, 5000);// Activity is started with requestCode 5000
        overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);
    }

    @OnClick(R.id.submitReportButton)
    public void clicksubmitReportButton() {
        reportUser();
    }

    String FriendsId = "0";
    String reasonId = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_user);
        ButterKnife.bind(this);
        context = ReportUserActivity.this;
        activity = ReportUserActivity.this;
        appController = (AppController) ReportUserActivity.this.getApplicationContext();
        user = new User(context);
        connectionDetector = new ConnectionDetector(ReportUserActivity.this);
        dialogUtil = new DialogUtil();
        progressDialog = new ProgressDialog(ReportUserActivity.this);
        progressDialog.setMessage(getResources().getString(R.string.please_wait));
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);

        FriendsId = getIntent().getStringExtra("FriendsId");
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


    private void reportUser() {
        if (connectionDetector.isConnectingToInternet()) {

            boolean correct = true;

            if (selectReasonTxt.getText().toString().equalsIgnoreCase("")) {
                correct = false;
                Helper.showToast(ReportUserActivity.this, getString(R.string.kindly_select_any_reason));

            } else if (descriptionET.getText().toString().equalsIgnoreCase("")) {
                correct = false;
                Helper.showToast(ReportUserActivity.this, getString(R.string.kindly_enter_description));

            } else if (correct) {
                MultipartTypedOutput multipartTypedOutput = new MultipartTypedOutput();
                String id = String.valueOf(user.getId());
                String description = descriptionET.getText().toString();
                multipartTypedOutput.addPart("user_id", new TypedString(id));
                multipartTypedOutput.addPart("reported_user", new TypedString(FriendsId));
                multipartTypedOutput.addPart("reason", new TypedString(reasonId));
                multipartTypedOutput.addPart("description", new TypedString(description));

//        progress = dialogUtil.showProgressDialog(MainActivity.this, getString(R.string.please_wait));
                appController.paServices.ReportingUser(multipartTypedOutput, new Callback<ReportUserRoot>() {
                    @Override
                    public void success(ReportUserRoot reportUserRoot, Response response) {
                        if (reportUserRoot.getStatus().equalsIgnoreCase("1")) {
//                        progress.dismiss();

                            Helper.showToast(ReportUserActivity.this, getString(R.string.your_reported_submitted_successfully));
                            ReportUserActivity.this.finish();
                        } else {
//                        progress.dismiss();
                            Helper.showToast(ReportUserActivity.this, getString(R.string.report_submission_failed));
                            ReportUserActivity.this.finish();
                        }
                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {
//                    progress.dismiss();
                        Log.e("error", retrofitError.toString());
                    }
                });
            }
        } else {
            com.master.design.therapist.Helper.Helper.showToast(ReportUserActivity.this, String.valueOf(R.string.no_internet_connection));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            // check if the request code is same as what is passed  here it is 5000
            if (requestCode == 5000) {

//            String message_age=data.getStringExtra("age");
                String reId = data.getStringExtra("reasonId");
                String reasonEng = data.getStringExtra("reasonEng");
                String reasonAR = data.getStringExtra("reasonAR");

                if (user.getLanguageCode().equalsIgnoreCase("en")) {
                    selectReasonTxt.setText(reasonEng);
                } else {
                    selectReasonTxt.setText(reasonAR);
                }
                reasonId = reId;
            }
        }
    }

}