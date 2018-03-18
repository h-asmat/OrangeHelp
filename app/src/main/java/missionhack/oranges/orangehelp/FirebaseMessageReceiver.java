package missionhack.oranges.orangehelp;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Owner on 3/17/2018.
 */

public class FirebaseMessageReceiver extends FirebaseMessagingService {
    private static final String TAG = "MyFBMessagingService";
    private Alert alert = Alert.getInstance();

    public  FirebaseMessageReceiver(){}

    private String getMessageFromData(RemoteMessage remoteMessage){
        String[] messageComponents = remoteMessage.getData().toString().split(",");
        String message = messageComponents[2].split("=")[1].replace("}", "");
        return message;
    }

    private double getLatFromData(RemoteMessage remoteMessage){
        String[] messageComponents = remoteMessage.getData().toString().split(",");
        String lat = messageComponents[0].split("=")[1];
        return Double.parseDouble(lat);
    }

    private double getLongFromData(RemoteMessage remoteMessage){
        String[] messageComponents = remoteMessage.getData().toString().split(",");
        String longitude = messageComponents[1].split("=")[1];
        return Double.parseDouble(longitude);
    }

    @Override public void onMessageReceived(RemoteMessage remoteMessage)
    {

        Log.d(TAG, "Message received!!! [" + remoteMessage.getData() + "]");
        if (alert != null) {
            alert.setAlertMessage(getMessageFromData(remoteMessage));
            alert.setLatitude(getLatFromData(remoteMessage));
            alert.setLongitude(getLongFromData(remoteMessage));
            if (getMessageFromData(remoteMessage).contains("fire")){
                alert.setOccupation(Occupation.Fireman);
            }
            else if (getMessageFromData(remoteMessage).contains("patient")){
                alert.setOccupation(Occupation.Doctor);
            }
            else if (getMessageFromData(remoteMessage).contains("conflict")){
                alert.setOccupation(Occupation.Cop);
            }
            alert.raiseAlertReceivedEvent();
        }
/*
        //notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //Setting up Notification channels for android O and above
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
        {
            // setupChannels();
        }
        int notificationId = new Random().nextInt(60000);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, ADMIN_CHANNEL_ID)
                //.setSmallIcon(R.drawable.ic_notification_small) //a resource for your custom small icon
                .setContentTitle(remoteMessage.getData().get("title")) //the "title" value you sent in your notification
                .setContentText(remoteMessage.getData().get("message")) //ditto
                .setAutoCancel(true) //dismisses the notification on click
                .setSound(defaultSoundUri);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(notificationId , notificationBuilder.build());
*/
    }
}
