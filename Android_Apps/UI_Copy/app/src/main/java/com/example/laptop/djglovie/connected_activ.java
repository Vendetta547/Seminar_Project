package com.example.laptop.djglovie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class connected_activ extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connected_activ_layout);
    }
    public void disconnectClick (View view)
    {
        Intent stopIntent = new Intent(this, ForegroundService.class);
        stopIntent.setAction(Constants.ACTION.STOPFOREGROUND_ACTION);
        startService(stopIntent);
        Intent disconnect_intent = new Intent(this,MainActivity.class);
        startActivity(disconnect_intent);
    }

    public void helpClick (View view)   // help button pressed
    {
        Intent help_intent = new Intent(this,help_activ.class);
        startActivity(help_intent);
    }
    public void aboutClick (View view)  // about button pressed
    {
        Intent about_intent = new Intent(this,about_activ.class);
        startActivity(about_intent);
    }

    public void contactClick (View view)  // about button pressed
    {
        Intent contact_intent = new Intent(this,contact_activ.class);
        startActivity(contact_intent);
    }

    @Override
    public void onBackPressed() { }

    public void onDestroy() {
        super.onDestroy();
        Intent stopIntent = new Intent(this, ForegroundService.class);
        stopIntent.setAction(Constants.ACTION.STOPFOREGROUND_ACTION);
        startService(stopIntent);
    }
}
