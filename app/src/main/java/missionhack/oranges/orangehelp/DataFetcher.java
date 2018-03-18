package missionhack.oranges.orangehelp;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.net.*;
import java.nio.charset.Charset;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.json.*;

/**
 * This class will fetch the data from the firebase
 * @author harmanRandhawa
 *
 */
public class DataFetcher implements Protocols{

	private String cmd ;
	private String sURL = "https://orangehelp-99f5d.firebaseio.com/Inhabitants.json";
	private String returnedToken;

	public DataFetcher(){
		this.cmd ="";
	}
	/**
	 * Constructor that creates the list when a command is sent 
	 * @param cmd
	 */
	public DataFetcher(String cmd){
		// TODO Auto-generated constructor stub
		this.cmd = cmd;
		try {
			startProcessing(cmd);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String getToken() {
		String token = "";
		String urlString = getURLFromOccupation(Occupation.Doctor);
		AsyncTask<String, Void, String> getRequest = new GetClient().execute(urlString);
		try {
			String result = getRequest.get();
			try {
				JSONObject object = new JSONObject(result);
				Iterator<String> iterator = object.keys();
				while (iterator.hasNext()) {
					JSONObject obj = object.getJSONObject(iterator.next());
					token = obj.getString("Token");

				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return token;
	}

	public static String getURLFromOccupation(Occupation occupation){
		String url = "";
		switch (occupation){
			case Cop:
				url = "https://orangehelp-99f5d.firebaseio.com/Inhabitants/Cops.json";
				break;
			case Fireman:
				url = "https://orangehelp-99f5d.firebaseio.com/Inhabitants/Firemen.json";
				break;
			case Doctor:
				url = "https://orangehelp-99f5d.firebaseio.com/Inhabitants/Doctors.json";
				break;
		}

		return url;

	}

	/** 
	 * This method will make connection with the database and fetch data from it
	 */
	public String startProcessing(String cmd){
		JSONObject json;
		String str ="";
		try {
			json = readJsonFromUrl(sURL);
			str = getToken(json.getJSONObject(cmd).toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			return str;
		}
		
	}
	/**
	 * This method will reads all the data from the given buffer 
	 * @param rd Reader buffer that contains all the data 
	 * @return String with all the data readed from the buffer
	 * @throws IOException If there is an error in connection
	 */
	private String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		    }
		return sb.toString();
		}
	/**
	 * This method will read the data from the url 
	 * @param url Target url from where data will be read
	 * @return JSON Object from the url 
	 * @throws IOException If there is an error in connection 
	 * @throws JSONException If theres an error in making JSON object
	 */
	private JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
		InputStream is = new URL(url).openStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject json = new JSONObject(jsonText);
			return json;
			} 
		finally {
			is.close();
		    }
		}
	/**
	 * This method will extract the token from JSON object 
	 * @param str JSON object that is sent in the form of string
	 * @return Returns the Token from JSON object
	 */
	private String getToken(String str) {
		String[] arr = str.split("\"");
		return arr[arr.length-2];
	}
	
}
