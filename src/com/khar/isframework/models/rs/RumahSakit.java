package com.khar.isframework.models.rs;

import android.content.Context;
import android.database.Cursor;
import android.os.Parcel;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.khar.isframework.DataAccess;
import com.khar.isframework.FlexibleModel;
import com.khar.isframework.Model;
import com.khar.isframework.formbuilder.FormBuilder;

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
		return getView(0, c);
	}
	
	@Override
	public String[] getAttributes() {
		String[] attr = {"id","tipe","nama","kecamatan","propinsi"};
		return attr;
	}

	@Override
	public View getView(int accessLevel, final Context c) {
		FormBuilder fb = new FormBuilder(c);
		String[] attr = getAttributes();
		for(int i=1;i<attr.length;i++){
		String val = null;
			if(getScenario().equals(EDIT)||getScenario().equals(VIEW)){
				val=(String)getAttribute(attr[i]);
			}
			if(getScenario().equals(EDIT)||getScenario().equals(CREATE))
			fb.addTextField(attr[i], attr[i],val);
			else if(getScenario().equals(VIEW)){
				fb.addViewField(attr[i],val);
			}
		}
		final View ll = fb.getView();
		if(this.getScenario().equals(Model.CREATE)||this.getScenario().equals(Model.EDIT)){
			fb.addButton("Save", new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					RumahSakit.this.fromView(ll);
					if(RumahSakit.this.validate()){
						RumahSakit.this.save();
						Toast.makeText(c, "Sukses Save", Toast.LENGTH_SHORT).show();
					}
					else{
						Toast.makeText(c, "Gagal Save, Input tidak valid", Toast.LENGTH_SHORT).show();
					}
				}
			});
		}
		return ll;		
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
