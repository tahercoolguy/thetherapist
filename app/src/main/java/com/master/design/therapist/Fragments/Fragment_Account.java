package com.master.design.therapist.Fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.master.design.therapist.Activity.AboutActivity;
import com.master.design.therapist.Activity.FriendSearchActivity;
import com.master.design.therapist.Activity.LanguageActivity;
import com.master.design.therapist.Activity.MainActivity;
import com.master.design.therapist.Activity.My_ProfileActivity;
import com.master.design.therapist.Activity.Sign_InActivity;
import com.master.design.therapist.Activity.SplashScreen;
import com.master.design.therapist.Controller.AppController;
import com.master.design.therapist.Helper.User;
import com.master.design.therapist.R;
import com.master.design.therapist.Utils.ConnectionDetector;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import it.sephiroth.android.library.widget.HListView;

public class Fragment_Account extends Fragment {

    private View rootView;
    private Context context;
    private Activity activity;

    @BindView(R.id.progress_bar)
    ProgressBar progress_bar;
    @BindView(R.id.txt_error)
    TextView txt_error;
    @BindView(R.id.myProfileLL)
    LinearLayout myProfileLL;
    @BindView(R.id.languageLL)
    LinearLayout languageLL;
    @BindView(R.id.notificationLL)
    LinearLayout notificationLL;
    @BindView(R.id.aboutLL)
    LinearLayout aboutLL;
    @BindView(R.id.logoutLL)
    LinearLayout logoutLL;

    @BindView(R.id.layout_parent)
    LinearLayout layout_parent;
    AppController appController;
    ConnectionDetector connectionDetector;
    ProgressDialog progressDialog;
    User user;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        context = getActivity();
        activity=this.getActivity();
        appController = (AppController) getActivity().getApplicationContext();
        user = new User(context);
        connectionDetector = new ConnectionDetector(getActivity());
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getResources().getString(R.string.please_wait));
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        ((MainActivity) context).setTitle(getString(R.string.home));
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.account_fragment_layout, container, false);
            ButterKnife.bind(this, rootView);

         }
        return rootView;
    }

    @OnClick(R.id.myProfileLL)
    public void clickmyProfileLL() {
        startActivity(new Intent(getActivity(), My_ProfileActivity.class));
        activity.overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);
    }

    @OnClick(R.id.languageLL)
    public void clicklanguageLL() {
        startActivity(new Intent(getActivity(), LanguageActivity.class));
        activity.overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);
    }

    @OnClick(R.id.notificationLL)
    public void clicknotificationLL() {

    }

    @OnClick(R.id.aboutLL)
    public void clickaboutLL() {
        startActivity(new Intent(getActivity(), AboutActivity.class));
        activity.overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);

    }

    @OnClick(R.id.logoutLL)
    public void clicklogoutLL() {
        ((MainActivity)context).finish();
        startActivity(new Intent(getActivity(), Sign_InActivity.class));
//        ((MainActivity)context).finish();
        user.setId(0);
         activity.overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void setDetails() {
        ShowProgress();
        rootView.postDelayed(new Runnable() {
            @Override
            public void run() {
                DismissProgress();
            }
        }, 1500);


    }

    public void ShowProgress() {
        progress_bar.setVisibility(View.VISIBLE);
        txt_error.setVisibility(View.GONE);
        layout_parent.setVisibility(View.GONE);
    }

    public void DismissProgress() {
        progress_bar.setVisibility(View.GONE);
        txt_error.setVisibility(View.GONE);
        layout_parent.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.action_back).setVisible(false);
    }
}
