package com.example.todolist;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.todolist.Model.CreateUpdateDeleteTodolist;
import com.example.todolist.Rest.ApiClient;
import com.example.todolist.Rest.ApiInterface.Todolist;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    Todolist mApiTodolist;
    int IdInt;

    public void showData(){
        String TitleText = getIntent().getStringExtra("Title");
        String DescriptionText = getIntent().getStringExtra("Descirption");
        String[] DateArray = getIntent().getStringArrayExtra("Date");
        String TimeText = getIntent().getStringExtra("Time");
        int StatusInt = getIntent().getIntExtra("Status", 0);
        int TypeInt = getIntent().getIntExtra("Type", 0);
        IdInt = getIntent().getIntExtra("Id", 0);

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
        TextView descriptionTextview = findViewById(R.id.dateTextDaily);
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

        mApiTodolist = ApiClient.getClient().create(Todolist.class);

        Button buttonDeleted = findViewById(R.id.deleteButton);

        buttonDeleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<CreateUpdateDeleteTodolist> deleteTodolistCall = mApiTodolist.deleteTodolist(new com.example.todolist.Model.Todolist(IdInt));

                deleteTodolistCall.enqueue(new Callback<CreateUpdateDeleteTodolist>() {
                    @Override
                    public void onResponse(Call<CreateUpdateDeleteTodolist> call, Response<CreateUpdateDeleteTodolist> response) {

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        getApplicationContext().startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<CreateUpdateDeleteTodolist> call, Throwable t) {

                    }
                });
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        showData();
    }

}