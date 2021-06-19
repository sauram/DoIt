package com.example.doit;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DatabaseClass extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "DatabaseClass.db";

    public static final String DATA_TABLE_NAME = "data";
    public static final String DATA_COLUMN_ID = "id";
    public static final String DATA_COLUMN_TITLE = "title";
    public static final String DATA_COLUMN_DESCRIPTION = "description";
    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + DATA_TABLE_NAME + " (" +
            DATA_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DATA_COLUMN_TITLE+ " TEXT, " +
            DATA_COLUMN_DESCRIPTION+ " TEXT "+ ")";
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DATA_TABLE_NAME;

    public DatabaseClass(@Nullable Context context) {
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
