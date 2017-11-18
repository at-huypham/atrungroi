package com.atrungroi.atrungroi.ui.fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.atrungroi.atrungroi.R;
import com.atrungroi.atrungroi.models.News;
import com.atrungroi.atrungroi.pref.ConstantUtils;
import com.atrungroi.atrungroi.ui.DetailActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huyphamna.
 */

public class ListGAFragment extends Fragment {
    private RecyclerView mRecyclerViewListGA;
    private ListGAAdapter mAdapter;
    private List<News> mNews;
    private TextView mTvError;
    private DatabaseReference mFirebaseDatabase;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_ga, container, false);
        initView(view);
        setData();
        if (mNews.size() > 0) {
            mRecyclerViewListGA.setVisibility(View.VISIBLE);
            initListGA();
        } else {
            mTvError.setVisibility(View.VISIBLE);
        }
        setupFirebase();
//        initListGA();
        return view;
    }

    private void setupFirebase() {
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference();
        mFirebaseDatabase.child(ConstantUtils.TREE_EVENT).orderByChild("timePost").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.exists()) {
//                    Discuss discuss = dataSnapshot.getValue(Discuss.class);
//                    if (discuss != null && !list.contains(discuss)) {
//                        list.add(discuss);
//                        adapter.notifyDataSetChanged();
//                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void initView(View view) {
        mRecyclerViewListGA = view.findViewById(R.id.recyclerViewListGA);
        mTvError = view.findViewById(R.id.tvError);
    }

    private void setData() {
        mNews = new ArrayList<>();
        mNews.add(new News("GIVE AWAY #1: ZEUS ARCANA", getString(R.string.dummy), R.drawable.img_dummy));
        mNews.add(new News("GIVE AWAY #2: FREE STEAM WALLLET", "Free $5 for the winner", R.drawable.bg_ninja));
        mNews.add(new News("GIVE AWAY #3: FREE STEAM WALLLET", "Free $10 for the winner", R.drawable.ic_logo_red));
        mNews.add(new News("GIVE AWAY #4: FREE STEAM WALLLET", "Free $15 for the winner", R.drawable.bg_ninja));
        mNews.add(new News("GIVE AWAY #5: ZEUS ARCANA", getString(R.string.dummy), R.drawable.img_dummy));
        mNews.add(new News("GIVE AWAY #6: FREE STEAM WALLLET", "Free $25 for the winner", R.drawable.ic_logo_red));
        mNews.add(new News("GIVE AWAY #7: FREE BUNDLE", "ABCXYZ", 0));
    }

    private void initListGA() {
        mAdapter = new ListGAAdapter(mNews, getContext());
        mRecyclerViewListGA.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerViewListGA.setAdapter(mAdapter);
        mRecyclerViewListGA.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int position = parent.getChildAdapterPosition(view);
                outRect.set(0, position == 0 ? 20 : 5, 0, 7);
            }
        });

        mAdapter.setPostListener(new ListGAAdapter.OnClickPostListener() {
            @Override
            public void clickPost(int position, View view) {
                startActivity(new Intent(getActivity(), DetailActivity.class));
            }
        });
    }

}
