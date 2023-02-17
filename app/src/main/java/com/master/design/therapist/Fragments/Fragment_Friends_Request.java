package com.master.design.therapist.Fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.master.design.therapist.Activity.MainActivity;
import com.master.design.therapist.Adapter.Adapter_Friends;
import com.master.design.therapist.Adapter.Adapter_Request;
import com.master.design.therapist.Controller.AppController;
import com.master.design.therapist.DM.InterestDM;
import com.master.design.therapist.R;
import com.master.design.therapist.Utils.ConnectionDetector;

import org.jdom2.Text;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import it.sephiroth.android.library.widget.HListView;

public class Fragment_Friends_Request extends Fragment {

    private View rootView;
    private Context context;

    @BindView(R.id.progress_bar)
    ProgressBar progress_bar;
    @BindView(R.id.txt_error)
    TextView txt_error;
    @BindView(R.id.rcvRcv)
    RecyclerView rcvRcv;
    @BindView(R.id.myFriendsTxt)
    TextView myFriendsTxt;
    @BindView(R.id.requestTxt)
    TextView requestTxt;

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
        appController = (AppController) getActivity().getApplicationContext();

        connectionDetector = new ConnectionDetector(getActivity());
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getResources().getString(R.string.please_wait));
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        ((MainActivity) context).setTitle(getString(R.string.home));
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.friends_request_fragment_layout, container, false);
            ButterKnife.bind(this, rootView);
            setMyFriendsAdapter();
        }
        return rootView;
    }


    @SuppressLint({"UseCompatLoadingForDrawables", "UseCompatLoadingForColorStateLists"})
    @OnClick(R.id.myFriendsTxt)
    public void clickMyFriends() {


        myFriendsTxt.setBackground(getActivity().getDrawable(R.drawable.rounded_rectangle_white));
        myFriendsTxt.setTextColor(getActivity().getColor(R.color.white));
        myFriendsTxt.setBackgroundTintList(context.getResources().getColorStateList(R.color.colorPrimary));


        requestTxt.setBackground(getActivity().getDrawable(R.drawable.rounded_rectangle_white));
        requestTxt.setTextColor(getActivity().getColor(R.color.black));
        requestTxt.setBackgroundTintList(context.getResources().getColorStateList(R.color.white));

        setMyFriendsAdapter();
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "UseCompatLoadingForColorStateLists"})
    @OnClick(R.id.requestTxt)
    public void clickRequestTxt() {

        myFriendsTxt.setBackground(getActivity().getDrawable(R.drawable.rounded_rectangle_white));
        myFriendsTxt.setTextColor(getActivity().getColor(R.color.black));
        myFriendsTxt.setBackgroundTintList(context.getResources().getColorStateList(R.color.white));


        requestTxt.setBackground(getActivity().getDrawable(R.drawable.rounded_rectangle_white));
        requestTxt.setTextColor(getActivity().getColor(R.color.white));
        requestTxt.setBackgroundTintList(context.getResources().getColorStateList(R.color.colorPrimary));

        setRequestAdapter();
    }


    private void setMyFriendsAdapter() {
        ArrayList<InterestDM> interestDMArrayList = new ArrayList<>();
        interestDMArrayList.add(new InterestDM("User Name", R.drawable.img_profile));
        interestDMArrayList.add(new InterestDM("User Name", R.drawable.img_profile));
        interestDMArrayList.add(new InterestDM("User Name", R.drawable.img_profile));
        interestDMArrayList.add(new InterestDM("User Name", R.drawable.img_profile));
        interestDMArrayList.add(new InterestDM("User Name", R.drawable.img_profile));
        interestDMArrayList.add(new InterestDM("User Name", R.drawable.img_profile));
        interestDMArrayList.add(new InterestDM("User Name", R.drawable.img_profile));
        interestDMArrayList.add(new InterestDM("User Name", R.drawable.img_profile));
        interestDMArrayList.add(new InterestDM("User Name", R.drawable.img_profile));
        interestDMArrayList.add(new InterestDM("User Name", R.drawable.img_profile));
        interestDMArrayList.add(new InterestDM("User Name", R.drawable.img_profile));
        interestDMArrayList.add(new InterestDM("User Name", R.drawable.img_profile));
        interestDMArrayList.add(new InterestDM("User Name", R.drawable.img_profile));
        interestDMArrayList.add(new InterestDM("User Name", R.drawable.img_profile));
        interestDMArrayList.add(new InterestDM("User Name", R.drawable.img_profile));
        interestDMArrayList.add(new InterestDM("User Name", R.drawable.img_profile));
        interestDMArrayList.add(new InterestDM("User Name", R.drawable.img_profile));
        interestDMArrayList.add(new InterestDM("User Name", R.drawable.img_profile));
        interestDMArrayList.add(new InterestDM("User Name", R.drawable.img_profile));
        interestDMArrayList.add(new InterestDM("User Name", R.drawable.img_profile));
        interestDMArrayList.add(new InterestDM("User Name", R.drawable.img_profile));
        interestDMArrayList.add(new InterestDM("User Name", R.drawable.img_profile));
        interestDMArrayList.add(new InterestDM("User Name", R.drawable.img_profile));
        interestDMArrayList.add(new InterestDM("User Name", R.drawable.img_profile));
        interestDMArrayList.add(new InterestDM("User Name", R.drawable.img_profile));

        Adapter_Friends adapter_friends = new Adapter_Friends(context, interestDMArrayList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
        rcvRcv.setLayoutManager(gridLayoutManager);
        rcvRcv.setAdapter(adapter_friends);
    }

    private void setRequestAdapter() {
        ArrayList<InterestDM> interestDMArrayList = new ArrayList<>();
        interestDMArrayList.add(new InterestDM("User Name", R.drawable.img_profile));
        interestDMArrayList.add(new InterestDM("User Name", R.drawable.img_profile));
        interestDMArrayList.add(new InterestDM("User Name", R.drawable.img_profile));
        interestDMArrayList.add(new InterestDM("User Name", R.drawable.img_profile));
        interestDMArrayList.add(new InterestDM("User Name", R.drawable.img_profile));
        interestDMArrayList.add(new InterestDM("User Name", R.drawable.img_profile));
        interestDMArrayList.add(new InterestDM("User Name", R.drawable.img_profile));
        interestDMArrayList.add(new InterestDM("User Name", R.drawable.img_profile));
        interestDMArrayList.add(new InterestDM("User Name", R.drawable.img_profile));
        interestDMArrayList.add(new InterestDM("User Name", R.drawable.img_profile));
        interestDMArrayList.add(new InterestDM("User Name", R.drawable.img_profile));
        interestDMArrayList.add(new InterestDM("User Name", R.drawable.img_profile));
        interestDMArrayList.add(new InterestDM("User Name", R.drawable.img_profile));
        interestDMArrayList.add(new InterestDM("User Name", R.drawable.img_profile));
        interestDMArrayList.add(new InterestDM("User Name", R.drawable.img_profile));
        interestDMArrayList.add(new InterestDM("User Name", R.drawable.img_profile));
        interestDMArrayList.add(new InterestDM("User Name", R.drawable.img_profile));
        interestDMArrayList.add(new InterestDM("User Name", R.drawable.img_profile));
        interestDMArrayList.add(new InterestDM("User Name", R.drawable.img_profile));
        interestDMArrayList.add(new InterestDM("User Name", R.drawable.img_profile));
        interestDMArrayList.add(new InterestDM("User Name", R.drawable.img_profile));
        interestDMArrayList.add(new InterestDM("User Name", R.drawable.img_profile));
        interestDMArrayList.add(new InterestDM("User Name", R.drawable.img_profile));
        interestDMArrayList.add(new InterestDM("User Name", R.drawable.img_profile));
        interestDMArrayList.add(new InterestDM("User Name", R.drawable.img_profile));

        Adapter_Request adapter_request = new Adapter_Request(context, interestDMArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL,false);
        rcvRcv.setLayoutManager(linearLayoutManager);
        rcvRcv.setAdapter(adapter_request);
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
