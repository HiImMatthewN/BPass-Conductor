package com.spcba.bpassconductor.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.spcba.bpassconductor.datamodels.ScannedItem;
import com.spcba.bpassconductor.repositories.FireStoreRepository;

import java.util.List;

public class TransactionsViewModel extends ViewModel {
    private FireStoreRepository fireStoreRepository = FireStoreRepository.getInstance();

    public TransactionsViewModel() {
        fireStoreRepository.getScannedItems();
    }

    public LiveData<List<ScannedItem>> getScannedItems() {
        return fireStoreRepository.getScannedItemsLiveData();
    }


}
