package com.example.todolist.Services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.todolist.MainActivity;
import com.example.todolist.Model.CreateNotification;
import com.example.todolist.R;
import com.example.todolist.Rest.ApiClient;
import com.example.todolist.Rest.ApiInterface.Notification;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    Notification mApiNotification;

    @Override
    public void onCreate() {
        super.onCreate();

        mApiNotification = ApiClient.getClient().create(Notification.class);
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.e("test", s);
        Call<CreateNotification> createNotificationCall = mApiNotification.createNotification(new com.example.todolist.Model.Notification(s));
        createNotificationCall.enqueue(new Callback<CreateNotification>() {
            @Override
            public void onResponse(Call<CreateNotification> call, Response<CreateNotification> response) {
            }

            @Override
            public void onFailure(Call<CreateNotification> call, Throwable t) {
                Log.e("error", t.getMessage());
            }
        });
        getSharedPreferences("_", MODE_PRIVATE).edit().putString("fb", s).apply();
    }
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.e("newToken", remoteMessage.getNotification().getTitle());
        sendNotification(remoteMessage);
    }

    public static String getToken(Context context) {
        return context.getSharedPreferences("_", MODE_PRIVATE).getString("fb", "empty");
    }


    private void sendNotification( RemoteMessage remoteMessage) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle(remoteMessage.getNotification().getTitle())
                        .setContentText(remoteMessage.getNotification().getBody())
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
