package com.atrungroi.atrungroi.ui.fragment;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
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
import com.atrungroi.atrungroi.services.NotificationAcessDonation;
import com.atrungroi.atrungroi.ui.DetailActivity;
import com.atrungroi.atrungroi.ui.MainActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
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
        mFirebaseDatabase.keepSynced(true);
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
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                Event event =  mListEvent.get(position);
                intent.putExtra("title", mListEvent.get(position).getTitle());
                intent.putExtra("dateTimeStart", mListEvent.get(position).getDateTimeStart());
                intent.putExtra("dateTimeEnd", mListEvent.get(position).getDateTimeEnd());
                intent.putExtra("timePost", mListEvent.get(position).getTimePost());
                intent.putExtra("content", mListEvent.get(position).getContent());
                intent.putExtra("imagesEvent", mListEvent.get(position).getImagesEvent());
                intent.putExtra("idUser", mListEvent.get(position).getIdUser());
                intent.putExtra("nameUser", mListEvent.get(position).getNameUser());
//                 Show qr code
                intent.putExtra("idEvent", mListEvent.get(position).getIdEvent());
                intent.putExtra("isCheck", mListEvent.get(position).getCheckQR());
                startActivity(intent);
            }
        });
    }


    private void scheduleNotification(Notification notification) {
        AlarmManager alarmManager = (AlarmManager)getContext().getSystemService(Context.ALARM_SERVICE);
        Intent notificationIntent = new Intent(getContext(), NotificationAcessDonation.class);
        notificationIntent.putExtra(NotificationAcessDonation.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationAcessDonation.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, 30);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
        }
    }

    private  Notification getNotificationAcessDonation() {
        Intent notificationIntent = new Intent(getContext(), MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getContext());
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(notificationIntent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        long[] pattern = {0, 300, 0};
        Notification.Builder builder = new Notification.Builder(getContext());
        builder.setContentText("Bạn đã trúng rồi!");
        builder.setContentTitle("Thông báo give away");
        builder.setTicker("Thông báo mới!");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setDefaults(Notification.DEFAULT_SOUND);
        builder.setContentIntent(pendingIntent);
        builder.setVibrate(pattern);
        builder.setAutoCancel(true);
        return builder.build();

    }


}
