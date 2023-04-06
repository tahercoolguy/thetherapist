package com.master.design.therapist.notification.Services;


//import io.opencensus.stats.Stats;


import com.master.design.therapist.Adapter.TherapistEducationDM;
import com.master.design.therapist.DataModel.TherapistAgeDM;
import com.master.design.therapist.DataModel.TherapistEthnicDM;
import com.master.design.therapist.DataModel.TherapistGenderDM;
import com.master.design.therapist.DataModel.TherapistInterestDM;
import com.master.design.therapist.DataModel.TherapistLoginDM;
import com.master.design.therapist.DataModel.TherapistRegisterDM;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.mime.MultipartTypedOutput;


public interface PAServices {
//    @Headers("Cache-Control: no-cache;")
//    @POST("/auth/signin")
//    void SignUp(@Body MultipartTypedOutput multipartTypedOutput, Callback<SignUpDM> signUpDMCallback);
//
//    @Headers("Cache-Control: no-cache;")
//    @POST("/auth/record_video")
//    void RecordedVideo(@Body MultipartTypedOutput multipartTypedOutput, Callback<VideoDM> videoDMCallback);
//
//    @Headers("Cache-Control: no-cache;")
//    @POST("/auth/user_login")
//    void LoginIn(@Body MultipartTypedOutput multipartTypedOutput, Callback<SignUpDM> signUpDMCallback);
//
//    @Headers("Cache-Control: no-cache;")
//    @GET("/user/view_home_banner")
//    void Banner(Callback<BannerDM> bannerDMCallback);
//
//    @Headers("Cache-Control: no-cache;")
//    @GET("/user/view_news")
//    void News(Callback<NewsDM> newsDMCallback);
//
//    @Headers("Cache-Control: no-cache;")
//    @GET("/user/view_events")
//    void Events(Callback<EventsDM> eventsDMCallback);
//
//    @Headers("Cache-Control: no-cache;")
//    @GET("/user/view_restaurants")
//    void Restaurent(Callback<RestaurentDM> restaurentDMCallback);
//
//
//    @Headers("Cache-Control: no-cache;")
//    @GET("/user/view_shops")
//    void Shops(Callback<ShopsDM> shopsDMCallback);

    @POST("/register")
    void TherapistRegister(@Body MultipartTypedOutput multipartTypedOutput, Callback<TherapistRegisterDM> therapistRegisterDMCallback);

//    // 1
//    @FormUrlEncoded
//    @POST("/register")
//    public void TherapistRegister(@Field("name") String name,
//                                 @Field("dob") String dob,
//                                 @Field("country") String country,
//                                 @Field("gender") String gender,
//                                 @Field("ethnicity") String ethnicity,
//                                 @Field("email") String email,
//                                  @Field("password") String password,
//                                 @Field("ConfirmPassword") String ConfirmPassword,
//                                 @Field("interests") String interests,
//                                  @Field("image") String  image,
//                                  @Field("about_you") String about_you,
//                                  @Field("education") String education,
//                                 Callback<TherapistRegisterDM> therapistRegisterDMCallback);

    // 2
    @FormUrlEncoded
    @POST("/login")
    public void TherapistLogin(@Field("email") String email,
                                  @Field("password") String password,

                               Callback<TherapistLoginDM> therapistLoginDMCallback);

    // 3
    @GET("/age")
    public void TherapistAge(Callback<TherapistAgeDM> therapistAgeDMCallback);

    // 4
    @GET("/gender")
    public void TherapistGender(Callback<TherapistGenderDM> therapistGenderDMCallback);

    // 5
    @GET("/education")
    public void TherapistEducation(Callback<TherapistEducationDM> therapistEducationDMCallback);

    // 6
    @GET("/ethnic")
    public void TherapistEthnic(Callback<TherapistEthnicDM> therapistEthnicDMCallback);

    // 7
    @GET("/interest")
    public void TherapistInterest(Callback<TherapistInterestDM> therapistInterestDMCallback);

}
