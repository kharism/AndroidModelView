package com.khar.isframework.formbuilder;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;

public class BasicTheme implements Theme{
	public int getTextColor(){
		return Color.BLACK;
	}

	@Override
	public View getRootView(Context c) {
		return new LinearLayout(c);
	}

	@Override
	public int getBackgroundColor() {
		return Color.WHITE;
	}

	@Override
	public int getLabelWidth() {
		return LayoutParams.FILL_PARENT;
	}

	@Override
	public float getLabelWeight() {
		return (float) 0.5;
	}

	@Override
	public int getLabelHeight() {
		return LayoutParams.WRAP_CONTENT;
	}

	@Override
	public float getElementWeight() {
		return (float) 0.5;
	}

	@Override
	public int getElementWidth() {
		return LayoutParams.FILL_PARENT;
	}

}
