package com.master.design.therapist.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.master.design.therapist.Adapter.Adapter_Search;
import com.master.design.therapist.DM.SearchDM;
import com.master.design.therapist.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FriendSearchActivity extends AppCompatActivity {

    private Context context;
    @BindView(R.id.rcvRcv)
    RecyclerView rcvRcv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_search);
        ButterKnife.bind(this);
        context = getApplicationContext();
        getBundleData();
        setRcvRcv();
    }

    private void getBundleData() {


    }


    private void setRcvRcv() {
        ArrayList<SearchDM> searchDMArrayList = new ArrayList<>();
        searchDMArrayList.add(new SearchDM("Age Range", "20-35"));
        searchDMArrayList.add(new SearchDM("Gender", "Male"));
        searchDMArrayList.add(new SearchDM("Interest (Optional)", "List"));
        searchDMArrayList.add(new SearchDM("Ethic (Optional)", "Arab"));
        searchDMArrayList.add(new SearchDM("Education (Optional)", "Arab"));

        Adapter_Search adapter_search = new Adapter_Search(context, searchDMArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        rcvRcv.setLayoutManager(linearLayoutManager);
        rcvRcv.setAdapter(adapter_search);


        adapter_search.setOnItemClickListener(new Adapter_Search.OnItemClickListener() {
            @Override
            public void onClickThis(int position, String heading, String subheading) {
                if (position == 0) {
                    startActivity(new Intent(FriendSearchActivity.this, FriendSearch_SelectActivity.class).putExtra("string1", "string1"));
                    overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);

                }
                if (position == 1) {
                    startActivity(new Intent(FriendSearchActivity.this, FriendSearch_SelectActivity.class).putExtra("string2", "string2"));
                    overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);

                }
                if (position == 2) {
                    startActivity(new Intent(FriendSearchActivity.this, FriendSearch_SelectActivity.class).putExtra("string3", "string3"));
                    overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);
                }
                if (position == 3) {
                    startActivity(new Intent(FriendSearchActivity.this, FriendSearch_SelectActivity.class).putExtra("string4", "string4"));
                    overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);

                }
                if (position == 4) {
                    startActivity(new Intent(FriendSearchActivity.this, FriendSearch_SelectActivity.class).putExtra("string5", "string5"));
                    overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);
                }
            }
        });

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