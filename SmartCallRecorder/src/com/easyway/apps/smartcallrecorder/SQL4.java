package com.easyway.apps.smartcallrecorder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
 
public class SQL4 extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "MyCalllogRecorder";	//��Ʈw�W��
	private static final int DATABASE_VERSION = 1;	//��Ʈw����
	public static final String MSG = "MSG";
	public static final String SERVICENAME = "SNAME";
	public static final String DATE = "DATE";
 
	private SQLiteDatabase db;
 
	public SQL4(Context context) {	//�غc�l
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		db = this.getWritableDatabase();
	}
 
	@Override
	public void onCreate(SQLiteDatabase db) {
		String DATABASE_CREATE_TABLE =
		    "create table MyRecorder ("
				+ "id INTEGER PRIMARY KEY AUTOINCREMENT,"
		        + "filename TEXT ,"
		        + "date TEXT ,"
		        + "name TEXT "
		    + ");";
		//�إ�config��ƪ�A�Ա��аѦ�SQL�y�k
		db.execSQL(DATABASE_CREATE_TABLE);
		
		
	}
 
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//oldVersion=�ª���Ʈw�����FnewVersion=�s����	�Ʈw����
		db.execSQL("DROP TABLE IF EXISTS MyRecorder");	//�R���¦�����ƪ�
		onCreate(db);
	}
	public int getCount() throws Exception{
		Cursor cursor = null;
		int count = 0;
		try{
			cursor = db.rawQuery("SELECT * FROM MyRecorder", null);
			count = cursor.getCount();
		}finally{
			cursor.close();
		}
		return count;
	}
	public Cursor getLikeFile(String msg){
		return db.rawQuery("SELECT * FROM MyRecorder where filename like '%"+msg+"%'", null);
	}
	
	public Cursor getIdDone(String msg){
		return db.rawQuery("SELECT * FROM MyRecorder where date like '%"+msg+"%'", null);
	}
	public Cursor getSPDone(String msg){
		return db.rawQuery("SELECT * FROM UserType where tel like '%"+msg+"%'", null);
	}
	public Cursor gethistoryDone(String date,String purpose){
		return db.rawQuery("SELECT * FROM CallHistory where date like '%"+date+"%' and purpose like '%"+purpose+"%'", null);
	}
	public Cursor getLatestId(){
		return db.rawQuery("SELECT id FROM MyRecorder order by id DESC limit 1", null);
	}
	public Cursor getAll() {
	    return db.rawQuery("SELECT * FROM MyRecorder order by id DESC", null);
	}
	public Cursor getAllByType() {
	    return db.rawQuery("SELECT * FROM UserType order by id DESC", null);
	}
	
	public Cursor getAllByHistory() {
	    return db.rawQuery("SELECT * FROM CallHistory order by id DESC", null);
	}
	
	public Cursor getServicesData(String serviecName)throws Exception{
		 return db.rawQuery("SELECT * FROM MyCalllog where serviceName ='"+ serviecName+"' order by id DESC limit 6",null);
	}
	public int deleteAllData(){
		
		try {
			Log.d("SQLITE","deleteAllData:"+String.valueOf(getCount()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 return db.delete("MyCalllog", null, null);
	}
	public int deleteData(String condition){
		return db.delete("MyRecorder", condition, null);
	}
	public int deleteDataByType(String condition){
		return db.delete("UserType", condition, null);
	}
	public Cursor queryDataByCount(int count)throws Exception{
		Cursor cursor = null;
		cursor = db.rawQuery("SELECT * FROM MyCalllog  order by id DESC limit " + count , null);
		//Log.v("SQLITE", "cursor.getCount()"+cursor.getCount());
		return cursor;
	}
	// ���o�@������
	public Cursor get(long rowId) throws SQLException {
		Cursor cursor = db.query(true,
		"table_name",				//��ƪ�W��
		new String[] {"_ID", "name", "value"},	//���W��
		"_ID=" + rowId,				//WHERE
		null, // WHERE ���Ѽ�
		null, // GROUP BY
		null, // HAVING
		null, // ORDOR BY
		null  // ����^�Ǫ�rows�ƶq
		);
 
		// �`�N�G���g�|�X��
		if (cursor != null) {
			cursor.moveToFirst();	//�N���в���Ĥ@�����
		}
		return cursor;
	}
	public long createByType(String tel,String type){
		ContentValues args = new ContentValues();
		args.put("tel", tel);
		args.put("type", type);
		
		return db.insert("UserType", null, args);
	}
	
	//�s�W�@���O���A���\�^��rowID�A���Ѧ^��-1
	public long create(String filename, String date, String name) throws Exception{
		ContentValues args = new ContentValues();
		args.put("filename", filename);
		args.put("date", date);
		args.put("name", name);
		
		return db.insert("MyRecorder", null, args);
    }
	
	//�s�W�@���O���A���\�^��rowID�A���Ѧ^��-1
	public long createHistory(String name, String number,String date,String during,String calltype,String purpose) throws Exception{
		ContentValues args = new ContentValues();
		args.put("name", name);
		args.put("number", number);
		args.put("date", date);
		args.put("during", during);
		args.put("calltype", calltype);
		args.put("purpose", purpose);
			
		return db.insert("CallHistory", null, args);
	 }
	
	//�R���O���A�^�Ǧ��\�R������
	public int delete(long rowId) {
		return db.delete("table_name",	//��ƪ�W��
		"_ID=" + rowId,			//WHERE
		null				//WHERE���Ѽ�
		);
	}
	//�ק�O���A�^�Ǧ��\�קﵧ��
	public int update(long rowId, String value) {
		ContentValues args = new ContentValues();
		args.put("value", value);
 
		return db.update("table_name",	//��ƪ�W��
		args,				//VALUE
		"_ID=" + rowId,			//WHERE
		null				//WHERE���Ѽ�
		);
	}
}
