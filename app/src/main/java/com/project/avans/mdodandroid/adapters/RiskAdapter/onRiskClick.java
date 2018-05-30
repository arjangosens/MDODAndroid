package com.project.avans.mdodandroid.adapters.RiskAdapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.project.avans.mdodandroid.MainActivity;
import com.project.avans.mdodandroid.R;
import com.project.avans.mdodandroid.object_classes.Risk;

public class onRiskClick extends AppCompatActivity implements AdapterView.OnItemClickListener, DialogInterface.OnShowListener{
        private Context context;
        private View updateDialogView;

        public onRiskClick(Context context) {
            this.context = context;
        }

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Risk rv = (Risk) adapterView.getItemAtPosition(i);
//            Intent intent = new Intent(context, MainActivity.class); // TODO change to edit page
//            intent.putExtra("Risk", rv);
//            context.startActivity(intent);
            showUpdateDialog();

        }

    private void showUpdateDialog(){
        AlertDialog alertDialog;

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // Get the layout inflater
        LayoutInflater inflater = LayoutInflater.from(context);

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


}

