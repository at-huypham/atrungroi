package com.atrungroi.atrungroi.ui.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.atrungroi.atrungroi.R;
import com.atrungroi.atrungroi.models.Event;
import com.atrungroi.atrungroi.models.MenuObject;
import com.atrungroi.atrungroi.models.User;
import com.atrungroi.atrungroi.pref.ConstantUtils;
import com.atrungroi.atrungroi.pref.ToastUtil;
import com.atrungroi.atrungroi.ui.MainActivity;
import com.atrungroi.atrungroi.ui.ShowScanQR;
import com.atrungroi.atrungroi.ui.fragment.CreateGAFragment;
import com.atrungroi.atrungroi.ui.fragment.ListApproveGAFragment;
import com.atrungroi.atrungroi.ui.fragment.ListGAFragment;
import com.atrungroi.atrungroi.ui.fragment.ResultGAFragment;
import com.atrungroi.atrungroi.ui.fragment.SettingFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.atrungroi.atrungroi.pref.ConstantUtils.TAG;

/**
 * Created by huyphamna.
 */

public class MenuActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private LinearLayout mLlContainer;
    private Toolbar mToolbar;
    private TextView mTvToolbar;
    private MenuAdapter mMenuAdapter;
    private RecyclerView mRecyclerViewMenu;
    private Menu mMenu;
    private int showMenu;
    private List<MenuObject> mMenuObjects;
    private int mPositionChoose = -1;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        initView();
        initToolbar();
        initDrawer();
        initRecyclerViewMenu();
        callFragment(new ListGAFragment());
        scanQr();
    }

    private void initView() {
        mDrawerLayout = findViewById(R.id.drawerLayout);
        mLlContainer = findViewById(R.id.llContainer);
        mToolbar = findViewById(R.id.toobar);
        mTvToolbar = findViewById(R.id.tvToolbar);
        mRecyclerViewMenu = findViewById(R.id.recyclerViewMenu);
    }

    private void initRecyclerViewMenu() {
        mMenuObjects = new ArrayList<>();
        mMenuObjects.add(new MenuObject("Danh sách give away", R.drawable.ic_list_black));
        mMenuObjects.add(new MenuObject("Give away đã tham gia", R.drawable.ic_playlist_add_check_black));
        mMenuObjects.add(new MenuObject("Tạo give away mới", R.drawable.ic_add_box_black));
        mMenuObjects.add(new MenuObject("Kết quả give away", R.drawable.ic_supervisor_account_black));
        mMenuObjects.add(new MenuObject("Thông tin", R.drawable.ic_settings_black));
        mMenuObjects.add(new MenuObject("Đăng xuất", R.drawable.ic_exit_to_app_black));

        mMenuAdapter = new MenuAdapter(this, mMenuObjects, new MenuAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (mPositionChoose > -1) {
                    mMenuObjects.get(mPositionChoose).setChoose();
                    mMenuAdapter.notifyItemChanged(mPositionChoose + 1);
                }
                mMenuObjects.get(position).setChoose();
                mPositionChoose = position;
                mTvToolbar.setText(mMenuObjects.get(position).getName());
                mMenuAdapter.notifyItemChanged(position + 1);
                mDrawerLayout.closeDrawers();
            }
        });
        mRecyclerViewMenu.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewMenu.setAdapter(mMenuAdapter);
        callFragmentFromMenu(mMenuAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.mMenu = menu;
        getMenuInflater().inflate(R.menu.menu_create, menu);
        menu.findItem(R.id.menuPost).setVisible(true);
        return true;
    }

    private void showMenu(int menuOption) {
        if (mMenu == null) {
            return;
        } else if (menuOption == 0) {
            mMenu.setGroupVisible(R.menu.menu_create, false);
            mMenu.findItem(R.id.menuPost).setVisible(false);
        } else if (menuOption == 1) {
            mMenu.findItem(R.id.menuPost).setVisible(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuPost:
                startActivity(new Intent(MenuActivity.this, ShowScanQR.class));
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void callFragmentFromMenu(MenuAdapter menuAdapter) {
        menuAdapter.setOnMenuItemClickListener(new MenuAdapter.OnMenuItemClickListener() {
            @Override
            public void onMenuClick(View v, int pos) {
                switch (pos) {
                    case 0:
                        callFragment(new ListGAFragment());
                        showMenu = 1;
                        break;
                    case 1:
                        callFragment(new ListApproveGAFragment());
                        showMenu = 0;
                        break;
                    case 2:
                        callFragment(new CreateGAFragment());
                        showMenu = 0;
                        break;
                    case 3:
                        callFragment(new ResultGAFragment());
                        showMenu = 0;
                        break;
                    case 4:
                        callFragment(new SettingFragment());
                        showMenu = 0;
                        break;
                    case 5:
                        mAuth.signOut();
                        ToastUtil.showShort(getApplicationContext(), "Đăng xuất thành công");
                        startActivity(new Intent(MenuActivity.this, MainActivity.class));
                        finish();
                        break;
                }
                showMenu(showMenu);
            }
        });
    }

    private void initToolbar() {
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        }
        mTvToolbar.setText("Danh sách give away");
    }

    private void initDrawer() {
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
                }
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
                }
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                //mLlContainer.setTranslationX(slideOffset * drawerView.getWidth());

            }
        };

        mDrawerLayout.addDrawerListener(mDrawerToggle);
    }

    private void callFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.commit();
    }

//    private void getUser(String id) {
//        Query query = mFirebaseDatabase.child(ConstantUtils.TREE_USER).child(id);
//        query.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    Log.d(TAG, "userabcd: " + dataSnapshot.getValue(User.class));
//                    user = dataSnapshot.getValue(User.class);
//                    Toast.makeText(MenuActivity.this, user.getAddress().toString(), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }

    private void scanQr(){
        final String a = getIntent().getStringExtra("code");


        Query query = mFirebaseDatabase.child(ConstantUtils.TREE_EVENT).orderByChild("idEvent").equalTo(a);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    FirebaseUser firebaseUser = mAuth.getCurrentUser();
                    Toast.makeText(MenuActivity.this, "Tham gia thành công", Toast.LENGTH_SHORT).show();
//                    getUser(firebaseUser.getUid());

                    mFirebaseDatabase.child(ConstantUtils.TREE_EVENT).child(a).child("userJoined").child(firebaseUser.getUid()).setValue(true);
//                    Toast.makeText(MenuActivity.this, "OK", Toast.LENGTH_SHORT).show();

                } else {
//                    Toast.makeText(MenuActivity.this, "ERROR", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        mFirebaseDatabase.child(ConstantUtils.TREE_EVENT).orderByChild("idEvent").addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                if (dataSnapshot.exists()) {
//                    Event event = dataSnapshot.getValue(Event.class);
////                    Log.d(TAG, "onChildAdded: " + event.getContent());
//                    if (a.equals(event.getIdEvent())) {
//                    } else {
//                        Toast.makeText(MenuActivity.this, "FALSE", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }
}
