package com.example.todolist;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import com.example.todolist.Adapter.TodolistAdapter;
import com.example.todolist.Model.CreateUpdateDeleteTodolist;
import com.example.todolist.Model.GetTodolist;
import com.example.todolist.Rest.ApiClient;
import com.example.todolist.Rest.ApiInterface.Notification;
import com.example.todolist.Rest.ApiInterface.Todolist;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AlphaAnimation;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.CheckBox;

import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.util.Calendar.MINUTE;

public class MainActivity extends AppCompatActivity {

    private ListView itemsListView;
    String secret;
    com.example.todolist.Model.Todolist todolistObj;
    Notification mApiInterfaceNotification;
    Todolist mApiTodolist;
    List<com.example.todolist.Model.Todolist> todolistList = new ArrayList<>();
    CheckBox CekSenin, CekSelasa, CekRabu, CekKamis, CekJumat, CekSabtu, CekMinggu;

    private FloatingActionButton fab;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.3F);
    private static final String TAG = "MainActivity";

    // get todo
    public void GetTodolist() {
        if(secret == "empty"){
            secret = getSharedPreferences("_", MODE_PRIVATE).getString("fb", "empty");
        }
        Call<GetTodolist> getTodolistCall = mApiTodolist.getTodolist(secret);

        getTodolistCall.enqueue(new Callback<GetTodolist>() {
            @Override
            public void onResponse(Call<GetTodolist> call, Response<GetTodolist> response) {
                todolistList = response.body().getListTodolist();

                TodolistAdapter adapter = new TodolistAdapter(getApplicationContext(), todolistList);

                itemsListView.setAdapter(adapter);

            }
            @Override
            public void onFailure(Call<GetTodolist> call, Throwable t) {

                Log.d("error", String.valueOf(todolistList.size()));
            }
        });
    }
    public void handleRefresh() {
        final SwipeRefreshLayout pullToRefresh = findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetTodolist();
                pullToRefresh.setRefreshing(false);
            }
        });
    }

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        secretStorage = new SecretStorage(this);
//        secretStorage.readSecret();

        itemsListView = findViewById(R.id.itemsList);
        fab = findViewById(R.id.fab);
        mApiTodolist = ApiClient.getClient().create(Todolist.class);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);
       // Toolbar toolbar = findViewById(R.id.toolbar);
