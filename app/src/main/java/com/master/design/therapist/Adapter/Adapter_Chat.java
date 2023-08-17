package com.master.design.therapist.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.master.design.therapist.Controller.AppController;
import com.master.design.therapist.DM.ChatDM;
import com.master.design.therapist.DataModel.Details;
import com.master.design.therapist.Helper.User;
import com.master.design.therapist.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import io.reactivex.annotations.NonNull;


public class Adapter_Chat extends RecyclerView.Adapter<Adapter_Chat.ViewHolder> {
    private Context context;
    private ArrayList<Details> arrayList;
    private ChatDM selected;
    User user;
    Adapter_Chat.OnItemClickListener onItemClickListener;
    String onlineStatus, latestmessage = "";
    int  messagecount=0;

    int selectedPosition = 0;

    public Adapter_Chat(Context context, ArrayList<Details> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        user = new User(context);

    }


    @NonNull
    @Override
    public Adapter_Chat.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custum_item_chat, parent, false);
        // set the view's size, margins, paddings and layout parameters
        Adapter_Chat.ViewHolder vh = new Adapter_Chat.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Chat.ViewHolder holder, int position) {
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


    private void setDetails(Adapter_Chat.ViewHolder viewHolder, int position) {

//        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                // on the below line we are finishing
//                // our current activity.
//                viewHolder.itemView.startAnimation(animation);
//
//            }
//        }, 100);


//        viewHolder.messageTxt.setText(arrayList.get(position).getMesseage());
//        viewHolder.messegeCountTxt.setText(arrayList.get(position).getMesseageCount());
//        viewHolder.timeTxt.setText(arrayList.get(position).getTime());
//        viewHolder.userNameTxt.setText(arrayList.get(position).getFront_user().getName());
//        viewHolder.timeTxt.setText(arrayList.get(position).getTimestamp());
//        viewHolder.messageTxt.setText(arrayList.get(position).getLatest_message());
        //       viewHolder.profileRIV.setImageResource(arrayList.get(position).getImage());

        if (user.getLanguageCode().equalsIgnoreCase("en")) {
            viewHolder.userNameTxt.setText(arrayList.get(position).getFront_user().getName());
            viewHolder.timeTxt.setText(arrayList.get(position).getTimestamp());
            viewHolder.messageTxt.setText(arrayList.get(position).getLatest_message());
        }else{
            viewHolder.userNameTxt.setText(arrayList.get(position).getFront_user().getName());
            viewHolder.timeTxt.setText(arrayList.get(position).getTimestamp());
            viewHolder.messageTxt.setText(arrayList.get(position).getLatest_message());
        }

        Picasso.with(context).load(AppController.THERAPIST_IMAGE + arrayList.get(position).getFront_user().getImage()).into(viewHolder.profileRIV);
//        if (arrayList.get(position).getMesseageCount().equalsIgnoreCase("0")) {
//            viewHolder.messegeCountTxt.setVisibility(View.GONE);
//        }

        messagecount = Integer.parseInt(arrayList.get(position).getMessage_count());
        if (messagecount>0) {
            viewHolder.messegeCountTxt.setVisibility(View.VISIBLE);
            viewHolder.messegeCountTxt.setText(arrayList.get(position).getMessage_count());

        } else {
            viewHolder.messegeCountTxt.setVisibility(View.GONE);
        }

        latestmessage = arrayList.get(position).getLatest_message();
        if (!latestmessage.equalsIgnoreCase("")) {
            if (latestmessage.contains(".jpg") || latestmessage.contains(".png")) {
                viewHolder.messageTxt.setText(R.string.sent_a_image);
            } else {
                viewHolder.messageTxt.setText(latestmessage);
            }
        } else {
            viewHolder.messageTxt.setText("");
        }


        onlineStatus = arrayList.get(position).getOnline_status();
        if (onlineStatus.equalsIgnoreCase("true")) {
            viewHolder.activeStatusRL.setVisibility(View.VISIBLE);
        } else {
            viewHolder.activeStatusRL.setVisibility(View.GONE);
        }





        viewHolder.clickLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onClickThis(position, AppController.THERAPIST_IMAGE + arrayList.get(position).getFront_user().getImage(), arrayList.get(position).getFront_user().getName(), arrayList.get(position).getFront_user().getId(), arrayList.get(position).getRoom_id());

            }
        });
    }

    // method for filtering our recyclerview items.
    public void filterList(ArrayList<Details> filterlist) {
        // below line is to add our filtered
        // list in our course array list.
        arrayList = filterlist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(Adapter_Chat.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public ChatDM getSelected() {
        return selected;
    }

    public void setSelected(ChatDM selected) {
        this.selected = selected;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView userNameTxt, timeTxt, messageTxt, messegeCountTxt;
        private LinearLayout clickLL;
        private RelativeLayout activeStatusRL;
        private RoundedImageView profileRIV;

        public ViewHolder(View itemView) {
            super(itemView);
            userNameTxt = itemView.findViewById(R.id.userNameTxt);
            profileRIV = itemView.findViewById(R.id.profileRIV);
            clickLL = itemView.findViewById(R.id.clickLL);
            timeTxt = itemView.findViewById(R.id.timeTxt);
            messageTxt = itemView.findViewById(R.id.messageTxt);
            messegeCountTxt = itemView.findViewById(R.id.messegeCountTxt);
            activeStatusRL = itemView.findViewById(R.id.activeStatusRL);

        }
    }

    public interface OnItemClickListener {

        void onClickThis(int position, String img, String name, String FriendId, String RoomID);
    }
}
