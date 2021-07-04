package com.master.design.blackeye.Fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.master.design.blackeye.Activity.MainActivity;
import com.master.design.blackeye.Adapter.Adapter_News;
import com.master.design.blackeye.Adapter.Adapter_Restaurent;
import com.master.design.blackeye.Adapter.Adapter_Shops;
import com.master.design.blackeye.Controller.AppController;
import com.master.design.blackeye.DataModel.BannerDM;
import com.master.design.blackeye.DataModel.NewsDM;
import com.master.design.blackeye.DataModel.RestaurentDM;
import com.master.design.blackeye.DataModel.ShopsDM;
import com.master.design.blackeye.Helper.DialogUtil;
import com.master.design.blackeye.Helper.User;
import com.master.design.blackeye.R;
import com.master.design.blackeye.Utils.ConnectionDetector;
import com.master.design.blackeye.Utils.Helper;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import it.sephiroth.android.library.widget.HListView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Home_Fragment extends Fragment {

    private View rootView;
    private Context context;

    @BindView(R.id.progress_bar)
    ProgressBar progress_bar;
    @BindView(R.id.txt_error)
    TextView txt_error;

    @BindView(R.id.layout_parent)
    androidx.core.widget.NestedScrollView layout_parent;
    private HListView lst_latest_profiles, lst_latest_news, lst_featured_video;
    AppController appController;
    ConnectionDetector connectionDetector;
    ProgressDialog progressDialog;

    @BindView(R.id.newsRV)
    RecyclerView newsRV;

    @BindView(R.id.restaurentRV)
    RecyclerView restaurentRV;

    @BindView(R.id.shopsNearYouRV)
    RecyclerView shopsNearYouRV;

    @OnClick(R.id.newsViewAllTxt)
    public void NewsAll()
    {}
    @OnClick(R.id.restaurentViewAllTxt)
    public void restaurentViewAllTxt()
    {}
    @OnClick(R.id.shopsNearYouViewAllTxt)
    public void shopsNearYouViewAllTxt()
    {}

    @BindView(R.id.banner_img)
    ImageView banner_img;

    @BindView(R.id.banner_txt)
    TextView banner_txt;

    Dialog progress;
    User user;
    DialogUtil dialogUtil;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        context = getActivity();
        appController = (AppController) getActivity().getApplicationContext();

        connectionDetector = new ConnectionDetector(getActivity());
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getResources().getString(R.string.please_wait));
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        ((MainActivity) context).setTitle(getString(R.string.home));
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.home_fragment, container, false);
            ButterKnife.bind(this,rootView);
            idMapping();

            setClickListeners();
            setDetails();
        }
        return rootView;
    }

    private void idMapping() {


    }

    private void setClickListeners() {

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void setDetails() {
        ShowProgress();
      Binding();
        rootView.postDelayed(new Runnable() {
            @Override
            public void run() {
                DismissProgress();
            }
        }, 1500);



    }

    public void Binding()
    {
        if(connectionDetector.isConnectingToInternet())
        {
            appController.paServices.Banner(new Callback<BannerDM>() {
                @Override
                public void success(BannerDM bannerDM, Response response) {
                    if(bannerDM.getStatus().equalsIgnoreCase("1"))
                    {
                        Picasso.with(getActivity()).load(bannerDM.getResult().getBanner_file()).into(banner_img);
                        banner_txt.setText(bannerDM.getResult().getTitle());
                    }
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });

            appController.paServices.News(new Callback<NewsDM>() {
                @Override
                public void success(NewsDM newsDM, Response response) {
                    if(newsDM.getStatus().equalsIgnoreCase("1"))
                    {
                        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL,false);
                        newsRV.setLayoutManager(linearLayoutManager);
                        newsRV.setAdapter(new Adapter_News(getActivity(), newsDM.getResult()));
                    }
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });


            appController.paServices.Restaurent(new Callback<RestaurentDM>() {
                @Override
                public void success(RestaurentDM restaurentDM, Response response) {
                    if(restaurentDM.getStatus().equalsIgnoreCase("1"))
                    {
                        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL,false);
                        restaurentRV.setLayoutManager(linearLayoutManager);
                        restaurentRV.setAdapter(new Adapter_Restaurent(getActivity(), restaurentDM.getResult()));
                    }
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });

            appController.paServices.Shops(new Callback<ShopsDM>() {
                @Override
                public void success(ShopsDM shopsDM, Response response) {
                    if(shopsDM.getStatus().equalsIgnoreCase("1"))
                    {
                        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL,false);
                        shopsNearYouRV.setLayoutManager(linearLayoutManager);
                        shopsNearYouRV.setAdapter(new Adapter_Shops(getActivity(), shopsDM.getResult()));

                    }
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });
        }else
            Helper.showToast(getActivity(),getString(R.string.no_internet_connection));
    }

    public void ShowProgress()
    {
        progress_bar.setVisibility(View.VISIBLE);
        txt_error.setVisibility(View.GONE);
        layout_parent.setVisibility(View.GONE);
    }

    public void DismissProgress()
    {
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
}