//        // setSupportActionBar(toolbar);
////        fab.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////
////                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
////                        .setAction("Action", null).show();
////            }
////        });
        
          onFabClick();
          hideFab();
          handleRefresh();
    }

    //menyembunyikan tombol floating tambah pada sat listview scroll
    private void hideFab(){
        itemsListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE){
                    fab.show();
                }else{
                    fab.hide();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    private void onFabClick(){
        try {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    v.startAnimation(buttonClick);
                    //showAddDialog();
                    showChooseDialog();
                }
            });
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        secret = getSharedPreferences("_", MODE_PRIVATE).getString("fb", "evnYtBBLfu448Nv9JZyZ9a:APA91bHdCd386C6Ato5_V0f6khPyOPVwIm4P0YJ3Ouluso-1F79O5yVdP3YRwjcJQ82_j84mz-BY5TLMNTNrl4vDa-ZIVKyG-NoXbRM9wcIle111Ia9Nd30P-MS3vtoTmAKf8y7I9E_Q");

        GetTodolist();
    }

    private void showChooseDialog(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getLayoutInflater().getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        @SuppressLint("InflateParams")
        final View dialogBoxView= inflater.inflate(R.layout.todo_choose, null);
        dialogBuilder.setView(dialogBoxView);

        final Button onetime = dialogBoxView.findViewById(R.id.id_onetime);
        final Button weekly = dialogBoxView.findViewById(R.id.id_weekly);

        onetime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(buttonClick);
                showAddDialogOneTime();
            }
        });

        weekly.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                view.startAnimation(buttonClick);
                ShowDialogWeekly();
            }
        });

        dialogBuilder.setTitle("Pilih Aktivitas");

        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    List<String> daysActive = new ArrayList<>();
    private void ShowDialogWeekly() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getLayoutInflater().getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        @SuppressLint("InflateParams") final View dialogBoxView = inflater.inflate(R.layout.custom_dialog_todo_repeat, null);
        dialogBuilder.setView(dialogBoxView);

        final EditText judulkegiatan = dialogBoxView.findViewById(R.id.edit_tugas);
        final TextView waktupengingat = dialogBoxView.findViewById(R.id.edit_waktu);
        final EditText desc = dialogBoxView.findViewById(R.id.edit_desc);

        final long date = System.currentTimeMillis();

        SimpleDateFormat timeSdf = new SimpleDateFormat("HH : mm");
        String timeString = timeSdf.format(date);
        waktupengingat.setText(timeString);

        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());


        //Set waktu
        waktupengingat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getLayoutInflater().getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                String time;
                                @SuppressLint("DefaultLocale") String minTime = String.format("%02d", minute);
                                String hourOfDayView = "" + hourOfDay;
                                if(hourOfDay < 10 ) {
                                    hourOfDayView = "0" + hourOfDay ;
                                }
                                time = hourOfDayView + " : " + minTime;
                                waktupengingat.setText(time);
                                cal.set(Calendar.HOUR, hourOfDay);
                                cal.set(Calendar.MINUTE, minute);
                                cal.set(Calendar.SECOND, 0);
                                Log.d(TAG, "onTimeSet: Time has been set successfully");
                            }
                        }, cal.get(Calendar.HOUR), cal.get(MINUTE), false);
                timePickerDialog.show();
            }

        });
        dialogBuilder.setTitle("Buat tugas baru");
        dialogBuilder.setPositiveButton("Tambah", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String title = judulkegiatan.getText().toString();
                String waktu = waktupengingat.getText().toString().replaceAll(" ", "");
                String deskripsi = desc.getText().toString();
                String date = String.join(",", daysActive);
                if (title.length() != 0) {
                    com.example.todolist.Model.Todolist todolist = new com.example.todolist.Model.Todolist(title, deskripsi, secret, 0, 1, date, waktu );
                    Call<CreateUpdateDeleteTodolist> createTodolistCall = mApiTodolist.createTodolist(todolist);

                    createTodolistCall.enqueue(new Callback<CreateUpdateDeleteTodolist>() {
                        @Override
                        public void onResponse(Call<CreateUpdateDeleteTodolist> call, Response<CreateUpdateDeleteTodolist> response) {
                            GetTodolist();
                        }

                        @Override
                        public void onFailure(Call<CreateUpdateDeleteTodolist> call, Throwable t) {

                        }
                    });

                } else {
                    //toastMsg("Oops, Gak bisa kosong tugas nya.");
                }

                daysActive = new ArrayList<>();
            }
        });

        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();

    }
    int indexActiveSenin, indexActiveSelasa, indexActiveRabu, indexActiveKamis, indexActiveJumat, indexActiveSabtu, indexActiveMinggu;
    public void onCheckboxClicked(View view){
        boolean checked = ((CheckBox) view).isChecked();

        switch(view.getId()){
            case R.id.checkBoxSenin:
                if(checked){
                    daysActive.add("Monday");
                    indexActiveSenin = daysActive.size() - 1;
                } else {
                    daysActive.remove(indexActiveSenin);
                }
                break;
            case R.id.checkBoxSelasa:
                if(checked){
                    daysActive.add("Tuesday");
                    indexActiveSelasa = daysActive.size() - 1;
                } else {
                    daysActive.remove(indexActiveSelasa);
                }
                break;
            case R.id.checkBoxRabu:
                if(checked){
                    daysActive.add("Wednesday");
                    indexActiveRabu = daysActive.size() - 1;
                } else {
                    daysActive.remove(indexActiveRabu);
                }
                break;
            case R.id.checkBoxKamis:
                if(checked){
                    daysActive.add("Thursday");
                    indexActiveKamis = daysActive.size() - 1;

                } else {
                    daysActive.remove(indexActiveKamis);
                }
                break;
            case R.id.checkBoxJumat:
                if(checked){
                    daysActive.add("Friday");
                    indexActiveJumat = daysActive.size() - 1;
                } else {
                    daysActive.remove(indexActiveJumat);
                }
                break;
            case R.id.checkBoxSabtu:
                if(checked){
                    daysActive.add("Saturday");
                    indexActiveSabtu = daysActive.size() - 1;
                } else {
                    daysActive.remove(indexActiveSabtu);
                }
                break;
            case R.id.checkBoxMinggu:
                if(checked){
                    daysActive.add("Sunday");
                    indexActiveMinggu = daysActive.size() - 1;
                } else {
                    daysActive.remove(indexActiveMinggu);
                }
                break;
        }
    }
    //Implementasi klik dari tombol tambah
    @SuppressLint("SimpleDateFormat")
    private void showAddDialogOneTime() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getLayoutInflater().getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        @SuppressLint("InflateParams")
        final View dialogView = inflater.inflate(R.layout.custom_dialog_todo, null);
        dialogBuilder.setView(dialogView);

        final EditText judul = dialogView.findViewById(R.id.edit_tugas);
        final TextView tanggal = dialogView.findViewById(R.id.date);
        final TextView waktu = dialogView.findViewById(R.id.edit_waktu);
        final TextView descView = dialogView.findViewById(R.id.edit_desc);


        final long date = System.currentTimeMillis();
        SimpleDateFormat dateSdf = new SimpleDateFormat("DD - MM - YYYY");
        String dateString = dateSdf.format(date);
        tanggal.setText(dateString);

        SimpleDateFormat timeSdf = new SimpleDateFormat("HH : mm");
        String timeString = timeSdf.format(date);
        waktu.setText(timeString);

        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());

        //Set tanggal
        tanggal.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                final DatePickerDialog datePickerDialog = new DatePickerDialog(getLayoutInflater().getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                int newMonth = monthOfYear + 1;
                                String monthView = "" + newMonth;
                                if(newMonth < 10) {
                                    monthView = "0" + dayOfMonth;
                                }
                                String dayViewOfMonth = "" + dayOfMonth;
                                if(dayOfMonth < 10) {
                                    dayViewOfMonth = "0" + dayOfMonth;
                                }
                                tanggal.setText(dayViewOfMonth + " - " + monthView + " - " + year);
                                cal.set(Calendar.YEAR, year);
                                cal.set(Calendar.MONTH, monthOfYear);
                                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                            }
                        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
                datePickerDialog.getDatePicker().setMinDate(date);
            }
        });

        //Set waktu
        waktu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getLayoutInflater().getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                String time;
                                @SuppressLint("DefaultLocale") String minTime = String.format("%02d", minute);
                                String hourOfDayView = "" + hourOfDay;
                                if(hourOfDay < 10 ) {
                                    hourOfDayView = "0" + hourOfDay ;
                                }
                                time = hourOfDayView + " : " + minTime;
                                waktu.setText(time);
                                cal.set(Calendar.HOUR, hourOfDay);
                                cal.set(Calendar.MINUTE, minute);
                                cal.set(Calendar.SECOND, 0);
                                Log.d(TAG, "onTimeSet: Time has been set successfully");
                            }
                        }, cal.get(Calendar.HOUR), cal.get(MINUTE), false);
                timePickerDialog.show();
            }
        });

        dialogBuilder.setTitle("Buat tugas baru");
        dialogBuilder.setPositiveButton("Tambah", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String title = judul.getText().toString();
                String desc = descView.getText().toString();
                String date = tanggal.getText().toString().replaceAll(" ", "");
                String time = waktu.getText().toString().replaceAll(" ", "");
                if (title.length() != 0) {
                    com.example.todolist.Model.Todolist todolist = new com.example.todolist.Model.Todolist(title, desc, secret, 0, 1, date, time );
                    Call<CreateUpdateDeleteTodolist> createTodolistCall = mApiTodolist.createTodolist(todolist);

                    createTodolistCall.enqueue(new Callback<CreateUpdateDeleteTodolist>() {
                        @Override
                        public void onResponse(Call<CreateUpdateDeleteTodolist> call, Response<CreateUpdateDeleteTodolist> response) {
                            GetTodolist();
                        }

                        @Override
                        public void onFailure(Call<CreateUpdateDeleteTodolist> call, Throwable t) {

                        }
                    });

                    try {
                        //insertDataToDb(title, date, time);
                        //scheduleNotification(getNotification(title), cal.getTimeInMillis());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    //toastMsg("Oops, Gak bisa kosong tugas nya.");
                }
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

          //Metode pesan toast
//        private void toastMsg(String msg) {
//            Toast t = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
//            t.setGravity(Gravity.CENTER, 0,0);
//            t.show();
//        }


        //Mengkonversi bulan dari huruf menjadi angka
        private String getMonth(int month) {
            return new DateFormatSymbols().getMonths()[month - 1];
        }




}