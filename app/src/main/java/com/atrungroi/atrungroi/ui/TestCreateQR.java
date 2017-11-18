package com.atrungroi.atrungroi.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.atrungroi.atrungroi.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

/**
 * Created by huyphamna on 18/11/2017.
 */

public class TestCreateQR extends AppCompatActivity {
    private Button mBtnButton;
    private EditText mEdtEdit;
    private ImageView mImgImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic_qr);
        final Context context = this;
        initView();
        genericQR();
//        Bitmap bitmap = getIntent().getParcelableExtra("pic");
//        mImgImage.setImageBitmap(bitmap);
    }

    private void initView(){
        mImgImage = findViewById(R.id.imageView);
        mEdtEdit = findViewById(R.id.editText);
        mBtnButton = findViewById(R.id.button);
    }

    private void genericQR(){
        mBtnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text2Qr = mEdtEdit.getText().toString();
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try {
                    BitMatrix bitMatrix = multiFormatWriter.encode(text2Qr, BarcodeFormat.QR_CODE,200,200);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
//                    Intent intent = new Intent(context, QrActivity.class);
//                    intent.putExtra("pic",bitmap);
//                    context.startActivity(intent);
                    mImgImage.setImageBitmap(bitmap);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
