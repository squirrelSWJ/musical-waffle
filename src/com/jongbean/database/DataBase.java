package com.jongbean.database;

import java.util.ArrayList;

import com.jongbean.dao.FavoriteDao;
import com.jongbean.utils.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DataBase {

	final static private String PACKAGE_NAME = "kr.jongbean";
	final static private String DB_NAME = "list.db";
	private Context mContext;
	public SQLiteDatabase db;
	ContentValues row;
	DBHelper mHelper;

	public static String getDbName() {
		return DB_NAME;
	}

	public DataBase(Context con){
		mContext = con;
		mHelper = new DBHelper(con, DB_NAME, null, 1);
		db = mHelper.getWritableDatabase();
	}

	public void close(){
		db.close();
	}

	public boolean insertGameReg(String Name, String URL){
		db = mHelper.getWritableDatabase();
		db.execSQL("INSERT OR REPLACE INTO Favorite (Name, URL,RegDate) VALUES ('"+Name+"', '"+URL+"', '"+Utils.getMilleageYMD()+"');");
		db.close();
		return false;
	}

	public ArrayList<FavoriteDao> selectFavoList(){
		ArrayList<FavoriteDao> items = new ArrayList<FavoriteDao>();

		db = mHelper.getReadableDatabase();
		Cursor cursor;

		cursor = db.rawQuery("select * from Favorite where 1 order by _id desc" , null);
		while (cursor.moveToNext()) {
			FavoriteDao favo = new FavoriteDao();
			favo.setNo(cursor.getInt(cursor.getColumnIndex("_id")));
			favo.setName(cursor.getString(cursor.getColumnIndex("name")));
			favo.setURL(cursor.getString(cursor.getColumnIndex("url")));
			favo.setRegDate(cursor.getString(cursor.getColumnIndex("regdate")));

			items.add(favo);

		}
		cursor.close();
		mHelper.close();


		return items;     
	}

	public void deleteContact(int id) {
		db = mHelper.getWritableDatabase();
		db.delete("Favorite", "_id = "+id, null);
		db.close();
	}

	public boolean webtooninsertGameReg(String Name, String URL){
		db = mHelper.getWritableDatabase();
		db.execSQL("INSERT OR REPLACE INTO WebToonFavorite (Name, URL,RegDate) VALUES ('"+Name+"', '"+URL+"', '"+Utils.getMilleageYMD()+"');");
		db.close();
		return false;
	}

	public ArrayList<FavoriteDao> webtoonselectFavoList(){
		ArrayList<FavoriteDao> items = new ArrayList<FavoriteDao>();

		db = mHelper.getReadableDatabase();
		Cursor cursor;

		cursor = db.rawQuery("select * from WebToonFavorite where 1 order by _id desc" , null);
		while (cursor.moveToNext()) {
			FavoriteDao favo = new FavoriteDao();
			favo.setNo(cursor.getInt(cursor.getColumnIndex("_id")));
			favo.setName(cursor.getString(cursor.getColumnIndex("name")));
			favo.setURL(cursor.getString(cursor.getColumnIndex("url")));
			favo.setRegDate(cursor.getString(cursor.getColumnIndex("regdate")));

			items.add(favo);

		}
		cursor.close();
		mHelper.close();


		return items;     
	}

	public void webtoondeleteContact(int id) {
		db = mHelper.getWritableDatabase();
		db.delete("WebToonFavorite", "_id = "+id, null);
		db.close();
	}

	public boolean appFavoInsert(String apppackage, String appname, String appicon){
		db = mHelper.getWritableDatabase();

		db.execSQL("INSERT OR REPLACE INTO AppFavoList (apppackage, appname, appicon) VALUES ('"+apppackage+"', '"+appname+"', '"+appicon+"');");
		db.close();
		return false;
	}

	public void appFavoDelete(int id) {
		db = mHelper.getWritableDatabase();
		db.delete("AppFavoList", "_id = "+id, null);
		db.close();
	}

//	public ArrayList<ApplicationInfoDao> appFavoList() throws SQLiteException{
//		ArrayList<ApplicationInfoDao> items = new ArrayList<ApplicationInfoDao>();
//
//		db = mHelper.getReadableDatabase();
//		Cursor cursor;
//
//		cursor = db.rawQuery("select * from AppFavoList where 1 order by _id desc" , null);
//		while (cursor.moveToNext()) {
//			ApplicationInfoDao favo = new ApplicationInfoDao();
//			favo.setAppId(cursor.getInt(cursor.getColumnIndex("_id")));
//			favo.setPackageName(cursor.getString(cursor.getColumnIndex("apppackage")));
//			favo.setAppName(cursor.getString(cursor.getColumnIndex("appname")));
//			favo.setAppIconName(cursor.getString(cursor.getColumnIndex("appicon")));
//
//			items.add(favo);
//
//		}
//		cursor.close();
//		mHelper.close();
//
//
//		return items;     
//	}

	public boolean checFavokInsert(String packagename){

		db = mHelper.getReadableDatabase();
		Cursor cursor;

		int count = 0;

		cursor = db.rawQuery("select count(*) as cnt from AppFavoList where apppackage like '"+packagename+"' order by _id desc" , null);
		while (cursor.moveToNext()) {
			count = cursor.getInt(cursor.getColumnIndex("cnt"));

		}
		cursor.close();
		mHelper.close();

		if(count == 0){
			return true;
		}else{
			return false;
		}

	}

	public int checFavokID(String packagename){

		db = mHelper.getReadableDatabase();
		Cursor cursor;

		int count = 0;

		cursor = db.rawQuery("select _id from AppFavoList where apppackage like '"+packagename+"' order by _id desc" , null);
		while (cursor.moveToNext()) {
			count = cursor.getInt(cursor.getColumnIndex("_id"));

		}
		cursor.close();
		mHelper.close();

		return count;

	}

}

class DBHelper extends SQLiteOpenHelper {

	// 생성자
	public DBHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(DataBases.CreateDB._CREATE);
		db.execSQL(DataBases.CreateDB2._CREATE);
		db.execSQL(DataBases.CreateDB3._CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS "+DataBase.getDbName());
		onCreate(db);
	}

}

class DataBases {

	public static final class CreateDB implements BaseColumns{
		public static final String NAME = "name";
		public static final String URL = "url";
		public static final String REGDATE = "regdate";
		public static final String _TABLENAME = "Favorite";
		public static final String _CREATE = 
				"create table "+_TABLENAME+"(" 
						+_ID+" integer primary key autoincrement, " 	
						+NAME+" text not null , " 
						+URL+" text not null , " 
						+REGDATE+" text not null );";
	}

	public static final class CreateDB2 implements BaseColumns{
		public static final String NAME = "name";
		public static final String URL = "url";
		public static final String REGDATE = "regdate";
		public static final String _TABLENAME = "WebToonFavorite";
		public static final String _CREATE = 
				"create table "+_TABLENAME+"(" 
						+_ID+" integer primary key autoincrement, " 	
						+NAME+" text not null , " 
						+URL+" text not null , " 
						+REGDATE+" text not null );";
	}

	public static final class CreateDB3 implements BaseColumns{
		public static final String PACKAGE = "apppackage";
		public static final String NAME = "appname";
		public static final String ICON = "appicon";
		public static final String _TABLENAME = "AppFavoList";
		public static final String _CREATE = 
				"create table "+_TABLENAME+"(" 
						+_ID+" integer primary key autoincrement, " 	
						+PACKAGE+" text not null , " 
						+NAME+" text not null , " 
						+ICON+" text not null );";
	}

}


