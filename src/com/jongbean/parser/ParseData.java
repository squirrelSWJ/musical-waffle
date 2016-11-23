package com.jongbean.parser;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.jongbean.dao.MovieDao;
import com.jongbean.utils.Utils;

public class ParseData {

	HashMap<String, Object> map = new HashMap<String, Object>();

	public HashMap<String, Object> getMovieParser(HashMap<String, Object> mapData){
		ArrayList<MovieDao> item = new ArrayList<MovieDao>();
		
		try {
			JSONObject jobj = (JSONObject) mapData.get("boxOfficeResult");
			if(!Utils.nullPoint(jobj)){
				if(!jobj.isNull("boxofficeType")){
					map.put("boxofficeType", jobj.getString("boxofficeType"));
				}
				if(!jobj.isNull("showRange")){
					map.put("showRange", jobj.getString("showRange"));
				}
				if(!jobj.isNull("yearWeekTime")){
					map.put("yearWeekTime", jobj.getString("yearWeekTime"));
				}
				if(!jobj.isNull("weeklyBoxOfficeList")){
					JSONArray jarr = jobj.getJSONArray("weeklyBoxOfficeList");
					
					for(int i = 0 ; i < jarr.length(); i++){
												
						MovieDao content = new MovieDao();
						
						JSONObject obj = jarr.getJSONObject(i);
						
						content.setRnum(obj.getString("rnum"));
						content.setRank(obj.getString("rank"));
						content.setRankInten(obj.getString("rankInten"));
						content.setRankOldAndNew(obj.getString("rankOldAndNew"));
						content.setMovieCd(obj.getString("movieCd"));
						content.setMovieNm(obj.getString("movieNm"));
						content.setOpenDt(obj.getString("openDt"));
						content.setSalesAmt(obj.getString("salesAmt"));
						content.setSalesShare(obj.getString("salesShare"));
						
						
						item.add(content);
					}
					
					map.put("BoxOffice",item);
				}
				
				

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Utils.log("error", "JSONException");
		}

		return map;
	}

}
