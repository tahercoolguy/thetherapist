package com.master.design.therapist.Adapter;

import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.master.design.therapist.DM.InterestDM;
import com.master.design.therapist.DM.SearchDM;
import com.master.design.therapist.Helper.User;
import com.master.design.therapist.R;

import java.util.ArrayList;


public class Adapter_Friends extends RecyclerView.Adapter<Adapter_Friends.ViewHolder> {
    private Context context;
    private ArrayList<InterestDM> arrayList;
    private InterestDM selected;
    User user;
    Adapter_Friends.OnItemClickListener onItemClickListener;


    int selectedPosition = 0;

    public Adapter_Friends(Context context, ArrayList<InterestDM> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        user = new User(context);

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

        viewHolder.nameTxt.setText(arrayList.get(position).getTittleInterest());
        viewHolder.profileImageRIV.setImageResource(arrayList.get(position).getImage());

        viewHolder.clickLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                onItemClickListener.onClickThis(position);

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

        public ViewHolder(View itemView) {
            super(itemView);
            profileImageRIV = itemView.findViewById(R.id.profileImageRIV);
            clickLL = itemView.findViewById(R.id.clickLL);
            nameTxt = itemView.findViewById(R.id.nameTxt);


        }
    }

    public interface OnItemClickListener {


        void onClickThis(int position);
    }
}
