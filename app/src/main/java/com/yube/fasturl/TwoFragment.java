package com.yube.fasturl;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


@SuppressLint("ValidFragment")
public class TwoFragment extends Fragment {

    int dbVersion=1;
    SharedPreferences preferences;

    Button openAlertBtn;
    Button searchBtn;
    Button orderBtn;

    DatabaseHandler db;

    TextView searchText;

    private ItemTouchHelper mItemTouchHelper;

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
        SharedPreferences.Editor editor = preferences.edit();
        dbVersion=preferences.getInt("db_version",-1);
        if(dbVersion<0){
            dbVersion=1;
        }


        openAlertBtn = view.findViewById(R.id.openalert);
orderBtn =view.findViewById(R.id.savesort);
        searchBtn = view.findViewById(R.id.go);
        searchText = view.findViewById(R.id.searchText);

        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.tablist);
        List<urlContact> urlList = new ArrayList<>();
        urlList = db.getAllContacts();
        // Toast.makeText(getActivity(),urlList.get(0).getName(),Toast.LENGTH_SHORT).show();
        final tabListAdapter tabListAdapter = new tabListAdapter(urlList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(tabListAdapter);
      


        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(tabListAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
        final SQLiteDatabase dbhandler = db.getWritableDatabase();

        orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               db.onUpgrade(dbhandler,dbVersion,dbVersion+1);
               db.changeSort(tabListAdapter.getUrlList());
               getActivity().finish();
                getActivity().startActivity( getActivity().getIntent());

            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), searchActivity.class);
                i.putExtra("url", "https://www.google.com.tr/search?q=" + searchText.getText());

                startActivity(i);
            }
        });

        openAlertBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view1) {
                alert alert = new alert();
                alert.showalert(getActivity(), db);
            }
        });


        // Inflate the layout for this fragment
        return view;


    }


}
