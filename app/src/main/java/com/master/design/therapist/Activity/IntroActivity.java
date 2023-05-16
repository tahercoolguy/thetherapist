package com.master.design.therapist.Activity;

import static com.facebook.FacebookSdk.getApplicationContext;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.master.design.therapist.Adapter.SliderAdapter;
import com.master.design.therapist.DM.IntroSliderDM;
import com.master.design.therapist.R;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IntroActivity extends AppCompatActivity {


    @BindView(R.id.slider)
    SliderView slider;

    Activity activity;
    int positionslider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        ButterKnife.bind(this);
        activity = this;
        setsliderData();
    }

    private void setsliderData() {

        ArrayList<IntroSliderDM> introSliderDMArrayList = new ArrayList<>();
        introSliderDMArrayList.add(new IntroSliderDM(getString(R.string.intro1_head) +
                getString(R.string.intro1_head_part2), getString(R.string.intro_1), R.drawable.ic_intro_one));
        introSliderDMArrayList.add(new IntroSliderDM(getString(R.string.intro_2_head) +
                getString(R.string.intro2_headpart2), getString(R.string.intro2), R.drawable.ic_intro_two));
        SliderAdapter adapter = new SliderAdapter(activity, introSliderDMArrayList);

        // below method is used to set auto cycle direction in left to
        // right direction you can change according to requirement.
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.left_slide_in);
        slider.startAnimation(animation);
        slider.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);


        // below method is used to
        // setadapter to sliderview.
        slider.setSliderAdapter(adapter);
        slider.setInfiniteAdapterEnabled(false);
        // below method is use to set
        // scroll time in seconds.

        slider.setScrollTimeInSec(3);
//
//                        // to set it scrollable automatically
//                        // we use below method.

//                            slider.setAutoCycle(true);
//
//                        // to start autocycle below method is used.

//                            slider.startAutoCycle();
    }


    public void slideNextPosition(int position) {
        positionslider = position;
        if (position == 0) {
            slider.slideToNextPosition();
            positionslider=1;
        } else {
            startActivity(new Intent(IntroActivity.this, Sign_InActivity.class));
            finish();
            overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);
        }

    }

    @OnClick(R.id.backImg)
    public void clickbackImg() {
        if (positionslider == 1) {
            slider.slideToPreviousPosition();
            positionslider=0;
        }else{
            finish();
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