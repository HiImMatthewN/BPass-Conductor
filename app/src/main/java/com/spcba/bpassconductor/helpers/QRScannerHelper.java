package com.spcba.bpassconductor.helpers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.spcba.bpassconductor.datamodels.Event;
import com.spcba.bpassconductor.ui.custom.QRScannerSurfaceView;

@SuppressLint("MissingPermission")
public class QRScannerHelper extends ContextWrapper {
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private String TAG = "QRScannerHelper";
    private QRScannerSurfaceView qrScannerSurfaceView;

    public QRScannerHelper(Context base) {
        super(base);
        Log.d(TAG, "QRScannerHelper: Created");
        barcodeDetector = new BarcodeDetector.Builder(getApplicationContext())
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        cameraSource = new CameraSource.Builder(getApplicationContext(), barcodeDetector)
                .setRequestedPreviewSize(720, 480)
                .setRequestedFps(60.0F)
                .setAutoFocusEnabled(true).build();

        qrScannerSurfaceView = new QRScannerSurfaceView(getApplicationContext(),cameraSource);


    }

    public void initQRScannerPreview() {
        qrScannerSurfaceView.initQrScanner(barcodeDetector);
    }
    public void stopCameraSource() {
        qrScannerSurfaceView.stopCameraSource();

    }


    public void startCamera() {
        if (AppPermissionHelper.cameraPermissionGranted(getApplicationContext())) {
            qrScannerSurfaceView.startCamera();

        }
    }


    public void enableBarcodeScanning(boolean value) {
       qrScannerSurfaceView.enableBarcodeScanning(value);
    }


    public LiveData<Event<String>> scannedValue() {
        return qrScannerSurfaceView.getBarcodeScanned();

    }

    public QRScannerSurfaceView getQrScannerSurfaceView() {
        return qrScannerSurfaceView;
    }


}
