package com.khar.isframework;

import com.khar.isframework.models.ibu.Ibu;
import com.khar.isframework.models.rs.RumahSakit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySqliteHelper extends SQLiteOpenHelper{
	private static String DATABASE_NAME="framework.db"; 
	  private static final int DATABASE_VERSION = 2;
	public MySqliteHelper(Context context) {
		super(context, DATABASE_NAME, null,DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase database) {
		String query = "CREATE TABLE sqlite_sequence(name,seq);";
		//Log.i("query",query);
		database.execSQL(RumahSakit.TABLE_STRING);
		database.execSQL(Ibu.TABLE_STRING);
		//database.execSQL(query);
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int arg1, int arg2) {
		
	}

}
