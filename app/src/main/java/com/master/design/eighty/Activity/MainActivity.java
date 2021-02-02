
package com.master.design.eighty.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.master.design.eighty.Fragments.Fragment_Default;
import com.master.design.eighty.Helper.DialogUtil;
import com.master.design.eighty.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    // Activity request codes
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setDetails();
    }

    public void setDetails()
    {

addFragment(new Fragment_Default(),false);


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

    public void setDrawer()
    {}
    public void setToolBar()
    {}
    public void setOnClickListeners()
    {}

    @Override
    public void onClick(View view) {
    }

    public void setElevation(boolean isElevate) {


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
    public void onBackPressed() {

        int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (backStackEntryCount == 0) {
        } else {
            if (fragment != null && fragment instanceof Fragment_Default) {
                //this.finish();
                exitDialog();


            } else {
                addFragment(new Fragment_Default(), false);
            }
            super.onBackPressed();
        }

    }
}
