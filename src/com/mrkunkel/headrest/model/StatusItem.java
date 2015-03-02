package com.mrkunkel.headrest.model;

public class StatusItem extends ServiceItem {

	private String mStatus;

	public StatusItem() {
		super();
	}

	public StatusItem(String name, String path, String status) {
		super(name, path);
		mStatus = status;
	}

	public StatusItem(int icon, String name, String path, String status) {
		super(icon, name, path);
		mStatus = status;
	}

	public int getIcon() {
		return mIcon;
	}

	public int getLayout() {
		return STATUS_LAYOUT;
	}

	public String getStatus() {
		return mStatus;
	}

}