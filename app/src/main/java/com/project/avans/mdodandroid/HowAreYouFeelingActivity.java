package com.project.avans.mdodandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeling);

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

                if(smileyHappy.isSelected()){
                    Log.i("IMAGE SELECTED: ", "1");
                } else if (smileyGood.isSelected()){
                    Log.i("IMAGE SELECTED: ", "2");
                } else if (smileyOk.isSelected()){
                    Log.i("IMAGE SELECTED: ", "3");
                } else if (smileySad.isSelected()) {
                    Log.i("IMAGE SELECTED: ", "4");
                } else if (smileyTerrible.isSelected()){
                    Log.i("IMAGE SELECTED: ", "5");
                } else {
                    Log.i("IMAGE SELECTED: ", "NO IMAGE SELECTED");
                    error.setText(getResources().getString(R.string.smileyNotSelected));
                }
            }
        });

    }

    // onclicklistener for the smileys
    @Override
    public void onClick(View v) {
        Log.i("Content: ", String.valueOf(v.getTag()));

        //get the tag when a image is clicked
        String emotion = String.valueOf(v.getTag());

        int color = getResources().getColor(R.color.selected);


        //this selects the current smiley and deselects the other smileys
        switch(emotion){
            case HAPPY:
                if(v.isSelected()){
                    v.setSelected(false);
                    smileyHappy.clearColorFilter();
                } else {
                    v.setSelected(true);
                    smileyHappy.setColorFilter(color);

                    //deselect all the other smileys
                    smileyGood.clearColorFilter();
                    smileyGood.setSelected(false);
                    smileyOk.clearColorFilter();
                    smileyOk.setSelected(false);
                    smileySad.clearColorFilter();
                    smileySad.setSelected(false);
                    smileyTerrible.clearColorFilter();
                    smileyTerrible.setSelected(false);
                }
                break;
            case GOOD:
                if(v.isSelected()){
                    v.setSelected(false);
                    smileyGood.clearColorFilter();
                } else {
                    v.setSelected(true);
                    smileyGood.setColorFilter(color);

                    //deselect all the other smileys
                    smileyHappy.clearColorFilter();
                    smileyHappy.setSelected(false);
                    smileyOk.clearColorFilter();
                    smileyOk.setSelected(false);
                    smileySad.clearColorFilter();
                    smileySad.setSelected(false);
                    smileyTerrible.clearColorFilter();
                    smileyTerrible.setSelected(false);
                }
                break;
            case OK:
                if(v.isSelected()){
                    v.setSelected(false);
                    smileyOk.clearColorFilter();
                } else {
                    v.setSelected(true);
                    smileyOk.setColorFilter(color);

                    //deselect all the other smileys
                    smileyHappy.clearColorFilter();
                    smileyHappy.setSelected(false);
                    smileyGood.clearColorFilter();
                    smileyGood.setSelected(false);
                    smileySad.clearColorFilter();
                    smileySad.setSelected(false);
                    smileyTerrible.clearColorFilter();
                    smileyTerrible.setSelected(false);
                }
                break;
            case SAD:
                if(v.isSelected()){
                    v.setSelected(false);
                    smileySad.clearColorFilter();
                } else {
                    v.setSelected(true);
                    smileySad.setColorFilter(color);

                    //deselect all the other smileys
                    smileyHappy.clearColorFilter();
                    smileyHappy.setSelected(false);
                    smileyGood.clearColorFilter();
                    smileyGood.setSelected(false);
                    smileyOk.clearColorFilter();
                    smileyOk.setSelected(false);
                    smileyTerrible.clearColorFilter();
                    smileyTerrible.setSelected(false);
                }
                break;
            case TERRIBLE:
                if(v.isSelected()){
                    v.setSelected(false);
                    smileyTerrible.clearColorFilter();
                } else {
                    v.setSelected(true);
                    smileyTerrible.setColorFilter(color);

                    //deselect all the other smileys
                    smileyHappy.clearColorFilter();
                    smileyHappy.setSelected(false);
                    smileyGood.clearColorFilter();
                    smileyGood.setSelected(false);
                    smileyOk.clearColorFilter();
                    smileyOk.setSelected(false);
                    smileySad.clearColorFilter();
                    smileySad.setSelected(false);
                }
                break;
        }
    }
}
