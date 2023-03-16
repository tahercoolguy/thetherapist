package com.master.design.therapist.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.iid.FirebaseInstanceId;
import com.master.design.therapist.Controller.AppController;
import com.master.design.therapist.Helper.DialogUtil;
import com.master.design.therapist.Helper.User;
import com.master.design.therapist.R;
import com.master.design.therapist.Utils.ConnectionDetector;
import com.master.design.therapist.Utils.Helper;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LoginActivity extends AppCompatActivity implements  Validator.ValidationListener{
//    AppController appController;
//
//    Dialog progress;
//    ConnectionDetector connectionDetector;
//    User user;
//    DialogUtil dialogUtil;
//
//    @NotEmpty
//    @Email
//    @BindView(R.id.emailET)
//    EditText EmailET;
//
//    @NotEmpty
//    @BindView(R.id.passwordET)
//    EditText passwordET;
//
//    @OnClick(R.id.signInBtn)
//    public void SignIn()
//    {
//
//        if(connectionDetector.isConnectingToInternet())
//        {
//
//            boolean correct = true;
//            if(emailET.getText().toString().equalsIgnoreCase(""))
//            {
//                correct=false;
//                Helper.showToast(LoginActivity.this,"kindly enter your email");
//            }
//
//            else if(passwordET.getText().toString().equalsIgnoreCase(""))
//            {
//                correct=false;
//                Helper.showToast(LoginActivity.this,"kindly enter your password");
//            }
//
//            else if (correct) {
//                String refreshedToken = FirebaseInstanceId.getInstance().getToken();
//
//                progress = dialogUtil.showProgressDialog(LoginActivity.this, getString(R.string.please_wait));
//
//                appController.paServices.CustomerLogin(emailET.getText().toString(), passwordET.getText().toString(),
//                        "2", refreshedToken, new Callback<CustomerRegisterDM>() {
//
//                            @Override
//
//                            public void success ( CustomerRegisterDM customerRegisterDM, Response response ) {
//                                progress.dismiss();
//                                if (customerRegisterDM.getStatus().equalsIgnoreCase("1")) {
////                        Helper.shwToast(LoginActivity.this,customerRegisterDM.getMessage());
//                                    user.setId(Integer.valueOf(customerRegisterDM.getCustomerID()));
//
//                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                                    finish();
//
//                                } else
//                                    Helper.showToast(LoginActivity.this, customerRegisterDM.getMessage());
//                            }
//
//                            @Override
//                            public void failure ( RetrofitError retrofitError ) {
//                                progress.dismiss();
//
//                                Log.e("error", retrofitError.toString());
//
//                            }
//                        });
//            }
//        }else
//            Helper.showToast(LoginActivity.this,getString(R.string.no_internet_connection));
//
//
//    }

    @OnClick(R.id.signUpBtn)
    public void signUpBtn()
    {
//        Intent intent=new Intent(LoginActivity.this,SignUpActivity.class);
//        startActivity(intent);
    }

    @OnClick(R.id.guestBtn)
    public void guestBtn()
    {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        ButterKnife.bind(this);
//        dialogUtil = new DialogUtil();
//        appController = (AppController) getApplicationContext();
//        connectionDetector = new ConnectionDetector(getApplicationContext());
//        user = new User(LoginActivity.this);
        validator=new Validator(this);
        validator.setValidationListener(this);

    }

    @Override
    public void onValidationSucceeded() {

    }
    boolean o=true;
    Validator validator;


    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);
            o=false;
            // Display error messages ;)
            if (view instanceof TextInputEditText) {
                ((TextInputEditText) view).setError(message);
            } else {
                Helper.showToast(LoginActivity.this,message);
            }
        }
    }

    public void isValid() {
        boolean done = true;
        o=true;
        validator.validate();
        //o=done;
        if(!done)
            o=done;

    }

}
