package com.master.design.therapist.Adapter;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.master.design.therapist.DataModel.CommonReasonDetail;
import com.master.design.therapist.DataModel.CommonReasonDetail;
import com.master.design.therapist.Helper.User;
import com.master.design.therapist.R;

import java.util.ArrayList;

import io.reactivex.annotations.NonNull;

public class Adapter_Common_Reason extends RecyclerView.Adapter<Adapter_Common_Reason.ViewHolder> {

    private Context context;
    private ArrayList<CommonReasonDetail> arrayList;
    private CommonReasonDetail selected;
    User user;
    Adapter_Common_Reason.OnItemClickListener onItemClickListener;


    int selectedPosition = 0;

    public Adapter_Common_Reason(Context context, ArrayList<CommonReasonDetail> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        user = new User(context);

    }


    @NonNull
    @Override
    public Adapter_Common_Reason.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custum_item_search, parent, false);
        // set the view's size, margins, paddings and layout parameters
        Adapter_Common_Reason.ViewHolder vh = new Adapter_Common_Reason.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Common_Reason.ViewHolder holder, int position) {
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


    private void setDetails(Adapter_Common_Reason.ViewHolder viewHolder, int position) {

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

//        viewHolder.smallTxt.setText(arrayList.get(position).getAge_eg());

        if (user.getLanguageCode().equalsIgnoreCase("en")) {
            viewHolder.smallTxt.setText(arrayList.get(position).getReason_eg());
        } else {
            viewHolder.smallTxt.setText(arrayList.get(position).getReason_arb());
        }

        viewHolder.mainTxt.setVisibility(View.GONE);
        viewHolder.smallTxt.setGravity(Gravity.CENTER);
        viewHolder.clickLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                onItemClickListener.onClickThis(position, arrayList.get(position).getHead(), arrayList.get(position).getDes());

                onItemClickListener.onClickThis(position, arrayList.get(position).getId(), arrayList.get(position).getReason_eg(), arrayList.get(position).getReason_arb());


//                if(viewHolder.selected_cardView.getBackgroundTintList()==context.getColorStateList(R.color.white))
//                {
//                    viewHolder.selected_cardView.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.red));
//                    ((FriendSearch_SelectActivity)context).selected_ageId=((FriendSearch_SelectActivity)context).selected_ageId+arrayList.get(position).getId() +"," ;
//                    ((FriendSearch_SelectActivity)context).selected_ageEng=((FriendSearch_SelectActivity)context).selected_ageEng+arrayList.get(position).getAge_eg() +"," ;
//                    ((FriendSearch_SelectActivity)context).selected_ageAR=((FriendSearch_SelectActivity)context).selected_ageAR+arrayList.get(position).getAge_arb() +"," ;
//                    Log.e("Checking", "Checking");
//
//                }
//                else
//                {
//                    viewHolder.selected_cardView.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.white));
//                    ((FriendSearch_SelectActivity)context).selected_ageId=((FriendSearch_SelectActivity)context).selected_ageId.replace(arrayList.get(position).getId()+",","");
//                    ((FriendSearch_SelectActivity)context).selected_ageEng=((FriendSearch_SelectActivity)context).selected_ageEng.replace(arrayList.get(position).getAge_eg()+",","");
//                    ((FriendSearch_SelectActivity)context).selected_ageAR=((FriendSearch_SelectActivity)context).selected_ageAR.replace(arrayList.get(position).getAge_arb()+",","");
//                    Log.e("Checking", "Checking");
//
//                }


            }
        });
    }

    public void setOnItemClickListener(Adapter_Common_Reason.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public CommonReasonDetail getSelected() {
        return selected;
    }

    public void setSelected(CommonReasonDetail selected) {
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
        void onClickThis(int position, String reasonId, String reasonEng, String reasonAR);
    }

}
