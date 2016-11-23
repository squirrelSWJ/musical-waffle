package com.example.sample;

import com.jongbean.database.DataBase;
import com.jongbean.utils.Utils;
import com.jongbean.view.MovieView;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

public class ActSub extends Activity {

	private MovieView movieview;
	private DataBase db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_sub);
		
		
	}


}
