package com.master.design.therapist.Adapter;

import android.content.Context;
import android.os.Handler;
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

import com.master.design.therapist.DM.SearchDM;
import com.master.design.therapist.DataModel.NewsResult;
import com.master.design.therapist.Helper.User;
import com.master.design.therapist.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class Adapter_Search extends RecyclerView.Adapter<Adapter_Search.ViewHolder> {
    private Context context;
    private ArrayList<SearchDM> arrayList;
    private SearchDM selected;
    User user;
    Adapter_Search.OnItemClickListener onItemClickListener;



    int selectedPosition = 0;

    public Adapter_Search(Context context, ArrayList<SearchDM> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        user = new User(context);

    }


    @NonNull
    @Override
    public Adapter_Search.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custum_item_search, parent, false);
        // set the view's size, margins, paddings and layout parameters
        Adapter_Search.ViewHolder vh = new Adapter_Search.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Search.ViewHolder holder, int position) {
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


    private void setDetails(Adapter_Search.ViewHolder viewHolder, int position) {
        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                // on the below line we are finishing
                // our current activity.
                viewHolder.itemView.startAnimation(animation);

            }
        }, 100);
        viewHolder.mainTxt.setText(arrayList.get(position).getHead());
        viewHolder.smallTxt.setText(arrayList.get(position).getDes());


        viewHolder.clickLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onClickThis(position);
                onItemClickListener.onGetSubItem(position,arrayList.get(position).getHead());

            }
        });
    }
    public void setOnItemClickListener(Adapter_Search.OnItemClickListener onItemClickListener) {
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

        }
    }

    public interface OnItemClickListener {


        void onClickThis(int position);
        void onGetSubItem(int position, String subheading);

    }
}
