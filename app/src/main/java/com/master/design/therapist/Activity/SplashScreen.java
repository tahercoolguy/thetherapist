package com.master.design.therapist.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.master.design.therapist.Models.User;
import com.master.design.therapist.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class SplashScreen extends AppCompatActivity {
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        user = new User(SplashScreen.this);

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
        int secondsDelayed = 2;
        new Handler().postDelayed(new Runnable() {
            public void run() {
//                String Id=user.getCountryId();
//                if(Id.equalsIgnoreCase("0"))
//                {
//                    startActivity(new Intent(SplashScreen.this, AddressSelector.class));
//                    finish();
//                }else {
//                    startActivity(new Intent(SplashScreen.this, AdvertiseSelector.class));
//                    finish();
//                }
//                startActivity(new Intent(SplashScreen.this, MainActivity.class));
               startActivity(new Intent(SplashScreen.this, LanguageActivity.class));
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_in);
            }
        }, secondsDelayed * 1000);
    }

}
