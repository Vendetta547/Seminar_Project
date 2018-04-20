package com.adafruit.bluefruit.le.connect.app;

import android.graphics.Color;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import com.adafruit.bluefruit.le.connect.R;

public class help_activ extends AppCompatActivity {

    Button play_button;
    VideoView video_window;
    MediaController mediaC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        setTitle("About");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        play_button = (Button)findViewById(R.id.video_play_button);
        video_window = (VideoView)findViewById(R.id.video_view);
        mediaC = new MediaController(this);
    }

    public void videoPlay(View v) {
        String videopath = "android.resource://com.adafruit.bluefruit.le.connect/"+R.raw.djglovie_flipped;
        Uri uri = Uri.parse(videopath);
        video_window.setVideoURI(uri);
        video_window.setMediaController(mediaC);
        mediaC.setAnchorView(video_window);
        video_window.start();
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