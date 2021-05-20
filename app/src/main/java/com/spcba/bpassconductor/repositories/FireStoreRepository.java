package com.spcba.bpassconductor.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.spcba.bpassconductor.datamodels.Receipt;
import com.spcba.bpassconductor.datamodels.ScannedItem;
import com.spcba.bpassconductor.datamodels.Ticket;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FireStoreRepository {
    private static FireStoreRepository instance;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "FireStoreRepository";

    private MutableLiveData<List<ScannedItem>> scannedItemsLiveData = new MutableLiveData<>();

    public static FireStoreRepository getInstance() {
        if (instance == null)
            instance = new FireStoreRepository();
        return instance;
    }

    public void getScannedItems(){
        Log.d(TAG, "getScannedItems: Fetching Scanned Items");
        db.collection("Conductor").document("Transactions")
        .collection("ScannedItems").orderBy("dateScanned", Query.Direction.DESCENDING)
        .addSnapshotListener(((value, error) -> {
            if (error != null){
                Log.d(TAG, "getScannedItems: Error Fetching Scanned Items " + error.getMessage());
                return;
            }
            if (value == null) {
                Log.d(TAG, "getScannedItems: Value is Null");
                return;
            }
            List<ScannedItem> scannedItems = value.toObjects(ScannedItem.class);
            scannedItemsLiveData.postValue(scannedItems);
        }));


    }
    public void acceptTicket(String uid, String ticketId) {
        db.collection("Users").document(uid)
                .collection("tickets")
                .whereEqualTo("used",false)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()) {
                        Ticket ticket = snapshot.toObject(Ticket.class);
                        Log.d(TAG, "acceptTicket: Id" + ticket.getId() + " isUsed" + ticket.isUsed());
                        if (ticket.getId().equals(ticketId)) {
                            updateTicketStatus(uid,snapshot.getId());
                            ScannedItem scannedItem = new ScannedItem("Ticket",ticketId,ticket.getDestination().getFare(),new Date());
                            addToScannedHistory(scannedItem);
                        }
                    }
                });
    }

    public void acceptReceipt(String uid, String refNum) {
        db.collection("Users").document(uid)
                .collection("loadTransactions")
                .whereEqualTo("paid",false)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()) {
                        Receipt receipt = snapshot.toObject(Receipt.class);
                        Log.d(TAG, "acceptReceipt: Ref Num" + receipt.getRefNumber() + " isUsed" + receipt.isPaid());
                        if (receipt.getRefNumber().equals(refNum)) {
                            updateReceiptStatus(uid,snapshot.getId());
                            incrementUserBalance(uid,receipt.getAmount());
                            ScannedItem scannedItem = new ScannedItem("Receipt",refNum,receipt.getAmount(),new Date());
                            addToScannedHistory(scannedItem);
                        }
                    }
                });
    }

    private void updateTicketStatus(String uid,String ticketDocId) {
        Log.d(TAG, "acceptTicket: Ticket id found, updating 'used' field");
        Map<String, Object> map = new HashMap<>();
        map.put("used", true);
        db.collection("Users").document(uid)
                .collection("tickets")
                .document(ticketDocId)
                .update(map).addOnSuccessListener(task -> {
            Log.d(TAG, "acceptTicket: Updating field success");
        });

    }
    private void updateReceiptStatus(String uid,String receiptDocId) {
        Log.d(TAG, "updateReceiptStatus: Receipt id found, updating 'paid' field");
        Map<String, Object> map = new HashMap<>();
        map.put("paid", true);
        db.collection("Users").document(uid)
                .collection("loadTransactions")
                .document(receiptDocId)
                .update(map).addOnSuccessListener(task -> {
            Log.d(TAG, "updateReceiptStatus: Updating field success");
        });
    }
    private void incrementUserBalance(String uid,int amount){
        Log.d(TAG, "incrementUserBalance: Increasing Users Balance");
        db.collection("Users").document(uid).update("balance", FieldValue.increment(amount))
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                    Log.d(TAG, "incrementUserBalance: Incremented User Balance");
                });
    }
    private void addToScannedHistory(ScannedItem scannedItem){
        Log.d(TAG, "addToScannedHistory: Adding to Scanned Item");
        db.collection("Conductor").document("Transactions")
                .collection("ScannedItems").add(scannedItem)
                .addOnCompleteListener(task->{
                    if (task.isSuccessful())
                        Log.d(TAG, "Added Item Successfully");
                    else
                        Log.d(TAG, "Failed Adding Item");

                });
    }

    public LiveData<List<ScannedItem>> getScannedItemsLiveData() {
        return scannedItemsLiveData;
    }
}