package com.master.design.therapist.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.master.design.therapist.Controller.AppController;
import com.master.design.therapist.DM.InterestDM;
import com.master.design.therapist.DataModel.Senders;
import com.master.design.therapist.Helper.DialogUtil;
import com.master.design.therapist.Helper.User;
import com.master.design.therapist.R;
import com.master.design.therapist.Utils.ConnectionDetector;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class Adapter_Request extends RecyclerView.Adapter<Adapter_Request.ViewHolder> {
    private Context context;
    private ArrayList<Senders> arrayList;
    private InterestDM selected;
    User user;
    Dialog progress;
    DialogUtil dialogUtil;
    AppController appController;
    ConnectionDetector connectionDetector;


    Adapter_Request.OnItemClickListener onItemClickListener;


    int selectedPosition = 0;

    public Adapter_Request(Context context, ArrayList<Senders> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        user = new User(context);
        dialogUtil = new DialogUtil();
        appController = (AppController) context.getApplicationContext();
        connectionDetector = new ConnectionDetector(context);

    }


    @NonNull
    @Override
    public Adapter_Request.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custum_item_request, parent, false);
        // set the view's size, margins, paddings and layout parameters
        Adapter_Request.ViewHolder vh = new Adapter_Request.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Request.ViewHolder holder, int position) {
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


    private void setDetails(Adapter_Request.ViewHolder viewHolder, int position) {

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

//        viewHolder.nameTxt.setText(arrayList.get(position).getName());

        if (user.getLanguageCode().equalsIgnoreCase("en")) {
            viewHolder.nameTxt.setText(arrayList.get(position).getName());
        }else {
            viewHolder.nameTxt.setText(arrayList.get(position).getName());
        }
//        viewHolder.profileImageRIV.setImageResource(arrayList.get(position).getImage());

        Picasso.with(context).load(AppController.THERAPIST_IMAGE + arrayList.get(position).getImage()).placeholder(R.drawable.tab_selector).into(viewHolder.profileImageRIV);

        viewHolder.clickLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                onItemClickListener.onClickThis(position);

            }
        });

        viewHolder.acceptTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onItemClickListener.clickAcceptButton(position, arrayList.get(position).getId(), "1");


            }
        });

        viewHolder.rejectTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.clickRejectButton(position, arrayList.get(position).getId(), "1");

            }
        });
    }

    public void setOnItemClickListener(Adapter_Request.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public InterestDM getSelected() {
        return selected;
    }

    public void setSelected(InterestDM selected) {
        this.selected = selected;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTxt, acceptTxt, rejectTxt;
        private RelativeLayout clickLL;
        private RoundedImageView profileImageRIV;

        public ViewHolder(View itemView) {
            super(itemView);
            profileImageRIV = itemView.findViewById(R.id.profileImageRIV);
            clickLL = itemView.findViewById(R.id.clickLL);
            nameTxt = itemView.findViewById(R.id.nameTxt);
            acceptTxt = itemView.findViewById(R.id.acceptTxt);
            rejectTxt = itemView.findViewById(R.id.rejectTxt);


        }
    }

    public interface OnItemClickListener {


        void clickAcceptButton(int position, String id, String resonse);

        void clickRejectButton(int position, String id, String resonse);
    }
}
