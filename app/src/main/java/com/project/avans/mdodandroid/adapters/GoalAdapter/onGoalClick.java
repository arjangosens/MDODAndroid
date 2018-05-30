package com.project.avans.mdodandroid.adapters.GoalAdapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.project.avans.mdodandroid.MyPersonalGoalsActivity;
import com.project.avans.mdodandroid.R;
import com.project.avans.mdodandroid.object_classes.Goal;


public class onGoalClick extends AppCompatActivity implements AdapterView.OnItemClickListener, DialogInterface.OnShowListener {
    private Context context;
    private LayoutInflater inflater;
    private View updateDialogView;

    public onGoalClick(Context context, LayoutInflater inflater) {
        this.context = context;
        this.inflater = inflater;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Goal rv = (Goal) adapterView.getItemAtPosition(i);
//            Intent intent = new Intent(context, MainActivity.class); // TODO change to edit page
//            intent.putExtra("Goal", rv);
//            context.startActivity(intent);
        showUpdateDialog();
    }

    private void showUpdateDialog() {
        AlertDialog alertDialog;

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(context.getResources().getString(R.string.newGoal));

        View view;

        view = inflater.inflate(R.layout.dialog_updateprofile, null);
        builder.setView(view);

        updateDialogView = view;
        builder.setPositiveButton(context.getResources().getString(R.string.saveChanges), null);
        builder.setNegativeButton(context.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        alertDialog = builder.create();
        alertDialog.setOnShowListener(this);

        // TODO: Deze alertDialog moet terug gestuurd worden naar de MyPersonalGoalsActivity
//        alertDialog.show();
    }

    @Override
    public void onShow(DialogInterface dialog) {
        Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                TextView incorrectFieldTextView = updateDialogView.findViewById(R.id.dialogUpdateProfile_textViewIncorrectField);
                TextView updateDialogGenericEditText = updateDialogView.findViewById(R.id.dialogUpdateProfile_editText);

                String field = String.valueOf(updateDialogGenericEditText.getText());
                Log.i("DialogUpdateProfile", "Value of field: " + field);
                if (field.equals("")) {
                    incorrectFieldTextView.setText(getResources().getString(R.string.userSettingsFieldInvalid));

                } else {

                }
            }
        });
    }
}
