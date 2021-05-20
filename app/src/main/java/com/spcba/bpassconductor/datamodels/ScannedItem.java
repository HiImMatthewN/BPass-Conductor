package com.spcba.bpassconductor.datamodels;

import java.util.Date;

public class ScannedItem {
    private String type;
    private String refId;
    private int amount;
    private Date dateScanned;

    public ScannedItem() {
    }

    public ScannedItem(String type, String refId, int amount, Date dateScanned) {
        this.type = type;
        this.refId = refId;
        this.amount = amount;
        this.dateScanned = dateScanned;
    }

    public String getType() {
        return type;
    }

    public String getRefId() {
        return refId;
    }

    public int getAmount() {
        return amount;
    }

    public Date getDateScanned() {
        return dateScanned;
    }
}
