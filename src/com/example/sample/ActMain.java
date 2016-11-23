package com.example.sample;

import java.util.HashMap;

import org.json.JSONObject;

import com.jongbean.parser.JsonThread;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

public class ActMain extends Activity {

	JsonThread jt;
	TextView txt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_main);

		txt = (TextView) findViewById(R.id.txt);
		
		jt = new JsonThread(mHandler, 0);
		jt.setDaemon(true);
		jt.setQuery("http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchWeeklyBoxOfficeList.json?"
				, "key=3b0b0415669c9b63537c9a6df1971f8d&targetDt=20161102&weekGb=0");
		jt.start();


	}

	private Handler mHandler = new Handler()
	{

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			HashMap<String, Object> map = new HashMap<String, Object>();
			map = (HashMap<String, Object>) msg.obj;
			
			String str = (String) map.get("boxOfficeResult").toString();
			
			txt.setText(str);
		}

	};

}
