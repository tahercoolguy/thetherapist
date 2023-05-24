package com.master.design.therapist.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

    @BindView(R.id.startSearchingTxt)
    TextView startSearchingTxt;

    private final static int MY_REQUEST_CODE0 = 0;
    private final static int MY_REQUEST_CODE1 = 1;
    private final static int MY_REQUEST_CODE2 = 2;
    private final static int MY_REQUEST_CODE3 = 3;
    private final static int MY_REQUEST_CODE4 = 4;
    private final static int MY_REQUEST_CODE5 = 5;
    Adapter_Search adapter_search;

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

    ArrayList<SearchDM> searchDMArrayList = new ArrayList<>();

    private void setRcvRcv() {

        searchDMArrayList.add(new SearchDM(getString(R.string.age_range), ""));
        searchDMArrayList.add(new SearchDM(getString(R.string.gender), " "));
        searchDMArrayList.add(new SearchDM(getString(R.string.interest_optional), " "));
        searchDMArrayList.add(new SearchDM(getString(R.string.ethic_optional), " "));
        searchDMArrayList.add(new SearchDM(getString(R.string.education_optional), " "));

        adapter_search = new Adapter_Search(context, searchDMArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        rcvRcv.setLayoutManager(linearLayoutManager);
        rcvRcv.setAdapter(adapter_search);


        adapter_search.setOnItemClickListener(new Adapter_Search.OnItemClickListener() {
            @Override
            public void onClickThis(int position) {
                if (position == 0) {
                    Intent intent = new Intent(FriendSearchActivity.this, FriendSearch_SelectActivity.class);
                    intent.putExtra("string1", "string1");
                    startActivityForResult(intent, 2);
                    overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);

                }
                if (position == 1) {
                    Intent intent = new Intent(FriendSearchActivity.this, FriendSearch_SelectActivity.class);
                    intent.putExtra("string2", "string2");
                    startActivityForResult(intent, 1);
                    overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);


                }
                if (position == 2) {
                    Intent intent = new Intent(FriendSearchActivity.this, FriendSearch_SelectActivity.class);
                    intent.putExtra("string3", "string3");
                    startActivityForResult(intent, 5);
                    overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);


                }
                if (position == 3) {

                    Intent intent = new Intent(FriendSearchActivity.this, FriendSearch_SelectActivity.class);
                    intent.putExtra("string4", "string4");
                    startActivityForResult(intent, 3);
                    overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);


                }
                if (position == 4) {

                    Intent intent = new Intent(FriendSearchActivity.this, FriendSearch_SelectActivity.class);
                    intent.putExtra("string5", "string5");
                    startActivityForResult(intent, 4);
                    overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);

                }
            }

            @Override
            public void onGetSubItem(int position, String subheading) {

            }
        });

    }

    String value;
    String selected_ageId="", selected_ageEng="", selected_ageAR = "";
    String selected_genderId="", selected_genderEng="", selected_genderAR = "";
    String selected_ethicID="", selected_ethicNameEng="", selected_ethicNameAR = "";
    String selected_educationID="", selected_educationEng = "";
    String InterestIdList="", InterestNameEngList="", InterestNameArList = "";


    @OnClick(R.id.startSearchingTxt)
    public void clickstartSearchingTxt() {
        if (!selected_ageId.equalsIgnoreCase("") && !selected_genderId.equalsIgnoreCase("")) {
            Intent intent = new Intent();
            intent.putExtra("selected_ageId", selected_ageId);
            intent.putExtra("selected_genderId", selected_genderId);
            intent.putExtra("selected_ethicID", selected_ethicID);
            intent.putExtra("selected_educationID", selected_educationID);
            intent.putExtra("InterestIdList", InterestIdList);
            setResult(3, intent);
            finish();
        } else {
            showdialogNoData(getString(R.string.friends_search), getString(R.string.age_and_gender_manadatory));
        }

    }

    public void showdialogNoData( String tittle, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(FriendSearchActivity.this);
        builder.setTitle(tittle)
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();

        alert.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 2) {
            if (data != null) {
//                    value = data.getStringExtra("value");

                selected_ageId = data.getStringExtra("age_id");
                selected_ageEng = data.getStringExtra("ageEng");
                selected_ageAR = data.getStringExtra("ageAR");

                searchDMArrayList.set(0, new SearchDM(getString(R.string.age_range), selected_ageEng));
                adapter_search.notifyDataSetChanged();
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

        if (requestCode == 1) {
            if (data != null) {
//                    value = data.getStringExtra("value");

                selected_genderId = data.getStringExtra("selected_genderId");
                selected_genderEng = data.getStringExtra("selected_genderEng");
                selected_genderAR = data.getStringExtra("selected_genderAR");

                searchDMArrayList.set(1, new SearchDM(getString(R.string.gender), selected_genderEng));
                adapter_search.notifyDataSetChanged();
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

        if (requestCode == 5) {
            if (data != null) {

                InterestIdList = data.getStringExtra("InterestIdList");
                InterestNameEngList = data.getStringExtra("InterestNameEngList");
                InterestNameArList = data.getStringExtra("InterestNameArList");

                searchDMArrayList.set(2, new SearchDM(getString(R.string.ethic_optional), InterestNameEngList));
                adapter_search.notifyDataSetChanged();

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

        if (requestCode == 3) {
            if (data != null) {
                selected_ethicID = data.getStringExtra("selected_ethicID");
                selected_ethicNameEng = data.getStringExtra("selected_ethicNameEng");
                selected_ethicNameAR = data.getStringExtra("selected_ethicNameAR");

                searchDMArrayList.set(3, new SearchDM(getString(R.string.ethic_optional), selected_ethicNameEng));
                adapter_search.notifyDataSetChanged();
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

        if (requestCode == 4) {
            if (data != null) {

                selected_educationID = data.getStringExtra("selected_educationID");
                selected_educationEng = data.getStringExtra("selected_educationEng");

                searchDMArrayList.set(4, new SearchDM(getString(R.string.ethic_optional), selected_educationEng));
                adapter_search.notifyDataSetChanged();


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

    @OnClick(R.id.notificationImg)
    public void clicknotification() {


    }

    @OnClick(R.id.backImg)
    public void clickBack() {
        finish();
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