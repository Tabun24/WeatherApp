package com.example.weatherapp.serialization;

import com.google.gson.annotations.SerializedName;

public class Main {

	
	@SerializedName("temp")
	private String temp;
	
	@SerializedName("pressure")
	private String pressure;
	
	@SerializedName("humidity")
	private String  humidity;
	
	@SerializedName("temp_min")
	private String temp_min;
	
	@SerializedName("temp_max")
	private String temp_max;
	
	public String getTemp() {
		return String.format("%.1f",Float.valueOf(temp)-273.15) + " \u2103";
	}

	public String getPressure() {
		return pressure +" hPa";
	}

	public String getHumidity() {
		return humidity+ " %";
	}

	public String getTemp_min() {
		return temp_min;
	}

	public String getTemp_max() {
		return temp_max;
	}

	

	
	
	
	
	
	

	
}
