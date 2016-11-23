package com.sungwook.adapter;

import java.util.ArrayList;

import com.example.sample.R;
import com.jongbean.dao.MovieDao;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {

	Context mContext;
	ArrayList<MovieDao> item = new ArrayList<MovieDao>();
	LayoutInflater inflater;
	
	TextView rank,title;
	
	public CustomAdapter(Context context, ArrayList<MovieDao> content) {
		// TODO Auto-generated constructor stub
		
		mContext = context;
		item = content; 
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
	}
	
	@Override
	public int getCount() {//리스트 개수를 세아려서 가져옴
		// TODO Auto-generated method stub
		return item.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return item.get(position);
		
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertview, ViewGroup parent) {//한행한행 만들어주는 역할
		// TODO Auto-generated method stub
		
		if(null == convertview){
			convertview = inflater.inflate(R.layout.item_row, parent,false);
						
		}
		rank = (TextView) convertview.findViewById(R.id.rank);
		title = (TextView) convertview.findViewById(R.id.title);
		
		rank.setText(item.get(position).getRank());
		title.setText(item.get(position).getMovieNm());
		
		
		
		
		return convertview;
	}

}
