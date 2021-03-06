package missionhack.oranges.orangehelp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DisplayAlertActivity extends AppCompatActivity {

    private Alert alert = Alert.getInstance();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_alert);
        TextView messageTextView = (TextView) findViewById(R.id.alertMessage);
        messageTextView.setText(alert.getAlertMessage());
        setAlertIcon();
        setAlertTitle();
        Button viewMapButton = (Button) findViewById(R.id.viewmapbutton);
        viewMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapIntent = new Intent(DisplayAlertActivity.this, MapsActivity.class);
                startActivity(mapIntent);
            }
        });
    }

    private void setAlertTitle() {
        TextView title = (TextView) findViewById(R.id.alertTitle);
        switch (alert.getOccupation()){
            case Fireman:
                title.setText("FIRE");
                break;
            case Doctor:
                title.setText("FIRST AID");
                break;
            case Cop:
                title.setText("CONFLICT");
                break;
        }
    }

    private void setAlertIcon() {
        ImageView image = (ImageView) findViewById(R.id.alertIcon);
        switch (alert.getOccupation()){
            case Cop:
                image.setImageResource(R.mipmap.siren_l);
                break;
            case Doctor:
                image.setImageResource(R.mipmap.medical_l);
                break;
            case Fireman:
                image.setImageResource(R.mipmap.fire_l);
                break;
        }

    }
}
