package com.atrungroi.atrungroi.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.atrungroi.atrungroi.R;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initView();
        initToolbar();
        mTvName = findViewById(R.id.tvName);
        mTvTittleGA = findViewById(R.id.tvTittleGA);
        mTvContentGA = findViewById(R.id.tvContentGA);
        mImgIllustration = findViewById(R.id.imgIllustration);
        mImgQRCode = findViewById(R.id.imgQRCode);
        mTvTimePost = findViewById(R.id.tvTimePost);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            String title = bundle.getString("title", "");
            String dateTimeStart = bundle.getString("dateTimeStart", "");
            String dateTimeEnd = bundle.getString("dateTimeEnd", "");
            String content = bundle.getString("content", "");
            String imagesEvent = bundle.getString("imagesEvent", "");
            String idUser = bundle.getString("idUser", "");
            String nameUser = bundle.getString("nameUser", "");
            String idEvent = bundle.getString("idEvent", "");
            String isCheck = bundle.getString("isCheck", "");
            mTvName.setText(nameUser);
            mTvTittleGA.setText(title);
            mTvTimePost.setText( dateTimeStart + " => " + dateTimeEnd);
            mTvContentGA.setText(content);
            loadImageFromUrl(imagesEvent, mImgIllustration);
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
            case R.id.menuScan:
                Toast.makeText(this, "Show camera or choose from library", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_scan_qr, menu);
        menu.findItem(R.id.menuScan);
        return super.onCreateOptionsMenu(menu);
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
}
