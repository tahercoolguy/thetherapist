package com.master.design.therapist.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
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
import com.master.design.therapist.DataModel.Edit_ProfileDM;
import com.master.design.therapist.DataModel.ProfileDM;
import com.master.design.therapist.DataModel.TherapistCountriesDM;
import com.master.design.therapist.Helper.BottomForAll;
import com.master.design.therapist.Helper.DataChangeDM;
import com.master.design.therapist.Helper.DialogUtil;
import com.master.design.therapist.Helper.Helper;
import com.master.design.therapist.Helper.ResponseListener;
import com.master.design.therapist.Helper.User;
import com.master.design.therapist.R;
import com.master.design.therapist.Utils.ConnectionDetector;
import com.squareup.picasso.Picasso;


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

    @BindView(R.id.mobilecodeET)
    TextView mobilecodeET;

    String Gender;
    String name;
    String SelectCountryid;

    @OnClick(R.id.maleTV)
    public void maleTV() {
        maleTV.setBackgroundResource(R.drawable.rounded_rectagle_blue);
        maleTV.setTextColor(Color.parseColor("#FFFFFF"));
        femaleTV.setBackgroundResource(R.drawable.rounded_rectangle_white);
        femaleTV.setTextColor(Color.parseColor("#000000"));
        Gender = "1";
    }

    @OnClick(R.id.femaleTV)
    public void femaleTV() {
        maleTV.setBackgroundResource(R.drawable.rounded_rectangle_white);
        maleTV.setTextColor(Color.parseColor("#000000"));
        femaleTV.setBackgroundResource(R.drawable.rounded_rectagle_blue);
        femaleTV.setTextColor(Color.parseColor("#FFFFFF"));
        Gender = "0";
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnClick(R.id.dateET)
    public void dateET() {
        datepick();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnClick(R.id.monthET)
    public void monthET() {
        datepick();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnClick(R.id.yearET)
    public void yearET() {
        datepick();
    }

    @OnClick(R.id.editProfileTxt)
    public void editProfileTxt() {
        EditProfileBinding();
    }


    ArrayList<DataChangeDM> arrayList1 = new ArrayList();

    @OnClick(R.id.selectCountryET)
    public void selectCountryET() {

        BottomForAll bottomForAll = bottomForAll = new BottomForAll();
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
    public void datepick() {
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

        getMyProfielDataAPI();
        SelectCountry();
        SelectMobileCode();
    }


    @OnClick(R.id.backImg)
    public void clickBack() {
        finish();
    }

    ArrayList<DataChangeDM> arrayList2 = new ArrayList();
    String SelectCountryCode, DialCode;

    @OnClick(R.id.mobilecodeET)
    public void clickMobilecodeET() {
        BottomForAll bottomForAll;
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
        bottomForAll.show(Edit_ProfileActivity.this.getSupportFragmentManager(), "bottomSheetCountry");

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

    String newMobile;

    @SuppressLint("SuspiciousIndentation")
    public void EditProfileBinding() {

        boolean correct = true;
        if (userNameET.getText().toString().equalsIgnoreCase("")) {
            correct = false;
            Helper.showToast(Edit_ProfileActivity.this, getString(R.string.enter_your_name));
        } else if (dateET.getText().toString().equalsIgnoreCase("")) {
            correct = false;
            Helper.showToast(Edit_ProfileActivity.this, getString(R.string.kindly_select_date_of_birth));
        } else if (monthET.getText().toString().equalsIgnoreCase("")) {
            correct = false;
            Helper.showToast(Edit_ProfileActivity.this, getString(R.string.kindly_enter_your_date_of_month));
        } else if (yearET.getText().toString().equalsIgnoreCase("")) {
            correct = false;
            Helper.showToast(Edit_ProfileActivity.this, getString(R.string.kindly_enter_your_date_of_year));
        } else if (selectCountryET.getText().toString().equalsIgnoreCase("")) {
            correct = false;
            Helper.showToast(Edit_ProfileActivity.this, getString(R.string.kindly_select_your_country));
        } else if (Gender == null) {
            correct = false;
            Helper.showToast(Edit_ProfileActivity.this, getString(R.string.kindly_select_gender));

        } else if (mobileET.getText().toString().equalsIgnoreCase("")) {
            correct = false;
            Helper.showToast(Edit_ProfileActivity.this, getString(R.string.kindly_enter_mobile));
        } else if (mobilecodeET.getText().toString().equalsIgnoreCase("")) {
            correct = false;
            Helper.showToast(Edit_ProfileActivity.this, getString(R.string.kindly_select_mobile_country_code));
        }

        if (correct) {
            if (connectionDetector.isConnectingToInternet()) {
                progress = dialogUtil.showProgressDialog(Edit_ProfileActivity.this, getString(R.string.please_wait));
                String date = (yearET.getText().toString() + "-" + monthET.getText().toString() + "-" + dateET.getText().toString());

                newMobile = mobilecodeET.getText().toString() + mobileET.getText().toString();
                appController.paServices.TherapistEdit_Profile(String.valueOf(user.getId()), userNameET.getText().toString(), date, SelectCountryid, Gender, newMobile, new Callback<Edit_ProfileDM>() {
                    @Override

                    public void success(Edit_ProfileDM edit_profileDM, Response response) {
                        progress.dismiss();
                        if (edit_profileDM.getStatus().equalsIgnoreCase("1")) {

                            Helper.showToast(Edit_ProfileActivity.this, edit_profileDM.getMsg());

                        } else {
//                            Helper.showToast(Edit_ProfileActivity.this,"profile already updated");
                            Helper.showToast(Edit_ProfileActivity.this, edit_profileDM.getMsg());
                        }
//
                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {
                        progress.dismiss();
                        Log.e("error", retrofitError.toString());

                    }
                });

            } else
                Helper.showToast(Edit_ProfileActivity.this, getString(R.string.no_internet_connection));

        }


    }

//    public void SelectCountry() {
//        if (connectionDetector.isConnectingToInternet()) {
//            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
//            //           progress = dialogUtil.showProgressDialog(Create_Account_Activity.this, getString(R.string.please_wait));
//            appController.paServices.TherapistCountries(new Callback<TherapistCountriesDM>() {
//                @Override
//                public void success(TherapistCountriesDM therapistCountriesDM, Response response) {
//                    //                   progress.dismiss();
//                    if (therapistCountriesDM.getStatus().equalsIgnoreCase("1")) {
//
//                        for (Details obj : therapistCountriesDM.getDetails()) {
//                            DataChangeDM s = new DataChangeDM();
//                            s.setName(obj.getCountry_name());
//                            s.setId(obj.getCountry_id());
//                            arrayList1.add(s);
//                        }
//                    } else
//                        Helper.showToast(Edit_ProfileActivity.this, getString(R.string.Api_data_not_found));
//                }
//
//                @Override
//                public void failure(RetrofitError retrofitError) {
//                    //                   progress.dismiss();
//                    Log.e("error", retrofitError.toString());
//                }
//            });
//        } else
//            Helper.showToast(Edit_ProfileActivity.this, getString(R.string.no_internet_connection));
//    }


    public void SelectCountry() {
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
//                            s.setId(obj.getCountry_id());
                            s.setId(obj.getIsoCode());
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


    public void SelectMobileCode() {
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


    String mydob, date;

    public void getMyProfielDataAPI() {
        if (connectionDetector.isConnectingToInternet()) {
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
            progress = dialogUtil.showProgressDialog(Edit_ProfileActivity.this, getString(R.string.please_wait));
            appController.paServices.TherapistProfile(String.valueOf(user.getId()), new Callback<ProfileDM>() {
                @Override
                public void success(ProfileDM profileDM, Response response) {
                    progress.dismiss();
                    if (profileDM.getStatus().equalsIgnoreCase("1")) {

                        userNameET.setText(profileDM.getUser_data().get(0).getName());

                        mydob = profileDM.getUser_data().get(0).getDob();
//                        mygenderId = profileDM.getUser_data().get(0).getGender().get(0).getId();
                        newMobile = profileDM.getUser_data().get(0).getPhone();
                        Gender = profileDM.getUser_data().get(0).getGender().get(0).getId();
                        SelectCountryid = profileDM.getUser_data().get(0).getCountry().get(0).getIsoCode();
                        selectCountryET.setText(profileDM.getUser_data().get(0).getCountry().get(0).getName_en());
                        date = profileDM.getUser_data().get(0).getDob();

                        if (Gender.equalsIgnoreCase("0")) {
                            //female
                            maleTV.setBackgroundResource(R.drawable.rounded_rectangle_white);
                            maleTV.setTextColor(Color.parseColor("#000000"));
                            femaleTV.setBackgroundResource(R.drawable.rounded_rectagle_blue);
                            femaleTV.setTextColor(Color.parseColor("#FFFFFF"));
                            Gender = "0";
                        } else {
                            //male
                            maleTV.setBackgroundResource(R.drawable.rounded_rectagle_blue);
                            maleTV.setTextColor(Color.parseColor("#FFFFFF"));
                            femaleTV.setBackgroundResource(R.drawable.rounded_rectangle_white);
                            femaleTV.setTextColor(Color.parseColor("#000000"));
                            Gender = "1";
                        }

                        String[] numberspilit = newMobile.split(" ");

                        if (numberspilit.length == 2) {
                            if (numberspilit.length <= 2) {
                                String isocode = numberspilit[0];
                                mobilecodeET.setText(isocode);
                                String number1 = numberspilit[1];
                                mobileET.setText("" + number1);
                            }
                        }
                        if (numberspilit.length == 3) {

                            if (numberspilit.length <= 3) {
                                String isocode = numberspilit[0];
                                mobilecodeET.setText(isocode);
                                String number1 = numberspilit[1];
                                String number2 = numberspilit[2];
                                mobileET.setText(number1 + "" + number2);
                            }
                        }
                        if (numberspilit.length == 4) {
                            if (numberspilit.length <= 4) {
                                String isocode = numberspilit[0];
                                mobilecodeET.setText(isocode);
                                String number1 = numberspilit[1];
                                String number2 = numberspilit[2];
                                String number3 = numberspilit[3];

                                mobileET.setText(number1 + " " + number2 + "" + number3);
                            }
                        }


                        String[] items1 = date.split("-");
                        String date1 = items1[2];
                        dateET.setText(date1);
                        String month = items1[1];
                        monthET.setText(month);
                        String year = items1[0];
                        yearET.setText(year);

                    } else {
                        Helper.showToast(Edit_ProfileActivity.this, getString(R.string.Api_data_not_found));
                    }

                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    Log.e("error", retrofitError.toString());
                }
            });
        } else {
            Helper.showToast(Edit_ProfileActivity.this, getString(R.string.no_internet_connection));
        }
    }


}










