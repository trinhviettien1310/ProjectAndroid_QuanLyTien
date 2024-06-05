package com.example.projectver3;

import static com.example.projectver3.Add_Update_Remind.remind;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.projectver3.fragment.RemindFragment;

import java.util.Date;

public class Remind_AlarmReceiver extends BroadcastReceiver {
    public static final String CHANNEL_ID = "CHANNEL_ID_NOTIFICATION";
    @SuppressLint("MissingPermission")
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, RemindFragment.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,i, PendingIntent.FLAG_IMMUTABLE);
        //Chuông
        Uri uri = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.chuong);
        //Custom notification
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.layout_custom_notification);
        remoteViews.setTextViewText(R.id.tvCustomName,remind.getNameRemind());
        remoteViews.setTextViewText(R.id.tvCustomNote,remind.getNoteRemind());
        remoteViews.setTextViewText(R.id.tvCustomDate,remind.getDateRemind().toString());
        remoteViews.setTextViewText(R.id.tvCustomTime,remind.getTimeRemind().toString());

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,CHANNEL_ID)
                .setSmallIcon(R.drawable.baseline_attach_money_24)
                .setCustomContentView(remoteViews)
                .setSound(uri)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(getNotificationId(),builder.build());




    }
    private int getNotificationId() {
        //trả về giá trị id không giống nhau
        return (int) new Date().getTime();
    }
}
