package com.example.weatherapp.history;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;



public class DB  {

	public static String MYTABLE = "mytable";
	public static String ID = "_id";
	public static String NAME = "name";
	public static String TEMP = "temp";
	public static String TIME = "time";
	public static String DATE = "date";
	private final String MYTABLE_DB_CREATE ="create table "+MYTABLE
			+" ("+ID+" integer primary key autoincrement," +NAME+" text,"+TEMP+" text,"+ TIME+ " text,"+DATE +" text"+");";
	
	private  final Context mCtx;
	private DBHelper mDBHelper;
	private SQLiteDatabase mDB;
	
	
	public DB(Context ctx){
		mCtx = ctx;
	}
	
	
	public void open(){
		mDBHelper = new DBHelper(mCtx, "myDB",null, 1);
		mDB = mDBHelper.getWritableDatabase();
	}
	
	public  void close(){
		if(mDB !=null) mDB.close();
	}
	
	
	public Cursor getCursor(){
		return mDB.query(MYTABLE, null, null, null, null, null, null);
	}

	public String getDate(){
		Cursor c =getCursor();
		if(c.moveToFirst())
		return c.getString(c.getColumnIndex(DATE));
		else return "";
	}
	
	
	public  void addNewItem(String name, String temp,String time,String date){
		ContentValues cv = new ContentValues();
		cv.put(NAME, name);
		cv.put(TEMP,temp);
		cv.put(TIME, time);
		cv.put(DATE, date);
		 mDB.insert(MYTABLE, null, cv);
	}
	
	public void deleteAll(){
		mDB.delete(MYTABLE, null, null);
	}
	
	private class DBHelper extends SQLiteOpenHelper{

		public DBHelper(Context context, String name, CursorFactory factory,int version) {
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(MYTABLE_DB_CREATE);
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		}
	}
}

