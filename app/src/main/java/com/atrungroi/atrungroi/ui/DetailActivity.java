package com.atrungroi.atrungroi.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.atrungroi.atrungroi.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.squareup.picasso.Picasso;

/**
 * Created by huyphamna.
 */

public class DetailActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private TextView mTvToolbar;
    private TextView mTvName;
    private TextView mTvTittleGA;
    private TextView mTvContentGA;
    private ImageView mImgIllustration;
    private ImageView mImgQRCode;
    private TextView mTvTimePost;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mFirebaseDatabase;
    String a;
    String idUserTemp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initView();
        initToolbar();
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mTvName = findViewById(R.id.tvName);
        mTvTittleGA = findViewById(R.id.tvTittleGA);
        mTvContentGA = findViewById(R.id.tvContentGA);
        mImgIllustration = findViewById(R.id.imgIllustration);
        mImgQRCode = findViewById(R.id.imgQRCode);
        mTvTimePost = findViewById(R.id.tvTimePost);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String title = bundle.getString("title", "");
            String dateTimeStart = bundle.getString("dateTimeStart", "");
            String dateTimeEnd = bundle.getString("dateTimeEnd", "");
            String content = bundle.getString("content", "");
            String imagesEvent = bundle.getString("imagesEvent", "");
            String idUser = bundle.getString("idUser", "");
            String nameUser = bundle.getString("nameUser", "");
            String idEvent = bundle.getString("idEvent", "");
            String isCheck = bundle.getString("isCheck", "");
            a = idEvent;
            idUserTemp = idUser;
            mTvName.setText(nameUser);
            mTvTittleGA.setText(title);
            mTvTimePost.setText(dateTimeStart + " => " + dateTimeEnd);
            mTvContentGA.setText(content);
            loadImageFromUrl(imagesEvent, mImgIllustration);
            FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
            if (idUserTemp.equals(firebaseUser.getUid())){
                genericQR();
            } else {
                mImgQRCode.setVisibility(View.GONE);
            }
        }
    }

    private void initView() {
        mToolbar = findViewById(R.id.toobar);
        mTvToolbar = findViewById(R.id.tvToolbar);
    }

    private void initToolbar() {
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(null);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        }
        mTvToolbar.setText("Give away #");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void loadImageFromUrl(String url, ImageView imageView) {
        Picasso.with(getApplicationContext())
                .load(url)
                .placeholder(R.drawable.ic_logo_red)
                .error(R.drawable.ic_logo_red)
                .resize(500, 500)
                .into(imageView);
    }

    private void genericQR() {

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(a, BarcodeFormat.QR_CODE, 200, 200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
//                    Intent intent = new Intent(context, QrActivity.class);
//                    intent.putExtra("pic",bitmap);
//                    context.startActivity(intent);
            mImgQRCode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

}
