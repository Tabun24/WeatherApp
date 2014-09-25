package com.example.weatherapp.serialization;

import com.example.weatherapp.Utils.TimeConverter;
import com.google.gson.annotations.SerializedName;

public class Sys {

	
	@SerializedName("type")
	private String type ;
	
	@SerializedName("id")
	private String id;
	
	@SerializedName("message")
	private String message;
	
	@SerializedName("country")
	private String country;
	
	@SerializedName("sunrise")
	private String sunrise;
	
	@SerializedName("sunset")
	private String sunset;
	
	
	
	public String getType() {
		return type;
	}

	public String getId() {
		return id;
	}

	public String getMessage() {
		return message;
	}

	public String getCountry() {
		return country;
	}

	public String getSunrise() {
		return TimeConverter.getTime2(sunrise);
		
	}

	public String getSunset() {
		return TimeConverter.getTime2(sunset);
		
	}

	
}
