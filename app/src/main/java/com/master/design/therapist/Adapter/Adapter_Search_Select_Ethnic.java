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

import com.master.design.therapist.DM.SearchDM;
import com.master.design.therapist.DataModel.Ethnic_details;
import com.master.design.therapist.Helper.User;
import com.master.design.therapist.R;

import java.util.ArrayList;

public class Adapter_Search_Select_Ethnic extends RecyclerView.Adapter<Adapter_Search_Select_Ethnic.ViewHolder>{

    private Context context;
    private ArrayList<Ethnic_details> arrayList;
    private SearchDM selected;
    User user;
    Adapter_Search_Select.OnItemClickListener onItemClickListener;


    int selectedPosition = 0;

    public Adapter_Search_Select_Ethnic(Context context, ArrayList<Ethnic_details> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        user = new User(context);

    }


    @NonNull
    @Override
    public Adapter_Search_Select_Ethnic.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custum_item_search, parent, false);
        // set the view's size, margins, paddings and layout parameters
        Adapter_Search_Select_Ethnic.ViewHolder vh = new Adapter_Search_Select_Ethnic.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Search_Select_Ethnic.ViewHolder holder, int position) {
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


    private void setDetails(Adapter_Search_Select_Ethnic.ViewHolder viewHolder, int position) {

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

        viewHolder.smallTxt.setText(arrayList.get(position).getEthnic_name());

        viewHolder.mainTxt.setVisibility(View.GONE);
        viewHolder.smallTxt.setGravity(Gravity.CENTER);
        viewHolder.clickLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                onItemClickListener.onClickThis(position, arrayList.get(position).getHead(), arrayList.get(position).getDes());

            }
        });
    }

    public void setOnItemClickListener(Adapter_Search_Select.OnItemClickListener onItemClickListener) {
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

        public ViewHolder(View itemView) {
            super(itemView);
            mainTxt = itemView.findViewById(R.id.mainTxt);
            smallTxt = itemView.findViewById(R.id.smallTxt);
            clickLL = itemView.findViewById(R.id.clickLL);
            mainTxt.setVisibility(View.GONE);
            smallTxt.setGravity(Gravity.CENTER);

        }
    }

    public interface OnItemClickListener {


        void onClickThis(int position, String heading, String subheading);
    }



}
