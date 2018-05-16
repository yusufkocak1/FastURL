package com.yube.fasturl;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


public class searchActivity extends AppCompatActivity {

    public WebView searchWebView;
    protected String url;
    protected DatabaseHandler db;
    private SwipeRefreshLayout mySwipeRefreshLayout;

    private ProgressDialog progressDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_search);
        getSupportActionBar().setTitle("");
        db = new DatabaseHandler(getApplicationContext());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        this.url = extras.getString("url");

        searchWebView = findViewById(R.id.searchWebView);
        searchWebView.setWebViewClient(new WebViewClient());
        searchWebView.getSettings().setJavaScriptEnabled(true);
        searchWebView.loadUrl(url);




        mySwipeRefreshLayout = this.findViewById(R.id.swipeContainer);

        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        searchWebView.reload();
                        mySwipeRefreshLayout.setRefreshing(false);
                    }
                }
        );
        searchWebView.setOnKeyListener(new View.OnKeyListener() {


            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //This is the filter
                if (event.getAction()!=KeyEvent.ACTION_DOWN)
                    return true;

                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (searchWebView.canGoBack()) {
                        searchWebView.goBack();
                    } else {
                        //webView.goBack();
                        Toast.makeText(searchActivity.this, "Daha nire gidecen gardaş", Toast.LENGTH_SHORT).show();

                        //((MainActivity)getActivity()).onBackPressed();
                    }
                    return true;
                }
                return false;
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.searchmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_favorite) {
            alert alert = new alert(searchWebView.getUrl());

            alert.showalert(searchActivity.this, db);
            return true;
        }
        if (id==android.R.id.home){
            startActivity(new Intent(this,MainActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
    private class MyWebViewClient extends WebViewClient {

        @SuppressWarnings("deprecation")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progressDialog = new ProgressDialog(searchActivity.this);
            progressDialog.setMessage("Lütfen Bekleyin ...");
            progressDialog.show();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if(progressDialog!=null){
                progressDialog.dismiss();
            }
        }
    }

}
