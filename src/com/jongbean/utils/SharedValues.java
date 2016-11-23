package com.jongbean.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedValues {
	private Context mContext;
	private SharedPreferences pef = null; 

	public SharedValues(Context con){
		mContext = con;
		pef = mContext.getSharedPreferences(SharedValues.class.getPackage()+"."+SharedValues.class.getName(), Activity.MODE_PRIVATE);
	}
	
	public void setFirstEnable(boolean b){
		SharedPreferences.Editor editor = pef.edit();
		editor.putBoolean("TutorialFirst", b);
		editor.commit();
	}
	
	public boolean getFirstEnable(){
		return pef.getBoolean("TutorialFirst", false);
	}
	
}
