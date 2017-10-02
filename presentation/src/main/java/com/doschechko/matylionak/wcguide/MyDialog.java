package com.doschechko.matylionak.wcguide;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;


public class MyDialog extends DialogFragment implements View.OnClickListener {
    private CheckBox box;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private int flag = 0; //0- показывать снова, 1- не больше не показывать.

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog, null);
        builder.setView(view);


        sharedPref = getActivity().getSharedPreferences(CONSTANTS.PREFERENCE, Context.MODE_PRIVATE);
        editor = sharedPref.edit();


        builder.setTitle(R.string.description);
        box = (CheckBox) view.findViewById(R.id.checkBox);
        box.setOnClickListener(this);


        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                editor.putInt(CONSTANTS.SHOW_AGAIN, flag);
                editor.commit();
            }
        });


        return builder.create();
    }


    @Override
    public void onClick(View v) {

        if (box.isChecked()) {
            flag = 1;
            editor.putInt(CONSTANTS.SHOW_AGAIN, flag);
            editor.commit();
        } else {
            flag = 0;
            editor.putInt(CONSTANTS.SHOW_AGAIN, flag);
            editor.commit();
        }

    }


    @Override
    public void onDestroy() {
        editor.putInt(CONSTANTS.SHOW_AGAIN, flag);
        editor.commit();
        super.onDestroy();
    }
}
