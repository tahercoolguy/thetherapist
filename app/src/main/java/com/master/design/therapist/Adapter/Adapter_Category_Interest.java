package com.master.design.therapist.Adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.master.design.therapist.DM.InterestDM;
import com.master.design.therapist.Adapter.DataModel.Interests;
import com.master.design.therapist.Helper.User;
import com.master.design.therapist.R;

import java.util.ArrayList;


public class Adapter_Category_Interest extends RecyclerView.Adapter<Adapter_Category_Interest.ViewHolder> {
    private Context context;
    private ArrayList<Interests> arrayList;
    private InterestDM selected;
    User user;
    Adapter_Category_Interest.OnItemClickListener onItemClickListener;


    int selectedPosition = 0;

    public Adapter_Category_Interest(Context context, ArrayList<Interests> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        user = new User(context);

    }


    @NonNull
    @Override
    public Adapter_Category_Interest.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custum_item_category, parent, false);
        // set the view's size, margins, paddings and layout parameters
        Adapter_Category_Interest.ViewHolder vh = new Adapter_Category_Interest.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Category_Interest.ViewHolder holder, int position) {
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


    private void setDetails(Adapter_Category_Interest.ViewHolder viewHolder, int position) {

        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                // on the below line we are finishing
                // our current activity.
                viewHolder.itemView.startAnimation(animation);

            }
        }, 100);

//        viewHolder.mainTxt.setText(arrayList.get(position).getHead());
        if (user.getLanguageCode().equalsIgnoreCase("en")) {
            viewHolder.interestTxt.setText(arrayList.get(position).getInterest_eg());
        }else{
            viewHolder.interestTxt.setText(arrayList.get(position).getInterest_arb());
        }

//        viewHolder.interestTxt.setText(arrayList.get(position).getInterest_eg());


        viewHolder.clickLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                onItemClickListener.onClickThis(position, arrayList.get(position).getImage());

            }
        });
    }

    public void setOnItemClickListener(Adapter_Category_Interest.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public InterestDM getSelected() {
        return selected;
    }

    public void setSelected(InterestDM selected) {
        this.selected = selected;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

         private TextView interestTxt;
        private RelativeLayout clickLL;

        public ViewHolder(View itemView) {
            super(itemView);
             interestTxt = itemView.findViewById(R.id.interestTxt);
            clickLL = itemView.findViewById(R.id.clickLL);

        }
    }

    public interface OnItemClickListener {


        void onClickThis(int position, int img);
    }
}
