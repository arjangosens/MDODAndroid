package com.project.avans.mdodandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;


public class HowAreYouFeelingActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView smileyHappy;
    private ImageView smileyGood;
    private ImageView smileyOk;
    private ImageView smileySad;
    private ImageView smileyTerrible;

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
    }

    // onclicklistener for the smileys
    @Override
    public void onClick(View v) {
        Log.i("Content: ", String.valueOf(v.getTag()));

        //get the tag when a image is clicked
        String emotion = String.valueOf(v.getTag());

        int color = getResources().getColor(R.color.coloraccent);


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
