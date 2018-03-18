package missionhack.oranges.orangehelp;

import android.app.Activity;
import android.content.Context;
import android.speech.RecognizerIntent;
import android.view.View;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements OnAlertReceivedListener{
    private TextView finalText;

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
        try {
            testSendingAlert();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void testSendingAlert() throws InterruptedException {
        Log.d(TAG, "testSendingAlert: Creating AlertSender and sending it an alert");
        alertSender = new AlertSender();
        alertSender.sendAlert(alertSender.HILAL_TOKEN, Occupation.Doctor);
    }

    private void createToken() {

        FirebaseTokenGenerator service = new FirebaseTokenGenerator();
        Intent serviceIntent = new Intent(this, FirebaseTokenGenerator.class);
        startService(serviceIntent);
    }

    @Override
    public void onAlertReceived() {
        Log.d(TAG, "A message was received and will be toasted!!!!");
        Intent alertIntent = new Intent(MainActivity.this, DisplayAlertActivity.class);
        startActivity(alertIntent);
    }

    public void getSpeechInput(View view){

        Intent intent  = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        if(intent.resolveActivity(getPackageManager() )!= null){
            startActivityForResult(intent, 10);}
        else{
            Toast.makeText(this, "Your device does not support speech input", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){

            case 10 :
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    finalText.setText(result.get(0));
                }
                break;

         }
    }
}
