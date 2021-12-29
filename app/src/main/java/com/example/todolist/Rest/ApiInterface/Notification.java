package com.example.todolist.Rest.ApiInterface;

import com.example.todolist.Model.CreateNotification;
import com.example.todolist.Model.GetNotification;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Notification {
    @GET("notification")
    Call<GetNotification> getNotification();

    @POST("notification/create")
    Call<CreateNotification> createNotification(@Body com.example.todolist.Model.Notification body);

}
