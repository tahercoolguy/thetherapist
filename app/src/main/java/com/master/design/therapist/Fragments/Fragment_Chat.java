package com.master.design.therapist.Fragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.iid.FirebaseInstanceId;
import com.master.design.therapist.Activity.Conversation_Activity;
import com.master.design.therapist.Activity.Create_Account_Activity;
import com.master.design.therapist.Activity.FriendSearch_SelectActivity;
import com.master.design.therapist.Activity.MainActivity;
import com.master.design.therapist.Activity.My_ProfileActivity;
import com.master.design.therapist.Adapter.Adapter_Chat;
import com.master.design.therapist.Controller.AppController;
import com.master.design.therapist.DM.ChatDM;
import com.master.design.therapist.DataModel.ChatlistDM;
import com.master.design.therapist.DataModel.Friend_ListDM;
import com.master.design.therapist.Helper.DialogUtil;
import com.master.design.therapist.Helper.Helper;
import com.master.design.therapist.Helper.User;
import com.master.design.therapist.R;
import com.master.design.therapist.Utils.ConnectionDetector;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import it.sephiroth.android.library.widget.HListView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Fragment_Chat extends Fragment {

    private View rootView;
    private Context context;
    private Activity activity;

    @BindView(R.id.progress_bar)
    ProgressBar progress_bar;
    @BindView(R.id.txt_error)
    TextView txt_error;

    @BindView(R.id.searchET)
    EditText searchET;
    @BindView(R.id.rcvRcv)
    RecyclerView rcvRcv;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;
    User user;

    @BindView(R.id.layout_parent)
    LinearLayout layout_parent;
    private HListView lst_latest_profiles, lst_latest_news, lst_featured_video;
    AppController appController;
    ConnectionDetector connectionDetector;
    ProgressDialog progressDialog;
    DialogUtil dialogUtil;
    Dialog progress;

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
            rootView = inflater.inflate(R.layout.chat_fragment_layout, container, false);
            ButterKnife.bind(this, rootView);
            dialogUtil = new DialogUtil();
            user = new User(getActivity());
            setDetails();
            setChatData();

            /*
             * Sets up a SwipeRefreshLayout.OnRefreshListener that is invoked when the user
             * performs a swipe-to-refresh gesture.
             */
            swiperefresh.setOnRefreshListener(
                    new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {

                            // This method performs the actual data-refresh operation.
                            // The method calls setRefreshing(false) when it's finished.
                            setChatData();
                        }
                    }
            );

        }
        return rootView;
    }

    private void setChatData() {

//        ArrayList<ChatDM> chatDMArrayList = new ArrayList<>();
//        chatDMArrayList.add(new ChatDM("Rachel", "10:36 PM", "5", "Today I work in a cafe. Come there, I’ll buy you", R.drawable.img_profile));
//        chatDMArrayList.add(new ChatDM("Dude", "01:03 AM", "0", "How are you ?", R.drawable.img_profile));
//        chatDMArrayList.add(new ChatDM("Rachel", "10:36 PM", "5", "Today I work in a cafe. Come there, I’ll buy you", R.drawable.img_profile));
//        chatDMArrayList.add(new ChatDM("Dude", "Fri", "0", "How are you ?", R.drawable.img_profile));
//        chatDMArrayList.add(new ChatDM("Rachel", "Sat", "3", "Today I work in a cafe. Come there, I’ll buy you", R.drawable.img_profile));
//        chatDMArrayList.add(new ChatDM("Dude", "Sun", "0", "How are you ?", R.drawable.img_profile));
//        chatDMArrayList.add(new ChatDM("Rachel", "10:36 PM", "1", "Today I work in a cafe. Come there, I’ll buy you", R.drawable.img_profile));
//        chatDMArrayList.add(new ChatDM("Dude", "01:03 AM", "0", "How are you ?", R.drawable.img_profile));
//        chatDMArrayList.add(new ChatDM("Rachel", "10:36 PM", "7", "Today I work in a cafe. Come there, I’ll buy you", R.drawable.img_profile));
//        chatDMArrayList.add(new ChatDM("Dude", "01:03 AM", "4", "How are you ?", R.drawable.img_profile));
//        chatDMArrayList.add(new ChatDM("Rachel", "10:36 PM", "6", "Today I work in a cafe. Come there, I’ll buy you", R.drawable.img_profile));
//        chatDMArrayList.add(new ChatDM("Dude", "01:03 AM", "0", "How are you ?", R.drawable.img_profile));
//        chatDMArrayList.add(new ChatDM("Rachel", "10:36 PM", "2", "Today I work in a cafe. Come there, I’ll buy you", R.drawable.img_profile));
//        chatDMArrayList.add(new ChatDM("Dude", "01:03 AM", "0", "How are you ?", R.drawable.img_profile));
//        chatDMArrayList.add(new ChatDM("Rachel", "10:36 PM", "5", "Today I work in a cafe. Come there, I’ll buy you", R.drawable.img_profile));
//        chatDMArrayList.add(new ChatDM("Dude", "01:03 AM", "0", "How are you ?", R.drawable.img_profile));
//        chatDMArrayList.add(new ChatDM("Rachel", "10:36 PM", "5", "Today I work in a cafe. Come there, I’ll buy you", R.drawable.img_profile));
//        chatDMArrayList.add(new ChatDM("Dude", "01:03 AM", "0", "How are you ?", R.drawable.img_profile));


        if (connectionDetector.isConnectingToInternet()) {
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
//            progress = dialogUtil.showProgressDialog(getActivity(), getString(R.string.please_wait));
            appController.paServices.TherapistChatList(String.valueOf(user.getId()), new Callback<ChatlistDM>() {
                @Override
                public void success(ChatlistDM chatlistDM, Response response) {
//                    progress.dismiss();
                    if (chatlistDM.getStatus().equalsIgnoreCase("1")) {

                        rcvRcv.setVisibility(View.VISIBLE);
                        swiperefresh.setRefreshing(false);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                        Adapter_Chat adapter_chat = new Adapter_Chat(context, chatlistDM.getDetails());
                        rcvRcv.setLayoutManager(linearLayoutManager);
                        rcvRcv.setAdapter(adapter_chat);


                        adapter_chat.setOnItemClickListener(new Adapter_Chat.OnItemClickListener() {
                            @Override
                            public void onClickThis(int position, String img, String name, String FriendIddd,String roomID) {
                                Intent intent = new Intent(getActivity(), Conversation_Activity.class);
                                intent.putExtra("Name", name);
                                intent.putExtra("image", img);
                                intent.putExtra("FriendId", FriendIddd);
                                intent.putExtra("chatRoomID", roomID);

                                startActivity(intent);
//                startActivity(new Intent(getActivity(), Conversation_Activity.class));
                                activity.overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);
                            }
                        });

                    } else {
                        rcvRcv.setVisibility(View.GONE);
                        swiperefresh.setRefreshing(false);
                        ((MainActivity) context).showdialogNoData(context, getString(R.string.chat), getString(R.string.no_chats));
                    }
//                        Helper.showToast(getActivity(), getString(R.string.Api_data_not_found));
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
