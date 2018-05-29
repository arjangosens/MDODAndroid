package com.project.avans.mdodandroid;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.project.avans.mdodandroid.adapters.RiskAdapter.AsyncRisk;
import com.project.avans.mdodandroid.adapters.RiskAdapter.RiskListener;
import com.project.avans.mdodandroid.adapters.RiskAdapter.onRiskClick;
import com.project.avans.mdodandroid.adapters.RiskAdapter.RiskAdapter;
import com.project.avans.mdodandroid.object_classes.Risk;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyPersonalRiskActivity extends AppCompatActivity implements DialogInterface.OnShowListener, RiskListener{

    private View updateDialogView;
    private ArrayList<Risk> RiskList = new ArrayList<>();
    private RiskAdapter RiskAdapter = null;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_risks);
        //Risk e = new Risk("1", "test");
        //RiskList.add(e);
        //getRisk();
        url = "https://jsonplaceholder.typicode.com/users";

        String[] urls = new String[] {url};
        AsyncRisk task = new AsyncRisk(this);
        task.execute(urls);

        Button add = findViewById(R.id.button_risks);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdateDialog();
            }
        });

        ListView RiskListView = findViewById(R.id.listView_risks);
        RiskAdapter = new RiskAdapter(getLayoutInflater(), RiskList);
        RiskListView.setAdapter(RiskAdapter);
        RiskAdapter.notifyDataSetChanged();
        RiskListView.setOnItemClickListener(new onRiskClick(getApplicationContext()) {});
    }

    @Override
    public void onRiskListener(Risk risk) {
        System.out.println("test");
        RiskList.add(risk);
        RiskAdapter.notifyDataSetChanged();

    }

    private void showUpdateDialog() {
        AlertDialog alertDialog;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        builder.setTitle(getResources().getString(R.string.risks));

        View view;

        view = inflater.inflate(R.layout.dialog_updateprofile, null);
        builder.setView(view);

        updateDialogView = view;
        builder.setPositiveButton(getResources().getString(R.string.saveChanges), null);
        builder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        alertDialog = builder.create();
        alertDialog.setOnShowListener(this);

        alertDialog.show();
    }

    @Override
    public void onShow(DialogInterface dialog) {
        Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                TextView incorrectFieldTextView = updateDialogView.findViewById(R.id.dialogUpdateProfile_textViewIncorrectField);
                TextView updateDialogGenericEditText = updateDialogView.findViewById(R.id.dialogUpdateProfile_editText);

            }
        });
    }

    //adds custom menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        Intent i;
        switch(id){
            case R.id.menu_user_settings:
                i = new Intent(getApplicationContext(), UserSettingsActivity.class);
                startActivity(i);
                break;
            case R.id.menu_logout:
                i = new Intent(getApplicationContext(), MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void getRisk() {
        RequestQueue queue = Volley.newRequestQueue(this);

        final String url = "https://jsonplaceholder.typicode.com/photos";

        // Request a string response from the provided URL.
        StringRequest request = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject;


                        try {
                            jsonObject = new JSONObject(response);

                            System.out.println(jsonObject);
                            //JSONArray RisksJSON = jsonObject.getJSONArray("Risks");
                            //System.out.println("risks " + RisksJSON);
//                            for(int i=0; i< RisksJSON.length(); i++){
//                                JSONObject JSONRisk = RisksJSON.getJSONObject(i);
//                                String Risk = JSONRisk.getString("Risk");
//                                String RiskID = JSONRisk.getString("_ID");
//                                Risk rp = new Risk(RiskID, Risk);
//                                onRiskListener(rp);
//                            }
                        } catch (JSONException e) {
                            Log.e("on resp", e.getLocalizedMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("VOLLEY_TAG", error.toString());
                    }
                })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer "+ MainActivity.Token);
                return params;

        }};
        queue.add(request);
    }

}
