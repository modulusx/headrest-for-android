package com.mrkunkel.headrest.model;

public class SwitchItem extends ServiceItem {

	private boolean mState;

	public SwitchItem() {
		super();
	}

	public SwitchItem(String name, String path, boolean state) {
		super(name, path);
		mState = state;
	}

	public SwitchItem(int icon, String name, String path, boolean state) {
		super(icon, name, path);
		mState = state;
	}

	public int getIcon() {
		return mIcon;
	}

	public int getLayout() {
		return SWITCH_LAYOUT;
	}

	public boolean getState() {
		return mState;
	}

}