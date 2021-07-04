package com.master.design.blackeye.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.master.design.blackeye.DataModel.NewsResult;
import com.master.design.blackeye.Helper.User;

import com.master.design.blackeye.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter_News extends RecyclerView.Adapter<Adapter_News.ViewHolder> {
    private Context context;
    private ArrayList<NewsResult> arrayList;
    private NewsResult selected;
    User user;


    int selectedPosition = 0;

    public Adapter_News(Context context, ArrayList<NewsResult> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        user = new User(context);

    }


    @NonNull
    @Override
    public Adapter_News.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        Adapter_News.ViewHolder vh = new Adapter_News.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_News.ViewHolder holder, int position) {
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


    private void setDetails(Adapter_News.ViewHolder viewHolder, int position) {
        Picasso.with(context).load(arrayList.get(position).getImage_file()).into(viewHolder.img);
        viewHolder.description.setText(arrayList.get(position).getDescription());
        viewHolder.read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public NewsResult getSelected() {
        return selected;
    }

    public void setSelected(NewsResult selected) {
        this.selected = selected;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView  description,read;
        private ImageView img;
        private LinearLayout clickll;
        public ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            description = itemView.findViewById(R.id.descriptionTxt);
            read = itemView.findViewById(R.id.readTx);

            //          
        }
    }
}
