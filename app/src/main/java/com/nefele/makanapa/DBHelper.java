package com.nefele.makanapa;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Awan on 19/09/2023.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "MakanApa.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create your database tables here
        String createTableQuery = "CREATE TABLE IF NOT EXISTS MasterPelanggan ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "username varchar(200)," +
                "password varchar(200))";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrades here
        db.execSQL("DROP TABLE IF EXISTS MasterPelanggan");
        onCreate(db);
    }
}
