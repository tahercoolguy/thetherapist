package com.master.design.therapist.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.master.design.therapist.R;

import butterknife.ButterKnife;

public class ThankYouActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank_you);
        ButterKnife.bind(this);
    }
}