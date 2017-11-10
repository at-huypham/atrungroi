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
import com.atrungroi.atrungroi.models.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huyphamna.
 */
public class ResultGAFragment extends Fragment{
    private TextView mTvError;
    private RecyclerView mRecyclerViewResult;
    private List<Result> mResults;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result_ga, container, false);
        initView(view);
        setData();
        if (mResults.size() > 0){
            mRecyclerViewResult.setVisibility(View.VISIBLE);
        } else {
            mTvError.setVisibility(View.VISIBLE);
        }
        return view;
    }

    private void initView(View view){
        mTvError = view.findViewById(R.id.tvErrorResult);
        mRecyclerViewResult = view.findViewById(R.id.recyclerViewListResultGA);
    }

    private void setData(){
        mResults = new ArrayList<>();
    }
}
