package com.nefele.makanapa;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Awan on 18/09/2023.
 */
public class MainMenu extends AppCompatActivity {
    String TAG = "MainMenu";
    Context context;
    DBHelper dbHelper;
    SQLiteDatabase db;
    boolean adaData;
    private TextView textViewResult;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private static AdapterCabang adapterCabang;
    private ArrayList<ItemCabang> itemCabangs = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        context = this;
        adaData = false;
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.white));

        textViewResult = findViewById(R.id.textViewResult);
        listView = findViewById(R.id.listView);
//        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);



        new FetchDataTask().execute("https://awanapp.000webhostapp.com/makanapa/getMasterCabang.php");
        testGetuser();
    }

    void testGetuser(){
        Cursor cursor = db.rawQuery("SELECT username, password FROM MasterPelanggan", null);
        while (cursor.moveToNext()) {
            adaData = true;
            Log.i(TAG, "cekData: " + cursor.getString(0) + cursor.getString(1));
            Toast.makeText(context, cursor.getString(0) +";"+ cursor.getString(1), Toast.LENGTH_SHORT).show();
        }
//        Toast.makeText(context, "data " + adaData + " ;" , Toast.LENGTH_SHORT).show();
        cursor.close();
    }

    private class FetchDataTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            try {
                String apiUrl = urls[0];

                URL url = new URL(apiUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                int responseCode = connection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();
                    return response.toString();
                } else {
                    return null; // Handle the error, e.g., by showing a message to the user
                }

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String jsonData) {
            if (jsonData != null) {
                parseJsonData(jsonData);
            } else {
                // Handle the case where jsonData is null (error occurred)
            }
        }
    }

    private void parseJsonData(String jsonData) {
        try {
            JSONArray jsonArray = new JSONArray(jsonData);

//            StringBuilder resultText = new StringBuilder();

            listView.setVisibility(View.GONE);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                // Parse the data from the JSON object and add it to your list
                String Nama = jsonObject.getString("Nama");
                String Cabang = jsonObject.getString("Cabang");
                String Cover = jsonObject.getString("Cover");
                // Add more data fields as needed

//                resultText.append(itemName).append("\n");
                // You can append more data fields to resultText as needed
//                adapter.add(Nama);
                itemCabangs.add(new ItemCabang(Cabang, Nama, Cover));
            }
            adapterCabang = new AdapterCabang(context, itemCabangs);
            listView.setAdapter(adapterCabang);
            listView.setVisibility(View.VISIBLE);

            // Update UI with the fetched data
//            textViewResult.setText(resultText.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
