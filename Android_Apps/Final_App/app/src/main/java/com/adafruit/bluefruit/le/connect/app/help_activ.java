package com.adafruit.bluefruit.le.connect.app;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.webkit.WebView;

import com.adafruit.bluefruit.le.connect.R;

public class help_activ extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        setTitle("About");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // display page in a webview format
        WebView infoWebView = (WebView) findViewById(R.id.infoWebView);
        if (infoWebView != null) {
            infoWebView.setBackgroundColor(Color.TRANSPARENT);
            infoWebView.loadUrl("file:///android_asset/help.html");
        }
    }




    /* makes the back arrow in the action bar behave the same as the android hardware back button */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }




} // end class help_activ