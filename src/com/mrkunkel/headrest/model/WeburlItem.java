package com.mrkunkel.headrest.model;

public class WeburlItem extends ServiceItem {

	public WeburlItem() {
		super();
	}

	public WeburlItem(String name, String path) {
		super(name, path);
	}

	public WeburlItem(int icon, String name, String path) {
		super(icon, name, path);
	}

	public int getIcon() {
		return mIcon;
	}

	public int getLayout() {
		return WEBURL_LAYOUT;
	}

}