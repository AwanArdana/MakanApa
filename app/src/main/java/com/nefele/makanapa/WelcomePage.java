package com.nefele.makanapa;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;


/**
 * Created by Awan on 18/09/2023.
 */
public class WelcomePage extends AppCompatActivity {
    String TAG = "WelcomePage";
    AppCompatButton btn_go;
    Context context;
    boolean adaData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_page);
        context = this;

        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        adaData = false;
        cekData(db);

        btn_go = findViewById(R.id.btn_go);

        btn_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!adaData) {
                    db.execSQL("INSERT INTO MasterPelanggan (username, password) VALUES ('','')");
                    Log.i(TAG, "onClick: insertData");
//                    ContentValues values = new ContentValues();
//                    values.put("user", "user");
//                    long newRowId = db.insert("MasterPelanggan", null, values);

                }
                Intent intent = new Intent(WelcomePage.this, MainMenu.class);
                startActivity(intent);

                // Finish the current activity
                finish();
            }
        });
    }

    void cekData(SQLiteDatabase db){
        Cursor cursor = db.rawQuery("SELECT username, password FROM MasterPelanggan", null);
        while (cursor.moveToNext()) {
            adaData = true;
            Log.i(TAG, "cekData: " + cursor.getString(0) + cursor.getString(1));
            Intent intent = new Intent(WelcomePage.this, MainMenu.class);
            startActivity(intent);

            // Finish the current activity
            finish();
        }
        Log.i(TAG, "cekData: adaData " + adaData);
        cursor.close();
    }
}
