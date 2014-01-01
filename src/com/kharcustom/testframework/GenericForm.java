package com.kharcustom.testframework;

import com.khar.isframework.Model;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class GenericForm extends Activity {
	public final static String MODEL="com.kharcustom.testframework.GenericForm.model";
	Model model;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(getIntent().getParcelableExtra(MODEL)!=null){
			model = getIntent().getParcelableExtra(MODEL);
		}
		setContentView(R.layout.activity_generic_form);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.generic_form, menu);
		return true;
	}

}
