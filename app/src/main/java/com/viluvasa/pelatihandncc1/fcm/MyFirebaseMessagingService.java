package com.viluvasa.pelatihandncc1.fcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.viluvasa.pelatihandncc1.R;
import com.viluvasa.pelatihandncc1.SampelActivity;
import com.viluvasa.pelatihandncc1.TampungFragmentActivity;

/**
 * Created by ASA on 12/14/2016.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
//    data_helper DH = new data_helper();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //Displaying data in log
        //It is optional
        Log.d(TAG, "Pesan : " + remoteMessage.getData());
        sendCustomNotification(remoteMessage.getData().get("title"), 1, remoteMessage.getData().get("message"));
        //Log.e(TAG, "onMessageReceived: "+remoteMessage.getNotification().getBody() );
//        Log.d(TAG, "From: " + remoteMessage.getFrom());
//        Log.d(TAG, "Notification Message ID: " + remoteMessage.getMessageId());
//        Log.d(TAG, "Notification Message Data: " + remoteMessage.getData().toString());

        //Calling method to generate notification
        //sendNotification(remoteMessage.getNotification().getBody());
//        if (remoteMessage.getData().get("key").toString().equals("2")){
//            sendOrderNotification(remoteMessage.getData().get("title").toString(), remoteMessage.getData().get("subtitle").toString(), remoteMessage.getData().get("value").toString());
//        }

//        1 : info/promo
//        2 : notif pemberitahuan status order/konfirmasi
//        3 : pesan masuk
    }

    //Custom Promo Notif

    private void sendCustomNotification(String judul, int tujuan, String pesan) {
        Intent intent = new Intent(this, TampungFragmentActivity.class);
        intent.putExtra("tujuan", tujuan);
        intent.putExtra("judul", judul);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(judul)
                .setContentText(pesan)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }
}
