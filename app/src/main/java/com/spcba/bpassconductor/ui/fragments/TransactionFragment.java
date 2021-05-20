package com.spcba.bpassconductor.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.spcba.bpassconductor.databinding.FragmentTransactionsBinding;
import com.spcba.bpassconductor.ui.adapters.ScannedItemsAdapter;
import com.spcba.bpassconductor.ui.viewmodels.TransactionsViewModel;

public class TransactionFragment extends Fragment {
    private FragmentTransactionsBinding binder;
    private RecyclerView scannedItemsRv;
    private ScannedItemsAdapter scannedItemsAdapter;
    private TransactionsViewModel transactionsViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binder = FragmentTransactionsBinding.inflate(inflater,container,false);
        return binder.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        transactionsViewModel = new ViewModelProvider(this).get(TransactionsViewModel.class);
        scannedItemsRv = binder.scannedItemsRv;
        scannedItemsAdapter = new ScannedItemsAdapter();
        scannedItemsRv.setAdapter(scannedItemsAdapter);

        transactionsViewModel.getScannedItems().observe(getViewLifecycleOwner(),scannedItems -> {
            scannedItemsAdapter.insertItems(scannedItems);

        });






    }
}
