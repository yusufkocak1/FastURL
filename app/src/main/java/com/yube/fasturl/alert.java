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
    private  String name="";
    String url="";
    boolean t = true;
    int mod = 0;
    Spinner spinner;
    int select = 0;
    urlContact urlContact;

    public alert() {
        this.url = "";
    }

    public alert(com.yube.fasturl.urlContact urlContact) {
        this.urlContact = urlContact;
        mod = 2;
        String[] split = urlContact.getUrl().split("//");
        this.url = split[1];
        this.name=urlContact.getName();
        t = false;
        if (url.charAt(4) == 's') {
            select = 2;
        } else
            select = 1;

        t=true;

    }

    public alert(String url) {
        String[] split = url.split("//");
        this.url = split[1];
        t = false;
        if (url.charAt(4) == 's') {
            select = 2;
        } else
            select = 1;


    }


    public String showalert(final Activity context, final DatabaseHandler db) {
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
        nameet.setText(name);



        btadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mod == 2) {
                    db.updateContact(urlContact);
                } else {
                    db.addContact(new urlContact(nameet.getText().toString(), spinner.getSelectedItem().toString() + urlet.getText().toString()));
                }
                dialog.dismiss();
                if (t) {
                    context.finish();
                    context.startActivity(context.getIntent());
                }
            }
        });

        dialog.show();
        return "succes";
    }
}