package com.master.design.therapist.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.makeramen.roundedimageview.RoundedImageView;
import com.master.design.therapist.Activity.Conversation_Activity;
import com.master.design.therapist.Controller.AppController;
import com.master.design.therapist.DM.InterestDM;
import com.master.design.therapist.DataModel.BlockedUserDetail;
import com.master.design.therapist.DataModel.ChatroomDM;
import com.master.design.therapist.Helper.DialogUtil;
import com.master.design.therapist.Helper.Helper;
import com.master.design.therapist.Helper.User;
import com.master.design.therapist.R;
import com.master.design.therapist.Utils.ConnectionDetector;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import io.reactivex.annotations.NonNull;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class Adapter_Blocked_Accounts extends RecyclerView.Adapter<Adapter_Blocked_Accounts.ViewHolder> {
    private Context context;
    private ArrayList<BlockedUserDetail> arrayList;
    private BlockedUserDetail selected;
    User user;

    Dialog progress;
    DialogUtil dialogUtil;

    AppController appController;
    ConnectionDetector connectionDetector;
    Adapter_Blocked_Accounts.OnItemClickListener onItemClickListener;


    int selectedPosition = 0;

    public Adapter_Blocked_Accounts(Context context, ArrayList<BlockedUserDetail> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        user = new User(context);
        dialogUtil = new DialogUtil();
        appController = (AppController) context.getApplicationContext();
        connectionDetector = new ConnectionDetector(context);


    }


    @NonNull
    @Override
    public Adapter_Blocked_Accounts.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custum_item_friends, parent, false);
        // set the view's size, margins, paddings and layout parameters
        Adapter_Blocked_Accounts.ViewHolder vh = new Adapter_Blocked_Accounts.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Blocked_Accounts.ViewHolder holder, int position) {
        setDetails(holder, position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    private void setDetails(Adapter_Blocked_Accounts.ViewHolder viewHolder, int position) {

        viewHolder.unfriendRL.setVisibility(View.GONE);
        viewHolder.blockRL.setVisibility(View.VISIBLE);
        viewHolder.unblockImgButton.setVisibility(View.VISIBLE);

        viewHolder.nameTxt.setText(arrayList.get(position).getBlocked_user().getName());
        viewHolder.nameTxt.setText(arrayList.get(position).getBlocked_user().getName());


        Picasso.with(context).load(AppController.THERAPIST_IMAGE + arrayList.get(position).getBlocked_user()).into(viewHolder.profileImageRIV);

        viewHolder.clickLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        viewHolder.unblockImgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.unblockFriend(position, arrayList.get(position).getBlocked_user().getId());

            }
        });


    }

    public void setOnItemClickListener(Adapter_Blocked_Accounts.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public BlockedUserDetail getSelected() {
        return selected;
    }

    public void setSelected(BlockedUserDetail selected) {
        this.selected = selected;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTxt;
        private LinearLayout clickLL;
        private RoundedImageView profileImageRIV;
        private ImageButton removeFriendImgBtn, unblockImgButton;
        private RelativeLayout blockRL, unfriendRL;
//        ImageView uunfriendIV;

        public ViewHolder(View itemView) {
            super(itemView);
            profileImageRIV = itemView.findViewById(R.id.profileImageRIV);
            clickLL = itemView.findViewById(R.id.clickLL);
            nameTxt = itemView.findViewById(R.id.nameTxt);
            removeFriendImgBtn = itemView.findViewById(R.id.removeFriendImgBtn);
            blockRL = itemView.findViewById(R.id.blockRL);
            unblockImgButton = itemView.findViewById(R.id.unblockImgButton);
            unfriendRL = itemView.findViewById(R.id.unfriendRL);
//            uunfriendIV = itemView.findViewById(R.id.unfriend);


        }
    }

    public interface OnItemClickListener {

        void unblockFriend(int position, String userId);
    }
}
