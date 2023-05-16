package com.master.design.therapist.Fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.iid.FirebaseInstanceId;
import com.master.design.therapist.Activity.MainActivity;
import com.master.design.therapist.Activity.Term_Privacy_TipsActivity;
import com.master.design.therapist.Adapter.Adapter_Friends;
import com.master.design.therapist.Adapter.Adapter_Request;
import com.master.design.therapist.Controller.AppController;
import com.master.design.therapist.DM.InterestDM;
import com.master.design.therapist.DataModel.Friend_ListDM;
import com.master.design.therapist.DataModel.Request_ListDM;
import com.master.design.therapist.DataModel.Terms_ConditionsDM;
import com.master.design.therapist.Helper.DialogUtil;
import com.master.design.therapist.Helper.Helper;
import com.master.design.therapist.Helper.User;
import com.master.design.therapist.R;
import com.master.design.therapist.Utils.ConnectionDetector;

import org.jdom2.Text;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import it.sephiroth.android.library.widget.HListView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Fragment_Friends_Request extends Fragment {

    private View rootView;
    private Context context;
    User user;

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
    DialogUtil dialogUtil;
    Dialog progress;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;

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
            dialogUtil = new DialogUtil();
            user = new User(getActivity());
            setMyFriendsAdapter();
            setDetails();
            /*
             * Sets up a SwipeRefreshLayout.OnRefreshListener that is invoked when the user
             * performs a swipe-to-refresh gesture.
             */
            swiperefresh.setOnRefreshListener(
                    new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {

                            if(myfriend_request.equalsIgnoreCase("my_friends")){
                                // This method performs the actual data-refresh operation.
                                // The method calls setRefreshing(false) when it's finished.
                                setMyFriendsAdapter();
                            }else{
                                setRequestAdapter();
                            }

                        }
                    }
            );

        }
        return rootView;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint({"UseCompatLoadingForDrawables", "UseCompatLoadingForColorStateLists"})
    @OnClick(R.id.myFriendsTxt)
    public void clickMyFriends() {
        myfriend_request = "my_friends";
        ShowProgress();
        myFriendsTxt.setBackground(getActivity().getDrawable(R.drawable.rounded_rectangle_white));
        myFriendsTxt.setTextColor(getActivity().getColor(R.color.white));
        myFriendsTxt.setBackgroundTintList(context.getResources().getColorStateList(R.color.colorPrimary));


        requestTxt.setBackground(getActivity().getDrawable(R.drawable.rounded_rectangle_white));
        requestTxt.setTextColor(getActivity().getColor(R.color.black));
        requestTxt.setBackgroundTintList(context.getResources().getColorStateList(R.color.white));

        setMyFriendsAdapter();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint({"UseCompatLoadingForDrawables", "UseCompatLoadingForColorStateLists"})
    @OnClick(R.id.requestTxt)
    public void clickRequestTxt() {
        ShowProgress();
        myfriend_request = "request";
        myFriendsTxt.setBackground(getActivity().getDrawable(R.drawable.rounded_rectangle_white));
        myFriendsTxt.setTextColor(getActivity().getColor(R.color.black));
        myFriendsTxt.setBackgroundTintList(context.getResources().getColorStateList(R.color.white));


        requestTxt.setBackground(getActivity().getDrawable(R.drawable.rounded_rectangle_white));
        requestTxt.setTextColor(getActivity().getColor(R.color.white));
        requestTxt.setBackgroundTintList(context.getResources().getColorStateList(R.color.colorPrimary));

        setRequestAdapter();
    }

    String myfriend_request;

    private void setMyFriendsAdapter() {
//        ArrayList<InterestDM> interestDMArrayList = new ArrayList<>();
//        interestDMArrayList.add(new InterestDM("User Name", R.drawable.img_profile));
//        interestDMArrayList.add(new InterestDM("User Name", R.drawable.img_profile));
//        interestDMArrayList.add(new InterestDM("User Name", R.drawable.img_profile));

        if (connectionDetector.isConnectingToInternet()) {
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
//            progress = dialogUtil.showProgressDialog(getActivity(), getString(R.string.please_wait));
            appController.paServices.TherapistFriend_List(String.valueOf(user.getId()), new Callback<Friend_ListDM>() {
                @Override
                public void success(Friend_ListDM friend_listDM, Response response) {

                    if (friend_listDM.getStatus().equalsIgnoreCase("1")) {
//                        progress.dismiss();
                        DismissProgress();
                        rcvRcv.setVisibility(View.VISIBLE);
                        myfriend_request = "my_friends";
                        swiperefresh.setRefreshing(false);
                        Adapter_Friends adapter_friends = new Adapter_Friends(context, friend_listDM.getAll_friends());
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
                        rcvRcv.setLayoutManager(gridLayoutManager);
                        rcvRcv.setAdapter(adapter_friends);

                    } else {
                        rcvRcv.setVisibility(View.GONE);
                        ((MainActivity) context).showdialogNoData(context, getString(R.string.my_friends), getString(R.string.no_friends));
                        swiperefresh.setRefreshing(false);
                        DismissProgress();
                    }
//                    progress.dismiss();
//                    Helper.showToast(getActivity(), getString(R.string.Api_data_not_found));
                }

                @Override
                public void failure(RetrofitError retrofitError) {
//                    progress.dismiss();
                    Log.e("error", retrofitError.toString());
                }
            });
        } else
            Helper.showToast(getActivity(), getString(R.string.no_internet_connection));


    }

    private void setRequestAdapter() {


        if (connectionDetector.isConnectingToInternet()) {
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
//            progress = dialogUtil.showProgressDialog(getActivity(), getString(R.string.please_wait));
            appController.paServices.TherapistRequest_List(String.valueOf(user.getId()), new Callback<Request_ListDM>() {
                @Override
                public void success(Request_ListDM request_listDM, Response response) {
                    if (request_listDM.getStatus().equalsIgnoreCase("1")) {
//                        progress.dismiss();
                        DismissProgress();
                        myfriend_request = "request";
                        rcvRcv.setVisibility(View.VISIBLE);
                        swiperefresh.setRefreshing(false);
                        Adapter_Request adapter_request = new Adapter_Request(context, request_listDM.getSenders());
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
                        rcvRcv.setLayoutManager(linearLayoutManager);
                        rcvRcv.setAdapter(adapter_request);
                    } else {
                        rcvRcv.setVisibility(View.GONE);
                        swiperefresh.setRefreshing(false);
                        DismissProgress();
//                        progress.dismiss();
//                        Helper.showToast(getActivity(), getString(R.string.Api_data_not_found));

                        ((MainActivity) context).showdialogNoData(context, getString(R.string.friend_request), getString(R.string.no_friend_request));
                    }
                }

                @Override
                public void failure(RetrofitError retrofitError) {
//                    progress.dismiss();
                    Log.e("error", retrofitError.toString());
                }
            });
        } else
            Helper.showToast(getActivity(), getString(R.string.no_internet_connection));


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
        }, 800);


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
