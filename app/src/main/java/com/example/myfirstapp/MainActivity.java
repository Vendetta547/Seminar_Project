package com.example.myfirstapp;

import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void nextSong(View view) {
        Intent playSpotify = new Intent("com.spotify.mobile.android.ui.widget.NEXT");
        playSpotify.setPackage("com.spotify.music");
        getApplicationContext().sendBroadcast(playSpotify);
    }

    public void previousSong(View view) {
        Intent playSpotify = new Intent("com.spotify.mobile.android.ui.widget.PREVIOUS");
        playSpotify.setPackage("com.spotify.music");
        getApplicationContext().sendBroadcast(playSpotify);
    }

    public void playSong(View view) {
        Intent i = new Intent(Intent.ACTION_MEDIA_BUTTON);
        i.setComponent(new ComponentName("com.spotify.music", "com.spotify.music.internal.receiver.MediaButtonReceiver"));
        i.putExtra(Intent.EXTRA_KEY_EVENT,new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MEDIA_PLAY));
        sendOrderedBroadcast(i, null);

        i = new Intent(Intent.ACTION_MEDIA_BUTTON);
        i.setComponent(new ComponentName("com.spotify.music", "com.spotify.music.internal.receiver.MediaButtonReceiver"));
        i.putExtra(Intent.EXTRA_KEY_EVENT,new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_MEDIA_PLAY));
        sendOrderedBroadcast(i, null);
    }

    public void pauseSong(View view) {
        Intent playSpotify = new Intent("com.spotify.mobile.android.ui.widget.PLAY");
        playSpotify.setPackage("com.spotify.music");
        getApplicationContext().sendBroadcast(playSpotify);
    }
}
