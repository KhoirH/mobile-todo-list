package com.example.todolist;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    public void showData(){
        String TitleText = getIntent().getStringExtra("Title");
        String DescriptionText = getIntent().getStringExtra("Descirption");
        String[] DateArray = getIntent().getStringArrayExtra("Date");
        String TimeText = getIntent().getStringExtra("Time");
        int StatusInt = getIntent().getIntExtra("Status", 0);
        int TypeInt = getIntent().getIntExtra("Type", 0);
        int IdInt = getIntent().getIntExtra("Id", 0);

        String StatusText = "Tidak Aktif";
        if(StatusInt == 1) {
            StatusText = "Aktif";
        }

        String TypeText = "One time";
        if(TypeInt == 1) {
            TypeText = "Weekly";
        }


        String DateText = String.join(", ", DateArray);

        TextView titleTextview = findViewById(R.id.titleDetail);
        TextView timeTextview = findViewById(R.id.timeDetail);
        TextView descriptionTextview = findViewById(R.id.descriptionDetail);
        TextView dateTextview = findViewById(R.id.dateDetail);
        TextView typeTextview = findViewById(R.id.typeDetail);
        TextView statusTextview = findViewById(R.id.statusDetail);

        typeTextview.setText(TypeText);
        statusTextview.setText(StatusText);
        titleTextview.setText(TitleText);
        timeTextview.setText(TimeText);
        dateTextview.setText(DateText);
        descriptionTextview.setText(DescriptionText);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

    }

    @Override
    protected void onStart() {
        super.onStart();
        showData();
    }
}