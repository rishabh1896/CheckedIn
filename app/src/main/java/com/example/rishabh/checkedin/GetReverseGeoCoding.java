package com.example.rishabh.checkedin;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.provider.Settings;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONObject;



public class GetReverseGeoCoding extends AsyncTask<String, Void, String> {
    Double lat,lon;
    ProgressDialog mProgress;
    Context context;
    GetReverseGeoCoding(Context context)
    {
        this.context=context;
        GPSTracker gps = new GPSTracker(context);
        if (gps.canGetLocation()) {
            lat = gps.getLatitude();
            lon = gps.getLongitude();
        } else {
            gps.showSettingAlert();
        }

    }

    private String  City = "";


    public String getCity() {
        return City;

    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Showing progress dialog

        mProgress = new ProgressDialog(context);
        mProgress.setMessage("Loading...");
        mProgress.setCancelable(true);
        mProgress.show();

    }
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        // Dismiss the progress dialog
        if (mProgress.isShowing()) {
            mProgress.dismiss();
        }

    }
    @Override
    protected void onCancelled() {

        super.onCancelled();
        mProgress.dismiss();

    }

    @Override
    protected String doInBackground(String... params) {

        System.out.println("Hi i'm in the addd");
        City = "";
        try {

            JSONObject jsonObj = parser_Json.getJSONfromURL("http://maps.googleapis.com/maps/api/geocode/json?latlng=" + lat + ","
                    + lon + "&sensor=true");
            System.out.println("Latitude"+ lat);
            String Status = jsonObj.getString("status");
            System.out.println("STatus"+Status);
            if (Status.equalsIgnoreCase("OK")) {
                JSONArray Results = jsonObj.getJSONArray("results");
                JSONObject zero = Results.getJSONObject(0);
                JSONArray address_components = zero.getJSONArray("address_components");

                for (int i = 0; i < address_components.length(); i++) {
                    JSONObject zero2 = address_components.getJSONObject(i);
                    String long_name = zero2.getString("long_name");
                    JSONArray mtypes = zero2.getJSONArray("types");
                    String Type = mtypes.getString(0);

                    if (TextUtils.isEmpty(long_name) == false || !long_name.equals(null) || long_name.length() > 0 || long_name != "") {

                          if (Type.equalsIgnoreCase("locality")) {
                              // Address2 = Address2 + long_name + ", ";
                              City = long_name;
                              System.out.println("City Name =" + City);
                          }
                    }

                    // JSONArray mtypes = zero2.getJSONArray("types");
                    // String Type = mtypes.getString(0);
                    // Log.e(Type,long_name);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return City;
    }
}