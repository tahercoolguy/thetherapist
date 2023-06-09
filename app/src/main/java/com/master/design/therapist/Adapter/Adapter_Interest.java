package com.master.design.therapist.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.master.design.therapist.Controller.AppController;
import com.master.design.therapist.DM.InterestDM;
import com.master.design.therapist.Adapter.DataModel.Interest_details;
import com.master.design.therapist.Helper.User;
import com.master.design.therapist.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class Adapter_Interest extends RecyclerView.Adapter<Adapter_Interest.ViewHolder> {
    private Context context;
    private ArrayList<Interest_details> arrayList;
    private InterestDM selected;
    User user;
    Adapter_Interest.OnItemClickListener onItemClickListener;
    int row_index;


    int selectedPosition = 0;

    public Adapter_Interest(Context context, ArrayList<Interest_details> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        user = new User(context);

    }


    @NonNull
    @Override
    public Adapter_Interest.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custum_item_interest, parent, false);
        // set the view's size, margins, paddings and layout parameters
        Adapter_Interest.ViewHolder vh = new Adapter_Interest.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Interest.ViewHolder holder, int position) {
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


    private void setDetails(Adapter_Interest.ViewHolder viewHolder, int position) {

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
//        viewHolder.mainTxt.setText(arrayList.get(position).getHead());
//        viewHolder.interestTxt.setText(arrayList.get(position).getInterest_eg());

        if (user.getLanguageCode().equalsIgnoreCase("en")) {
            viewHolder.interestTxt.setText(arrayList.get(position).getInterest_eg());
        }else {
            viewHolder.interestTxt.setText(arrayList.get(position).getInterest_arb());
        }

        if(arrayList.get(position).getInterest_img()!=null) {
            Picasso.with(context).load(AppController.THERAPIST_IMAGE+arrayList.get(position).getInterest_img()).placeholder(R.drawable.tab_selector).into(viewHolder.img);
        }


        viewHolder.clickLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                onItemClickListener.onClickThis(position, arrayList.get(position).getTittleInterest());
//
//                row_index = position;
//                notifyDataSetChanged();
            }
        });

        if (row_index == position) {
            viewHolder.selected_cardView.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.red));
        } else {
            viewHolder.selected_cardView.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.white));
        }
    }

    public void setOnItemClickListener(Adapter_Interest.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public InterestDM getSelected() {
        return selected;
    }

    public void setSelected(InterestDM selected) {
        this.selected = selected;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView img;
        private TextView interestTxt;
        private RelativeLayout clickLL;
        private CardView selected_cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            interestTxt = itemView.findViewById(R.id.interestTxt);
            clickLL = itemView.findViewById(R.id.clickLL);
            selected_cardView = itemView.findViewById(R.id.selected_cardView);

        }
    }

    public interface OnItemClickListener {


        void onClickThis(int position, String tittle);
    }
}
