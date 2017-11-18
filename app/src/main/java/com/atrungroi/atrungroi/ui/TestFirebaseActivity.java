package com.atrungroi.atrungroi.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.atrungroi.atrungroi.R;
import com.atrungroi.atrungroi.models.News;
import com.atrungroi.atrungroi.models.Result;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huyphamna.
 */

public class TestFirebaseActivity extends AppCompatActivity {
    private DatabaseReference mDatabaseReference;

    private TextView mTvTakeData;
    private Button mBtnChangeData;
    private EditText mEdtChange;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_firebase);
        initView();
//        setData();
        takeDataFromServer();
        changeDataServerFromApp();
    }

    private void initView(){
        mTvTakeData = findViewById(R.id.tvTakeData);
        mBtnChangeData = findViewById(R.id.btnChangeData);
        mEdtChange = findViewById(R.id.edtChangeData);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
    }

    private void setData() {

        // save data with object, take the latest value
        News news = new News("abc1", "vhbwqvbjqnv", 0);
        mDatabaseReference.child("news").setValue(news);

        // save data with map, take the latest value
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("Bike", 2);
        mDatabaseReference.child("Vehicle").setValue(map);

        // push, save a list value, after change
        List<Result> results = new ArrayList<>();
        results.add(new Result("A 456 da khong trung"));
        results.add(new Result("Huy Pham"));
        results.add(new Result("Khiem Nguyen"));
        mDatabaseReference.child("result").push().setValue(results);

        // take listener when complete setValue
        mDatabaseReference.child("Check").setValue("check complete", new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null){
                    Toast.makeText(TestFirebaseActivity.this, "Complete", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TestFirebaseActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void takeDataFromServer(){

        // onDataChange take the data from server and fill im the text view, real time
        mDatabaseReference.child("Check").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mTvTakeData.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void changeDataServerFromApp(){
        mBtnChangeData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabaseReference.child("Check").setValue(mEdtChange.getText().toString().trim());
            }
        });
    }

    private void takeDataWithListener(){
        mDatabaseReference.child("result").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

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
}
