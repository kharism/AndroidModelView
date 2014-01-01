package com.khar.isframework;

import java.util.ArrayList;
import java.util.List;

import com.kharcustom.testframework.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class ModelAdapter extends ArrayAdapter<Model> {
	protected List<Model> models;
	protected Context context;
	protected int selected;
	public ModelAdapter(Context c,int id,List<Model> objects) {
		super(c, id, objects);
		models = new ArrayList<Model>();
		this.context = c;
		selected = -1;
	}	
	public void setItems(List<Model> models){
		this.models = models;
		notifyDataSetChanged();
	}
	@Override
	public int getCount() {
		return models.size();
	}
	@Override
	public Model getItem(int position) {
		// TODO Auto-generated method stub
		return models.get(position);
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return super.getView(position, convertView, parent);
	}
}
