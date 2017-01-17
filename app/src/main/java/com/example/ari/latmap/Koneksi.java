package com.example.ari.latmap;

/**
 * Created by Ari on 1/15/2017.
 */

public class Koneksi {
    public static final String URL_GET_ALL = "http://probalam.com/probal_server/tampil_hotel.php";
    public static final String URL_GET_OSM = "http://probalam.com/probal_server/get_data.php?nama_lokasi=";


    //Keys that will be used to send the request to php scripts
    public static final String KEY_EMP_ID = "Id_lokasi";
    public static final String KEY_EMP_NAME = "nama_lokasi";
    public static final String KEY_EMP_LAT = "latitude";
    public static final String KEY_EMP_LONGI= "longitude";
    //public static final String KEY_EMP_ALAMAT= "alamat";
   // public static final String KEY_EMP_PARAM= "data_parameter";

    //JSON Tags
    public static final String TAG_JSON_ARRAY="result";
    public static final String TAG_ID = "Id_lokasi";
    public static final String TAG_NAMA= "nama_lokasi";
    public static final String TAG_LAT= "latitude";
    public static final String TAG_LONGI = "longitude";
   // public static final String TAG_ALAMAT = "alamat";
    //public static final String TAG_PARAM = "data_parameter";

    //employee id to pass with intent
    public static final String OSM_ID = "osm_id";
}
