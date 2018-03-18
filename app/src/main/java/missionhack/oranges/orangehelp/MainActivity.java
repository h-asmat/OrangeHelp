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
import android.view.View;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextView finalText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        finalText = (TextView) findViewById(R.id.thetext);
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
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
                    finalText.setText(result.get(0));
                }
                break;

         }
        MyFirebaseInstanceIDService service = new MyFirebaseInstanceIDService();
        Intent serviceIntent = new Intent(this, MyFirebaseInstanceIDService.class);
        startService(serviceIntent);
    }
}
