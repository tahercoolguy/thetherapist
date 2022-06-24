package com.master.design.blackeye.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.master.design.blackeye.DataModel.RestaurentResult;
import com.master.design.blackeye.DataModel.RestaurentResult;
import com.master.design.blackeye.Helper.User;
import com.master.design.blackeye.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter_Restaurent extends RecyclerView.Adapter<Adapter_Restaurent.ViewHolder> {
    private Context context;
    private ArrayList<RestaurentResult> arrayList;
    private RestaurentResult selected;
    User user;


    int selectedPosition = 0;

    public Adapter_Restaurent(Context context, ArrayList<RestaurentResult> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        user = new User(context);

    }


    @NonNull
    @Override
    public Adapter_Restaurent.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurent_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        Adapter_Restaurent.ViewHolder vh = new Adapter_Restaurent.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Restaurent.ViewHolder holder, int position) {
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


    private void setDetails(Adapter_Restaurent.ViewHolder viewHolder, int position) {
        if(!arrayList.get(position).getImage_file().equalsIgnoreCase(""))
        Picasso.get( ).load(arrayList.get(position).getImage_file()).into(viewHolder.img);
        viewHolder.companyName.setText(arrayList.get(position).getRestaurant_name());
        viewHolder.status.setText(arrayList.get(position).getStatus());
        Picasso.get( ).load(arrayList.get(position).getAttachment()).into(viewHolder.companyIcon);
        viewHolder.location.setText(arrayList.get(position).getLocation());
    }

    public RestaurentResult getSelected() {
        return selected;
    }

    public void setSelected(RestaurentResult selected) {
        this.selected = selected;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView companyName,status,location;
        private ImageView companyIcon,img;
        
        public ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            companyIcon = itemView.findViewById(R.id.companyIcon);
            companyName = itemView.findViewById(R.id.companyName);
            status= itemView.findViewById(R.id.status);
            location = itemView.findViewById(R.id.location);

            //          
        }
    }
}
