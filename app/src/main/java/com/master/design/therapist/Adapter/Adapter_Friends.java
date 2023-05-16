package com.master.design.therapist.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.makeramen.roundedimageview.RoundedImageView;
import com.master.design.therapist.Activity.Conversation_Activity;
import com.master.design.therapist.Activity.Create_Account_Activity;
import com.master.design.therapist.Activity.FriendSearch_SelectActivity;
import com.master.design.therapist.Activity.MainActivity;
import com.master.design.therapist.Controller.AppController;
import com.master.design.therapist.DM.InterestDM;
import com.master.design.therapist.DM.SearchDM;
import com.master.design.therapist.DataModel.All_friends;
import com.master.design.therapist.DataModel.ChatlistDM;
import com.master.design.therapist.DataModel.ChatroomDM;
import com.master.design.therapist.DataModel.UnfriendDM;
import com.master.design.therapist.Fragments.Fragment_Friends_Request;
import com.master.design.therapist.Helper.DialogUtil;
import com.master.design.therapist.Helper.Helper;
import com.master.design.therapist.Helper.User;
import com.master.design.therapist.R;
import com.master.design.therapist.Utils.ConnectionDetector;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class Adapter_Friends extends RecyclerView.Adapter<Adapter_Friends.ViewHolder> {
    private Context context;
    private ArrayList<All_friends> arrayList;
    private InterestDM selected;
    User user;

    Dialog progress;
    DialogUtil dialogUtil;

    AppController appController;
    ConnectionDetector connectionDetector;
    Adapter_Friends.OnItemClickListener onItemClickListener;


    int selectedPosition = 0;

    public Adapter_Friends(Context context, ArrayList<All_friends> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        user = new User(context);
        dialogUtil = new DialogUtil();
        appController = (AppController) context.getApplicationContext();
        connectionDetector = new ConnectionDetector(context);


    }


    @NonNull
    @Override
    public Adapter_Friends.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custum_item_friends, parent, false);
        // set the view's size, margins, paddings and layout parameters
        Adapter_Friends.ViewHolder vh = new Adapter_Friends.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Friends.ViewHolder holder, int position) {
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


    private void setDetails(Adapter_Friends.ViewHolder viewHolder, int position) {

        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                // on the below line we are finishing
                // our current activity.
                viewHolder.itemView.startAnimation(animation);

            }
        }, 100);

        viewHolder.nameTxt.setText(arrayList.get(position).getName());
//        viewHolder.profileImageRIV.setImageResource(arrayList.get(position).getImage());

        Picasso.with(context).load(AppController.THERAPIST_IMAGE + arrayList.get(position).getImage()).placeholder(R.drawable.tab_selector).into(viewHolder.profileImageRIV);

        viewHolder.clickLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                onItemClickListener.onClickThis(position);
                if (connectionDetector.isConnectingToInternet()) {
                    String refreshedToken = FirebaseInstanceId.getInstance().getToken();
                    progress = dialogUtil.showProgressDialog(context, context.getString(R.string.please_wait));
                    appController.paServices.TherapistChatChatRoom(String.valueOf(user.getId()), arrayList.get(position).getId(), new Callback<ChatroomDM>() {
                        @Override
                        public void success(ChatroomDM chatroomDM, Response response) {
                            progress.dismiss();
                            if (chatroomDM.getStatus().equalsIgnoreCase("1")) {


                                Intent intent = new Intent(context, Conversation_Activity.class);
                                intent.putExtra("FriendId", arrayList.get(position).getId());
                                intent.putExtra("image", AppController.THERAPIST_IMAGE + arrayList.get(position).getImage());
                                intent.putExtra("Name", arrayList.get(position).getName());
                                context.startActivity(intent);
                                Helper.showToast(context, chatroomDM.getMsg());
                            } else
                                Helper.showToast(context, chatroomDM.getMsg());
                        }

                        @Override
                        public void failure(RetrofitError retrofitError) {
                            progress.dismiss();
                            Log.e("error", retrofitError.toString());
                        }
                    });
                } else
                    Helper.showToast(context, String.valueOf(R.string.no_internet_connection));

            }
        });


        viewHolder.uunfriendIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                onItemClickListener.onClickThis(position);

                AlertDialog.Builder adb = new AlertDialog.Builder(context);
//                adb.setView(alertDialogView);
                adb.setTitle("Unfriend Friend");
//                adb.setIcon(android.R.drawable.ic_dialog_alert);
                adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {


                        if (connectionDetector.isConnectingToInternet()) {
                            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
                            progress = dialogUtil.showProgressDialog(context, context.getString(R.string.please_wait));
                            appController.paServices.TherapistUnfriend(String.valueOf(user.getId()), arrayList.get(position).getId(), new Callback<UnfriendDM>() {
                                @Override
                                public void success(UnfriendDM unfriendDM, Response response) {
                                    progress.dismiss();
                                    if (unfriendDM.getStatus().equalsIgnoreCase("1")) {

                                        Helper.showToast(context, unfriendDM.getMsg());
                                        ((MainActivity) context).addFragment(new Fragment_Friends_Request(), false);

                                    } else
                                        Helper.showToast(context, unfriendDM.getMsg());
                                }

                                @Override
                                public void failure(RetrofitError retrofitError) {
                                    progress.dismiss();
                                    Log.e("error", retrofitError.toString());
                                }
                            });
                        } else
                            Helper.showToast(context, String.valueOf(R.string.no_internet_connection));


                    }
                });
                adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //                  ((MainActivity)context).addFragment(new Fragment_Friends_Request(),false);
                        //                    adb.setCancelable(true);
                        dialog.dismiss();
                    }
                });
                adb.show();


            }
        });


    }

    public void setOnItemClickListener(Adapter_Friends.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public InterestDM getSelected() {
        return selected;
    }

    public void setSelected(InterestDM selected) {
        this.selected = selected;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTxt;
        private LinearLayout clickLL;
        private RoundedImageView profileImageRIV;
        ImageView uunfriendIV;

        public ViewHolder(View itemView) {
            super(itemView);
            profileImageRIV = itemView.findViewById(R.id.profileImageRIV);
            clickLL = itemView.findViewById(R.id.clickLL);
            nameTxt = itemView.findViewById(R.id.nameTxt);
            uunfriendIV = itemView.findViewById(R.id.unfriend);


        }
    }

    public interface OnItemClickListener {


        void onClickThis(int position);
    }
}
