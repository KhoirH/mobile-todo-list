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

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TodolistAdapter extends ArrayAdapter<Todolist> {

    public TodolistAdapter(@NonNull Context context, @NonNull List<Todolist> objects) {
        super(context, 0, objects);
    }
    private Date stringToDate(String aDate,String aFormat) {

        if(aDate==null) return null;
        ParsePosition pos = new ParsePosition(0);
        SimpleDateFormat simpledateformat = new SimpleDateFormat(aFormat);
        Date stringDate = simpledateformat.parse(aDate, pos);
        return stringDate;

    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Todolist td = getItem(position);

        if (convertView == null) {
            if(td.getType() == 1) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.todo_content_weekly, parent, false);
            } else {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.todo_content_daily, parent, false);
            }
        }

        String timeText, dateText;
        String DateText = String.join(", ", td.getDetail_datetime());
        TextView titleView = convertView.findViewById(R.id.title);
        TextView dateView  = convertView.findViewById(R.id.dateTextDaily);
        TextView timeView = convertView.findViewById(R.id.timeTextDaily);
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

        Date time = stringToDate(td.getTime_scheduler(), "HH:mm");
        timeText = new SimpleDateFormat("hh:mm aaa").format(time);

        if(td.getType() == 0) {
            Date date = stringToDate(DateText, "dd-MM-yyyy");
            dateText = new SimpleDateFormat("DD MMM").format(date);
        } else {
            List<String> newDateText = new ArrayList<>();;
            for(int i = 0; i < td.getDetail_datetime().length; i++){
                newDateText.add(td.getDetail_datetime()[i].substring(0, 3));
            }
            dateText = String.join(", ", newDateText);
        }

        titleView.setText(td.getTitle());
        dateView.setText(dateText);
        timeView.setText(timeText);
        return convertView;
    }
}
