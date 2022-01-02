package com.example.todolist.Adapter;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

import com.example.todolist.DetailActivity;
import com.example.todolist.Model.Todolist;
import com.example.todolist.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import static java.util.Calendar.MINUTE;

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
        TextView descriptionView  = convertView.findViewById(R.id.descriptionDetail);
        convertView.setClickable(true);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), DetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Title", td.getTitle());
                intent.putExtra("Descirption", td.getDescription());
                intent.putExtra("Date", td.getDetail_datetime());
                intent.putExtra("Time", td.getTime_sheduler());
                intent.putExtra("Status", td.getStatus());
                intent.putExtra("Type", td.getType());
                intent.putExtra("Id", td.getId());

                getContext().startActivity(intent);
            }
        });
        descriptionView.setText(td.getDescription());
        titleView.setText(td.getTitle());
        return convertView;
    }
}
