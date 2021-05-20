package com.spcba.bpassconductor.ui.viewmodels;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.spcba.bpassconductor.repositories.FireStoreRepository;

public class QRScannerViewModel extends ViewModel {
    private FireStoreRepository fireStoreRepository = FireStoreRepository.getInstance();
    private static final String TAG = "QRScannerViewModel";


    public void scannedQrCode(String value) {
        String[] qrCodeValues = value.split("-");
        if (qrCodeValues.length < 3) return;

        String type = qrCodeValues[0];
        String uid = qrCodeValues[1];
        String id = qrCodeValues[2];

        if (type.equals("Ticket"))
            fireStoreRepository.acceptTicket(uid, id);
        else if (type.equals("Receipt"))
            fireStoreRepository.acceptReceipt(uid, id);


        Log.d(TAG, "Transaction type " + type + " User UID: " + uid + "Dd " + id);


    }


}
