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
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.master.design.therapist.DM.SearchDM;
import com.master.design.therapist.DataModel.Age_details;
import com.master.design.therapist.DataModel.GetAll_Image.GetAllImageOutput;
import com.master.design.therapist.Helper.User;
import com.master.design.therapist.R;

import java.util.ArrayList;

public class Adapter_Posted_Image extends RecyclerView.Adapter<Adapter_Posted_Image.ViewHolder> {

    private Context context;
    private ArrayList<GetAllImageOutput> arrayList;
    private GetAllImageOutput selected;
    User user;
    Adapter_Posted_Image.OnItemClickListener onItemClickListener;


    int selectedPosition = 0;

    public Adapter_Posted_Image(Context context, ArrayList<GetAllImageOutput> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        user = new User(context);

    }


    @NonNull
    @Override
    public Adapter_Posted_Image.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custum_item_posted_image, parent, false);
        // set the view's size, margins, paddings and layout parameters
        Adapter_Posted_Image.ViewHolder vh = new Adapter_Posted_Image.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Posted_Image.ViewHolder holder, int position) {
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


    private void setDetails(Adapter_Posted_Image.ViewHolder viewHolder, int position) {


        viewHolder.deleteRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                onItemClickListener.onClickThis(position, arrayList.get(position).getHead(), arrayList.get(position).getDes());
                onItemClickListener.deleteImage(position, "");
            }
        });
    }

    public void setOnItemClickListener(Adapter_Posted_Image.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public GetAllImageOutput getSelected() {
        return selected;
    }

    public void setSelected(GetAllImageOutput selected) {
        this.selected = selected;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private RoundedImageView frontRoundedImg;
        private RelativeLayout deleteRL;

        public ViewHolder(View itemView) {
            super(itemView);
            frontRoundedImg = itemView.findViewById(R.id.frontRoundedImg);
            deleteRL = itemView.findViewById(R.id.deleteRL);

        }
    }

    public interface OnItemClickListener {

        void deleteImage(int position, String id);
    }

}
