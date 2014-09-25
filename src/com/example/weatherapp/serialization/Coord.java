package com.example.weatherapp.serialization;

import com.google.gson.annotations.SerializedName;

public class Coord {

	@SerializedName("lon")
	private String lon;
	
	@SerializedName("lat")
	private String lat ;
	
	public String getLon() {
		return lon;
	}

	public String getLat() {
		return lat;
	}

	public String getLatLon(){
		return lat +" "+lon;
	}
}
