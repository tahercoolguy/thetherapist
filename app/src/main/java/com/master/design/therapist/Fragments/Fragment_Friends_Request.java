package com.master.design.therapist.Fragments;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.iid.FirebaseInstanceId;
import com.master.design.therapist.Activity.BlockedAccountActivity;
import com.master.design.therapist.Activity.MainActivity;
import com.master.design.therapist.Adapter.Adapter_Friends;
import com.master.design.therapist.Adapter.Adapter_Request;
import com.master.design.therapist.Controller.AppController;
import com.master.design.therapist.DataModel.CommonReasonRoot;
import com.master.design.therapist.DataModel.Friend_ListDM;
import com.master.design.therapist.DataModel.ReportUserRoot;
import com.master.design.therapist.DataModel.Request_ListDM;
import com.master.design.therapist.DataModel.Request_ResponseDM;
import com.master.design.therapist.DataModel.UnfriendDM;
import com.master.design.therapist.DataModel.UserBlockUnblockRoot;
import com.master.design.therapist.Helper.DialogUtil;
import com.master.design.therapist.Helper.Helper;
import com.master.design.therapist.Helper.User;
import com.master.design.therapist.R;
import com.master.design.therapist.Utils.ConnectionDetector;

import org.jetbrains.annotations.Nullable;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import it.sephiroth.android.library.widget.HListView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.MultipartTypedOutput;
import retrofit.mime.TypedString;

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

            setDetails();
            setMyFriendsAdapter();
            myfriend_request = "my_friends";
            /*
             * Sets up a SwipeRefreshLayout.OnRefreshListener that is invoked when the user
             * performs a swipe-to-refresh gesture.
             */
            swiperefresh.setOnRefreshListener(
                    new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {

                            if (myfriend_request.equalsIgnoreCase("my_friends")) {
                                // This method performs the actual data-refresh operation.
                                // The method calls setRefreshing(false) when it's finished.
                                setMyFriendsAdapter();
                            } else {
                                setRequestAdapter();
                            }

                        }
                    }
            );
