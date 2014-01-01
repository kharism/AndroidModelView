package com.khar.isframework.models;

import android.content.Context;
import android.database.Cursor;
import android.os.Parcel;
import android.view.View;
import android.widget.EditText;

import com.khar.isframeork.formbuilder.FormBuilder;
import com.khar.isframework.DataAccess;
import com.khar.isframework.FlexibleModel;
import com.khar.isframework.Model;

public class RumahSakit extends FlexibleModel {

	public static final String TABLE_STRING="create table \"rumah_sakit\"(\"id\" INTEGER PRIMARY KEY AUTOINCREMENT,\"tipe\" TEXT,\"nama\" TEXT,\"kecamatan\" TEXT,\"propinsi\" TEXT);";
	public RumahSakit(Parcel p) {
		super(p);
		// TODO Auto-generated constructor stub
	}
	public RumahSakit(DataAccess db) {
		super(db);
	}

	@Override
	public FlexibleModel fromParcel(Parcel in) {
		// TODO Auto-generated method stub
		return new RumahSakit(in);
	}

	@Override
	public void fillFieldFromCursor(Cursor c) {
		setAttribute("id", c.getInt(0));
		setAttribute("tipe", c.getString(1));
		setAttribute("nama", c.getString(2));
		setAttribute("kecamatan", c.getString(3));
		setAttribute("propinsi", c.getString(4));
		
	}

	@Override
	public Model fromCursor(Cursor c) {
		RumahSakit rs = new RumahSakit(database);
		rs.setAttribute("id", c.getInt(0));
		rs.setAttribute("tipe", c.getString(1));
		rs.setAttribute("nama", c.getString(2));
		rs.setAttribute("kecamatan", c.getString(3));
		rs.setAttribute("propinsi", c.getString(4));
		return rs;
	}

	@Override
	public String[] getTags() {
		// TODO Auto-generated method stub
		return getAttributes();
	}

	@Override
	public View getView(Context c) {
		// TODO Auto-generated method stub
		return getView(c,"create");
	}

	@Override
	public View getView(Context c, String mode) {
		return getView(0, c, mode);
	}
	
	@Override
	public String[] getAttributes() {
		String[] attr = {"id","tipe","nama","kecamatan","propinsi"};
		return attr;
	}

	@Override
	public View getView(int accessLevel, Context c, String mode) {
		FormBuilder fb = new FormBuilder(c);
		String[] attr = getAttributes();
		for(int i=1;i<attr.length;i++){
		String val = null;
			if(mode.equals(EDIT)){
				val=(String)getAttribute(attr[i]);
			}
			fb.addTextField(attr[i], attr[i],val);
		}
		return fb.getView();
	}

	@Override
	public void fromView(View v) {
		String[] tags = getAttributes();		
		for(int i=1;i<tags.length;i++){
			EditText et = (EditText) v.findViewWithTag(tags[i]);
			setAttribute(tags[i], et.getText().toString());
		}
	}

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return "rumah_sakit";
	}

}
