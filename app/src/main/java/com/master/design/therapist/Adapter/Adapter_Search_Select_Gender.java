package com.master.design.therapist.Adapter;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.master.design.therapist.Activity.FriendSearch_SelectActivity;
import com.master.design.therapist.DM.SearchDM;
import com.master.design.therapist.DataModel.Age_details;
import com.master.design.therapist.DataModel.Gender_details;
import com.master.design.therapist.Helper.User;
import com.master.design.therapist.R;

import java.util.ArrayList;

public class Adapter_Search_Select_Gender extends RecyclerView.Adapter<Adapter_Search_Select_Gender.ViewHolder>{

    private Context context;
    private ArrayList<Gender_details> arrayList;
    private SearchDM selected;
    User user;
    Adapter_Search_Select_Gender.OnItemClickListener onItemClickListener;


    int selectedPosition = 0;

    public Adapter_Search_Select_Gender(Context context, ArrayList<Gender_details> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        user = new User(context);

    }


    @NonNull
    @Override
    public Adapter_Search_Select_Gender.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custum_item_search, parent, false);
        // set the view's size, margins, paddings and layout parameters
        Adapter_Search_Select_Gender.ViewHolder vh = new Adapter_Search_Select_Gender.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Search_Select_Gender.ViewHolder holder, int position) {
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


    private void setDetails(Adapter_Search_Select_Gender.ViewHolder viewHolder, int position) {

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

        viewHolder.smallTxt.setText(arrayList.get(position).getGender());

        viewHolder.mainTxt.setVisibility(View.GONE);
        viewHolder.smallTxt.setGravity(Gravity.CENTER);
        viewHolder.clickLL.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
//                onItemClickListener.onClickThis(position, arrayList.get(position).getHead(), arrayList.get(position).getDes());

//                onItemClickListener.onClickThis(position,arrayList.get(position).getGender(),arrayList.get(position).getGender_arb(),arrayList.get(position).getId());

                if(viewHolder.selected_cardView.getBackgroundTintList()==context.getColorStateList(R.color.white))
                {
                    viewHolder.selected_cardView.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.red));
                    ((FriendSearch_SelectActivity)context).selected_genderId=((FriendSearch_SelectActivity)context).selected_genderId+arrayList.get(position).getId() +"," ;
                    ((FriendSearch_SelectActivity)context).selected_genderEng=((FriendSearch_SelectActivity)context).selected_genderEng+arrayList.get(position).getGender() +"," ;
                    ((FriendSearch_SelectActivity)context).selected_genderAR=((FriendSearch_SelectActivity)context).selected_genderAR+arrayList.get(position).getGender_arb() +"," ;
                    Log.e("Checking", "Checking");

                }
                else
                {
                    viewHolder.selected_cardView.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.white));
                    ((FriendSearch_SelectActivity)context).selected_genderId=((FriendSearch_SelectActivity)context).selected_genderId.replace(arrayList.get(position).getId()+",","");
                    ((FriendSearch_SelectActivity)context).selected_genderEng=((FriendSearch_SelectActivity)context).selected_genderEng.replace(arrayList.get(position).getGender()+",","");
                    ((FriendSearch_SelectActivity)context).selected_genderAR=((FriendSearch_SelectActivity)context).selected_genderAR.replace(arrayList.get(position).getGender_arb()+",","");
                    Log.e("Checking", "Checking");

                }


            }
        });
    }

    public void setOnItemClickListener(Adapter_Search_Select_Gender.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public SearchDM getSelected() {
        return selected;
    }

    public void setSelected(SearchDM selected) {
        this.selected = selected;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mainTxt, smallTxt;
        private LinearLayout clickLL;
        private CardView selected_cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            mainTxt = itemView.findViewById(R.id.mainTxt);
            smallTxt = itemView.findViewById(R.id.smallTxt);
            clickLL = itemView.findViewById(R.id.clickLL);
            selected_cardView = itemView.findViewById(R.id.selected_cardView1);
            mainTxt.setVisibility(View.GONE);
            smallTxt.setGravity(Gravity.CENTER);

        }
    }

    public interface OnItemClickListener {


        void onClickThis(int position, String genderEng, String genderAR,String genderId);
    }

}
