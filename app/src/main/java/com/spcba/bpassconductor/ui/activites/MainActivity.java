package com.spcba.bpassconductor.ui.activites;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.spcba.bpassconductor.R;
import com.spcba.bpassconductor.databinding.ActivityMainBinding;
import com.spcba.bpassconductor.helpers.AppPermissionHelper;
import com.spcba.bpassconductor.ui.viewmodels.MainActivityViewModel;

public class MainActivity extends BaseActivity {
    private ActivityMainBinding binder;
    private MainActivityViewModel mainActivityViewModel;
    private static final String TAG = "MainActivity";
    private FloatingActionButton resetCameraBtn;
    private View qrScannerView;
    private View transactionView;
    private AppCompatImageButton qrScannerBtn;
    private AppCompatImageButton transactionBtn;
    private NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        binder = ActivityMainBinding.inflate(getLayoutInflater());

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
        setContentView(binder.getRoot());

        resetCameraBtn = binder.resetCameraBtn;
        qrScannerView = binder.qrScannerView;
        transactionView = binder.transactionView;
        qrScannerBtn = binder.qrScannerBtn;
        transactionBtn = binder.transactionBtn;
        resetCameraBtn.setOnClickListener(btn->{
            if (navController.getCurrentDestination().getId() == R.id.transactionFragment) return;
            mainActivityViewModel.resetCamera(true);

        });

        qrScannerView.setOnClickListener(view->{
            goToQrScannerFragment();
        });
        qrScannerBtn.setOnClickListener(btn->{
            goToQrScannerFragment();

        });

        transactionView.setOnClickListener(view->{
            goToTransactionFragment();

        });
        transactionBtn.setOnClickListener(btn->{
            goToQrScannerFragment();

        });




    }
    private void goToQrScannerFragment(){
        if (navController.getGraph().getStartDestination() == navController.getCurrentDestination().getId())
            return;
        navController.popBackStack();
        qrScannerBtn.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_qr_scanner_selected));
        transactionBtn.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_transaction_unselected));

    }
    private void goToTransactionFragment(){
        if (navController.getCurrentDestination().getId() == R.id.transactionFragment) return;
        navController.navigate(R.id.action_qrScannerFragment_to_transactionFragment);
        qrScannerBtn.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_qr_scanner_unselected));
        transactionBtn.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_transaction_selected));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == AppPermissionHelper.REQUEST_ALL_PERMISSION) {
            mainActivityViewModel
                    .cameraPermissionGranted(grantResults[0]
                            == PackageManager.PERMISSION_GRANTED);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (BaseActivity.showPinFragment)
            Log.d(TAG, "onResume: showing pin fragment");
        else
            Log.d(TAG, "onResume: not showing pin fragment");

    }
}