package com.spcba.bpassconductor.datamodels;

import com.google.firebase.firestore.PropertyName;

public class Ticket {
    private String id;
    @PropertyName("used")
    private boolean isUsed;
    private Destination destination;

    public Ticket() {
    }


    public Destination getDestination() {
        return destination;
    }

    public String getId() {
        return id;
    }


    @PropertyName("used")
    public boolean isUsed() {
        return isUsed;
    }
}
