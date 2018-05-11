package com.yube.fasturl;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;


@SuppressLint("ValidFragment")
public class OneFragment extends Fragment {
     WebView webView = null;
    SharedPreferences preferences;

    @SuppressLint("ValidFragment")
    public OneFragment() {
   }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         View view=inflater.inflate(R.layout.fragment_main, container, false);
         preferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());



        webView =  view.findViewById(R.id.translate);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://translate.google.com.tr");
        return view;



    }

}
