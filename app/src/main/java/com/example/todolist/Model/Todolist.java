package com.example.todolist.Model;

import com.google.gson.annotations.SerializedName;

public class Todolist {

    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("secret")
    private String secret;
    @SerializedName("type")
    private int type;
    @SerializedName("status")
    private int status;
    @SerializedName("id_todolist")
    private int id_todolist;
    @SerializedName("date")
    private String date;
    @SerializedName("detail_datetime")
    private String[] detail_datetime;
    @SerializedName("type_scheduler")
    private int type_scheduler;
    @SerializedName("time_scheduler")
    private String time_scheduler;
    @SerializedName("time")
    private String time;

    public Todolist(int id)
    {
        this.id = id;
    }
    public Todolist(String title, String description, String secret, int type, int status, String date, String time) {
        this.title = title;
        this.description = description;
        this.secret = secret;
        this.type = type;
        this.status = status;
        this.date = date;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime_scheduler() {
        return time_scheduler;
    }

    public void setTime_scheduler(String time_scheduler) {
        this.time_scheduler = time_scheduler;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId_todolist() {
        return id_todolist;
    }

    public void setId_todolist(int id_todolist) {
        this.id_todolist = id_todolist;
    }

    public String[] getDetail_datetime() {
        return detail_datetime;
    }

    public void setDetail_datetime(String[] detail_datetime) {
        this.detail_datetime = detail_datetime;
    }

    public int getType_scheduler() {
        return type_scheduler;
    }

    public void setType_scheduler(int type_scheduler) {
        this.type_scheduler = type_scheduler;
    }

}
