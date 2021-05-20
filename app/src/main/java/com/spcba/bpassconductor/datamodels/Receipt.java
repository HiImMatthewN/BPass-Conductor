package com.spcba.bpassconductor.datamodels;

import com.google.firebase.firestore.PropertyName;

public class Receipt {
    private String refNumber;
    @PropertyName("paid")
    private boolean isPaid;
    private int amount;
    public Receipt() {
    }

    public int getAmount() {
        return amount;
    }

    public String getRefNumber() {
        return refNumber;
    }


    @PropertyName("paid")
    public boolean isPaid() {
        return isPaid;
    }
}
