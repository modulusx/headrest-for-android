package com.mrkunkel.headrest.model;

import java.util.List;

public class SelectItem extends ServiceItem {

	private List<String> mOptions;

	public SelectItem() {
		super();
	}

	public SelectItem(String name, String path, List<String> options) {
		super(name, path);
		mOptions = options;
	}

	public SelectItem(int icon, String name, String path, List<String> options) {
		super(icon, name, path);
		mOptions = options;
	}

	public int getIcon() {
		return mIcon;
	}

	public int getLayout() {
		return SELECT_LAYOUT;
	}

	public List<String> getOptions() {
		return mOptions;
	}

}