package com.adafruit.bluefruit.le.connect.app;

import android.app.Fragment;
import android.app.FragmentManager;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import com.adafruit.bluefruit.le.connect.R;
import com.adafruit.bluefruit.le.connect.ble.BleManager;
import java.util.ArrayList;

public class UartActivity extends UartInterfaceActivity  {


    // Log
    private final static String TAG = UartActivity.class.getSimpleName();

    // Activity request codes (used for onActivityResult)
    private static final int kActivityRequestCode_ConnectedSettingsActivity = 0;

    // Data
    private boolean mShowDataInHexFormat;
    private volatile SpannableStringBuilder mTextSpanBuffer;
    private volatile ArrayList<UartDataChunk> mDataBuffer;
    private volatile int mReceivedBytes;
    private DataFragment mRetainedDataFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connected_activ_layout);

        setTitle("DJ Glovie");

        mBleManager = BleManager.getInstance(this);
        restoreRetainedDataFragment();
        onServicesDiscovered();
    }




    public void disconnectClick (View view)
    {
        Intent stopIntent = new Intent(this, ForegroundService.class);
        stopIntent.setAction(Constants.ACTION.STOPFOREGROUND_ACTION);
        startService(stopIntent);
        this.finish();
        try {
            Thread.sleep(100);
        } catch(InterruptedException e) {

        }
    }




    public void helpClick (View view)
    {
        Intent help_intent = new Intent(this,help_activ.class);
        startActivity(help_intent);
    }




    public void aboutClick (View view)
    {
        Intent about_intent = new Intent(this,about_activ.class);
        startActivity(about_intent);
    }




    public void contactClick (View view)
    {
        Intent contact_intent = new Intent(this,contact_activ.class);
        startActivity(contact_intent);
    }



    /* disable hardware back button on android phone */
    @Override
    public void onBackPressed() { }




    public void onDestroy() {
        super.onDestroy();
        Intent stopIntent = new Intent(this, ForegroundService.class);
        stopIntent.setAction(Constants.ACTION.STOPFOREGROUND_ACTION);
        startService(stopIntent);
        saveRetainedDataFragment();
    }




    @Override
    public void onResume() {
        super.onResume();
        // Setup listeners
        mBleManager.setBleListener(this);
    }




    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent intent) {
        if (requestCode == kActivityRequestCode_ConnectedSettingsActivity && resultCode == RESULT_OK) {
            finish();
        }
    }




    @Override
    public void onDisconnected() {
        super.onDisconnected();
        Log.d(TAG, "Disconnected. Back to previous activity");
        finish();
    }




    @Override
    public void onServicesDiscovered() {
        super.onServicesDiscovered();
        enableRxNotifications();
    }




    // region DataFragment
    public static class DataFragment extends Fragment {
        private boolean mShowDataInHexFormat;
        private SpannableStringBuilder mTextSpanBuffer;
        private ArrayList<UartDataChunk> mDataBuffer;
        private int mReceivedBytes;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setRetainInstance(true);
        }
    }




    private void restoreRetainedDataFragment() {
        // find the retained fragment
        FragmentManager fm = getFragmentManager();
        mRetainedDataFragment = (DataFragment) fm.findFragmentByTag(TAG);

        if (mRetainedDataFragment == null) {
            // Create
            mRetainedDataFragment = new DataFragment();
            fm.beginTransaction().add(mRetainedDataFragment, TAG).commit();

            mDataBuffer = new ArrayList<>();
            mTextSpanBuffer = new SpannableStringBuilder();
        } else {
            // Restore status
            mShowDataInHexFormat = mRetainedDataFragment.mShowDataInHexFormat;
            mTextSpanBuffer = mRetainedDataFragment.mTextSpanBuffer;
            mDataBuffer = mRetainedDataFragment.mDataBuffer;
            mReceivedBytes = mRetainedDataFragment.mReceivedBytes;
        }
    }




    private void saveRetainedDataFragment() {
        mRetainedDataFragment.mShowDataInHexFormat = mShowDataInHexFormat;
        mRetainedDataFragment.mTextSpanBuffer = mTextSpanBuffer;
        mRetainedDataFragment.mDataBuffer = mDataBuffer;
        mRetainedDataFragment.mReceivedBytes = mReceivedBytes;
    }




    @Override
    public synchronized void onDataAvailable(BluetoothGattCharacteristic characteristic) {
        super.onDataAvailable(characteristic);
        // UART RX
        if (characteristic.getService().getUuid().toString().equalsIgnoreCase(UUID_SERVICE)) {
            if (characteristic.getUuid().toString().equalsIgnoreCase(UUID_RX)) {
                final byte[] bytes = characteristic.getValue();
                mReceivedBytes += bytes.length;

                final UartDataChunk dataChunk = new UartDataChunk(System.currentTimeMillis(), UartDataChunk.TRANSFERMODE_RX, bytes);
                mDataBuffer.add(dataChunk);
                // send received commands to spotify handler
                commandHandler(bytes);
            }
        }
    }




    /* determines which spotify intent the user selected */
    public void commandHandler(byte[] b) {
        String command = new String(b);
        if (command.equals(Constants.SPOTIFY_COMMAND.PLAY_PAUSE)) {
            togglePlayPause();
        } else if (command.equals(Constants.SPOTIFY_COMMAND.NEXT)) {
            nextSong();
        } else if (command.equals(Constants.SPOTIFY_COMMAND.BACK)) {
            previousSong();
        }
    }


    /* sends intent to the spotify app to skip to the next song */
    public void nextSong() {
        Intent playSpotify = new Intent("com.spotify.mobile.android.ui.widget.NEXT");
        playSpotify.setPackage("com.spotify.music");
        getApplicationContext().sendBroadcast(playSpotify);
    }




    /* sends intent to the spotify app to go back to the previous song */
    public void previousSong() {
        Intent playSpotify = new Intent("com.spotify.mobile.android.ui.widget.PREVIOUS");
        playSpotify.setPackage("com.spotify.music");
        getApplicationContext().sendBroadcast(playSpotify);
    }




    /* sends intent to spotify app to play the current song */
    public void playSong() {
        Intent i = new Intent(Intent.ACTION_MEDIA_BUTTON);
        i.setComponent(new ComponentName("com.spotify.music", "com.spotify.music.internal.receiver.MediaButtonReceiver"));
        i.putExtra(Intent.EXTRA_KEY_EVENT,new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MEDIA_PLAY));
        sendOrderedBroadcast(i, null);

        i = new Intent(Intent.ACTION_MEDIA_BUTTON);
        i.setComponent(new ComponentName("com.spotify.music", "com.spotify.music.internal.receiver.MediaButtonReceiver"));
        i.putExtra(Intent.EXTRA_KEY_EVENT,new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_MEDIA_PLAY));
        sendOrderedBroadcast(i, null);
    }




    /* sends intent to the spotify app to pause the current song */
    public void pauseSong() {
        Intent playSpotify = new Intent("com.spotify.mobile.android.ui.widget.PLAY"); /* for some reason, the spotify play intent actually pauses a song instead... */
        playSpotify.setPackage("com.spotify.music");
        getApplicationContext().sendBroadcast(playSpotify);
    }



    /* determine whether the play or pause intent should be broadcast */
    public void togglePlayPause() {
        AudioManager manager = (AudioManager)this.getSystemService(Context.AUDIO_SERVICE);
        if(manager.isMusicActive())
        {
            pauseSong();
        } else {
            playSong();
        }
    }




} // end class UartActivity
