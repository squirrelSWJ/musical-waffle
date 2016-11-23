package com.jongbean.utils;

import java.io.IOException;

import com.jongbean.dao.GPSData;
import com.jongbean.gps.GPSProvider;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Handler;

public class GPSUse {

	private Activity activity;
	boolean gps_enable;
	boolean gps_enable_chk;

	boolean update_enable_chk;

	/**
	 * GPS 환경설정페이지 이동 후 접속시 체크
	 */
	public boolean resumeCheck = false;

	/**
	 * GPS 사용 설정부분
	 */
	private boolean gps_Check;
	private LocationManager locationManager = null;
	private GPSProvider gpsProvider;

	/**
	 * GPS 정보 저장
	 */
	private GPSData mGps;

	private Utils utils;
	private SharedValues sv;
	
	private Handler mHandler2;

	public GPSUse(Activity ac, Handler handler_){
		this.activity = ac;
		utils = new Utils(activity);
		sv = new SharedValues(activity);
		mHandler2 = handler_;
	}

	/**
	 * GPS정보를 가져오기 위한 메소드
	 * @return 결과값
	 * @throws IOException 네트워크 에러 체크
	 */
	public void Gps_Init() throws IOException{
		//GPS 서비스 등록
		String context = Context.LOCATION_SERVICE;
		locationManager = (LocationManager)(activity.getSystemService(context));
		mGps = new GPSData(activity);

		if(!locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
//			if(!sv.getGPSStatus()){
//				alertCheckGPS();
//			}else{
				GPSConfirm();
//			}
			

		}else{

			String temp =""; 
			gpsProvider = new GPSProvider(locationManager);

			GPSData.gps_lat = gpsProvider.getLatitude();
			GPSData.gps_lon = gpsProvider.getLongitude();

			Utils.log("gps_lat", ""+GPSData.gps_lat);
			Utils.log("gps_lon", ""+GPSData.gps_lon);
			
			mHandler.sendEmptyMessageDelayed(0, 1000);
			
		}

	}
	
	Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			
			if(GPSData.gps_lat == 0.0){
				try {
					Gps_Init();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				mHandler2.sendEmptyMessage(0);
			}
			
			
		};
	};

	public static boolean bStart = true;

	/**
	 * 위치정보 사용에 따른 메시지 출력
	 */
	public void alertCheckGPS() {

		AlertDialog.Builder gsDialog = new AlertDialog.Builder(activity);
		gsDialog.setTitle("위치정보확인");
		gsDialog.setMessage("‘테스트’에서 고객님의 현재 위치 정보를 사용하고자 합니다.");
		gsDialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// GPS설정 화면으로 이동
				//sv.setGPSStatus(true);
				moveConfigGPS();
				dialog.dismiss();
				resumeCheck = true;
			}
		});
		gsDialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// GPS설정 화면으로 이동
				dialog.dismiss();
				GPSData.gps_lat = 37.516799;
				GPSData.gps_lon = 127.046911;
				
//				Act.locaionThread();
			}
		}).create().show();

	}
	
	/**
	 * 위치정보 사용에 따른 메시지 출력
	 */
	public void GPSConfirm() {

		AlertDialog.Builder gsDialog = new AlertDialog.Builder(activity);
		gsDialog.setTitle("테스트");
		gsDialog.setMessage("위치정보를 승인하셔야 주변정보를 사용하실수 있습니다.");
		gsDialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// GPS설정 화면으로 이동
				moveConfigGPS();
				dialog.dismiss();
				resumeCheck = true;
			}
		});
		gsDialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// GPS설정 화면으로 이동
				GPSData.gps_lat = 37.516799;
				GPSData.gps_lon = 127.046911;
				
//				Act.locaionThread();
				dialog.dismiss();
			}
		}).create().show();

	}

	// GPS 설정화면으로 이동
	public void moveConfigGPS() {
		Intent gpsOptionsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		activity.startActivity(gpsOptionsIntent);
	}

}
