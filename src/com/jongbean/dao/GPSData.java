package com.jongbean.dao;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.view.WindowManager.BadTokenException;

/**
 * GPS �������?����
 * @author jongbean
 *
 */
public class GPSData {

	public static double gps_lat = 0.0;
	public static double gps_lon = 0.0;
	public static String gps_sigu="";
	public static String gps_myeon="";
	public static String gps_addr="";
	
	private Context mContext;
	
	public GPSData(Context context){
		mContext = context;
	}
}
