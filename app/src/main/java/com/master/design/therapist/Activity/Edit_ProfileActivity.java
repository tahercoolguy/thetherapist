package com.master.design.therapist.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;
import com.google.firebase.iid.FirebaseInstanceId;
import com.master.design.therapist.Controller.AppController;
import com.master.design.therapist.DataModel.Details;
import com.master.design.therapist.DataModel.Edit_ProfileDM;
import com.master.design.therapist.DataModel.TherapistCountriesDM;
import com.master.design.therapist.Helper.BottomForAll;
import com.master.design.therapist.Helper.DataChangeDM;
import com.master.design.therapist.Helper.DialogUtil;
import com.master.design.therapist.Helper.Helper;
import com.master.design.therapist.Helper.ResponseListener;
import com.master.design.therapist.Helper.User;
import com.master.design.therapist.R;
import com.master.design.therapist.Utils.ConnectionDetector;


import java.text.SimpleDateFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Edit_ProfileActivity extends AppCompatActivity {

    AppController appController;
    ConnectionDetector connectionDetector;
    User user;
    DialogUtil dialogUtil;
    Dialog progress;

    @BindView(R.id.userNameET)
    EditText userNameET;

    @BindView(R.id.mobileET)
    EditText mobileET;

    @BindView(R.id.dateET)
    TextView dateET;

    @BindView(R.id.monthET)
    TextView monthET;

    @BindView(R.id.yearET)
    TextView yearET;


    @BindView(R.id.selectCountryET)
    TextView selectCountryET;

    @BindView(R.id.genderselectLL)
    LinearLayout genderselectLL;

    @BindView(R.id.femaleTV)
    TextView femaleTV;

    @BindView(R.id.maleTV)
    TextView maleTV;

    String Gender;
    String name;
    String SelectCountryid;

    @OnClick(R.id.maleTV)
    public void maleTV() {
        Gender="1";
    }

    @OnClick(R.id.femaleTV)
    public void femaleTV() {
        Gender="0";
    }

    @OnClick(R.id.dateET)
    public void dateET() {
        datepick();
    }
    @OnClick(R.id.monthET)
    public void monthET() {
        datepick();
    }

    @OnClick(R.id.yearET)
    public void yearET() {
        datepick();
    }

    @OnClick(R.id.editProfileTxt)
    public void editProfileTxt() {
        EditProfileBinding();
    }

    BottomForAll bottomForAll;
    ArrayList<DataChangeDM> arrayList1 = new ArrayList();
    @OnClick(R.id.selectCountryET)
    public void selectCountryET()
    {

        bottomForAll = new BottomForAll();
        bottomForAll.arrayList = arrayList1;

        bottomForAll.setResponseListener(new ResponseListener() {
            @Override
            public void response(Object object) {

                name = ((DataChangeDM) object).getName();
                SelectCountryid = ((DataChangeDM) object).getId();
//               user.setAreaId(AreaID);
                selectCountryET.setText(name);

            }
        });
        bottomForAll.show(Edit_ProfileActivity.this.getSupportFragmentManager(), "bottomSheetCountry");


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void datepick()
    {
        new SingleDateAndTimePickerDialog.Builder(this)
                .bottomSheet()
                .curved()
                .displayMinutes(false)
                .displayHours(false)
                .displayDays(false)
                .displayMonth(true)
                .mainColor(getColor(R.color.black))

                .listener(new SingleDateAndTimePickerDialog.Listener() {
                    @Override
                    public void onDateSelected(java.util.Date date) {
                        String inputPattern = "yyyy-MM-dd";
                        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);

                        String inputPattern2 = "yyyy-MMM-dd";
                        SimpleDateFormat inputFormat2 = new SimpleDateFormat(inputPattern2);

                        try {
                            String str = inputFormat.format(date);
                            String str1 = inputFormat2.format(date);
//                            dateTxt.setText(str);
//                            Date=str;

                            String Month = "MM";
                            inputFormat2 = new SimpleDateFormat(Month);
                            String MonthText = inputFormat2.format(date);
                            monthET.setText(MonthText);

                            String Year = "yyyy";
                            inputFormat = new SimpleDateFormat(Year);
                            String YearText = inputFormat.format(date);
                            yearET.setText(YearText);

                            String Day = "dd";
                            inputFormat = new SimpleDateFormat(Day);
                            String DateText = inputFormat.format(date);
                            dateET.setText(DateText);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                })

                .displayYears(true)
                .displayDaysOfMonth(true)
                .display();



    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);
        appController = (AppController) getApplicationContext();
        connectionDetector = new ConnectionDetector(Edit_ProfileActivity.this);
        user = new User(Edit_ProfileActivity.this);
        dialogUtil = new DialogUtil();

        SelectCountry();
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
        overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);
    }

    @SuppressLint("SuspiciousIndentation")
    public void EditProfileBinding()
    {

                   boolean correct = true;
                   if (userNameET.getText().toString().equalsIgnoreCase("")) {
                       correct = false;
                       Helper.showToast(Edit_ProfileActivity.this, "kindly enter your name");
                   } else if (dateET.getText().toString().equalsIgnoreCase("")) {
                       correct = false;
                       Helper.showToast(Edit_ProfileActivity.this, "kindly enter your Date of Birth");
                   } else if (monthET.getText().toString().equalsIgnoreCase("")) {
                       correct = false;
                       Helper.showToast(Edit_ProfileActivity.this, "kindly enter your Date of Month");
                   } else if (yearET.getText().toString().equalsIgnoreCase("")) {
                       correct = false;
                       Helper.showToast(Edit_ProfileActivity.this, "kindly enter your Date of Year");
                   } else if (selectCountryET.getText().toString().equalsIgnoreCase("")) {
                       correct = false;
                       Helper.showToast(Edit_ProfileActivity.this, "kindly select your country");
                   } else if (Gender == null) {
                       correct = false;
                       Helper.showToast(Edit_ProfileActivity.this, "kindly select Gender");

                   } else if (mobileET.getText().toString().equalsIgnoreCase("")) {
                       correct = false;
                       Helper.showToast(Edit_ProfileActivity.this, "kindly enter your Email");
                   }

                   if (correct) {
                       if (connectionDetector.isConnectingToInternet()) {
                           progress = dialogUtil.showProgressDialog(Edit_ProfileActivity.this, getString(R.string.please_wait));
                           String date = (yearET.getText().toString() + "-" + monthET.getText().toString() + "-" + dateET.getText().toString());
                           appController.paServices.TherapistEdit_Profile(String.valueOf(user.getId()), userNameET.getText().toString(), date, SelectCountryid, Gender, mobileET.getText().toString(), new Callback<Edit_ProfileDM>() {
                               @Override

                               public void success(Edit_ProfileDM edit_profileDM, Response response) {
                                   progress.dismiss();
                                   if (edit_profileDM.getStatus().equalsIgnoreCase("1")) {
//


                                       Helper.showToast(Edit_ProfileActivity.this, edit_profileDM.getMsg());

                                   } else
                                       Helper.showToast(Edit_ProfileActivity.this, edit_profileDM.getMsg());
                               }

                               @Override
                               public void failure(RetrofitError retrofitError) {
                                   progress.dismiss();
                                   Log.e("error", retrofitError.toString());

                               }
                           });

                       }
                       else
                       Helper.showToast(Edit_ProfileActivity.this,getString(R.string.no_internet_connection));

                   }


              }

    public void SelectCountry()
    {
        if (connectionDetector.isConnectingToInternet()) {
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
            //           progress = dialogUtil.showProgressDialog(Create_Account_Activity.this, getString(R.string.please_wait));
            appController.paServices.TherapistCountries(new Callback<TherapistCountriesDM>() {
                @Override
                public void success(TherapistCountriesDM therapistCountriesDM, Response response) {
                    //                   progress.dismiss();
                    if (therapistCountriesDM.getStatus().equalsIgnoreCase("1")) {

                        for (Details obj : therapistCountriesDM.getDetails()) {
                            DataChangeDM s = new DataChangeDM();
                            s.setName(obj.getCountry_name());
                            s.setId(obj.getCountry_id());
                            arrayList1.add(s);
                        }
                    } else
                        Helper.showToast(Edit_ProfileActivity.this, getString(R.string.Api_data_not_found));
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    //                   progress.dismiss();
                    Log.e("error", retrofitError.toString());
                }
            });
        } else
            Helper.showToast(Edit_ProfileActivity.this, getString(R.string.no_internet_connection));
    }



}










