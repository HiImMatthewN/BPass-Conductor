package com.spcba.bpassconductor;

import android.app.Application;
import android.util.Log;

import com.spcba.bpassconductor.interfaces.LogoutListener;

import java.util.Timer;
import java.util.TimerTask;

public class App extends Application {
    private static LogoutListener logoutListener = null;
    private static Timer timer = null;
    private static final String TAG = "App";
    @Override
    public void onCreate() {
        super.onCreate();
    }


    public static void userSessionStart() {
        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (logoutListener != null) {
                    logoutListener.onSessionLogout();
                    Log.d("App", "Session Destroyed");
                }
            }
        },  (1000 * 60  ) );
    }

    public static void resetSession() {
        userSessionStart();
    }

    public static void registerSessionListener(LogoutListener listener) {
        logoutListener = listener;
    }
}
