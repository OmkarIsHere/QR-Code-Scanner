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

import android.util.Log;
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
    private CodeScannerView codeScannerView;
    private CodeScanner codeScanner;
    private TextView qrEncode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        codeScannerView= findViewById(R.id.qrCodeScannerView);
        qrEncode= findViewById(R.id.txtViewEncode);

        final int PERMISSION_ALL = 1;
        String[] permissions = { android.Manifest.permission.CAMERA };
        Log.d(TAG, "onCreate: permission ");
        try {
            if(hasPermissions(this, permissions)) {
                Log.d(TAG, "before runCode Method ");
                runCodeScanner();
                Log.d(TAG, "after runCodeMethod: ");
            }else
                ActivityCompat.requestPermissions(this , permissions, PERMISSION_ALL);
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Developer is solving virtual null pointer error. Stay tune...", Toast.LENGTH_SHORT).show();
            Log.d(TAG, e.toString());
        }

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
        codeScanner = new CodeScanner(QRscannerActivity.this, codeScannerView);
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