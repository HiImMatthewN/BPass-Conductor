package com.spcba.bpassconductor.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.spcba.bpassconductor.datamodels.Event;

public class PinActivityViewModel extends ViewModel {

    private String currentEnteredPin  ="";
    private String pin = "123456";
    private static final String TAG = "PinActivityViewModel";
    private MutableLiveData<Event<String>> inputEditTextLiveData = new MutableLiveData<>();
    private MutableLiveData<Event<Boolean>> enteredPinLiveData = new MutableLiveData<>();
    public void enterPinValue(String value){
        currentEnteredPin = currentEnteredPin.concat(value);
        inputEditTextLiveData.setValue(new Event<>(currentEnteredPin));

    }

    public void checkEnteredPin(){
        if (pin.equals(currentEnteredPin))
            enteredPinLiveData.postValue(new Event<>(true));
        else{
            currentEnteredPin = "";

            enteredPinLiveData.postValue(new Event<>(false));
            inputEditTextLiveData.postValue(new Event<>(currentEnteredPin));

        }


    }

    public LiveData<Event<String>> getInputEditTextLiveData() {
        return inputEditTextLiveData;
    }
    public LiveData<Event<Boolean>> getEnteredPinVerification(){
        return enteredPinLiveData;
    }
}
