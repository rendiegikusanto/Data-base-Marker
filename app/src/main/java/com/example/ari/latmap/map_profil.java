package com.example.ari.latmap;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.util.HashMap;

public class map_profil extends AppCompatActivity {
    private String JSON_STRING;
    MapView map;
    EditText txt_cari;
    Button btn_cari;
    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_profil);
        map = (MapView) findViewById(R.id.map);

        Intent intent = getIntent();
        id = intent.getStringExtra(Koneksi.TAG_NAMA);

        txt_cari = (EditText) findViewById(R.id.txt_lokasi);
        btn_cari = (Button) findViewById(R.id.btn_cari);

        btn_cari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getJSON();
            }
        });
    }


    private void getJSON(){
        final String lokasi = txt_cari.getText().toString().trim();
        class GetJSON extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(map_profil.this,"Menampilkan Data","Tunggu Sebentar...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showMap(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> nama_lok = new HashMap<>();
                nama_lok.put(Koneksi.KEY_EMP_NAME, lokasi);

                RequestHandler rh = new RequestHandler();
                String s = rh.sendPostRequest(Koneksi.URL_GET_OSM, nama_lok);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    private void showMap(String json){
        if(json !=null){
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray result = jsonObject.getJSONArray(Koneksi.TAG_JSON_ARRAY);
                JSONObject c = result.getJSONObject(0);

                String name = c.getString(Koneksi.TAG_NAMA);
                String latt = c.getString(Koneksi.TAG_LAT);
                String longg = c.getString(Koneksi.TAG_LONGI);

                Double lt = Double.parseDouble(latt);
                Double lgs = Double.parseDouble(longg);

                tampilPeta(lt, lgs, name);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            Toast.makeText(getApplication(),"Pencarian Lokasi Tidak Ditemukan",Toast.LENGTH_LONG).show();
            petaBandarLampung(); //kondisi ini masih belum selesai
        }

    }

    void tampilPeta(Double lts, Double lgs, String marks){
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);

        GeoPoint startPoint = new GeoPoint(lts, lgs);
        IMapController mapController = map.getController();
        mapController.setZoom(15);
        mapController.setCenter(startPoint);

        Marker startMarker = new Marker(map);
        startMarker.setPosition(startPoint);
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        startMarker.setTitle(marks);
        startMarker.setIcon(getResources().getDrawable(R.drawable.person));
        map.getOverlays().add(startMarker);
        map.invalidate();
    }

    void petaBandarLampung(){
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);

        GeoPoint startPoint = new GeoPoint(-5.397140, 105.266789);
        IMapController mapController = map.getController();
        mapController.setZoom(12);
        mapController.setCenter(startPoint);

    }

}