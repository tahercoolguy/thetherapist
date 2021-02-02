package com.master.design.eighty.Utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;


import com.master.design.eighty.BuildConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.core.content.FileProvider;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

/**
 * Created by Dakshay Sanghvi on 9/20/2016.
 */

public class Util {

    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_IMAGE_FROM_GALLERY = 2;
    public static final int CROP_REQUEST = 3;
    public static final int TAKE_VIDEO = 4;
    public static final int CHOOSE_VIDEO_FROM_GALLERY = 5;

    public static final String VIDEO_SUFFIX = ".mp4";
    public static final String IMAGE_SUFFIX = ".jpg";

    public static final String SFD = "SFD";
    public static final String SFND = "SFND";
    public static final String NRF = "NRF";

    public static final String ZERO_DECIMAL = "%.0f";
    public static final String ONE_DECIMAL = "%.1f";
    public static final String TWO_DECIMAL = "%.2f";
    private static final String THREE_DECIMAL = "%.3f";
    private static final String FOLDER_NAME = "/Player Agent/";


    /*public static String getFormattedPrice(Context context, double price) {
        return String.format(Locale.ENGLISH, THREE_DECIMAL + " %s", price, context.getResources().getString(R.string.KD));
    }

    public static String getFormattedPrice(Context context, double price, int currency) {
        return String.format(Locale.ENGLISH, THREE_DECIMAL + " %s", price, context.getResources().getString(currency));
    }

    public static String getFormattedPrice(Context context, double price, String currency) {
        return String.format(Locale.ENGLISH, THREE_DECIMAL + " %s", price, currency);
    }
*/
    public static boolean isNumber(String raw) {
        return raw.matches("[0-9]+") && raw.length() > 2;
    }

    public static String formatUrl(String url, ArrayList<String> parameters) {
        StringBuilder sb = new StringBuilder();
        sb.append(url);
        for (String string : parameters) {
            sb.append("/");
            sb.append(string);
        }

        return sb.toString();
    }

    static String formatUrl(String url, String... strings) {
        StringBuilder sb = new StringBuilder();
        sb.append(url);
        for (String string : strings) {
            sb.append("/");
            sb.append(string);
        }

        return sb.toString();
    }

