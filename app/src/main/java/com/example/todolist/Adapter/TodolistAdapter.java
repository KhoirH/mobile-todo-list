package com.example.todolist.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.todolist.Model.Todolist;
import com.example.todolist.R;

import java.util.List;

public class TodolistAdapter extends ArrayAdapter<Todolist> {

    public TodolistAdapter(@NonNull Context context, @NonNull List<Todolist> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Todolist td = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.todo_content, parent, false);
        }
        TextView titleView = convertView.findViewById(R.id.title);
        TextView descriptionView  = convertView.findViewById(R.id.description);
        descriptionView.setText(td.getDescription());
        titleView.setText(td.getTitle());
        return convertView;
    }

}
