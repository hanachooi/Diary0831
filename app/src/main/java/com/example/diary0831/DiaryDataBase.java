package com.example.diary0831;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DiaryDataBase {
    private static final String TAG = "DiaryDatabase";

    /**
     * Singleton instance
     */
    private static DiaryDataBase database;


    /**
     * database name
     */
    public static String DATABASE_NAME = "itmind.db";

    /**
     * table name for BOOK_INFO
     */
    public static String TABLE_Diary_INFO = "diaryContestDB";

    /**
     * version
     */
    public static int DATABASE_VERSION = 1;


    /**
     * Helper class defined
     */
    private DatabaseHelper dbHelper;

    /**
     * Database object
     */
    private SQLiteDatabase db;


    private Context context;

    /**
     * Constructor
     */
    DiaryDataBase(Context context) {
        this.context = context;
    }


    public static DiaryDataBase getInstance(Context context) {
        if (database == null) {
            database = new DiaryDataBase(context);
        }

        return database;
    }

    /**
     * open database
     */
    public boolean open() {
        println("opening database [" + DATABASE_NAME + "].");

        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();

        return true;
    }

    /**
     * close database
     */
    public void close() {
        println("closing database [" + DATABASE_NAME + "].");
        db.close();
        database = null;
    }

    /**
     * execute raw query using the input SQL
     * close the cursor after fetching any result 아래의 rawquery를 sql 에 넣어서 실행을 한다.
     */
    public Cursor rawQuery(String SQL) {
        println("\nexecuteQuery called.\n");

        Cursor c1 = null;
        try {
            c1 = db.rawQuery(SQL, null);
            println("cursor count : " + c1.getCount());
        } catch(Exception ex) {
            Log.e(TAG, "Exception in executeQuery", ex);
        }

        return c1;
    }

    public boolean execSQL(String SQL) {
        println("\nexecute called.\n");

        try {
            Log.d(TAG, "SQL : " + SQL);
            db.execSQL(SQL);
        } catch(Exception ex) {
            Log.e(TAG, "Exception in executeQuery", ex);
            return false;
        }

        return true;
    }

    private class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase _db) {
            // TABLE_BOOK_INFO 테이블을 만듦
            println("creating table [" + TABLE_Diary_INFO + "].");

            // drop existing table 테이블이 존재하는지를 확인
            String DROP_SQL = "drop table if exists " + TABLE_Diary_INFO;
            try {
                _db.execSQL(DROP_SQL);
            } catch(Exception ex) {
                Log.e(TAG, "Exception in DROP_SQL", ex);
            }

            // create table 테이블을 만들고 테이블이 예외일 경우 log로 내보냄.
            String CREATE_SQL = "create table diaryContestDB (dataDate, diary_content);";
            try {
                _db.execSQL(CREATE_SQL);
            } catch(Exception ex) {
                Log.e(TAG, "Exception in CREATE_SQL", ex);
            }
        }

        public void onOpen(SQLiteDatabase db) {
            println("opened database [" + DATABASE_NAME + "].");

        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            println("Upgrading database from version " + oldVersion + " to " + newVersion + ".");

            if (oldVersion < 2) {   // version 1

            }

        }

        private void insertRecord(SQLiteDatabase _db, String dateData, String diary_content) {
            try {
                String text;
                _db.execSQL( "insert into " + TABLE_Diary_INFO + "(dataDate, diary_content) values ('" + dateData + "', '" + diary_content + "');" );
            } catch(Exception ex) {
                Log.e(TAG, "Exception in executing insert SQL.", ex);
            }
        }

    }

    public void insertRecord(String dateData, String diary_content) {
        try {
            db.execSQL( "insert into " + TABLE_Diary_INFO + "(dataDate, diary_content) values ('" + dateData + "', '" + diary_content + "');" );
        } catch(Exception ex) {
            Log.e(TAG, "Exception in executing insert SQL.", ex);
        }
    }

    private void println(String msg) {
        Log.d(TAG, msg);
    }

}