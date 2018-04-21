package com.adafruit.bluefruit.le.connect.app;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
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

        setTitle("Help");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       // play_button = (Button)findViewById(R.id.video_play_button);
        video_window = (VideoView)findViewById(R.id.video_view);
        mediaC = new MediaController(this);


        String videopath = "android.resource://com.adafruit.bluefruit.le.connect/"+R.raw.djglovie_flipped;
        Uri uri = Uri.parse(videopath);
        video_window.setVideoURI(uri);
        video_window.setMediaController(mediaC);
        mediaC.setAnchorView(video_window);

        video_window.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                video_window.start();
                return false;
            }
        });

        //video_window.start();
    }





    public void contactClick (View view)
    {
        Intent contact_intent = new Intent(this,contact_activ.class);
        startActivity(contact_intent);
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