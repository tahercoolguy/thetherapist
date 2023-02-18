package com.master.design.therapist.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
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

import com.master.design.therapist.Activity.MainActivity;
import com.master.design.therapist.Adapter.Adapter_Chat;
import com.master.design.therapist.Controller.AppController;
import com.master.design.therapist.DM.ChatDM;
import com.master.design.therapist.R;
import com.master.design.therapist.Utils.ConnectionDetector;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import it.sephiroth.android.library.widget.HListView;

public class Fragment_Chat extends Fragment {

    private View rootView;
    private Context context;

    @BindView(R.id.progress_bar)
    ProgressBar progress_bar;
    @BindView(R.id.txt_error)
    TextView txt_error;

    @BindView(R.id.searchET)
    EditText searchET;
    @BindView(R.id.rcvRcv)
    RecyclerView rcvRcv;

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
            rootView = inflater.inflate(R.layout.chat_fragment_layout, container, false);
            ButterKnife.bind(this, rootView);
            
            setChatData();

        }
        return rootView;
    }

    private void setChatData() {

        ArrayList<ChatDM>chatDMArrayList= new ArrayList<>();
        chatDMArrayList.add(new ChatDM("Rachel","10:36 PM","5","Today I work in a cafe. Come there, I’ll buy you",R.drawable.img_profile));
        chatDMArrayList.add(new ChatDM("Dude","01:03 AM","0","How are you ?",R.drawable.img_profile));
        chatDMArrayList.add(new ChatDM("Rachel","10:36 PM","5","Today I work in a cafe. Come there, I’ll buy you",R.drawable.img_profile));
        chatDMArrayList.add(new ChatDM("Dude","Fri","0","How are you ?",R.drawable.img_profile));
        chatDMArrayList.add(new ChatDM("Rachel","Sat","3","Today I work in a cafe. Come there, I’ll buy you",R.drawable.img_profile));
        chatDMArrayList.add(new ChatDM("Dude","Sun","0","How are you ?",R.drawable.img_profile));
        chatDMArrayList.add(new ChatDM("Rachel","10:36 PM","1","Today I work in a cafe. Come there, I’ll buy you",R.drawable.img_profile));
        chatDMArrayList.add(new ChatDM("Dude","01:03 AM","0","How are you ?",R.drawable.img_profile));
        chatDMArrayList.add(new ChatDM("Rachel","10:36 PM","7","Today I work in a cafe. Come there, I’ll buy you",R.drawable.img_profile));
        chatDMArrayList.add(new ChatDM("Dude","01:03 AM","4","How are you ?",R.drawable.img_profile));
        chatDMArrayList.add(new ChatDM("Rachel","10:36 PM","6","Today I work in a cafe. Come there, I’ll buy you",R.drawable.img_profile));
        chatDMArrayList.add(new ChatDM("Dude","01:03 AM","0","How are you ?",R.drawable.img_profile));
        chatDMArrayList.add(new ChatDM("Rachel","10:36 PM","2","Today I work in a cafe. Come there, I’ll buy you",R.drawable.img_profile));
        chatDMArrayList.add(new ChatDM("Dude","01:03 AM","0","How are you ?",R.drawable.img_profile));
        chatDMArrayList.add(new ChatDM("Rachel","10:36 PM","5","Today I work in a cafe. Come there, I’ll buy you",R.drawable.img_profile));
        chatDMArrayList.add(new ChatDM("Dude","01:03 AM","0","How are you ?",R.drawable.img_profile));
        chatDMArrayList.add(new ChatDM("Rachel","10:36 PM","5","Today I work in a cafe. Come there, I’ll buy you",R.drawable.img_profile));
        chatDMArrayList.add(new ChatDM("Dude","01:03 AM","0","How are you ?",R.drawable.img_profile));


        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(context);
        Adapter_Chat adapter_chat= new Adapter_Chat(context,chatDMArrayList);
        rcvRcv.setLayoutManager(linearLayoutManager);
        rcvRcv.setAdapter(adapter_chat);

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
