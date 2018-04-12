package com.example.laptop.djglovie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void connectClick (View view)    // connect button pressed
    {
    Intent connect_intent = new Intent(this,connected_activ.class);
    startActivity(connect_intent);
    }
    public void helpClick (View view)   // help button pressed
    {
        Intent help_intent = new Intent(this,about_activ.class);
        startActivity(help_intent);
    }
    public void aboutClick (View view)  // about button pressed
    {
        Intent about_intent = new Intent(this,about_activ.class);
        startActivity(about_intent);
    }

    @Override
    public void onBackPressed() { }

}
