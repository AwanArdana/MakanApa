package com.nefele.makanapa;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Awan on 18/09/2023.
 */
public class WelcomePage extends AppCompatActivity {
    String TAG = "WelcomePage";
    AppCompatButton btn_go;
    Context context;
    SQLiteDatabase db;
    DBHelper dbHelper;
    boolean adaData;
    TextView txt_loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_page);
        context = this;

        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();

        adaData = false;
        new GetKategori().execute("");

        txt_loading = findViewById(R.id.txt_loading);

        btn_go = findViewById(R.id.btn_go);

        btn_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!adaData) {
                    db.execSQL("DELETE FROM MasterPelanggan");
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

    private class GetKategori extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... strings) {
            btn_go.setVisibility(View.GONE);
            txt_loading.setText("Get Kategori...");
            try {
                String a = FungsiUmum.getResponseAPI("https://awanapp.000webhostapp.com/makanapa/getMasterKategori.php");
                Log.i(TAG, "doInBackground: getkategori " + a);
                return a;
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONArray jsonArray = new JSONArray(s);
                db.execSQL("DELETE FROM MasterKategori");
                String value = "";
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String id = jsonObject.getString("id");
                    String NamaKategori = jsonObject.getString("NamaKategori");

                    value += value.equals("")?"('"+id+"','"+NamaKategori+"')":", ('"+id+"','"+NamaKategori+"')";
//                    String
                }
                Log.i(TAG, "onPostExecute: value " + value);
                db.execSQL("INSERT INTO MasterKategori (id,NamaKategori) VALUES "+value);
//                db.execSQL("DELETE FROM MasterPelanggan");
//                    db.execSQL("INSERT INTO MasterPelanggan (username, password) VALUES ('','')");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            cekData(db);
        }
    }

    void cekData(SQLiteDatabase db){
        txt_loading.setText("Get User Local...");
        Cursor cursor = db.rawQuery("SELECT username, password FROM MasterPelanggan", null);
        while (cursor.moveToNext()) {
            adaData = true;
            Log.i(TAG, "cekData: " + cursor.getString(0) + cursor.getString(1));
        }
        Log.i(TAG, "cekData: adaData " + adaData);
        cursor.close();

        if (!adaData) {
            txt_loading.setText("");
            btn_go.setVisibility(View.VISIBLE);
        }else {
            Intent intent = new Intent(WelcomePage.this, MainMenu.class);
            startActivity(intent);

            // Finish the current activity
            finish();
        }
    }
}
