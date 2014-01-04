package com.kharcustom.testframework;

import java.util.List;

import com.khar.isframework.DataAccess;
import com.khar.isframework.Model;
import com.khar.isframework.Query;
import com.khar.isframework.SqliteDataAccess;
import com.khar.isframework.models.ibu.Ibu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;

/**
 * An activity representing a single Ibu detail screen. This activity is only
 * used on handset devices. On tablet-size devices, item details are presented
 * side-by-side with a list of items in a {@link IbuListActivity}.
 * <p>
 * This activity is mostly just a 'shell' activity containing nothing more than
 * a {@link IbuDetailFragment}.
 */
public class IbuDetailActivity extends FragmentActivity {
	DataAccess dataAccess;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ibu_detail);
		dataAccess = new SqliteDataAccess(getApplicationContext());
		
		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);

		// savedInstanceState is non-null when there is fragment state
		// saved from previous configurations of this activity
		// (e.g. when rotating the screen from portrait to landscape).
		// In this case, the fragment will automatically be re-added
		// to its container so we don't need to manually add it.
		// For more information, see the Fragments API guide at:
		//
		// http://developer.android.com/guide/components/fragments.html
		//
		if (savedInstanceState == null) {
			// Create the detail fragment and add it to the activity
			// using a fragment transaction.
			Bundle arguments = new Bundle();
			arguments.putString(IbuDetailFragment.ARG_ITEM_ID, getIntent()
					.getStringExtra(IbuDetailFragment.ARG_ITEM_ID));
			IbuDetailFragment fragment = new IbuDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.add(R.id.ibu_detail_container, fragment).commit();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.detil_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpTo(this, new Intent(this, IbuListActivity.class));
			return true;
		case R.id.item_edit:
			Ibu ibu = new Ibu(dataAccess);
			Query q = new Query();
			q.addWhere("id="+getIntent().getStringExtra(IbuDetailFragment.ARG_ITEM_ID));
			List<Model> m = ibu.findAll(q);
			Intent i = new Intent(getApplicationContext(), DummyEditForm.class);
			i.putExtra("model",m.get(0));
			startActivity(i);
			return true;
		case R.id.item_delete:
			Ibu ibu2 = new Ibu(dataAccess);
			ibu2.deleteByPk(Integer.parseInt(getIntent().getStringExtra(IbuDetailFragment.ARG_ITEM_ID)));
			finish();
			return true;	
		}
		return super.onOptionsItemSelected(item);
	}
}
