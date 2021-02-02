package com.master.design.eighty.Helper;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import android.text.InputFilter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.master.design.eighty.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;


public class Helper {
    private String TAG = "Helper";
    private Context context;

    public Helper(Context context) {
        this.context = context;
    }


    public static void showToast(Context context, String toastString) {
        /*Toast.makeText(context, toastString, Toast.LENGTH_SHORT).show();*/
        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.toast_layout, null);
        ((TextView) view.findViewById(R.id.txt_message)).setText(toastString);
        toast.setView(view);
        toast.show();
    }

    public static String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] pro = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, pro, null, null, null);
            int column_index = 0;
            if (cursor != null) {
                column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            }
            if (cursor != null) {
                cursor.moveToFirst();
            }
            assert cursor != null;
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }


    public static void showKeyBoard(EditText editText, Context context) {
        InputMethodManager imgr = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        editText.requestFocus();
    }

    public static void hideSoftKeyboard(View view) {
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void hideSoftKeyboard(Context context) {
        View view = ((Activity) context).getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static int getDeviceHeight(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }

    public static int getDeviceWidth(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    public static void setTransparentStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = activity.getWindow(); // in Activity's onCreate() for instance
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }

    public static void hideStatusBar(Activity activity) {
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public static void setStatusBarColor(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
        }
    }

    public static void disableWebView(WebView webView) {
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        webView.setLongClickable(false);
        webView.setHapticFeedbackEnabled(false);
    }


    public static int getStatusBarHeight(Activity activity) {
        int result = 0;
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = activity.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static CharSequence setTypeFaceSpan(Context context, String string) {
        SpannableString s = new SpannableString(string);
        //s.setSpan(new TypefaceSpan(Typeface.createFromAsset(context.getAssets(), context.getString(R.string.AvenirRoman))), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return s;
    }

    public static void clearAllPreviousActivity(Intent intent) {
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    }

    public static void setListViewHeightBasedOnItems(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {
            int numberOfItems = listAdapter.getCount();
            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() * (numberOfItems - 1);

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight;
            listView.setLayoutParams(params);
            listView.requestLayout();
        }
    }

    public static int getRandom(int min, int max) {
        return new Random().nextInt((max - min) + 1) + min;
    }

    public static void MakeCall(Context context, String contact) {
        try {
            Intent callIntent = new Intent(Intent.ACTION_VIEW);
            callIntent.setData(Uri.parse("tel:" + contact));
            context.startActivity(callIntent);
        } catch (ActivityNotFoundException activityException) {
            Log.e("Phone dialer", "Call failed", activityException);
        }
    }

    public static void Email(Context context, String email) {
        String mailto = "mailto:" + email +
                "?cc=" + "" +
                "&subject=" + Uri.encode("") +
                "&body=" + Uri.encode("");

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse(mailto));
        try {
            context.startActivity(emailIntent);
        } catch (ActivityNotFoundException e) {
            //TODO: Handle case where no email app is available
        }
    }

    public static void SMS(Context context, String contact) {
        try {
            Intent callIntent = new Intent(Intent.ACTION_VIEW);
            callIntent.setData(Uri.parse("sms:" + contact));
            context.startActivity(callIntent);
        } catch (ActivityNotFoundException activityException) {
            Log.e("Phone dialer", "Call failed", activityException);
        }
    }

    public static void OpenWebSite(Context context, String site_url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + site_url));
        context.startActivity(browserIntent);
    }

    public static void openKeyBoard(Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }
    }

    public static void DownloadImage(String url) {
        final File file = createOfferName(url.substring(url.lastIndexOf('/') + 1));
       /* Glide.with(getApplicationContext())
                .load(url)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>(256, 256) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                        createImageFile(resource, file);
                    }
                });*/
    }

    public static File createOfferName(String file_name) {
        if (file_name == null || file_name.isEmpty()) {
            file_name = "offer";
        }

        File newDirectory = new File(Environment.getExternalStorageDirectory() + "/Manzelle/");
        if (!newDirectory.exists()) {
            if (newDirectory.mkdir()) {
                Log.d("Util", newDirectory.getAbsolutePath() + " directory created");
            }
        }
        return new File(newDirectory, (file_name));

    }

    private static void createImageFile(Bitmap imageBitmap, File image_file) {
        //Convert bitmap to byte array
        //write the bytes in file
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(image_file);
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 85, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageBitmap.recycle();
        System.gc();
    }

    public static String getVolleyError(VolleyError volleyError) {
        String message = null;
        if (volleyError instanceof NetworkError) {
            message = "Can not connect to Internet... Please check your connection!";
        } else if (volleyError instanceof ServerError) {
            message = "The server could not be found. Please try again after some time!!";
        } else if (volleyError instanceof AuthFailureError) {
            message = "Can not connect to Internet... Please check your connection!";
        } else if (volleyError instanceof ParseError) {
            message = "Parsing error! Please try again after some time!!";
        } else if (volleyError instanceof NoConnectionError) {
            message = "Can not connect to Internet... Please check your connection!";
        } else if (volleyError instanceof TimeoutError) {
            message = "Connection Time Out! Please check your internet connection.";
        }
        return message;
    }

    public static void setEditTextAllCaps(EditText editText) {
        editText.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
    }

    public static int convertDipToPixels(Activity activity, float dip) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float density = metrics.density;
        return (int) (dip * density);
    }


    public static void ButtonSelector(View imageView) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
            imageView.setOnTouchListener(new View.OnTouchListener() {
                private Rect rect;

                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN: {
                            rect = new Rect(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
                            v.getBackground().setColorFilter(0x44000000, PorterDuff.Mode.SRC_ATOP);
                            v.invalidate();
                            break;
                        }
                        case MotionEvent.ACTION_MOVE:
                            if (!rect.contains(v.getLeft() + (int) event.getX(), v.getTop() + (int) event.getY())) {
                                // User moved outside bounds
                                v.getBackground().clearColorFilter();
                                v.invalidate();
                            }
                            break;
                        case MotionEvent.ACTION_UP:
                        case MotionEvent.ACTION_CANCEL: {
                            // mVelocityTracker.recycle();
                            v.getBackground().clearColorFilter();
                            v.invalidate();
                            break;

                        }
                    }
                    return false;
                }
            });
        }
    }


    public static void setAlpha(final View view) {
        view.setOnTouchListener(new View.OnTouchListener() {
            private Rect rect;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    rect = new Rect(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
                    view.setAlpha(0.5f);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    view.setAlpha(1f);
                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    if (!rect.contains(v.getLeft() + (int) event.getX(), v.getTop() + (int) event.getY())) {
                        // User moved outside bounds
                        view.setAlpha(1f);
                    }

                }
                return false;
            }
        });
    }

    public static Spannable getAsteriskRed(String str) {
        Spannable word = new SpannableString(str);
        word.setSpan(new StyleSpan(Typeface.BOLD), (str.length() - 1), (str.length()), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        word.setSpan(new ForegroundColorSpan(Color.parseColor("#FFB220")), (str.length() - 1), (str.length()), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return word;
    }

    public static void BounceButtonSelector(final View view) {
        view.setOnTouchListener(new View.OnTouchListener() {
            private Rect rect;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        rect = new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
                        Animation anim = new ScaleAnimation(1f, 0.9f, 1f, 0.9f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                        anim.setFillAfter(true);
                        anim.setDuration(70);
                        view.startAnimation(anim);
                        view.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_MOVE:
                        if (!rect.contains(view.getLeft() + (int) event.getX(), view.getTop() + (int) event.getY())) {
                            // User moved outside bounds
                            Animation anim = new ScaleAnimation(0.9f, 1f, 0.9f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                            anim.setFillAfter(true);
                            anim.setDuration(70);
                            view.startAnimation(anim);
                            view.invalidate();
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL: {
                        // mVelocityTracker.recycle();
                        Animation anim = new ScaleAnimation(0.9f, 1f, 0.9f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                        anim.setFillAfter(true);
                        anim.setDuration(70);
                        v.startAnimation(anim);
                        v.invalidate();
                        break;

                    }
                }
                return false;
            }
        });
    }

    public static void setBounceAnimation(final View view) {
        Animation anim = new ScaleAnimation(1f, 0.6f, 1f, 0.6f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setFillAfter(true);
        anim.setDuration(100);
        view.startAnimation(anim);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Animation anim = new ScaleAnimation(0.6f, 1f, 0.6f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                anim.setFillAfter(true);
                anim.setDuration(100);
                view.startAnimation(anim);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public static void ImageSelector(final ImageView iv_add_post) {
        iv_add_post.setOnTouchListener(new View.OnTouchListener() {
            private Rect rect;

            @Override
            public boolean onTouch(View view, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        rect = new Rect(iv_add_post.getLeft(), iv_add_post.getTop(), iv_add_post.getRight(), iv_add_post.getBottom());
                        iv_add_post.getDrawable().setColorFilter(0x44000000, PorterDuff.Mode.SRC_ATOP);
                        iv_add_post.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_MOVE:
                        if (!rect.contains(iv_add_post.getLeft() + (int) event.getX(), iv_add_post.getTop() + (int) event.getY())) {
                            // User moved outside bounds
                            iv_add_post.getDrawable().clearColorFilter();
                            iv_add_post.invalidate();
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL: {
                        // mVelocityTracker.recycle();
                        iv_add_post.getDrawable().clearColorFilter();
                        iv_add_post.invalidate();
                        break;

                    }
                }
                return false;
            }
        });
    }

    public static void ChangeRatingBarColor(Context context, RatingBar rating_bar) {
        LayerDrawable stars = (LayerDrawable) rating_bar.getProgressDrawable();
        stars.getDrawable(0).setColorFilter(Color.parseColor("#431616"), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(2).setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
    }


    public static void AppSharing(Context context) {
        Intent shareIntent = ShareCompat.IntentBuilder.from((Activity) context)
                .setType("text/plain")
                .setSubject("Download Player Agent App")
                .setText("Player Agent App :" + "\n" + "https://play.google.com/store/apps/details?id=")
                .getIntent();
        if (shareIntent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(shareIntent);
        }

       /* Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, "Hey view/download this image");
        String path = Images.Media.insertImage(getContentResolver(), loadedImage, "", null);
        Uri screenshotUri = Uri.parse(path);

        intent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
        intent.setType("image/*");
        startActivity(Intent.createChooser(intent, "Share image via..."));*/
    }

    public static void ClearFragmentStack(Context context) {
        ((AppCompatActivity) context).getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public static void RotationAnimation(View view, int from, int to, long duration) {
     /*   ObjectAnimator objectanimator = ObjectAnimator.ofFloat(view, "rotation", degree);
        objectanimator.setDuration(duration);
        objectanimator.start();*/
        RotateAnimation rotate = new RotateAnimation(from, to, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(duration);
        rotate.setFillAfter(true);
        view.startAnimation(rotate);
    }

    public static void setTextViewDrawableColor(TextView textView, int color) {
        for (Drawable drawable : textView.getCompoundDrawables()) {
            if (drawable != null) {
                drawable.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(textView.getContext(), color), PorterDuff.Mode.SRC_IN));
            }
        }
    }
}