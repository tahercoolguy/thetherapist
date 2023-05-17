//package com.master.design.therapist.Adapter;
//
//import android.annotation.SuppressLint;
//import android.net.Uri;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.RelativeLayout;
//
//import androidx.cardview.widget.CardView;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.makeramen.roundedimageview.RoundedImageView;
//import com.master.design.therapist.R;
//
//import java.util.ArrayList;
//
//import io.reactivex.annotations.NonNull;
//
//public class AddImageAdapter extends RecyclerView.Adapter<AddImageAdapter.ViewHolder> {
//
//    private ArrayList list;
//    OnItemClickListener onItemClickListener;
//    String imguridafault;
//
//    public AddImageAdapter(ArrayList list, String imguridafault) {
//        this.list = list;
//        this.imguridafault = imguridafault;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//        View view = inflater.inflate(R.layout.cusutm_item_add_photo, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
//        holder.mainImg.setImageURI((Uri) list.get(position));
//
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
//
//
//        holder.deleteRL.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onItemClickListener.onClickImageDelete(position);
//            }
//        });
//
//        holder.editRL.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onItemClickListener.onClickImageEdit(position);
//            }
//        });
//
//        holder.addImgCV.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onItemClickListener.onClickAddMoreImage(position);
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return list.size();
//    }
//
//    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
//        this.onItemClickListener = onItemClickListener;
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        RoundedImageView mainImg;
//        CardView addImgCV;
//        RelativeLayout editRL, deleteRL;
//        CardView imageRL;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            mainImg = itemView.findViewById(R.id.mainImg);
//            editRL = itemView.findViewById(R.id.editRL);
//            deleteRL = itemView.findViewById(R.id.deleteRL);
//            addImgCV = itemView.findViewById(R.id.addImgCV);
//            imageRL = itemView.findViewById(R.id.imageRL);
//        }
//    }
//
//    public interface OnItemClickListener {
//
//
//        void onClickImageEdit(int position);
//
//        void onClickImageDelete(int position);
//
//        void onClickAddMoreImage(int position);
//    }
//}