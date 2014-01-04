package com.kharcustom.testframework;

import com.khar.isframework.DataAccess;
import com.khar.isframework.FlexibleModel;
import com.khar.isframework.Model;
import com.khar.isframework.SqliteDataAccess;
import com.khar.isframework.models.ibu.Ibu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DummyDetailFragment extends Fragment {
	public static final String MODEL = "item_id";
	View rootView;

	private FlexibleModel mItem;
	private DataAccess dataAccess;
	public DummyDetailFragment(){
		
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mItem = getArguments().getParcelable(MODEL);
		mItem.setScenario(Model.VIEW);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return mItem.getView(this.getActivity());
	}
}
