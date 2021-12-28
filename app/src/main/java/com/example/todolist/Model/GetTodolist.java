package com.example.todolist.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetTodolist {

    @SerializedName("result")
    List<Todolist> listTodolist;
    public List<Todolist> getListTodolist() {
        return listTodolist;
    }

    public void setListTodolist(List<Todolist> listTodolist) {
        this.listTodolist = listTodolist;
    }

}
