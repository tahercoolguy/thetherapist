package com.master.design.therapist.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
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
    private final static int MY_REQUEST_CODE0 = 0;
    private final static int MY_REQUEST_CODE1 = 1;
    private final static int MY_REQUEST_CODE2 = 2;
    private final static int MY_REQUEST_CODE3 = 3;
    private final static int MY_REQUEST_CODE4 = 4;

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
            public void onClickThis(int position) {
                if (position == 0) {
                    Intent intent = new Intent(FriendSearchActivity.this, FriendSearch_SelectActivity.class);
                    intent.putExtra("string1", "string1");
                    startActivityForResult(intent, MY_REQUEST_CODE0);
                    overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);

                }
                if (position == 1) {
                    Intent intent = new Intent(FriendSearchActivity.this, FriendSearch_SelectActivity.class);
                    intent.putExtra("string2", "string2");
                    startActivityForResult(intent, MY_REQUEST_CODE1);
                    overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);


                }
                if (position == 2) {
                    Intent intent = new Intent(FriendSearchActivity.this, FriendSearch_SelectActivity.class);
                    intent.putExtra("string3", "string3");
                    startActivityForResult(intent, MY_REQUEST_CODE2);
                    overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);


                }
                if (position == 3) {

                    Intent intent = new Intent(FriendSearchActivity.this, FriendSearch_SelectActivity.class);
                    intent.putExtra("string4", "string4");
                    startActivityForResult(intent, MY_REQUEST_CODE3);
                    overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);


                }
                if (position == 4) {

                    Intent intent = new Intent(FriendSearchActivity.this, FriendSearch_SelectActivity.class);
                    intent.putExtra("string5", "string5");
                    startActivityForResult(intent, MY_REQUEST_CODE4);
                    overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);

                }
            }

            @Override
            public void onGetSubItem(int position, String subheading) {

            }
        });

    }

    String value;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == MY_REQUEST_CODE0) {
                if (data != null) {
                    value=data.getStringExtra("value");
//                    ArrayList<SearchDM> searchDMArrayList = new ArrayList<>();
//                    searchDMArrayList.add(new SearchDM("Age Range", "20-35"));
//                    searchDMArrayList.add(new SearchDM("Gender", "Male"));
//                    searchDMArrayList.add(new SearchDM("Interest (Optional)", "List"));
//                    searchDMArrayList.add(new SearchDM("Ethic (Optional)", "Arab"));
//                    searchDMArrayList.add(new SearchDM("Education (Optional)", "Arab"));
//
//                    Adapter_Search adapter_search = new Adapter_Search(context, searchDMArrayList);
//                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
//                    rcvRcv.setLayoutManager(linearLayoutManager);
//                    rcvRcv.setAdapter(adapter_search);

                }

            }

            if (requestCode == MY_REQUEST_CODE1) {
                if (data != null) {
                    value=data.getStringExtra("value");
//                    ArrayList<SearchDM> searchDMArrayList = new ArrayList<>();
//                    searchDMArrayList.add(new SearchDM("Age Range", "20-35"));
//                    searchDMArrayList.add(new SearchDM("Gender", value));
//                    searchDMArrayList.add(new SearchDM("Interest (Optional)", "List"));
//                    searchDMArrayList.add(new SearchDM("Ethic (Optional)", "Arab"));
//                    searchDMArrayList.add(new SearchDM("Education (Optional)", "Arab"));
//
//                    Adapter_Search adapter_search = new Adapter_Search(context, searchDMArrayList);
//                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
//                    rcvRcv.setLayoutManager(linearLayoutManager);
//                    rcvRcv.setAdapter(adapter_search);
                }
            }

            if (requestCode == MY_REQUEST_CODE2) {
                if (data != null) {
                    value=data.getStringExtra("value");
//                    ArrayList<SearchDM> searchDMArrayList = new ArrayList<>();
//                    searchDMArrayList.add(new SearchDM("Age Range", "20-35"));
//                    searchDMArrayList.add(new SearchDM("Gender", "male"));
//                    searchDMArrayList.add(new SearchDM("Interest (Optional)", value));
//                    searchDMArrayList.add(new SearchDM("Ethic (Optional)", "Arab"));
//                    searchDMArrayList.add(new SearchDM("Education (Optional)", "Arab"));
//
//                    Adapter_Search adapter_search = new Adapter_Search(context, searchDMArrayList);
//                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
//                    rcvRcv.setLayoutManager(linearLayoutManager);
//                    rcvRcv.setAdapter(adapter_search);

                }
            }

            if (requestCode == MY_REQUEST_CODE3) {
                if (data != null) {
                    value=data.getStringExtra("value");

//                    ArrayList<SearchDM> searchDMArrayList = new ArrayList<>();
//                    searchDMArrayList.add(new SearchDM("Age Range", "20-35"));
//                    searchDMArrayList.add(new SearchDM("Gender", "male"));
//                    searchDMArrayList.add(new SearchDM("Interest (Optional)", "cooking"));
//                    searchDMArrayList.add(new SearchDM("Ethic (Optional)", value));
//                    searchDMArrayList.add(new SearchDM("Education (Optional)", "Arab"));
//
//                    Adapter_Search adapter_search = new Adapter_Search(context, searchDMArrayList);
//                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
//                    rcvRcv.setLayoutManager(linearLayoutManager);
//                    rcvRcv.setAdapter(adapter_search);

                }
            }

            if (requestCode == MY_REQUEST_CODE4) {
                if (data != null) {
                    value=data.getStringExtra("value");

//                    ArrayList<SearchDM> searchDMArrayList = new ArrayList<>();
//                    searchDMArrayList.add(new SearchDM("Age Range", "20-35"));
//                    searchDMArrayList.add(new SearchDM("Gender", "male"));
//                    searchDMArrayList.add(new SearchDM("Interest (Optional)", "cooking"));
//                    searchDMArrayList.add(new SearchDM("Ethic (Optional)", "arab"));
//                    searchDMArrayList.add(new SearchDM("Education (Optional)", value));
//
//                    Adapter_Search adapter_search = new Adapter_Search(context, searchDMArrayList);
//                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
//                    rcvRcv.setLayoutManager(linearLayoutManager);
//                    rcvRcv.setAdapter(adapter_search);

                }

            }
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