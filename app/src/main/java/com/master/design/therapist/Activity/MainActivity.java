
package com.master.design.therapist.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.master.design.therapist.Adapter.Adapter_Menu;
import com.master.design.therapist.Fragments.Fragment_Account;
import com.master.design.therapist.Fragments.Fragment_Chat;
import com.master.design.therapist.Fragments.Fragment_Default;
import com.master.design.therapist.Fragments.Fragment_Friends_Request;
import com.master.design.therapist.Fragments.Fragment_Home;
import com.master.design.therapist.Helper.ContextWrapper;
import com.master.design.therapist.Helper.DialogUtil;
import com.master.design.therapist.Helper.Helper;
import com.master.design.therapist.Helper.User;
import com.master.design.therapist.Models.DrawerMenu;
import com.master.design.therapist.R;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.master.design.therapist.Models.DrawerMenu.RECORD;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
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

    @BindView(R.id.bottomNavigationView)
    BottomNavigationView bottomNavigationView;

//    @BindView(R.id.settingLL)
//    LinearLayout settingLL;
//
//    @BindView(R.id.chatLL)
//    LinearLayout chatLL;
//
//    @BindView(R.id.friendsLL)
//    LinearLayout friendsLL;
//
//    @BindView(R.id.homeLL)
//    LinearLayout homeLL;
//
//    @BindView(R.id.homeImage)
//    ImageView homeImage;
//
//    @BindView(R.id.messageImage)
//    ImageView messageImage;
//
//    @BindView(R.id.friendImage)
//    ImageView friendImage;
//
//    @BindView(R.id.settingsImage)
//    ImageView settingsImage;
//
//    @OnClick(R.id.homeLL)
//    public void clickHomeLL() {
//
//    }
//
//    @OnClick(R.id.friendsLL)
//    public void clickfriendsLL() {
//
//    }
//
//    @OnClick(R.id.chatLL)
//    public void clickchatLL() {
//
//    }
//
//    @OnClick(R.id.settingLL)
//    public void clickSettingLL() {
//
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = new User(this);
        ButterKnife.bind(this);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        addFragment(new Fragment_Home(), false);

        idMappings();
//        setDrawer();
        setToolBar();
        setOnClickListeners();
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
//                drawerNavigationToggle();
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

//    private void setDrawer() {
//        drawer = findViewById(R.id.drawer_layout);
//        drawer.setScrimColor(Color.parseColor("#00FFFFFF"));
//        ListView mDrawerList = findViewById(R.id.listView);
//
//
//        ArrayList<DrawerMenu> menus = new ArrayList<>();
//        menus = new ArrayList<>();
//        menus.add(new DrawerMenu(RECORD, R.drawable.ic_record, "RECORD"));
//        menus.add(new DrawerMenu(DrawerMenu.NEWS, R.drawable.ic_news, "NEWS"));
//        menus.add(new DrawerMenu(DrawerMenu.BUSINESS, R.drawable.ic_business, "BUSINESSES"));
//        menus.add(new DrawerMenu(DrawerMenu.EVENTS, R.drawable.ic_events, "EVENTS"));
//        menus.add(new DrawerMenu(DrawerMenu.JOBS, R.drawable.ic_jobs, "JOBS"));
//        menus.add(new DrawerMenu(DrawerMenu.PROFESSIONALS, R.drawable.ic_professionals, "PROFESSIONALS"));
//
//        menus.add(new DrawerMenu(DrawerMenu.CHAT, R.drawable.ic_chat, "CHAT"));
//        menus.add(new DrawerMenu(DrawerMenu.Contact_Us, R.drawable.ic_contact_us, "CONTACT US"));
//
//
//        View drawer_header_view = getLayoutInflater().inflate(R.layout.layout_drawer_header, null);
//
////        imageProfile = (RoundedImageView) drawer_header_view.findViewById(R.id.imageProfile);
////        textUserName = (TextView) drawer_header_view.findViewById(R.id.textUserName);
//        mDrawerList.addHeaderView(drawer_header_view, null, false);
//        mDrawerList.setAdapter(new Adapter_Menu(this, menus));
//        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
//        drawer_header_view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
//        drawer.addDrawerListener(getToggle());
//    }

//
//    private DrawerLayout.DrawerListener getToggle() {
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        return new ActionBarDrawerToggle(this, drawer, R.string.app_name, R.string.app_name) {
//            public void onDrawerClosed(View view) {
//                supportInvalidateOptionsMenu();
//            }
//
//            public void onDrawerOpened(View drawerView) {
//                supportInvalidateOptionsMenu();
//            }
//
//            @Override
//            public void onDrawerSlide(View drawerView, float slideOffset) {
//                super.onDrawerSlide(drawerView, slideOffset);
//
//                main_view.setTranslationX(slideOffset * drawerView.getWidth());
////                int padding = (int) (slideOffset * (drawerView.getWidth() / 7));
////                main_view.setPadding(0, padding, 0, padding);
//                drawer.bringChildToFront(drawerView);
//                drawer.requestLayout();
//            }
//        };
//    }
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.imageViewBg:
//                drawerNavigationToggle();
//                break;
//        }
//    }

    @SuppressLint({"NonConstantResourceId", "UseCompatLoadingForDrawables", "ResourceType"})
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
//                item.setIcon(getResources().getDrawable(R.drawable.ic_home));
                addFragment(new Fragment_Home(), false);
                return true;

            case R.id.friends:
//                item.setIcon(getResources().getDrawable(R.drawable.ic_handshake));
                addFragment(new Fragment_Friends_Request(), false);
                return true;

            case R.id.message:
//                item.setIcon(getResources().getDrawable(R.drawable.ic_message_select));
                addFragment(new Fragment_Chat(), false);
                return true;

            case R.id.account:
                item.setIcon(getResources().getDrawable(R.drawable.ic_account_select));
                addFragment(new Fragment_Account(), false);
                return true;


        }
        return false;
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

//    @Override
//    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
//            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);
//            if (backStackEntryCount == 0) {
//                if (fragment != null && fragment instanceof Fragment_Default) {
//                    exitDialog();
//                } else {
//                    finish();
//                }
//            } else {
//                super.onBackPressed();
//            }
//        }
//    }

    @Override
    public void onBackPressed() {

        if (bottomNavigationView.getSelectedItemId() == R.id.home) {
            finish();


        } else {
            bottomNavigationView.setSelectedItemId(R.id.home);


        }


    }

    private void exitDialog() {
//        DialogUtil.showDialogTwoButton(this, R.drawable.app_icon, getString(R.string.app_name), getString(R.string.are_you_sure_you_want_to_exit_the_app), getString(R.string.ok), getString(R.string.cancel), new DialogUtil.CallBack() {
//            @Override
//            public void onDismiss(boolean isPressedOK) {
//                if (isPressedOK) {
//                    MainActivity.this.finish();
//                }
//            }
//        });
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


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_in);

    }
}
