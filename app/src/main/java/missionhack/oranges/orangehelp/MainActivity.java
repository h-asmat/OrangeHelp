package missionhack.oranges.orangehelp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyFirebaseInstanceIDService service = new MyFirebaseInstanceIDService();
        Intent serviceIntent = new Intent(this, MyFirebaseInstanceIDService.class);
        startService(serviceIntent);
        AlertSender sender = new AlertSender();
        //sender.execute();
    }
}
