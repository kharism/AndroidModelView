package com.khar.isframework.formbuilder;

import android.content.Context;
import android.graphics.Color;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

public class FormBuilder {
	private Context context;
	private ViewGroup curView;
	private LinearLayout ll;
	private Theme theme;	
	public FormBuilder(Context context){
		this.context = context;
		theme =  new BasicTheme();
		curView = new ScrollView(context);
		ll = new LinearLayout(context);
		ll.setOrientation(LinearLayout.VERTICAL);
		curView.addView(ll);
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
	public FormBuilder addButton(String buttonLabel,OnClickListener listener){
		Button b = new Button(context);
		b.setText(buttonLabel);
		b.setTextColor(theme.getTextColor());
		b.setOnClickListener(listener);
		ll.addView(b);
		return this;
	}
	public FormBuilder addTextField(String textLabel, String tag,String value,TextWatcher tw){
		TextView tv = new TextView(context);
		tv.setTextColor(theme.getTextColor());
		tv.setLayoutParams(new LayoutParams(theme.getLabelWidth(), theme.getLabelHeight()));
		tv.setVisibility(View.VISIBLE);
		tv.setText(textLabel);
		ll.addView(tv);
		EditText te = new EditText(context);
		if(value!=null){
			te.setText(value);
		}
		te.setVisibility(View.VISIBLE);
		te.setTextColor(theme.getTextColor());
		te.setLayoutParams(new LayoutParams(theme.getLabelWidth(), theme.getLabelHeight()));
		te.setTag(tag);
		if(tw!=null)
		te.addTextChangedListener(tw);
		ll.addView(te);
		return this;
	}
	public FormBuilder addTextField(String textLabel, String tag,String value){
		return this.addTextField(textLabel, tag, value,null);		
	}
	public FormBuilder addSpinner(String fieldName,String tag,SpinnerAdapter da){
		TextView tv = new TextView(context);
		tv.setTextColor(theme.getTextColor());
		tv.setLayoutParams(new LayoutParams(theme.getLabelWidth(), theme.getLabelHeight()));
		ll.addView(tv);
		Spinner s = new Spinner(context);
		s.setTag(tag);
		s.setLayoutParams(new LayoutParams(theme.getLabelWidth(), theme.getLabelHeight()));
		s.setAdapter(da);
		ll.addView(s);
		return this;
	}
	public FormBuilder addViewField(String textLabel, String val) {
		TextView tv = new TextView(context);
		tv.setTextColor(theme.getTextColor());
		tv.setLayoutParams(new LayoutParams(theme.getLabelWidth(), theme.getLabelHeight()));
		tv.setText(textLabel);
		ll.addView(tv);
		TextView tv2 = new TextView(context);
		tv2.setTextColor(theme.getTextColor());
		tv2.setLayoutParams(new LayoutParams(theme.getLabelWidth(), theme.getLabelHeight()));
		tv2.setText(val);
		tv2.setTextSize((float) 35.3);
		ll.addView(tv2);
		return this;
	}
}
