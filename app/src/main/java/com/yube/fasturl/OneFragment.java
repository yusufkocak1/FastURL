package com.yube.fasturl;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


@SuppressLint("ValidFragment")
public class OneFragment extends Fragment {
    public static WebView webView = null;
     String url;
    SharedPreferences preferences;
    private ProgressDialog progressDialog;
    private SwipeRefreshLayout mySwipeRefreshLayout;

    @SuppressLint("ValidFragment")
    public OneFragment(String url) {
        this.url=url;
   }

    public OneFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

         View view=inflater.inflate(R.layout.fragment_main, container, false);
         preferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());

        webView =  view.findViewById(R.id.translate);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(true);

        webView.loadUrl(url);


        mySwipeRefreshLayout = view.findViewById(R.id.swipeContainer);

        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        mySwipeRefreshLayout.setRefreshing(true);
                        ( new Handler()).postDelayed(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                mySwipeRefreshLayout.setRefreshing(false);
                                webView.reload();
                            }
                        }, 4000);
                    }
                }
        );


        webView.setOnKeyListener(new View.OnKeyListener() {


            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //This is the filter
                if (event.getAction()!=KeyEvent.ACTION_DOWN)
                    return true;

                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (webView.canGoBack()) {
                        webView.goBack();
                    } else {
                        //webView.goBack();
                        Toast.makeText(getActivity(), "Daha nire gidecen gardaş", Toast.LENGTH_SHORT).show();

                        //((MainActivity)getActivity()).onBackPressed();
                    }
                    return true;
                }
                return false;
            }
        });
        return view;



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
            progressDialog = new ProgressDialog(getActivity());
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
