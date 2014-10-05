package com.example.weatherapp;


import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Binder;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.weatherapp.Utils.TimeConverter;
import com.example.weatherapp.Utils.WeatherLocation;
import com.example.weatherapp.history.DB;
import com.example.weatherapp.serialization.GsonParse;
import com.google.gson.Gson;

public class WeatherService extends Service {

	public final static String ACTION_WIDGET_UPDATE = "com.example.weatherapp.widget.update";
	public final static String TAG_TEMP ="temperature";
	public final static String TAG_ICON ="icon";
	
	private final IBinder  mBinder = new LocalBinder();
	private GsonParse gsonParser;
	private AlarmManager am;
	private WeatherLocation wLoc;
			
	@Override
	public void onCreate() {
		super.onCreate();
		am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		setUpdateMode();
		wLoc = new WeatherLocation(getApplicationContext());
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if(netInfo != null && netInfo.isConnected()){
	    	load(); 
	    } else {
	    	Toast.makeText(getApplicationContext(),"No Internet access", Toast.LENGTH_SHORT).show();
	    }
		return Service.START_NOT_STICKY;
	}
	
	private void setUpdateMode(){
		Intent intent = new Intent(getApplicationContext(), WeatherService.class);
		PendingIntent pIntent = PendingIntent.getService(getApplicationContext(), 0, intent,PendingIntent.FLAG_UPDATE_CURRENT );
		am.setRepeating(AlarmManager.RTC, System.currentTimeMillis(), 10800000, pIntent); // 3 hours
		
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}
	
	public class LocalBinder extends Binder {
		WeatherService getService(){
			return WeatherService.this;
		}
	}
	
	public void load(){
		RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
		JsonObjectRequest mRequest = new JsonObjectRequest(Method.GET, wLoc.getUrl(), null, new Response.Listener<JSONObject>() {
			
			@Override
			public void onResponse(JSONObject response) {
				Gson gson = new Gson();
				gsonParser = gson.fromJson(response.toString(), GsonParse.class);
				try {
					String cod = response.getString("cod");  // {"cod":"404","message":"Error: Not found city"}
					if(cod.equalsIgnoreCase("404")){ 
							gsonParser = null;
					Toast.makeText(getApplicationContext(), "Error: Not found city", Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				if(gsonParser != null){
					
					sendBroadcast(new Intent(WeatherFragment.ACTION_DOWNLOAD_COMPLETE));
					sendBroadcastWidget(gsonParser);
					
					DB db = new DB(getApplicationContext());
					db.open();
					SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
					String name = sp.getString("list_pref", "city").equalsIgnoreCase("city")? gsonParser.getName() : gsonParser.getCoord().getLatLon();
					if(!TimeConverter.IsToday(db.getDate()))
						db.deleteAll();
					db.addNewItem(name, gsonParser.getMain().getTemp(),gsonParser.getDtTime(),gsonParser.getDtDate());
					db.close();
					
					stopSelf();
				}	
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(getApplicationContext(),"No Internet access" ,Toast.LENGTH_SHORT).show();
			}
		});
		
		mQueue.add(mRequest);
	}
	
	public GsonParse getGsonParser(){
		return gsonParser;
	}

	private void sendBroadcastWidget(GsonParse gson){
		Intent  intent  = new Intent(getApplicationContext(), WeatherWidget.class);
		intent.setAction(ACTION_WIDGET_UPDATE);
		intent.putExtra(TAG_TEMP,gson.getMain().getTemp());  
		intent.putExtra(TAG_ICON, gson.getWeather().get(0).getIconBitmap(getApplicationContext(), 1));
		sendBroadcast(intent);
	}
	
}
