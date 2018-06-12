package com.project.avans.mdodandroid.activities.homepageActivies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.avans.mdodandroid.R;
import com.project.avans.mdodandroid.activities.homepageActivies.HomepageActivity;
import com.project.avans.mdodandroid.applicationLogic.api.NetworkManager;
import com.project.avans.mdodandroid.applicationLogic.api.VolleyListener;

import org.json.JSONObject;


public class HowAreYouFeelingActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView smileyHappy;
    private ImageView smileyGood;
    private ImageView smileyOk;
    private ImageView smileySad;
    private ImageView smileyTerrible;
    private Button buttonConfirm;
    private TextView description;
    private TextView error;

    //create final strings because the emotions stay the same
    final String HAPPY = "Happy";
    final String GOOD = "Good";
    final String OK = "Ok";
    final String SAD = "Sad";
    final String TERRIBLE = "Terrible";

    private int color;
    private int colorSmileyTerrible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeling);

        //Find colors
        color = getResources().getColor(R.color.selected);
        colorSmileyTerrible = getResources().getColor(R.color.colorSmileyTerrible);

        //Find all the smileys in the activity_feeling.xml in the layout folder
        smileyHappy = findViewById(R.id.smiley_happy);
        smileyGood = findViewById(R.id.smiley_good);
        smileyOk = findViewById(R.id.smiley_ok);
        smileySad = findViewById(R.id.smiley_sad);
        smileyTerrible = findViewById(R.id.smiley_terrible);

        //set onclicklistener
        smileyHappy.setOnClickListener(this);
        smileyGood.setOnClickListener(this);
        smileyOk.setOnClickListener(this);
        smileySad.setOnClickListener(this);
        smileyTerrible.setOnClickListener(this);

        //set Tags
        smileyHappy.setTag(HAPPY);
        smileyGood.setTag(GOOD);
        smileyOk.setTag(OK);
        smileySad.setTag(SAD);
        smileyTerrible.setTag(TERRIBLE);

        //set Black color
        smileyHappy.setColorFilter(color);
        smileyGood.setColorFilter(color);
        smileyOk.setColorFilter(color);
        smileySad.setColorFilter(color);
        smileyTerrible.setColorFilter(color);


        //find descriptionField to send the description to the api when the confirm button is pressed
        description = findViewById(R.id.editText_feeling_description);

        //find the error textfield to show errors on the screen;
        error = findViewById(R.id.textview_feeling_error);

        //find confirm button to send data input to the api
        buttonConfirm = findViewById(R.id.button_feeling_confirm);
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String field = String.valueOf(description.getText());
                

                if(field.equals("")){
                    error.setText(getResources().getString(R.string.descriptionInvalid));
                }

                int value;

                if(smileyHappy.isSelected()){
                    value = 1;
                    Log.i("IMAGE SELECTED: ", "1");
                    
                } else if (smileyGood.isSelected()){
                    value = 2;
                    Log.i("IMAGE SELECTED: ", "2");
                    
                } else if (smileyOk.isSelected()){
                    value = 3;
                    Log.i("IMAGE SELECTED: ", "3");
                    
                } else if (smileySad.isSelected()) {
                    value = 4;
                    Log.i("IMAGE SELECTED: ", "4");
                    
                } else if (smileyTerrible.isSelected()){
                    value = 5;
                    Log.i("IMAGE SELECTED: ", "5");
                    
                } else {
                    value = 0;
                    Log.i("IMAGE SELECTED: ", "NO IMAGE SELECTED");
                    error.setText(getResources().getString(R.string.smileyNotSelected));
                }

                NetworkManager.getInstance().postStatus(value, field, new VolleyListener<JSONObject>() {
                    @Override
                    public void getResult(JSONObject object) {
                        Log.i("TEST: ", object.toString());

                        if (!(object == null))
                        {
                            Intent intent = new Intent(getApplicationContext(), HomepageActivity.class);
                            startActivity(intent);
                        } else {
                            error.setText(getResources().getString(R.string.somethingWentWrong));
                        }
                    }
                });


            }
        });

    }

    // onclicklistener for the smileys
    @Override
    public void onClick(View v) {
        Log.i("Content: ", String.valueOf(v.getTag()));

        //get the tag when a image is clicked
        String emotion = String.valueOf(v.getTag());




        //this selects the current smiley and deselects the other smileys
        switch(emotion){
            case HAPPY:
                if(v.isSelected()){
                    v.setSelected(false);
                    smileyHappy.setColorFilter(color);
                } else {
                    v.setSelected(true);
                    smileyHappy.clearColorFilter();

                    //deselect all the other smileys
                    smileyGood.setColorFilter(color);
                    smileyGood.setSelected(false);
                    smileyOk.setColorFilter(color);
                    smileyOk.setSelected(false);
                    smileySad.setColorFilter(color);
                    smileySad.setSelected(false);
                    smileyTerrible.setColorFilter(color);
                    smileyTerrible.setSelected(false);
                }
                break;
            case GOOD:
                if(v.isSelected()){
                    v.setSelected(false);
                    smileyGood.setColorFilter(color);
                } else {
                    v.setSelected(true);
                    smileyGood.clearColorFilter();

                    //deselect all the other smileys
                    smileyHappy.setColorFilter(color);
                    smileyHappy.setSelected(false);
                    smileyOk.setColorFilter(color);
                    smileyOk.setSelected(false);
                    smileySad.setColorFilter(color);
                    smileySad.setSelected(false);
                    smileyTerrible.setColorFilter(color);
                    smileyTerrible.setSelected(false);
                }
                break;
            case OK:
                if(v.isSelected()){
                    v.setSelected(false);
                    smileyOk.setColorFilter(color);
                } else {
                    v.setSelected(true);
                    smileyOk.clearColorFilter();


                    //deselect all the other smileys
                    smileyHappy.setColorFilter(color);
                    smileyHappy.setSelected(false);
                    smileyGood.setColorFilter(color);
                    smileyGood.setSelected(false);
                    smileySad.setColorFilter(color);
                    smileySad.setSelected(false);
                    smileyTerrible.setColorFilter(color);
                    smileyTerrible.setSelected(false);
                }
                break;
            case SAD:
                if(v.isSelected()){
                    v.setSelected(false);
                    smileySad.setColorFilter(color);
                } else {
                    v.setSelected(true);
                    smileySad.clearColorFilter();

                    //deselect all the other smileys
                    smileyHappy.setColorFilter(color);
                    smileyHappy.setSelected(false);
                    smileyGood.setColorFilter(color);
                    smileyGood.setSelected(false);
                    smileyOk.setColorFilter(color);
                    smileyOk.setSelected(false);
                    smileyTerrible.setColorFilter(color);
                    smileyTerrible.setSelected(false);
                }
                break;
            case TERRIBLE:
                if(v.isSelected()){
                    v.setSelected(false);
                    smileyTerrible.setColorFilter(color);
                } else {
                    v.setSelected(true);
                    smileyTerrible.setColorFilter(colorSmileyTerrible);

                    //deselect all the other smileys
                    smileyHappy.setColorFilter(color);
                    smileyHappy.setSelected(false);
                    smileyGood.setColorFilter(color);
                    smileyGood.setSelected(false);
                    smileyOk.setColorFilter(color);
                    smileyOk.setSelected(false);
                    smileySad.setColorFilter(color);
                    smileySad.setSelected(false);
                }
                break;
        }
    }
}
