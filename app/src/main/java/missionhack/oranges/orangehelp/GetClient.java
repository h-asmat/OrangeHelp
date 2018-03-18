package missionhack.oranges.orangehelp;

/**
 * Created by Owner on 3/18/2018.
 */

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class GetClient extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... params) {
        try {
            String urlString = params[0];
            URL url = new URL(urlString);
            URLConnection urlConnection = url.openConnection();
            InputStream in = urlConnection.getInputStream();
            JSONObject reader = new JSONObject(Util.inputStreamToString(in).toString());
            return reader.toString();
        } catch (Exception e){
            Log.d("exception", e.getMessage());
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        // Log.d("Json result", result);
    }
}
