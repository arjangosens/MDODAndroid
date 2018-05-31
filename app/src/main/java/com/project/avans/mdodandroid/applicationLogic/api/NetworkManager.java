package com.project.avans.mdodandroid.applicationLogic.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.project.avans.mdodandroid.MainActivity;
import com.project.avans.mdodandroid.object_classes.UserSettingsType;
import com.project.avans.mdodandroid.object_classes.Risk;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NetworkManager
{
    private static final String TAG = "NetworkManager";
    private static NetworkManager instance = null;

    private static final String prefixURL = "https://mdod.herokuapp.com/api/";

    //for Volley API
    public RequestQueue requestQueue;

    private NetworkManager(Context context)
    {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        //other stuf if you need
    }

    public static synchronized NetworkManager getInstance(Context context)
    {
        if (null == instance)
            instance = new NetworkManager(context);
        return instance;
    }

    //this is so you don't need to pass context each time
    public static synchronized NetworkManager getInstance()
    {
        if (null == instance)
        {
            throw new IllegalStateException(NetworkManager.class.getSimpleName() +
                    " is not initialized, call getInstance(...) first");
        }
        return instance;
    }

    public void loginClient(Object email, Object password, final VolleyListener<String> listener)
    {

        String url = prefixURL + "login/client";

        Map<String, Object> jsonParams = new HashMap<>();
        jsonParams.put("email", email);
        jsonParams.put("password", password);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonParams),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        Log.d(TAG + ": ", "login/client Response : " + response.toString());
                        if(null != response.toString())
                            listener.getResult(response.toString());
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        if (null != error.networkResponse)
                        {
                            Log.d(TAG + ": ", "Error Response code: " + error.networkResponse.statusCode);
                            listener.getResult("");
                        }
                    }
                });

        requestQueue.add(request);
    }

    public void getClient(final VolleyListener<JSONArray> listener) {

        String url = prefixURL + "client";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response)
                    {
                        Log.d(TAG + ": ", "GET client Response : " + response.toString());
                        if(null != response.toString())
                            listener.getResult(response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        if (null != error.networkResponse)
                        {
                            Log.d(TAG + ": ", "Error Response code: " + error.networkResponse.statusCode);
                            listener.getResult(null);

                        }
                    }
                }) {@Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            Map<String, String> params = new HashMap<String, String>();
            Log.i(TAG, "Mainactivity.Token = " + MainActivity.Token);
            params.put("Authorization", "Bearer " + MainActivity.Token);
            params.put("X-Access-Token", MainActivity.Token);
            params.put("Content-Type", "application/json");

            return params;
        }};

        requestQueue.add(request);
    }

    public void updateClient(String firstname, String infix, String lastname, String phonenumber,
                             String birthday, String city, String adress, String zipcode, final VolleyListener<JSONObject> listener) {

        String url = prefixURL + "client";

    public void postGoal(String description, final VolleyListener<JSONObject> listener) {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, new JSONObject(jsonParams),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        Log.d(TAG + ": ", "PUT client Response : " + response.toString());
                        if(null != response.toString())
                            listener.getResult(response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        if (null != error.networkResponse)
                        {
                            Log.d(TAG + ": ", "Error Response code: " + error.networkResponse.statusCode);
                            listener.getResult(null);

                        }
                    }
                }) {@Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            Map<String, String> params = new HashMap<String, String>();
            Log.i(TAG, "Mainactivity.Token = " + MainActivity.Token);
            params.put("Authorization", "Bearer " + MainActivity.Token);
            params.put("X-Access-Token", MainActivity.Token);
            params.put("Content-Type", "application/json");

            return params;
        }};

        requestQueue.add(request);

    }

    public void deleteClient(final VolleyListener<JSONObject> listener) {
        String url = prefixURL + "client";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        Log.d(TAG + ": ", "DELETE client Response : " + response.toString());
                        if(null != response.toString())
                            listener.getResult(response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        if (null != error.networkResponse)
                        {
                            Log.d(TAG + ": ", "Error Response code: " + error.networkResponse.statusCode);
                            listener.getResult(null);
                        }
                    }
                }){@Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            Map<String, String> params = new HashMap<String, String>();
            Log.i(TAG, "Mainactivity.Token = " + MainActivity.Token);
            params.put("Authorization", "Bearer " + MainActivity.Token);
            params.put("X-Access-Token", MainActivity.Token);
            params.put("Content-Type", "application/json");

            return params;
        }};
        requestQueue.add(request);
    }

    public void putGoal(String goalId, String description, final VolleyListener<JSONObject> listener) {

        String url = prefixURL + "v1/goal/" + goalId ;

        Map<String, Object> jsonParams = new HashMap<>();
        jsonParams.put("description", description);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, new JSONObject(jsonParams),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        Log.d(TAG + ": ", "putGoal Response : " + response.toString());
                        if(null != response.toString())
                            listener.getResult(response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        if (null != error.networkResponse)
                        {
                            Log.d(TAG + ": ", "Error Response code: " + error.networkResponse.statusCode);
                            listener.getResult(null);
                        }
                    }
                }){@Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            Map<String, String> params = new HashMap<String, String>();
            Log.i(TAG, "Mainactivity.Token = " + MainActivity.Token);
            params.put("Authorization", "Bearer " + MainActivity.Token);
            params.put("X-Access-Token", MainActivity.Token);
            params.put("Content-Type", "application/json");

            return params;
        }};
        requestQueue.add(request);
    }


    public void postRisk(String description, final VolleyListener<JSONObject> listener) {

        String url = prefixURL + "v1/risk";

        Map<String, Object> jsonParams = new HashMap<>();
        jsonParams.put("description", description);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonParams),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        Log.d(TAG + ": ", "post risk Response : " + response.toString());
                        if(null != response.toString())
                            listener.getResult(response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        if (null != error.networkResponse)
                        {
                            Log.d(TAG + ": ", "Error Response code: " + error.networkResponse.statusCode);
                            listener.getResult(null);
                        }
                    }
                }){@Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            Map<String, String> params = new HashMap<String, String>();
            Log.i(TAG, "Mainactivity.Token = " + MainActivity.Token);
            params.put("Authorization", "Bearer " + MainActivity.Token);
            params.put("X-Access-Token", MainActivity.Token);
            params.put("Content-Type", "application/json");

            return params;
        }};
        requestQueue.add(request);
    }


    public void updateRisk(String description, Risk risk, final VolleyListener<String> listener) {

        String url = prefixURL + "v1/risk/" + risk.getRiskID();

        Map<String, Object> jsonParams = new HashMap<>();
        jsonParams.put("description", description);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, new JSONObject(jsonParams),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        Log.d(TAG + ": ", "post risk Response : " + response.toString());
                        if(null != response.toString())
                            listener.getResult(response.toString());
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        if (null != error.networkResponse)
                        {
                            Log.d(TAG + ": ", "Error Response code: " + error.networkResponse.statusCode);
                            listener.getResult(null);

                        }
                    }
                }) {@Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            Map<String, String> params = new HashMap<String, String>();
            Log.i(TAG, "Mainactivity.Token = " + MainActivity.Token);
            params.put("Authorization", "Bearer " + MainActivity.Token);
            params.put("X-Access-Token", MainActivity.Token);
            params.put("Content-Type", "application/json");

            return params;
        }};

        requestQueue.add(request);
    }
}