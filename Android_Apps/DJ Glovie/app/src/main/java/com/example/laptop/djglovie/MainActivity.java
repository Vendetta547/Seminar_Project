package com.example.laptop.djglovie;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
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
        Intent startIntent = new Intent(this, ForegroundService.class);
        startIntent.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
        startService(startIntent);
        Intent connect_intent = new Intent(this,connected_activ.class);
        startActivity(connect_intent);
    }

    public void helpClick (View view)   // help button pressed
    {
        Intent help_intent = new Intent(this,contact_activ.class);
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
}
