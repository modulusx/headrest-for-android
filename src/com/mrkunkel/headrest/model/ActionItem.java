package com.mrkunkel.headrest.model;

public class ActionItem extends ServiceItem {

	public ActionItem() {
		super();
	}

	public ActionItem(String name, String path) {
		super(name, path);
	}

	public ActionItem(int icon, String name, String path) {
		super(icon, name, path);
	}

	public int getIcon() {
		return mIcon;
	}

	public int getLayout() {
		return ACTION_LAYOUT;
	}

}