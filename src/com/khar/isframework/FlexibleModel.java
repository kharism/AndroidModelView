package com.khar.isframework;

import java.util.HashMap;
import java.util.Map;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;

/**
 * a flexible model that uses map to store attributes, already implements setAttr and getAttr method
 * just like Plain Old PHP Object :P
 * @author kharisma
 *
 */
public abstract class FlexibleModel extends Model {
	Map<String,String> _attr;
	public FlexibleModel(DataAccess db) {
		super(db);
		_attr = new HashMap<String, String>();
	}	

	@Override
	public ContentValues toContentValue() {
		ContentValues cv = new ContentValues();
		String[] attr = getAttributes();
		int start = 0;
		if(this.getScenario()==Model.CREATE)
			start=1;
		for(int i=start;i<attr.length;i++){
			cv.put(attr[i], (String)getAttribute(attr[i]));
		}
		return cv;
	}

	@Override
	public Object getAttribute(String attributeName) {
		return _attr.get(attributeName);
	}

	public void setAttribute(String name,String value){
		setAttribute(name, (Object)value);
	}
	@Override
	/**
	 * set attribute
	 * @param attributeName
	 * @param value (string only please)
	 */
	public void setAttribute(String attributeName, Object value) {
		_attr.put(attributeName, (String)value);
	}
	
	public FlexibleModel(Parcel p) {
		super(p);
		_attr = new HashMap<String, String>();
		String[] attr = getAttributes();
		for(int i=0;i<attr.length;i++){
			String h = p.readString();
			setAttribute(attr[i], h);
		}
		setScenario(p.readString());
	}
	
	
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		String[] attr = getAttributes();
		for(int i=0;i<attr.length;i++){
			String j = (String)getAttribute(attr[i]);
			dest.writeString(j);
		}
		dest.writeString(getScenario());
	}

	@Override
	public  abstract void fillFieldFromCursor(Cursor c);

	@Override
	public abstract Model fromCursor(Cursor c);
	
	public abstract String[] getTags();

}
