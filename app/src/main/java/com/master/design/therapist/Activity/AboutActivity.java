package com.master.design.therapist.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.master.design.therapist.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutActivity extends AppCompatActivity {

    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        activity=this;
    }


    @OnClick(R.id.backImg)
    public void clickBack() {
        finish();
    }

    @OnClick(R.id.termsLL)
    public void clicktermsLL() {
        startActivity(new Intent(AboutActivity.this, Term_Privacy_TipsActivity.class).putExtra("terms","terms"));
        activity.overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);
    }

    @OnClick(R.id.policyLL)
    public void clickpolicyLL() {
        startActivity(new Intent(AboutActivity.this, Term_Privacy_TipsActivity.class).putExtra("policy","policy"));
        activity.overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);
    }

    @OnClick(R.id.tipsLL)
    public void clicktipsLL() {
        startActivity(new Intent(AboutActivity.this, Term_Privacy_TipsActivity.class).putExtra("tips","tips"));
        activity.overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);
    }

    @Override
    public void onBackPressed() {
        activity.overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);
        super.onBackPressed();
    }

    @Override
    public void finish() {
        super.finish();
        activity.overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);
    }
}