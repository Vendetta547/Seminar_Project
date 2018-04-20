package com.adafruit.bluefruit.le.connect.app;

public class Constants {


    /* actions to control foreground service notification */
    public interface ACTION {
        public static String MAIN_ACTION = "com.nkdroid.alertdialog.action.main";
        public static String STARTFOREGROUND_ACTION = "com.nkdroid.alertdialog.action.startforeground";
        public static String STOPFOREGROUND_ACTION = "com.nkdroid.alertdialog.action.stopforeground";
    }

    public interface NOTIFICATION_ID {
        public static int FOREGROUND_SERVICE = 101; /* unique int id for foreground service notification (required by android) */
    }

    /* commands that will be received from arduino feather board */
    public interface SPOTIFY_COMMAND {
        public static String PLAY_PAUSE = "play/pause";
        public static String NEXT = "next";
        public static String BACK = "previous";
    }

    public static String GLOVE_NAME = "Adafruit Bluefruit LE";


}
