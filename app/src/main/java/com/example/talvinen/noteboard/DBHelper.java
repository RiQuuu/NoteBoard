package com.example.talvinen.noteboard;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "crud.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_NOTE = "CREATE TABLE " + Note.TABLE + "("
                + Note.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Note.KEY_text + "TEXT, "
                + Note.KEY_text2 + "TEXT, "
                + Note.KEY_text3 + "TEXT, "
                + Note.KEY_text4 + "TEXT )";

        db.execSQL(CREATE_TABLE_NOTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST " + Note.TABLE);
        onCreate(db);
    }
}
