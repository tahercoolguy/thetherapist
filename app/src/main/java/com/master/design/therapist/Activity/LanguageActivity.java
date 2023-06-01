package com.master.design.therapist.Activity;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.master.design.therapist.Helper.Language;
import com.master.design.therapist.Helper.User;
import com.master.design.therapist.Helper.Util;
import com.master.design.therapist.R;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LanguageActivity extends AppCompatActivity {

    @BindView(R.id.englishRL)
    RelativeLayout englishRL;


    @BindView(R.id.arabicRL)
    RelativeLayout arabicRL;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        ButterKnife.bind(this);
        user = new User(LanguageActivity.this);
    }

    @OnClick(R.id.englishRL)
    public void clickEnglishRL() {
        Language language = new Language(1,"Engish","en");
        user.setLanguage(language);

        Util.setConfigChange(LanguageActivity.this,"en");

        restartActivity(LanguageActivity.this);
        startActivity(new Intent(LanguageActivity.this,IntroActivity.class));
        finish();

        englishRL.setBackground(getDrawable(R.drawable.language_select_bg));
        arabicRL.setBackground(getDrawable(R.drawable.language_unselect_bg));
//        startActivity(new Intent(LanguageActivity.this, IntroActivity.class));
        overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);
        finish();
    }

    @OnClick(R.id.arabicRL)
    public void clickArabicRL() {
        Language language = new Language(2,"Arabic","ar");
        user.setLanguage(language);
        Util.setConfigChange(LanguageActivity.this,"ar");
//        getActivity().getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        restartActivity(LanguageActivity.this);
        startActivity(new Intent(LanguageActivity.this,IntroActivity.class));
        finish();

        englishRL.setBackground(getDrawable(R.drawable.language_unselect_bg));
        arabicRL.setBackground(getDrawable(R.drawable.language_select_bg));
//        startActivity(new Intent(LanguageActivity.this, IntroActivity.class));
        finish();
        overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);

    }

    public static void restartActivity(Activity activity){
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
}