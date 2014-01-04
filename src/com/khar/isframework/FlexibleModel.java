package com.khar.isframework;

import java.util.HashMap;
import java.util.Map;

import com.khar.isframework.models.ibu.Ibu;
import com.khar.isframework.models.rs.RumahSakit;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

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
		try{
			_attr.put(attributeName, (String)value);
		}
		catch(ClassCastException ex){
			_attr.put(attributeName, String.valueOf(value));
		}
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
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		String[] attr = getAttributes();
		dest.writeString(getTableName());
		for(int i=0;i<attr.length;i++){
			String j = (String)getAttribute(attr[i]);
			dest.writeString(j);
		}
		dest.writeString(getScenario());
	}
	
	
	public abstract FlexibleModel fromParcel(Parcel in);

	@Override
	public  abstract void fillFieldFromCursor(Cursor c);

	@Override
	public abstract Model fromCursor(Cursor c);
	
	public abstract String[] getTags();
	public static Parcelable.Creator<FlexibleModel> CREATOR = new Parcelable.Creator<FlexibleModel>() {
        public FlexibleModel createFromParcel(Parcel in) {
            String tableName = in.readString();
            if(tableName.equals("ibu"))
        	return new Ibu(in);
            else if(tableName.equals("rumah_sakit"))
            	return new RumahSakit(in);
            else return null;
        }

        public FlexibleModel[] newArray(int size) {
            return new FlexibleModel[size];
        }
    };	

}
