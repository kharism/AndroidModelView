package com.khar.isframework.formbuilder;

import android.content.Context;
import android.graphics.Color;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

public class FormBuilder {
	private Context context;
	private ViewGroup curView;
	private Theme theme;	
	public FormBuilder(Context context){
		this.context = context;
		theme =  new BasicTheme();
		curView = new LinearLayout(context);
	}
	public FormBuilder(Context context,Theme theme){
		this.context = context;
		this.theme =  theme;
		curView = new LinearLayout(context);
	}
	public FormBuilder(Context context,ViewGroup baseView){
		this.context = context;
		this.curView = baseView;
	}
	public View getView(){
		return curView;
	}
	public FormBuilder addTextField(String fieldName, String tag,String value,TextWatcher tw){
		TextView tv = new TextView(context);
		tv.setTextColor(theme.getTextColor());
		tv.setLayoutParams(new LayoutParams(theme.getLabelWidth(), theme.getLabelHeight()));
		curView.addView(tv);
		EditText te = new EditText(context);
		if(value!=null){
			te.setText(value);
		}
		te.setTextColor(theme.getTextColor());
		te.setLayoutParams(new LayoutParams(theme.getLabelWidth(), theme.getLabelHeight()));
		te.setTag(tag);
		if(tw!=null)
		te.addTextChangedListener(tw);
		curView.addView(te);
		return this;
	}
	public FormBuilder addTextField(String fieldName, String tag,String value){
		return this.addTextField(fieldName, tag, value,null);		
	}
	public FormBuilder addSpinner(String fieldName,String tag,SpinnerAdapter da){
		TextView tv = new TextView(context);
		tv.setTextColor(theme.getTextColor());
		tv.setLayoutParams(new LayoutParams(theme.getLabelWidth(), theme.getLabelHeight()));
		curView.addView(tv);
		Spinner s = new Spinner(context);
		s.setTag(tag);
		s.setLayoutParams(new LayoutParams(theme.getLabelWidth(), theme.getLabelHeight()));
		s.setAdapter(da);
		curView.addView(s);
		return this;
	}
}
