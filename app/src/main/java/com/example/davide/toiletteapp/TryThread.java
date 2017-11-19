package com.example.davide.toiletteapp;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class TryThread {

    private static String urlName = "http://6a337b13.ngrok.io/api/";
    private static String myId;


    static public void setStatusEntry(String name){

        URL url = null;
        BufferedWriter writer = null;
        StringBuilder stringBuilder;

        try {
            // create the HttpURLConnection
            url = new URL(urlName +"cesso");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // just want to do an HTTP  here
            connection.setRequestMethod("POST");

            // uncomment this if you want to write output to this url
            connection.setDoOutput(true);

            // give it 15 seconds to respond
            connection.setReadTimeout(15 * 1000);
            connection.connect();

            // read the output from the server
            writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            JSONObject request = new JSONObject();
            request.put("username", name);
            request.put("status", "busy");
            writer.write(request.toString());

            String line = null;
            stringBuilder = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
            JSONObject jsonObject = new JSONObject(stringBuilder.toString());
            myId = jsonObject.getString("id");


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // close the reader; this can throw an exception too, so
            // wrap it in another try/catch block.
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
    }

    static public String getStatus() {
        URL url = null;
        BufferedReader reader = null;
        StringBuilder stringBuilder;

        try {
            // create the HttpURLConnection
            url = new URL(urlName +"cesso?status=busy");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // just want to do an HTTP GET here
            connection.setRequestMethod("GET");

            // give it 15 seconds to respond
            connection.setReadTimeout(15 * 1000);
            connection.connect();

            // read the output from the server
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            stringBuilder = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }


            JSONArray jsonArray = new JSONArray(stringBuilder.toString());
            String stronzo = jsonArray.getJSONObject(0).getString("username");

            return stronzo;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // close the reader; this can throw an exception too, so
            // wrap it in another try/catch block.
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
        return null;
    }

    public static void setStatusExit(String name){
        URL url = null;
        BufferedWriter writer = null;
        StringBuilder stringBuilder;

        try {
            // create the HttpURLConnection
            url = new URL(urlName +"cesso/"+myId);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // just want to do an HTTP  here
            connection.setRequestMethod("POST");

            // uncomment this if you want to write output to this url
            connection.setDoOutput(true);

            // give it 15 seconds to respond
            connection.setReadTimeout(15 * 1000);
            connection.connect();

            // read the output from the server
            writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));

            JSONObject request = new JSONObject();
            request.put("status", "free");

            writer.write(request.toString());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // close the reader; this can throw an exception too, so
            // wrap it in another try/catch block.
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
    }
}
