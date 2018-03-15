package com.example.c.scanapp;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.HandlerThread;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by C on 3/10/2018.
 */

public class CheckProduct extends AsyncTask<String,Void,String>{

    Context context;
    AlertDialog alertDialog;

    public AsyncResponse delegate = null;


    CheckProduct (AsyncResponse text){
        delegate = text;
    }

    @Override
    protected String doInBackground(String... params) {
        String action = params[0];
        // if scanning bar code is to get product information
        if(action.equals("getData")){
            try {
                String scanResult = params[1];
                //save the info into JSON format
                JSONObject obj = new JSONObject();
                try {
                    obj.put("scanCode", Integer.parseInt(scanResult));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //open URL connection
                URL url = new URL("http://34.208.5.219/Ensemble/checkProduct");
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setDoInput(true);

                //past scanCode into POST request and send it
                OutputStream outputStream = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                //String postData = URLEncoder.encode("scanCode","UTF-8")+"="+URLEncoder.encode(scanResult,"UTF-8");
                writer.write(obj.toString());
                writer.flush();
                writer.close();
                outputStream.close();

                //Receive result from API call and save result
                InputStream input = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input,"iso-8859-1"));
                String inputResult = "";
                String line = "";
                while((line = reader.readLine())!= null){
                    inputResult += line;
                }

                //close reader and connection
                reader.close();
                input.close();
                connection.disconnect();
                return inputResult;
            }catch (MalformedURLException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        if(action.equals("update")){
            try {

                //modify information into JSON format
                String name = "{\"ProductName\":"+"\""+params[1]+"\",";
                String amount = "\"ProductAmount\":"+params[2]+",";
                String price = "\"ProductPrice\":"+"\""+params[3]+"\",";
                String code = "\"ProductCode\":"+params[4]+"}";
                String overAll = name + amount + price + code;
                JSONObject obj = new JSONObject(overAll);

                //open URL connection
                URL url = new URL("http://34.208.5.219/Ensemble/updateProduct");
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setDoInput(true);

                //past scanCode into POST request and send it
                OutputStream outputStream = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                //String postData = URLEncoder.encode("scanCode","UTF-8")+"="+URLEncoder.encode(scanResult,"UTF-8");
                writer.write(obj.toString());
                writer.flush();
                writer.close();
                outputStream.close();

                //Receive result from API call and save result
                InputStream input = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input,"iso-8859-1"));
                String inputResult = "";
                String line = "";
                while((line = reader.readLine())!= null){
                    inputResult += line;
                }

                //close reader and connection
                reader.close();
                input.close();
                connection.disconnect();
                return inputResult;
            }catch (MalformedURLException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String value){
        delegate.processFinish(value);
    }

    @Override
    protected void onProgressUpdate(Void... values){
        super.onProgressUpdate(values);
    }
}

