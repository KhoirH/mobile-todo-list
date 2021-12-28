package com.example.todolist.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetNotification {

    @SerializedName("result")
    List<Notification> listSecret;

    public List<Notification> getListSecret() {
        return listSecret;
    }

    public void setListSecret(List<Notification> listSecret) {
        this.listSecret = listSecret;
    }
}
