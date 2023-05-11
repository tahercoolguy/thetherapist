package com.master.design.therapist.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
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
import com.master.design.therapist.DataModel.Ethnic_details;
import com.master.design.therapist.DataModel.TherapistCountriesDM;
import com.master.design.therapist.DataModel.TherapistEthnicDM;
import com.master.design.therapist.DataModel.TherapistRegisterDM;
import com.master.design.therapist.Helper.BottomForAll;
import com.master.design.therapist.Helper.DataChangeDM;
import com.master.design.therapist.Helper.DialogUtil;
import com.master.design.therapist.Helper.Helper;
import com.master.design.therapist.Helper.ResponseListener;
import com.master.design.therapist.Helper.User;
import com.master.design.therapist.R;
import com.master.design.therapist.Utils.ConnectionDetector;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Create_Account_Activity extends AppCompatActivity {

    AppController appController;
    ConnectionDetector connectionDetector;
    User user;
    DialogUtil dialogUtil;
    Dialog progress;

    @BindView(R.id.userNameET)
    EditText userNameET;

    @BindView(R.id.emailET)
    EditText emailEt;

    @BindView(R.id.PasswordEdT)
    EditText PasswordEdT;

    @BindView(R.id.confirmPasswordET)
    EditText confirmPasswordET;

    @BindView(R.id.ethnicityyET)
    TextView ethnicityyET;

    @BindView(R.id.mobilecodeET)
    TextView mobilecodeET;

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

//    @BindView(R.id.full_date)
//    LinearLayout full_date;


    String Gender;
    String name;
    String SelectCountryid,SelectCountryCode;
    String ethnicityyid;
    String Date;
    String DialCode;



    @OnClick(R.id.maleTV)
    public void maleTV() {

        maleTV.setBackgroundResource(R.drawable.rounded_rectagle_blue);
        maleTV.setTextColor(Color.parseColor("#FFFFFF"));
        femaleTV.setBackgroundResource(R.drawable.rounded_rectangle_white);
        femaleTV.setTextColor(Color.parseColor("#000000"));
        Gender="1";
    }

    @OnClick(R.id.femaleTV)
    public void femaleTV() {
        maleTV.setBackgroundResource(R.drawable.rounded_rectangle_white);
        maleTV.setTextColor(Color.parseColor("#000000"));
        femaleTV.setBackgroundResource(R.drawable.rounded_rectagle_blue);
        femaleTV.setTextColor(Color.parseColor("#FFFFFF"));
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



//    @OnClick(R.id.continueTxt)
//    public void continueTxt() {
//        Binding();
//    }

    ArrayList<DataChangeDM> arrayList = new ArrayList();
BottomForAll bottomForAll;
        @OnClick(R.id.ethnicityyET)
          public void ethnicityyET()
        {
                  bottomForAll = new BottomForAll();
                        bottomForAll.arrayList = arrayList;

                            bottomForAll.setResponseListener(new ResponseListener() {
                                @Override
                                public void response(Object object) {

                                    name = ((DataChangeDM) object).getName();
                                    ethnicityyid = ((DataChangeDM) object).getId();
//                                    user.setAreaId(AreaID);
                                    ethnicityyET.setText(name);


                                }
                            });
                            bottomForAll.show(Create_Account_Activity.this.getSupportFragmentManager(), "bottomSheetCountry");


}

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
        bottomForAll.show(Create_Account_Activity.this.getSupportFragmentManager(), "bottomSheetCountry");


    }


    ArrayList<DataChangeDM> arrayList2 = new ArrayList();
    @OnClick(R.id.mobilecodeET)
    public void mobilecodeET()
    {


        bottomForAll = new BottomForAll();
        bottomForAll.arrayList = arrayList2;

        bottomForAll.setResponseListener(new ResponseListener() {
            @Override
            public void response(Object object) {

                DialCode = ((DataChangeDM) object).getName();
                SelectCountryCode = ((DataChangeDM) object).getId();
//               user.setAreaId(AreaID);
                mobilecodeET.setText(SelectCountryCode);

            }
        });
        bottomForAll.show(Create_Account_Activity.this.getSupportFragmentManager(), "bottomSheetCountry");


    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        ButterKnife.bind(this);
        ButterKnife.bind(this);
        appController = (AppController) getApplicationContext();
        connectionDetector = new ConnectionDetector(Create_Account_Activity.this);
        user = new User(Create_Account_Activity.this);
        dialogUtil = new DialogUtil();
        BindingEthenicity();
        SelectCountry();
        SelectMobileCode();



    }

    @OnClick(R.id.continueTxt)
    public void clickcontinueTxt() {
        Binding();

//        startActivity(new Intent(Create_Account_Activity.this, FriendSearch_SelectActivity.class).putExtra("string33", "string33"));
        overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);
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

    public void Binding() {
//        if(connectionDetector.isConnectingToInternet())
//        {
        boolean correct = true;
        if (userNameET.getText().toString().equalsIgnoreCase("")) {
            correct = false;
            Helper.showToast(Create_Account_Activity.this, "kindly enter your name");
        } else if (dateET.getText().toString().equalsIgnoreCase("")) {
            correct = false;
            Helper.showToast(Create_Account_Activity.this, "kindly enter your Date of Birth");
        } else if (monthET.getText().toString().equalsIgnoreCase("")) {
            correct = false;
            Helper.showToast(Create_Account_Activity.this, "kindly enter your Date of Month");
        } else if (yearET.getText().toString().equalsIgnoreCase("")) {
            correct = false;
            Helper.showToast(Create_Account_Activity.this, "kindly enter your Date of Year");
        } else if (selectCountryET.getText().toString().equalsIgnoreCase("")) {
            correct = false;
            Helper.showToast(Create_Account_Activity.this, "kindly select your country");
        } else if (ethnicityyET.getText().toString().equalsIgnoreCase("")) {
            correct = false;
            Helper.showToast(Create_Account_Activity.this, "kindly enter your Ethnicity");
        } else if (mobileET.getText().toString().equalsIgnoreCase("")) {
            correct = false;
            Helper.showToast(Create_Account_Activity.this, "kindly enter your mobile number");
        } else if (emailEt.getText().toString().equalsIgnoreCase("")) {
            correct = false;
            Helper.showToast(Create_Account_Activity.this, "kindly enter your mobile Number");
        }

        else if (PasswordEdT.getText().toString().equalsIgnoreCase("")) {
            correct = false;
            Helper.showToast(Create_Account_Activity.this, "kindly enter your Password");
        } else if (confirmPasswordET.getText().toString().equalsIgnoreCase("")) {
            correct = false;
            Helper.showToast(Create_Account_Activity.this, "kindly enter your Confirm Password");
        }
        else if (Gender==null) {
            correct = false;
            Helper.showToast(Create_Account_Activity.this, "kindly select Gender");
        }
//
//
////            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        if (correct) {

     //       startActivity(new Intent(Create_Account_Activity.this, FriendSearch_SelectActivity.class).putExtra("string33", "string33"));
        Intent intent = new Intent(Create_Account_Activity.this, FriendSearch_SelectActivity.class);
        intent.putExtra("string33", "string33");
        intent.putExtra("userName", userNameET.getText().toString());
        intent.putExtra("date", (yearET.getText().toString()+"-"+monthET.getText().toString()+"-"+dateET.getText().toString()));
        intent.putExtra("selectCountry", SelectCountryid);
        intent.putExtra("gender", Gender);
        intent.putExtra("ethnicity", ethnicityyid);
        intent.putExtra("mobileNumber", mobilecodeET.getText().toString()+mobileET.getText().toString());
        intent.putExtra("email", emailEt.getText().toString());
        intent.putExtra("password", PasswordEdT.getText().toString());
        intent.putExtra("confirmPassword", confirmPasswordET.getText().toString());

            startActivity(intent);

        }
//                progress = dialogUtil.showProgressDialog(Create_Account_Activity.this,getString(R.string.please_wait));
//
//                appController.paServices.TherapistRegister(userNameET.getText().toString(), date.getText().toString(), selectCountryET.getText().toString(), gender.getText().toString(), ethnicityyET.getText().toString(), emailEt.getText().toString(), passwordET.getText().toString(), confirmPasswordET.getText().toString(),, new Callback<TherapistRegisterDM>() {
//                    @Override
//
//                    public void success (TherapistRegisterDM therapistRegisterDM, Response response ) {
//                        progress.dismiss();
//                        if (therapistRegisterDM.getStatus().equalsIgnoreCase("1") ){
////
//
//                            user.setId(Integer.valueOf(therapistRegisterDM.getCustomerID()));
//
//
//                            startActivity(new Intent(Create_Account_Activity.this, VerifyActivity.class));
//
//                        } else
//                            Helper.showToast(Create_Account_Activity.this, customerRegisterDM.getMessage());
//                    }
//
//                    @Override
//                    public void failure ( RetrofitError retrofitError ) {
//                        progress.dismiss();
//                        Log.e("error", retrofitError.toString());
//
//                    }
//                });
//            }
//        }else
//            Helper.showToast(Create_Account_Activity.this,getString(R.string.no_internet_connection));
//    }
    }


    public void BindingEthenicity()
    {
        if (connectionDetector.isConnectingToInternet()) {
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
 //           progress = dialogUtil.showProgressDialog(Create_Account_Activity.this, getString(R.string.please_wait));
            appController.paServices.TherapistEthnic(new Callback<TherapistEthnicDM>() {
                @Override
                public void success(TherapistEthnicDM therapistEthnicDM, Response response) {
 //                   progress.dismiss();
                    if (therapistEthnicDM.getStatus().equalsIgnoreCase("1")) {

                        for (Ethnic_details obj : therapistEthnicDM.getEthnic_details()) {
                            DataChangeDM s = new DataChangeDM();
                            s.setName(obj.getEthnic_name());
                            s.setId(obj.getId());
                            arrayList.add(s);
                        }
                    } else
                        Helper.showToast(Create_Account_Activity.this, getString(R.string.Api_data_not_found));
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    Log.e("error", retrofitError.toString());
                }
            });
        } else
            Helper.showToast(Create_Account_Activity.this, getString(R.string.no_internet_connection));


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
                            s.setName(obj.getName_en());
                            s.setId(obj.getCountry_id());

                            arrayList1.add(s);
                        }
                    } else
                        Helper.showToast(Create_Account_Activity.this, getString(R.string.Api_data_not_found));
                }

                @Override
                public void failure(RetrofitError retrofitError) {
 //                   progress.dismiss();
                    Log.e("error", retrofitError.toString());
                }
            });
        } else
            Helper.showToast(Create_Account_Activity.this, getString(R.string.no_internet_connection));
    }


    public void SelectMobileCode()
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
                            s.setName(obj.getName_en());
                            s.setId(obj.getDialCode());

                            arrayList2.add(s);
                        }
                    } else
                        Helper.showToast(Create_Account_Activity.this, getString(R.string.Api_data_not_found));
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    //                   progress.dismiss();
                    Log.e("error", retrofitError.toString());
                }
            });
        } else
            Helper.showToast(Create_Account_Activity.this, getString(R.string.no_internet_connection));
    }




    public void datepick()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
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


    }

}