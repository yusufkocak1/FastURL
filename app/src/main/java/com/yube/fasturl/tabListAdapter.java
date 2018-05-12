package com.yube.fasturl;

/**
 * Created by yusuf on 11.05.2018.
 */

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

public class tabListAdapter extends RecyclerView.Adapter<tabListAdapter.MyViewHolder>implements ItemTouchHelperAdapter   {
    private List<urlContact> urlList;

    public List<urlContact> getUrlList() {
        return urlList;
    }

    public DatabaseHandler db;
public Context activity;




    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tabname;
        public Button delBtn;
        private final Button editBtn;
        public DatabaseHandler db;
        public MyViewHolder(View view) {
            super(view);
            tabname = view.findViewById(R.id.tabname);
            delBtn=view.findViewById(R.id.delBtn);
            editBtn=view.findViewById(R.id.editBtn);
        }
    }


    public tabListAdapter(List<urlContact> list) {
        this.urlList = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tab_recycler_item, parent, false);
         db=new DatabaseHandler(parent.getContext());
activity=parent.getContext();
        return new MyViewHolder(itemView);
    }



    @Override
    public void onItemDismiss(int position) {
        urlList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(urlList, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }



    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final urlContact urlContact = urlList.get(position);
        holder.tabname.setText(urlContact.getName());

        holder.delBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                db.deleteContact(urlContact);
                ((Activity)activity).finish();
                activity.startActivity(((Activity) activity).getIntent());
            }
        });

        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new alert(urlContact).showalert((Activity)activity,db);
            }
        });



    }




    @Override
    public int getItemCount() {
        return urlList.size();
    }






}