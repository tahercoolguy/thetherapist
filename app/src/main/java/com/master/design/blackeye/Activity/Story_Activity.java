
package com.master.design.blackeye.Activity;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.master.design.blackeye.Controller.AppController;
import com.master.design.blackeye.Helper.DialogUtil;
import com.master.design.blackeye.Helper.Helper;
import com.master.design.blackeye.Helper.User;
import com.master.design.blackeye.R;
import com.master.design.blackeye.Utils.ConnectionDetector;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.ButterKnife;
import jp.shts.android.storiesprogressview.StoriesProgressView;
import jp.wasabeef.picasso.transformations.BlurTransformation;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.MultipartTypedOutput;
import retrofit.mime.TypedString;


public class Story_Activity extends AppCompatActivity implements StoriesProgressView.StoriesListener {
    User user;
    AppController appController;
    ConnectionDetector connectionDetector;
    DialogUtil dialogUtil;
    Dialog progress;
    ArrayList<String> array_image = new ArrayList<>();
    ArrayList<String> array_image_count = new ArrayList<>();
    ArrayList<Integer> mL;
    String date, countryid;

//    @BindView(R.id.webView)
//    webView webView;


    // on below line we are creating a int array
    // in which we are storing all our image ids.
    private final int[] resources = new int[]{
//            R.drawable.story_img_1,
//            R.drawable.story_img_2,
//            R.drawable.story_img_3,
//            R.drawable.story_img_1,
//            R.drawable.story_img_2,
//            R.drawable.story_img_3
    };


    // on below line we are creating variable for
    // our press time and time limit to display a story.
    long pressTime = 0L;
    long limit = 500L;

    // on below line we are creating variables for
    // our progress bar view and image view .
    private StoriesProgressView storiesProgressView;
    ImageView image, backgrd;

    // on below line we are creating a counter
    // for keeping count of our stories.
    private int counter = 0;

    // on below line we are creating a new method for adding touch listener
    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            // inside on touch method we are
            // getting action on below line.
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:

                    // on action down when we press our screen
                    // the story will pause for specific time.
                    pressTime = System.currentTimeMillis();

                    // on below line we are pausing our indicator.
                    storiesProgressView.pause();
                    return false;
                case MotionEvent.ACTION_UP:

                    // in action up case when user do not touches
                    // screen this method will skip to next image.
                    long now = System.currentTimeMillis();

                    // on below line we are resuming our progress bar for status.
                    storiesProgressView.resume();

                    // on below line we are returning if the limit < now - presstime
                    return limit < now - pressTime;
            }
            return false;
        }
    };

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.story_activity);
        user = new User(this);
        ButterKnife.bind(Story_Activity.this);
        webView = (WebView) findViewById(R.id.webView);
        appController = (AppController) getApplicationContext();
        connectionDetector = new ConnectionDetector(Story_Activity.this);
        dialogUtil = new DialogUtil();
        mL = new ArrayList<Integer>();

        backgrd = (ImageView) findViewById(R.id.bckgrd);


        // inside in create method below line is use to make a full screen.
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.story_activity);

        // on below line we are initializing our variables.
        storiesProgressView = (StoriesProgressView) findViewById(R.id.stories);


