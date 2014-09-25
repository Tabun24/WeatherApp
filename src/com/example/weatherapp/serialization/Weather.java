package com.example.weatherapp.serialization;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.gson.annotations.SerializedName;

public class Weather {

	
	

	@SerializedName("id")
	private String id ;
	
	@SerializedName("main")
	private String main;
	
	@SerializedName("description")
	private String description ;
	
	@SerializedName("icon")
	private String icon ;
	
	
	
	public String getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public String getIcon() {
		return icon;
	}
	
	
	
	public Bitmap getIconBitmap(Context context,int scale) {
		int resuorceId = context.getResources().getIdentifier("icon"+icon, "drawable",context.getPackageName());
		Bitmap original = BitmapFactory.decodeResource(context.getResources(), resuorceId);
		Bitmap b  = Bitmap.createScaledBitmap(original, original.getWidth()*scale, original.getHeight()*scale, false);
		return b;
	}
	
	

	
	
	
	
	
	
	
}
