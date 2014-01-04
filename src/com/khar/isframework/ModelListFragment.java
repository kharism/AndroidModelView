package com.khar.isframework;

import com.khar.isframework.models.ibu.IbuListFragment.Callbacks;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

public class ModelListFragment extends ListFragment{
	protected Callbacks mCallbacks = sDummyCallbacks;
	protected ModelAdapter adapter;
	protected DataAccess dataAccess;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		dataAccess = new SqliteDataAccess(getActivity().getApplicationContext());
	}
	public void refresh(){
		onResume();
	}
	/**
	 * A dummy implementation of the {@link Callbacks} interface that does
	 * nothing. Used only when this fragment is not attached to an activity.
	 */
	private static Callbacks sDummyCallbacks = new Callbacks() {
		@Override
		public void onItemSelected(String id) {
		}
	};
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		// Activities containing this fragment must implement its callbacks.
		if (!(activity instanceof Callbacks)) {
			throw new IllegalStateException(
					"Activity must implement fragment's callbacks.");
		}
		mCallbacks = (Callbacks) activity;
	}
	/**
	 * try superclassing this method
	 */
	@Override
	public void onDetach() {
		super.onDetach();

		// Reset the active callbacks interface to the dummy implementation.
		mCallbacks = sDummyCallbacks;
	}
	/**
	 * try superclassing this method
	 */
	@Override
	public void onListItemClick(ListView listView, View view, int position,
			long id) {
		super.onListItemClick(listView, view, position, id);

		// Notify the active callbacks interface (the activity, if the
		// fragment is attached to one) that an item has been selected.
		Model m = adapter.getItem(position);
		mCallbacks.onItemSelected((String)m.getAttribute("id"));
	}
}
