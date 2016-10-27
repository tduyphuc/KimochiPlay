package com.example.phuctdse61834.kimochiplay;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by PhucTDSE61834 on 10/26/2016.
 */

public class JSONHandler {

    public JSONHandler() {
    }

    public String getJSONStringFromService(String requestURL){
        String result = null;
        try{
            URL url = new URL(requestURL);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            //read and convert
            InputStream stream = new BufferedInputStream(con.getInputStream());
            result = convertStreamToString(stream);
        }catch (MalformedURLException e) {
            Log.e("Handler", "MalformedURLException: " + e.getMessage());
        } catch (IOException e) {
            Log.e("Handler", "IOException: " + e.getMessage());
        } catch (Exception e){
            Log.e("Handler", "Exception: " + e.getMessage());
        }
        return result;
    }

    private String convertStreamToString(InputStream stream){
        BufferedReader bf = new BufferedReader(new InputStreamReader(stream));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try{
            while ((line = bf.readLine()) != null){
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try{
                stream.close();
                bf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
