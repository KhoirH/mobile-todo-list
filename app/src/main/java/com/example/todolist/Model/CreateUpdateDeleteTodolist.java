package com.example.todolist.Model;

import com.google.gson.annotations.SerializedName;


public class CreateUpdateDeleteTodolist {
    @SerializedName("status")
    String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}