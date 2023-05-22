package com.master.design.therapist.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.master.design.therapist.R;

import butterknife.ButterKnife;

public class ThankYouActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank_you);
        ButterKnife.bind(this);

        int secondsDelayed = 3;
        new Handler().postDelayed(new Runnable() {
            public void run() {
//                String Id=user.getCountryId();
//                if(Id.equalsIgnoreCase("0"))
//                {
//                    startActivity(new Intent(SplashScreen.this, AddressSelector.class));
//                    finish();
//                }else {
//                    startActivity(new Intent(SplashScreen.this, AdvertiseSelector.class));
//                    finish();
//                }
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
//                startActivity(new Intent(ThankYouActivity.this, MainActivity.class));
                finish();
                overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);

            }
        }, secondsDelayed * 1500);
    }


    @Override
    public void onBackPressed() {
//        overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_in);
//        super.onBackPressed();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);
    }
}