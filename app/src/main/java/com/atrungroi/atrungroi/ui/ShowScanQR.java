package com.atrungroi.atrungroi.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.widget.Toast;

import com.atrungroi.atrungroi.R;
import com.atrungroi.atrungroi.ui.menu.MenuActivity;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import info.androidhive.barcode.BarcodeReader;

/**
 * Created by huyphamna on 19/11/2017.
 */

public class ShowScanQR extends AppCompatActivity implements BarcodeReader.BarcodeReaderListener {
    BarcodeReader barcodeReader;
    String a;
//    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
//        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference();
        // get the barcode reader instance
        barcodeReader = (BarcodeReader) getSupportFragmentManager().findFragmentById(R.id.barcode_scanner);
    }

    @Override
    public void onScanned(final Barcode barcode) {

        // playing barcode reader beep sound
        barcodeReader.playBeep();
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                Toast.makeText(getApplicationContext(), "Barcode: " + barcode.displayValue, Toast.LENGTH_SHORT).show();
//
//                handler.postDelayed(new Runnable() {
//                    public void run() {
//                        finish();
//                    }
//                }, 70);
//            }
//        });

        // ticket details activity by passing barcode
        Intent intent = new Intent(ShowScanQR.this, MenuActivity.class);
        intent.putExtra("code", barcode.displayValue);
        startActivity(intent);
        finish();
    }

    @Override
    public void onScannedMultiple(List<Barcode> list) {

    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }

    @Override
    public void onCameraPermissionDenied() {
        finish();
    }

    @Override
    public void onScanError(String s) {
        Toast.makeText(getApplicationContext(), "Error occurred while scanning " + s, Toast.LENGTH_SHORT).show();
    }


}
