package com.khar.isframework;

import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.database.Cursor;

public interface DataAccess {
	/**
	 * save to database
	 * @param tablename
	 * @param cv
	 * @return
	 */
	long save(String tablename,ContentValues cv);
	/**
	 * findAll record on database mathed by query condition
	 * @param tableName
	 * @param condition
	 * @return
	 */
	Cursor findAll(String tableName,Query condition);
	/**
	 * count all record on {tableName} matching condition
	 * @param tableName
	 * @param condition
	 * @return
	 */
	int count(String tableName,Query condition);
	int countBySql(String tableName,String sql);
	/**
	 * delete 
	 * @param tableName
	 * @param condition
	 * @return
	 */
	int delete(String tableName,Query condition);
	/**
	 * find record in table based on query criteria
	 * @param tableName
	 * @param condition
	 * @return
	 */
	boolean exist(String tableName,Query condition);
}
