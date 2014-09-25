package com.example.weatherapp.preference;


import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.example.weatherapp.R;

public class PrefActivity extends PreferenceActivity {
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.pref);
		
	}

}
