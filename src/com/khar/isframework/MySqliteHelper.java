package com.khar.isframework;

import com.khar.isframework.models.Ibu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MySqliteHelper extends SQLiteOpenHelper{
	private static String DATABASE_NAME="framework.db"; 
	  private static final int DATABASE_VERSION = 1;
	public MySqliteHelper(Context context) {
		super(context, DATABASE_NAME, null,DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase database) {
		String query = Ibu.TABLE_STRING+"CREATE TABLE sqlite_sequence(name,seq);";
		database.execSQL(query);
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int arg1, int arg2) {
		// TODO Auto-generated method stub
	}

}
