package com.master.design.therapist.Activity;

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
import com.master.design.therapist.Adapter.Education_details;
import com.master.design.therapist.Adapter.TherapistEducationDM;
import com.master.design.therapist.Controller.AppController;
import com.master.design.therapist.DataModel.Details;
import com.master.design.therapist.DataModel.Edit_ProfileDM;
import com.master.design.therapist.DataModel.Ethnic_details;
import com.master.design.therapist.DataModel.MyProfile.NewInterest;
import com.master.design.therapist.DataModel.MyProfile.Root;
import com.master.design.therapist.DataModel.TherapistCountriesDM;
import com.master.design.therapist.DataModel.TherapistEthnicDM;
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
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.annotations.Nullable;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.MultipartTypedOutput;
import retrofit.mime.TypedString;

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


    @BindView(R.id.selectEthnicityTxt)
    TextView selectEthnicityTxt;

    @BindView(R.id.selectInterestTxt)
    TextView selectInterestTxt;
    @BindView(R.id.aboutYouET)
    EditText aboutYouET;
    @BindView(R.id.educationET)
    TextView educationET;

    String Gender = "";
    String name;
    String nameAr, educationAr;
    String SelectCountryid;

    String age;
    String educationID;
    String educationName;
    ArrayList<DataChangeDM> arrayList4 = new ArrayList();

    @BindView(R.id.emailET)
    EditText emailEt;
    @BindView(R.id.ageTxt)
    TextView ageTxt;

    @OnClick(R.id.educationET)
    public void educationET() {
        bottomForAll = new BottomForAll();
        bottomForAll.arrayList = arrayList4;

        bottomForAll.setResponseListener(new ResponseListener() {
            @Override
            public void response(Object object) {

                educationName = ((DataChangeDM) object).getName();
                educationAr = ((DataChangeDM) object).getNameAr();
                educationID = ((DataChangeDM) object).getId();
//                                    user.setAreaId(AreaID);

                if (user.getLanguageCode().equalsIgnoreCase("en")) {
                    educationET.setText(educationName);
                } else {
                    educationET.setText(educationAr);
                }
            }
        });
        bottomForAll.show(Edit_ProfileActivity.this.getSupportFragmentManager(), "bottomSheetCountry");
    }


    ArrayList<DataChangeDM> arrayList = new ArrayList();
    BottomForAll bottomForAll;

    String ethnicityyid;
    ArrayList<DataChangeDM> arrayList3 = new ArrayList();

    @OnClick(R.id.selectEthnicityTxt)
    public void clickselectEthnicityTxt() {

        bottomForAll = new BottomForAll();
        bottomForAll.arrayList = arrayList3;


        bottomForAll.setResponseListener(new ResponseListener() {
            @Override
            public void response(Object object) {

                name = ((DataChangeDM) object).getName();
                nameAr = ((DataChangeDM) object).getNameAr();
                ethnicityyid = ((DataChangeDM) object).getId();
//                                    user.setAreaId(AreaID);

                if (user.getLanguageCode().equalsIgnoreCase("en")) {
                    selectEthnicityTxt.setText(name);
                } else {
                    selectEthnicityTxt.setText(nameAr);
                }
//                selectEthnicityTxt.setText(name);


            }
        });
        bottomForAll.show(Edit_ProfileActivity.this.getSupportFragmentManager(), "bottomSheetCountry");


    }

    @OnClick(R.id.selectInterestTxt)
    public void clickselectInterestTxt() {
        Intent intent = new Intent(Edit_ProfileActivity.this, FriendSearch_SelectActivity.class);
        intent.putExtra("string3", "string3");
        startActivityForResult(intent, 5);
        overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);

    }

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


    ArrayList<DataChangeDM> arrayList1 = new ArrayList();

    @OnClick(R.id.selectCountryET)
    public void selectCountryET() {

        BottomForAll bottomForAll = bottomForAll = new BottomForAll();
        bottomForAll.arrayList = arrayList1;

        bottomForAll.setResponseListener(new ResponseListener() {
            @Override
            public void response(Object object) {

                name = ((DataChangeDM) object).getName();
                nameAr = ((DataChangeDM) object).getNameAr();
                SelectCountryid = ((DataChangeDM) object).getId();
//               user.setAreaId(AreaID);
                if (user.getLanguageCode().equalsIgnoreCase("en")) {
                    selectCountryET.setText(name);
                } else {
                    selectCountryET.setText(nameAr);
                }
            }
        });
        bottomForAll.show(Edit_ProfileActivity.this.getSupportFragmentManager(), "bottomSheetCountry");


    }


    public void datepick() {
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
                        public void onDateSelected(Date date) {
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);
        appController = (AppController) getApplicationContext();
        connectionDetector = new ConnectionDetector(Edit_ProfileActivity.this);
        user = new User(Edit_ProfileActivity.this);
        dialogUtil = new DialogUtil();
        SelectMobileCode();
        BindingEducation();

        SelectCountry();
    }


    @OnClick(R.id.backImg)
    public void clickBack() {
        Intent intent = new Intent(Edit_ProfileActivity.this, My_ProfileActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish(); // Finish ActivityC
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

                SelectCountryCode = ((DataChangeDM) object).getId();
//               user.setAreaId(AreaID);
                mobilecodeET.setText(SelectCountryCode);

                if (user.getLanguageCode().equalsIgnoreCase("en")) {
                    DialCode = ((DataChangeDM) object).getName();
                } else {
                    DialCode = ((DataChangeDM) object).getNameAr();
                }

            }
        });
        bottomForAll.show(Edit_ProfileActivity.this.getSupportFragmentManager(), "bottomSheetCountry");

    }

    @OnClick(R.id.ageTxt)
    public void clickAgeET() {
//        startActivity(new Intent(Create_Account_Activity.this, FriendSearch_SelectActivity.class).putExtra("string1","string1"));
        Intent intent = new Intent(Edit_ProfileActivity.this, FriendSearch_SelectActivity.class);
        intent.putExtra("age_single", "age_single");
        startActivityForResult(intent, 2);// Activity is started with requestCode 2

    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(Edit_ProfileActivity.this, My_ProfileActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish(); // Finish ActivityC
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
        } else if (Gender.equalsIgnoreCase("")) {
            correct = false;
            Helper.showToast(Edit_ProfileActivity.this, getString(R.string.kindly_select_gender));

        }
//        else if (mobileET.getText().toString().equalsIgnoreCase("")) {
//            correct = false;
//            Helper.showToast(Edit_ProfileActivity.this, getString(R.string.kindly_enter_mobile));
//        }
//        else if (mobilecodeET.getText().toString().equalsIgnoreCase("")) {
//            correct = false;
//            Helper.showToast(Edit_ProfileActivity.this, getString(R.string.kindly_select_mobile_country_code));
//        }
//        else if (selectEthnicityTxt.getText().toString().equalsIgnoreCase("")) {
//            correct = false;
//            Helper.showToast(Edit_ProfileActivity.this, getString(R.string.kindlyenter_your_ethnicity));
//        }
        else if (selectInterestTxt.getText().toString().equalsIgnoreCase("")) {
            correct = false;
            Helper.showToast(Edit_ProfileActivity.this, getString(R.string.kindly_select_interest));
        } else if (aboutYouET.getText().toString().equalsIgnoreCase("")) {
            correct = false;
            Helper.showToast(Edit_ProfileActivity.this, getString(R.string.kindly_tell_me_about_u));
        } else if (aboutYouET.getText().toString().length() > 50) {
            correct = false;
            Helper.showToast(Edit_ProfileActivity.this, getString(R.string.characters_should_be_less_than));
        } else if (educationET.getText().toString().equalsIgnoreCase("")) {
            correct = false;
            Helper.showToast(Edit_ProfileActivity.this, getString(R.string.kindly_select_education));
        }

        String aboutyou = aboutYouET.getText().toString();
        if (!InterestIdList.isEmpty()) {  //creating a constructor of StringBuffer class
            StringBuffer sb = new StringBuffer(InterestIdList);
            //invoking the method
            if (!sb.equals("")) {

                try {
                    sb.deleteCharAt(sb.length() - 1);

                    InterestIdList = String.valueOf(sb);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }


        if (correct) {
            if (connectionDetector.isConnectingToInternet()) {
                progress = dialogUtil.showProgressDialog(Edit_ProfileActivity.this, getString(R.string.please_wait));
                String date = (yearET.getText().toString() + "-" + monthET.getText().toString() + "-" + dateET.getText().toString());

                if(mobileET.getText().toString().equalsIgnoreCase("")){
                    newMobile="";
                    mobilecodeET.setText("");
                }else{
                    newMobile = mobilecodeET.getText().toString() + mobileET.getText().toString();

                }

                String email = emailEt.getText().toString();
                appController.paServices.TherapistEdit_Profile(String.valueOf(user.getId()), userNameET.getText().toString(), date, SelectCountryid, Gender, newMobile, educationID, ethnicityyid, InterestIdList, aboutyou, email, age, new Callback<Edit_ProfileDM>() {
                    @Override

                    public void success(Edit_ProfileDM edit_profileDM, Response response) {
                        progress.dismiss();
                        if (edit_profileDM.getStatus().equalsIgnoreCase("1")) {

                            Helper.showToast(Edit_ProfileActivity.this, edit_profileDM.getMsg());
                            // Inside ActivityC, when you want to start a new activity and clear the stack
                            Intent intent = new Intent(Edit_ProfileActivity.this, My_ProfileActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish(); // Finish ActivityC

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
                        arrayList.clear();
                        for (Details obj : therapistCountriesDM.getDetails()) {
                            DataChangeDM s = new DataChangeDM();
                            s.setName(obj.getName_en());
                            s.setNameAr(obj.getName_ar());
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
                        arrayList.clear();
                        for (Details obj : therapistCountriesDM.getDetails()) {
                            DataChangeDM s = new DataChangeDM();
                            s.setName(obj.getName_en());
                            s.setNameAr(obj.getName_ar());
                            s.setId(obj.getDialCode());

                            arrayList2.add(s);
                        }

                        getMyProfielDataAPI();

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
            MultipartTypedOutput multipartTypedOutput = new MultipartTypedOutput();
            multipartTypedOutput.addPart("id", new TypedString(String.valueOf(user.getId())));
            appController.paServices.TherapistProfileNew(multipartTypedOutput, new Callback<Root>() {
                @Override
                public void success(Root profileDM, Response response) {
                    progress.dismiss();
                    if (profileDM.getStatus().equalsIgnoreCase("1")) {

                        emailEt.setText(profileDM.getUser_data().get(0).getEmail());
                        userNameET.setText(profileDM.getUser_data().get(0).getName());
                        age = profileDM.getUser_data().get(0).getAge().getId();
                        if (user.getLanguageCode().equalsIgnoreCase("en")) {
                            ageTxt.setText(profileDM.getUser_data().get(0).getAge().getAge_eg());
                        } else {
                            ageTxt.setText(profileDM.getUser_data().get(0).getAge().getAge_ar());
                        }

//                        if (user.getLanguageCode().equalsIgnoreCase("en")) {
//                            selectEthnicityTxt.setText(profileDM.getUser_data().get(0).getEthnicity().getName_en());
//                        } else {
//                            selectEthnicityTxt.setText(profileDM.getUser_data().get(0).getEthnicity().getName_ar());
//                        }
//                        ethnicityyid = profileDM.getUser_data().get(0).getEthnicity().getId();

                        aboutYouET.setText(profileDM.getUser_data().get(0).getAboutyou());
                        if (user.getLanguageCode().equalsIgnoreCase("en")) {
                            educationET.setText(profileDM.getUser_data().get(0).getEducation().getName_en());
                        } else {
                            educationET.setText(profileDM.getUser_data().get(0).getEducation().getName_ar());
                        }
                        educationID = profileDM.getUser_data().get(0).getEducation().getId();

                        mydob = profileDM.getUser_data().get(0).getDob();
//                        mygenderId = profileDM.getUser_data().get(0).getGender().get(0).getId();

                        try {
                            if(!profileDM.getUser_data().get(0).getPhone().equalsIgnoreCase("")){
                                try {
                                    newMobile = profileDM.getUser_data().get(0).getPhone();
                                    String extractedCountryCode = null;
                                    String extractedPhoneNumber = null;

                                    for (DataChangeDM countryCode : arrayList2) {
                                        if (newMobile.startsWith(countryCode.getId())) {
                                            extractedCountryCode = countryCode.getId();
                                            extractedPhoneNumber = newMobile.substring(countryCode.getId().length());
                                            break;
                                        }
                                    }

                                    if (extractedCountryCode != null && extractedPhoneNumber != null) {
                                        // Now you have the separated country code and phone number
                                        // extractedCountryCode contains the country code (e.g., "+91")
                                        // extractedPhoneNumber contains the remaining phone number
                                        SelectCountryCode = extractedCountryCode;
                                        mobilecodeET.setText(extractedCountryCode);
                                        mobileET.setText(extractedPhoneNumber);
                                    } else {
                                        // The phone number doesn't match any known country code
                                    }


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        Gender = profileDM.getUser_data().get(0).getGender().getId();
                        SelectCountryid = profileDM.getUser_data().get(0).getCountry().getIsoCode();
                        String countryName;
                        if (user.getLanguageCode().equalsIgnoreCase("en")) {
                            countryName = profileDM.getUser_data().get(0).getCountry().getName_en();
                        } else {
                            countryName = profileDM.getUser_data().get(0).getCountry().getName_ar();
                        }


                        selectCountryET.setText(countryName);
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


//                        String[] numberspilit = newMobile.split(" ");
//
//                        if (numberspilit.length == 2) {
//                            if (numberspilit.length <= 2) {
//                                String isocode = numberspilit[0];
//                                mobilecodeET.setText(isocode);
//                                String number1 = numberspilit[1];
//                                mobileET.setText("" + number1);
//                            }
//                        }
//                        if (numberspilit.length == 3) {
//
//                            if (numberspilit.length <= 3) {
//                                String isocode = numberspilit[0];
//                                mobilecodeET.setText(isocode);
//                                String number1 = numberspilit[1];
//                                String number2 = numberspilit[2];
//                                mobileET.setText(number1 + "" + number2);
//                            }
//                        }
//                        if (numberspilit.length == 4) {
//                            if (numberspilit.length <= 4) {
//                                String isocode = numberspilit[0];
//                                mobilecodeET.setText(isocode);
//                                String number1 = numberspilit[1];
//                                String number2 = numberspilit[2];
//                                String number3 = numberspilit[3];
//
//                                mobileET.setText(number1 + " " + number2 + "" + number3);
//                            }
//                        }



//                        String phoneNumber = newMobile; // Your phone number
//                        String extractedCountryCode = "";
//                        String extractedPhoneNumber = phoneNumber;
//
//                        if (arrayList2 != null) {
//                            for (DataChangeDM countryCode : arrayList2) {
//                                String dialCode = countryCode.getDialCode();
//                                if (dialCode != null && phoneNumber.startsWith(dialCode)) {
//                                    extractedCountryCode = dialCode;
//                                    extractedPhoneNumber = phoneNumber.substring(dialCode.length());
//                                    break;
//                                }
//                            }
//                        }
//
//                        mobilecodeET.setText(extractedCountryCode);
//                        mobileET.setText(extractedPhoneNumber);
//                        newMobile = extractedCountryCode + extractedPhoneNumber;


                        String[] items1 = date.split("-");
                        String date1 = items1[2];
                        dateET.setText(date1);
                        String month = items1[1];
                        monthET.setText(month);
                        String year = items1[0];
                        yearET.setText(year);


                        ArrayList<String> interestlist = new ArrayList<>();
                        ArrayList<String> interestlistID = new ArrayList<>();

                        for (NewInterest newinterest : profileDM.getUser_data().get(0).getInterests()) {
                            if (user.getLanguageCode().equalsIgnoreCase("en")) {
                                interestlist.add(newinterest.getInterest_eg());
                            } else {
                                interestlist.add(newinterest.getInterest_arb());
                            }
                            interestlistID.add(newinterest.getId());
                        }

                        StringBuilder combinedInterests = new StringBuilder();
                        StringBuilder combinedInterestsID = new StringBuilder();
                        for (int i = 0; i < interestlist.size(); i++) {
                            String combinedInterest = interestlist.get(i) + " ";
                            String combinedInterestID = interestlistID.get(i) + ",";
                            combinedInterests.append(combinedInterest);
                            combinedInterestsID.append(combinedInterestID);
                            if (i < interestlist.size() - 1) {
                                combinedInterests.append(",");
                            }
                        }

                        String combinedInterestsString = combinedInterests.toString();
                        String combinedInterestsIDString = combinedInterestsID.toString();

                        selectInterestTxt.setText(combinedInterestsString);
                        InterestIdList = combinedInterestsIDString;
// Now you have the combined interests and IDs separated by commas


//                        BindingEthenicity();

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


    String selected_educationID = "", selected_educationEng = "";
    String InterestIdList = "", InterestNameEngList = "", InterestNameArList = "";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode != RESULT_CANCELED) {
            if (requestCode == 5) {
                if (data != null) {
                    InterestIdList = data.getStringExtra("InterestIdList");
                    InterestNameEngList = data.getStringExtra("InterestNameEngList");
                    InterestNameArList = data.getStringExtra("InterestNameArList");

                    if (user.getLanguageCode().equalsIgnoreCase("en")) {
                        selectInterestTxt.setText(InterestNameEngList);
                    } else {
                        selectInterestTxt.setText(InterestNameArList);
                    }
                }
            }
        }

        if (resultCode != RESULT_CANCELED) {
            // check if the request code is same as what is passed  here it is 2
            if (requestCode == 2) {

//            String message_age=data.getStringExtra("age");
                String age_id = data.getStringExtra("age_id");
                String ageEng = data.getStringExtra("ageEng");
                String ageAR = data.getStringExtra("ageAR");

                if (user.getLanguageCode().equalsIgnoreCase("en")) {
                    ageTxt.setText(ageEng);
                } else {
                    ageTxt.setText(ageAR);
                }
                age = age_id;
            }
        }


        super.onActivityResult(requestCode, resultCode, data);
    }

    public void BindingEthenicity() {
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
                            s.setNameAr(obj.getEthnic_name_arb());
                            s.setId(obj.getId());
                            arrayList3.add(s);
                        }

                    } else
                        Helper.showToast(Edit_ProfileActivity.this, getString(R.string.Api_data_not_found));
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    Log.e("error", retrofitError.toString());
                }
            });
        } else
            Helper.showToast(Edit_ProfileActivity.this, getString(R.string.no_internet_connection));


    }

    public void BindingEducation() {
        if (connectionDetector.isConnectingToInternet()) {
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
            progress = dialogUtil.showProgressDialog(Edit_ProfileActivity.this, getString(R.string.please_wait));
            appController.paServices.TherapistEducation(new Callback<TherapistEducationDM>() {
                @Override
                public void success(TherapistEducationDM therapistEducationDM, Response response) {
                    progress.dismiss();
                    if (therapistEducationDM.getStatus().equalsIgnoreCase("1")) {

                        for (Education_details obj : therapistEducationDM.getEducation_details()) {
                            DataChangeDM s = new DataChangeDM();
                            s.setName(obj.getEducation());
                            s.setNameAr(obj.getEducation_arb());
                            s.setId(obj.getId());
                            arrayList4.add(s);
                        }
                    } else
                        Helper.showToast(Edit_ProfileActivity.this, getString(R.string.Api_data_not_found));
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    Log.e("error", retrofitError.toString());
                }
            });
        } else
            Helper.showToast(Edit_ProfileActivity.this, getString(R.string.no_internet_connection));

    }
}










