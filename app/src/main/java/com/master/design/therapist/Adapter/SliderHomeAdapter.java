package com.master.design.therapist.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.master.design.therapist.Activity.IntroActivity;
import com.master.design.therapist.Controller.AppController;
import com.master.design.therapist.DM.IntroSliderDM;
import com.master.design.therapist.DataModel.Cancel_Friend_RequestDM;
import com.master.design.therapist.DataModel.Image;
import com.master.design.therapist.DataModel.Send_Friend_RequestDM;
import com.master.design.therapist.DataModel.Users;
import com.master.design.therapist.Fragments.Fragment_Home;
import com.master.design.therapist.Helper.DialogUtil;
import com.master.design.therapist.Helper.Helper;
import com.master.design.therapist.Helper.User;
import com.master.design.therapist.R;
import com.master.design.therapist.Utils.ConnectionDetector;
import com.smarteist.autoimageslider.SliderView;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.BlurTransformation;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SliderHomeAdapter extends SliderViewAdapter<SliderHomeAdapter.SliderAdapterViewHolder> {

    // list for storing urls of images.
//    private final List<SliderData> mSliderItems;
    User user;
    Dialog progress;
    DialogUtil dialogUtil;

    AppController appController;
    ConnectionDetector connectionDetector;
    ArrayList<Image> eventsDetailsData;
    Activity context;
    ArrayList<Image> eventsDetailsImageData;
    SliderHomeAdapter.OnItemClickListener onItemClickListener;
    SliderView sliderView;

    // Constructor
    public SliderHomeAdapter(Activity context, ArrayList<Image> eventsDetailsData,SliderView sliderView) {
        this.eventsDetailsData = eventsDetailsData;
        this.context = context;
        this.sliderView=sliderView;
        user=new User(context);
        dialogUtil = new DialogUtil();

        appController = (AppController) context.getApplicationContext();
        connectionDetector = new ConnectionDetector(context);
    }

    // We are inflating the slider_layout
    // inside on Create View Holder method.
    @Override
    public SliderAdapterViewHolder onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_home_screen_slider, null);
        return new SliderAdapterViewHolder(inflate);
    }

    // Inside on bind view holder we will
    // set data to item of Slider View.
    @Override
    public void onBindViewHolder(SliderAdapterViewHolder viewHolder, int position) {

//        viewHolder.img.setImageResource(eventsDetailsData.get(position).getImage());
        Picasso.with(context).load(AppController.THERAPIST_IMAGE+eventsDetailsData.get(position).getImage_link()).placeholder(R.drawable.tab_selector).into(viewHolder.fontRoundedimg);
        Picasso.with(context).load(AppController.THERAPIST_IMAGE+eventsDetailsData.get(position).getImage_link()).placeholder(R.drawable.tab_selector).transform(new BlurTransformation(context, 25, 1)).into(viewHolder.bgRoundedimg);
//        viewHolder.username.setText(eventsDetailsData.get(position).getName());
//        viewHolder.aboutTxt.setText(eventsDetailsData.get(position).getAboutyou());
//        viewHolder.tittleTxt.setText(eventsDetailsData.get(position).getTittleTxt());
//        viewHolder.descriptionTxt.setText(eventsDetailsData.get(position).getDescriptionTxt());



//        viewHolder.nextTxt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Fragment_Home fragment_home= new Fragment_Home();
////
////                onItemClickListener.onNextClick(position);
//                sliderView.slideToNextPosition();
//
////                fragment_home.setNextButton(context);
//            }
//        });


//
//        viewHolder.sendRequestimg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Fragment_Home fragment_home= new Fragment_Home();
////
////                onItemClickListener.onSendRequest(eventsDetailsData.get(position).getId());
//
//                if (connectionDetector.isConnectingToInternet()) {
//                    String refreshedToken = FirebaseInstanceId.getInstance().getToken();
//                    progress = dialogUtil.showProgressDialog(context, context.getString(R.string.please_wait));
//                    appController.paServices.TherapistSend_Friend_Request(String.valueOf(user.getId()),eventsDetailsData.get(position).getId(),new Callback<Send_Friend_RequestDM>() {
//                        @Override
//                        public void success(Send_Friend_RequestDM send_friend_requestDM, Response response) {
//                            progress.dismiss();
//                            if (send_friend_requestDM.getStatus().equalsIgnoreCase("1")) {
//                                Helper.showToast(context,send_friend_requestDM.getMsg() );
//                                viewHolder.sendRequestimg.setVisibility(View.GONE);
//                               viewHolder.recieveRequestImg.setVisibility(View.VISIBLE);
//
//                            } else
//                                Helper.showToast(context, send_friend_requestDM.getMsg());
//                        }
//
//                        @Override
//                        public void failure(RetrofitError retrofitError) {
//                            progress.dismiss();
//                            Log.e("error", retrofitError.toString());
//                        }
//                    });
//                } else
//                    Helper.showToast(context, String.valueOf(R.string.no_internet_connection));
//
//
//
//
//
////                fragment_home.setNextButton(context);
//                }
//        });
//
//        viewHolder.recieveRequestImg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Fragment_Home fragment_home= new Fragment_Home();
////
////                onItemClickListener.onSendRequest(eventsDetailsData.get(position).getId());
//
//                if (connectionDetector.isConnectingToInternet()) {
//                    String refreshedToken = FirebaseInstanceId.getInstance().getToken();
//                    progress = dialogUtil.showProgressDialog(context,context.getString(R.string.please_wait));
//                    appController.paServices.TherapistCancel_Friend_Request(String.valueOf(user.getId()),eventsDetailsData.get(position).getId(),new Callback<Cancel_Friend_RequestDM>() {
//                        @Override
//                        public void success(Cancel_Friend_RequestDM cancel_friend_requestDM, Response response) {
//                            progress.dismiss();
//                            if (cancel_friend_requestDM.getStatus().equalsIgnoreCase("1")) {
//                                Helper.showToast(context,cancel_friend_requestDM.getMsg() );
//                                viewHolder.sendRequestimg.setVisibility(View.VISIBLE);
//                                viewHolder.recieveRequestImg.setVisibility(View.GONE);
//
//                            } else
//                                Helper.showToast(context, cancel_friend_requestDM.getMsg());
//                        }
//
//                        @Override
//                        public void failure(RetrofitError retrofitError) {
//                            progress.dismiss();
//                            Log.e("error", retrofitError.toString());
//                        }
//                    });
//                } else
//                    Helper.showToast(context,String.valueOf(R.string.no_internet_connection));
//
//
////                fragment_home.setNextButton(context);
//            }
//        });
//
//        Adapter_Category_Interest adapter_category_interest = new Adapter_Category_Interest(context,eventsDetailsData.get(position).getInterests() );
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
//        viewHolder.recyclerView.setLayoutManager(linearLayoutManager);
//        viewHolder.recyclerView.setAdapter(adapter_category_interest);


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

    public void setOnItemClickListener(SliderHomeAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onNextClick(int position);
        void onSendRequest(String id);
        void onCancelRequest(String id);

    }

    static class SliderAdapterViewHolder extends ViewHolder {
        // Adapter class for initializing
        // the views of our slider view.
        View itemView;
        private ImageView img,fontRoundedimg,bgRoundedimg,sendRequestimg,recieveRequestImg;
        private TextView tittleTxt, descriptionTxt, nextTxt,username,aboutTxt;
        RecyclerView recyclerView;
        LinearLayout ll;

        public SliderAdapterViewHolder(View itemView) {
            super(itemView);
            fontRoundedimg = itemView.findViewById(R.id.frontRoundedImg);
            bgRoundedimg = itemView.findViewById(R.id.bgRoundedImg);
//            sendRequestimg = itemView.findViewById(R.id.sendRequestImg);
//            recieveRequestImg  = itemView.findViewById(R.id.recieveRequestImg);
//   //         chatimg = itemView.findViewById(R.id.chatImg);
//            username = itemView.findViewById(R.id.userNameTxt);
//            descriptionTxt = itemView.findViewById(R.id.descriptionTxt);
//            nextTxt = itemView.findViewById(R.id.nextTxt);
//            aboutTxt = itemView.findViewById(R.id.aboutTxt);
//            recyclerView= itemView.findViewById(R.id.categoryRcv);
            ll= itemView.findViewById(R.id.layout_parent);
            this.itemView = itemView;
        }
    }

}
