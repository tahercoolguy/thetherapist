package com.master.design.therapist.Fragments;

import static com.facebook.FacebookSdk.getApplicationContext;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.iid.FirebaseInstanceId;
import com.makeramen.roundedimageview.RoundedImageView;
import com.master.design.therapist.Activity.Create_Account_Activity;
import com.master.design.therapist.Activity.FriendSearchActivity;
import com.master.design.therapist.Activity.FriendSearch_SelectActivity;
import com.master.design.therapist.Activity.MainActivity;
import com.master.design.therapist.Activity.MyPostedImagesActivity;
import com.master.design.therapist.Adapter.Adapter_Category_Interest;
import com.master.design.therapist.Adapter.SliderAdapter;
import com.master.design.therapist.Adapter.SliderHomeAdapter;
import com.master.design.therapist.Controller.AppController;
import com.master.design.therapist.DM.InterestDM;
import com.master.design.therapist.DM.IntroSliderDM;
import com.master.design.therapist.DataModel.AddMultipleImageRoot;
import com.master.design.therapist.DataModel.Cancel_Friend_RequestDM;
import com.master.design.therapist.DataModel.Request_ResponseDM;
import com.master.design.therapist.DataModel.Send_Friend_RequestDM;
import com.master.design.therapist.DataModel.TherapistEthnicDM;
import com.master.design.therapist.DataModel.TherapistHomeDM;
import com.master.design.therapist.DataModel.Users;
import com.master.design.therapist.Helper.BlurBuilder;
import com.master.design.therapist.Helper.DialogUtil;
import com.master.design.therapist.Helper.Helper;
import com.master.design.therapist.Helper.User;
import com.master.design.therapist.R;
import com.master.design.therapist.Utils.ConnectionDetector;
import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import it.sephiroth.android.library.widget.HListView;
import me.echodev.resizer.Resizer;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.MultipartTypedOutput;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedString;

public class Fragment_Home extends Fragment {

    private View rootView;
    private Context context;
    private Activity activity;

    @BindView(R.id.progress_bar)
    ProgressBar progress_bar;
    @BindView(R.id.txt_error)
    TextView txt_error;

    @BindView(R.id.sendRequestImg)
    ImageView sendRequestImg;
    @BindView(R.id.nextTxt)
    TextView nextTxt;
    @BindView(R.id.userNameTxt)
    TextView userNameTxt;
    @BindView(R.id.aboutTxt)
    TextView aboutTxt;
    @BindView(R.id.chatImg)
    ImageView chatImg;
    @BindView(R.id.categoryRcv)
    RecyclerView categoryRcv;
    @BindView(R.id.changeSearchTxt)
    ImageView changeSearchTxt;
    @BindView(R.id.recieveRequestImg)
    ImageView recieveRequestImg;
    @BindView(R.id.bgRoundedImg)
    RoundedImageView bgRoundedImg;
    @BindView(R.id.frontRoundedImg)
    RoundedImageView frontRoundedImg;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.mainLLL)
    LinearLayout mainLLL;

    @BindView(R.id.layout_parent)
    LinearLayout layout_parent;
    private HListView lst_latest_profiles, lst_latest_news, lst_featured_video;
    AppController appController;
    ConnectionDetector connectionDetector;
    ProgressDialog progressDialog;
    Dialog progress;
    DialogUtil dialogUtil;
    User user;
    String anotherUserId;


    @BindView(R.id.slider)
    SliderView slider;
//    @BindView(R.id.swiperefresh)
//    SwipeRefreshLayout swiperefresh;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        context = getActivity();
        activity = getActivity();
        appController = (AppController) getActivity().getApplicationContext();

        connectionDetector = new ConnectionDetector(getActivity());
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getResources().getString(R.string.please_wait));
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);

        ((MainActivity) context).setTitle(getString(R.string.home));
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.home_fragment_layout, container, false);
            ButterKnife.bind(this, rootView);
            user = new User(getActivity());
            dialogUtil = new DialogUtil();
            mainLLL.setVisibility(View.GONE);
//            Bitmap resultBmp = BlurBuilder.blur(getActivity(), BitmapFactory.decodeResource(getResources(), R.drawable.marshall_img));
//            Drawable dr = new BitmapDrawable(resultBmp);
//            bgRoundedImg.setImageDrawable(dr);

//            setInterestData();

            setDetails();
            setsliderData();

            /*
             * Sets up a SwipeRefreshLayout.OnRefreshListener that is invoked when the user
             * performs a swipe-to-refresh gesture.
             */
