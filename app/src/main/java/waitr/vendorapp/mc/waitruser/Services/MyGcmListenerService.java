package waitr.vendorapp.mc.waitruser.Services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

import java.util.Set;

import waitr.vendorapp.mc.waitruser.Helpers.Constants;
import waitr.vendorapp.mc.waitruser.R;
import waitr.vendorapp.mc.waitruser.activities.MainActivity;

/**
 * Created by siddharth on 11/21/15.
 */

public class MyGcmListenerService extends GcmListenerService {

    private static final String TAG = "MyGcmListenerService";
    SharedPreferences mSharedPreferences;
    /**
     * Called when message is received.
     *
     * @param from SenderID of the sender.
     * @param data Data bundle containing message data as key/value pairs.
     *             For Set of keys use data.keySet().
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(String from, Bundle data) {

//        Log.d(TAG, "From: " + from);
//        Log.d(TAG, "Message: " + message);
        Set<String> mStringSet = data.keySet();
        String keys = "";
        for (String s : mStringSet)
            keys += ", " + s;
        Log.d("keys", keys);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        Log.d("keys1", data.getString("item"));
        String messageType = data.getString("collapse_key");
        if(messageType==null)
            Log.d("collapse","null");
        else{
            Log.d("collapse",messageType);
            if(messageType.equals("Item")&&mSharedPreferences.getBoolean(Constants.RECEIVE_NOTIFICATIONS_NEW_ITEM,false)){
                String item = data.getString("item");


            }
            else if(messageType.equals("Order")&&mSharedPreferences.getBoolean(Constants.RECEIVE_NOTIFICATIONS_ORDER,false)){

            }
            else{

            }

        }

//        sendNotification(message);

    }

    private void sendNotification(String message) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ingredients24black)
                .setContentTitle(message)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());//TODO:: ID OF notification
    }
}