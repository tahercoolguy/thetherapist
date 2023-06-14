package com.master.design.therapist.Services;


//import io.opencensus.stats.Stats;


import com.master.design.therapist.Adapter.DataModel.DeleteAccountDM;
import com.master.design.therapist.Adapter.DataModel.SocialUserDM;
import com.master.design.therapist.Adapter.TherapistEducationDM;
import com.master.design.therapist.Adapter.DataModel.AddMultipleImageRoot;
import com.master.design.therapist.Adapter.DataModel.Cancel_Friend_RequestDM;
import com.master.design.therapist.Adapter.DataModel.ChatHistoryDM;
import com.master.design.therapist.Adapter.DataModel.ChatlistDM;
import com.master.design.therapist.Adapter.DataModel.ChatroomDM;
import com.master.design.therapist.Adapter.DataModel.Edit_ProfileDM;
import com.master.design.therapist.Adapter.DataModel.Friend_ListDM;
import com.master.design.therapist.Adapter.DataModel.GetAll_Image.GetAllImageRoot;
import com.master.design.therapist.Adapter.DataModel.MyProfile.Root;
import com.master.design.therapist.Adapter.DataModel.RemoveImageRoot;
import com.master.design.therapist.Adapter.DataModel.Request_ListDM;
import com.master.design.therapist.Adapter.DataModel.Request_ResponseDM;
import com.master.design.therapist.Adapter.DataModel.Send_Friend_RequestDM;
import com.master.design.therapist.Adapter.DataModel.SendingImageDM;
import com.master.design.therapist.Adapter.DataModel.Terms_ConditionsDM;
import com.master.design.therapist.Adapter.DataModel.TherapistAgeDM;
import com.master.design.therapist.Adapter.DataModel.TherapistCountriesDM;
import com.master.design.therapist.Adapter.DataModel.TherapistEthnicDM;
import com.master.design.therapist.Adapter.DataModel.TherapistGenderDM;
import com.master.design.therapist.Adapter.DataModel.TherapistHomeDM;
import com.master.design.therapist.Adapter.DataModel.TherapistInterestDM;
import com.master.design.therapist.Adapter.DataModel.TherapistLoginDM;
import com.master.design.therapist.Adapter.DataModel.TherapistRegisterDM;
import com.master.design.therapist.Adapter.DataModel.TokenRoot;
import com.master.design.therapist.Adapter.DataModel.UnfriendDM;
import com.master.design.therapist.Adapter.DataModel.Update_Pic_ProfileDM;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.mime.MultipartTypedOutput;


public interface PAServices {

    //1
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

