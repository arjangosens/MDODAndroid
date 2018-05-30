package com.project.avans.mdodandroid.adapters.GoalAdapter;

import android.os.AsyncTask;
import android.util.Log;

import com.project.avans.mdodandroid.MainActivity;
import com.project.avans.mdodandroid.adapters.RiskAdapter.RiskListener;
import com.project.avans.mdodandroid.object_classes.Goal;
import com.project.avans.mdodandroid.object_classes.Risk;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class AsyncGoal extends AsyncTask<String, Void, String> {
    private GoalListener listener = null;


    public AsyncGoal(GoalListener listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... params) {
        InputStream inputStream = null;
        int responseCode = -1;
        String RiskUrl = params[0];
        String response = "";

        try{
            URL url = new URL(RiskUrl);
            URLConnection urlConnection = url.openConnection();

            if(!(urlConnection instanceof HttpURLConnection)){
                return null;
            }

            HttpURLConnection httpConnection = (HttpURLConnection) urlConnection;
            httpConnection.setAllowUserInteraction(false);
            httpConnection.setInstanceFollowRedirects(true);
            httpConnection.setRequestMethod("GET");
            httpConnection.setRequestProperty("Content-Type", "application/" + "json");
            httpConnection.setRequestProperty("authorization", "Bearer " + MainActivity.Token);
            httpConnection.connect();
            responseCode = httpConnection.getResponseCode();

            if(responseCode == HttpURLConnection.HTTP_OK){
                inputStream = httpConnection.getInputStream();
                response = getStringFromInputStream(inputStream);
            }else{
                Log.e("connection", "ERROR, Invalid response");
            }
        } catch (MalformedURLException e) {
            Log.e("connectionurl", "doInBackground MalformedURLExeption " + e.getLocalizedMessage());
            return null;
        } catch (IOException e) {
            Log.e("internet", "doInBackground IOException " + e.getLocalizedMessage());
            return null;
        }
        return response;
    }

    protected void onPostExecute(String response){
        if(response == null || response.equals("")){
            Log.e("onpost", "onPostExecute() kreeg een lege repsonse");
            return;
        }

        JSONArray jsonObject;
        try {
            jsonObject = new JSONArray(response);

            for(int i=0; i< jsonObject.length(); i++){
                JSONObject RiskJson = jsonObject.getJSONObject(i);
                String goal = RiskJson.getString("name");
                String goalID = RiskJson.getString("id");
//                System.out.println(RiskID);

                com.project.avans.mdodandroid.object_classes.Goal rp = new Goal(goalID, goal);
                listener.onGoalListener(rp);
            }
        } catch (JSONException e) {
            Log.e("json ex", e.getLocalizedMessage());
        }
    }

    private String getStringFromInputStream(InputStream inputStream) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            br = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
}
