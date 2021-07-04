
package com.master.design.blackeye.Activity;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.makeramen.roundedimageview.RoundedImageView;
import com.master.design.blackeye.Adapter.Adapter_Menu;
import com.master.design.blackeye.Fragments.Fragment_Default;
import com.master.design.blackeye.Fragments.Home_Fragment;
import com.master.design.blackeye.Helper.ContextWrapper;
import com.master.design.blackeye.Helper.DialogUtil;
import com.master.design.blackeye.Helper.Helper;
import com.master.design.blackeye.Helper.User;
import com.master.design.blackeye.Models.DrawerMenu;
import com.master.design.blackeye.R;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.OnClick;

import static com.master.design.blackeye.Models.DrawerMenu.RECORD;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;

    // key to store image path in savedInstance state
    public static final String KEY_IMAGE_STORAGE_PATH = "image_path";

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    // Bitmap sampling size
    public static final int BITMAP_SAMPLE_SIZE = 8;

    // Gallery directory name to store the images or videos
    public static final String GALLERY_DIRECTORY_NAME = "Hello Camera";

    // Image and Video file extensions
    public static final String IMAGE_EXTENSION = "jpg";
    public static final String VIDEO_EXTENSION = "mp4";
    private DrawerLayout drawer;
    private RelativeLayout main_view;
    private AppBarLayout app_bar;
    private TextView txt_title, textUserName;
    private RoundedImageView imageProfile;

    private User user;

    @OnClick(R.id.HomeLL)
    public void dashBoard(){
//        addFragment(new DashboardFragment(),false);

    }

    @OnClick(R.id.MenuLL)
    public void selfService(){
//        addFragment(new SelfServiceFragment(),false);

    }

    @OnClick(R.id.reportLL)
    public void reportIncident(){
//        Intent intent=new Intent(MainActivity.this,IncidentActivity.class);
//        startActivity(intent);

    }


    @OnClick(R.id.profileLL)
    public void eForm(){
//        addFragment(new EFormFragment(),false);
    }


    @OnClick(R.id.SettingLL)
    public void More(){
//        addFragment(new more_fragment(),false);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = new User(this);
        idMappings();
        setDrawer();
        setToolBar();
        setOnClickListeners();
        addFragment(new Home_Fragment(), false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setDetails();
    }

    public void setDetails() {

    }

    private void setOnClickListeners() {

    }

    private void idMappings() {
        txt_title = findViewById(R.id.txt_title);
        main_view = findViewById(R.id.main_view);
        app_bar = (AppBarLayout) findViewById(R.id.app_bar);
    }

    private void setToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_icon);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerNavigationToggle();
            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_icon);
            getSupportActionBar().setTitle(getString(R.string.app_name));
            setElevation(true);
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        txt_title.setText(title);
    }

    private void drawerNavigationToggle() {
        if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else
            drawer.openDrawer(GravityCompat.START);
    }

    private void setDrawer() {
        drawer = findViewById(R.id.drawer_layout);
        drawer.setScrimColor(Color.parseColor("#00FFFFFF"));
        ListView mDrawerList = findViewById(R.id.listView);


        ArrayList<DrawerMenu> menus = new ArrayList<>();
        menus = new ArrayList<>();
        menus.add(new DrawerMenu(RECORD,R.drawable.ic_record,"RECORD"));
        menus.add(new DrawerMenu(DrawerMenu.NEWS,R.drawable.ic_news,"NEWS"));
        menus.add(new DrawerMenu(DrawerMenu.BUSINESS,R.drawable.ic_business,"BUSINESSES"));
        menus.add(new DrawerMenu(DrawerMenu.EVENTS,R.drawable.ic_events,"EVENTS"));
        menus.add(new DrawerMenu(DrawerMenu.JOBS,R.drawable.ic_jobs,"JOBS"));
        menus.add(new DrawerMenu(DrawerMenu.PROFESSIONALS,R.drawable.ic_professionals,"PROFESSIONALS"));

        menus.add(new DrawerMenu(DrawerMenu.CHAT,R.drawable.ic_chat,"CHAT"));
        menus.add(new DrawerMenu(DrawerMenu.Contact_Us,R.drawable.ic_contact_us,"CONTACT US"));



        View drawer_header_view = getLayoutInflater().inflate(R.layout.layout_drawer_header, null);

//        imageProfile = (RoundedImageView) drawer_header_view.findViewById(R.id.imageProfile);
//        textUserName = (TextView) drawer_header_view.findViewById(R.id.textUserName);
        mDrawerList.addHeaderView(drawer_header_view, null, false);
        mDrawerList.setAdapter(new Adapter_Menu(this, menus));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        drawer_header_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        drawer.addDrawerListener(getToggle());
    }


    private DrawerLayout.DrawerListener getToggle() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        return new ActionBarDrawerToggle(this, drawer, R.string.app_name, R.string.app_name) {
            public void onDrawerClosed(View view) {
                supportInvalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);

                main_view.setTranslationX(slideOffset * drawerView.getWidth());
//                int padding = (int) (slideOffset * (drawerView.getWidth() / 7));
//                main_view.setPadding(0, padding, 0, padding);
                drawer.bringChildToFront(drawerView);
                drawer.requestLayout();
            }
        };
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageViewBg:
                drawerNavigationToggle();
                break;
        }
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            Helper.hideSoftKeyboard(MainActivity.this);
            DrawerMenu menu = (DrawerMenu) adapterView.getItemAtPosition(position);
            Fragment fragment = null;
            switch (menu.getId()) {
                case RECORD:
//                    fragment = new HomeFragment();
                    break;

                default:
                    break;
            }
            if (fragment != null) {
                addFragment(fragment, false);
            }
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);
            if (backStackEntryCount == 0) {
                if (fragment != null && fragment instanceof Fragment_Default) {
                    exitDialog();
                } else {
                    addFragment(new Fragment_Default(), false);
                }
            } else {
                super.onBackPressed();
            }
        }
    }

    private void exitDialog() {
        DialogUtil.showDialogTwoButton(this, R.drawable.app_icon, getString(R.string.app_name), getString(R.string.are_you_sure_you_want_to_exit_the_app), getString(R.string.ok), getString(R.string.cancel), new DialogUtil.CallBack() {
            @Override
            public void onDismiss(boolean isPressedOK) {
                if (isPressedOK) {
                    MainActivity.this.finish();
                }
            }
        });
    }

    public void addFragment(Fragment fragment, boolean addToStack) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment, fragment.getClass().getName());
        if (addToStack) {
            fragmentTransaction.addToBackStack(fragment.getClass().getName());
        }
        fragmentTransaction.commitAllowingStateLoss();
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ContextWrapper.wrap(newBase, new Locale(new User(newBase).getLanguageCode())));
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_back:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setElevation(boolean isElevate) {
        if (isElevate) {
            ViewCompat.setElevation(findViewById(R.id.app_bar), getResources().getDimension(R.dimen.elevation));
        } else {
            ViewCompat.setElevation(findViewById(R.id.app_bar), 0f);
        }
    }
}