    public static String encode(String text) {
        try {
            return URLEncoder.encode(text, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return text;
    }

    public static void setConfigChange(Context context, String langCode) {
        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
        //  wrap(context, langCode);

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Locale locale = new Locale(langCode);
            Locale.setDefault(locale);
            Configuration configuration = context.getResources().getConfiguration();
            configuration.setLocale(locale);
            context.createConfigurationContext(configuration);

            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            android.content.res.Configuration conf = context.getResources().getConfiguration();
            conf.locale = new Locale(getLanguage_Code());
            context.getResources().updateConfiguration(conf, dm);
        } else {
            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            Configuration conf = context.getResources().getConfiguration();
            conf.locale = new Locale(langCode);
            context.getResources().updateConfiguration(conf, dm);
        }*/
    }

    public static void refreshFragment(Activity activity) {
        Intent intent = activity.getIntent();
        activity.finish();
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        activity.startActivity(intent);
        activity.overridePendingTransition(0, 0);
    }


    public static String getUniquePsuedoID() {
        return "35" + //we make this look like a valid IMEI
                Build.BOARD.length() % 10 + Build.BRAND.length() % 10 +
                Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 +
                Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 +
                Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 +
                Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 +
                Build.TAGS.length() % 10 + Build.TYPE.length() % 10 +
                Build.USER.length() % 10;
    }

    static File createNewFile(String prefix, String extension) {
        if (prefix == null || prefix.isEmpty()) {
            prefix = "IMG_";
        }

        File newDirectory = new File(Environment.getExternalStorageDirectory() + FOLDER_NAME);
        if (!newDirectory.exists()) {
            if (newDirectory.mkdir()) {
                Log.d("Util", newDirectory.getAbsolutePath() + " directory created");
            }
        }

        return new File(newDirectory, (prefix + System.currentTimeMillis() + extension));
    }

    public static File createNewFile(String prefix) {
        if (prefix == null || prefix.isEmpty()) {
            prefix = "IMG_";
        }

        File newDirectory = new File(Environment.getExternalStorageDirectory() + FOLDER_NAME);
        if (!newDirectory.exists()) {
            if (newDirectory.mkdir()) {
                Log.d("Util", newDirectory.getAbsolutePath() + " directory created");
            }
        }

        return new File(newDirectory, (prefix + System.currentTimeMillis() + IMAGE_SUFFIX));
    }


    public static boolean isValidEmail(String text) {
        return text != null && android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches();
    }

  /*  public static Uri getURI(Context context, File file) {
        return FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", file);

    }*/

    public static void cropCapturedImage(Fragment fragment, Uri photoURI) {
        //grant uri with essential permission the first arg is the The packagename you would like to allow to access the Uri.
        fragment.getActivity().grantUriPermission("com.android.camera", photoURI, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(photoURI, "image/*");

        //you must setup two line below
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("return-data", true);

        //you must setup this
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
        fragment.startActivityForResult(intent, 3);
    }

    public static File getPath(Context context, Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String path = cursor.getString(column_index);
        cursor.close();


        if (path == null) {
            return null;
        } else {
            return copy(new File(path), createNewFile("CROPPED_"));
        }

    }

    public static File generatePath(Context context, Uri uri) {
        String filePath = null;
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        if (isKitKat) {
            filePath = generateFromKitkat(uri, context);
        }

        if (filePath != null) {
            return new File(filePath);
        }
        Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.MediaColumns.DATA}, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                filePath = cursor.getString(columnIndex);
            }
            cursor.close();
        }
        return filePath == null ? new File(uri.getPath()) : new File(filePath);
    }

    @TargetApi(19)
    private static String generateFromKitkat(Uri uri, Context context) {
        String filePath = null;
        if (DocumentsContract.isDocumentUri(context, uri)) {
            String wholeID = DocumentsContract.getDocumentId(uri);
            String id = wholeID.split(":")[1];

            String[] column = {MediaStore.Video.Media.DATA};
            String sel = MediaStore.Video.Media._ID + "=?";

            Cursor cursor = context.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, column, sel, new String[]{id}, null);
            if (cursor != null) {
                int columnIndex = cursor.getColumnIndex(column[0]);
                if (cursor.moveToFirst()) {
                    filePath = cursor.getString(columnIndex);
                }
                cursor.close();
            }

        }
        return filePath;
    }


    public static File copy(File src, File dst) {
        InputStream in = null;
        try {
            in = new FileInputStream(src);
            OutputStream out = new FileOutputStream(dst);

            // Transfer bytes from in to out
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
            return dst;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public static void add_no_media() {
        File no_media = Util.createNewFile(".nomedia");
        try {
            if (no_media.mkdirs()) {
                boolean isCreated = no_media.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static File createFile(String file_name) {
        File newDirectory = new File(Environment.getExternalStorageDirectory() + FOLDER_NAME);
        if (!newDirectory.exists()) {
            if (newDirectory.mkdir()) {
                Log.d("Util", newDirectory.getAbsolutePath() + " directory created");
            }
        }
        return new File(newDirectory, (file_name + ".jpg"));
    }

    public static boolean isFileExist(String file_name) {
        return Util.createFile(file_name).exists();
    }

    public static boolean isFileExist(File file) {
        return file.exists();
    }

    public static boolean canScrollUp(View view) {
        if (Build.VERSION.SDK_INT < 14) {
            if (view instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) view;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView
                        .getChildAt(0).getTop() < absListView.getPaddingTop());
            } else {
                return view.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(view, -1);
        }
    }

    public static double getDouble(String message) {
        try {
            return Double.parseDouble(message);
        } catch (Exception e) {
//            e.printStackTrace();
            Log.e("Invalid Double", e.toString());
        }
        return 0;
    }

    public static String encodeToNonLossyAscii(String original) {
        original = encode(original);
        Charset asciiCharset = Charset.forName("US-ASCII");
        if (asciiCharset.newEncoder().canEncode(original)) {
            return original;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < original.length(); i++) {
            char c = original.charAt(i);
            if (c < 128) {
                stringBuilder.append(c);
            } else if (c < 256) {
                String octal = Integer.toOctalString(c);
                stringBuilder.append("\\");
                stringBuilder.append(octal);
            } else {
                String hex = Integer.toHexString(c);
                stringBuilder.append("\\u");
                stringBuilder.append(hex);
            }
        }
        return stringBuilder.toString();
    }

    private static final Pattern UNICODE_HEX_PATTERN = Pattern.compile("\\\\u([0-9A-Fa-f]{4})");
    private static final Pattern UNICODE_OCT_PATTERN = Pattern.compile("\\\\([0-7]{3})");

    public static String decodeFromNonLossyAscii(String original) {

//        Log.e("Raw", original);
        original = decode(original);
//        Log.e("UTF Decode", original);

        Matcher matcher = UNICODE_HEX_PATTERN.matcher(original);
        StringBuffer charBuffer = new StringBuffer(original.length());
        while (matcher.find()) {
            String match = matcher.group(1);
            char unicodeChar = (char) Integer.parseInt(match, 16);
            matcher.appendReplacement(charBuffer, Character.toString(unicodeChar));
        }
        matcher.appendTail(charBuffer);
        String parsedUnicode = charBuffer.toString();


        matcher = UNICODE_OCT_PATTERN.matcher(parsedUnicode);
        charBuffer = new StringBuffer(parsedUnicode.length());
        while (matcher.find()) {
            String match = matcher.group(1);
            char unicodeChar = (char) Integer.parseInt(match, 8);
            matcher.appendReplacement(charBuffer, Character.toString(unicodeChar));
        }
        matcher.appendTail(charBuffer);
//        Log.e("Decode", original);
        return charBuffer.toString();
    }

    public static Uri getURI(Context context, File file) {
        return FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", file);

    }

    public static String decode(String text) {
        try {
            return URLDecoder.decode(text, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return text;
    }

    public static Spanned fromHTML(String raw) {
        if (raw == null) raw = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(raw, Html.FROM_HTML_MODE_COMPACT);
        } else {
            return Html.fromHtml(raw);
        }
    }
}
