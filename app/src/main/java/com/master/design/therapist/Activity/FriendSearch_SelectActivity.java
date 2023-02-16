package com.master.design.therapist.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.master.design.therapist.Adapter.Adapter_Search;
import com.master.design.therapist.Adapter.Adapter_Search_Select;
import com.master.design.therapist.DM.SearchDM;
import com.master.design.therapist.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FriendSearch_SelectActivity extends AppCompatActivity {

    private Context context;

    @BindView(R.id.rcvRcv)
    RecyclerView rcvRcv;

    @BindView(R.id.tittleTxt)
    TextView tittleTxt;

    @BindView(R.id.startSearchingTxt)
    TextView startSearchingTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_search);
        ButterKnife.bind(this);
        context = getApplicationContext();
        startSearchingTxt.setText(getString(R.string.selectt));
        getIntentData();
    }

    String position0, position1, position2, position3, position4 ;

    private void getIntentData() {
        Intent intent = getIntent();
        if (intent != null) {
            position0 =  intent.getStringExtra("string1");
            position1 = intent.getStringExtra("string2");;
            position2 = intent.getStringExtra("string3");
            position3 = intent.getStringExtra("string4");
            position4 = intent.getStringExtra("string5");

            if (position0 != null) {
                setPositionData(position0);
            }
            if (position1 != null) {
                setPositionData(position1);
            }
            if (position2 != null) {
                setPositionData(position2);
            }
            if (position3 != null) {
                setPositionData(position3);
            }
            if (position4 != null) {
                setPositionData(position4);
            }

        }

    }

    @SuppressLint("SetTextI18n")
    private void setPositionData(String position) {

        if (position.equalsIgnoreCase("string1")) {

            tittleTxt.setText(getString(R.string.age_rangee));

            ArrayList<SearchDM> searchDMArrayList = new ArrayList<>();
            searchDMArrayList.add(new SearchDM("", "20-35"));
            searchDMArrayList.add(new SearchDM("", "20-35"));
            searchDMArrayList.add(new SearchDM("", "20-35"));
            searchDMArrayList.add(new SearchDM("", "20-35"));
            searchDMArrayList.add(new SearchDM("", "20-35"));



            Adapter_Search_Select adapter_search = new Adapter_Search_Select(context, searchDMArrayList);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            rcvRcv.setLayoutManager(linearLayoutManager);
            rcvRcv.setAdapter(adapter_search);

        }

        if (position.equalsIgnoreCase("string2")) {
            tittleTxt.setText(getString(R.string.gender_));

            ArrayList<SearchDM> searchDMArrayList = new ArrayList<>();
            searchDMArrayList.add(new SearchDM("", "Male"));
            searchDMArrayList.add(new SearchDM("", "Female"));
            searchDMArrayList.add(new SearchDM("", "Both"));


            Adapter_Search_Select adapter_search = new Adapter_Search_Select(context, searchDMArrayList);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            rcvRcv.setLayoutManager(linearLayoutManager);
            rcvRcv.setAdapter(adapter_search);
        }
        if (position.equalsIgnoreCase("string3")) {
            tittleTxt.setText(getString(R.string.interestt));

        }
        if (position.equalsIgnoreCase("string4")) {
            tittleTxt.setText(getString(R.string.ethic));

            ArrayList<SearchDM> searchDMArrayList = new ArrayList<>();
            searchDMArrayList.add(new SearchDM("", "arab"));
            searchDMArrayList.add(new SearchDM("", "Indian"));


            Adapter_Search_Select adapter_search = new Adapter_Search_Select(context, searchDMArrayList);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            rcvRcv.setLayoutManager(linearLayoutManager);
            rcvRcv.setAdapter(adapter_search);
        }
        if (position.equalsIgnoreCase("string5")) {
            tittleTxt.setText(getString(R.string.education));

            ArrayList<SearchDM> searchDMArrayList = new ArrayList<>();
            searchDMArrayList.add(new SearchDM("", "High School"));
            searchDMArrayList.add(new SearchDM("", "Graduate"));
            searchDMArrayList.add(new SearchDM("", "Post Graduate"));
            searchDMArrayList.add(new SearchDM("", "PHD"));


            Adapter_Search_Select adapter_search = new Adapter_Search_Select(context, searchDMArrayList);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            rcvRcv.setLayoutManager(linearLayoutManager);
            rcvRcv.setAdapter(adapter_search);

        }
    }




    @OnClick(R.id.notificationImg)
    public void clicknotification() {


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