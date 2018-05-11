package com.yube.fasturl;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by yusuf on 11.05.2018.
 */

public class alert {
    Button btadd;
    EditText urlet;
    EditText nameet;
    String url;
     Spinner spinner;
     int select=0;
    public alert() {
        this.url = "";
    }

    public alert(String url) {
        String []split=url.split("//");
        this.url = split[1];
        if(url.charAt(4)=='s'){
            select=2;
        }
        else
            select=1;

    }

    public String showalert(Activity context, final DatabaseHandler db) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.addalert);
         spinner = dialog.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.protocol, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(select);
        btadd = dialog.findViewById(R.id.addbutton);
        nameet = dialog.findViewById(R.id.name2);
        urlet = dialog.findViewById(R.id.url2);
        urlet.setText(url);
        btadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.addContact(new urlContact(nameet.getText().toString(), spinner.getSelectedItem().toString() + urlet.getText().toString()));
                dialog.dismiss();
            }
        });

        dialog.show();
        return "succes";
    }
}