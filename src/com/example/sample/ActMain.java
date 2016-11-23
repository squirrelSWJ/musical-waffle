package com.example.sample;

import java.util.ArrayList;
import java.util.HashMap;

import com.jongbean.dao.MovieDao;
import com.jongbean.parser.JsonThread;
import com.jongbean.parser.ParseData;
import com.sungwook.adapter.CustomAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;
import android.widget.TextView;

public class ActMain extends Activity {

	JsonThread jt;
	TextView txt,txt1,txt2;
	ListView listview;
	
	ParseData parsedata;
	CustomAdapter mCustomAdapter;
	ArrayList<MovieDao> item = new ArrayList<MovieDao>();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_main);

		txt = (TextView) findViewById(R.id.txt);
		txt1 = (TextView) findViewById(R.id.txt1);
		txt2 = (TextView) findViewById(R.id.txt2);
		listview = (ListView) findViewById(R.id.listview);
		
		
		
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
			
			parsedata = new ParseData();
			map = parsedata.getMovieParser(map);
			
			txt.setText((String)map.get("boxofficeType"));
			txt1.setText("조회기간 +"+(String)map.get("showRange"));
			txt2.setText((String)map.get("yearWeekTime").toString().substring(0,4) + "년 " +
			(String)map.get("yearWeekTime").toString().substring(4,6) + "주차");
			
			item = (ArrayList<MovieDao>)map.get("BoxOffice");
			
			mCustomAdapter = new CustomAdapter(ActMain.this, item);
			listview.setAdapter(mCustomAdapter);
		}

	};

}
