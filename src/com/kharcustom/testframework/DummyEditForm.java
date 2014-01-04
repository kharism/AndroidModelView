package com.kharcustom.testframework;

import com.khar.isframework.DataAccess;
import com.khar.isframework.FlexibleModel;
import com.khar.isframework.Model;
import com.khar.isframework.SqliteDataAccess;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class DummyEditForm extends Activity {
	FlexibleModel m;
	DataAccess dataAccess;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dataAccess = new SqliteDataAccess(getApplicationContext());
		Intent i =getIntent(); 
		if(i.hasExtra("model"))
		{
			m=(FlexibleModel)i.getParcelableExtra("model");
			m.setDataAccess(dataAccess);
			setTitle(m.getTableName());
			View v = m.getView(this);
			setContentView(v);
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dummy_edit_form, menu);
		return true;
	}

}
