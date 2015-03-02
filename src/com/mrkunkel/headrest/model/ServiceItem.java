package com.mrkunkel.headrest.model;

import com.mrkunkel.headrest.R;

public class ServiceItem {
	public final static int SERVICE_LAYOUT = R.layout.service_item;
	public final static int SWITCH_LAYOUT = R.layout.switch_item;
	public final static int SLIDER_LAYOUT = R.layout.slider_item;
	public final static int SELECT_LAYOUT = R.layout.select_item;
	public final static int STATUS_LAYOUT = R.layout.status_item;
	public final static int ACTION_LAYOUT = R.layout.action_item;
	public final static int WEBURL_LAYOUT = R.layout.weburl_item;
	
	protected int mIcon;
	protected String mName;
	protected String mPath;
	
	public ServiceItem() {
	}

	public ServiceItem(String name, String path) {
		mIcon = R.drawable.ic_home;
		mName = name;
		mPath = path;
	}

	public ServiceItem(int icon, String name, String path) {
		mIcon = icon;
		mName = name;
		mPath = path;
	}
	
	public int getIcon() {
		return mIcon;
	}
	
	public int getLayout() {
		return SERVICE_LAYOUT;
	}

	public String getName() {
		return mName;
	}

	public String getPath() {
		return mPath;
	}

	protected int constrain(int value, int min, int max) {
		if (value < min)
			return min;
		else if (value > max)
			return max;
		else
			return value;
	}
}
