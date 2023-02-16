package com.master.design.therapist.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.master.design.therapist.DM.InterestDM;
import com.master.design.therapist.DM.SearchDM;
import com.master.design.therapist.Helper.User;
import com.master.design.therapist.R;

import java.util.ArrayList;


public class Adapter_Interest extends RecyclerView.Adapter<Adapter_Interest.ViewHolder> {
    private Context context;
    private ArrayList<InterestDM> arrayList;
    private InterestDM selected;
    User user;
    Adapter_Interest.OnItemClickListener onItemClickListener;


    int selectedPosition = 0;

    public Adapter_Interest(Context context, ArrayList<InterestDM> arrayList) {
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
//        viewHolder.mainTxt.setText(arrayList.get(position).getHead());
        viewHolder.interestTxt.setText(arrayList.get(position).getTittleInterest());
        viewHolder.img.setImageResource(arrayList.get(position).getImage());


        viewHolder.clickLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                onItemClickListener.onClickThis(position, arrayList.get(position).getImage());

            }
        });
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

        public ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            interestTxt = itemView.findViewById(R.id.interestTxt);
            clickLL = itemView.findViewById(R.id.clickLL);

        }
    }

    public interface OnItemClickListener {


        void onClickThis(int position, int img);
    }
}
