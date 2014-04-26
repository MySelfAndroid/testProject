/**
 * 
 */
package com.easyway.apps.items;

public class Item {

	int mIcon;
	int mSpans;
	String mTitle;
	String mDescription;
	boolean mIsEnable;

	public Item(int icon, int spans, String title, String description,boolean isEnable) {

		mIcon = icon;
		mSpans = spans;
		mTitle = title;
		mDescription = description;
		mIsEnable = isEnable;

	}

	public int getSpans() {
		return mSpans;
	}

	public String getDescription() {
		return mDescription;
	}

	public int getIcon() {
		return mIcon;
	}

	public String getTitle() {
		return mTitle;
	}
	
	public boolean isEnable(){
		return mIsEnable;
	}
}
