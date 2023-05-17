package com.master.design.therapist.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.master.design.therapist.Adapter.Adapter_Interest;
import com.master.design.therapist.Adapter.Adapter_Interest_new;
import com.master.design.therapist.Adapter.Adapter_Search;
import com.master.design.therapist.Adapter.Adapter_Search_Select;
import com.master.design.therapist.Adapter.Adapter_Search_Select1;
import com.master.design.therapist.Adapter.Adapter_Search_Select_Education;
import com.master.design.therapist.Adapter.Adapter_Search_Select_Ethnic;
import com.master.design.therapist.Adapter.Adapter_Search_Select_Gender;
import com.master.design.therapist.Adapter.TherapistEducationDM;
import com.master.design.therapist.Controller.AppController;
import com.master.design.therapist.DM.InterestDM;
import com.master.design.therapist.DM.SearchDM;
import com.master.design.therapist.DataModel.TherapistAgeDM;
import com.master.design.therapist.DataModel.TherapistEthnicDM;
import com.master.design.therapist.DataModel.TherapistGenderDM;
import com.master.design.therapist.DataModel.TherapistInterestDM;
import com.master.design.therapist.DataModel.TherapistLoginDM;
import com.master.design.therapist.Helper.DialogUtil;
import com.master.design.therapist.Helper.User;
import com.master.design.therapist.R;
import com.master.design.therapist.Utils.ConnectionDetector;
import com.master.design.therapist.Utils.Helper;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class FriendSearch_SelectActivity extends AppCompatActivity {


    AppController appController;

    Dialog progress;
    ConnectionDetector connectionDetector;
    User user;
    DialogUtil dialogUtil;

    String username;
    String date;
    String selectCountry;
    String gender;
    String ethenicity;
    String mobilenumber;
    String email;
    String password;
    String confirmPassword;


    public String InterestIdList="";

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
        dialogUtil = new DialogUtil();
        appController = (AppController) getApplicationContext();
        connectionDetector = new ConnectionDetector(getApplicationContext());
        user = new User(FriendSearch_SelectActivity.this);

        username=getIntent().getStringExtra("userName");
        date=getIntent().getStringExtra("date");
        selectCountry=getIntent().getStringExtra("selectCountry");
        gender=getIntent().getStringExtra("gender");
        ethenicity=getIntent().getStringExtra("ethnicity");
        mobilenumber=getIntent().getStringExtra("mobileNumber");
        email=getIntent().getStringExtra("email");
        password=getIntent().getStringExtra("password");
        confirmPassword=getIntent().getStringExtra("confirmPassword");


        startSearchingTxt.setText(getString(R.string.selectt));
        getIntentData();
    }

    String position0, position1, position2, position3, position4 ,string33;

    public void getIntentData() {
        Intent intent = getIntent();
        if (intent != null) {
            position0 = intent.getStringExtra("string1");
            position1 = intent.getStringExtra("string2");
            position2 = intent.getStringExtra("string3");
            position3 = intent.getStringExtra("string4");
            position4 = intent.getStringExtra("string5");
            string33 = intent.getStringExtra("string33");

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
            }  if (string33 != null) {
                setPositionData(string33);
            }

        }



    @OnClick(R.id.startSearchingTxt)
    public void clickstartSearchingTxt(){
        if(string33!=null){
            if (!InterestIdList.equalsIgnoreCase("")) {
                Intent intent = new Intent(FriendSearch_SelectActivity.this, About_You_Activity.class);
                intent.putExtra("userName", username);
                intent.putExtra("date", date);
                intent.putExtra("selectCountry", selectCountry);
                intent.putExtra("gender", gender);
                intent.putExtra("ethnicity", ethenicity);
                intent.putExtra("mobileNumber", mobilenumber);
                intent.putExtra("email", email);
                intent.putExtra("password", password);
                intent.putExtra("confirmPassword", confirmPassword);
                intent.putExtra("InterestIdlist", InterestIdList);
                startActivity(intent);
            }
            else
            {
              Helper.showToast(FriendSearch_SelectActivity.this, getString(R.string.kindly_select_interest));
            }



//            startActivity(new Intent(FriendSearch_SelectActivity.this, About_You_Activity.class));
            overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);
        }
    }

    @SuppressLint({"SetTextI18n", "SuspiciousIndentation"})
    public void setPositionData(String position)
    {

        if (position.equalsIgnoreCase("string1"))
        {

            tittleTxt.setText(getString(R.string.age_rangee));

//            ArrayList<SearchDM> searchDMArrayList = new ArrayList<>();
//            searchDMArrayList.add(new SearchDM("", "20-35"));
//            searchDMArrayList.add(new SearchDM("", "20-35"));
//            searchDMArrayList.add(new SearchDM("", "20-35"));
//            searchDMArrayList.add(new SearchDM("", "20-35"));
//            searchDMArrayList.add(new SearchDM("", "20-35"));

            if(connectionDetector.isConnectingToInternet())
            {

                String refreshedToken = FirebaseInstanceId.getInstance().getToken();

                progress = dialogUtil.showProgressDialog(FriendSearch_SelectActivity.this, getString(R.string.please_wait));

                appController.paServices.TherapistAge( new Callback<TherapistAgeDM>() {

                    @Override

                    public void success ( TherapistAgeDM therapistAgeDM, Response response ) {
                        progress.dismiss();
                        if (therapistAgeDM.getStatus().equalsIgnoreCase("1")) {


            Adapter_Search_Select1 adapter_search = new Adapter_Search_Select1(FriendSearch_SelectActivity.this, therapistAgeDM.getAge_details());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FriendSearch_SelectActivity.this);
            rcvRcv.setLayoutManager(linearLayoutManager);
            rcvRcv.setAdapter(adapter_search);
            adapter_search.setOnItemClickListener(new Adapter_Search_Select.OnItemClickListener() {
                @Override
                public void onClickThis(int position, String heading, String subheading) {
                    String value = subheading ;
                    Intent intent = new Intent();
                    intent.putExtra("value", value);
                    setResult(RESULT_OK, intent);
                 }
            });
                        } else
                            Helper.showToast(FriendSearch_SelectActivity.this, getString(R.string.Api_data_not_found));
                    }

                    @Override
                    public void failure ( RetrofitError retrofitError ) {
                        progress.dismiss();

                        Log.e("error", retrofitError.toString());

                    }
                });

        }else
            Helper.showToast(FriendSearch_SelectActivity.this,getString(R.string.no_internet_connection));




        }







        if (position.equalsIgnoreCase("string2")) {
            tittleTxt.setText(getString(R.string.gender_));

//            ArrayList<SearchDM> searchDMArrayList = new ArrayList<>();
//            searchDMArrayList.add(new SearchDM("", "Male"));
//            searchDMArrayList.add(new SearchDM("", "Female"));
//            searchDMArrayList.add(new SearchDM("", "Both"));

            if(connectionDetector.isConnectingToInternet())
            {

                String refreshedToken = FirebaseInstanceId.getInstance().getToken();

                progress = dialogUtil.showProgressDialog(FriendSearch_SelectActivity.this, getString(R.string.please_wait));

                appController.paServices.TherapistGender( new Callback<TherapistGenderDM>() {

                    @Override

                    public void success ( TherapistGenderDM therapistGenderDM, Response response ) {
                        progress.dismiss();
                        if (therapistGenderDM.getStatus().equalsIgnoreCase("1")) {


            Adapter_Search_Select_Gender adapter_search = new Adapter_Search_Select_Gender(FriendSearch_SelectActivity.this, therapistGenderDM.getGender_details());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FriendSearch_SelectActivity.this);
            rcvRcv.setLayoutManager(linearLayoutManager);
            rcvRcv.setAdapter(adapter_search);

            adapter_search.setOnItemClickListener(new Adapter_Search_Select.OnItemClickListener() {
                @Override
                public void onClickThis(int position, String heading, String subheading) {
                    String value = subheading ;
                    Intent intent = new Intent();
                    intent.putExtra("value", value);
                    setResult(RESULT_OK, intent);
                 }
            });

                        } else
                            Helper.showToast(FriendSearch_SelectActivity.this, getString(R.string.Api_data_not_found));
                    }

                    @Override
                    public void failure ( RetrofitError retrofitError ) {
                        progress.dismiss();

                        Log.e("error", retrofitError.toString());

                    }
                });

            }else
                Helper.showToast(FriendSearch_SelectActivity.this,getString(R.string.no_internet_connection));



        }
        if (position.equalsIgnoreCase("string3") ) {
            tittleTxt.setText(getString(R.string.interestt));
            startSearchingTxt.setText(R.string.continue_);

//            ArrayList<InterestDM> interestDMArrayList = new ArrayList<>();
//            interestDMArrayList.add(new InterestDM("Film", R.drawable.film));
//            interestDMArrayList.add(new InterestDM("Music", R.drawable.film));
//            interestDMArrayList.add(new InterestDM("Books", R.drawable.film));
//            interestDMArrayList.add(new InterestDM("Arts", R.drawable.film));
//            interestDMArrayList.add(new InterestDM("Games", R.drawable.film));
//            interestDMArrayList.add(new InterestDM("Computer", R.drawable.film));
//            interestDMArrayList.add(new InterestDM("Poem", R.drawable.film));
//            interestDMArrayList.add(new InterestDM("Social Media", R.drawable.film));
//            interestDMArrayList.add(new InterestDM("Pets", R.drawable.film));
//            interestDMArrayList.add(new InterestDM("Bars", R.drawable.film));
//            interestDMArrayList.add(new InterestDM("Books", R.drawable.film));
//            interestDMArrayList.add(new InterestDM("Sports", R.drawable.film));
//            interestDMArrayList.add(new InterestDM("Film", R.drawable.film));
//            interestDMArrayList.add(new InterestDM("Music", R.drawable.film));
//            interestDMArrayList.add(new InterestDM("Books", R.drawable.film));
//            interestDMArrayList.add(new InterestDM("Arts", R.drawable.film));
//            interestDMArrayList.add(new InterestDM("Games", R.drawable.film));
//            interestDMArrayList.add(new InterestDM("Computer", R.drawable.film));
//            interestDMArrayList.add(new InterestDM("Poem", R.drawable.film));
//            interestDMArrayList.add(new InterestDM("Social Media", R.drawable.film));
//            interestDMArrayList.add(new InterestDM("Pets", R.drawable.film));
//            interestDMArrayList.add(new InterestDM("Bars", R.drawable.film));
//            interestDMArrayList.add(new InterestDM("Books", R.drawable.film));
//            interestDMArrayList.add(new InterestDM("Sports", R.drawable.film));

            if(connectionDetector.isConnectingToInternet())
            {

                String refreshedToken = FirebaseInstanceId.getInstance().getToken();

                progress = dialogUtil.showProgressDialog(FriendSearch_SelectActivity.this, getString(R.string.please_wait));

                appController.paServices.TherapistInterest( new Callback<TherapistInterestDM>() {

                    @Override

                    public void success ( TherapistInterestDM therapistInterestDM, Response response ) {
                        progress.dismiss();
                        if (therapistInterestDM.getStatus().equalsIgnoreCase("1")) {


            Adapter_Interest adapter_interest = new Adapter_Interest(FriendSearch_SelectActivity.this, therapistInterestDM.getInterest_details());
            GridLayoutManager gridLayoutManager = new GridLayoutManager(FriendSearch_SelectActivity.this, 2);
            rcvRcv.setLayoutManager(gridLayoutManager);
            rcvRcv.setAdapter(adapter_interest);
            adapter_interest.setOnItemClickListener(new Adapter_Interest.OnItemClickListener() {
                @Override
                public void onClickThis(int position, String tittle) {
                    String value =tittle;
                    Intent intent = new Intent();
                    intent.putExtra("value", value);
                    setResult(RESULT_OK, intent);
                 }
            });



                        } else
                            Helper.showToast(FriendSearch_SelectActivity.this, getString(R.string.Api_data_not_found));
                    }

                    @Override
                    public void failure ( RetrofitError retrofitError ) {
                        progress.dismiss();

                        Log.e("error", retrofitError.toString());

                    }
                });

            }else
                Helper.showToast(FriendSearch_SelectActivity.this,getString(R.string.no_internet_connection));




        }
        if (position.equalsIgnoreCase("string4")) {
            tittleTxt.setText(getString(R.string.ethic));

//            ArrayList<SearchDM> searchDMArrayList = new ArrayList<>();
//            searchDMArrayList.add(new SearchDM("", "arab"));
//            searchDMArrayList.add(new SearchDM("", "Indian"));


            if(connectionDetector.isConnectingToInternet())
            {

                String refreshedToken = FirebaseInstanceId.getInstance().getToken();

                progress = dialogUtil.showProgressDialog(FriendSearch_SelectActivity.this, getString(R.string.please_wait));

                appController.paServices.TherapistEthnic( new Callback<TherapistEthnicDM>() {

                    @Override

                    public void success (TherapistEthnicDM therapistEthnicDM, Response response ) {
                        progress.dismiss();
                        if (therapistEthnicDM.getStatus().equalsIgnoreCase("1")) {



            Adapter_Search_Select_Ethnic adapter_search = new Adapter_Search_Select_Ethnic(FriendSearch_SelectActivity.this, therapistEthnicDM.getEthnic_details());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FriendSearch_SelectActivity.this);
            rcvRcv.setLayoutManager(linearLayoutManager);
            rcvRcv.setAdapter(adapter_search);

            adapter_search.setOnItemClickListener(new Adapter_Search_Select.OnItemClickListener() {
                @Override
                public void onClickThis(int position, String heading, String subheading) {
                    String value = subheading ;
                    Intent intent = new Intent();
                    intent.putExtra("value", value);
                    setResult(RESULT_OK, intent);
                 }
            });


                        } else
                            Helper.showToast(FriendSearch_SelectActivity.this, getString(R.string.Api_data_not_found));
                    }

                    @Override
                    public void failure ( RetrofitError retrofitError ) {
                        progress.dismiss();

                        Log.e("error", retrofitError.toString());

                    }
                });

            }else
                Helper.showToast(FriendSearch_SelectActivity.this,getString(R.string.no_internet_connection));




        }
        if (position.equalsIgnoreCase("string5")) {
            tittleTxt.setText(getString(R.string.education));

//            ArrayList<SearchDM> searchDMArrayList = new ArrayList<>();
//            searchDMArrayList.add(new SearchDM("", "High School"));
//            searchDMArrayList.add(new SearchDM("", "Graduate"));
//            searchDMArrayList.add(new SearchDM("", "Post Graduate"));
//            searchDMArrayList.add(new SearchDM("", "PHD"));

            if(connectionDetector.isConnectingToInternet())
            {

                String refreshedToken = FirebaseInstanceId.getInstance().getToken();

                progress = dialogUtil.showProgressDialog(FriendSearch_SelectActivity.this, getString(R.string.please_wait));

                appController.paServices.TherapistEducation( new Callback<TherapistEducationDM>() {

                    @Override

                    public void success (TherapistEducationDM therapistEducationDM, Response response ) {
                        progress.dismiss();
                        if (therapistEducationDM.getStatus().equalsIgnoreCase("1")) {


            Adapter_Search_Select_Education adapter_search = new Adapter_Search_Select_Education(FriendSearch_SelectActivity.this,therapistEducationDM.getEducation_details());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FriendSearch_SelectActivity.this);
            rcvRcv.setLayoutManager(linearLayoutManager);
            rcvRcv.setAdapter(adapter_search);
            adapter_search.setOnItemClickListener(new Adapter_Search_Select.OnItemClickListener() {
                @Override
                public void onClickThis(int position, String heading, String subheading) {
                    String value = subheading ;
                    Intent intent = new Intent();
                    intent.putExtra("value", value);
                    setResult(RESULT_OK, intent);
                 }
            });

                        } else
                            Helper.showToast(FriendSearch_SelectActivity.this, getString(R.string.Api_data_not_found));
                    }

                    @Override
                    public void failure ( RetrofitError retrofitError ) {
                        progress.dismiss();

                        Log.e("error", retrofitError.toString());

                    }
                });

            }else
                Helper.showToast(FriendSearch_SelectActivity.this,getString(R.string.no_internet_connection));




        }



        if (position.equalsIgnoreCase("string33")) {
            tittleTxt.setText(getString(R.string.interestt));
            startSearchingTxt.setText(R.string.continue_);

//            ArrayList<InterestDM> interestDMArrayList = new ArrayList<>();
//            interestDMArrayList.add(new InterestDM("Film", R.drawable.film));
//            interestDMArrayList.add(new InterestDM("Music", R.drawable.film));
//            interestDMArrayList.add(new InterestDM("Books", R.drawable.film));
//            interestDMArrayList.add(new InterestDM("Arts", R.drawable.film));
//            interestDMArrayList.add(new InterestDM("Games", R.drawable.film));
//            interestDMArrayList.add(new InterestDM("Computer", R.drawable.film));
//            interestDMArrayList.add(new InterestDM("Poem", R.drawable.film));
//            interestDMArrayList.add(new InterestDM("Social Media", R.drawable.film));
//            interestDMArrayList.add(new InterestDM("Pets", R.drawable.film));
//            interestDMArrayList.add(new InterestDM("Bars", R.drawable.film));
//            interestDMArrayList.add(new InterestDM("Books", R.drawable.film));
//            interestDMArrayList.add(new InterestDM("Sports", R.drawable.film));
//            interestDMArrayList.add(new InterestDM("Film", R.drawable.film));
//            interestDMArrayList.add(new InterestDM("Music", R.drawable.film));
//            interestDMArrayList.add(new InterestDM("Books", R.drawable.film));
//            interestDMArrayList.add(new InterestDM("Arts", R.drawable.film));
//            interestDMArrayList.add(new InterestDM("Games", R.drawable.film));
//            interestDMArrayList.add(new InterestDM("Computer", R.drawable.film));
//            interestDMArrayList.add(new InterestDM("Poem", R.drawable.film));
//            interestDMArrayList.add(new InterestDM("Social Media", R.drawable.film));
//            interestDMArrayList.add(new InterestDM("Pets", R.drawable.film));
//            interestDMArrayList.add(new InterestDM("Bars", R.drawable.film));
//            interestDMArrayList.add(new InterestDM("Books", R.drawable.film));
//            interestDMArrayList.add(new InterestDM("Sports", R.drawable.film));

            if(connectionDetector.isConnectingToInternet())
            {

                String refreshedToken = FirebaseInstanceId.getInstance().getToken();

                progress = dialogUtil.showProgressDialog(FriendSearch_SelectActivity.this, getString(R.string.please_wait));

                appController.paServices.TherapistInterest( new Callback<TherapistInterestDM>() {

                    @Override

                    public void success ( TherapistInterestDM therapistInterestDM, Response response ) {
                        progress.dismiss();
                        if (therapistInterestDM.getStatus().equalsIgnoreCase("1")) {

            Adapter_Interest_new adapter_interest = new Adapter_Interest_new(FriendSearch_SelectActivity.this, therapistInterestDM.getInterest_details());
            GridLayoutManager gridLayoutManager = new GridLayoutManager(FriendSearch_SelectActivity.this, 2);
            rcvRcv.setLayoutManager(gridLayoutManager);
            rcvRcv.setAdapter(adapter_interest);


                        } else
                            Helper.showToast(FriendSearch_SelectActivity.this, getString(R.string.Api_data_not_found));
                    }

                    @Override
                    public void failure ( RetrofitError retrofitError ) {
                        progress.dismiss();

                        Log.e("error", retrofitError.toString());

                    }
                });

            }else
                Helper.showToast(FriendSearch_SelectActivity.this,getString(R.string.no_internet_connection));




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