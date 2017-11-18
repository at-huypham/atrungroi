package com.atrungroi.atrungroi.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.atrungroi.atrungroi.R;
import com.atrungroi.atrungroi.models.Event;
import com.atrungroi.atrungroi.pref.ConstantUtils;
import com.atrungroi.atrungroi.ui.DetailActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import static com.atrungroi.atrungroi.pref.ConstantUtils.TAG;

/**
 * Created by huyphamna.
 */

public class ListGAFragment extends Fragment {
    private RecyclerView mRecyclerViewListGA;
    private ListGAAdapter mAdapter;
    private List<Event> mListEvent = new ArrayList<>();
    private DatabaseReference mFirebaseDatabase;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_ga, container, false);
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference();
        initView(view);
        initListGA();
        setupFirebase();
        return view;
    }

    private void setupFirebase() {

        mFirebaseDatabase.child(ConstantUtils.TREE_EVENT).orderByChild("dateTimeEnd").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.exists()) {
                    Event event = dataSnapshot.getValue(Event.class);
//                    Log.d(TAG, "onChildAdded: " + event.getContent());
                    if (event != null && !mListEvent.contains(event)) {
                        mListEvent.add(event);
                        Log.d(TAG, "onChildAdded: " + mListEvent.get(0).getContent());
                        mAdapter.notifyDataSetChanged();
                    }
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
    }


    private void initListGA() {
        mAdapter = new ListGAAdapter(mListEvent, getContext());
        mRecyclerViewListGA.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerViewListGA.setAdapter(mAdapter);

        mAdapter.setPostListener(new ListGAAdapter.OnClickPostListener() {
            @Override
            public void clickPost(int position, View view) {
                startActivity(new Intent(getActivity(), DetailActivity.class));
            }
        });
    }

}
