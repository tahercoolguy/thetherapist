package com.master.design.therapist.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.iid.FirebaseInstanceId;
import com.master.design.therapist.Controller.AppController;
import com.master.design.therapist.DataModel.TherapistLoginDM;
import com.master.design.therapist.Helper.DialogUtil;
import com.master.design.therapist.Helper.User;
import com.master.design.therapist.R;
import com.master.design.therapist.Utils.ConnectionDetector;
import com.master.design.therapist.Utils.Helper;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Sign_InActivity extends AppCompatActivity {

    AppController appController;

    Dialog progress;
    ConnectionDetector connectionDetector;
    User user;
    DialogUtil dialogUtil;

    @NotEmpty
    @Email
    @BindView(R.id.userNameET)
    EditText userNameET;

    @NotEmpty
    @BindView(R.id.passETET)
    TextInputEditText passETET;

    @OnClick(R.id.logINTxt)
    public void logINTxt()
    {

        if(connectionDetector.isConnectingToInternet())
        {

            boolean correct = true;
            if(userNameET.getText().toString().equalsIgnoreCase(""))
            {
                correct=false;
                Helper.showToast(Sign_InActivity.this,getString(R.string.kindly_enter_email));
            }

            else if(passETET.getText().toString().equalsIgnoreCase(""))
            {
                correct=false;
                Helper.showToast(Sign_InActivity.this,getString(R.string.kindly_enter_password));
            }

            else if (correct)
            {
                String refreshedToken = FirebaseInstanceId.getInstance().getToken();

                progress = dialogUtil.showProgressDialog(Sign_InActivity.this, getString(R.string.please_wait));

                appController.paServices.TherapistLogin(userNameET.getText().toString(), passETET.getText().toString(), new Callback<TherapistLoginDM>() {

                            @Override

                            public void success ( TherapistLoginDM therapistLoginDM, Response response ) {
                                progress.dismiss();
                                if (therapistLoginDM.getStatus().equalsIgnoreCase("1")) {
//                        Helper.shwToast(LoginActivity.this,customerRegisterDM.getMessage());
                                    user.setId(Integer.valueOf(therapistLoginDM.getUser().getId()));

                                    startActivity(new Intent(Sign_InActivity.this, MainActivity.class));
                                    overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);
                                    finish();

                                } else
                                    Helper.showToast(Sign_InActivity.this, getString(R.string.user_login_failed));
                            }

                            @Override
                            public void failure ( RetrofitError retrofitError ) {
                                progress.dismiss();

                                Log.e("error", retrofitError.toString());

                            }
                        });
            }
        }else
            Helper.showToast(Sign_InActivity.this,getString(R.string.no_internet_connection));


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
        dialogUtil = new DialogUtil();
        appController = (AppController) getApplicationContext();
        connectionDetector = new ConnectionDetector(getApplicationContext());
        user = new User(Sign_InActivity.this);
    }

//    @OnClick(R.id.logINTxt)
//    public void clicklogINTxt() {
//        startActivity(new Intent(Sign_InActivity.this, MainActivity.class));
//        overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);
//    }

    @OnClick(R.id.createAccountTxt)
    public void clickcreateAccountTxt() {
        startActivity(new Intent(Sign_InActivity.this, Create_Account_Activity.class));
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
}