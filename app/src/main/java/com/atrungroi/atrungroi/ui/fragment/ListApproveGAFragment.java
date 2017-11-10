package com.atrungroi.atrungroi.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.atrungroi.atrungroi.R;
import com.atrungroi.atrungroi.models.Approve;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huyphamna.
 */

public class ListApproveGAFragment extends Fragment{
    private TextView mTvError;
    private RecyclerView mRecyclerViewApprove;
    private List<Approve> mApproves;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_approve_ga, container, false);
        initView(view);
        setData();
        if (mApproves.size() > 0){
            mRecyclerViewApprove.setVisibility(View.VISIBLE);
        } else {
            mTvError.setVisibility(View.VISIBLE);
        }
        return view;
    }

    private void initView(View view){
        mTvError = view.findViewById(R.id.tvErrorApprove);
        mRecyclerViewApprove = view.findViewById(R.id.recyclerViewListApproveGA);
    }

    private void setData(){
        mApproves = new ArrayList<>();
    }
}
