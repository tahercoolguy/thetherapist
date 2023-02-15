package com.master.design.therapist.Utils;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;

@SuppressLint("SimpleDateFormat")
public class MyCommon {

    public static final int DEFAULT_INT = -9999;
    public static final String DEFAULT_STRING = "";
    public static final float DEFAULT_FLOAT = -0.999f;
    public static final double DEFAULT_DOUBLE = -0.999;
    public static TextView txtTitle;
    public static ImageButton imgBack;


    public static final  String WS_BASE_URL ="http://203.124.98.99/api/" ;
    public static final String CategImagePath ="http://shopch11.com/categpics/";
    public static final String ProductImagePath ="http://shopch11.com/itemimages/";



    public static void setPortraitOrientation(Activity a) {
        a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }



    public static final long number = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;

    public static String getStringFromView(EditText editText) {
        return editText.getText().toString().trim();
    }

    public static String getJsonFromWebServiceResponse(String webServiceResponse) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput(new StringReader(webServiceResponse));
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.TEXT) {
                    return xpp.getText();
                }
                eventType = xpp.next();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return "";
    }







}