    // 2      need device token device type
    @FormUrlEncoded
    @POST("/login")
    public void TherapistLogin(@Field("email") String email,
                               @Field("password") String password,
                               @Field("logintype") String logintype,
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

    // 8
    @GET("/countries")
    public void TherapistCountries(Callback<TherapistCountriesDM> therapistCountriesDMCallback);


    // 9
    @FormUrlEncoded
    @POST("/home")
    public void TherapistHome(@Field("id") String id,
                              Callback<TherapistHomeDM> therapistHomeDMCallback);

    // 10
    @GET("/terms_conditions")
    public void TherapistTerms_conditions(Callback<Terms_ConditionsDM> terms_conditionsDMCallback);

    // 11
    @GET("/privacy_policy")
    public void TherapistPrivacy_Policy(Callback<Terms_ConditionsDM> terms_conditionsDMCallback);

    // 12
    @GET("/tips")
    public void TherapistTips(Callback<Terms_ConditionsDM> terms_conditionsDMCallback);

    // 13
    @FormUrlEncoded
    @POST("/friend_list")
    public void TherapistFriend_List(@Field("id") String id,
                                     Callback<Friend_ListDM> friend_listDMCallback);


    // 14
    @FormUrlEncoded
    @POST("/request_list")
    public void TherapistRequest_List(@Field("receiver_id") String receiver_id,
                                      Callback<Request_ListDM> friend_listDMCallback);

    // 15
    @FormUrlEncoded
    @POST("/request_response")
    public void TherapistRequest_Response(@Field("sender") String sender,
                                          @Field("receiver") String receiver,
                                          @Field("response") String response,
                                          Callback<Request_ResponseDM> friend_listDMCallback);

    // 16
    @FormUrlEncoded
    @POST("/send_friend_request")
    public void TherapistSend_Friend_Request(@Field("sender_id") String sender_id,
                                             @Field("receiver_id") String receiver_id,
                                             Callback<Send_Friend_RequestDM> send_friend_requestDMCallback);


    // 17
    @FormUrlEncoded
    @POST("/cancel_friend_request")
    public void TherapistCancel_Friend_Request(@Field("sender_id") String sender_id,
                                               @Field("receiver_id") String receiver_id,
                                               Callback<Cancel_Friend_RequestDM> cancel_friend_requestDMCallback);

    // 18
//    @FormUrlEncoded
//    @POST("/profile")
//    public void TherapistProfile(@Field("id") String id,
//                                 Callback<ProfileDM> profileDMCallback);

//    // 18
//    @POST("/profile")
//    void TherapistProfile(@Body MultipartTypedOutput multipartTypedOutput, Callback<ProfileDM> profileDMCallback);

    // 18
    @POST("/profile")
    void TherapistProfileNew(@Body MultipartTypedOutput multipartTypedOutput, Callback<Root> rootCallback);


    //19
    @POST("/update_pic")
    void TherapistUpdate_Pic(@Body MultipartTypedOutput multipartTypedOutput, Callback<Update_Pic_ProfileDM> update_pic_profileDMCallback);

    // 20
    @FormUrlEncoded
    @POST("/edit_profile")
    public void TherapistEdit_Profile(@Field("id") String id,
                                      @Field("name") String name,
                                      @Field("dob") String dob,
                                      @Field("country") String country,
                                      @Field("gender") String gender,
                                      @Field("phone") String phone,
                                      @Field("education") String education,
                                      @Field("ethnicity") String ethnicity,
                                      @Field("interests") String interests,
                                      @Field("aboutyou") String aboutyou,
                                      Callback<Edit_ProfileDM> edit_profileDMCallback);

    // 21
    @FormUrlEncoded
    @POST("/chatlist")
    public void TherapistChatList(@Field("user_id") String user_id,
                                  Callback<ChatlistDM> chatlistDMCallback);


    // 22
//    @FormUrlEncoded
//    @POST("/chathistory")
//    public void TherapistChatHistory(@Field("user_1") String user_1,
//                                     @Field("user_2") String user_2,
//                                     @Field("room_id") String room_id,
//                                     @Field("user_id ") String user_id ,
//                                     Callback<ChatHistoryDM> chatHistoryDMCallback);

    // 23
    @POST("/chathistory")
    void TherapistChatHistory(@Body MultipartTypedOutput multipartTypedOutput, Callback<ChatHistoryDM> chatHistoryDMCallback);



    // 23
    @FormUrlEncoded
    @POST("/chatroom")
    public void TherapistChatChatRoom(@Field("sender_user") String sender_user,
                                      @Field("receiver_user") String receiver_user,
                                      Callback<ChatroomDM> chatroomDMCallback);


    // 24
    @FormUrlEncoded
    @POST("/unfriend")
    public void TherapistUnfriend(@Field("user_id") String user_id,
                                  @Field("friend_id") String friend_id,
                                  Callback<UnfriendDM> unfriendDMCallback);

    // 25
    @FormUrlEncoded
    @POST("/showimages")
    void ShowImages(@Field("the_user") String the_user,
                    Callback<GetAllImageRoot> getAllImageRootCallback);

    // 26
    @FormUrlEncoded
    @POST("/removeimage")
    void RemoveImage(@Field("the_user") String the_user,
                     @Field("image_id") String image_id,
                     Callback<RemoveImageRoot> removeImageRootCallback);

    // 27
    @FormUrlEncoded
    @POST("/remove_pic")
    void Remove_Pic(@Field("id") String id,
                    Callback<RemoveImageRoot> removeImageRootCallback);

    // 28
    @POST("/addimages")
    void Add_Multiple_Images(@Body MultipartTypedOutput multipartTypedOutput, Callback<AddMultipleImageRoot> addMultipleImageRootCallback);

    // 29
    @POST("/search")
    void Search_User(@Body MultipartTypedOutput multipartTypedOutput, Callback<TherapistHomeDM> therapistHomeDMCallback);

    // 30
    @POST("/sendingimage")
    void SendingImageChat(@Body MultipartTypedOutput multipartTypedOutput, Callback<SendingImageDM> sendingImageDMCallback);

    // 31
    @POST("/update_token")
    void Update_Token(@Body MultipartTypedOutput multipartTypedOutput, Callback<TokenRoot> tokenRootCallback);

    // 32
    @POST("/online")
    void Online(@Body MultipartTypedOutput multipartTypedOutput, Callback<TokenRoot> tokenRootCallback);

    // 33
    @POST("/offline")
    void Offline(@Body MultipartTypedOutput multipartTypedOutput, Callback<TokenRoot> tokenRootCallback);

    // 34
    @FormUrlEncoded
    @POST("/deleteaccount")
    void DeleteAccount(@Field("id") String id,
                    Callback<DeleteAccountDM> deleteAccountDMCallback);

    //35
    @FormUrlEncoded
    @POST("/socialuser")
    void SocialUser(@Field("name") String name,
                     @Field("email") String email,
                     Callback<SocialUserDM> socialUserDMCallback);

}
