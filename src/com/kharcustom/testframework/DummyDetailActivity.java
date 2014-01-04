package com.kharcustom.testframework;

import com.khar.isframework.DataAccess;
import com.khar.isframework.Model;
import com.khar.isframework.SqliteDataAccess;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class DummyDetailActivity extends Activity {
	Model m;
	DataAccess dataAccess;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dataAccess = new SqliteDataAccess(getApplicationContext());
		
		m=getIntent().getParcelableExtra("model");
		m.setDataAccess(dataAccess);
		m.setScenario(Model.VIEW);
		View v = m.getView(this, Model.VIEW);
		setContentView(v);
	}
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch(item.getItemId()){
		case R.id.action_edit:
			m.setScenario(Model.EDIT);
			Intent i = new Intent(this, DummyEditForm.class);
			i.putExtra("model", m);
			startActivity(i);
			return true;
		case R.id.action_delete:
			m.delete();
			finish();
			return true;
		}
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dummy_detail, menu);
		return true;
	}

}
