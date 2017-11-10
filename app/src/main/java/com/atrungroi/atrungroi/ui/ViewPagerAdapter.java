package com.atrungroi.atrungroi.ui;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by huyphamna.
 */

public class ViewPagerAdapter extends PagerAdapter {
    private int[] layout;
    private Context mContext;

    public ViewPagerAdapter(int[] layout, Context mContext) {
        this.layout = layout;
        this.mContext = mContext;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext).inflate(layout[position], container, false);
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return layout.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}