//
    }

    @Override
    public void onNext() {
        // this method is called when we move
        // to next progress view of story.

        if ((counter + 1) < 0) return;

        counter = counter + 1;
        if (array_image.get(counter) != null) {
            String new1 = array_image.get(counter);
            if (array_image.get(counter).contains(".mp4")) {
                webView.setVisibility(View.VISIBLE);
                image.setVisibility(View.GONE);
//      to add your video uncomment it
//                webView.loadUrl(AppController.base_image_url + array_image.get(counter));

            } else {
                webView.setVisibility(View.GONE);
                image.setVisibility(View.VISIBLE);
                //      to add your image uncomment it
//                Picasso.get().load(AppController.base_image_url + array_image.get(counter)).into(image);
//                Picasso.get()
//                        .load(AppController.base_image_url + array_image.get(counter))
//                        .transform(new BlurTransformation(Story_activity.this, 5, 25))
//                        .into(backgrd);
            }
        }


//        image.setImageResource(resources[++counter]);
    }

    @Override
    public void onPrev() {

        // this method id called when we move to previous story.
        // on below line we are decreasing our counter
        if ((counter - 1) < 0) return;
        // on below line we are setting image to image view
//        image.setImageResource(resources[--counter]);
        counter = counter - 1;

        if (array_image.get(counter) != null)
            if (array_image.get(counter).contains(".mp4")) {
                webView.setVisibility(View.VISIBLE);
                image.setVisibility(View.GONE);
                //      to add your video uncomment it

//                webView.loadUrl(AppController.base_image_url + array_image.get(counter));

            } else {
                webView.setVisibility(View.GONE);
                image.setVisibility(View.VISIBLE);
                //      to add your image uncomment it

//                Picasso.get().load(AppController.base_image_url + array_image.get(counter)).into(image);
//                Picasso.get()
//                        .load(AppController.base_image_url + array_image.get(counter))
//                        .transform(new BlurTransformation(Story_Activity.this, 5, 25))
//                        .into(backgrd);
            }


    }

    @Override
    public void onComplete() {
        // when the stories are completed this method is called.
        // in this method we are moving back to initial main activity.
//        Intent i = new Intent(Story_activity.this, MainActivity.class);
//        startActivity(i);
        finish();
    }

    @Override
    protected void onDestroy() {
        // in on destroy method we are destroying
        // our stories progress view.
        storiesProgressView.destroy();
        super.onDestroy();
    }




    // !!!!!!!!!!!!!!!!!!!!!!!!!!!  Dont remove this class is very important to implement stories API

    public void storiesByDateAPI(String date, String countryid) {

        if (connectionDetector.isConnectingToInternet()) {

            progress = dialogUtil.showProgressDialog(Story_Activity.this, getString(R.string.please_wait));

            MultipartTypedOutput multipartTypedOutput = new MultipartTypedOutput();
            multipartTypedOutput.addPart("date", new TypedString(date));
            multipartTypedOutput.addPart("countryid", new TypedString(countryid));

//            appController.paServices.StoriesByDate(multipartTypedOutput, new Callback<StoriesByDateRootDM>() {
//                @Override
//
//                public void success(StoriesByDateRootDM storiesByDateRootDM, Response response) {
//                    progress.dismiss();
//                    if (storiesByDateRootDM.getOutput().getSuccess().equalsIgnoreCase("1")) {
//
//                        for (AllStoryImage story : storiesByDateRootDM.getOutput().getAllimagedata()
//                        ) {
//                            array_image.add(story.getImage());
//                        }
//
//
////                        array_image_count.add(String.valueOf(storiesByDateRootDM.getOutput().getData().get(0).getImagedata().size()));
//
//                        // on below line we are setting the total count for our stories.
//                        storiesProgressView.setStoriesCount(array_image.size());
//
//                        // on below line we are setting story duration for each story.
//                        storiesProgressView.setStoryDuration(3000L);
//
//                        // on below line we are calling a method for set
//                        // on story listener and passing context to it.
//                        storiesProgressView.setStoriesListener(Story_Activity.this);
//
//                        // below line is use to start stories progress bar.
//                        storiesProgressView.startStories(counter);
//
//                        // initializing our image view.
//                        image = (ImageView) findViewById(R.id.image);
//                        backgrd = (ImageView) findViewById(R.id.bckgrd);
//                        // on below line we are setting image to our image view.
////                        image.setImageResource(resources[counter]);
//
//                        if (array_image.get(0).contains(".mp4")) {
//                            webView.setVisibility(View.VISIBLE);
//                            image.setVisibility(View.GONE);
////                            webView.setVideoURI(Uri.parse(AppController.base_image_url + array_image.get(counter))); //the string of the URL mentioned above
////                            webView.requestFocus();
////                            webView.start();
//
//                            webView.loadUrl(AppController.base_image_url + array_image.get(counter));
//                        } else {
//                            webView.setVisibility(View.GONE);
//                            image.setVisibility(View.VISIBLE);
//                            Picasso.get()
//                                    .load(AppController.base_image_url + array_image.get(0))
//                                    .transform(new BlurTransformation(Story_Activity.this, 5, 25))
//                                    .into(backgrd);
//                            Picasso.get().load(AppController.base_image_url + array_image.get(0)).into(image);
//
//                        }
//                        // below is the view for going to the previous story.
//                        // initializing our previous view.
//                        View reverse = findViewById(R.id.reverse);
//
//                        // adding on click listener for our reverse view.
//                        reverse.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                // inside on click we are
//                                // reversing our progress view.
//                                storiesProgressView.reverse();
//                            }
//                        });
//
//                        // on below line we are calling a set on touch
//                        // listener method to move towards previous image.
//                        reverse.setOnTouchListener(onTouchListener);
//
//                        // on below line we are initializing
//                        // view to skip a specific story.
//                        View skip = findViewById(R.id.skip);
//                        skip.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                // inside on click we are
//                                // skipping the story progress view.
//                                storiesProgressView.skip();
//                            }
//                        });
//                        // on below line we are calling a set on touch
//                        // listener method to move to next story.
//                        skip.setOnTouchListener(onTouchListener);
//
////                        array_image.add(storiesByDateRootDM.getOutput().getData().get(0).getImagedata().get(0).getStoryimage());
////                        mL.add(Integer.valueOf(storiesByDateRootDM.getOutput().getData().get(0).getImagedata().get(0).getStoryimage()));
//
//                    } else
//                        Helper.showToast(Story_Activity.this, "no stories present");
//                }
//
//                @Override
//                public void failure(RetrofitError retrofitError) {
//                    progress.dismiss();
//                    Log.e("error", retrofitError.toString());
//
//                }
//            });

        } else
            Helper.showToast(Story_Activity.this, getString(R.string.no_internet_connection));


    }

}
