package com.example.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.weatherapp.preference.PrefActivity;

public class MainActivity extends ActionBarActivity {

	private Fragment frag;
	private Fragment fragment ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		frag = null;
		try {
			frag = (Fragment) Class.forName("com.example.weatherapp.WeatherFragment").newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		getSupportFragmentManager().beginTransaction().add(R.id.cont, frag).commit();
		
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.details_menu_actions, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
		case R.id.refresh:			
			if(frag != null){
				((WeatherFragment)frag).mService.load();}
			
			return true;
		case R.id.prSetting:
			Intent intent = new Intent(this, PrefActivity.class);
			startActivity(intent);
			return true;
		case R.id.History:
			
			try {   			   
				fragment = (Fragment) Class.forName("com.example.weatherapp.history.HistoryFragment").newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
			getSupportFragmentManager().beginTransaction().replace(R.id.cont, fragment).addToBackStack(null).commit();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
}
