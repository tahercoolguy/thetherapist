package com.master.design.therapist.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.makeramen.roundedimageview.RoundedImageView;
import com.master.design.therapist.Adapter.DataModel.All_messages;
import com.master.design.therapist.Helper.User;
import com.master.design.therapist.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MessageChatAdapter extends RecyclerView.Adapter {

    public List<All_messages> messageChatModelList;
    Context context;
    User user;
    String friendId;
    String status;

    MessageChatAdapter.Onitemclicklistener onitemClickListener;

    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;


    public MessageChatAdapter(List<All_messages> messageChatModelList, Context context, String friendId, String status) {
        this.messageChatModelList = messageChatModelList;
        this.context = context;
        this.friendId = friendId;
        this.status = status;
        user = new User(context);
    }

    // Determines the appropriate ViewType according to the sender of the message.
    @Override
    public int getItemViewType(int position) {
        All_messages message = (All_messages) messageChatModelList.get(position);
        String senderID = message.getSender_user_id();
        String userID = String.valueOf(user.getId());
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
//            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//
//                    // on the below line we are finishing
//                    // our current activity.
//                    view.startAnimation(animation);
//
//                }
//            }, 100);
            return new SentMessageHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.receive_layout, parent, false);
//            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//
//                    // on the below line we are finishing
//                    // our current activity.
//                    view.startAnimation(animation);
//
//                }
//            }, 100);
            return new ReceivedMessageHolder(view);
        }

        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        All_messages message = messageChatModelList.get(position);
        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(message, holder, holder);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(message, holder);
        }
        setDetails(holder, position);

    }

    private void setDetails(RecyclerView.ViewHolder holder, int position) {

        holder.itemView.findViewById(R.id.sendimageRIV).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onitemClickListener.openImage(messageChatModelList.get(position).getImage_url());
            }
        });
    }


    @Override
    public int getItemCount() {
        return messageChatModelList.size();
    }

    public void setOnitemClickListener(MessageChatAdapter.Onitemclicklistener onitemClickListener) {
        this.onitemClickListener = onitemClickListener;
    }

    public class SentMessageHolder extends RecyclerView.ViewHolder {

        TextView message;
        TextView time;
        ImageView image1, image2;
        RoundedImageView sendimageRIV;
        RelativeLayout senderRL;


        public SentMessageHolder(@NonNull View itemView) {
            super(itemView);
            message = (TextView) itemView.findViewById(R.id.message);
            time = (TextView) itemView.findViewById(R.id.time);
            image1 = (ImageView) itemView.findViewById(R.id.one_right_image);
            image2 = (ImageView) itemView.findViewById(R.id.two_right_image);
            sendimageRIV = (RoundedImageView) itemView.findViewById(R.id.sendimageRIV);
            senderRL = (RelativeLayout) itemView.findViewById(R.id.senderRL);


        }

        void bind(All_messages messageModel, RecyclerView.ViewHolder holder, RecyclerView.ViewHolder viewHolder) {

            if (messageModel.getImage_url() != null) {
                if (!messageModel.getImage_url().equalsIgnoreCase("")) {
                    if (messageModel.getImage_url().contains(".jpg") || messageModel.getImage_url().contains(".png")) {
                        sendimageRIV.setVisibility(View.VISIBLE);
                        message.setVisibility(View.GONE);
                        Picasso.with(context).load(messageModel.getImage_url()).into(sendimageRIV);
//                    senderRL.setVisibility(View.GONE);
                        time.setText(messageModel.getTimestamp());
                        if (messageModel.getStatus().equalsIgnoreCase("send")) {
                            image1.setVisibility(View.VISIBLE);
                            image2.setVisibility(View.GONE);
                        } else {
                            image2.setVisibility(View.VISIBLE);
                            image1.setVisibility(View.GONE);
                        }
                    } else {
                        senderRL.setVisibility(View.VISIBLE);
                        sendimageRIV.setVisibility(View.GONE);
                        message.setVisibility(View.VISIBLE);

                        message.setText(messageModel.getMessage());

                        time.setText(messageModel.getTimestamp());
                        if (messageModel.getStatus().equalsIgnoreCase("send")) {
                            image1.setVisibility(View.VISIBLE);
                            image2.setVisibility(View.GONE);
                        } else {
                            image2.setVisibility(View.VISIBLE);
                            image1.setVisibility(View.GONE);
                        }
                    }
                } else {
                    senderRL.setVisibility(View.VISIBLE);
                    sendimageRIV.setVisibility(View.GONE);
                    message.setVisibility(View.VISIBLE);

                    message.setText(messageModel.getMessage());

                    time.setText(messageModel.getTimestamp());
                    if (messageModel.getStatus().equalsIgnoreCase("send")) {
                        image1.setVisibility(View.VISIBLE);
                        image2.setVisibility(View.GONE);
                    } else {
                        image2.setVisibility(View.VISIBLE);
                        image1.setVisibility(View.GONE);
                    }
                }
            } else {
                senderRL.setVisibility(View.VISIBLE);
                sendimageRIV.setVisibility(View.GONE);
                message.setVisibility(View.VISIBLE);

                message.setText(messageModel.getMessage());

                time.setText(messageModel.getTimestamp());
                if (messageModel.getStatus().equalsIgnoreCase("send")) {
                    image1.setVisibility(View.VISIBLE);
                    image2.setVisibility(View.GONE);
                } else {
                    image2.setVisibility(View.VISIBLE);
                    image1.setVisibility(View.GONE);
                }

//                if (messageModel.getStatus().equalsIgnoreCase("delivered")) {
//                    image2.setVisibility(View.VISIBLE);
//                    image1.setVisibility(View.GONE);
//
//                } else if (status.equalsIgnoreCase("delivered")) {
//                    image2.setVisibility(View.VISIBLE);
//                    image1.setVisibility(View.GONE);
//
//                }

            }


//            message.setText(messageModel.getMessage());

//            time.setText(messageModel.getTimestamp());


        }

    }

    public class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView message;
        TextView time;
        RoundedImageView sendimageRIV;
        LinearLayout recieverLL;

        public ReceivedMessageHolder(@NonNull View itemView) {
            super(itemView);
            message = (TextView) itemView.findViewById(R.id.message);
            time = (TextView) itemView.findViewById(R.id.time);
            sendimageRIV = (RoundedImageView) itemView.findViewById(R.id.sendimageRIV);
            recieverLL = (LinearLayout) itemView.findViewById(R.id.recieverLL);

        }

        void bind(All_messages messageModel, RecyclerView.ViewHolder holder) {

            if (messageModel.getImage_url() != null) {
                if (!messageModel.getImage_url().equalsIgnoreCase("")) {
                    if (messageModel.getImage_url().contains(".jpg") || messageModel.getImage_url().contains(".png")) {
                        sendimageRIV.setVisibility(View.VISIBLE);
//                        recieverLL.setVisibility(View.GONE);
                        message.setVisibility(View.GONE);
                        time.setText(messageModel.getTimestamp());
                        Picasso.with(context).load(messageModel.getImage_url()).into(sendimageRIV);

                    } else {
                        message.setText(messageModel.getMessage());
                        time.setText(messageModel.getTimestamp());
//                        recieverLL.setVisibility(View.VISIBLE);
                        message.setVisibility(View.VISIBLE);
                        sendimageRIV.setVisibility(View.GONE);
                    }
                } else {
                    message.setText(messageModel.getMessage());
                    time.setText(messageModel.getTimestamp());
//                    recieverLL.setVisibility(View.VISIBLE);
                    message.setVisibility(View.VISIBLE);
                    sendimageRIV.setVisibility(View.GONE);
                }
            } else {
                message.setText(messageModel.getMessage());
                time.setText(messageModel.getTimestamp());
//                recieverLL.setVisibility(View.VISIBLE);
                message.setVisibility(View.VISIBLE);
                sendimageRIV.setVisibility(View.GONE);
            }


        }
    }

    public interface Onitemclicklistener {
        void openImage(String message);
    }
}


