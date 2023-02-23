package com.master.design.therapist.Fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.master.design.therapist.Activity.FriendSearchActivity;
import com.master.design.therapist.Activity.FriendSearch_SelectActivity;
import com.master.design.therapist.Activity.MainActivity;
import com.master.design.therapist.Adapter.Adapter_Category_Interest;
import com.master.design.therapist.Controller.AppController;
import com.master.design.therapist.DM.InterestDM;
import com.master.design.therapist.Helper.BlurBuilder;
import com.master.design.therapist.R;
import com.master.design.therapist.Utils.ConnectionDetector;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import it.sephiroth.android.library.widget.HListView;

public class Fragment_Home extends Fragment {

    private View rootView;
    private Context context;
    private Activity activity;

    @BindView(R.id.progress_bar)
    ProgressBar progress_bar;
    @BindView(R.id.txt_error)
    TextView txt_error;

    @BindView(R.id.sendRequestImg)
    ImageView sendRequestImg;
    @BindView(R.id.nextTxt)
    TextView nextTxt;
    @BindView(R.id.userNameTxt)
    TextView userNameTxt;
    @BindView(R.id.aboutTxt)
    TextView aboutTxt;
    @BindView(R.id.chatImg)
    ImageView chatImg;
    @BindView(R.id.categoryRcv)
    RecyclerView categoryRcv;
    @BindView(R.id.changeSearchTxt)
    TextView changeSearchTxt;
    @BindView(R.id.recieveRequestImg)
    ImageView recieveRequestImg;
    @BindView(R.id.bgRoundedImg)
    RoundedImageView bgRoundedImg;
    @BindView(R.id.frontRoundedImg)
    RoundedImageView frontRoundedImg;

    @BindView(R.id.layout_parent)
    LinearLayout layout_parent;
    private HListView lst_latest_profiles, lst_latest_news, lst_featured_video;
    AppController appController;
    ConnectionDetector connectionDetector;
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        context = getActivity();
        activity = getActivity();
        appController = (AppController) getActivity().getApplicationContext();

        connectionDetector = new ConnectionDetector(getActivity());
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getResources().getString(R.string.please_wait));
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        ((MainActivity) context).setTitle(getString(R.string.home));
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.home_fragment_layout, container, false);
            ButterKnife.bind(this, rootView);
            setInterestData();
            Bitmap resultBmp = BlurBuilder.blur(getActivity(), BitmapFactory.decodeResource(getResources(), R.drawable.marshall_img));
            Drawable dr = new BitmapDrawable(resultBmp);
            bgRoundedImg.setImageDrawable(dr);

        }
        return rootView;
    }

    @OnClick(R.id.sendRequestImg)
    public void clicksendRequestImg() {
        sendRequestImg.setVisibility(View.GONE);
        recieveRequestImg.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.chatImg)
    public void clickchatImg() {

    }

    @OnClick(R.id.nextTxt)
    public void clicknextTxt() {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.left_slide_out);
        layout_parent.startAnimation(animation);
        sendRequestImg.setVisibility(View.VISIBLE);
        recieveRequestImg.setVisibility(View.GONE);
    }

    @OnClick(R.id.changeSearchTxt)
    public void clickchangeSearchTxt() {
        startActivity(new Intent(context, FriendSearchActivity.class));
        activity.overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);
    }

    private void setInterestData() {

        ArrayList<InterestDM> interestDMArrayList = new ArrayList<>();
        interestDMArrayList.add(new InterestDM("Movies", R.drawable.ic_add_image));
        interestDMArrayList.add(new InterestDM("Study", R.drawable.ic_add_image));
        interestDMArrayList.add(new InterestDM("Music", R.drawable.ic_add_image));
        interestDMArrayList.add(new InterestDM("Songs", R.drawable.ic_add_image));
        interestDMArrayList.add(new InterestDM("Cricket", R.drawable.ic_add_image));
        interestDMArrayList.add(new InterestDM("Travel", R.drawable.ic_add_image));
        interestDMArrayList.add(new InterestDM("Pets", R.drawable.ic_add_image));
        interestDMArrayList.add(new InterestDM("Bikes", R.drawable.ic_add_image));
        interestDMArrayList.add(new InterestDM("Movies", R.drawable.ic_add_image));
        interestDMArrayList.add(new InterestDM("Study", R.drawable.ic_add_image));
        interestDMArrayList.add(new InterestDM("Music", R.drawable.ic_add_image));
        interestDMArrayList.add(new InterestDM("Songs", R.drawable.ic_add_image));
        interestDMArrayList.add(new InterestDM("Cricket", R.drawable.ic_add_image));
        interestDMArrayList.add(new InterestDM("Travel", R.drawable.ic_add_image));
        interestDMArrayList.add(new InterestDM("Pets", R.drawable.ic_add_image));
        interestDMArrayList.add(new InterestDM("Bikes", R.drawable.ic_add_image));

        Adapter_Category_Interest adapter_category_interest = new Adapter_Category_Interest(context, interestDMArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        categoryRcv.setLayoutManager(linearLayoutManager);
        categoryRcv.setAdapter(adapter_category_interest);

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
