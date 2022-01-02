package com.example.todolist.Rest.ApiInterface;

import com.example.todolist.Model.CreateUpdateDeleteTodolist;
import com.example.todolist.Model.GetTodolist;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Todolist {
    @GET("todolist")
    Call<GetTodolist> getTodolist(@Query("secret") String secret);

    @POST("todolist/create")
    Call<CreateUpdateDeleteTodolist> createTodolist(@Body com.example.todolist.Model.Todolist body);

    @POST("todolist/update")
    Call<CreateUpdateDeleteTodolist> editTodolist(@Body com.example.todolist.Model.Todolist body);

    @POST("todolist/delete")
    Call<CreateUpdateDeleteTodolist> deleteTodolist(@Body com.example.todolist.Model.Todolist body);
}
