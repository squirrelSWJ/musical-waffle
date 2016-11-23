package com.jongbean.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import com.jongbean.utils.Utils;

import android.util.Log;

public class Parser {

	private URL url;							// URL ��ü
	private HttpURLConnection huc = null; 		// httpurlconnection
	private BufferedReader bufReader = null;
	private PrintWriter prtWriter = null;
	private StringBuilder strBuilder;

	// ������ ��� ���� �ؽ���
	private HashMap<String, Object> map = new HashMap<String, Object>(); 

	/**
	 * Get����� �̿��ϱ� ���� URLConnection ����
	 * @param seturl �ش� URL
	 * @param query �Ķ����
	 */
	public void initGet(String seturl,String query){
		try {
			url = new URL(seturl+query);
			huc = (HttpURLConnection) url.openConnection();
			huc.setDefaultUseCaches(true);
			huc.setConnectTimeout(10000);
			huc.setReadTimeout(10000);
			huc.setDoInput(true);
			huc.setDoOutput(true);
			huc.setRequestMethod("GET");
			//huc.setRequestProperty("Context-Type", "application/x-www-form-urlencoded");
			huc.setRequestProperty("Accept", "application/json");
			huc.setRequestProperty("Content-type", "application/json");
		} catch ( MalformedURLException e1 ) {
			e1.printStackTrace();
		} catch ( ProtocolException e1 ) {
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//			Log.e("GetMsg", "URL open connection ERROR");
			e.printStackTrace();
		} catch (OutOfMemoryError e) {
			//			Log.e("TAG", "Out of Memeory!!!");
		} 
	}

	/**
	 * POST����� �̿��ϱ� ���� URLConnection ����
	 * @param seturl URL ����
	 */
	public void initPost(String seturl){
		try {
			url = new URL(seturl);
			huc = (HttpURLConnection) url.openConnection();
			huc.setDefaultUseCaches(true);
			huc.setConnectTimeout(10000);
			huc.setReadTimeout(10000);
			huc.setDoInput(true);
			huc.setDoOutput(true);
			huc.setRequestMethod("POST");
			//huc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			huc.setRequestProperty("Accept", "application/json");
			huc.setRequestProperty("Content-type", "application/json");
		} catch ( MalformedURLException e1 ) {
			e1.printStackTrace();
		} catch ( ProtocolException e1 ) {
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//			Log.e("PostMsg", "URL open connection ERROR");
			e.printStackTrace();
		} catch (OutOfMemoryError e) {
			//			Log.e("TAG", "Out of Memeory!!!");
		} 
	}

