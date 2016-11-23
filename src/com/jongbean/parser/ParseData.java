package com.jongbean.parser;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.jongbean.utils.Utils;

public class ParseData {

	HashMap<String, Object> map = new HashMap<String, Object>();

	public HashMap<String, Object> getMovieParser(HashMap<String, Object> mapData){
		
		
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
				
				

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Utils.log("error", "JSONException");
		}

		return map;
	}

}
