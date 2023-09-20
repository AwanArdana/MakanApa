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

        db.execSQL(createTableQuery);
        db.execSQL(createTableNewTableQuery);
    }

    static String createTableQuery = "CREATE TABLE IF NOT EXISTS MasterPelanggan ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "username varchar(200)," +
            "password varchar(200))";

    static String createTableNewTableQuery = "CREATE TABLE IF NOT EXISTS MasterKategori ("
            + "id int,"
            + "NamaKategori varchar(100))";

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrades here
        db.execSQL("DROP TABLE IF EXISTS MasterPelanggan");
        db.execSQL("DROP TABLE IF EXISTS MasterKategori");
        onCreate(db);
    }
}