	/**
	 * GET����� �Ķ���� ������ UTF-8�� ���� �����ϴ� �κ�
	 * @param value �Ķ����
	 * @return ����� ������
	 */
	public String getGetSource(){
		strBuilder = new StringBuilder();
		String line = "";
		try {
			prtWriter = new PrintWriter(new OutputStreamWriter(huc.getOutputStream(), "utf-8"));
			prtWriter.write("");
			prtWriter.flush();

			bufReader = new BufferedReader(new InputStreamReader(huc.getInputStream(), "utf-8"));

			while((line = bufReader.readLine())!= null){
				strBuilder.append(line);
			}

			if(prtWriter == null){				
			}
			else{
				prtWriter.close();
				prtWriter = null;
			}
			try {
				if(bufReader == null){

				} else {
					bufReader.close();
					bufReader = null;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			//			Log.e("PostMsg", "EncodingSet ERROR");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//			Log.e("PostMsg", e.getMessage());
			e.printStackTrace();
		} catch (OutOfMemoryError e) {
			//			Log.e("TAG", "Out of Memeory!!!");
		} finally{
			huc.disconnect();
		}

		return strBuilder.toString();
	}

	/**
	 * POST��� �Ķ���� ������ UTF-8�� ���� �����ϴ� �κ�
	 * @param value �Ķ����
	 * @return ����� ������
	 */
	public String getPostSource(String query){
		strBuilder = new StringBuilder();
		String line = "";
		try {
			prtWriter = new PrintWriter(new OutputStreamWriter(huc.getOutputStream(), "utf-8"));
			prtWriter.write(query);
			prtWriter.flush();

			bufReader = new BufferedReader(new InputStreamReader(huc.getInputStream(), "utf-8"));

			while((line = bufReader.readLine())!= null){
				strBuilder.append(line);
			}

			if(prtWriter == null){				
			}
			else{
				prtWriter.close();
				prtWriter = null;
			}
			try {
				if(bufReader == null){

				} else {
					bufReader.close();
					bufReader = null;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			//			Log.e("PostMsg", "EncodingSet ERROR");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//			Log.e("PostMsg", "OutStream ERROR");
			e.printStackTrace();
		} catch (OutOfMemoryError e) {
			//			Log.e("TAG", "Out of Memeory!!!");
		} finally{
			huc.disconnect();
		}

		return strBuilder.toString();
	}

	public HashMap<String, Object> postPaser(String url, String query) {
		initPost(url);
		Utils.log("url", "url = " + url+query);
		String src = getPostSource(query);
		String resultStr = "";
		Utils.log("src", "src = " + src);

		try {

			JSONObject jsonObj = new JSONObject(src);

			Iterator keys = jsonObj.keys();
			while(keys.hasNext()){	
				String key = (String)keys.next();
				map.put(key, jsonObj.get(key));
				Utils.log("tag", "key = " + key + ", value = " + jsonObj.get(key));
			}

		} catch (JSONException e) {
			Log.d("JSONException", e.getMessage());
		}
		return map;

	}
	
	public HashMap<String, Object> getJson(String url_, String query){

		//		Log.e("URL", url_+query);
		Utils.log("url", "url = " + url_+query);
		HttpParams httpParameters = new BasicHttpParams();
		strBuilder = new StringBuilder();
		String line = null;
		BufferedReader rd = null;
		HttpGet httpget = new HttpGet(url_ + query);
		int timeoutConnection = 10000;
		HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
		int timeoutSocket = 10000;
		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
		DefaultHttpClient client = new DefaultHttpClient();
		client.setParams(httpParameters);

		try {
			HttpResponse response = client.execute(httpget);
			BufferedReader br = new BufferedReader(new 
					InputStreamReader(response.getEntity().getContent(),"utf-8"));
			for (;;) {
				line = br.readLine();
				if (line == null) break;
				strBuilder.append(line);
			}
			br.close();
		} 
		catch (Exception e) {;}
		try {
			//JSON String���� ���� JSONArray ��. [](���ȣ)

			JSONObject jsonObj = new JSONObject(strBuilder.toString());

			//			JSONObject jsonObj = resultjson.getJSONObject("data");
			Iterator keys = jsonObj.keys();
			while(keys.hasNext()){
				String key = (String)keys.next();
				map.put(key, jsonObj.get(key));
				Utils.log("tag", "key = " + key + ", value = " + jsonObj.get(key));
			}

		} catch (JSONException e) {
			//			Log.d("JSONException", e.getMessage());
		}
		return map;

	}

	public HashMap<String, Object> getPaser(String url, String query) {
		initGet(url,query);
		String src = getGetSource();
		String resultStr = "";
		Utils.log("url", "url = " + url+query);
		Utils.log("src", "src = " + src);

		try {
			//JSON String���� ���� JSONArray ��. [](���ȣ)

			JSONObject jsonObj = new JSONObject(src);

			//			JSONObject jsonObj = resultjson.getJSONObject("data");
			Iterator keys = jsonObj.keys();
			while(keys.hasNext()){
				String key = (String)keys.next();
				map.put(key, jsonObj.get(key));
				Utils.log("tag", "key = " + key + ", value = " + jsonObj.get(key));
			}

		} catch (JSONException e) {
			//			Log.d("JSONException", e.getMessage());
		}
		return map;

	}

}