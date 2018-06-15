package com.project.avans.mdodandroid.activities.homepageActivies;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.avans.mdodandroid.R;
import com.project.avans.mdodandroid.activities.loginAndRegisterActivities.LoginActivity;
import com.project.avans.mdodandroid.activities.settingActivities.PhoneSettingsActivity;
import com.project.avans.mdodandroid.activities.settingActivities.UserSettingsActivity;
import com.project.avans.mdodandroid.adapters.messageAdapter.MessageAdapter;
import com.project.avans.mdodandroid.applicationLogic.ConnectionChecker;
import com.project.avans.mdodandroid.applicationLogic.api.NetworkManager;
import com.project.avans.mdodandroid.applicationLogic.api.VolleyListener;
import com.project.avans.mdodandroid.domain.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyMessagesActivity extends AppCompatActivity implements DialogInterface.OnShowListener, VolleyListener<JSONArray> {
    private ListView messages;
    private Button newMessage;
    private View updateDialogView;
    private MessageAdapter messageAdapter = null;
    private ArrayList<Message> messagesList = new ArrayList<>();
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_messages);
        context = this;

        //set the toolbar so it has the right image
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        messages = findViewById(R.id.listView_message);
        newMessage = findViewById(R.id.button_new_message);

        //Connection check
        if (ConnectionChecker.CheckCon(context)) {
            Toast toast = Toast.makeText(context, R.string.noConnection, Toast.LENGTH_SHORT);
            toast.show();
            Intent i = new Intent(getApplicationContext(), HomepageActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else {
            //API call
        NetworkManager.getInstance().getMessages(this);}

        messageAdapter = new MessageAdapter(getLayoutInflater(), messagesList, this);
        messages.setAdapter(messageAdapter);
        messageAdapter.notifyDataSetChanged();

        newMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdateDialog();
            }
        });
    }


    //API result processing
    @Override
    public void getResult(JSONArray object) {
        Log.i("Result messages: ", object.toString());
        if (!(object == null)) {
            for (int i = 0; i < object.length(); i++) {
                try {
                    String date = object.getJSONObject(i).getString("date");
                    String[] parts = date.split("[-T.]+");
                    String date2 = parts[2] + "-" + parts[1] + "-" + parts[0] + " " + parts[3];
                    messagesList.add(new Message(object.getJSONObject(i).getString("message"), date2 , object.getJSONObject(i).getString("sendBy")));
                    messageAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //Making of Dialog box
    private void showUpdateDialog() {
        AlertDialog alertDialog;
        String hint = getResources().getString(R.string.insertMessage);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();


        View view;

        view = inflater.inflate(R.layout.dialog_updateprofile, null);
        builder.setView(view);

        updateDialogView = view;

        builder.setTitle(getResources().getString(R.string.newMessage));
        final TextView updateDialogGenericEditText = updateDialogView.findViewById(R.id.dialogUpdateProfile_editText);

        updateDialogGenericEditText.setHint(hint);

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

    //Dialogbox functionality
    @Override
    public void onShow(final DialogInterface dialog) {
        Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final TextView incorrectFieldTextView = updateDialogView.findViewById(R.id.dialogUpdateProfile_textViewIncorrectField);
                final TextView updateDialogGenericEditText = updateDialogView.findViewById(R.id.dialogUpdateProfile_editText);

                final String field = String.valueOf(updateDialogGenericEditText.getText());
                Log.i("DialogUpdateProfile", "Value of field: " + field);

                if (field.equals("")) {
                    incorrectFieldTextView.setText(getResources().getString(R.string.userSettingsFieldInvalid));
                } else {
                    //connection check
                    if (ConnectionChecker.CheckCon(context)) {
                        Toast toast = Toast.makeText(context, R.string.noConnection, Toast.LENGTH_SHORT);
                        toast.show();

                    } else {
                        //API call
                    NetworkManager.getInstance().postMessage(field, new VolleyListener<JSONObject>() {
                        @Override
                        public void getResult(JSONObject object) {
                            if (!(object == null))
                            {
                                messagesList.add(0, new Message(field, getResources().getString(R.string.justNow), getResources().getString(R.string.sendByMe)));
                                dialog.dismiss();
                            } else {
                                incorrectFieldTextView.setText(getResources().getString(R.string.messageNotsend));
                            }
                        }
                    });}
                }
            }
        });
    }

    //adds custom menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return true;
    }

    //Custom menu functionality
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        Intent i;
        switch (id) {
            case R.id.menu_user_settings:
                i = new Intent(getApplicationContext(), UserSettingsActivity.class);
                startActivity(i);
                break;
            case R.id.menu_logout:
                i = new Intent(getApplicationContext(), LoginActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(i);
                break;
            case R.id.menu_user_phone:
                i = new Intent(getApplicationContext(), PhoneSettingsActivity.class);
                startActivity(i);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
