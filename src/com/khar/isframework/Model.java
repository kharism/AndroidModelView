package com.khar.isframework;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.khar.isframework.validator.Validator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
/**
 * Base Class used as a representation of real life object that can be manipulated
 * @author kharisma
 *
 */
public abstract class Model implements Parcelable{
	public static String EDIT="com.khar.isframework.model.edit";
	public static String CREATE="com.khar.isframework.model.create";
	public static String SEARCH="com.khar.isframework.model.search";
	protected DataAccess database;
	private String scenario;
	protected long id;
	protected String[] ATTRIBUTES={"id"};
	public List<String> ERRORS;
	private Map<String,List<Validator>> _validator;
	public Model(Context c){
		database = new SqliteDataAccess(c);
	}
	public Model(DataAccess db) {
		this.database = db;
		ERRORS = new ArrayList<String>();
		_validator = new HashMap<String, List<Validator>>();
		scenario = CREATE;
	}
	/**
	 * data access used in this object
	 * @return
	 */
	public DataAccess getDataAccess(){
		return database;
	}
	/**
	 * set data access used for this database
	 * @param database
	 */
	public void setDataAccess(DataAccess database){
		this.database = database;
	}
	/**
	 * used for building parcelable for this model, so this can be parsed as extras
	 * @param p
	 */
	public Model(Parcel p){
		ERRORS = new ArrayList<String>();
		_validator = new HashMap<String, List<Validator>>();		
	}
	/**
	 * set this object scenario
	 * @param scenario
	 */
	public void setScenario(String scenario){
		this.scenario = scenario;
	}
	/**
	 * get this object scenario
	 * @return
	 */
	public String getScenario(){
		return scenario;
	}
	/**
	 * get form for this model
	 * @return
	 */
	public abstract View getView(Context c);
	/**
	 * 
	 * @param c
	 * @param mode
	 * @return
	 */
	public abstract View getView(Context c,String mode);
	public abstract View getView(int accessLevel,Context c,String mode);
	/**
	 * fill this objects attributes from generated view
	 * @param v
	 */
	public abstract void fromView(View v);
	public abstract String getTableName();
	/**
	 * create content value for database
	 * @return
	 */
	public abstract ContentValues toContentValue();
	/**
	 * needed to get Fields value dynamically
	 * @param attributeName
	 * @return
	 */
	public abstract Object getAttribute(String attributeName);
	/**
	 * needed to set Fields value dynamically
	 * @param attributeName
	 * @param value
	 */
	public abstract void setAttribute(String attributeName,Object value);
	/**
	 * assign value to attribute from cursor
	 */
	public abstract void fillFieldFromCursor(Cursor c);
	/**
	 * create new model from cursor
	 * @param c
	 * @return
	 */
	public abstract Model fromCursor(Cursor c);
	/**
	 * shit done to this model before save
	 */
	protected void beforeSave(){
		
	}
	/**
	 * shit done to this model after save ether save is success or not
	 */
	protected void afterSave(){
		
	}
	/**
	 * override this method to add Validator to certain attributes
	 */
	protected void initValidator(){
		
	}
	public String[] getAttributes(){
		return ATTRIBUTES;
	}
	/**
	 * reset error list->validate this model->populate error list if error exist
	 * @return
	 */
	public boolean validate(){
		boolean valid = true;
		ERRORS.removeAll(ERRORS);
		Iterator<Entry<String,List<Validator>>> it = _validator.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String,List<Validator>> pairs = (Map.Entry)it.next();
			String attr = pairs.getKey();
			List<Validator> vld = pairs.getValue();
			Iterator<Validator> it2 = vld.iterator();
			while(it2.hasNext()){
				Validator val =it2.next();
				if(!val.validate(getAttribute(attr))){
					valid=false;
					ERRORS.add(val.getErrorMesage());
				}
			}
		}
		return valid;
	}
	/**
	 * validate->before save->save->after save
	 * @return
	 */
	public boolean save(){
		if(this.validate()){
			beforeSave();
			long h =database.save(getTableName(), toContentValue());
			setScenario(EDIT);
			afterSave();
			if(h>0){
				this.id =h;
				return true;
			}
			else {
				ERRORS.add("unknown shit happeded");
				return false;
			}
		}
		else
			return false;
	}
	/**
	 * find all Model 
	 * @return
	 */
	public List<Model> findAll(){
		Query condition = new Query();
		condition.addSelect(getAttributes());
		return findAll(condition);
	}
	/**
	 * find all Model based on query condition
	 * @param condition
	 * @return
	 */
	public List<Model> findAll(Query condition){
		List<Model> lm = new ArrayList<Model>();
		Cursor curr = database.findAll(getTableName(), condition);
		curr.moveToFirst();
		while(!curr.isAfterLast()){
			Model g = fromCursor(curr);
			g.setScenario(EDIT);
			lm.add(g);
			curr.moveToNext();
		}
		return lm;
	}
	/**
	 * count all record on table
	 * @return
	 */
	public int count(){
		Query q = new Query();
		return database.count(getTableName(),q);
	}
	/**
	 * count all record with matched condition 
	 * @param condition
	 * @return
	 */
	public int count(Query condition){
		return database.count(getTableName(), condition);
	}
	/**
	 * count all record which has attributes matched attr
	 * @param tableName
	 * @param attr
	 * @return
	 */
	public int countByAttributes(Map<String,String> attr){
		Query condition = new Query();
		condition.addWhere(attr);
		return database.count(getTableName(), condition);
	}
	/**
	 * 
	 * @param sql the here clause
	 * @return
	 */
	public int countBySql(String sql){
		Query condition = new Query();
		condition.addWhere(sql);
		return database.count(getTableName(), condition);
	}
	public int delete(){
		Query condition = new Query();
		return database.delete(getTableName(), condition);
	}
	public int delete(Query condition){
		return database.delete(getTableName(), condition);
	}
	public int deleteByAttributes(Map<String,String> attr){
		Query condition = new Query();
		condition.addWhere(attr);
		return database.delete(getTableName(), condition);
	}
	public int deleteByPk(int a){
		Query condition = new Query();
		condition.addWhere("id="+a);
		return database.delete(getTableName(), condition);
	}
	public boolean exist(Query condition){
		return database.exist(getTableName(), condition);
	}
	public boolean exist(String tableName,String sql){
		Query q = new Query();
		q.addWhere(sql);
		return database.exist(getTableName(), q);
	}
	public boolean exist(String tableName,Map<String,String> attr){
		Query q = new Query();
		q.addWhere(attr);
		return database.exist(getTableName(), q);
	}
	
}
