package com.example.newapp;

/**
 * Created by Nishant on 11/13/2017.
 */

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.NotificationCompat.WearableExtender;

public class MessagePromptNotification {
    private Context mContext;

    public MessagePromptNotification(Context context) {
        this.mContext = context;
    }

    public void createNotification(int missionNumber){
        SharedPreferences preferences = mContext.getSharedPreferences("prefName", mContext.MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        int notificationID = preferences.getInt("notificationID", 0);
        notificationID++;
        edit.putInt("notificationID", notificationID);
        edit.putString("startedFrom", "notification");
        edit.putInt("notificationMissionNumber", missionNumber);
        edit.apply();

        Intent intent = new Intent(mContext, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Action action = new NotificationCompat.Action.Builder(
                R.drawable.ic_full_sad, "Open Memories", pendingIntent).build(); //change drawable to app icon

        Vibrator v = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 250 milliseconds
        v.vibrate(250);

        Notification notification = new NotificationCompat.Builder(mContext)
                .setContentText("New story is available!")
                .setContentTitle("Memories")
                .setTicker("Notification!")
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.record_button)
                .setDefaults(Notification.DEFAULT_SOUND)
                .extend(new NotificationCompat.WearableExtender().addAction(action))
                .build();

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(mContext);
        notificationManagerCompat.notify(notificationID, notification);
    }
}
