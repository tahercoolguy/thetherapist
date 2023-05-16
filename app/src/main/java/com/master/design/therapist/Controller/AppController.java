package com.master.design.therapist.Controller;

import android.app.Application;
import android.content.res.Configuration;
import android.text.TextUtils;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.izikode.izilib.roguin.endpoint.FacebookEndpoint;
import com.izikode.izilib.roguin.endpoint.GoogleEndpoint;

import com.master.design.therapist.Services.PAServices;
import com.master.design.therapist.Utils.MyUrlConnectionClient;

import java.util.Locale;

import retrofit.RestAdapter;

//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;

public class AppController extends Application {

    private static final int MY_SOCKET_TIMEOUT_MS = 0;
    private DefaultRetryPolicy defaultRetryPolicy = new DefaultRetryPolicy(MY_SOCKET_TIMEOUT_MS, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    public PAServices paServices;
    boolean isFirstTime = false;

    RestAdapter restAdapter;
    RestAdapter restAdaptergoogle;

    private static final String SHOPCH11_SERVER = "http://207.154.215.156:8000/therapist";
    public static final String THERAPIST_IMAGE = "http://207.154.215.156:8000";

    public static final String TAG = AppController.class.getSimpleName();

    private static AppController mInstance;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    LruBitmapCache lruBitmapCache;

    public static synchronized AppController getInstance() {
        if (mInstance == null) {
            mInstance = new AppController();
        }
        return mInstance;
    }

  public GoogleEndpoint googleEndpoint;
    public FacebookEndpoint facebookEndpoint;

    @Override
    public void onCreate() {
        super.onCreate();
        GsonBuilder builder = new GsonBuilder();

        Gson deserializerGson = builder.create();
        //     .setConverter(new GsonConverter(deserializerGson))
        restAdapter = new RestAdapter.Builder().
                setEndpoint(SHOPCH11_SERVER)
                .setClient(new MyUrlConnectionClient())

                .build();
        paServices= restAdapter.create(PAServices.class);
        mInstance = this;
        googleEndpoint.initialize(this);
        facebookEndpoint.initialize(this);
     //   TwitterEndpoint.initialize(this);
//        firebaseAuth=getFirebaseAuth();
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }


    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue, new LruBitmapCache());
        }
        return this.mImageLoader;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setRetryPolicy(defaultRetryPolicy);
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setRetryPolicy(defaultRetryPolicy);
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public void cancelAllPendingRequests() {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(new RequestQueue.RequestFilter() {
                @Override
                public boolean apply(Request<?> request) {
                    return true;
                }
            });
        }
    }

    public LruBitmapCache getLruBitmapCache() {
        if (lruBitmapCache == null) {
            lruBitmapCache = new LruBitmapCache();
        }
        return lruBitmapCache;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        ContextWrapper.wrap(this, new Locale("en"));
    }

    private static final String TAG1 = AppController.class.getSimpleName();

//    public FirebaseAuth firebaseAuth;
//
//    public FirebaseAuth.AuthStateListener mAuthListener;
//
//    public FirebaseAuth getFirebaseAuth(){
//        return firebaseAuth = FirebaseAuth.getInstance();
//    }
//
//    public String getFirebaseUserAuthenticateId() {
//        String userId = null;
//        if(firebaseAuth.getCurrentUser() != null){
//            userId = firebaseAuth.getCurrentUser().getUid();
//        }
//        return userId;
//    }
//
//    public void checkUserLogin(final Context context){
//        if(firebaseAuth.getCurrentUser() != null){
//            Intent profileIntent = new Intent(context, SignUpActivity.class);
//            context.startActivity(profileIntent);
//        }
//    }
//
//    public void isUserCurrentlyLogin(final Context context){
//        mAuthListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//                if(null != user){
//                    Intent profileIntent = new Intent(context, SignUpActivity.class);
//                    context.startActivity(profileIntent);
//                }else{
//                    Intent loginIntent = new Intent(context, LoginActivity.class);
//                    context.startActivity(loginIntent);
//                }
//            }
//        };
//    }
//    private FirebaseAuth mAuth;
//    public void createNewUser(Context context, String email, String password){
//        firebaseAuth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
//                        if (!task.isSuccessful()) {
////                            errorMessage.setText("Failed to login. Invalid user");
//
//                        }
//                    }
//                });
//
//    }
//
//    public void signOut()
//    {
//        firebaseAuth.signOut();
//    }
//    public void loginAUser(final Context context, String email, String password){
//        firebaseAuth=getFirebaseAuth();
//        firebaseAuth.signInWithEmailAndPassword(email, password)
//                .addOnCompleteListener((Activity)context, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (!task.isSuccessful()) {
//                            Log.w(TAG, "signInWithEmail", task.getException());
//                          //  Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
//                        }else {
//
//                            Intent profileIntent = new Intent(context, SignUpActivity.class);
//                            context.startActivity(profileIntent);
//                        }
//                    }
//                });
//    }
}




