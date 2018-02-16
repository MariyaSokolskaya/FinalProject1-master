package com.example.pasha.finalproject1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "contact.db";
    public static final String TABLE_CONTACTS = "contacts";
    public static final String KEY_ID = "_id";
    public static final String KEY_NAZVANIE = "nazvanie";

    public static final String KEY_MESTO = "mesto";
    public static final String KEY_TIME = "time";
    public static final String KEY_DATA = "data";


    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    //определяются поля для таблицы
    public DBHelper(Context context) { super(context,DATABASE_NAME, null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable ="create table " + TABLE_CONTACTS + " ( " + KEY_ID + " integer primary key autoincrement, " +KEY_DATA + " text," +
        KEY_NAZVANIE + " text, " + KEY_MESTO + " text, " + KEY_TIME + " text " + " ) ";
//создаётся таблица
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_CONTACTS);
        onCreate(db);
    }
}
