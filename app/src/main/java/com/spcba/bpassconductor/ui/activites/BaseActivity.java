package com.spcba.bpassconductor.ui.activites;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.spcba.bpassconductor.App;
import com.spcba.bpassconductor.interfaces.LogoutListener;

public class BaseActivity extends AppCompatActivity implements LogoutListener {
    private static final String TAG = "BaseActivity";
    public static  boolean showPinFragment = true;
    @Override
    protected void onResume() {
        super.onResume();
        App.registerSessionListener(this);
    }

    @Override
    public void onSessionLogout() {
        Log.d(TAG, "onSessionLogout: Called");
        showPinFragment = true;
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        App.resetSession();
        showPinFragment = false;

    }
}
