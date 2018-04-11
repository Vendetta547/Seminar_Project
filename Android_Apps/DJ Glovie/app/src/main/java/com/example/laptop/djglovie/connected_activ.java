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
        Intent disconnect_intent = new Intent(this,MainActivity.class);
        startActivity(disconnect_intent);
    }
}
