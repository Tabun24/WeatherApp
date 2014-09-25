package com.example.weatherapp;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;


public class WeatherWidget extends AppWidgetProvider {
	
	public  final static String ACTION_UPDATE_WIDGET = "com.example.weatherapp.update.widget";
	
	public void onUpdate(Context context,AppWidgetManager appWidgetManager,int[] appWidgetIds){
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		Log.d("myLogs","onUpdate Context = " + context.hashCode());
		
		for(int id : appWidgetIds){
			udateWidget(context,appWidgetManager,id);
		}
	}

	static void udateWidget(Context context,AppWidgetManager appWidgetManager, int id) {
		RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.widget2);
		Intent serviceIntent = new Intent(context, WeatherService.class);
		PendingIntent pIntent = PendingIntent.getService(context, 0, serviceIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		remoteView.setOnClickPendingIntent(R.id.tvWidgetUdate, pIntent);
		appWidgetManager.updateAppWidget(id, remoteView);
	}
	
	public void onReceive(Context context, Intent intent){
		super.onReceive(context, intent);
		Log.d("myLogs","onReceive");
		if(intent.getAction().equalsIgnoreCase(WeatherService.ACTION_WIDGET_UPDATE)){
			
			Bundle bundle = intent.getExtras();
			RemoteViews widgetView = new RemoteViews(context.getPackageName(), R.layout.widget2);
			widgetView.setTextViewText(R.id.tvWidgetTemp, bundle.getString(WeatherService.TAG_TEMP));
			widgetView.setImageViewBitmap(R.id.tvWidgetIcon, (Bitmap) bundle.getParcelable(WeatherService.TAG_ICON));
			ComponentName cName = new ComponentName(context,WeatherWidget.class);
			AppWidgetManager.getInstance(context).updateAppWidget(cName, widgetView);
		}
	}

}