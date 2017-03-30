package com.example.rishabh.checkedin;

import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class parser_Json {
    public static JSONObject getJSONfromURL(String url) {

        // initialize
        InputStream in = null;
        String result = "";
        JSONObject jObject = null;
        HttpURLConnection urlConnection;
        // http post
        try {

           /* HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();*/
            URL ur = new URL(url);
            urlConnection = (HttpURLConnection) ur.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream());

          //  BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        } catch (Exception e) {
            Log.e("log_tag", "Error in http connection " + e.toString());
        }

        // convert response to string
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
                System.out.println("FFFFF"+line);
            }
            in.close();
            result = sb.toString();
        } catch (Exception e) {
            Log.e("log_tag", "Error converting result " + e.toString());
        }

        // try parse the string to a JSON object
        try {
            jObject = new JSONObject(result);
        } catch (JSONException e) {
            Log.e("log_tag", "Error parsing data " + e.toString());
        }

        return jObject;
    }

}