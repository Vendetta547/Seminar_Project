package com.adafruit.bluefruit.le.connect.app;

import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.adafruit.bluefruit.le.connect.ble.BleManager;
import com.adafruit.bluefruit.le.connect.ble.BleUtils;

import java.nio.charset.Charset;
import java.util.Arrays;

public class UartInterfaceActivity extends AppCompatActivity implements BleManager.BleManagerListener {
    // Log
    private final static String TAG = UartInterfaceActivity.class.getSimpleName();

    // Service Constants
    public static final String UUID_SERVICE = "6e400001-b5a3-f393-e0a9-e50e24dcca9e";
    public static final String UUID_RX = "6e400003-b5a3-f393-e0a9-e50e24dcca9e";

    // Data
    protected BleManager mBleManager;
    protected BluetoothGattService mUartService;
    private boolean isRxNotificationEnabled = false;



    // region SendDataWithCompletionHandler
    protected interface SendDataCompletionHandler {
        void sendDataResponse(String data);
    }

    final private Handler sendDataTimeoutHandler = new Handler();
    private Runnable sendDataRunnable = null;
    private SendDataCompletionHandler sendDataCompletionHandler = null;

    // endregion

    @Override
    public void onConnected() {

    }

    @Override
    public void onConnecting() {

    }

    @Override
    public void onDisconnected() {

    }

    @Override
    public void onServicesDiscovered() {
        mUartService = mBleManager.getGattService(UUID_SERVICE);
    }

    protected void enableRxNotifications() {
        isRxNotificationEnabled = true;
        mBleManager.enableNotification(mUartService, UUID_RX, true);
    }

    @Override
    public void onDataAvailable(BluetoothGattCharacteristic characteristic) {
        // Check if there is a pending sendDataRunnable
        if (sendDataRunnable != null) {
            if (characteristic.getService().getUuid().toString().equalsIgnoreCase(UUID_SERVICE)) {
                if (characteristic.getUuid().toString().equalsIgnoreCase(UUID_RX)) {

                    Log.d(TAG, "sendData received data");
                    sendDataTimeoutHandler.removeCallbacks(sendDataRunnable);
                    sendDataRunnable = null;

                    if (sendDataCompletionHandler != null) {
                        final byte[] bytes = characteristic.getValue();
                        final String data = new String(bytes, Charset.forName("UTF-8"));

                        final SendDataCompletionHandler dataCompletionHandler = sendDataCompletionHandler;
                        sendDataCompletionHandler = null;
                        dataCompletionHandler.sendDataResponse(data);
                    }
                }
            }
        }
    }

    @Override
    public void onDataAvailable(BluetoothGattDescriptor descriptor) {

    }

    @Override
    public void onReadRemoteRssi(int rssi) {

    }
}
