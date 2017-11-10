package com.atrungroi.atrungroi.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atrungroi.atrungroi.R;
import com.atrungroi.atrungroi.pref.PrefManager;

/**
 * Created by huyphamna.
 * Activity for intro screen.
 */
public class IntroActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private ViewPagerAdapter mViewPagerAdapter;
    private LinearLayout mLlDot;
    private TextView[] mTvDot;
    private int[] layout;
    private Button mBtnNext;
    private Button mBtnSkip;
    private PrefManager mPrefManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

//        checkPref();
        changeStatusBarColor();
        initView();
        initViewPagerWelcome();
        addBottomDots(0);
        initButton();
    }

//    private void checkPref() {
//        mPrefManager = new PrefManager(this);
//        if (!mPrefManager.isFirstTime()) {
//            launchHomeScreen();
//            finish();
//        }
//    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private void initView() {
        mViewPager = findViewById(R.id.viewPagerWelcome);
        mLlDot = findViewById(R.id.llDots);
        mBtnNext = findViewById(R.id.btnNext);
        mBtnSkip = findViewById(R.id.btnSkip);
    }

    private void initViewPagerWelcome() {
        layout = new int[]{
                R.layout.item_welcome_slide1,
                R.layout.item_welcome_slide2,
                R.layout.item_welcome_slide3,
                R.layout.item_welcome_slide4
        };

        mViewPagerAdapter = new ViewPagerAdapter(layout, this);
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                addBottomDots(position);
                if (position == layout.length - 1) {
                    // last page. make button text to GOT IT
                    mBtnNext.setText(getString(R.string.start));
                    mBtnSkip.setVisibility(View.GONE);
                } else {
                    // still pages are left
                    mBtnNext.setText(getString(R.string.next));
                    mBtnSkip.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initButton() {
        mBtnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchHomeScreen();
            }
        });

        mBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentItem = getItem(1);
                if (currentItem < layout.length) {
                    mViewPager.setCurrentItem(currentItem);
                } else {
                    launchHomeScreen();
                }
            }
        });
    }

    private void launchHomeScreen() {
        mPrefManager = new PrefManager(this);
        mPrefManager.setFirstTimeLauch(false);
        startActivity(new Intent(IntroActivity.this, MainActivity.class));
        finish();
    }

    private void addBottomDots(int currentPage) {
        mTvDot = new TextView[layout.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        mLlDot.removeAllViews();
        for (int i = 0; i < mTvDot.length; i++) {
            mTvDot[i] = new TextView(this);
            mTvDot[i].setText(Html.fromHtml("&#8226;"));
            mTvDot[i].setTextSize(35);
            mTvDot[i].setTextColor(colorsInactive[currentPage]);
            mLlDot.addView(mTvDot[i]);
        }

        if (mTvDot.length > 0)
            mTvDot[currentPage].setTextColor(colorsActive[currentPage]);
    }

    private int getItem(int i) {
        return mViewPager.getCurrentItem() + i;
    }
}
