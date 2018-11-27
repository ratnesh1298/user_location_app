package com.example.ratnesh.isroproject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAdapter extends SQLiteOpenHelper {
	static String name = "isrodb.db";
	static String path = "";
	static ArrayList<Structure> a;
	static SQLiteDatabase sdb;

	@Override
	public void onCreate(SQLiteDatabase db) { 
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	private DBAdapter(Context v) {
		super(v, name, null, 1);
		path = "/data/data/" + v.getApplicationContext().getPackageName()
				+ "/databases";
	}

	public boolean checkDatabase() {
		SQLiteDatabase db = null;
		try {
			db = SQLiteDatabase.openDatabase(path + "/" + name, null,
					SQLiteDatabase.OPEN_READONLY);
		} catch (Exception e) {

		}
		if (db == null) {
			
			return false;
		} else {
			db.close();
			return true;
		}
	}

	public static synchronized DBAdapter getDBAdapter(Context v) {
		return (new DBAdapter(v));
	}

	public void createDatabase(Context v) {
		this.getReadableDatabase();
		try {
			InputStream myInput = v.getAssets().open(name);
		    // Path to the just created empty db
		String outFileName = path +"/"+ name;
		    // Open the empty db as the output stream
		OutputStream myOutput = new FileOutputStream(outFileName);
		    // transfer bytes from the inputfile to the outputfile
		byte[] bytes = new byte[1024];
		int length;
		while ((length = myInput.read(bytes)) > 0) {
			myOutput.write(bytes, 0, length);
		}
		    // Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();
			
		/*	
			
			
			
			InputStream is = v.getAssets().open("quiz.sqlite");
			// System.out.println(is.available());
			System.out.println(new File(path + "/" + name).getAbsolutePath());
			FileOutputStream fos = new FileOutputStream(path + "/" + name);
			int num = 0;
			while ((num = is.read()) > 0) {
				fos.write((byte) num);
			}
			fos.close();
			is.close();*/
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	public void openDatabase() {
		try {
			sdb = SQLiteDatabase.openDatabase(path + "/" + name, null,
					SQLiteDatabase.OPEN_READWRITE);
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
public void  insertDB(String Name ,double lati, double longi)
{
	ContentValues cv= new ContentValues();
	cv.put("Name",Name);
	cv.put("latitude",lati);
	cv.put("longitude",longi);
	sdb.insert("latlong", null, cv);
}
	public ArrayList<Structure> getData(String Name ) {
		Cursor c1 = sdb.rawQuery("select * from latlong where Name=\""+Name+"\"", null);
		a = new ArrayList<Structure>();
		while (c1.moveToNext()) {
			Structure q1 = new Structure();
			q1.setName(c1.getString(0));
			q1.setLat1(c1.getDouble(1));
			q1.setLong1(c1.getDouble(2));
			a.add(q1);
		}
		return a; 
	}


}
