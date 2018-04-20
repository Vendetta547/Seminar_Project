package com.adafruit.bluefruit.le.connect.app;

class UartDataChunk {


    static final int TRANSFERMODE_RX = 1;

    private byte[] mData;

    UartDataChunk(long timestamp, int mode, byte[] bytes) {
        mData = bytes;
    }

    public byte[] getData() {
        return mData;
    }


} // end class UartDataChunk
