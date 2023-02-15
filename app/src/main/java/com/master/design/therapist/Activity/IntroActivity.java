package com.master.design.therapist.Activity;

import android.os.Bundle;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.master.design.therapist.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IntroActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        ButterKnife.bind(this);
    }

//    @OnClick(R.id.englishRL)
//    public void clickEnglishRL() {
//
//    }
//
//    @OnClick(R.id.arabicRL)
//    public void clickArabicRL() {
//       ;
//
//
//    }

    @Override
    public void onBackPressed() {
        overridePendingTransition(R.anim.fade_out_fast,R.anim.fade_out_fast);
        super.onBackPressed();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_out_fast,R.anim.fade_out_fast);
    }
}