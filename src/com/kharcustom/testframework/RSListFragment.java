package com.kharcustom.testframework;

import java.util.List;

import com.khar.isframework.DataAccess;
import com.khar.isframework.Model;
import com.khar.isframework.ModelAdapter;
import com.khar.isframework.ModelListFragment;
import com.khar.isframework.SqliteDataAccess;
import com.khar.isframework.models.Ibu;
import com.khar.isframework.models.RumahSakit;
import com.kharcustom.testframework.IbuListFragment.Callbacks;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class RSListFragment extends ModelListFragment {
	
	/**
	 * A dummy implementation of the {@link Callbacks} interface that does
	 * nothing. Used only when this fragment is not attached to an activity.
	 */
	private static Callbacks sDummyCallbacks = new Callbacks() {
		@Override
		public void onItemSelected(String id) {
		}
	};
	public RSListFragment() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		RumahSakit rs = new RumahSakit(dataAccess);
		List<Model> lists = rs.findAll();
		adapter = new RSAdapter(getActivity(), lists);
		setListAdapter(adapter);
	}
	@Override
	public void onResume() {
		RumahSakit ibu = new RumahSakit(dataAccess);
		List<Model> ibus = ibu.findAll();
		adapter.setItems(ibus);
		super.onResume();
	}
	
	
	private class RSAdapter extends ModelAdapter{

		public RSAdapter(Context c, int id, List<Model> objects) {
			super(c, id, objects);
			models = objects;
		}
		public RSAdapter(Context context, List<Model> objects) {
			super(context, R.layout.ibu_list_view, objects);
			models = objects;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.ibu_list_view, parent, false);
			TextView tv = (TextView)rowView.findViewById(R.id.nama_ibu);
			tv.setText((String)models.get(position).getAttribute("nama"));
			if(position==selected){
				rowView.setBackgroundColor(Color.GRAY);
			}
			return rowView;
		}
	}
}
