package com.master.design.therapist.Activity;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.englishRL)
    public void clickEnglishRL() {
        englishRL.setBackground(getDrawable(R.drawable.language_select_bg));
        arabicRL.setBackground(getDrawable(R.drawable.language_unselect_bg));
        startActivity(new Intent(LanguageActivity.this, MainActivity.class));
        overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);

    }

    @OnClick(R.id.arabicRL)
    public void clickArabicRL() {
        englishRL.setBackground(getDrawable(R.drawable.language_unselect_bg));
        arabicRL.setBackground(getDrawable(R.drawable.language_select_bg));
        startActivity(new Intent(LanguageActivity.this, MainActivity.class));
        overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);

    }

    @Override
    public void onBackPressed() {
        overridePendingTransition(R.anim.right_slide_in,R.anim.right_slide_in);
        super.onBackPressed();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.right_slide_in,R.anim.right_slide_in);

    }
}