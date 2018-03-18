package missionhack.oranges.orangehelp;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import android.speech.RecognizerIntent;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements OnAlertReceivedListener{
    private TextView finalText;

    private static final String TAG = "MainActivity";
    private static final String FIRE_DISTRESS_CALL = "FIRE";
    private static final String CONFLICT_DISTRESS_CALL = "CONFLICT";
    private static final String MEDICAL_DISTRESS_CALL = "MEDICAL";
    private Occupation occupation;

    FirebaseMessageReceiver messageReceiver;
    AlertSender alertSender;
    Alert alert = Alert.getInstance();
    DataFetcher dataFetcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        finalText = (TextView) findViewById(R.id.thetext);
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
        dataFetcher = new DataFetcher();
        alert.setAlertReceivedListener(this);
        Log.d(TAG, "Creating message receiver");
        messageReceiver = new FirebaseMessageReceiver();
        createToken();
        /*try {
            sendAlert();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }


    private void sendAlert(String token, Occupation occupation) throws InterruptedException {
        Log.d(TAG, "sendAlert: Creating AlertSender and sending it an alert");
        alertSender = new AlertSender();
        alertSender.sendAlert(token, occupation);
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

        GPStracker g = new GPStracker(getApplicationContext());
        Location l = g.getLocation();
        System.out.println(l);
        if (l != null) {
            double lat = l.getLatitude();
            double lon = l.getLongitude();
            Toast.makeText(getApplicationContext(), "LAT: "+lat+"\n LON: "+lon, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){

            case 10 :
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    finalText = (TextView) findViewById(R.id.thetext);
                    finalText.setText(result.get(0));
                    try {
                        checkWordAgainstDatabase(result.get(0));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                }
                break;

         }
    }

    private void checkWordAgainstDatabase(String word) throws InterruptedException {
        String token = null;
        // pass the word from here to the database
        if (word.toUpperCase().equals(FIRE_DISTRESS_CALL.toUpperCase())) {
            try {
                occupation = Occupation.Fireman;
                token = dataFetcher.getToken(Occupation.Fireman);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (word.toUpperCase().equals(CONFLICT_DISTRESS_CALL.toUpperCase())){
            try {
                occupation = Occupation.Cop;
                token = dataFetcher.getToken(Occupation.Cop);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (word.toUpperCase().equals(MEDICAL_DISTRESS_CALL.toUpperCase())) {
            try {
                occupation = Occupation.Doctor;
                token = dataFetcher.getToken(Occupation.Doctor);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Log.d(TAG, "onActivityResult: TOKEN IS: " + token);
        if (token!=null) {
            sendAlert(token, occupation);
        }
    }
}
