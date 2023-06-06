package com.example.qrcodescanner;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.budiyev.android.codescanner.ScanMode;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.qrcodescanner.databinding.ActivityQrscannerBinding;
import com.google.zxing.Result;

public class QRscannerActivity extends AppCompatActivity {
    private static final String TAG = "QRscannerActivity";
    CodeScannerView codeScannerView;
    CodeScanner codeScanner;
    TextView qrEncode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        codeScannerView= findViewById(R.id.qrCodeScannerView);
        qrEncode= findViewById(R.id.txtViewEncode);

        int PERMISSION_ALL=1;
        String[] permissions = { android.Manifest.permission.CAMERA };
        if(hasPermissions(this, permissions))
            runCodeScanner();
        else
            ActivityCompat.requestPermissions(this , permissions, PERMISSION_ALL);

    }

    public static boolean hasPermissions(Context context, String...permissions){
        if(context!= null && permissions != null){
            for(String permission: permissions){
                if(ActivityCompat.checkSelfPermission(context, permission)!= PackageManager.PERMISSION_GRANTED)
                    return false;
            }
        }
        return true;
    }
    private void runCodeScanner() {
        codeScanner = new CodeScanner(this, codeScannerView);
        codeScanner.setAutoFocusEnabled(true);
        codeScanner.setFormats(CodeScanner.ALL_FORMATS);
        codeScanner.setScanMode(ScanMode.CONTINUOUS);

        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String data = result.getText().trim();
                        qrEncode.setText(data);

                    }

                });
            }
        });
    }

}