package com.jongbean.utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.Policy;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.sample.ActMain;
import com.example.sample.ActSub;
import com.example.sample.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager.BadTokenException;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class Utils {

	public static final String TAG = "Sample";
	private static final boolean DEBUG = true;
	//	private static final String Store = "TSTORE";
	private static final String Store = "PLAYSTORE";
	
	private static final String url = "http://www.kobis.or.kr";
	
	public static String keyword = "";
	public static String keyCode = "3b0b0415669c9b63537c9a6df1971f8d";
	
	private static Context mContext;
	private static Typeface mTypeface;

	public static void log(boolean showFlag, String tag, String msg){
		if(showFlag) Log.e(tag, msg);
	}

	public static void log(String tag, String msg){
		if(DEBUG) Log.e(tag, msg);
	}
	
	public static String getUrl()
	{
		return url;
	}

	public static String getKeyCode()
	{
		return keyCode;
	}
	
	public static String getCurrentAppVersion(Context context){
		String version = "";

		try {
			final PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			version = info.versionName;
		} catch (NameNotFoundException e) {
			log(TAG, "cannot found current package name");
			e.printStackTrace();
			return null;
		}

		return version;
	}

	
	public static SharedPreferences getSharedPref(Context context, String name){
		SharedPreferences pref = context.getSharedPreferences(name, 0);
		return pref;
	}

	public static int getIDFinder(Context mContext,String str){
		int layoutResId = mContext.getResources().getIdentifier(str, "id", mContext.getPackageName());
		return layoutResId;
	}

	public static boolean isPackageInstalled(Context context, String pkgName){
		PackageManager pm = context.getPackageManager();

		try
		{
			pm.getApplicationInfo(pkgName, PackageManager.GET_META_DATA);
		}
		catch(NameNotFoundException e)
		{
			return false;
		}

		return true;
	}

	public static String getMapValues(HashMap<String, String> map){
		String valueString = "";

		Iterator values = map.values().iterator();

		while(values.hasNext()){
			String value = (String)values.next();
			if(valueString.equals("")) valueString += value;
			else valueString += ("," + value);
		}

		return valueString;
	}

	public static HashMap<String, Object> getDataMap(JSONObject obj){
		HashMap<String, Object> map = new HashMap<String, Object>();
		Iterator keys = obj.keys();

		while(keys.hasNext()){
			String key = (String)keys.next();
			try {
				map.put(key, obj.get(key));
			} catch (JSONException e) {
				e.printStackTrace();
				log(TAG, "getDataMap : not found object");
				map = null;
			}
		}

		return map;
	}

	public static HashMap<String, Object> getDataMap(String str){
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			JSONObject obj = new JSONObject(str);
			Iterator keys = obj.keys();
			while (keys.hasNext()) {
				String key = (String) keys.next();
				map.put(key, obj.get(key));
			}

		} catch (JSONException e) {
			e.printStackTrace();
			log(TAG, "getDataMap : not found object");
			map = null;
		}

		return map;
	}

	public static AlertDialog.Builder initDialog(Context context, String title, String msg){
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setCancelable(false);
		dialog.setTitle(title);
		dialog.setMessage(msg);
		return dialog;
	}

	public static Toast initEmptyToast(Context context){
		Toast toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
		return toast;
	}

	public static String addParameter(String param, String key, String value){
		String str = param;

		if(param == null || param.equals("")) str = key + "=" + value;
		else str += "&" + key + "=" + value;

		return str;
	}

	public static String getRegDate(long millis){
		String date = "";

		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(millis);

		date = cal.get(Calendar.YEAR) + "." + 
				Utils.getZeroSumString(cal.get(Calendar.MONTH) + 1) + "." + 
				Utils.getZeroSumString(cal.get(Calendar.DAY_OF_MONTH)); 

		return date;
	}

	public static String getRegTime(long millis){
		String time = "";

		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(millis);

		time = Utils.getZeroSumString(cal.get(Calendar.HOUR_OF_DAY)) + ":" +
				Utils.getZeroSumString(cal.get(Calendar.MINUTE));

		return time;
	}

	public static String getRegDateTime(long millis){
		String date = "", time = "";

		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(millis);

		date = cal.get(Calendar.YEAR) + "." + 
				getZeroSumString(cal.get(Calendar.MONTH) + 1) + "." + 
				getZeroSumString(cal.get(Calendar.DAY_OF_MONTH)); 
		time = getZeroSumString(cal.get(Calendar.HOUR_OF_DAY)) + ":" +
				getZeroSumString(cal.get(Calendar.MINUTE));
		return date + " " + time;
	}

	public static String getDBDate(long millis){
		String date = "";

		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(millis);

		date = cal.get(Calendar.YEAR) + "-" + 
				Utils.getZeroSumString(cal.get(Calendar.MONTH) + 1) + "-" + 
				Utils.getZeroSumString(cal.get(Calendar.DAY_OF_MONTH)); 

		return date;
	}

	public static String getFilenameInURL(String url){
		String filename = "";

		String[] parts = url.split("/");
		filename = parts[parts.length-1];
		log(TAG, "getFilenameInURL : filename = " + filename);
		return filename;
	}


	public static String addHypenToPhone(String source){
		String str = "";
		String[] parts = new String[3];

		if(source.length() == 10){
			parts[0] = source.substring(0, 3);
			parts[1] = source.substring(3, 6);
			parts[2] = source.substring(6);
			str = parts[0] + "-" + parts[1] + "-" + parts[2];
		}else if(source.length() == 11){
			parts[0] = source.substring(0, 3);
			parts[1] = source.substring(3, 7);
			parts[2] = source.substring(7);
			str = parts[0] + "-" + parts[1] + "-" + parts[2];
		}else str = "�����Ͱ� �ùٸ��� �ʽ��ϴ�";

		return str;
	}

	public static boolean networkUsable(Context context){
		ConnectivityManager cm = (ConnectivityManager)context.getSystemService(context.CONNECTIVITY_SERVICE);
		boolean wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected();
		boolean mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected();
		boolean netUsable = true;
		if(!wifi && !mobile) netUsable = false;	
		return netUsable;
	}

	public Intent getMainEvent(Context mContext){
		Intent intent = new Intent(mContext, ActMain.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NO_ANIMATION);
		return intent;
	}



	public static int findDrawableIdByName(Context context, String name){
		int resID = 0;

		String resName = "@drawable/"+name;
		resID = context.getResources().getIdentifier(resName, "Drawable", context.getPackageName());

		if(resID == 0) resID = context.getResources().getIdentifier("@drawable/weather_shine", "Drawable", context.getPackageName());

		return resID;
	}
	
	public static int findStringIdByName(Context context, String name){
		int resID = 0;

		String resName = "@string/"+name;
		resID = context.getResources().getIdentifier(resName, "String", context.getPackageName());

		if(resID == 0) resID = context.getResources().getIdentifier("@string/weather_32", "String", context.getPackageName());

		return resID;
	}

	public static String getZeroSumString(int value){
		String temp = "";

		if(value < 10) temp = "0" + value;
		else temp = "" + value;

		return temp;
	}

	public static String getDayText(int dow){
		String day = "";

		switch(dow){
		case 1 : day = "일요일";
		break;
		case 2 : day = "월요일";
		break;
		case 3 : day = "화요일";
		break;
		case 4 : day = "수요일";
		break;
		case 5 : day = "목요일";
		break;
		case 6 : day = "금요일";
		break;
		case 7 : day = "토요일";
		break;
		}

		return day;
	}

	public static String ageToYear(int age){
		String year = "";

		Calendar cal = Calendar.getInstance();
		int yearInt = cal.get(Calendar.YEAR);
		int tempYear = (yearInt - age) + 1;
		year = Integer.toString(tempYear);

		return year;
	}

	public static String intToGen(int i){
		String gen = "";

		if(i==1) gen = "M";
		else if(i == 2) gen = "W";

		return gen;
	}

	/**
	 * ��¥ ����ϴ� �޼ҵ�
	 * ������ ��¥���� ũ�� true
	 */

	public static boolean isFinished(String end_date) throws ParseException {
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");

		Date product_end = date.parse(end_date);
		Date current = new Date();

		return current.after(product_end);
	}

	public static String replaceString(String Expression, String Pattern, String Rep)
	{
		if (Expression==null || Expression.equals("")) return "";

		int s = 0;
		int e = 0;
		StringBuffer result = new StringBuffer();

		while ((e = Expression.indexOf(Pattern, s)) >= 0) {
			result.append(Expression.substring(s, e));
			result.append(Rep);
			s = e + Pattern.length();
		}
		result.append(Expression.substring(s));
		return result.toString();
	}

	public static String getCurrentDeviceId(Context mContext) {
		String userDeviceid = Secure.getString(mContext.getContentResolver(),
				Settings.Secure.ANDROID_ID); // device id
		return userDeviceid;
	}

	public String replaceTag(String Expression, String type){
		String result = "";
		if (Expression==null || Expression.equals("")) return "";

		if (type == "encode") {
			result = replaceString(Expression, "&", "&amp;");
			result = replaceString(result, "\"", "&quot;");

			result = replaceString(result, "'", "&apos;");
			result = replaceString(result, "<", "&lt;");
			result = replaceString(result, ">", "&gt;");
			result = replaceString(result, "\r", "<br>");
			result = replaceString(result, "\n", "<p>");
		}
		else if (type == "decode") {
			result = replaceString(Expression, "&amp;", "&");
			result = replaceString(result, "&quot;", "\"");

			result = replaceString(result, "&apos;", "'");
			result = replaceString(result, "&lt;", "<");
			result = replaceString(result, "&gt;", ">");
			result = replaceString(result, "<br>", "\r");
			result = replaceString(result, "<p>", "\n");        
		}

		return result;  
	}

	public static String replaceHTMLTag(String Expression){
		String result = "";
		if (Expression==null || Expression.equals("")) return "";

		result = replaceString(Expression, "<a ", "<z ");
		result = replaceString(result, "</a>", "</z>");

		return result;  
	}

	public String replaceJsonTag(String Expression){
		String result = "";
		if (Expression==null || Expression.equals("")) return "";

		result = replaceString(Expression, "&#91;", "[");
		result = replaceString(result, "&#93;", "]");

		return result;  
	}

	/**
	 * <pre>
	 * 금액을 한글 금액으로 변환하여 리턴 - 1,000,000 -> 일백만 
	 * 
	 * @author jcpark
	 * @param number : 금액
	 * @return String : 한글 금액
	 * </pre>
	 */		
	public static String NumberToKorean(Long number)
	{
		String[] numberChar = { "", "일", "이", "삼", "사", "오", "육", "칠", "팔", "구" };
		String[] levelChar = { "", "십", "백", "천" };
		String[] decimalChar = { "", "만", "억", "조", "경", "해", "시", "양", "구", "간", "정" };

		String result = " ";
		String convString = number.toString();

		int length = 0;
		while ((length = convString.length()) > 0)
		{
			byte num = Byte.parseByte(convString.substring(0, 1));
			System.out.println(num);
			if (num != 0)
				result += numberChar[num] + levelChar[(length - 1) % 4] + " ";

			// 단위가 바뀌는 값들 (21(해), 17(경), 13(조), 9(억), 5(만), 1(원))
			if (length % 4 == 1)
			{
				result = result.substring(0, result.length() - 1);
				result += decimalChar[length / 4] + " ";
			}

			convString = convString.substring(1);
		}

		return result;
	}

	/**
	 * <pre>
	 * 금액을 단위 별 한글 금액으로 변환하여 리턴 - 1,000,000 -> 100만 
	 * 
	 * @author jcpark
	 * @param number : 금액
	 * 		  blank : 공백 삽입 여부
	 * @return String : 단위 별 한글 금액
	 * </pre>
	 */		
	public static String GetTradeMoney(Long number, Boolean blank)
	{
		String[] price_unit = new String[] { "", "만", "억", "조", "경", "해", "시", "양", "구", "간", "정" };
		String TradeMoney = number.toString();
		int moneyLength = TradeMoney.length();
		int blockCount = moneyLength / 4;
		int modCount = moneyLength % 4;	

		if (modCount > 0) {
			blockCount++;
		}

		if (modCount == 0) {
			modCount = 4;
		}

		String result = "";
		String temp = "";

		for (int i = 0; i < blockCount; i++) {

			if (i == 0) {
				temp = TradeMoney.substring(0, modCount);
			} else {
				temp = TradeMoney.substring(modCount + (4 * (i - 1)), modCount + (4 * (i - 1)) + 4);
			}

			if (!"0000".equals(temp)) {
				temp = Integer.toString(Integer.parseInt(temp));

				if (temp.length() == 4) {
					temp = temp.substring(0, 1) + "," + temp.substring(1, 4);
				}

				temp += price_unit[blockCount - (i + 1)];

				if (blank) {
					temp += " ";
				}

				result += temp;
			}
		}

		return result;
	}

	/**
	 * <pre>
	 * 금액을 단위 별 한글 금액으로 변환하여 리턴 - 1,000,000 -> 100만 
	 * 
	 * @author jcpark
	 * @param number : 금액
	 * 		  blank : 공백 삽입 여부
	 * @return String : 단위 별 한글 금액
	 * </pre>
	 */		
	public static String GetTradeMoney(String number, Boolean blank)
	{
		String[] price_unit = new String[] { "", "만", "억", "조", "경", "해", "시", "양", "구", "간", "정" };
		String TradeMoney = number.toString();
		int moneyLength = TradeMoney.length();
		int blockCount = moneyLength / 4;
		int modCount = moneyLength % 4;	

		if (modCount > 0) {
			blockCount++;
		}

		if (modCount == 0) {
			modCount = 4;
		}

		String result = "";
		String temp = "";

		for (int i = 0; i < blockCount; i++) {

			if (i == 0) {
				temp = TradeMoney.substring(0, modCount);
			} else {
				temp = TradeMoney.substring(modCount + (4 * (i - 1)), modCount + (4 * (i - 1)) + 4);
			}

			if (!"0000".equals(temp)) {
				temp = Integer.toString(Integer.parseInt(temp));

				if (temp.length() == 4) {
					temp = temp.substring(0, 1) + "," + temp.substring(1, 4);
				}

				temp += price_unit[blockCount - (i + 1)];

				if (blank) {
					temp += " ";
				}

				result += temp;
			}
		}

		return result;
	}

	public Utils(Context con){
		mContext = con;
	}

	public String getStore(){
		return Store;
	}

	public static void setToastShow(Context con, String msg){
		Toast.makeText(con, msg, Toast.LENGTH_SHORT).show();
	}

	public static void setToastShow(String msg){
		Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
	}


	public static boolean nullPoint(Object obj){
		return null == obj;
	}

	public static String UTF8Return(String encode){
		String encodeResult = null;
		try {
			encodeResult = URLEncoder.encode(encode, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return encodeResult;
	}

	public static String urlencode(String original)
	{
		String temp = "";
		if(!nullPoint(original)){
			temp = original.replace(" ", "%20");
		}

		return temp;
	}

	public static String imageEncode(String original)
	{
		String temp = "";
		if(!nullPoint(original)){
			temp = original.replace(" ", "+");
		}

		return temp;
	}

	public static void customToastShow(Context con, int img, CharSequence text) {
		Context mContext = con;
		if (mTypeface == null)
			mTypeface = Typeface.createFromAsset(mContext.getAssets(), "AuctionGothic_Medium.ttf");


		TextView tv = new TextView(mContext); 
		tv.setText(text);
		tv.setTextSize(con.getResources().getDimension(R.dimen.txt_6));
		tv.setTextColor(Color.WHITE); 
		tv.setTypeface(mTypeface);
		tv.setGravity(Gravity.CENTER);
		LinearLayout ll = new LinearLayout(mContext); 
		ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)); 
		ll.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.my_rounded_corner));
		ll.setGravity(Gravity.CENTER); 
		ll.addView(tv); 
		final Toast t = Toast.makeText(mContext, "", Toast.LENGTH_SHORT);
		t.setGravity(Gravity.CENTER, 0, 0); 
		t.setView(ll); 
		t.show(); 

		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				t.cancel();

			}
		}, 1);

	}

	public static void customToastShow(Context con, CharSequence text) {
		Context mContext = con;
//		if (mTypeface == null)
//			mTypeface = Typeface.createFromAsset(mContext.getAssets(), "AuctionGothic_Medium.ttf");
//

		TextView tv = new TextView(mContext); 
		tv.setText(text);
		tv.setTextSize(con.getResources().getDimension(R.dimen.txt_6));
		tv.setTextColor(Color.WHITE); 
//		tv.setTypeface(mTypeface);
		tv.setGravity(Gravity.CENTER);
		LinearLayout ll = new LinearLayout(mContext); 
		ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)); 
		ll.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.my_rounded_corner));
		ll.setGravity(Gravity.CENTER); 
		ll.addView(tv); 
		final Toast t = Toast.makeText(mContext, "", Toast.LENGTH_SHORT);
		t.setGravity(Gravity.CENTER, 0, 0); 
		t.setView(ll); 
		t.show(); 

	}

	public static LinearLayout.LayoutParams getParams(){
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT,
				Gravity.CENTER_HORIZONTAL
				);

		params.weight = 1.0f;
		return params; 
	}

	public static FrameLayout.LayoutParams getFrameLayoutParams(){
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT,
				Gravity.CENTER_HORIZONTAL
				);
		return params; 
	}

	// 캐쉬 ?��?��
	public static void clearApplicationCache(java.io.File dir, Context mContext){
		if(dir==null)
			dir = mContext.getCacheDir();
		else;
		if(dir==null)
			return;
		else;
		java.io.File[] children = dir.listFiles();
		try{
			for(int i=0;i<children.length;i++)
				if(children[i].isDirectory())
					clearApplicationCache(children[i], mContext);
				else children[i].delete();
		}
		catch(Exception e){}
	}


	//?��바이?�� ?��?��?�� 체크
	public String getCurrentDeviceId() {
		String userDeviceid = Secure.getString(mContext.getContentResolver(),
				Settings.Secure.ANDROID_ID); // device id
		return userDeviceid;
	}

	/**
	 * Process�? ?��?��중인�? ?���? ?��?��.
	 * @param context, packageName
	 * @return true/false
	 */
	public static boolean isRunningProcess(Context context, String packageName) {

		boolean isRunning = false;

		ActivityManager actMng = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);                      

		List<RunningAppProcessInfo> list = actMng.getRunningAppProcesses();     

		for(RunningAppProcessInfo rap : list)                           
		{                                
			if(rap.processName.equals(packageName))                              
			{                                   
				isRunning = true;     
				break;
			}                         
		}

		return isRunning;
	}


	//?��?�� ?���? �??��?���? ?��?�� 메소?��
	public HashMap<String, Object> getYMD(){
		HashMap<String, Object> map = new HashMap<String, Object>();

		Calendar calendar = Calendar.getInstance();
		map.put("year", calendar.get(Calendar.YEAR));
		map.put("month",calendar.get(Calendar.MONTH) + 1);
		map.put("day",calendar.get(Calendar.DAY_OF_MONTH));

		return map;
	}

	//?��?�� ?���? �??��?���? ?��?�� 메소?��
	public String getYesterdayYMD(){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		return calendar.get(Calendar.YEAR) + "" + getZero(calendar.get(Calendar.MONTH) + 1) + "" + getZero((calendar.get(Calendar.DAY_OF_MONTH)));
	}


	//?��?�� ?���? �??��?���? ?��?�� 메소?��
	public static String getMilleageYMD(){
		String result = "";
		Calendar calendar = Calendar.getInstance();
		result = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" +calendar.get(Calendar.DAY_OF_MONTH);

		return result;
	}

	public String getMilleageWeekYMD(){
		String result = "";
		Calendar calendar = Calendar.getInstance();
		calendar.add(calendar.DAY_OF_MONTH, -7);
		result = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" +calendar.get(Calendar.DAY_OF_MONTH);

		return result;
	}

	public String getMilleageWeek2YMD(){
		String result = "";
		Calendar calendar = Calendar.getInstance();
		calendar.add(calendar.DAY_OF_MONTH, -14);
		result = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" +calendar.get(Calendar.DAY_OF_MONTH);

		return result;
	}

	public String getMilleageMonthYMD(){
		String result = "";
		Calendar calendar = Calendar.getInstance();
		calendar.add(calendar.MONTH, -1);
		result = calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + "-" +calendar.get(Calendar.DAY_OF_MONTH);

		return result;
	}

	//	//?��?�� ?���? �??��?���? ?��?�� 메소?��
	//	public String getRegDate(String regdate){
	//		Utils.log(TAG, regdate);
	//		String replaceRegdate = "";
	//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd a HH:mm:ss",Locale.KOREA);
	//		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
	//		Date d = null;
	//
	//		try {
	//			d = df.parse(regdate);
	//			replaceRegdate = formatter.format(d);
	//			Utils.log(TAG, replaceRegdate);
	//		} catch (ParseException e) {
	//			// TODO Auto-generated catch block
	//			e.printStackTrace();
	//		}
	//
	//		return replaceRegdate;
	//	}

	//?��?�� ?���? �??��?���? ?��?�� 메소?��
	public String getGameMarkerRegDate(String regdate){
		Utils.log(TAG, regdate);
		String replaceRegdate = "";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd a HH:mm:ss",Locale.KOREA);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy / MM / dd?�� ?���?");
		Date d = null;

		try {
			d = df.parse(regdate);
			replaceRegdate = formatter.format(d);
			Utils.log(TAG, replaceRegdate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return replaceRegdate;
	}

	//?��?�� ?���? �??��?���? ?��?�� 메소?��
	public String getRegDate(String regdate){
		Utils.log(TAG, regdate);
		String replaceRegdate = "";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd a HH:mm:ss",Locale.KOREA);
		SimpleDateFormat formatter = new SimpleDateFormat("MM?�� dd?�� EEE?��?��");
		Date d = null;

		try {
			d = df.parse(regdate);
			replaceRegdate = formatter.format(d);
			Utils.log(TAG, replaceRegdate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return replaceRegdate;
	}

	//?��?�� ?���? �??��?���? ?��?�� 메소?��
	public String getDetailRegDate(String regdate){
		Utils.log(TAG, regdate);
		String replaceRegdate = "";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd a HH:mm:ss",Locale.KOREA);
		SimpleDateFormat formatter = new SimpleDateFormat("?��록일?�� : yyyy. MM. dd");
		Date d = null;

		try {
			d = df.parse(regdate);
			replaceRegdate = formatter.format(d);
			Utils.log(TAG, replaceRegdate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return replaceRegdate;
	}


	//?��?�� ?���? �??��?���? ?��?�� 메소?��
	public String getTodayYMD(){
		String today = "";
		Calendar calendar = Calendar.getInstance();

		today = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);

		return today;
	}

	public boolean isCheckedData(EditText et){

		String eString = et.getText().toString();

		if(eString.equals("")){
			return false;
		}else{
			return true;
		}
	}

	public String editTextToString(EditText et){
		String eString = et.getText().toString();
		return eString;
	}

	public int getMaxDay(int year, int month){
		Calendar calendar = Calendar.getInstance ( );
		calendar.set(year, month-1, 1);
		int maxDays = calendar.getActualMaximum (Calendar.DAY_OF_MONTH);
		return maxDays;
	}

	public int getWeekend(int year, int month, int day){
		int dayChk = 0;
		Calendar calendar = Calendar.getInstance ( );
		calendar.set(year, month-1, day);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		if(dayOfWeek == Calendar.SATURDAY ){
			dayChk = 1;
		}else if(dayOfWeek == Calendar.SUNDAY){
			dayChk = 2;
		}else{
			dayChk = 0;
		}
		return dayChk;
	}

	//?��?�� ?���? �??��?���? ?��?�� 메소?��
	public String getTodayWeekendText(){
		String today = "";
		Calendar calendar = Calendar.getInstance();

		final String[] week = { "?��", "?��", "?��", "?��", "�?", "�?", "?��" };
		String weekText = week[calendar.get(Calendar.DAY_OF_WEEK) - 1];
		return weekText;
	}

	public String getWeekendText(int year, int month, int day){
		Calendar calendar = Calendar.getInstance ( );
		calendar.set(year, month-1, day);
		final String[] week = { "?��", "?��", "?��", "?��", "�?", "�?", "?��" };
		String weekText = week[calendar.get(Calendar.DAY_OF_WEEK) - 1];
		return weekText;
	}

	// ?���? 롱클�? ?��?��?�� ?��?�� 방�? 리스?��(webview xml ?��?��?��?�� lockClickable="false" ?? ?���? ?��?��)
	public static View.OnLongClickListener doNotSelectText = new View.OnLongClickListener() {
		@Override
		public boolean onLongClick(View v) { return true; }
	};


	public boolean checkMailAddress(String address){
		boolean valid = true;

		if(address.contains("@@")) return false;

		String[] splitAt = address.split("@");
		log("checkMailAddress", "split @ length : " + splitAt.length);
		if(splitAt.length < 2) return false;

		String replaceDot = splitAt[1].replace(".", "@");
		if(replaceDot.contains("@@")) return false;
		String[] splitAt2 = replaceDot.split("@");
		log("checkMailAddress", "split @ [1] = " + splitAt[1] + ", split @ 2 length : " + splitAt2.length);
		if(splitAt2.length < 2) return false;

		return valid;
	}

	public long getNowTime(){
		Date curDate = new Date();
		return curDate.getTime();
	}

	public static String getEncodeStr(String target){
		String encodeStr = "";

		if(!nullPoint(target)){

			try {
				encodeStr = URLEncoder.encode(target, "utf-8");
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return encodeStr;
	}

	public static void initEmptyToast(Context context, String msg){
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}

	public static String ReplaceString(String Expression, String Pattern, String Rep)
	{
		if (Expression==null || Expression.equals("")) return "";

		int s = 0;
		int e = 0;
		StringBuffer result = new StringBuffer();

		while ((e = Expression.indexOf(Pattern, s)) >= 0) {
			result.append(Expression.substring(s, e));
			result.append(Rep);
			s = e + Pattern.length();
		}
		result.append(Expression.substring(s));
		return result.toString();
	}

	public void alertDialog (String title, String msg, DialogInterface.OnClickListener positive, DialogInterface.OnClickListener nagative){

		try{
			AlertDialog build = new Builder(mContext)
			.setTitle(title)
			.setMessage(msg)
			.setPositiveButton("?��?��", positive)
			.setNegativeButton("취소", nagative)
			.show();
		}catch (BadTokenException e){

		}

	}

	public static void alertSingleDialog (String title, String msg, DialogInterface.OnClickListener positive){
		try{
			AlertDialog build = new Builder(mContext)
			.setTitle(title)
			.setMessage(msg)
			.setPositiveButton("?��?��", positive)
			.show();
		}catch (BadTokenException e){

		}


	}

	public DialogInterface.OnClickListener getLoginEvent(){
		DialogInterface.OnClickListener onLoginPasitive = new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub

				//				Intent intent = new Intent(mContext, ActLoginNew.class);
				//				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NO_ANIMATION);
				//				mContext.startActivity(intent);
			}

		};

		return onLoginPasitive;
	}

	public static DialogInterface.OnClickListener getBackEvent(final Activity activity){
		DialogInterface.OnClickListener onGPSPasitive = new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				activity.finish();
			}

		};

		return onGPSPasitive;
	}

	public static DialogInterface.OnClickListener getNagativeEvent(){
		DialogInterface.OnClickListener onNagative = new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}

		};
		return onNagative;
	}

	//	public Intent getAcLoginFirst(){
	//		Intent intent = new Intent(mContext, AcLoginFirst.class);
	//		mContext.startActivity(intent);
	//
	//		return intent;
	//	}
	//	
	//	public Intent getAcLoginNew(){
	//		Intent intent = new Intent(mContext, ActLoginNew.class);
	//		mContext.startActivity(intent);
	//
	//		return intent;
	//	}
	//	
	//	public Intent getActContact(){
	//		Intent intent = new Intent(mContext, ActContact.class);
	//		mContext.startActivity(intent);
	//
	//		return intent;
	//	}


	public Intent getFAQEvent(String uri){
		Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse(uri));
		mContext.startActivity(intent);

		return intent;
	}

	public Intent getUriView(String uri){
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
		mContext.startActivity(intent);

		return intent;
	}

	public Intent getPolicyDetail(String title, String content){
		Intent intent = new Intent(mContext, Policy.class);
		intent.putExtra("title", title);
		intent.putExtra("content", content);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NO_ANIMATION);
		mContext.startActivity(intent);

		return intent;
	}

	public Intent getTstoreEvent(String pid) throws ActivityNotFoundException{
		String tstorePID = pid;
		Intent intent = new Intent();
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.setClassName("com.skt.skaf.A000Z00040", "com.skt.skaf.A000Z00040.A000Z00040");
		intent.setAction("COLLAB_ACTION");
		intent.putExtra("com.skt.skaf.COL.URI", ("PRODUCT_VIEW/" + tstorePID + "/0").getBytes());
		intent.putExtra("com.skt.skaf.COL.REQUESTER", "A000Z00040"); // tstore app ID
		mContext.startActivity(intent);

		return intent;
	}

	public Intent getMarketView(String packageName){
		Uri uri = Uri.parse("market://details?id="+packageName);  
		Intent it = new Intent(Intent.ACTION_VIEW, uri);  
		mContext.startActivity(it);  
		return it;
	}

	public void keyboardcheck(EditText search, boolean keycheck){
		InputMethodManager mgr = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);

		if(keycheck){
			mgr.showSoftInput(search, InputMethodManager.SHOW_IMPLICIT);

		}else{
			mgr.hideSoftInputFromWindow(search.getWindowToken(), 0);
		}
	}

	public static String ReplaceTag(String Expression, String type){
		String result = "";
		if (Expression==null || Expression.equals("")) return "";

		if (type == "encode") {
			result = ReplaceString(Expression, "&", "&amp;");
			result = ReplaceString(result, "\"", "&quot;");

			result = ReplaceString(result, "'", "&apos;");
			result = ReplaceString(result, "<", "&lt;");
			result = ReplaceString(result, ">", "&gt;");
			result = ReplaceString(result, "\r", "<br>");
			result = ReplaceString(result, "\n", "<p>");
			result = ReplaceString(result, " ", "&nbsp;");
		}
		else if (type == "decode") {
			result = ReplaceString(Expression, "&amp;", "&");
			result = ReplaceString(result, "&quot;", "\"");

			result = ReplaceString(result, "&apos;", "'");
			result = ReplaceString(result, "&lt;", "<");
			result = ReplaceString(result, "&gt;", ">");
			result = ReplaceString(result, "<br>", "\r");
			result = ReplaceString(result, "<p>", "\n");   
			result = ReplaceString(result, "&nbsp;", " ");  
		}

		return result;  
	}

	

	/**
	 * 금액?�� ?��???��?�� 천단?�� ,�? ?���? ?��?�� 메소?��
	 * @param intPrice 금액
	 * @return ,�? ?��?���? 금액
	 */
	public static String getPriceText(int intPrice){
		String text = "";
		String cost = Integer.toString(intPrice);
		String price = "";

		int len,share,rest,start,end;

		if(cost.startsWith("-")){

			len = cost.length();
			price = cost.substring(1,len);

			len = price.length();
			share = len/3;
			rest = len%3;
			start = 0;
			end = rest;


		}else{
			price = cost;

			len = price.length();
			share = len/3;
			rest = len%3;
			start = 0;
			end = rest;
		}


		if(rest>0){
			text = price.substring(start, end);
			if(len>3) text +=",";
		}
		for(int i=0;i<share;i++){
			start = end;
			end += 3;
			if(i<share-1) text = text + price.substring(start, end)+",";
			else text = text + price.substring(start, end);
		}



		return text;
	}

	public static String getZero(int num){
		String number = String.valueOf(num);


		if(number.length() == 2){
			number = String.valueOf(num);
		}else{
			number = "0" + number;
		}

		return number;
	}

	public static String getDistance(String dist){
		String number = "";

		float distance = Float.parseFloat(dist);
		distance = distance/1000;

		number = distance+"Km";

		return number;
	}

	public static String getTourDistance(String dist){

		return dist+"Km";
	}

	/**
	 * 금액?�� ?��???��?�� 천단?�� ,�? ?���? ?��?�� 메소?��
	 * @param intPrice 금액
	 * @return ,�? ?��?���? 금액
	 */
	public String getPriceText(long intPrice){
		String text = "";
		String cost = Long.toString(intPrice);
		String price = "";

		int len,share,rest,start,end;

		if(cost.startsWith("-")){

			len = cost.length();
			price = cost.substring(1,len);

			len = price.length();
			share = len/3;
			rest = len%3;
			start = 0;
			end = rest;


		}else{
			price = cost;

			len = price.length();
			share = len/3;
			rest = len%3;
			start = 0;
			end = rest;
		}


		if(rest>0){
			text = price.substring(start, end);
			if(len>3) text +=",";
		}
		for(int i=0;i<share;i++){
			start = end;
			end += 3;
			if(i<share-1) text = text + price.substring(start, end)+",";
			else text = text + price.substring(start, end);
		}



		return text;
	}

	public static String toTag(String str) {

		String result = str;

		if(result != null) {
			result = result.replace("<", "&lt;").replace(">", "&gt;");
			result = result.replace("[", "&#91;").replace("]", "&#93;");
			result = result.replace("{", "&#123;").replace("}", "&#125;");
			result = result.replace("!", "&#33;");
			result = result.replace("\"", "&quot;");
			result = result.replace("#", "&#35;");
			result = result.replace("$", "&#36;");
			result = result.replace("%", "&#37;");
			result = result.replace("&", "&amp;");
			result = result.replace("'", "&#39;");
			result = result.replace("(", "&#40;").replace(")", "&#41;");
			result = result.replace("/", "&#47;");
			result = result.replace("?", "&#63;");
			result = result.replace("\\", "&#92");
		}
		return result;
	}

	public String ReplaceHTMLTag(String Expression){
		String result = "";
		if (Expression==null || Expression.equals("")) return "";

		result = ReplaceString(Expression, "<a ", "<z ");
		result = ReplaceString(result, "</a>", "</z>");

		return result;  
	}

	public String ReplaceJsonTag(String Expression){
		String result = "";
		if (Expression==null || Expression.equals("")) return "";

		result = ReplaceString(Expression, "&#91;", "[");
		result = ReplaceString(result, "&#93;", "]");

		return result;  
	}

	public static String getTag() {
		return TAG;
	}


	public static boolean isCellphone(String str) {
		return str.matches("(01[016789])(\\d{3,4})(\\d{4})");
	}

	public static String makePhoneNumber(String phoneNumber) {
		String regEx = "(\\d{3})(\\d{3,4})(\\d{4})";
		if(!Pattern.matches(regEx, phoneNumber)) return null;
		return phoneNumber.replaceAll(regEx, "$1-$2-$3");

	}

	public JSONArray getContactList(Activity main) {

		JSONArray jarr = new JSONArray();

		Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

		String[] projection = new String[] {
				ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
				ContactsContract.CommonDataKinds.Phone.NUMBER,
				ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME };

		String[] selectionArgs = null;

		String sortOrder = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
				+ " COLLATE LOCALIZED ASC";

		Cursor contactCursor = main.managedQuery(uri, projection, null,
				selectionArgs, sortOrder);

		if (contactCursor.moveToFirst()) {
			do {
				String phonenumber = contactCursor.getString(1).replaceAll("-",
						"");
				if (phonenumber.length() == 10) {
					phonenumber = phonenumber.substring(0, 3) + "-"
							+ phonenumber.substring(3, 6) + "-"
							+ phonenumber.substring(6);
				} else if (phonenumber.length() > 8) {
					phonenumber = phonenumber.substring(0, 3) + "-"
							+ phonenumber.substring(3, 7) + "-"
							+ phonenumber.substring(7);
				}

				JSONObject jobj = new JSONObject();
				try {
					jobj.put("name", contactCursor.getString(2));
					jobj.put("tel", phonenumber);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


				jarr.put(jobj);
			} while (contactCursor.moveToNext());
		}

		return jarr;

	}

	public static String[] onDivide(String str){

		return str.split(",");
	}



	public Bitmap getResizeBitmap(Bitmap bitmap, int viewwidth){

		int viewWidth = viewwidth;
		float width = bitmap.getWidth();
		float height = bitmap.getHeight();

		// Calculate image's size by maintain the image's aspect ratio
		if(width < viewwidth)
		{
			float percente = (float)(width / 100);
			float scale = (float)(viewwidth / percente);
			width *= (scale / 100);
			height *= (scale / 100);
		}

		// Resizing image
		Bitmap sizingBmp = Bitmap.createScaledBitmap(bitmap, (int) width, (int) height, true);

		return sizingBmp;
	}

	public static Bitmap getWebImg(String imgPath){
		Bitmap bmp = null;
		try {
			URL url = new URL(imgPath);
			URLConnection con = url.openConnection();
			con.connect();
			BufferedInputStream bis = new BufferedInputStream(con.getInputStream());
			bmp = BitmapFactory.decodeStream(bis);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (OutOfMemoryError e){
			e.getMessage();
			return null;
		}
		return bmp;
	}

	public static Bitmap getBitmapFromDrawable(Drawable drawable){
		Bitmap b = ((BitmapDrawable) drawable).getBitmap();
		return b;
	}
	
	public String getTemp(String temp){
		String onTemp = "";
		
		int temp_int = 0;
		temp_int = Integer.parseInt(temp);
		
		int ondo = (int) ((temp_int - 32) / 1.8);
		onTemp = String.valueOf(ondo);
		
		return onTemp;
	}

	public static String getWeatherIco(String index_){
		String weather = "";

		int index = Integer.parseInt(index_);

		switch (index) {
		case 1:
			weather = "weather_shine";
			break;
		case 2:
		case 16:
		case 21:
			weather = "weather_shinecloud";
			break;
		case 3:
		case 11:
			weather = "weather_cloud";
			break;

		case 4:
		case 6:
		case 7:
		case 10:
		case 12:
		case 13:
		case 15:
		case 17:
		case 18:
		case 20:
			weather = "weather_rain";
			break;
		case 5:
		case 8:
		case 14:
		case 19:
			weather = "weather_snow";
			break;
		case 9:
			weather = "weather_smog";
			break;
		case 22:
			weather = "weather_yellowsmog";
			break;

		default:
			weather = "weather_shinecloud";
			break;
		}


		return weather;
	}

	public static void alert(String message) {
		new AlertDialog.Builder(mContext)
		.setIcon(android.R.drawable.ic_dialog_alert)
		.setTitle(R.string.app_name)
		.setMessage(message)
		.setPositiveButton(android.R.string.ok, null)
		.create().show();
	}

	public static Bundle setBundle(String title, String url){
		Bundle b = new Bundle();
		b.putString("title", title);
		b.putString("url", url);

		return b;
	}

	public Intent getActDash(){
		Intent intent = new Intent(mContext, ActSub.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NO_ANIMATION);
		mContext.startActivity(intent);

		return intent;
	}
	
	public void shareShareIntent(String Url)
	{
		try{
			Intent shareIntent = new Intent("android.intent.action.SEND");
			shareIntent.setType("text/plain");
			shareIntent.putExtra("android.intent.extra.TEXT", Url);
			mContext.startActivity(shareIntent);
		}catch(Exception e){e.printStackTrace();}
	}
	
	public void notificationTest()
	{
		PendingIntent activity = null;
		Intent intent = null;
		intent = new Intent(mContext.getApplicationContext(), ActSub.class);
		
		activity = PendingIntent.getActivity(mContext.getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		
		
		NotificationManager nm = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification n = new Notification.Builder(mContext)
			.setContentTitle("타이틀")
			.setContentText("노출정보")
			.setSmallIcon(R.drawable.ic_launcher) // 아이콘
			//.setLargeIcon(bitmap)              // 이미지 사용시 나타나게함
			//.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE) // 사운드와 진동
			.setAutoCancel(true) // 
			.setNumber(1)  //푸시 아이디
			.setContentIntent(activity)
			//.setTicker((String) map.get("Message"))
		.build();
		nm.notify(Integer.parseInt("1") ,n);
	}
	
	public void setImageLoader(ImageView img, String url)
	{
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(mContext)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs() // Remove for release app
				.build();

		ImageLoader.getInstance().init(config);
		
		ImageLoader imageLoader = ImageLoader.getInstance();
		ImageAware imageAware = new ImageViewAware(img, false);
		
		DisplayImageOptions options = new DisplayImageOptions.Builder()
		           .showImageForEmptyUri(R.drawable.ic_launcher)
		           .cacheOnDisc(true)
		           .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
		           .cacheInMemory(true)
		           .bitmapConfig(Bitmap.Config.RGB_565)
		           .displayer(new RoundedBitmapDisplayer(0))
		           .build();
		
		imageLoader.displayImage(url, imageAware, options);
	}
	
//	public Intent getActPortal(){
//		Intent intent = new Intent(mContext, ActPortal.class);
//		mContext.startActivity(intent);
//
//		return intent;
//	}
}