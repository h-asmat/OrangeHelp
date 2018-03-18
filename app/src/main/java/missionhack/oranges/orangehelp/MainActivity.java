package missionhack.oranges.orangehelp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements OnAlertReceivedListener{

    private static final String TAG = "MainActivity";
    FirebaseMessageReceiver messageReceiver;
    AlertSender alertSender;
    Alert alert = Alert.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alert.setAlertReceivedListener(this);
        Log.d(TAG, "Creating message receiver");
        messageReceiver = new FirebaseMessageReceiver();
        createToken();
        testSendingAlert();
    }

    private void testSendingAlert() {
        Log.d(TAG, "testSendingAlert: Creating AlertSender and sending it an alert");
        alertSender = new AlertSender();
        alertSender.sendAlert(alertSender.HILAL_TOKEN, Occupation.Fireman);
    }

    private void createToken() {

        FirebaseTokenGenerator service = new FirebaseTokenGenerator();
        Intent serviceIntent = new Intent(this, FirebaseTokenGenerator.class);
        startService(serviceIntent);
    }

    @Override
    public void onAlertReceived() {
        Log.d(TAG, "A message was received and will be toasted!!!!");
    }
}
