package com.master.design.therapist.Adapter;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.master.design.therapist.Activity.IntroActivity;
import com.master.design.therapist.Activity.Sign_InActivity;
import com.master.design.therapist.DM.IntroSliderDM;
import com.master.design.therapist.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderAdapterViewHolder> {

    // list for storing urls of images.
//    private final List<SliderData> mSliderItems;
    ArrayList<IntroSliderDM> eventsDetailsData;
    Activity context;
    ArrayList<IntroSliderDM> eventsDetailsImageData;

    // Constructor
    public SliderAdapter(Activity context, ArrayList<IntroSliderDM> eventsDetailsData) {
        this.eventsDetailsData = eventsDetailsData;
        this.context = context;
    }

    // We are inflating the slider_layout
    // inside on Create View Holder method.
    @Override
    public SliderAdapterViewHolder onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_layout, null);
        return new SliderAdapterViewHolder(inflate);
    }

    // Inside on bind view holder we will
    // set data to item of Slider View.
    @Override
    public void onBindViewHolder(SliderAdapterViewHolder viewHolder, int position) {

        viewHolder.img.setImageResource(eventsDetailsData.get(position).getImage());
        viewHolder.tittleTxt.setText(eventsDetailsData.get(position).getTittleTxt());
        viewHolder.descriptionTxt.setText(eventsDetailsData.get(position).getDescriptionTxt());


        viewHolder.nextTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((IntroActivity)context).slideNextPosition(position);
            }
        });


        // Glide is use to load image
        // from url in your imageview.
//        Glide.with(viewHolder.itemView)
//                .load(eventsDetailsData.get(position).getImagedata().get(position).getImage())
//                .fitCenter()
//                .into(viewHolder.imageViewBackground);
    }

    // this method will return
    // the count of our list.
    @Override
    public int getCount() {
        return eventsDetailsData.size();
    }

    static class SliderAdapterViewHolder extends SliderViewAdapter.ViewHolder {
        // Adapter class for initializing
        // the views of our slider view.
        View itemView;
        private ImageView img;
        private TextView tittleTxt, descriptionTxt, nextTxt;

        public SliderAdapterViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            tittleTxt = itemView.findViewById(R.id.tittleTxt);
            descriptionTxt = itemView.findViewById(R.id.descriptionTxt);
            nextTxt = itemView.findViewById(R.id.nextTxt);


            this.itemView = itemView;
        }
    }
}