//            swiperefresh.setOnRefreshListener(
//                    new SwipeRefreshLayout.OnRefreshListener() {
//                        @Override
//                        public void onRefresh() {
//
//                            // This method performs the actual data-refresh operation.
//                            // The method calls setRefreshing(false) when it's finished.
//                            setsliderData();
//                        }
//                    }
//            );

        }
        return rootView;
    }


    @OnClick(R.id.sendRequestImg)
    public void clicksendRequestImg() {
        SendRequestBinding();
        //        sendRequestImg.setVisibility(View.GONE);
//        recieveRequestImg.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.recieveRequestImg)
    public void clickrecieveRequestImg() {
        cancelRequestBinding();
        //        sendRequestImg.setVisibility(View.GONE);
//        recieveRequestImg.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.chatImg)
    public void clickchatImg() {

        ((MainActivity) context).showdialogNoData(context, getString(R.string.chat), getString(R.string.home_screen_chat_messege));
    }

    int listposition = 0;
    String nextUserID;

    @OnClick(R.id.nextTxt)
    public void clicknextTxt() {
        listposition = listposition + 1;
        int listSize = therapistHomeDMPosition.getUsers().size();
        if (listposition < listSize) {
            nextUserID = therapistHomeDMPosition.getUsers().get(listposition).getId();
            userNameTxt.setText(therapistHomeDMPosition.getUsers().get(listposition).getName());
            aboutTxt.setText(therapistHomeDMPosition.getUsers().get(listposition).getAboutyou());
            sendRequestImg.setVisibility(View.VISIBLE);
            recieveRequestImg.setVisibility(View.GONE);

            Adapter_Category_Interest adapter_category_interest = new Adapter_Category_Interest(context, therapistHomeDMPosition.getUsers().get(listposition).getInterests());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            categoryRcv.setLayoutManager(linearLayoutManager);
            categoryRcv.setAdapter(adapter_category_interest);

            SliderHomeAdapter imageadapter = new SliderHomeAdapter(activity, therapistHomeDMPosition.getUsers().get(listposition).getImage(), slider);

//             below method is used to set auto cycle direction in left to
//             right direction you can change according to requirement.
            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.left_slide_in);
            slider.startAnimation(animation);
            slider.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);


            imageadapter.setOnItemClickListener(new SliderHomeAdapter.OnItemClickListener() {
                @Override
                public void onNextClick(int position) {

                    //               slider.slideToNextPosition();

                }

                @Override
                public void onSendRequest(String id) {

//               SendRequestBinding(id);

                }

                @Override
                public void onCancelRequest(String id) {

                    //               cancelRequestBinding(id);

                }
            });


            // below method is used to
            // setadapter to sliderview.
            slider.setSliderAdapter(imageadapter);
            slider.setInfiniteAdapterEnabled(true);
            // below method is use to set
            // scroll time in seconds.

            slider.setScrollTimeInSec(2);
//
//                        // to set it scrollable automatically
//                        // we use below method.

            slider.setAutoCycle(true);
//
//                        // to start autocycle below method is used.

            slider.startAutoCycle();


        } else {
            listposition = 0;
            setsliderData();
        }

    }

    @OnClick(R.id.changeSearchTxt)
    public void clickchangeSearchTxt() {
//        startActivity(new Intent(context, FriendSearchActivity.class));
//        activity.overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);
        ((MainActivity) context).searchDataHomeFragment(context);

    }

