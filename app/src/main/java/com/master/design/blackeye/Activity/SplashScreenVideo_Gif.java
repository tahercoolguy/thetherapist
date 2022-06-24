package com.master.design.blackeye.Activity;

import static android.view.View.GONE;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.iid.FirebaseInstanceId;
import com.master.design.blackeye.Helper.User;
import com.master.design.blackeye.R;
import com.master.design.blackeye.Utils.ConnectionDetector;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SplashScreenVideo_Gif extends AppCompatActivity {
    User user;
    ConnectionDetector connectionDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen_video_or_gif);
        user = new User(SplashScreenVideo_Gif.this);


        setSystemUIFlags(GONE);
        user = new User(SplashScreenVideo_Gif.this);
        view = (VideoView) findViewById(R.id.video_view);
        String path = "android.resource://" + getPackageName() + "/" + R.raw.splash_screen;
        view.setVideoURI(Uri.parse(path));
        view.start();
        connectionDetector = new ConnectionDetector(SplashScreenVideo_Gif.this);

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("Android Token", "Refreshed token: " + refreshedToken);
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    getPackageName(),
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA");
                messageDigest.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(messageDigest.digest(), Base64.DEFAULT));
            }

        } catch (PackageManager.NameNotFoundException e) {
            Log.d("Exception NameNotFound", "hurray");
        } catch (NoSuchAlgorithmException e) {
            Log.d("Exception NoSuction", "hurray");

        }

        view.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                String Id = String.valueOf(user.getId());
                if (Id.equalsIgnoreCase("0")) {
                    startActivity(new Intent(SplashScreenVideo_Gif.this, MainActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(SplashScreenVideo_Gif.this, MainActivity.class));
                    finish();
                }
            }
        });
//
    }

    private void setSystemUIFlags(int visibility) {
        // here we get ourCurrentActivity
        // from it, we grab the window
        // and from that we grab the decorView for our current activity
        View decorView = getWindow().getDecorView();
        // think of the decorView as the eldest ancestor of the UI class familly tree.
        // it contains not only your views, but all current system views
        // eg system alerts and *navigation elements*
        // it contains a method for manipulating some of those system views
        decorView.setSystemUiVisibility(visibility);
    }

    @Override
    protected void onPause() {
        super.onPause();
        view.pause();
    }

    VideoView view;

    @Override
    protected void onResume() {
        super.onResume();

//        String path = "android.resource://" + getPackageName() + "/" + R.raw.video_splash;
//        view.setVideoURI(Uri.parse(path));
        String path = "android.resource://" + getPackageName() + "/" + R.raw.splash_screen;
        view.setVideoURI(Uri.parse(path));
        view.start();
    }
}