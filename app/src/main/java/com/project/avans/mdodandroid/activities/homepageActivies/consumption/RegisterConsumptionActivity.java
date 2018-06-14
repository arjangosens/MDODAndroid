package com.project.avans.mdodandroid.activities.homepageActivies.consumption;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.avans.mdodandroid.R;
import com.project.avans.mdodandroid.activities.homepageActivies.HomepageActivity;
import com.project.avans.mdodandroid.activities.homepageActivies.PhoneActivity;
import com.project.avans.mdodandroid.applicationLogic.ConnectionChecker;
import com.project.avans.mdodandroid.applicationLogic.api.NetworkManager;
import com.project.avans.mdodandroid.applicationLogic.api.VolleyListener;
import com.project.avans.mdodandroid.adapters.consumptionAdapter.ConRegSubstanceAdapter;
import com.project.avans.mdodandroid.applicationLogic.notifications.NotificationService;
import com.project.avans.mdodandroid.domain.Substance;

import org.json.JSONObject;

import java.util.ArrayList;

public class RegisterConsumptionActivity extends AppCompatActivity implements ConRegSubstanceAdapter.OnItemClickListener, View.OnClickListener {
    private RecyclerView substanceRv;

    private TextView typetextView;
    private TextView LocationTextView;
    private TextView causeTextView;
    private TextView unitTextView;

    private TextView amountTextView;


    private ArrayList<Substance> substances;
    private ArrayList<ImageView> smileys;
    private Integer substanceId;

    private Integer feelingId;
    private ImageView smileyHappy;
    private ImageView smileyGood;
    private ImageView smileyOk;
    private ImageView smileySad;
    private ImageView smileyTerrible;

    String substanceStr;
    Context context;
    private String channelId;

    private ConRegSubstanceAdapter adapter;
    private static final String TAG = "RegConsumptionActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_consumption);

        context = this;
        substances = new ArrayList<>();
        smileys = new ArrayList<>();

        initTypes();
        initSmileys();

        substanceStr = "";

        substanceRv = (RecyclerView) findViewById(R.id.act_registerConsumption_RecyclerViewSubstances);
        typetextView = (TextView) findViewById(R.id.act_registerConsumption_textViewSelectionValue);
        LocationTextView = (TextView)findViewById(R.id.act_registerConsumption_editTextLocationValue);
        causeTextView = (TextView)findViewById(R.id.act_registerConsumption_editTextCauseValue);
        amountTextView = (TextView)findViewById(R.id.act_registerConsumption_editTextAmountValue);
        unitTextView = (TextView)findViewById(R.id.act_unit_textview);




//        adapter = new ConRegSubstanceAdapter(substances);
//        adapter.setOnItemClickListener(this);
//        substanceRv.setAdapter(adapter);

        substanceRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        this.adapter = new ConRegSubstanceAdapter(substances, new ConRegSubstanceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.i(TAG, "onItemClick(" + position + ") called");

                try {
                    Substance substance = substances.get(position);
                    typetextView.setText(substance.getType());
                    substanceStr = String.valueOf(typetextView.getText());
                    initUnit(substance.getMeasurement());
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }


            }
        });
        substanceRv.setAdapter(this.adapter);

        Button btn = (Button) findViewById(R.id.registerUsageButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO checks for empty fields

                String location = "";
                String cause = "";
                int amount = 0;

                try {
                    location = String.valueOf(LocationTextView.getText());
                    cause = String.valueOf(causeTextView.getText());
                    amount = Integer.valueOf(amountTextView.getText().toString());

                } catch (NullPointerException | NumberFormatException e){
                    Log.i(TAG, "Some fields were still empty!");
                    e.printStackTrace();
                }

                if(!substanceStr.isEmpty() && !location.isEmpty() && !cause.isEmpty() && feelingId != null){

                    Log.i("data, ",location + " " + amount + " " + feelingId +  " " + cause + " " + substanceId );
                    if (ConnectionChecker.CheckCon(context)) {
                        Toast toast = Toast.makeText(context, R.string.noConnection, Toast.LENGTH_SHORT);
                        toast.show();

                    } else {
                        NetworkManager.getInstance().postUsage(location,amount,feelingId,cause,substanceId, new VolleyListener<JSONObject>() {
                            @Override
                            public void getResult(JSONObject object) {
                                try {
                                    Log.i("TEST: ", object.toString());

                                    if (!(object == null)) {

                                        AlertDialog.Builder builder = new AlertDialog.Builder(context);

                                        NotificationService.Notificat(NotificationService.getNotification("U Heeft al 2 dagen geen gebruik ingevoerd, voer uw gebruik in alstublieft.", context), 60 * 1000, context);
                                        //NotificationService.Notificat(NotificationService.getNotification("U Heeft al 2 dagen geen gebruik ingevoerd, voer uw gebruik in alstublieft.", context), 2*24*60*60*1000, context);
                                        builder.setMessage(getResources().getString(R.string.used)).setPositiveButton(getResources().getString(R.string.yes), dialogClickListener).setNegativeButton(getResources().getString(R.string.no), dialogClickListener).show();



                                    } else {

                                    }
                                }
                                catch (NullPointerException e){
                                    //TODO iets mis met de server
                                    e.printStackTrace();
                                }
                            }
                        });}

                } else {
                    Toast toast = Toast.makeText(RegisterConsumptionActivity.this, getResources().getString(R.string.emptyFields), Toast.LENGTH_SHORT);
                    toast.show();
                }

            }
        });

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        substanceRv.setLayoutManager(llm);

    }

    @Override
    public void onItemClick(int position) {
//        substanceRv.getRecycledViewPool().clear();
        Log.i(TAG, "OnItemClick() called on pos " + position);
    }

    private void initTypes() {
        //substances.add(new Substance("Nothing", getResources().getDrawable(R.drawable.like), ""));
        substances.add(new Substance("Wiet", getResources().getDrawable(R.drawable.marijuana), "joints"));
        substances.add(new Substance("Alcohol", getResources().getDrawable(R.drawable.wine), "glazen"));
        substances.add(new Substance("GHB", getResources().getDrawable(R.drawable.ghb), "ml"));
        substances.add(new Substance("LSD", getResources().getDrawable(R.drawable.lsd), "mg"));
        substances.add(new Substance("Cocaïne", getResources().getDrawable(R.drawable.cocaine), "g"));
        substances.add(new Substance("Anders", getResources().getDrawable(R.drawable.question), ""));
    }

    private void initUnit(String unit) {

        Log.i("substance Id: ", substanceStr);

        unitTextView.setText(unit);
        if(substanceStr.equals("Nothing")){
            substanceId = 12;
        }
        else if (substanceStr.equals("Alcohol")){
            Log.i("Alcohol", "chosen");
            substanceId = 1;
        }
        else if (substanceStr.equals("Wiet")){
            substanceId = 2;
        }
        else if (substanceStr.equals("GHB")){
            substanceId = 8;
        }
        else if (substanceStr.equals("LSD")){
            substanceId = 9;
        }
        else if (substanceStr.equals("Cocaïne")) {
            substanceId = 10;
        }
        else if (substanceStr.equals("Anders")){
            substanceId = 11;
        }
    }

    private void initSmileys() {
        smileyHappy = (ImageView) findViewById(R.id.con_smiley_happy);
        smileyGood = (ImageView) findViewById(R.id.con_smiley_good);
        smileyOk = (ImageView) findViewById(R.id.con_smiley_ok);
        smileySad = (ImageView) findViewById(R.id.con_smiley_sad);
        smileyTerrible = (ImageView) findViewById(R.id.con_smiley_terrible);

        smileys.add(smileyHappy);
        smileys.add(smileyGood);
        smileys.add(smileyOk);
        smileys.add(smileySad);
        smileys.add(smileyTerrible);

        for (ImageView smiley : smileys) {
            smiley.setOnClickListener(this);
            smiley.setColorFilter(getResources().getColor(android.R.color.black));
        }

    }

    private void selectSmiley(int position) {
        ImageView selectedSmiley = smileys.get(position);

        for (ImageView smiley : smileys) {
            if (smiley.equals(selectedSmiley)) {
                smiley.clearColorFilter();

            } else {
                smiley.setColorFilter(getResources().getColor(android.R.color.black));
            }
        }
    }

    @Override
    public void onClick(View v) {
        Log.i(TAG, "Onclick called");

        switch (v.getId()) {
            case R.id.con_smiley_happy:
                selectSmiley(0);
                feelingId = 0;
                break;

            case R.id.con_smiley_good:
                selectSmiley(1);
                feelingId = 1;
                break;

            case R.id.con_smiley_ok:
                selectSmiley(2);
                feelingId = 2;
                break;

            case R.id.con_smiley_sad:
                selectSmiley(3);
                feelingId = 3;
                break;

            case R.id.con_smiley_terrible:
                selectSmiley(4);
                feelingId = 4;
                break;
        }

        Log.i(TAG, "feelingId = " + feelingId);
    }

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    Intent i = new Intent(getApplicationContext(),PhoneActivity.class);
                    startActivity(i);
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    Intent i2 = new Intent(getApplicationContext(), ConsumptionActivity.class);
                    startActivity(i2);
                    break;
            }
        }
    };

}
