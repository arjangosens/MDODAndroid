package com.project.avans.mdodandroid.activities.homepageActivies;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.project.avans.mdodandroid.R;
import com.project.avans.mdodandroid.adapters.messageAdapter.MessageAdapter;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_messages);

        //set the toolbar so it has the right image
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        messages = findViewById(R.id.listView_message);
        newMessage = findViewById(R.id.button_new_message);

        NetworkManager.getInstance().getMessages(this);

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

    @Override
    public void getResult(JSONArray object) {
        Log.i("TEST: ", object.toString());
        if (!(object == null)) {
            for (int i = 0; i < object.length(); i++) {
                try {
                    Log.i("TEST2: ", object.getJSONObject(i).toString());
                    messagesList.add(new Message(object.getJSONObject(i).getString("message"), object.getJSONObject(i).getString("date"), object.getJSONObject(i).getString("sendBy")));
                    messageAdapter.notifyDataSetChanged();
                    Log.i("TEST3: ", messagesList.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void showUpdateDialog() {
        AlertDialog alertDialog;
        String hint = "Voer hier uw bericht in";

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
                    NetworkManager.getInstance().postMessage(field, new VolleyListener<JSONObject>() {
                        @Override
                        public void getResult(JSONObject object) {
                            if (!(object == null))
                            {
                                messagesList.add(0, new Message(field, "zojuist", "uzelf"));
                                dialog.dismiss();
                            } else {
                                incorrectFieldTextView.setText(getResources().getString(R.string.somethingWentWrong));
                            }
                        }
                    });
                }
            }
        });
    }
}
