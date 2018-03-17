package missionhack.oranges.orangehelp;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Owner on 3/17/2018.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "MyFirebaseIIDService";
    private static final String FIREBASE_TOKEN = "FIREBASETOKEN";

    @Override
    public void onTokenRefresh(){
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        preferences.edit().putString(FIREBASE_TOKEN, refreshedToken).apply();
        // Naturally, you would like to send it to your server at some point,
        // say user registration, or even right away, so that the server can send this device
        // notifications through Firebase.

    }
}
