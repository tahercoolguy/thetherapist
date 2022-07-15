package com.master.design.blackeye.Activity;

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
import com.master.design.blackeye.Models.User;
import com.master.design.blackeye.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SplashScreen extends AppCompatActivity {
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        user=new User(SplashScreen.this);

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

//             info = getPackageManager().getPackageInfo(
//                    "44:64:0F:46:C1:E8:29:E9:C7:F3:FF:50:5C:EA:80:86:46:0B:67:4C",
//                    PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.i("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//                Log.println(1,"Base64", Base64.encodeToString(md.digest(),Base64.NO_WRAP));
//                Log.println(1,"Base64", Base64.encodeToString(md.digest(),Base64.NO_WRAP));
//                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//                Log.v("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//                Log.w("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//
//            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("Exception NameNotFound","hurray");
        } catch (NoSuchAlgorithmException e) {
            Log.d("Exception NoSuction","hurray");

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
                startActivity(new Intent(SplashScreen.this, Location_Activity.class));
                    finish();
            }
        }, secondsDelayed * 1000);
    }

}
