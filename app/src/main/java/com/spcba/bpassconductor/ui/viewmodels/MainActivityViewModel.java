package com.spcba.bpassconductor.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.spcba.bpassconductor.datamodels.Event;

public class MainActivityViewModel extends ViewModel {
    private MutableLiveData<Event<Boolean>> isCameraPermissionGranted = new MutableLiveData<>();
    private MutableLiveData<Event<Boolean>> resetCameraLiveData = new MutableLiveData<>();


    public void cameraPermissionGranted(boolean isGranted){
        isCameraPermissionGranted.setValue(new Event<>(isGranted));
    }
    public void resetCamera(boolean value){
        resetCameraLiveData.setValue(new Event<>(value));
    }

    public LiveData<Event<Boolean>> getIsCameraPermissionGranted() {
        return isCameraPermissionGranted;
    }

    public LiveData<Event<Boolean>> getResetCameraLiveData() {
        return resetCameraLiveData;
    }
}
