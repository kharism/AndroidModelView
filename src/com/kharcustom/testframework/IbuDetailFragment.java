package com.kharcustom.testframework;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.khar.isframework.DataAccess;
import com.khar.isframework.Model;
import com.khar.isframework.Query;
import com.khar.isframework.SqliteDataAccess;
import com.khar.isframework.models.Ibu;
import com.kharcustom.testframework.dummy.DummyContent;

/**
 * A fragment representing a single Ibu detail screen. This fragment is either
 * contained in a {@link IbuListActivity} in two-pane mode (on tablets) or a
 * {@link IbuDetailActivity} on handsets.
 */
public class IbuDetailFragment extends Fragment {
	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_ITEM_ID = "item_id";
	View rootView;

	private Ibu mItem;
	private DataAccess dataAccess;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public IbuDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments().containsKey(ARG_ITEM_ID)) {
			// Load the dummy content specified by the fragment
			// arguments. In a real-world scenario, use a Loader
			// to load content from a content provider.
			initView();
		}
	}
	private void initView(){
		String id = getArguments().getString(ARG_ITEM_ID);
		dataAccess = new SqliteDataAccess(getActivity().getApplicationContext());
		Ibu ibu = new Ibu(dataAccess);
		Query q = new Query();
		q.addWhere(" id="+id);
		List<Model> mod = ibu.findAll(q);
		mItem = (Ibu)mod.get(0);
	}
	@Override
	public void onResume() {
		initView();
		if (mItem != null) {
			((TextView) rootView.findViewById(R.id.ibu_detail))
					.setText((String)mItem.getAttribute("nama"));
			((TextView) rootView.findViewById(R.id.ibu_ktp))
			.setText((String)mItem.getAttribute("ktp"));
		}
		super.onResume();
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_ibu_detail,
				container, false);

		// Show the dummy content as text in a TextView.
		if (mItem != null) {
			((TextView) rootView.findViewById(R.id.ibu_detail))
					.setText((String)mItem.getAttribute("nama"));
			((TextView) rootView.findViewById(R.id.ibu_ktp))
			.setText((String)mItem.getAttribute("ktp"));
		}

		return rootView;
	}
}
