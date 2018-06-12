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
import com.project.avans.mdodandroid.activities.loginAndRegisterActivities.LoginActivity;
import com.project.avans.mdodandroid.domain.Risk;

import org.json.JSONArray;
import org.json.JSONObject;

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
                            if (error.networkResponse.statusCode == 404){
                                listener.getResult("empty");
                            }
                            else{
                                Log.d(TAG + ": ", "Error Response code: " + error.networkResponse.statusCode);
                                listener.getResult("");
                            }

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
            Log.i(TAG, "Mainactivity.Token = " + LoginActivity.Token);
            params.put("Authorization", "Bearer " + LoginActivity.Token);
            params.put("X-Access-Token", LoginActivity.Token);
            params.put("Content-Type", "application/json");

            return params;
        }};

        requestQueue.add(request);
    }



    public void postGoal(String description, final VolleyListener<JSONObject> listener) {
        String url = prefixURL + "v1/goal";

        Map<String, Object> jsonParams = new HashMap<>();
        jsonParams.put("description", description);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonParams),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        Log.d(TAG + ": ", "login/client Response : " + response.toString());
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
            Log.i(TAG, "Mainactivity.Token = " + LoginActivity.Token);
            params.put("Authorization", "Bearer " + LoginActivity.Token);
            params.put("X-Access-Token", LoginActivity.Token);
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
            Log.i(TAG, "Mainactivity.Token = " + LoginActivity.Token);
            params.put("Authorization", "Bearer " + LoginActivity.Token);
            params.put("X-Access-Token", LoginActivity.Token);
            params.put("Content-Type", "application/json");

            return params;
        }};
        requestQueue.add(request);
    }

    public void putStatusGoal(String goalId, String status, final VolleyListener<JSONObject> listener) {

        String url = prefixURL + "v1/goal/update/status";

        Map<String, Object> jsonParams = new HashMap<>();
        jsonParams.put("goalId", goalId);
        jsonParams.put("isCompleted", status);

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
            Log.i(TAG, "Mainactivity.Token = " + LoginActivity.Token);
            params.put("Authorization", "Bearer " + LoginActivity.Token);
            params.put("X-Access-Token", LoginActivity.Token);
            params.put("Content-Type", "application/json");

            return params;
        }};
        requestQueue.add(request);
    }

    public void deleteGoal(String goalId, final VolleyListener<JSONObject> listener) {

        String url = prefixURL + "v1/goal/" + goalId ;

        Map<String, Object> jsonParams = new HashMap<>();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, url, new JSONObject(jsonParams),
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
            Log.i(TAG, "Mainactivity.Token = " + LoginActivity.Token);
            params.put("Authorization", "Bearer " + LoginActivity.Token);
            params.put("X-Access-Token", LoginActivity.Token);
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
            Log.i(TAG, "Mainactivity.Token = " + LoginActivity.Token);
            params.put("Authorization", "Bearer " + LoginActivity.Token);
            params.put("X-Access-Token", LoginActivity.Token);
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
                            listener.getResult("");
                        }
                    }
                }){@Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            Map<String, String> params = new HashMap<String, String>();
            Log.i(TAG, "Mainactivity.Token = " + LoginActivity.Token);
            params.put("Authorization", "Bearer " + LoginActivity.Token);
            params.put("X-Access-Token", LoginActivity.Token);
            params.put("Content-Type", "application/json");

            return params;
        }};
        requestQueue.add(request);
    }

    public void deleteRisk(String riskId, final VolleyListener<JSONObject> listener) {

        String url = prefixURL + "v1/risk/" + riskId ;

        Map<String, Object> jsonParams = new HashMap<>();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, url, new JSONObject(jsonParams),
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
            Log.i(TAG, "Mainactivity.Token = " + LoginActivity.Token);
            params.put("Authorization", "Bearer " + LoginActivity.Token);
            params.put("X-Access-Token", LoginActivity.Token);
            params.put("Content-Type", "application/json");

            return params;
        }};
        requestQueue.add(request);
    }


    public void updateClient(String firstname, String infix, String lastname, String phonenumber,
                             String birthday, String city, String adress, String zipcode, final VolleyListener<JSONObject> listener) {

        String url = prefixURL + "client";

        Map<String, Object> jsonParams = new HashMap<>();
        jsonParams.put("firstname", firstname);
        jsonParams.put("infix", infix);
        jsonParams.put("lastname", lastname);
        jsonParams.put("phonenumber", phonenumber);
        jsonParams.put("dob", birthday);
        jsonParams.put("city", city);
        jsonParams.put("adress", adress);
        jsonParams.put("zipcode", zipcode);

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
            Log.i(TAG, "Mainactivity.Token = " + LoginActivity.Token);
            params.put("Authorization", "Bearer " + LoginActivity.Token);
            params.put("X-Access-Token", LoginActivity.Token);
            params.put("Content-Type", "application/json");

            return params;
        }};

        requestQueue.add(request);

    }

    public void deleteClient(final VolleyListener<JSONObject> listener) {
        String url = prefixURL + "client";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG + ": ", "DELETE client Response : " + response.toString());
                        if (null != response.toString())
                            listener.getResult(response);
                        Log.i("resp", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (null != error.networkResponse) {
                            Log.d(TAG + ": ", "Error Response code: " + error.networkResponse.statusCode);
                            listener.getResult(null);

                        }
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                Log.i(TAG, "Mainactivity.Token = " + LoginActivity.Token);
                params.put("Authorization", "Bearer " + LoginActivity.Token);
                params.put("X-Access-Token", LoginActivity.Token);
                params.put("Content-Type", "application/json");

                return params;
            }
        };

        requestQueue.add(request);
    }

    public void postMoment(String substanceId, String lust, String description, String prevention, final VolleyListener<JSONObject> listener) {

        String url = prefixURL + "v1/difficult_moment";

        Map<String, Object> jsonParams = new HashMap<>();
        jsonParams.put("prevention", prevention);
        jsonParams.put("substance_id", substanceId);
        jsonParams.put("lust", lust);
        jsonParams.put("description", description);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonParams),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        Log.d(TAG + ": ", "post moment Response : " + response.toString());
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
            Log.i(TAG, "Mainactivity.Token = " + LoginActivity.Token);
            params.put("Authorization", "Bearer " + LoginActivity.Token);
            params.put("X-Access-Token", LoginActivity.Token);
            params.put("Content-Type", "application/json");

            return params;
        }};
        requestQueue.add(request);
    }

    public void getMoment(final VolleyListener<JSONArray> listener) {

        String url = prefixURL + "v1/difficult_moment";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response)
                    {
                        Log.d(TAG + ": ", "GET moment Response : " + response.toString());
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
            Log.i(TAG, "Mainactivity.Token = " + LoginActivity.Token);
            params.put("Authorization", "Bearer " + LoginActivity.Token);
            params.put("X-Access-Token", LoginActivity.Token);
            params.put("Content-Type", "application/json");

            return params;
        }};

        requestQueue.add(request);
    }

    public void getSubstances(final VolleyListener<JSONArray> listener) {

        String url = prefixURL + "v1/substance/all";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response)
                    {
                        Log.d(TAG + ": ", "GET substance Response : " + response.toString());
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
            Log.i(TAG, "Mainactivity.Token = " + LoginActivity.Token);
            params.put("Authorization", "Bearer " + LoginActivity.Token);
            params.put("X-Access-Token", LoginActivity.Token);
            params.put("Content-Type", "application/json");

            return params;
        }};

        requestQueue.add(request);
    }




    public void getClientPhone(final VolleyListener<JSONArray> listener) {

        String url = prefixURL + "v1/phone";

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
            Log.i(TAG, "Mainactivity.Token = " + LoginActivity.Token);
            params.put("Authorization", "Bearer " + LoginActivity.Token);
            params.put("X-Access-Token", LoginActivity.Token);
            params.put("Content-Type", "application/json");

            return params;
        }};

        requestQueue.add(request);
    }


    public void updatephone(String institution, String doctor, String buddy, String ice,
                             Integer id, final VolleyListener<JSONObject> listener) {

        String url = prefixURL + "v1/phone";

        Map<String, Object> jsonParams = new HashMap<>();
        jsonParams.put("firm", institution);
        jsonParams.put("buddy", buddy);
        jsonParams.put("ice", ice);
        jsonParams.put("id", id);


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, new JSONObject(jsonParams),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        Log.d(TAG + ": ", "PUT phonenr Response : " + response.toString());
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
            Log.i(TAG, "Mainactivity.Token = " + LoginActivity.Token);
            params.put("Authorization", "Bearer " + LoginActivity.Token);
            params.put("X-Access-Token", LoginActivity.Token);
            params.put("Content-Type", "application/json");

            return params;
        }};

        requestQueue.add(request);

    }

    public void getCleanDays(final VolleyListener<JSONObject> listener) {

        String url = prefixURL + "v1/usage/clean/status";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
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
            Log.i(TAG, "Mainactivity.Token = " + LoginActivity.Token);
            params.put("Authorization", "Bearer " + LoginActivity.Token);
            params.put("X-Access-Token", LoginActivity.Token);
            params.put("Content-Type", "application/json");

            return params;
        }};

        requestQueue.add(request);
    }

    public void getEmotionStatusDays(final VolleyListener<JSONObject> listener) {
        String url = prefixURL + "v1/mood/status";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
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
            Log.i(TAG, "Mainactivity.Token = " + LoginActivity.Token);
            params.put("Authorization", "Bearer " + LoginActivity.Token);
            params.put("X-Access-Token", LoginActivity.Token);
            params.put("Content-Type", "application/json");

            return params;
        }};

        requestQueue.add(request);
    }


    public void postStatus(int emotion, String description, final VolleyListener<JSONObject> listener) {

        String url = prefixURL + "v1/mood";

        Map<String, Object> jsonParams = new HashMap<>();
        jsonParams.put("value", emotion);
        jsonParams.put("description", description);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonParams),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        Log.d(TAG + ": ", "post status Response : " + response.toString());
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
            Log.i(TAG, "Mainactivity.Token = " + LoginActivity.Token);
            params.put("Authorization", "Bearer " + LoginActivity.Token);
            params.put("X-Access-Token", LoginActivity.Token);
            params.put("Content-Type", "application/json");

            return params;
        }};
        requestQueue.add(request);
    }

    public void postUsage(String Location, Integer Amount, Integer Mood, String Cause,Integer SubstanceId, final VolleyListener<JSONObject> listener) {

        String url = prefixURL + "v1/usage";

        Map<String, Object> jsonParams = new HashMap<>();
        jsonParams.put("location", Location);
        jsonParams.put("amount", Amount);
        jsonParams.put("mood", Mood);
        jsonParams.put("cause", Cause);
        jsonParams.put("substanceId", SubstanceId);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonParams),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        Log.d(TAG + ": ", "post moment Response : " + response.toString());
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
            Log.i(TAG, "Mainactivity.Token = " + LoginActivity.Token);
            params.put("Authorization", "Bearer " + LoginActivity.Token);
            params.put("X-Access-Token", LoginActivity.Token);
            params.put("Content-Type", "application/json");

            return params;
        }};
        requestQueue.add(request);
    }

    public void getUsage(final VolleyListener<JSONArray> listener) {

        String url = prefixURL + "v1/usage";

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
            Log.i(TAG, "Mainactivity.Token = " + LoginActivity.Token);
            params.put("Authorization", "Bearer " + LoginActivity.Token);
            params.put("X-Access-Token", LoginActivity.Token);
            params.put("Content-Type", "application/json");

            return params;
        }};

        requestQueue.add(request);
    }

    public void getMessages(final VolleyListener<JSONArray> listener) {

        String url = prefixURL + "v1/messages/client";

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
            Log.i(TAG, "Mainactivity.Token = " + LoginActivity.Token);
            params.put("Authorization", "Bearer " + LoginActivity.Token);
            params.put("X-Access-Token", LoginActivity.Token);
            params.put("Content-Type", "application/json");

            return params;
        }};

        requestQueue.add(request);
    }

    public void postMessage(String message, final VolleyListener<JSONObject> listener) {

        String url = prefixURL + "v1/messages/client";

        Map<String, Object> jsonParams = new HashMap<>();
        jsonParams.put("message", message);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonParams),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        Log.d(TAG + ": ", "post moment Response : " + response.toString());
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
            Log.i(TAG, "Mainactivity.Token = " + LoginActivity.Token);
            params.put("Authorization", "Bearer " + LoginActivity.Token);
            params.put("X-Access-Token", LoginActivity.Token);
            params.put("Content-Type", "application/json");

            return params;
        }};
        requestQueue.add(request);
    }

}