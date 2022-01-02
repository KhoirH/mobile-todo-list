package com.example.todolist.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.todolist.DetailActivity;
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

        String TypeText = "One time";
        if(td.getType() == 1) {
            TypeText = "Weekly";
        }


        String DateText = " | " + String.join(", ", td.getDetail_datetime());

        TextView titleView = convertView.findViewById(R.id.title);
        TextView typeView  = convertView.findViewById(R.id.typeTodolist);
        TextView dateView  = convertView.findViewById(R.id.dateTodolist);

        convertView.setClickable(true);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), DetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Title", td.getTitle());
                intent.putExtra("Descirption", td.getDescription());
                intent.putExtra("Date", td.getDetail_datetime());
                intent.putExtra("Time", td.getTime_scheduler());
                intent.putExtra("Status", td.getStatus());
                intent.putExtra("Type", td.getType());
                intent.putExtra("Id", td.getId());

                getContext().startActivity(intent);
            }
        });
        typeView.setText(TypeText);
        dateView.setText(DateText);
        titleView.setText(td.getTitle());
        return convertView;
    }
}
