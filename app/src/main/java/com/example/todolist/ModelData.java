package com.example.todolist;

public class ModelData {

    int id;
    private String title;
    private String date;
    private String time;

    ModelData(int id, String title, String date, String time){
        this.id = id;
        this.title = title;
        this.date = date;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
