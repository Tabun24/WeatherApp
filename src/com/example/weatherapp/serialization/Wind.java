package com.example.weatherapp.serialization;

import com.google.gson.annotations.SerializedName;

public class Wind {
	
	@SerializedName("speed")
	private String speed;
	
	@SerializedName("deg")
	private String deg ;
	

	public String getSpeed() {
		return speed;
	}
	public String getDeg() {
		return deg;
	}


}