//    private void setInterestData() {
//
////        ArrayList<InterestDM> interestDMArrayList = new ArrayList<>();
////        interestDMArrayList.add(new InterestDM("Movies", R.drawable.ic_add_image));
////        interestDMArrayList.add(new InterestDM("Study", R.drawable.ic_add_image));
////        interestDMArrayList.add(new InterestDM("Music", R.drawable.ic_add_image));
////        interestDMArrayList.add(new InterestDM("Songs", R.drawable.ic_add_image));
////        interestDMArrayList.add(new InterestDM("Cricket", R.drawable.ic_add_image));
////        interestDMArrayList.add(new InterestDM("Travel", R.drawable.ic_add_image));
////        interestDMArrayList.add(new InterestDM("Pets", R.drawable.ic_add_image));
////        interestDMArrayList.add(new InterestDM("Bikes", R.drawable.ic_add_image));
////        interestDMArrayList.add(new InterestDM("Movies", R.drawable.ic_add_image));
////        interestDMArrayList.add(new InterestDM("Study", R.drawable.ic_add_image));
////        interestDMArrayList.add(new InterestDM("Music", R.drawable.ic_add_image));
////        interestDMArrayList.add(new InterestDM("Songs", R.drawable.ic_add_image));
////        interestDMArrayList.add(new InterestDM("Cricket", R.drawable.ic_add_image));
////        interestDMArrayList.add(new InterestDM("Travel", R.drawable.ic_add_image));
////        interestDMArrayList.add(new InterestDM("Pets", R.drawable.ic_add_image));
////        interestDMArrayList.add(new InterestDM("Bikes", R.drawable.ic_add_image));
//
//        if (connectionDetector.isConnectingToInternet()) {
//            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
//            //           progress = dialogUtil.showProgressDialog(Create_Account_Activity.this, getString(R.string.please_wait));
//            appController.paServices.TherapistHome(String.valueOf(user.getId()), new Callback<TherapistHomeDM>() {
//                @Override
//                public void success(TherapistHomeDM therapistHomeDM, Response response) {
//                    //                   progress.dismiss();
//                    if (therapistHomeDM.getStatus().equalsIgnoreCase("1")) {
//
//                        Picasso.with(context).load(AppController.THERAPIST_IMAGE + therapistHomeDM.getUsers().get(0).getImage()).into(frontRoundedImg);
//                        userNameTxt.setText(therapistHomeDM.getUsers().get(0).getName());
//                        aboutTxt.setText(therapistHomeDM.getUsers().get(0).getAboutyou());
//
//                        Adapter_Category_Interest adapter_category_interest = new Adapter_Category_Interest(context, therapistHomeDM.getUsers().get(0).getInterests());
//                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
//                        categoryRcv.setLayoutManager(linearLayoutManager);
//                        categoryRcv.setAdapter(adapter_category_interest);
//
//                    } else {
//
//                    }
////                        Helper.showToast(context, getString(R.string.Api_data_not_found));
//                }
//
//                @Override
//                public void failure(RetrofitError retrofitError) {
//                    Log.e("error", retrofitError.toString());
//                }
//            });
//        } else
//            Helper.showToast(context, getString(R.string.no_internet_connection));
////
//
//    }


    @Override
    public void onResume() {
        super.onResume();
    }

    private void setDetails() {
        ShowProgress();
        rootView.postDelayed(new Runnable() {
            @Override
            public void run() {
                DismissProgress();
            }
        }, 800);


    }

    public void ShowProgress() {
        progress_bar.setVisibility(View.VISIBLE);
        txt_error.setVisibility(View.GONE);
        layout_parent.setVisibility(View.GONE);
    }

    public void DismissProgress() {
        progress_bar.setVisibility(View.GONE);
        txt_error.setVisibility(View.GONE);
        layout_parent.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.action_back).setVisible(false);
    }


    public void SendRequestBinding() {
        if (connectionDetector.isConnectingToInternet()) {
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
            progress = dialogUtil.showProgressDialog(context, getString(R.string.please_wait));
            appController.paServices.TherapistSend_Friend_Request(String.valueOf(user.getId()), nextUserID, new Callback<Send_Friend_RequestDM>() {
                @Override
                public void success(Send_Friend_RequestDM send_friend_requestDM, Response response) {
                    progress.dismiss();
                    if (send_friend_requestDM.getStatus().equalsIgnoreCase("1")) {
                        Helper.showToast(context, send_friend_requestDM.getMsg());
                        sendRequestImg.setVisibility(View.GONE);
                        recieveRequestImg.setVisibility(View.VISIBLE);

                    } else {
                        Helper.showToast(context, send_friend_requestDM.getMsg());
                    }
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    progress.dismiss();
                    Log.e("error", retrofitError.toString());
                }
            });
        } else
            Helper.showToast(context, getString(R.string.no_internet_connection));

    }

    TherapistHomeDM therapistHomeDMPosition;

    public void setsliderData() {

//        ArrayList<IntroSliderDM> introSliderDMArrayList = new ArrayList<>();
//        introSliderDMArrayList.add(new IntroSliderDM(getString(R.string.intro1_head) +
//                getString(R.string.intro1_head_part2), getString(R.string.intro_1), R.drawable.ic_intro_one));
//        introSliderDMArrayList.add(new IntroSliderDM(getString(R.string.intro_2_head) +
//                getString(R.string.intro2_headpart2), getString(R.string.intro2), R.drawable.ic_intro_two));

        if (connectionDetector.isConnectingToInternet()) {
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
            //           progress = dialogUtil.showProgressDialog(Create_Account_Activity.this, getString(R.string.please_wait));
            String id = String.valueOf(user.getId());
            appController.paServices.TherapistHome(id, new Callback<TherapistHomeDM>() {
                @Override
                public void success(TherapistHomeDM therapistHomeDM, Response response) {
                    //                   progress.dismiss();
                    if (therapistHomeDM.getStatus().equalsIgnoreCase("1")) {
                        try {
                            therapistHomeDMPosition = therapistHomeDM;
                            nextUserID = therapistHomeDMPosition.getUsers().get(0).getId();

//                        Picasso.with(context).load("http://207.154.215.156:8000" + therapistHomeDM.getUsers().get(0).getImage()).into(frontRoundedImg);
                            userNameTxt.setText(therapistHomeDM.getUsers().get(0).getName());
                            aboutTxt.setText(therapistHomeDM.getUsers().get(0).getAboutyou());


                            Adapter_Category_Interest adapter_category_interest = new Adapter_Category_Interest(context, therapistHomeDM.getUsers().get(0).getInterests());
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                            categoryRcv.setLayoutManager(linearLayoutManager);
                            categoryRcv.setAdapter(adapter_category_interest);

                            SliderHomeAdapter imageadapter = new SliderHomeAdapter(activity, therapistHomeDM.getUsers().get(0).getImage(), slider);

                            // below method is used to set auto cycle direction in left to
                            // right direction you can change according to requirement.
                            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.left_slide_in);
                            slider.startAnimation(animation);
                            slider.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);


                            imageadapter.setOnItemClickListener(new SliderHomeAdapter.OnItemClickListener() {
                                @Override
                                public void onNextClick(int position) {

                                    //               slider.slideToNextPosition();

                                }

                                @Override
                                public void onSendRequest(String id) {

//               SendRequestBinding(id);

                                }

                                @Override
                                public void onCancelRequest(String id) {

                                    //               cancelRequestBinding(id);

                                }
                            });


                            // below method is used to
                            // setadapter to sliderview.
                            slider.setSliderAdapter(imageadapter);
                            slider.setInfiniteAdapterEnabled(true);
                            // below method is use to set
                            // scroll time in seconds.

                            slider.setScrollTimeInSec(2);
//
//                        // to set it scrollable automatically
//                        // we use below method.

                            slider.setAutoCycle(true);
//
//                        // to start autocycle below method is used.

                            slider.startAutoCycle();

                            mainLLL.setVisibility(View.VISIBLE);

                        } catch (Exception e) {
                            ll.setVisibility(View.GONE);
                            e.printStackTrace();
                        }


                    } else {
//                         Helper.showToast(context, getString(R.string.Api_data_not_found));
                        mainLLL.setVisibility(View.GONE);
                        ((MainActivity) context).showdialogNoData(context, getString(R.string.best_matches), getString(R.string.no_user_exist));
                    }
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    Log.e("error", retrofitError.toString());
                }
            });
        } else
            Helper.showToast(context, getString(R.string.no_internet_connection));
    }

