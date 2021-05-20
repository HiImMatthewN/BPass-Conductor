
package com.spcba.bpassconductor.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.spcba.bpassconductor.databinding.FragmentQrScannerBinding;
import com.spcba.bpassconductor.helpers.AppPermissionHelper;
import com.spcba.bpassconductor.helpers.QRScannerHelper;
import com.spcba.bpassconductor.ui.viewmodels.MainActivityViewModel;
import com.spcba.bpassconductor.ui.viewmodels.QRScannerViewModel;


public class QRScannerFragment extends Fragment {
    private FragmentQrScannerBinding binder;
    private FrameLayout qrScannerContainer;
    private QRScannerHelper qrScannerHelper;
    private QRScannerViewModel qrScannerViewModel;
    private TextView scanSuccessTv;
    private MainActivityViewModel mainActivityViewModel;
    private String TAG = "QRScannerFragment";
    private boolean enableScanning = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binder = FragmentQrScannerBinding.inflate(inflater, container, false);
        return binder.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        qrScannerContainer = binder.qrScannerContainer;
        qrScannerViewModel = new ViewModelProvider(this).get(QRScannerViewModel.class);
        mainActivityViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
        scanSuccessTv = binder.scanSuccessMsg;
        qrScannerHelper = new QRScannerHelper(requireContext());
        Log.d(TAG, "onViewCreated: ");


        qrScannerHelper.scannedValue().observe(getViewLifecycleOwner(), scannedEvent -> {
            if (scannedEvent.isHandled()) return;
            qrScannerHelper.stopCameraSource();
            qrScannerViewModel.scannedQrCode(scannedEvent.getContentIfNotHandled());
            scanSuccessTv.setVisibility(View.VISIBLE);
        });

        mainActivityViewModel.getIsCameraPermissionGranted().observe(getViewLifecycleOwner(), grantedEvent -> {
            if (grantedEvent.isHandled()) return;
            if (grantedEvent.getContentIfNotHandled())
                qrScannerHelper.initQRScannerPreview();

        });
        mainActivityViewModel.getResetCameraLiveData().observe(getViewLifecycleOwner(), resetCameraEvent -> {
            if (resetCameraEvent.isHandled()) return;
            if (resetCameraEvent.getContentIfNotHandled()) {
                qrScannerContainer.removeAllViews();
                qrScannerContainer.addView(qrScannerHelper.getQrScannerSurfaceView());
                qrScannerHelper.initQRScannerPreview();

                scanSuccessTv.setVisibility(View.GONE);

            }


        });


    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: " + AppPermissionHelper.cameraPermissionGranted(getContext()));
        if (AppPermissionHelper.cameraPermissionGranted(getContext())) {
            qrScannerContainer.addView(qrScannerHelper.getQrScannerSurfaceView());
            qrScannerHelper.initQRScannerPreview();
        } else {
            AppPermissionHelper.requestPermission(getContext());
            Log.d(TAG, "onResume: Requesting Permission");
        }

    }


    @Override
    public void onPause() {
        super.onPause();
        qrScannerContainer.removeAllViews();
    }
}
