package com.kharcustom.testframework;

import com.khar.isframework.DataAccess;
import com.khar.isframework.Model;
import com.khar.isframework.SqliteDataAccess;
import com.khar.isframework.models.Ibu;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * An activity representing a list of Ibu-ibu. This activity has different
 * presentations for handset and tablet-size devices. On handsets, the activity
 * presents a list of items, which when touched, lead to a
 * {@link IbuDetailActivity} representing item details. On tablets, the activity
 * presents the list of items and item details side-by-side using two vertical
 * panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link IbuListFragment} and the item details (if present) is a
 * {@link IbuDetailFragment}.
 * <p>
 * This activity also implements the required {@link IbuListFragment.Callbacks}
 * interface to listen for item selections.
 */
@SuppressLint("NewApi")
public class IbuListActivity extends FragmentActivity implements
		IbuListFragment.Callbacks {

	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 * device.
	 */
	private boolean mTwoPane;
	private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private DataAccess da;
    private ActionBarDrawerToggle mDrawerToggle;
    private ModuleMenuHandler currentModule;
    private Menu menu;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ibu_list);
		da = new SqliteDataAccess(getApplicationContext());
		currentModule = new IbuModuleHandler();
		if (findViewById(R.id.ibu_detail_container) != null) {
			// The detail container view will be present only in the
			// large-screen layouts (res/values-large and
			// res/values-sw600dp). If this view is present, then the
			// activity should be in two-pane mode.
			mTwoPane = true;

			// In two-pane mode, list items should be given the
			// 'activated' state when touched.
			((IbuListFragment) getSupportFragmentManager().findFragmentById(
					R.id.ibu_list)).setActivateOnItemClick(true);
		}
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        String[] mPlanetTitles = {"Ibu","Rumah Sakit"};
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mPlanetTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_launcher,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
                ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle("SIMaternal");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle("SIMaternal Menu");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
	}
	
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
            mDrawerLayout.closeDrawers();
        }
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.generic_menu, menu);
		this.menu = menu;
		return true;
		
	}
	public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        //menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }
	/**
	 * Draer Menu handler
	 * @param position
	 */
	private void selectItem(int position){
		if(position ==0){
			Fragment f = new IbuListFragment();
			getSupportFragmentManager().beginTransaction().replace(R.id.ibu_list, f).commit();
			currentModule = new IbuModuleHandler();
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		currentModule.resume();
	}
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
		switch(item.getItemId()){
		case R.id.action_tambah_data:
			currentModule.addItem();
			return true;
		}
		return super.onMenuItemSelected(featureId, item);
	}
	public class BaseModuleHandler implements ModuleMenuHandler{
		protected Class detilActivity;
		protected Fragment detilFragment;
		protected String intentKey;
		@Override
		public void selectItem(int selection) {
			String id = String.valueOf(selection);
			if (mTwoPane) {
				// In two-pane mode, show the detail view in this activity by
				// adding or replacing the detail fragment using a
				// fragment transaction.
				Bundle arguments = new Bundle();
				arguments.putString(IbuDetailFragment.ARG_ITEM_ID, id);
				detilFragment.setArguments(arguments);
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.ibu_detail_container, detilFragment).commit();

			} else {
				// In single-pane mode, simply start the detail activity
				// for the selected item ID.
				Intent detailIntent = new Intent(IbuListActivity.this, IbuDetailActivity.class);
				detailIntent.putExtra(intentKey, id);
				startActivity(detailIntent);
			}
		}

		@Override
		public void resume() {
			getFragmentManager().findFragmentById(R.id.ibu_list);
		}

		@Override
		public void addItem() {
			
		}

		@Override
		public void editItem() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void deleteItem() {
			// TODO Auto-generated method stub
			
		}
		
	}
	public class IbuModuleHandler extends BaseModuleHandler{
		public IbuModuleHandler() {
			detilActivity = IbuDetailActivity.class;
			detilFragment = new IbuDetailFragment();
			intentKey = IbuDetailFragment.ARG_ITEM_ID;
		}
	}
	/**
	 * Callback method from {@link IbuListFragment.Callbacks} indicating that
	 * the item with the given ID was selected.
	 */
	@Override
	public void onItemSelected(String id) {
		currentModule.selectItem(Integer.parseInt(id));
	}
	
}
