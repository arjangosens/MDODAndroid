package com.project.avans.mdodandroid.adapters.riskAdapter;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;

import com.project.avans.mdodandroid.domain.Risk;

public class OnRiskClick extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private OnAlertBoxAvailableR listener;


    public OnRiskClick(OnAlertBoxAvailableR listener) {
        this.listener = listener;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Risk rv = (Risk) adapterView.getItemAtPosition(i);
        listener.onAlertBoxAvailableR(rv);
    }
}


//
//    private void showUpdateDialog(){
//        AlertDialog alertDialog;
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        // Get the layout inflater
//        LayoutInflater inflater = LayoutInflater.from(context);
//
//        builder.setTitle(getResources().getString(R.string.risks));
//
//        View view;
//
//        view = inflater.inflate(R.layout.dialog_updateprofile, null);
//        builder.setView(view);
//
//        updateDialogView = view;
//        builder.setPositiveButton(getResources().getString(R.string.saveChanges), null);
//        builder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
//                dialog.cancel();
//            }
//        });
//
//        alertDialog = builder.create();
//        alertDialog.setOnShowListener(this);
//
//        alertDialog.show();
//    }
//
//    @Override
//    public void onShow(DialogInterface dialog) {
//        Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
//        button.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                TextView incorrectFieldTextView = updateDialogView.findViewById(R.id.dialogUpdateProfile_textViewIncorrectField);
//                TextView updateDialogGenericEditText = updateDialogView.findViewById(R.id.dialogUpdateProfile_editText);
//
//            }
//        });
//    }

