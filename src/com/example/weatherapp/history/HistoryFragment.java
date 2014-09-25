package com.example.weatherapp.history;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.weatherapp.R;

public class HistoryFragment extends Fragment implements LoaderCallbacks<Cursor>{

	private DB db;
	private SimpleCursorAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frag_history, container, false);
		ListView lv = (ListView) view.findViewById(R.id.lvHistory);
		
		db = new DB(getActivity());
		db.open();
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(),R.layout.itemlayout,
				db.getCursor(), new String[]{DB.NAME,DB.TEMP,DB.TIME}, new int[]{R.id.tvName,R.id.tvTemp,R.id.tvTime},0);
		lv.setAdapter(adapter);
		getLoaderManager().initLoader(0, null, this);
		
		return view;
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		return new MyCursorLoader(getActivity(), db);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
		if(adapter != null)
		adapter.swapCursor(cursor);
		
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
	}
	
	
	static class MyCursorLoader extends CursorLoader{
		DB db;
		public MyCursorLoader(Context context,DB db) {
			super(context);
			this.db = db;
			
		}
		
		@Override
		public Cursor loadInBackground() {
			return db.getCursor();
		}
	}
}
