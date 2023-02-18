package com.master.design.therapist.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.master.design.therapist.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class Conversation_Activity extends AppCompatActivity {

    private Context context;
    private Activity activity;
    @BindView(R.id.rcvRcv)
    RecyclerView recyclerView;
    @BindView(R.id.profileCircleImg)
    CircleImageView profileCircleImg;
    @BindView(R.id.userNameTxt)
    TextView userNameTxt;
    @BindView(R.id.backImg)
    ImageView backImg;
    @BindView(R.id.notificationImg)
    ImageView notificationImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        ButterKnife.bind(this);
        context = getApplicationContext();
        activity = Conversation_Activity.this;
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