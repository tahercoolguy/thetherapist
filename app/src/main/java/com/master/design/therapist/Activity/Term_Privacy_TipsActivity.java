package com.master.design.therapist.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.master.design.therapist.Controller.AppController;
import com.master.design.therapist.DataModel.Terms_ConditionsDM;
import com.master.design.therapist.Helper.DialogUtil;
import com.master.design.therapist.Helper.Helper;
import com.master.design.therapist.Helper.User;
import com.master.design.therapist.R;
import com.master.design.therapist.Utils.ConnectionDetector;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Term_Privacy_TipsActivity extends AppCompatActivity {

    AppController appController;
    ConnectionDetector connectionDetector;
    User user;
    DialogUtil dialogUtil;
    Dialog progress;
    Context context;

    @BindView(R.id.conditionTxt)
    TextView conditionTxt;
    @BindView(R.id.tittleTxt)
    TextView tittleTxt;
    String terms, policy, tips;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_privacy_tips);
        ButterKnife.bind(this);
        appController = (AppController) getApplicationContext();
        connectionDetector = new ConnectionDetector(Term_Privacy_TipsActivity.this);
        user = new User(Term_Privacy_TipsActivity.this);
        dialogUtil = new DialogUtil();
        context = getApplicationContext();

        Intent intent = getIntent();
        if (intent != null) {
            terms = intent.getStringExtra("terms");
            policy = intent.getStringExtra("policy");
            tips = intent.getStringExtra("tips");

            setTittle();
        }
    }

    private void setTittle() {

        if (terms != null) {
//            tittleTxt.setText(getString(R.string.terms___and_condition));
//            conditionTxt.setText("These Website Standard Terms and Conditions written on this webpage shall manage your use of our website, Webiste Name accessible at Website.com.\n" +
//                    "\n" +
//                    "These Terms will be applied fully and affect to your use of this Website. By using this Website, you agreed to accept all terms and conditions written in here. You must not use this Website if you disagree with any of these Website Standard Terms and Conditions.\n" +
//                    "\n" +
//                    "Minors or people below 18 years old are not allowed to use this Website."+
//            "\n"+"Other than the content you own, under these Terms, Company Name and/or its licensors own all the intellectual property rights and materials contained in this Website.\n" +
//                    "\n" +
//                    "You are granted limited license only for purposes of viewing the material contained on this Website.\n" +
//                    "\n" +
//                    "Restrictions\n" +
//                    "You are specifically restricted from all of the following:\n" +
//                    "\n" +
//                    "publishing any Website material in any other media;\n" +
//                    "selling, sublicensing and/or otherwise commercializing any Website material;\n" +
//                    "publicly performing and/or showing any Website material;\n" +
//                    "using this Website in any way that is or may be damaging to this Website;\n" +
//                    "using this Website in any way that impacts user access to this Website;\n" +
//                    "using this Website contrary to applicable laws and regulations, or in any way may cause harm to the Website, or to any person or business entity;\n" +
//                    "engaging in any data mining, data harvesting, data extracting or any other similar activity in relation to this Website;\n" +
//                    "using this Website to engage in any advertising or marketing.");
            if (connectionDetector.isConnectingToInternet()) {
                String refreshedToken = FirebaseInstanceId.getInstance().getToken();
                progress = dialogUtil.showProgressDialog(Term_Privacy_TipsActivity.this, getString(R.string.please_wait));
                appController.paServices.TherapistTerms_conditions(new Callback<Terms_ConditionsDM>() {
                    @Override
                    public void success(Terms_ConditionsDM terms_conditionsDM, Response response) {
                        progress.dismiss();
                        if (terms_conditionsDM.getStatus().equalsIgnoreCase("1")) {


                            if (user.getLanguageCode().equalsIgnoreCase("en")) {

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    conditionTxt.setText(Html.fromHtml(terms_conditionsDM.getDetails().get(0).getContent(), Html.FROM_HTML_MODE_COMPACT));
                                    tittleTxt.setText(terms_conditionsDM.getDetails().get(0).getTitle());
                                }

                            }else{
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    conditionTxt.setText(Html.fromHtml(terms_conditionsDM.getDetails().get(0).getContent_in_arab(), Html.FROM_HTML_MODE_COMPACT));
                                    tittleTxt.setText(terms_conditionsDM.getDetails().get(0).getTitle_in_arab());
                                }

                            }



                        } else{

                            showdialogNoData(context,getString(R.string.terms),getString(R.string.no_terms_and_condition));
                        }
//                            Helper.showToast(Term_Privacy_TipsActivity.this, getString(R.string.Api_data_not_found));
                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {
                        Log.e("error", retrofitError.toString());
                    }
                });
            } else
                Helper.showToast(Term_Privacy_TipsActivity.this, getString(R.string.no_internet_connection));

        }
        if (policy != null) {
//            tittleTxt.setText(getString(R.string.privacy_policy));

//            conditionTxt.setText("We at Wasai LLC respect the privacy of your personal information and, as such, make every effort to ensure your information is protected and remains private. As the owner and operator of loremipsum.io (the \"Website\") hereafter referred to in this Privacy Policy as \"Lorem Ipsum\", \"us\", \"our\" or \"we\", we have provided this Privacy Policy to explain how we collect, use, share and protect information about the users of our Website (hereafter referred to as “user”, “you” or \"your\"). For the purposes of this Agreement, any use of the terms \"Lorem Ipsum\", \"us\", \"our\" or \"we\" includes Wasai LLC, without limitation. We will not use or share your personal information with anyone except as described in this Privacy Policy.\n" +
//                    "\n" +
//                    "This Privacy Policy will inform you about the types of personal data we collect, the purposes for which we use the data, the ways in which the data is handled and your rights with regard to your personal data. Furthermore, this Privacy Policy is intended to satisfy the obligation of transparency under the EU General Data Protection Regulation 2016/679 (\"GDPR\") and the laws implementing GDPR.\n" +
//                    "\n" +
//                    "For the purpose of this Privacy Policy the Data Controller of personal data is Wasai LLC and our contact details are set out in the Contact section at the end of this Privacy Policy. Data Controller means the natural or legal person who (either alone or jointly or in common with other persons) determines the purposes for which and the manner in which any personal information are, or are to be, processed.\n" +
//                    "\n" +
//                    "For purposes of this Privacy Policy, \"Your Information\" or \"Personal Data\" means information about you, which may be of a confidential or sensitive nature and may include personally identifiable information (\"PII\") and/or financial information. PII means individually identifiable information that would allow us to determine the actual identity of a specific living person, while sensitive data may include information, comments, content and other information that you voluntarily provide.\n" +
//                    "\n" +
//                    "Lorem Ipsum collects information about you when you use our Website to access our services, and other online products and services (collectively, the “Services”) and through other interactions and communications you have with us. The term Services includes, collectively, various applications, websites, widgets, email notifications and other mediums, or portions of such mediums, through which you have accessed this Privacy Policy.\n" +
//                    "\n" +
//                    "We may change this Privacy Policy from time to time. If we decide to change this Privacy Policy, we will inform you by posting the revised Privacy Policy on the Site. Those changes will go into effect on the \"Last updated\" date shown at the end of this Privacy Policy. By continuing to use the Site or Services, you consent to the revised Privacy Policy. We encourage you to periodically review the Privacy Policy for the latest information on our privacy practices.");

            if (connectionDetector.isConnectingToInternet()) {
                String refreshedToken = FirebaseInstanceId.getInstance().getToken();
                progress = dialogUtil.showProgressDialog(Term_Privacy_TipsActivity.this, getString(R.string.please_wait));
                appController.paServices.TherapistPrivacy_Policy(new Callback<Terms_ConditionsDM>() {
                    @Override
                    public void success(Terms_ConditionsDM terms_conditionsDM, Response response) {
                        progress.dismiss();
                        if (terms_conditionsDM.getStatus().equalsIgnoreCase("1")) {


                            if (user.getLanguageCode().equalsIgnoreCase("en")) {

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    conditionTxt.setText(Html.fromHtml(terms_conditionsDM.getDetails().get(0).getContent(), Html.FROM_HTML_MODE_COMPACT));
                                    tittleTxt.setText(terms_conditionsDM.getDetails().get(0).getTitle());
                                }

                            }else{
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    conditionTxt.setText(Html.fromHtml(terms_conditionsDM.getDetails().get(0).getContent_in_arab(), Html.FROM_HTML_MODE_COMPACT));
                                    tittleTxt.setText(terms_conditionsDM.getDetails().get(0).getTitle_in_arab());
                                }

                            }


                        } else{
                            showdialogNoData(context,getString(R.string.privacy_policy),getString(R.string.no_privacy_policy));
                        }
//                            Helper.showToast(Term_Privacy_TipsActivity.this, getString(R.string.Api_data_not_found));
                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {
                        Log.e("error", retrofitError.toString());
                    }
                });
            } else
                Helper.showToast(Term_Privacy_TipsActivity.this, getString(R.string.no_internet_connection));

        }
        if (tips != null) {
            tittleTxt.setText(getString(R.string.tips));
//            conditionTxt.setText("We at Wasai LLC respect the privacy of your personal information and, as such, make every effort to ensure your information is protected and remains private. As the owner and operator of loremipsum.io (the \"Website\") hereafter referred to in this Privacy Policy as \"Lorem Ipsum\", \"us\", \"our\" or \"we\", we have provided this Privacy Policy to explain how we collect, use, share and protect information about the users of our Website (hereafter referred to as “user”, “you” or \"your\"). For the purposes of this Agreement, any use of the terms \"Lorem Ipsum\", \"us\", \"our\" or \"we\" includes Wasai LLC, without limitation. We will not use or share your personal information with anyone except as described in this Privacy Policy.\n" +
//                    "\n" +
//                    "This Privacy Policy will inform you about the types of personal data we collect, the purposes for which we use the data, the ways in which the data is handled and your rights with regard to your personal data. Furthermore, this Privacy Policy is intended to satisfy the obligation of transparency under the EU General Data Protection Regulation 2016/679 (\"GDPR\") and the laws implementing GDPR.\n" +
//                    "\n" +
//                    "For the purpose of this Privacy Policy the Data Controller of personal data is Wasai LLC and our contact details are set out in the Contact section at the end of this Privacy Policy. Data Controller means the natural or legal person who (either alone or jointly or in common with other persons) determines the purposes for which and the manner in which any personal information are, or are to be, processed.\n" +
//                    "\n" +
//                    "For purposes of this Privacy Policy, \"Your Information\" or \"Personal Data\" means information about you, which may be of a confidential or sensitive nature and may include personally identifiable information (\"PII\") and/or financial information. PII means individually identifiable information that would allow us to determine the actual identity of a specific living person, while sensitive data may include information, comments, content and other information that you voluntarily provide.\n" +
//                    "\n" +
//                    "Lorem Ipsum collects information about you when you use our Website to access our services, and other online products and services (collectively, the “Services”) and through other interactions and communications you have with us. The term Services includes, collectively, various applications, websites, widgets, email notifications and other mediums, or portions of such mediums, through which you have accessed this Privacy Policy.\n" +
//                    "\n" +
//                    "We may change this Privacy Policy from time to time. If we decide to change this Privacy Policy, we will inform you by posting the revised Privacy Policy on the Site. Those changes will go into effect on the \"Last updated\" date shown at the end of this Privacy Policy. By continuing to use the Site or Services, you consent to the revised Privacy Policy. We encourage you to periodically review the Privacy Policy for the latest information on our privacy practices.");
//
            if (connectionDetector.isConnectingToInternet()) {
                String refreshedToken = FirebaseInstanceId.getInstance().getToken();
                progress = dialogUtil.showProgressDialog(Term_Privacy_TipsActivity.this, getString(R.string.please_wait));
                appController.paServices.TherapistTips(new Callback<Terms_ConditionsDM>() {
                    @Override
                    public void success(Terms_ConditionsDM terms_conditionsDM, Response response) {
                        progress.dismiss();
                        if (terms_conditionsDM.getStatus().equalsIgnoreCase("1")) {



                            if (user.getLanguageCode().equalsIgnoreCase("en")) {

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    conditionTxt.setText(Html.fromHtml(terms_conditionsDM.getDetails().get(0).getContent(), Html.FROM_HTML_MODE_COMPACT));
                                    tittleTxt.setText(terms_conditionsDM.getDetails().get(0).getTitle());
                                }

                            }else{
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    conditionTxt.setText(Html.fromHtml(terms_conditionsDM.getDetails().get(0).getContent_in_arab(), Html.FROM_HTML_MODE_COMPACT));
                                    tittleTxt.setText(terms_conditionsDM.getDetails().get(0).getTitle_in_arab());
                                }

                            }


                        } else{

                            showdialogNoData(context,getString(R.string.tips),getString(R.string.no_tips));
                        }

                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {
                        Log.e("error", retrofitError.toString());
                    }
                });
            } else
                Helper.showToast(Term_Privacy_TipsActivity.this, getString(R.string.no_internet_connection));


        }
    }

    public void showdialogNoData(Context context, String tittle, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
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