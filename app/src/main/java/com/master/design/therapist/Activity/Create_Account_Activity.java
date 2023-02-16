package com.master.design.therapist.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.master.design.therapist.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class Create_Account_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.selectTxt)
    public void clickselectTxt() {
    }

    @OnClick(R.id.backImg)
    public void clickBack() {
        finish();
    }

    @Override
    public void onBackPressed() {
        overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_in);
        super.onBackPressed();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_in);
    }
}