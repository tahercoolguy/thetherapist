package com.master.design.therapist.Activity;


import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.iid.FirebaseInstanceId;
import com.master.design.therapist.DataModel.Forgotpassword;
import com.master.design.therapist.DataModel.SocialUserDM;
import com.master.design.therapist.Controller.AppController;
import com.master.design.therapist.DataModel.TherapistLoginDM;
import com.master.design.therapist.DataModel.TokenRoot;
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
import retrofit.mime.MultipartTypedOutput;
import retrofit.mime.TypedString;

public class Sign_InActivity extends AppCompatActivity {

    private static final int REQ_ONE_TAP = 2;  // Can be any integer unique to the Activity.
    private boolean showOneTapUI = true;
    AppController appController;
    String check;
    Dialog progress;
    ConnectionDetector connectionDetector;
    User user;
    DialogUtil dialogUtil;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

//    private SignInClient oneTapClient;
//    private BeginSignInRequest signUpRequest;


    @BindView(R.id.googleSignInButton)
    ImageView googleSignInButton;
    @BindView(R.id.forgotPasswordTxt)
    TextView forgotPasswordTxt;


    @OnClick(R.id.googleSignInButton)
    public void googleSignInButton() {
        check = "2";
        user.setCheck(check);
        sign();
    }

    public void sign() {
        Intent intent = gsc.getSignInIntent();
        startActivityForResult(intent, 1000);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                task.getResult(ApiException.class);
                nativageToAnotherActivity();
            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void nativageToAnotherActivity() {
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
//            Toast.makeText(getApplicationContext(), "Email:"+personEmail, Toast.LENGTH_SHORT).show();

            if (connectionDetector.isConnectingToInternet()) {
                String refreshedToken = FirebaseInstanceId.getInstance().getToken();
                progress = dialogUtil.showProgressDialog(Sign_InActivity.this, getString(R.string.please_wait));
                appController.paServices.SocialUser(personName, personEmail, new Callback<SocialUserDM>() {
                    @Override
                    public void success(SocialUserDM socialUserDM, Response response) {
                        progress.dismiss();


                        if (socialUserDM.getStatus().equalsIgnoreCase("1")) {
                            user.setId(Integer.valueOf(socialUserDM.getUser_id()));
                            Intent intent = new Intent(Sign_InActivity.this, MainActivity.class);
                            startActivity(intent);

                        } else

                            //                       showdialogNoData(context,getString(R.string.terms),getString(R.string.no_terms_and_condition));}
                            Helper.showToast(Sign_InActivity.this, socialUserDM.getMsg());
                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {
                        Log.e("error", retrofitError.toString());
                    }
                });
            } else
                Helper.showToast(Sign_InActivity.this, getString(R.string.no_internet_connection));

        }
//        Intent intent = new Intent(Sign_InActivity.this, MainActivity.class);
//                            startActivity(intent);
    }


    @NotEmpty
    @Email
    @BindView(R.id.userNameET)
    EditText userNameET;

    @NotEmpty
    @BindView(R.id.passETET)
    TextInputEditText passETET;


    @OnClick(R.id.logINTxt)
    public void logINTxt() {
        check = "2";
        user.setCheck(check);

        if (connectionDetector.isConnectingToInternet()) {

            boolean correct = true;
            if (userNameET.getText().toString().equalsIgnoreCase("")) {
                correct = false;
                Helper.showToast(Sign_InActivity.this, getString(R.string.kindly_enter_email));
            } else if (passETET.getText().toString().equalsIgnoreCase("")) {
                correct = false;
                Helper.showToast(Sign_InActivity.this, getString(R.string.kindly_enter_password));
            } else if (correct) {
                String refreshedToken = FirebaseInstanceId.getInstance().getToken();

                progress = dialogUtil.showProgressDialog(Sign_InActivity.this, getString(R.string.please_wait));

                appController.paServices.TherapistLogin(userNameET.getText().toString(), passETET.getText().toString(), "0", new Callback<TherapistLoginDM>() {

                    @Override

                    public void success(TherapistLoginDM therapistLoginDM, Response response) {
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
                    public void failure(RetrofitError retrofitError) {
                        progress.dismiss();

                        Log.e("error", retrofitError.toString());

                    }
                });
            }
        } else
            Helper.showToast(Sign_InActivity.this, getString(R.string.no_internet_connection));


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

//        if(user.getOffline().equalsIgnoreCase("0")){
//            updateOffline();
//        }
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);

//        oneTapClient = Identity.getSignInClient(this);
//        signUpRequest = BeginSignInRequest.builder()
//                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
//                        .setSupported(true)
//                        // Your server's client ID, not your Android client ID.
//                        .setServerClientId(getString(R.string.web_client_id))
//                        // Show all accounts on the device.
//                        .setFilterByAuthorizedAccounts(false)
//                        .build())
//                .build();
//
//
//   googleSignInButton.setOnClickListener(new View.OnClickListener() {
//       @Override
//       public void onClick(View view) {
//
//           oneTapClient.beginSignIn(signUpRequest)
//                   .addOnSuccessListener(Sign_InActivity.this, new OnSuccessListener<BeginSignInResult>() {
//                       @Override
//                       public void onSuccess(BeginSignInResult result) {
//                           try {
//                               startIntentSenderForResult(
//                                       result.getPendingIntent().getIntentSender(), REQ_ONE_TAP,
//                                       null, 0, 0, 0);
//                           } catch (IntentSender.SendIntentException e) {
//                               Log.e("TAG", "Couldn't start One Tap UI: " + e.getLocalizedMessage());
//                           }
//                       }
//                   })
//                   .addOnFailureListener(Sign_InActivity.this, new OnFailureListener() {
//                       @Override
//                       public void onFailure(@NonNull Exception e) {
//                           // No Google Accounts found. Just continue presenting the signed-out UI.
//                           Log.d("TAG", e.getLocalizedMessage());
//                       }
//                   });
//
//       }
//   });

        //   }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        switch (requestCode) {
//            case REQ_ONE_TAP:
//                try {
//                    SignInCredential credential = oneTapClient.getSignInCredentialFromIntent(data);
//                    String idToken = credential.getGoogleIdToken();
//                    if (idToken !=  null) {
//                        // Got an ID token from Google. Use it to authenticate
//                        // with your backend.
//                        String email=credential.getId();
//                        Toast.makeText(getApplicationContext(), "Email:"+email, Toast.LENGTH_SHORT).show();
//                        Log.d("TAG", "Got ID token.");
//                    }
//                } catch (ApiException e) {
//                    // ...
//                    switch (e.getStatusCode()) {
//                        case CommonStatusCodes.CANCELED:
//                            Log.d("TAG", "One-tap dialog was closed.");
//                            // Don't re-prompt the user.
//                            showOneTapUI = false;
//                            break;
//                        case CommonStatusCodes.NETWORK_ERROR:
//                            Log.d("TAG", "One-tap encountered a network error.");
//                            // Try again or just ignore.
//                            break;
//                        default:
//                            Log.d("TAG", "Couldn't get credential from result."
//                                    + e.getLocalizedMessage());
//                            break;
//                    }
//                }
//                break;
//        }
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


    @OnClick(R.id.forgotPasswordTxt)
    public void clickForgotPassword() {

        showForgotPasswordDialog();
    }
    AlertDialog dialog;
    private void showForgotPasswordDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.forgot_password_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView)
                .setTitle(getString(R.string.forgot_password))
                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle reset password action
                        EditText emailEditText = dialogView.findViewById(R.id.emailEditText);
                        String email = emailEditText.getText().toString();

                        if(!email.contains("@")){
                            Helper.showToast(Sign_InActivity.this, getString(R.string.kindly_enter_your_registered_email));
                        }else {
                            forgotPassword(email);
                        }
                        // Implement your password reset logic here using the provided email
                    }
                })
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

          dialog = builder.create();
        dialog.show();
    }


    private void forgotPassword(String email) {
        if (connectionDetector.isConnectingToInternet()) {
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
            MultipartTypedOutput multipartTypedOutput = new MultipartTypedOutput();
            multipartTypedOutput.addPart("email", new TypedString(email));

            progress = dialogUtil.showProgressDialog(Sign_InActivity.this, getString(R.string.please_wait));
            appController.paServices.Reset_Password(multipartTypedOutput, new Callback<Forgotpassword>() {
                @Override
                public void success(Forgotpassword tokenRoot, Response response) {
                    if (tokenRoot.getStatus().equalsIgnoreCase("1")) {
                        progress.dismiss();
                        dialog.cancel();
                        Helper.showToast(Sign_InActivity.this, getString(R.string.password_reset_link_sent_on_registered_email));
                    } else {
                        progress.dismiss();
                        dialog.cancel();
                        Helper.showToast(Sign_InActivity.this, getString(R.string.kindly_enter_your_registered_email));
                    }
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    progress.dismiss();
                    Log.e("error", retrofitError.toString());
                    dialog.cancel();
                    Helper.showToast(Sign_InActivity.this, getString(R.string.kindly_enter_your_registered_email));
                }
            });
        } else {
            com.master.design.therapist.Helper.Helper.showToast(Sign_InActivity.this, String.valueOf(R.string.no_internet_connection));
        }
    }

}