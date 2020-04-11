package com.example.testjson;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<HashMap<String, String>> markerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        markerList = new ArrayList<>();
        listView = findViewById(R.id.list);

        new GetContacts().execute();

    }

    class GetContacts extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity.this, "Getting JSON", Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler sh = new HttpHandler();
            String url = "https://historical-marker.herokuapp.com/markers";
            String jsonStr = sh.makeServiceCall(url);

            if (jsonStr != null) {
                try {
                    JSONArray markers = new JSONArray(jsonStr);
                    for (int i=0; i<markers.length(); i++) {
                        JSONObject marker = markers.getJSONObject(i);
                        String id = marker.getString("id");
                        String marker_id = marker.getString("marker_id");
                        String title = marker.getString("title");
                        String subtitle1 = marker.getString("subtitle1");
                        String subtitle2 = marker.getString("subtitle2");
                        String year = marker.getString("year");
                        String erected_by = marker.getString("erected_by");
                        String latitude = marker.getString("latitude");
                        String longitude = marker.getString("longitude");
                        String address = marker.getString("address");
                        String town = marker.getString("town");
                        String county = marker.getString("county");
                        String state = marker.getString("state");
                        String location = marker.getString("location");
                        String link = marker.getString("url");
                        String inscription = marker.getString("inscription");

                        HashMap<String, String> m = new HashMap<>();
                        m.put("id", id);
                        m.put("marker_id", marker_id);
                        m.put("title", title);
                        m.put("subtitle1", subtitle1);
                        m.put("subtitle2", subtitle2);
                        m.put("year", year);
                        m.put("erected_by", erected_by);
                        m.put("latitude", latitude);
                        m.put("longitude", longitude);
                        m.put("address", address);
                        m.put("town", town);
                        m.put("county", county);
                        m.put("state", state);
                        m.put("location", location);
                        m.put("url", link);
                        m.put("inscription", inscription);
                        markerList.add(m);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.i("message", "can't get the json from the server");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ListAdapter listAdapter = new SimpleAdapter(MainActivity.this, markerList, R.layout.list_item, new String[]{"title", "latitude", "longitude"}, new int[]{R.id.marker_title, R.id.latitude, R.id.longitude});
            listView.setAdapter(listAdapter);
        }
    }



}
