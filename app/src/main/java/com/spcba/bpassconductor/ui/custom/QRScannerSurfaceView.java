package com.spcba.bpassconductor.ui.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.spcba.bpassconductor.datamodels.Event;

import java.io.IOException;
@SuppressLint("MissingPermission")
public class QRScannerSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private CameraSource cameraSource;
    private boolean enableBarCodeDetection = true;
    private MutableLiveData<Event<String>> barcodeScanned = new MutableLiveData<>();
    private SurfaceHolder holder;

    private static final String TAG = "QRScannerSurfaceView";


    public QRScannerSurfaceView(Context context,CameraSource cameraSource) {
        super(context);
        this.cameraSource = cameraSource;

    }
    public void initQrScanner(BarcodeDetector barcodeDetector){
        setBarcodeProcessor(barcodeDetector);
        getHolder().addCallback(this);

    }
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
            try {
                this.holder = holder;
                cameraSource.start(holder);

            } catch (IOException e) {
                e.printStackTrace();
            }

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        cameraSource.stop();

    }
    private void setBarcodeProcessor(BarcodeDetector barcodeDetector){



        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
            }

            @Override
            public void receiveDetections(@NonNull Detector.Detections<Barcode> detections) {
                Log.d(TAG, "receiveDetections: Scanning: " + enableBarCodeDetection);

                if (!enableBarCodeDetection) return;

                final SparseArray<Barcode> barcodeSparseArray = detections.getDetectedItems();



                if (barcodeSparseArray.size() != 0) {

                    String scannedValue = barcodeSparseArray.valueAt(0).displayValue;
                    Log.d(TAG, "receiveDetections: " + scannedValue);

                    barcodeDetector.release();

                    barcodeScanned.postValue(new Event<>(scannedValue));
                    enableBarCodeDetection = true;


                }

            }
        });
    }

    public LiveData<Event<String>> getBarcodeScanned() {
        return barcodeScanned;
    }
    public void stopCameraSource(){
        cameraSource.stop();
    }
    public void startCamera(){
        try {
            cameraSource.start(holder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void enableBarcodeScanning(boolean value) {
        enableBarCodeDetection = value;


    }
}
