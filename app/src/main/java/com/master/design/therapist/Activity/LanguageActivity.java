package com.master.design.therapist.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.firebase.iid.FirebaseInstanceId;
import com.master.design.therapist.DataModel.TokenRoot;
import com.master.design.therapist.Controller.AppController;
import com.master.design.therapist.Helper.DialogUtil;
import com.master.design.therapist.Helper.Language;
import com.master.design.therapist.Helper.User;
import com.master.design.therapist.Helper.Util;
import com.master.design.therapist.R;
import com.master.design.therapist.Utils.ConnectionDetector;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.MultipartTypedOutput;
import retrofit.mime.TypedString;

public class LanguageActivity extends AppCompatActivity {

    @BindView(R.id.englishRL)
    RelativeLayout englishRL;


    @BindView(R.id.arabicRL)
    RelativeLayout arabicRL;

    @BindView(R.id.englishLL)
    LinearLayout englishLL;


    @BindView(R.id.arabicLL)
    LinearLayout arabicLL;

    AppController appController;
    String check;
    Dialog progress;
    ConnectionDetector connectionDetector;
    DialogUtil dialogUtil;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        ButterKnife.bind(this);
        user = new User(LanguageActivity.this);
        dialogUtil = new DialogUtil();
        appController = (AppController) getApplicationContext();
        connectionDetector = new ConnectionDetector(getApplicationContext());



        String userID = String.valueOf(user.getId());
        if (!userID.equalsIgnoreCase("0")) {
            if (user.getLanguageCode().equalsIgnoreCase("en")) {
                Language language = new Language(1, "Engish", "en");
                user.setLanguage(language);
                englishRL.setBackground(getDrawable(R.drawable.language_select_bg));
                arabicRL.setBackground(getDrawable(R.drawable.language_unselect_bg));
                Util.setConfigChange(LanguageActivity.this, "en");
            } else {
                Language language = new Language(2, "Arabic", "ar");
                user.setLanguage(language);
                offline = true;
                englishRL.setBackground(getDrawable(R.drawable.language_unselect_bg));
                arabicRL.setBackground(getDrawable(R.drawable.language_select_bg));
                Util.setConfigChange(LanguageActivity.this, "ar");

            }
        } else {

        }

    }

    boolean offline = false;

    @OnClick(R.id.englishRL)
    public void clickEnglishRL() {
        Language language = new Language(1, "Engish", "en");
        user.setLanguage(language);
        englishRL.setBackground(getDrawable(R.drawable.language_select_bg));
        arabicRL.setBackground(getDrawable(R.drawable.language_unselect_bg));

        offline = true;
        Util.setConfigChange(LanguageActivity.this, "en");

        restartActivity(LanguageActivity.this);
        startActivity(new Intent(LanguageActivity.this, IntroActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        finish();


//        startActivity(new Intent(LanguageActivity.this, IntroActivity.class));
        overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);
        finish();

    }

    @OnClick(R.id.arabicRL)
    public void clickArabicRL() {
        Language language = new Language(2, "Arabic", "ar");
        user.setLanguage(language);
        offline = true;
        englishRL.setBackground(getDrawable(R.drawable.language_unselect_bg));
        arabicRL.setBackground(getDrawable(R.drawable.language_select_bg));
        Util.setConfigChange(LanguageActivity.this, "ar");
//        getActivity().getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        restartActivity(LanguageActivity.this);
        startActivity(new Intent(LanguageActivity.this, IntroActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        finish();


//        startActivity(new Intent(LanguageActivity.this, IntroActivity.class));
        finish();
        overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);

    }

    @OnClick(R.id.englishLL)
    public void clickEnglishLL() {
        Language language = new Language(1, "Engish", "en");
        user.setLanguage(language);

        offline = true;

        englishRL.setBackground(getDrawable(R.drawable.language_select_bg));
        arabicRL.setBackground(getDrawable(R.drawable.language_unselect_bg));
        Util.setConfigChange(LanguageActivity.this, "en");

        restartActivity(LanguageActivity.this);
        startActivity(new Intent(LanguageActivity.this, IntroActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        finish();


//        startActivity(new Intent(LanguageActivity.this, IntroActivity.class));
        overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);
        finish();

    }

    @OnClick(R.id.arabicLL)
    public void clickArabicLL() {
        Language language = new Language(2, "Arabic", "ar");
        user.setLanguage(language);
        offline = true;
        englishRL.setBackground(getDrawable(R.drawable.language_unselect_bg));
        arabicRL.setBackground(getDrawable(R.drawable.language_select_bg));
        Util.setConfigChange(LanguageActivity.this, "ar");
//        getActivity().getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        restartActivity(LanguageActivity.this);
        startActivity(new Intent(LanguageActivity.this, IntroActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        finish();


//        startActivity(new Intent(LanguageActivity.this, IntroActivity.class));
        finish();
        overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);

    }


    public static void restartActivity(Activity activity) {
        if (Build.VERSION.SDK_INT >= 11) {
            activity.recreate();
        } else {
            activity.finish();
            activity.startActivity(activity.getIntent());
        }
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

    @Override
    protected void onDestroy() {
        if (offline) {
            updateOffline();

        }
        super.onDestroy();
    }

    private void updateOffline() {
        if (connectionDetector.isConnectingToInternet()) {
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
            MultipartTypedOutput multipartTypedOutput = new MultipartTypedOutput();
            String id = String.valueOf(user.getId());
            multipartTypedOutput.addPart("id", new TypedString(id));

//            progress = dialogUtil.showProgressDialog(MainActivity.this, getString(R.string.please_wait));
            appController.paServices.Offline(multipartTypedOutput, new Callback<TokenRoot>() {
                @Override
                public void success(TokenRoot tokenRoot, Response response) {
                    if (tokenRoot.getStatus().equalsIgnoreCase("1")) {
//                        progress.dismiss();
                        user.setId(0);
                        user.setOffline("1");
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
            com.master.design.therapist.Helper.Helper.showToast(LanguageActivity.this, String.valueOf(R.string.no_internet_connection));
        }
    }
}