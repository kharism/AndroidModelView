package com.kharcustom.testframework;

import com.khar.isframework.DataAccess;
import com.khar.isframework.Model;
import com.khar.isframework.SqliteDataAccess;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;

public class DummyEditForm extends Activity {
	Model m;
	DataAccess dataAccess;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dataAccess = new SqliteDataAccess(getApplicationContext());
		
		m=getIntent().getParcelableExtra("model");
		m.setDataAccess(dataAccess);
		View v = m.getView(getApplicationContext());
		setContentView(v);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dummy_edit_form, menu);
		return true;
	}

}
