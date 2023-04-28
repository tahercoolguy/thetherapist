package com.master.design.therapist.Adapter;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.master.design.therapist.DM.MessageChatModel;
import com.master.design.therapist.DataModel.All_messages;
import com.master.design.therapist.Helper.User;
import com.master.design.therapist.R;

import java.util.List;

public class MessageChatAdapter extends RecyclerView.Adapter {

    List<All_messages> messageChatModelList;
    Context context;
    User user;
   String friendId ;
   String Id;
   String message;
   ImageView sendImg;
   MessageChatAdapter.Onitemclicklistener onitemClickListener;

    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;



    public MessageChatAdapter(List<All_messages> messageChatModelList, Context context,String friendId,String message,String Id,ImageView sendImg) {
        this.messageChatModelList = messageChatModelList;
        this.context = context;
        this.friendId=friendId;
        this.Id=Id;
        this.sendImg=sendImg;
        this.message=message;
        user= new User(context);
    }

    // Determines the appropriate ViewType according to the sender of the message.
    @Override
    public int getItemViewType(int position) {
        All_messages message = (All_messages) messageChatModelList.get(position);
        String senderID=message.getSender_user();
        String userID= String.valueOf(user.getId());
        if (userID.equalsIgnoreCase(senderID)) {
            // If the current user is the sender of the message
            Log.e("getItemViewType", "1");
            return VIEW_TYPE_MESSAGE_SENT;
//            Log.e("getItemViewType", "1");
//            return VIEW_TYPE_MESSAGE_RECEIVED;
        } else {
            // If some other user sent the message

            Log.e("getItemViewType", "2");
            return VIEW_TYPE_MESSAGE_RECEIVED;

//            Log.e("getItemViewType", "2");
//            return VIEW_TYPE_MESSAGE_SENT;
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;

        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.send_layout, parent, false);
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    // on the below line we are finishing
                    // our current activity.
                    view.startAnimation(animation);

                }
            }, 100);
            return new SentMessageHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.receive_layout, parent, false);
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    // on the below line we are finishing
                    // our current activity.
                    view.startAnimation(animation);

                }
            }, 100);
            return new ReceivedMessageHolder(view);
        }

        return null;
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        All_messages message = messageChatModelList.get(position);
        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(message);
        }


    }



    @Override
    public int getItemCount() {
        return messageChatModelList.size();
    }

    public void setOnitemClickListener(MessageChatAdapter.Onitemclicklistener onitemClickListener)
    {
        this.onitemClickListener=onitemClickListener;
    }

    private class SentMessageHolder extends RecyclerView.ViewHolder {

        TextView message;
        TextView time;


        public SentMessageHolder(@NonNull View itemView) {
            super(itemView);
            message = (TextView) itemView.findViewById(R.id.message);
            time = (TextView) itemView.findViewById(R.id.time);

        }

        void bind(All_messages messageModel) {

            message.setText(messageModel.getMessage());

            time.setText(messageModel.getTimestamp());


        }

    }

    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView message;
        TextView time;

        public ReceivedMessageHolder(@NonNull View itemView) {
            super(itemView);
            message = (TextView) itemView.findViewById(R.id.message);
            time = (TextView) itemView.findViewById(R.id.time);
        }

        void bind(All_messages messageModel) {
            message.setText(messageModel.getMessage());
            time.setText(messageModel.getTimestamp());


        }
    }

    public interface Onitemclicklistener
    {
       void    fun(String message);
    }
}


