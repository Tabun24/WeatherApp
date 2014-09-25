package com.example.weatherapp.Utils;

import android.annotation.SuppressLint;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;



public class TimeConverter {

	SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy HH.mm.ss",Locale.UK);
	@SuppressLint("SimpleDateFormat")
	static SimpleDateFormat format2 = new SimpleDateFormat("dd-MM-yyyy");
	
	public static String getDate(Date date){
		return format2.format(date);
		}
	public static String getDate(String date){
		return getDate(new Date(Long.valueOf(date+ "000")));
		}
	
	
	
	public static boolean IsToday(String time ){
		return time.equals(getDate(new Date()));
	}
	
	
	public static String getTime2(String time){
		 DateFormat format =DateFormat.getTimeInstance();
		return format.format(new Date(Long.valueOf(time+"000")));
	}
	

	public static String getTime3(long time){
		 DateFormat format =DateFormat.getTimeInstance();
		return format.format(time);
	}
	
}
