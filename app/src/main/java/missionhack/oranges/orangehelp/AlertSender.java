package missionhack.oranges.orangehelp;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Owner on 3/17/2018.
 */

public class AlertSender {

    private String HILAL_TOKEN = "fyQa7XfLySE:APA91bGLifXle1e18WJ2B9_Ize_K05dYlxX2IiBQEtm5JT0wKk8zQ86KetioivHkWxacJQLhpEbs1goAHnWbaOZEQwDJn7ROdgIDmw_qDP2-fkYmfPuofgXjOEAbCA4aOA8eArBtLffw";

    public AlertSender(){
        sendAlert(HILAL_TOKEN, Occupation.Doctor);

    }

    public void sendAlert(final String token, final Occupation occupation){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("https://fcm.googleapis.com/fcm/send");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Authorization", "key=AAAAU2kGNGg:APA91bHP0MWGhrryiAXoXnZVHRXtIvmzDYH__bsuNd1ru_12K6K_PenOnQ35U9t1Nr82r3AIDW5ZJ_pY9YUbidTfEQ7Ojl4A_oxrKksuMAORqeKD_PFvOIRlZv5MJOOLaT951mYNi7mT");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept","application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);


                    Log.i("JSON", getJsonWithToken(token, occupation));
                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                    os.writeBytes(getJsonWithToken(token, occupation));

                    os.flush();
                    os.close();

                    Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                    Log.i("MSG" , conn.getResponseMessage());

                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();

    }

    private String getJsonWithToken(String token, Occupation occupation){
        String message = "";
        switch (occupation){
            case Cop:
                message = "There is a conflict that needs resolution in your area";
                break;
            case Doctor:
                message = "There is a patient that needs treatment in your area";
                break;
            case Fireman:
                message = "There is a fire that needs putting out in your area";
        }
        return "{ \"to\" : \"" + token +"\", \"data\" : { \"message\" : \"" + message + "\" } }";
    }
}