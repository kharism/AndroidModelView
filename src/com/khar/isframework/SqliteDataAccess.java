package com.khar.isframework;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SqliteDataAccess implements DataAccess {
	private MySqliteHelper _helper;
	private SQLiteDatabase _database;
	public SqliteDataAccess(Context context) {
		_helper = new MySqliteHelper(context);
		_database = _helper.getWritableDatabase();
	}

	@Override
	public long save(String tableName, ContentValues values) {
		if(values.containsKey("id")){
			_database.update(tableName, values, "id="+values.getAsString("id"), null);
			return (long)values.getAsInteger("id");
		}			
		return _database.insert(tableName, null, values);
	}

	@Override
	public Cursor findAll(String tableName, Query condition) {
		return _database.query(condition.distinct, tableName, condition.getSelect(), condition.getWhere(), null, condition.getGroupBy(), condition.getHaving(), condition.getOrderBy(), condition.getLimit());		
	}

	@Override
	public int count(String tableName, Query condition) {
		String[] countCol = {" count(id) as count"};
		Cursor c = _database.query(condition.distinct, tableName, countCol, condition.getWhere(), null, condition.getGroupBy(), condition.getHaving(), condition.getOrderBy(), condition.getLimit());
		return c.getInt(0);
	}

	@Override
	public int countBySql(String tableName, String sql) {
		Query condition = new Query();
		condition.addWhere(sql);
		return count(tableName, condition);
	}

	@Override
	public int delete(String tableName, Query condition) {
		return _database.delete(tableName, condition.getWhere(),null);		
	}

	@Override
	public boolean exist(String tableName, Query condition) {
		Cursor c = _database.query(condition.distinct, tableName, condition.getSelect(), condition.getWhere(), null, condition.getGroupBy(), condition.getHaving(), condition.getOrderBy(), condition.getLimit());
		return c.getCount()>0;
	}

}