//            updateDisplay();

        }
        return rootView;
    }


    @TargetApi(Build.VERSION_CODES.M)
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

    @TargetApi(Build.VERSION_CODES.M)
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

                        adapter_friends.setOnItemClickListener(new Adapter_Friends.OnItemClickListener() {
                            @Override
                            public void removeFriend(int position, String userId) {

                                unFriendAPI(userId);
                            }
                        });

                    } else {
                        rcvRcv.setVisibility(View.GONE);
                        swiperefresh.setRefreshing(false);
                        try {
                            showdialogNoData(context, getString(R.string.my_friends), getString(R.string.no_friends));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
//                        ((MainActivity) context).showdialogNoData(context, getString(R.string.my_friends), getString(R.string.no_friends));
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
        } else {
            swiperefresh.setRefreshing(false);
            try {
                showdialogNoData(context, getString(R.string.my_friends), getString(R.string.no_internet_connection));

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
//            Helper.showToast(getActivity(), getString(R.string.no_internet_connection));


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

                        adapter_request.setOnItemClickListener(new Adapter_Request.OnItemClickListener() {
                            @Override
                            public void clickAcceptButton(int position, String id, String response) {

                                acceptRequestAPI(id, response);
                                setRequestAdapter();
                            }


                            @Override
                            public void clickRejectButton(int position, String id, String response) {

                                rejectRequestAPI(id, response);
                                setRequestAdapter();
                            }
                        });


                    } else {
                        rcvRcv.setVisibility(View.GONE);
                        swiperefresh.setRefreshing(false);
                        DismissProgress();
//                        progress.dismiss();
//                        Helper.showToast(getActivity(), getString(R.string.Api_data_not_found));

                        try {
                            showdialogNoData(context, getString(R.string.friend_request), getString(R.string.no_friend_request));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

//                        ((MainActivity) context).showdialogNoData(context, getString(R.string.friend_request), getString(R.string.no_friend_request));
                    }
                }

                @Override
                public void failure(RetrofitError retrofitError) {
//                    progress.dismiss();
                    Log.e("error", retrofitError.toString());
                }
            });
        } else {
            swiperefresh.setRefreshing(false);
            rcvRcv.setVisibility(View.GONE);
            swiperefresh.setRefreshing(false);
            DismissProgress();
            try {
                showdialogNoData(context, getString(R.string.friend_request), getString(R.string.no_internet_connection));

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
//            Helper.showToast(getActivity(), getString(R.string.no_internet_connection));


    private void acceptRequestAPI(String id, String response) {
        if (connectionDetector.isConnectingToInternet()) {
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
//            progress = dialogUtil.showProgressDialog(context, String.valueOf(R.string.please_wait));
            appController.paServices.TherapistRequest_Response(id, String.valueOf(user.getId()), response, new Callback<Request_ResponseDM>() {
                @Override
                public void success(Request_ResponseDM request_responseDM, Response response) {
//                    progress.dismiss();
                    if (request_responseDM.getStatus().equalsIgnoreCase("1")) {
//                        Helper.showToast(context, request_responseDM.getMsg());

                        setRequestAdapter();

                    } else {
//                        Helper.showToast(context, String.valueOf(R.string.Api_data_not_found));
                    }

                }

                @Override
                public void failure(RetrofitError retrofitError) {
//                    progress.dismiss();
                    Log.e("error", retrofitError.toString());
                }
            });
        } else
            Helper.showToast(context, String.valueOf(R.string.no_internet_connection));

    }

    private void rejectRequestAPI(String id, String response) {
        if (connectionDetector.isConnectingToInternet()) {
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
//            progress = dialogUtil.showProgressDialog(context, String.valueOf(R.string.please_wait));
            appController.paServices.TherapistRequest_Response(id, String.valueOf(user.getId()), response, new Callback<Request_ResponseDM>() {
                @Override
                public void success(Request_ResponseDM request_responseDM, Response response) {
//                    progress.dismiss();
                    if (request_responseDM.getStatus().equalsIgnoreCase("1")) {
//                        Helper.showToast(context, request_responseDM.getMsg());
                        setRequestAdapter();

                    } else {
//                        Helper.showToast(context, String.valueOf(R.string.Api_data_not_found));
                    }

                }

                @Override
                public void failure(RetrofitError retrofitError) {
//                    progress.dismiss();
                    Log.e("error", retrofitError.toString());
                }
            });
        } else
            Helper.showToast(context, String.valueOf(R.string.no_internet_connection));


    }


    private void unFriendAPI(String id) {
        AlertDialog.Builder adb = new AlertDialog.Builder(context);
//                adb.setView(alertDialogView);
        adb.setTitle(R.string.are_you_sure_want_to_unfriend);
//                adb.setIcon(android.R.drawable.ic_dialog_alert);
        adb.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {


                if (connectionDetector.isConnectingToInternet()) {
                    String refreshedToken = FirebaseInstanceId.getInstance().getToken();
//                    progress = dialogUtil.showProgressDialog(context, context.getString(R.string.please_wait));
                    appController.paServices.TherapistUnfriend(String.valueOf(user.getId()), id, new Callback<UnfriendDM>() {
                        @Override
                        public void success(UnfriendDM unfriendDM, Response response) {
//                            progress.dismiss();
                            if (unfriendDM.getStatus().equalsIgnoreCase("1")) {
                                setMyFriendsAdapter();
//                                Helper.showToast(context, unfriendDM.getMsg());
//                                ((MainActivity) context).addFragment(new Fragment_Friends_Request(), false);

                            } else {
//                                Helper.showToast(context, unfriendDM.getMsg());
                            }

                        }

                        @Override
                        public void failure(RetrofitError retrofitError) {
//                            progress.dismiss();
                            Log.e("error", retrofitError.toString());
                        }
                    });
                } else {
                    Helper.showToast(context, String.valueOf(R.string.no_internet_connection));
                }

            }
        });
        adb.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //                  ((MainActivity)context).addFragment(new Fragment_Friends_Request(),false);
                //                    adb.setCancelable(true);
                dialog.dismiss();
            }
        });
        adb.show();


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

    private void updateDisplay() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            //@Override
            public void run() {
                try {
                    if (myfriend_request.equalsIgnoreCase("my_friends")) {
                        setMyFriendsAdapter();

                    } else {

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, 1000, 5000);//Update text every second

    }


    private void unblockUser(String unblock_user_id) {
        if (connectionDetector.isConnectingToInternet()) {
            MultipartTypedOutput multipartTypedOutput = new MultipartTypedOutput();
            String id = String.valueOf(user.getId());
            multipartTypedOutput.addPart("user_id", new TypedString(id));
            multipartTypedOutput.addPart("unblock_user_id", new TypedString(unblock_user_id));

//        progress = dialogUtil.showProgressDialog(MainActivity.this, getString(R.string.please_wait));
            appController.paServices.UnblockUser(multipartTypedOutput, new Callback<UserBlockUnblockRoot>() {
                @Override
                public void success(UserBlockUnblockRoot userBlockUnblockRoot, Response response) {
                    if (userBlockUnblockRoot.getStatus().equalsIgnoreCase("1")) {
//                        progress.dismiss();


                    } else {
//                        progress.dismiss();
                    }
                }

                @Override
                public void failure(RetrofitError retrofitError) {
//                    progress.dismiss();
                    Log.e("error", retrofitError.toString());
                }
            });
        } else {
            com.master.design.therapist.Helper.Helper.showToast(getActivity(), String.valueOf(R.string.no_internet_connection));
        }
    }


    private void blockUser(String blocked_user_id) {
        if (connectionDetector.isConnectingToInternet()) {
            MultipartTypedOutput multipartTypedOutput = new MultipartTypedOutput();
            String id = String.valueOf(user.getId());
            multipartTypedOutput.addPart("user_id", new TypedString(id));
            multipartTypedOutput.addPart("blocked_user_id", new TypedString(blocked_user_id));

//        progress = dialogUtil.showProgressDialog(MainActivity.this, getString(R.string.please_wait));
            appController.paServices.UnblockUser(multipartTypedOutput, new Callback<UserBlockUnblockRoot>() {
                @Override
                public void success(UserBlockUnblockRoot userBlockUnblockRoot, Response response) {
                    if (userBlockUnblockRoot.getStatus().equalsIgnoreCase("1")) {
//                        progress.dismiss();


                    } else {
//                        progress.dismiss();
                    }
                }

                @Override
                public void failure(RetrofitError retrofitError) {
//                    progress.dismiss();
                    Log.e("error", retrofitError.toString());
                }
            });
        } else {
            com.master.design.therapist.Helper.Helper.showToast(getActivity(), String.valueOf(R.string.no_internet_connection));
        }
    }

    private void commonReasons() {
        if (connectionDetector.isConnectingToInternet()) {

//        progress = dialogUtil.showProgressDialog(MainActivity.this, getString(R.string.please_wait));
            appController.paServices.CommonReasons(new Callback<CommonReasonRoot>() {
                @Override
                public void success(CommonReasonRoot commonReasonRoot, Response response) {
                    if (commonReasonRoot.getStatus().equalsIgnoreCase("1")) {
//                        progress.dismiss();


                    } else {
//                        progress.dismiss();
                    }
                }

                @Override
                public void failure(RetrofitError retrofitError) {
//                    progress.dismiss();
                    Log.e("error", retrofitError.toString());
                }
            });
        } else {
            com.master.design.therapist.Helper.Helper.showToast(getActivity(), String.valueOf(R.string.no_internet_connection));
        }
    }

    private void reportUser(String reported_user, String reason, String description) {
        if (connectionDetector.isConnectingToInternet()) {
            MultipartTypedOutput multipartTypedOutput = new MultipartTypedOutput();
            String id = String.valueOf(user.getId());
            multipartTypedOutput.addPart("user_id", new TypedString(id));
            multipartTypedOutput.addPart("reported_user", new TypedString(reported_user));
            multipartTypedOutput.addPart("description", new TypedString(description));

//        progress = dialogUtil.showProgressDialog(MainActivity.this, getString(R.string.please_wait));
            appController.paServices.ReportingUser(multipartTypedOutput, new Callback<ReportUserRoot>() {
                @Override
                public void success(ReportUserRoot reportUserRoot, Response response) {
                    if (reportUserRoot.getStatus().equalsIgnoreCase("1")) {
//                        progress.dismiss();


                    } else {
//                        progress.dismiss();
                    }
                }

                @Override
                public void failure(RetrofitError retrofitError) {
//                    progress.dismiss();
                    Log.e("error", retrofitError.toString());
                }
            });
        } else {
            com.master.design.therapist.Helper.Helper.showToast(getActivity(), String.valueOf(R.string.no_internet_connection));
        }
    }
}
