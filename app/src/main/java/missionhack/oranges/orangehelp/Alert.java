package missionhack.oranges.orangehelp;

import android.util.Log;

/**
 * Created by Owner on 3/17/2018.
 */

public class Alert {
    private static Alert instance = new Alert();
    private String alertMessage;
    private Occupation occupation;
    private OnAlertReceivedListener alertListener;
    private static final String TAG = "AlertSingleton";

    public void setAlertReceivedListener(final OnAlertReceivedListener eventListener) {
        Log.d(TAG, "setAlertReceivedListener: Custom event listener set");
        alertListener = eventListener;
    }

    private Alert(){}

    public static Alert getInstance(){
        return instance;
    }

    public String getAlertMessage(){
        return alertMessage;
    }
    public void setAlertMessage(String alertMessage){
        this.alertMessage = alertMessage;
        if (alertListener!=null) {
            alertListener.onAlertReceived();
        }
    }

    public Occupation getOccupation() {
        return occupation;
    }

    public void setOccupation(Occupation occupation) {
        this.occupation = occupation;
    }
}
