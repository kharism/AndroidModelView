package com.kharcustom.testframework;

import java.util.List;

import com.khar.isframework.DataAccess;
import com.khar.isframework.Model;
import com.khar.isframework.ModelListFragment;
import com.khar.isframework.Query;
import com.khar.isframework.SqliteDataAccess;
import com.khar.isframework.models.ibu.Ibu;
import com.khar.isframework.models.ibu.IbuListFragment;
import com.khar.isframework.models.rs.RSListFragment;
import com.khar.isframework.models.rs.RumahSakit;

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
	private static final String DETIL_FRAGMENT_TAG="ooo";
	private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private DataAccess da;
    private ActionBarDrawerToggle mDrawerToggle;
    private ModuleMenuHandler currentModule;
    private Menu menu;
    private int selectedItem;
    private ModelListFragment currListFragment;
    private Fragment currDetilFragment;
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
			/*((IbuListFragment) getSupportFragmentManager().findFragmentById(
					R.id.ibu_list)).setActivateOnItemClick(true);*/
						
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
		if(mTwoPane){
			menu.findItem(R.id.action_edit).setVisible(true);
			menu.findItem(R.id.action_delete).setVisible(true);
		}
		return true;		
	}
	
	public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        //menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }
	/**
	 * Drawer Menu handler
	 * @param position
	 */
	private void selectItem(int position){
		if(position ==0){
			Fragment f = new IbuListFragment();
			currListFragment = (ModelListFragment) f;
			getSupportFragmentManager().beginTransaction().replace(R.id.ibu_list, f).commit();
			currentModule = new IbuModuleHandler();
		}
		else if(position==1){
			Fragment f = new RSListFragment();
			currListFragment = (ModelListFragment) f;
			getSupportFragmentManager().beginTransaction().replace(R.id.ibu_list, f).commit();
			currentModule = new RSModuleHandler();
		}
		Fragment f = getSupportFragmentManager().findFragmentByTag(DETIL_FRAGMENT_TAG);
		//getSupportFragmentManager().beginTransaction().replace(R.id.ibu_detail_container, new EmptyDetilFragment()).commit();
		if(f!=null)
		getSupportFragmentManager().beginTransaction().remove(f).commit();
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
		case R.id.action_delete:
			currentModule.deleteItem();
			currListFragment.refresh();
			return true;
		case R.id.action_edit:
			currentModule.editItem();
			return true;
		}
		return super.onMenuItemSelected(featureId, item);
	}
	public class BaseModuleHandler implements ModuleMenuHandler{
		protected Class detilActivity;
		protected Fragment detilFragment;
		protected String intentKey;
		protected Model model;
		@Override
		public void selectItem(int selection) {
			selectedItem = selection;
			String id = String.valueOf(selection);
			if (mTwoPane) {
				// In two-pane mode, show the detail view in this activity by
				// adding or replacing the detail fragment using a
				// fragment transaction.
				currDetilFragment = detilFragment;
				Bundle arguments = new Bundle();
				Query q = new Query();
				q.addWhere("id="+id);
				List<Model> models = model.findAll(q);
				arguments.putParcelable(intentKey, models.get(0));
				detilFragment.setArguments(arguments);
				
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.ibu_detail_container, detilFragment,DETIL_FRAGMENT_TAG).commit();
				detilFragment = generateDetailFragment();
			} else {
				// In single-pane mode, simply start the detail activity
				// for the selected item ID.
				Intent detailIntent = new Intent(IbuListActivity.this, detilActivity);
				Query q = new Query();
				q.addWhere("id="+id);
				Model model2 = model.findAll(q).get(0);
				detailIntent.putExtra("model", model2);
				startActivity(detailIntent);
			}
		}

		@Override
		public void resume() {
			getFragmentManager().findFragmentById(R.id.ibu_list);
		}
		
		protected Fragment generateDetailFragment(){
			return null;
		}

		@Override
		public void addItem() {
				Intent i = new Intent(getApplicationContext(), DummyEditForm.class);
				i.putExtra("model", model);
				startActivity(i);			
		}

		@Override
		public void editItem() {
			Query q = new Query();
			q.addWhere("id="+String.valueOf(selectedItem));
			Model m = model.findAll(q).get(0);
			Intent i = new Intent(IbuListActivity.this, DummyEditForm.class);
			m.setScenario(Model.EDIT);
			i.putExtra("model", m);
			startActivity(i);
		}

		@Override
		public void deleteItem() {
			Query q = new Query();
			q.addWhere("id="+String.valueOf(selectedItem));
			Model m = model.findAll(q).get(0);
			m.delete();
		}
		
	}
	public class IbuModuleHandler extends BaseModuleHandler{
		public IbuModuleHandler() {
			detilActivity = DummyDetailActivity.class;
			detilFragment = new DummyDetailFragment();
			intentKey = DummyDetailFragment.MODEL;
			model = new Ibu(da);
		}
		@Override
		protected Fragment generateDetailFragment() {
			// TODO Auto-generated method stub
			return new DummyDetailFragment();
		}
	}
	public class RSModuleHandler extends BaseModuleHandler{
		public RSModuleHandler() {
			detilActivity = DummyDetailActivity.class;
			detilFragment = new DummyDetailFragment();
			intentKey = DummyDetailFragment.MODEL;
			model = new RumahSakit(da);
		}
		@Override
		protected Fragment generateDetailFragment() {
			// TODO Auto-generated method stub
			return new DummyDetailFragment();
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
