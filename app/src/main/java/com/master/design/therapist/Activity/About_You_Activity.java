package com.master.design.therapist.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.master.design.therapist.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class About_You_Activity extends AppCompatActivity {

    private Activity activity;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_you);
        ButterKnife.bind(this);
        context = this.getApplicationContext();
        activity = this;
    }


    @OnClick(R.id.backImg)
    public void clickBack() {
        finish();
    }

    @OnClick(R.id.signUpTxt)
    public void clicksignUpTxt() {
        startActivity(new Intent(About_You_Activity.this, Sign_InActivity.class));
        overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);
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