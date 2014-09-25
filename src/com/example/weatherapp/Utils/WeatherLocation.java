package com.example.weatherapp.Utils;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

public class WeatherLocation {
	
	private String baseUrl ="http://api.openweathermap.org/data/2.5/weather";
	private String APPID = "APPID=8bdf1d8220dec4f79a0108a52d61c8cb";
	
	private String NameOfCity;
	private String Latitude;
	private String Longitude;
	private long timeOut = 1000;//3600000;
	
	private Geocoder geocoder;
	private Location loc;
	private  Context context;
	private  LocationManager locManager;
	private static Criteria searchProviderCriteria = new Criteria();
	static{
		searchProviderCriteria.setPowerRequirement(Criteria.POWER_LOW);
		searchProviderCriteria.setAccuracy(Criteria.ACCURACY_COARSE);
		searchProviderCriteria.setCostAllowed(false);
	}
	
	
	public WeatherLocation(Context context){
		this.context = context;
		geocoder = new Geocoder(context);
	}
	
	public  String getUrl(){
		locManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
		String pref = sp.getString("list_pref", "city");
		
			String provider = locManager.getBestProvider(searchProviderCriteria, true);
			loc= locManager.getLastKnownLocation(provider);
			if(loc == null || (System.currentTimeMillis()- loc.getTime())>timeOut){
				locManager.requestSingleUpdate(provider, new LocationListener() {
					
					@Override
					public void onStatusChanged(String provider, int status, Bundle extras) {
					}
					@Override
					public void onProviderEnabled(String provider) {
					}
					
					@Override
					public void onProviderDisabled(String provider) {
					}
					
					@Override
					public void onLocationChanged(Location location) {
						loc = location;
						Log.d("myLogs","onLocationChanged");
					}
				}, null);
			}
			
		try {
				List<Address> list = 	geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
				list.get(0);
				
					NameOfCity = list.get(0).getLocality();
					Latitude = String.format("%.2f", loc.getLatitude());
					Longitude = String.format("%.2f", loc.getLongitude()); 
					
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if(pref.equalsIgnoreCase("city")){
				return baseUrl+"?"+APPID+"&q="+NameOfCity;
			} else {
				return baseUrl+"?"+APPID+"&lat="+Latitude+"&lon="+Longitude;
			}
	}

}