//    public void setNextButton(Context context){
//        slider.slideToNextPosition();
//    }

    public void cancelRequestBinding() {
        if (connectionDetector.isConnectingToInternet()) {
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
            progress = dialogUtil.showProgressDialog(context, getString(R.string.please_wait));
            appController.paServices.TherapistCancel_Friend_Request(String.valueOf(user.getId()), nextUserID, new Callback<Cancel_Friend_RequestDM>() {
                @Override
                public void success(Cancel_Friend_RequestDM cancel_friend_requestDM, Response response) {
                    progress.dismiss();
                    if (cancel_friend_requestDM.getStatus().equalsIgnoreCase("1")) {
                        Helper.showToast(context, cancel_friend_requestDM.getMsg());
                        sendRequestImg.setVisibility(View.VISIBLE);
                        recieveRequestImg.setVisibility(View.GONE);

                    } else
                        Helper.showToast(context, cancel_friend_requestDM.getMsg());
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    progress.dismiss();
                    Log.e("error", retrofitError.toString());
                }
            });
        } else
            Helper.showToast(context, getString(R.string.no_internet_connection));

    }

    public void searchAPI(String selected_ageId, String selected_genderId, String selected_ethicID, String selected_educationID, String interestIdList) {
        if (connectionDetector.isConnectingToInternet()) {
//            String age_id,  gender, interest = null, ethic = null, education = null;

//            age_id=((MainActivity)context).age_id;
//            gender=((MainActivity)context).gender;
//            interest=((MainActivity)context).interest;
//            ethic=((MainActivity)context).ethic;
//            education=((MainActivity)context).education;

            MultipartTypedOutput multipartTypedOutput = new MultipartTypedOutput();
            multipartTypedOutput.addPart("id", new TypedString(String.valueOf(user.getId())));
            multipartTypedOutput.addPart("age_range", new TypedString(selected_ageId));
            multipartTypedOutput.addPart("gender", new TypedString(selected_genderId));
            if (interestIdList.equalsIgnoreCase("")){
                multipartTypedOutput.addPart("interest", new TypedString(interestIdList));
            }
            if (selected_ethicID != null || !selected_ethicID.equalsIgnoreCase("")){
                multipartTypedOutput.addPart("ethnic", new TypedString(selected_ethicID));
            }

            if (selected_educationID != null || !selected_educationID.equalsIgnoreCase("")) {
                multipartTypedOutput.addPart("education", new TypedString(selected_educationID));
            }

//            progress = dialogUtil.showProgressDialog(My_ProfileActivity.this, getString(R.string.please_wait));

            appController.paServices.Search_User(multipartTypedOutput, new Callback<TherapistHomeDM>() {
                @Override
                public void success(TherapistHomeDM therapistHomeDM, Response response) {
//                    progress.dismiss();

                    if (therapistHomeDM.getStatus().equalsIgnoreCase("1")) {
                        try {
                            therapistHomeDMPosition = therapistHomeDM;
                            nextUserID = therapistHomeDMPosition.getUsers().get(0).getId();

//                        Picasso.with(context).load("http://207.154.215.156:8000" + therapistHomeDM.getUsers().get(0).getImage()).into(frontRoundedImg);
                            userNameTxt.setText(therapistHomeDM.getUsers().get(0).getName());
                            aboutTxt.setText(therapistHomeDM.getUsers().get(0).getAboutyou());


                            Adapter_Category_Interest adapter_category_interest = new Adapter_Category_Interest(context, therapistHomeDM.getUsers().get(0).getInterests());
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                            categoryRcv.setLayoutManager(linearLayoutManager);
                            categoryRcv.setAdapter(adapter_category_interest);

                            SliderHomeAdapter imageadapter = new SliderHomeAdapter(activity, therapistHomeDM.getUsers().get(0).getImage(), slider);

                            // below method is used to set auto cycle direction in left to
                            // right direction you can change according to requirement.
                            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.left_slide_in);
                            slider.startAnimation(animation);
                            slider.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);


                            imageadapter.setOnItemClickListener(new SliderHomeAdapter.OnItemClickListener() {
                                @Override
                                public void onNextClick(int position) {

                                    //               slider.slideToNextPosition();

                                }

                                @Override
                                public void onSendRequest(String id) {

//               SendRequestBinding(id);

                                }

                                @Override
                                public void onCancelRequest(String id) {

                                    //               cancelRequestBinding(id);

                                }
                            });


                            // below method is used to
                            // setadapter to sliderview.
                            slider.setSliderAdapter(imageadapter);
                            slider.setInfiniteAdapterEnabled(true);
                            // below method is use to set
                            // scroll time in seconds.

                            slider.setScrollTimeInSec(2);
//
//                        // to set it scrollable automatically
//                        // we use below method.

                            slider.setAutoCycle(true);
//
//                        // to start autocycle below method is used.

                            slider.startAutoCycle();

                            mainLLL.setVisibility(View.VISIBLE);

                        } catch (Exception e) {
                            ll.setVisibility(View.GONE);
                            e.printStackTrace();
                        }


                    } else {
//                         Helper.showToast(context, getString(R.string.Api_data_not_found));
                        mainLLL.setVisibility(View.GONE);
                        ((MainActivity) context).showdialogNoData(context, getString(R.string.best_matches), getString(R.string.no_user_exist));
                    }
                }

                @Override
                public void failure(RetrofitError retrofitError) {
//                    progress.dismiss();

                    Log.e("error", retrofitError.toString());

                }
            });
        } else
            Helper.showToast(getActivity(), getString(R.string.no_internet_connection));
    }
    String selected_ageId, selected_genderId, selected_ethicID, selected_educationID, InterestIdList = "";

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==3){
            if(data!=null){
                selected_ageId = data.getStringExtra("selected_ageId");
                selected_genderId = data.getStringExtra("selected_genderId");
                selected_ethicID = data.getStringExtra("selected_ethicID");
                selected_educationID = data.getStringExtra("selected_educationID");
                InterestIdList = data.getStringExtra("InterestIdList");
                searchAPI(selected_ageId,selected_genderId,selected_ethicID,selected_educationID,InterestIdList);

            }
        }
    }
}
