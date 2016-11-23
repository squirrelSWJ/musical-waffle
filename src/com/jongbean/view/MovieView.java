package com.jongbean.view;

import com.example.sample.R;
import com.jongbean.utils.Utils;

import android.app.Activity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class MovieView extends LinearLayout {

	private Activity act;
	private Utils utils;

	private LinearLayout view;
	
	public MovieView(Activity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
		
		act = activity;
		view = (LinearLayout) LayoutInflater.from(activity).inflate(R.layout.view_movie, null, false);

		this.addView(view,Utils.getParams());

		layoutInit();
	}

	private void layoutInit(){

		utils = new Utils(act);
	}
}
