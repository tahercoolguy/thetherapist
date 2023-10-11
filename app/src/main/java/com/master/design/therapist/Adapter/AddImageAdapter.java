package com.master.design.therapist.Adapter;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.master.design.therapist.R;

import java.util.ArrayList;

import io.reactivex.annotations.NonNull;

public class AddImageAdapter extends RecyclerView.Adapter<AddImageAdapter.ViewHolder> {

    private ArrayList list;
    OnItemClickListener onItemClickListener;
    String imguridafault;

    public AddImageAdapter(ArrayList list, String imguridafault) {
        this.list = list;
        this.imguridafault = imguridafault;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.custum_item_posted_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.frontRoundedImg.setImageURI((Uri) list.get(position));

//        if (imguridafault.equalsIgnoreCase("1")) {
//
//            if (position == 0) {
//                holder.addImgCV.setVisibility(View.VISIBLE);
//                holder.imageRL.setVisibility(View.GONE);
//            } else {
//                holder.addImgCV.setVisibility(View.GONE);
//                holder.imageRL.setVisibility(View.VISIBLE);
//            }
//
//        } else {
//            holder.addImgCV.setVisibility(View.GONE);
//            holder.imageRL.setVisibility(View.VISIBLE);
//        }


        holder.deleteImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onItemClickListener.onClickImageDelete(position);
                // Get the current position
                int currentPosition = holder.getAdapterPosition();

                if (currentPosition != RecyclerView.NO_POSITION) {
                    // Remove the item at the current position
                    list.remove(currentPosition);
                    notifyItemRemoved(currentPosition);
                }
            }
        });

//        holder.editRL.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onItemClickListener.onClickImageEdit(position);
//            }
//        });

//        holder.addImgCV.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onItemClickListener.onClickAddMoreImage(position);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RoundedImageView frontRoundedImg;
        private ImageButton deleteImgBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            frontRoundedImg = itemView.findViewById(R.id.frontRoundedImg);
            deleteImgBtn = itemView.findViewById(R.id.deleteImgBtn);

        }
    }

    public interface OnItemClickListener {


//        void onClickImageEdit(int position);

//        void onClickImageDelete(int position);

//        void onClickAddMoreImage(int position);
    }
}