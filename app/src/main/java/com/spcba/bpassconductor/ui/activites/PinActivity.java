package com.spcba.bpassconductor.ui.activites;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.spcba.bpassconductor.databinding.ActivityPinBinding;
import com.spcba.bpassconductor.ui.viewmodels.PinActivityViewModel;

public class PinActivity extends AppCompatActivity {
    private ActivityPinBinding binder;
    private EditText pinEditText;


    private Button btnOne;
    private Button btnTwo;
    private Button btnThree;
    private Button btnFour;
    private Button btnFive;
    private Button btnSix;
    private Button btnSeven;
    private Button btnEight;
    private Button btnNine;
    private Button btnZero;
    private static final String TAG = "PinActivity";

    private PinActivityViewModel pinActivityViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = ActivityPinBinding.inflate(getLayoutInflater());
        pinActivityViewModel = new ViewModelProvider(this).get(PinActivityViewModel.class);
        setContentView(binder.getRoot());
        pinEditText = binder.pinEt;
        btnOne = binder.pinBtnOne;
        btnTwo = binder.pinBtnTwo;
        btnThree = binder.pinBtnThree;
        btnFour = binder.pinBtnFour;
        btnFive = binder.pinBtnFive;
        btnSix = binder.pinBtnSix;
        btnSeven = binder.pinBtnSeven;
        btnEight = binder.pinBtnEight;
        btnNine = binder.pinBtnNine;
        btnZero = binder.pinBtnZero;


        btnOne.setOnClickListener(btn->{
            pinActivityViewModel.enterPinValue("1");
        });
        btnTwo.setOnClickListener(btn->{
            pinActivityViewModel.enterPinValue("2");
        });
        btnThree.setOnClickListener(btn->{
            pinActivityViewModel.enterPinValue("3");
        });
        btnFour.setOnClickListener(btn->{
            pinActivityViewModel.enterPinValue("4");
        });
        btnFive.setOnClickListener(btn->{
            pinActivityViewModel.enterPinValue("5");
        });
        btnSix.setOnClickListener(btn->{
            pinActivityViewModel.enterPinValue("6");
        });
        btnSeven.setOnClickListener(btn->{
            pinActivityViewModel.enterPinValue("7");
        });
        btnEight.setOnClickListener(btn->{
            pinActivityViewModel.enterPinValue("8");
        });
        btnNine.setOnClickListener(btn->{
            pinActivityViewModel.enterPinValue("9");
        });
        btnZero.setOnClickListener(btn->{
            pinActivityViewModel.enterPinValue("0");
        });






        pinEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count ==6)
                    pinActivityViewModel.checkEnteredPin();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        pinActivityViewModel.getInputEditTextLiveData().observe(this,inputEvent->{
                    if (inputEvent.isHandled()) return;
                    pinEditText.setText(inputEvent.getContentIfNotHandled());


        });
        pinActivityViewModel.getEnteredPinVerification().observe(this,enteredPinEvent->{
            if (enteredPinEvent.isHandled()) return;
            if (enteredPinEvent.getContentIfNotHandled()){
                goToLobbyActivity();
                finish();

            }
            else
                Toast.makeText(this, "Incorrect Pin", Toast.LENGTH_SHORT).show();



        });






    }

    private void goToLobbyActivity(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);


    }
}
