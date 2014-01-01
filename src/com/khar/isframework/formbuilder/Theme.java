package com.khar.isframework.formbuilder;

import android.content.Context;
import android.view.View;

public interface Theme {
	public int getTextColor();
	public View getRootView(Context c);
	public int getBackgroundColor();
	public int getLabelWidth();
	public float getLabelWeight();
	public int getLabelHeight();
	public float getElementWeight();
	public int getElementWidth();
}
