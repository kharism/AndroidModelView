package com.kharcustom.testframework;

import java.util.zip.Inflater;

import com.khar.isframework.DataAccess;
import com.khar.isframework.Model;
import com.khar.isframework.SqliteDataAccess;
import com.khar.isframework.models.Ibu;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class FormTester extends Activity {
	DataAccess dataAccess;
	Ibu ibu;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dataAccess = new SqliteDataAccess(getApplicationContext());
		ibu = new Ibu(dataAccess);
		Context c = getApplicationContext();
		View v = ibu.getView(c);		
		setContentView(ibu.getView(c, Model.CREATE));
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			Intent i = new Intent(getApplicationContext(), IbuListActivity.class);
			startActivity(i);
			return true;

		default:
			return true;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.form_tester, menu);
		return true;
	}
	
}
