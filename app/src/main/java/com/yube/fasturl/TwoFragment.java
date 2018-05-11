package com.yube.fasturl;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


@SuppressLint("ValidFragment")
public class TwoFragment extends Fragment {

    SharedPreferences preferences;
    Button openAlertBtn;
    Button searchBtn;
    DatabaseHandler db;
    TextView searchText;

    @SuppressLint("ValidFragment")
    public TwoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_main2, container, false);
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        db = new DatabaseHandler(getActivity().getApplicationContext());

        openAlertBtn =view.findViewById(R.id.openalert);

        searchBtn=view.findViewById(R.id.go);
searchText=view.findViewById(R.id.searchText);


        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), searchActivity.class);
                i.putExtra("url","https://www.google.com.tr/search?q="+searchText.getText() );

                startActivity(i);
            }
        });

openAlertBtn.setOnClickListener(new View.OnClickListener() {

    @Override
    public void onClick(View view1) {
       alert alert = new alert();
                  alert.showalert(getActivity(),db);
    }
});




   // Inflate the layout for this fragment
        return view;


    }


}
