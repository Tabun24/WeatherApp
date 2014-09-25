package com.example.weatherapp;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weatherapp.WeatherService.LocalBinder;
import com.example.weatherapp.serialization.GsonParse;

public class WeatherFragment extends Fragment {

	WeatherService mService;
	boolean mBound = false;
	private Intent intent;
	public final static String ACTION_DOWNLOAD_COMPLETE = "com.example.weatherapp.downloand.complete";
	
	private TextView tvCity;
	private TextView tvTime ;
	private TextView tvTimeValue ;
	private TextView tvTemperature;
	private TextView tvTemperatureValue;
	private TextView tvWeatherDescription;
	private ImageView ivWeatherIcon;
	private TextView tvPressure;
	private TextView tvPressureValue;
	private TextView tvHumidity;
	private TextView tvHumidityValue;
	private TextView tvSunRise;
	private TextView tvSunRiseValue;
	private TextView tvSunSet;
	private TextView tvSunSetValue;
	
	private View view;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		intent = new Intent(getActivity(), WeatherService.class);
		getActivity().startService(intent);
		getActivity().bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
		IntentFilter filter = new IntentFilter(ACTION_DOWNLOAD_COMPLETE);
		getActivity().registerReceiver(mReceiver, filter);
	}
	
	private BroadcastReceiver mReceiver = new BroadcastReceiver(){

		@Override
		public void onReceive(Context context, Intent intent) {
			if(ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction())){
				fillView();
			}
		}
		
	};
	

	@Override
	public void onStop() {
		super.onStop();
		if(mBound){
			getActivity().unbindService(mConnection);
			mBound = false;
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		if(view ==null){
		view = inflater.inflate(R.layout.frag, container, false);
		tvCity = (TextView) view.findViewById(R.id.tvCity);
		tvTime = (TextView) view.findViewById(R.id.tvTime);
		tvTimeValue = (TextView) view.findViewById(R.id.tvTimeValue);
		tvTemperature = (TextView) view.findViewById(R.id.tvTemperature);
		tvTemperatureValue = (TextView) view.findViewById(R.id.tvTemperatureValue);
		tvWeatherDescription = (TextView) view.findViewById(R.id.tvWeatherDescription);
		ivWeatherIcon = (ImageView) view.findViewById(R.id.ivWeatherIcon);
		tvPressure = (TextView) view.findViewById(R.id.tvPressure);
		tvPressureValue= (TextView) view.findViewById(R.id.tvPressureValue);
		tvHumidity = (TextView) view.findViewById(R.id.tvHumidity);
		tvHumidityValue = (TextView) view.findViewById(R.id.tvHumidityValue);
		tvSunRise = (TextView) view.findViewById(R.id.tvSunRise);
		tvSunRiseValue = (TextView) view.findViewById(R.id.tvSunRiseValue);
		tvSunSet = (TextView) view.findViewById(R.id.tvSunSet);
		tvSunSetValue = (TextView) view.findViewById(R.id.tvSunSetValue);
		} else ((ViewGroup)view.getParent()).removeView(view);
		
		return view;
	}
	
	private void fillView(){
		GsonParse gson =mService.getGsonParser();
		Resources r = getResources();
		tvCity.setText(gson.getName());
		tvTime.setText(r.getString(R.string.time));
		tvTimeValue.setText(gson.getDtTime());
		tvTemperature.setText(r.getString(R.string.temperature));
		tvTemperatureValue.setText(gson.getMain().getTemp());
		tvWeatherDescription.setText(gson.getWeather().get(0).getDescription());
		tvPressure.setText(r.getString(R.string.pressure));
		tvPressureValue.setText(gson.getMain().getPressure());
		tvHumidity.setText(r.getString(R.string.humidity));
		tvHumidityValue.setText(gson.getMain().getHumidity());
		ivWeatherIcon.setImageBitmap(gson.getWeather().get(0).getIconBitmap(getActivity(), 3));
		tvSunRise.setText(r.getString(R.string.sun_rise));
		tvSunRiseValue.setText(gson.getSys().getSunrise());
		tvSunSet.setText(r.getString(R.string.sun_set));
		tvSunSetValue.setText(gson.getSys().getSunset());
		
	}
	
	
	private ServiceConnection mConnection  = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			mBound = false;
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			LocalBinder binder = (LocalBinder) service;
			mService = binder.getService();
			mBound = true;
		}
	};
	
	public void onDestroy(){
		super.onDestroy();
		getActivity().unregisterReceiver(mReceiver);
		view = null;
	};
	
}
