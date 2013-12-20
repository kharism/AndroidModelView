package com.khar.isframework.models;

import java.util.ArrayList;
import java.util.List;
import com.kharcustom.testframework.R;

import android.content.Context;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.khar.isframework.DataAccess;
import com.khar.isframework.FlexibleModel;
import com.khar.isframework.Model;

public class Ibu extends FlexibleModel {

	public static final String TABLE_STRING="create table \"ibu\"(\"id\" INTEGER PRIMARY KEY AUTOINCREMENT,\"ktp\" TEXT,\"nama\" TEXT);";
//"CREATE TABLE sqlite_sequence(name,seq);";
	public Ibu(DataAccess db) {
		super(db);
		// TODO Auto-generated constructor stub
	}	
	
	public Ibu(Parcel in) {
		super(in);
	}

	@Override
	public String[] getAttributes() {
		String[] attr = {"id","ktp","nama"};
		return attr;
	}
	
	public String[] getTags() {
		return getAttributes();
	}

	@Override
	public void fillFieldFromCursor(Cursor c) {
		this.setAttribute("id", c.getInt(0));
		this.setAttribute("ktp", c.getString(1));
		this.setAttribute("nama", c.getString(2));
	}

	@Override
	public Model fromCursor(Cursor c) {
		Ibu ibu = new Ibu(this.database);
		int g = c.getInt(0);
		ibu.setAttribute("id", String.valueOf(g));		
		ibu.setAttribute("ktp", c.getString(1));
		ibu.setAttribute("nama", c.getString(2));
		return ibu;
	}

	@Override
	public String getTableName() {
		return "ibu";
	}

	@Override
	public View getView(Context c) {
		// TODO Auto-generated method stub
		return getView(0,c,Model.EDIT);
	}
	
	public View getView(Context c,String mode) {
		// TODO Auto-generated method stub
		return getView(0,c,mode);
	}

	@Override
	public void fromView(View v) {
		String[] tags = this.getTags();
		EditText f = (EditText)v.findViewWithTag(tags[1]);
		this.setAttribute("ktp", f.getText().toString());
		EditText f2 = (EditText)v.findViewWithTag(tags[2]);
		this.setAttribute("nama", f2.getText().toString());
	}
	@Override
	public View getView(int accessLevel,Context c,String mode) {
		final Model model = this;
		if(accessLevel==0){
			View v = new View(c);
			ArrayList<View> childrens = new ArrayList<View>();
			final LinearLayout ll = new LinearLayout(c);
			ll.setOrientation(LinearLayout.VERTICAL);
			TextView ktpLabel = new TextView(c);
			ktpLabel.setText("No Ktp");
			ktpLabel.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			ktpLabel.setTextColor(Color.BLACK);
			ll.addView(ktpLabel);
			EditText ktpEdit = new EditText(c);
			ktpEdit.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			ktpEdit.setTag("ktp");
			ktpEdit.setTextColor(Color.BLACK);
			if(mode == Model.EDIT)
				ktpEdit.setText((String)getAttribute("ktp"));
			ll.addView(ktpEdit);
			TextView namaLabel = new TextView(c);
			namaLabel.setText("Nama");
			namaLabel.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			namaLabel.setTextColor(Color.BLACK);
			ll.addView(namaLabel);
			EditText nama = new EditText(c);
			nama.setTextColor(Color.BLACK);
			nama.setTag("nama");
			nama.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			if(mode == Model.EDIT)
				nama.setText((String)getAttribute("nama"));
			ll.addView(nama);
			Button b = new Button(c);
			b.setText("Save");
			b.setTextColor(Color.BLACK);
			b.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					model.fromView(ll);
					model.save();
				}
			});
			ll.addView(b);
			return ll;
		}
		return null;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public FlexibleModel createFromParcel(Parcel in) {
            return new Ibu(in); 
        }

        public FlexibleModel[] newArray(int size) {
            return new FlexibleModel[size];
        }
    };

	
}

