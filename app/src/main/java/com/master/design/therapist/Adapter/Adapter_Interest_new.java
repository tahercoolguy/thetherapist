package com.master.design.therapist.Adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.master.design.therapist.Activity.FriendSearch_SelectActivity;
import com.master.design.therapist.Controller.AppController;
import com.master.design.therapist.DM.InterestDM;
import com.master.design.therapist.DataModel.Interest_details;
import com.master.design.therapist.Helper.User;
import com.master.design.therapist.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import io.reactivex.annotations.NonNull;

public class Adapter_Interest_new extends RecyclerView.Adapter<Adapter_Interest_new.ViewHolder>{

    private Context context;
    private ArrayList<Interest_details> arrayList;
    private InterestDM selected;
    User user;
    Adapter_Interest_new.OnItemClickListener onItemClickListener;
    int row_index;
 FriendSearch_SelectActivity friendSearch_selectActivity;

    int selectedPosition = 0;

    public Adapter_Interest_new(Context context, ArrayList<Interest_details> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        user = new User(context);

    }


    @NonNull
    @Override
    public Adapter_Interest_new.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custum_item_inerest_new, parent, false);
        // set the view's size, margins, paddings and layout parameters
        Adapter_Interest_new.ViewHolder vh = new Adapter_Interest_new.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Interest_new.ViewHolder holder, int position) {
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


    private void setDetails(Adapter_Interest_new.ViewHolder viewHolder, int position) {

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
//        viewHolder.img.setImageResource(arrayList.get(position).getImage());

        viewHolder.clickLL.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
//                onItemClickListener.onClickThis(position, arrayList.get(position).getTittleInterest());
//
//                row_index = position;
//                notifyDataSetChanged();

           if(viewHolder.selected_cardView.getBackgroundTintList()==context.getColorStateList(R.color.white))
            {
                viewHolder.selected_cardView.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.red));
                ((FriendSearch_SelectActivity)context).InterestIdList=((FriendSearch_SelectActivity)context).InterestIdList+arrayList.get(position).getId() +"," ;
                if (user.getLanguageCode().equalsIgnoreCase("en")) {
                    ((FriendSearch_SelectActivity)context).InterestNameEngList=((FriendSearch_SelectActivity)context).InterestNameEngList+arrayList.get(position).getInterest_eg() +"," ;

                }else{
                    ((FriendSearch_SelectActivity)context).InterestNameEngList=((FriendSearch_SelectActivity)context).InterestNameEngList+arrayList.get(position).getInterest_arb() +"," ;

                }
                ((FriendSearch_SelectActivity)context).InterestNameArList=((FriendSearch_SelectActivity)context).InterestNameArList+arrayList.get(position).getInterest_arb() +"," ;
                Log.e("Checking", "Checking");

            }
           else
           {
               viewHolder.selected_cardView.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.white));
               ((FriendSearch_SelectActivity)context).InterestIdList=((FriendSearch_SelectActivity)context).InterestIdList.replace(arrayList.get(position).getId()+",","");
               if (user.getLanguageCode().equalsIgnoreCase("en")) {
                   ((FriendSearch_SelectActivity)context).InterestNameEngList=((FriendSearch_SelectActivity)context).InterestNameEngList.replace(arrayList.get(position).getInterest_eg()+",","");

               }else{
                   ((FriendSearch_SelectActivity)context).InterestNameEngList=((FriendSearch_SelectActivity)context).InterestNameEngList.replace(arrayList.get(position).getInterest_arb()+",","");

               }
               ((FriendSearch_SelectActivity)context).InterestNameArList=((FriendSearch_SelectActivity)context).InterestNameArList.replace(arrayList.get(position).getInterest_arb()+",","");
               Log.e("Checking", "Checking");

           }


            }
        });

//        if (row_index == position) {
//            viewHolder.selected_cardView.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.red));
//        } else {
//            viewHolder.selected_cardView.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.white));
//        }
    }

    public void setOnItemClickListener(Adapter_Interest_new.OnItemClickListener onItemClickListener) {
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
            img = itemView.findViewById(R.id.img1);
            interestTxt = itemView.findViewById(R.id.interestTxt);
            clickLL = itemView.findViewById(R.id.clickLL);
            selected_cardView = itemView.findViewById(R.id.selected_cardView1);

        }
    }

    public interface OnItemClickListener {


        void onClickThis(int position, String interstID,String intArname,String intEngName);
    }
}
