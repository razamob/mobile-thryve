package com.myappcompany.thea.mobileappthryve;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface JsonPlaceHolderApi {

    @GET()
    Call<StudentContainer> getStudentAccounts(@Url String url);

    @GET()
    Call<StudentAuthContainer> getStudentAuths(@Url String url);

    @POST("student/get-studentaccount")
    Call<StudentAccount> createStudentAccount(@Body StudentAccount newStudentAccount);

    //@POST("studentauth/get-studentauth")
    //Call<StudentAuthContainer> createStudentAuth(@Body StudentAuthContainer newStudentAuth);

    @GET
    Call<AppointmentContainer> getAppointments(@Url String url);

    @POST("studentauth/get-studentauth")
    Call<StudentAuthContainer> createStudentAuth(@Body StudentAuth newStudentAuth);

    @FormUrlEncoded
    @POST("studentauth/get-studentauth")
    Call<StudentAuthContainer> createStudentAuth(
            @Field("sheridan_id") String sheridanId,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("studentauth/get-studentauth")
    Call<StudentAuthContainer> createStudentAuth(@FieldMap Map<String, String> fields);
}
