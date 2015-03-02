package com.mrkunkel.headrest.model;

public class SliderItem extends ServiceItem {

	private int mValue;
	private int mMin;
	private int mMax;

	public SliderItem() {
		super();
	}

	public SliderItem(String name, String path, int min, int max, int value) {
		super(name, path);
		mMin = min;
		mMax = max;
		mValue = value;
	}

	public SliderItem(int icon, String name, String path, int min, int max,
			int value) {
		super(icon, name, path);
		mMin = min;
		mMax = max;
		mValue = value;
	}

	public int getLayout() {
		return SLIDER_LAYOUT;
	}

	public float getValue() {
		return mValue;
	}
	
	public int getMin() {
		return mMin;
	}
	
	public int getMax() {
		return mMax;
	}
	
	public void setValue(int value) {
		mValue = constrain(value, mMin, mMax);
	}

}