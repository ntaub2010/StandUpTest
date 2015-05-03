package com.naomitaub.standuptest;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Naomi on 3/22/2015.
 */
public class DatabaseOperations extends SQLiteOpenHelper {

    public DatabaseOperations (Context context) {
        super(context, MyConstants.Constants.DATABASE_NAME, null, MyConstants.Constants.DATABASE_VERSION);
    }

    public static String CREATE_ACCOUNT_QUERY = "CREATE TABLE " + MyConstants.Constants.ACCOUNT_INFO_TABLE +
            "(" + MyConstants.Constants.USERNAME + " TEXT, " + MyConstants.Constants.PASSWORD + " TEXT);";
//create table User_Info_Table(username TEXT, password TEXT);
    /* MyConstants.Constants.FIRST_NAME + "TEXT," + MyConstants.Constants.LAST_NAME + "TEXT," +
            MyConstants.Constants.BIRTHDATE + "TEXT," + MyConstants.Constants.AGE + "INTEGER," +
            MyConstants.Constants.GENDER + "TEXT */

    public static String CREATE_USER_INFO_QUERY = "CREATE TABLE " + MyConstants.Constants.USER_INFO_TABLE +
            "(" + MyConstants.Constants.FIRST_NAME + " TEXT, " + MyConstants.Constants.LAST_NAME + " TEXT, " +
            MyConstants.Constants.BIRTHDATE + " TEXT, " + MyConstants.Constants.AGE + " INTEGER, " +
            MyConstants.Constants.GENDER + " TEXT);";

    public static String CREATE_RECORDS_QUERY = "CREATE TABLE " + MyConstants.Constants.USER_HR_RECORDS_TABLE +
            "(" + MyConstants.Constants.TEST_TYPE + " TEXT, " + MyConstants.Constants.RECORD_DATE + " TEXT, " +
            MyConstants.Constants.RECORD_TIME + " TEXT, " + MyConstants.Constants.LOWEST_BPM + " INTEGER, " +
            MyConstants.Constants.HIGHEST_BPM + " INTEGER, " + MyConstants.Constants.RANGE_BPM + " INTEGER, " +
            MyConstants.Constants.RECORDS_BLURB + " TEXT);";



    @Override
    public void onCreate(SQLiteDatabase sdb) {

        sdb.execSQL(CREATE_ACCOUNT_QUERY);
        Log.i("Database operations", "Account Info Table created");

        sdb.execSQL(CREATE_USER_INFO_QUERY);
        Log.i("Database operations", "User Info Table created");

        sdb.execSQL(CREATE_RECORDS_QUERY);
        Log.i("Database operations", "Records Table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

    }
//, String fName, String lName, String bdate, Integer age, String gender
    public void putAccountInfo(DatabaseOperations dop, String userName, String password) {

        SQLiteDatabase SQ = dop.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(MyConstants.Constants.USERNAME, userName);
        cv.put(MyConstants.Constants.PASSWORD, password);
        /*cv.put(MyConstants.Constants.FIRST_NAME, fName);
        cv.put(MyConstants.Constants.LAST_NAME, lName);
        cv.put(MyConstants.Constants.BIRTHDATE, bdate);
        cv.put(MyConstants.Constants.AGE, age);
        cv.put(MyConstants.Constants.GENDER, gender);*/

        long k = SQ.insert(MyConstants.Constants.ACCOUNT_INFO_TABLE, null, cv);
        Log.i("Database operations", "ACCT - One row inserted");

    }

    public void putUserInfo(DatabaseOperations dop, String fName, String lName, String bDate, int age, String gender) {

        SQLiteDatabase SQ = dop.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(MyConstants.Constants.FIRST_NAME, fName);
        cv.put(MyConstants.Constants.LAST_NAME, lName);
        cv.put(MyConstants.Constants.BIRTHDATE, bDate);
        cv.put(MyConstants.Constants.AGE, age);
        cv.put(MyConstants.Constants.GENDER, gender);

        long k = SQ.insert(MyConstants.Constants.USER_INFO_TABLE, null, cv);
        Log.i("Database operations", "USERINFO - One row inserted");
    }

    public void putRecordInfo(DatabaseOperations dop, String rType, String rDate, String rTime, int rLow, int rHigh, int rRange, String rBlurb) {

        SQLiteDatabase SQ = dop.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(MyConstants.Constants.TEST_TYPE, rType);
        cv.put(MyConstants.Constants.RECORD_DATE, rDate);
        cv.put(MyConstants.Constants.RECORD_TIME, rTime);
        cv.put(MyConstants.Constants.LOWEST_BPM, rLow);
        cv.put(MyConstants.Constants.HIGHEST_BPM, rHigh);
        cv.put(MyConstants.Constants.RANGE_BPM, rRange);
        cv.put(MyConstants.Constants.RECORDS_BLURB, rBlurb);

        long k = SQ.insert(MyConstants.Constants.USER_HR_RECORDS_TABLE, null, cv);
        Log.i("Database operations", "RECORDS - One row inserted");
    }

    public Cursor getLoginInfo(DatabaseOperations dop) {
        SQLiteDatabase SQ = dop.getReadableDatabase();
        String[] columns = {MyConstants.Constants.USERNAME, MyConstants.Constants.PASSWORD};
        Cursor CR = SQ.query(MyConstants.Constants.ACCOUNT_INFO_TABLE, columns, null, null, null, null, null);
        return CR;
    }

    public Cursor getRecordsInfo(DatabaseOperations dop) {
        SQLiteDatabase SQ = dop.getReadableDatabase();
        String[] columns = {MyConstants.Constants.TEST_TYPE, MyConstants.Constants.RECORD_DATE, MyConstants.Constants.RECORD_TIME};
        Cursor CR = SQ.query(MyConstants.Constants.USER_HR_RECORDS_TABLE, columns, null, null, null, null, null);
        return CR;
    }
}
