package com.naomitaub.standuptest;

import android.provider.BaseColumns;

/**
 * Created by Naomi on 3/22/2015.
 */
public class MyConstants {

    public MyConstants() {

    }


    public static abstract class Constants implements BaseColumns {
        public static final String DATABASE_NAME = "Stand_Up_Database";
        public static final int DATABASE_VERSION = 2;

        public static final String ACCOUNT_INFO_TABLE = "Account_Info_Table";
        public static final String KEY_ID = "_id";
        public static final String USERNAME = "username";
        public static final String PASSWORD = "password";

        public static final String USER_INFO_TABLE = "User_Info_Table";
        public static final String FIRST_NAME = "firstName";
        public static final String LAST_NAME = "lastName";
        public static final String BIRTHDATE = "birthdate";
        public static final String AGE = "age";
        public static final String GENDER = "gender";

        public static final String USER_HR_RECORDS_TABLE = "User_HR_Records_Table";
        public static final String RECORD_DATE = "recordDate";
        public static final String RECORD_NAME = "recordName";

        public static final String RECORDS_DETAILS_TABLE = "Records_Details_Table";
        public static final String LOWEST_BPM = "lowest_bpm";
        public static final String HIGHEST_BPM = "highest_bpm";
        public static final String RANGE_BPM = "range_bpm";
        public static final String AVG_BPM = "avg_bpm";
        public static final String NOTES = "notes";
    }
}