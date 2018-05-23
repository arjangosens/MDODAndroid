package com.project.avans.mdodandroid;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    EditText email;
    EditText password;
    TextView result;
    String Token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = (EditText) findViewById(R.id.editText_Email);
        password = (EditText) findViewById(R.id.editText_Password);
        result = (TextView) findViewById(R.id.textview_Status);

        Button btn = (Button) findViewById(R.id.button_Login);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(
                        email.getText().toString(),
                        password.getText().toString()
                );
            }
        });

        TextView registerTextView = (TextView) findViewById(R.id.textView_register);
        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
            }
        });
    }

    private void login(String username, String password) {

        RequestQueue queue = Volley.newRequestQueue(this);

        final String url = "https://mdod.herokuapp.com/api/login/client";

        JSONObject body = new JSONObject();
        try {
            body.put("email", username);
            body.put("password", password);
        } catch(Exception e) {
            Log.e("VOLLEY_TAG", e.toString());
        }

        // Request a string response from the provided URL.
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("VOLLEY_TAG", response.toString());
                        try {

                            Token = response.getString("token");
                            Log.d("the token", Token);
                            Intent i = new Intent(getApplicationContext(), HomepageActivity.class);

                            startActivity(i);
                        } catch (JSONException e) {
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("VOLLEY_TAG", error.toString());
                        result.setTextColor(Color.RED);
                        result.setText(R.string.unValidCredentials);
                    }
                }
        );

        queue.add(request);
    }
}
