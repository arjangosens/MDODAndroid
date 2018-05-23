package com.project.avans.mdodandroid;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class UserSettingsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ListView settingsListview;
    private ArrayList<String> settings = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);

        String firstName = getResources().getString(R.string.firstName);
        String insertion = getResources().getString(R.string.Insertion);
        String lastName = getResources().getString(R.string.Lastname);
        String dateOfBirth = getResources().getString(R.string.Dateofbirth);
        String email = getResources().getString(R.string.Email);
        String password = getResources().getString(R.string.password);

        //TODO: add local user data

        settings.add(firstName);
        settings.add(insertion);
        settings.add(lastName);
        settings.add(dateOfBirth);
        settings.add(email);
        settings.add(password);

        //TODO: connect the Textviews to the userdata

        settingsListview = (ListView) findViewById(R.id.listview_settings);

        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, settings);

        settingsListview.setAdapter(adapter);

        settingsListview.setOnItemClickListener(this);
    }

    private void showUpdateDialog(String type) {
        AlertDialog alertDialog;

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        builder.setTitle("Change " + type);

        switch (type) {
            case ("Emailadres"):
            case ("Email address"):
                builder.setView(inflater.inflate(R.layout.dialog_updateprofile_email, null));
                break;
            case ("Wachtwoord"):
            case ("Password"):
                builder.setView(inflater.inflate(R.layout.dialog_updateprofile_password, null));
                break;

            default:
                builder.setView(inflater.inflate(R.layout.dialog_updateprofile, null));
                break;
        }

        builder.setPositiveButton("Save changes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                // TODO: Save changes made in AlertDialog
            }
        })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        alertDialog = builder.create();

        alertDialog.show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        showUpdateDialog(settings.get(position));

    }
}
