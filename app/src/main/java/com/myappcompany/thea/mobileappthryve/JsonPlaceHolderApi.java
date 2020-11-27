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

    @GET
    Call<StudentContainer> getStudentAccounts(@Url String url);

    @GET
    Call<StudentAuthContainer> getStudentAuths(@Url String url);

    @GET
    Call<AppointmentContainer> getAppointments(@Url String url);

    @GET
    Call<SchoolProgramContainer> getAssignedSchoolProgram(@Url String url);

    //@POST("student/get-studentaccount")
    //Call<StudentAccount> createStudentAccount(@Body StudentAccount newStudentAccount);

    //@POST("studentauth/get-studentauth")
    //Call<StudentAuthContainer> createStudentAuth(@Body StudentAuthContainer newStudentAuth);

    @FormUrlEncoded
    @POST("student/insert-studentaccount/{auth_id}/{program_id}")
    Call<StudentContainer> createStudentAccount(
            @Path("auth_id") int newAuth,
            @Path("program_id") int newProg,
            @Field("fname") String firstName,
            @Field("lname") String lastName,
            @Field("email") String eMail,
            @Field("student_number") String studentNumber,
            @Field("program_year") String programYear,
            @Field("als") boolean als,
            @Field("coop") boolean coop,
            @Field("international") boolean international,
            @Field("phone_number") String phoneNumber
    );

    @FormUrlEncoded
    @POST("studentauth/get-studentauth")
    Call<StudentAuthContainer> createStudentAuth(
            @Field("sheridan_id") String sheridanId,
            @Field("password") String password
    );
}
