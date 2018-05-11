package com.yube.fasturl;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
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
        webView.loadUrl(url);


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


}
