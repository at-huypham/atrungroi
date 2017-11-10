package com.atrungroi.atrungroi.ui.menu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.atrungroi.atrungroi.R;
import com.atrungroi.atrungroi.models.MenuObject;
import com.atrungroi.atrungroi.ui.fragment.CreateGAFragment;
import com.atrungroi.atrungroi.ui.fragment.ListApproveGAFragment;
import com.atrungroi.atrungroi.ui.fragment.ListGAFragment;
import com.atrungroi.atrungroi.ui.fragment.ResultGAFragment;
import com.atrungroi.atrungroi.ui.fragment.SettingFragment;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        initView();
        initToolbar();
        initDrawer();
        initRecyclerViewMenu();
        callFragment(new ListGAFragment());
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
        mMenuObjects.add(new MenuObject("Danh sách give away", R.drawable.ic_list_ga));
        mMenuObjects.add(new MenuObject("Give away đã tham gia", R.drawable.ic_approve_ga));
        mMenuObjects.add(new MenuObject("Tạo give away mới", R.drawable.ic_create_ga));
        mMenuObjects.add(new MenuObject("Kết quả give away", R.drawable.ic_win_ga));
        mMenuObjects.add(new MenuObject("Thông tin", R.drawable.ic_setting));

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
        menu.findItem(R.id.menuPost).setVisible(false);
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
                Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
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
                        showMenu = 0;
                        break;
                    case 1:
                        callFragment(new ListApproveGAFragment());
                        showMenu = 0;
                        break;
                    case 2:
                        callFragment(new CreateGAFragment());
                        showMenu = 1;
                        break;
                    case 3:
                        callFragment(new ResultGAFragment());
                        showMenu = 0;
                        break;
                    case 4:
                        callFragment(new SettingFragment());
                        showMenu = 0;
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
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_close);
        }
        mTvToolbar.setText("Danh sách give away");
    }

    private void initDrawer() {
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_open);
                }
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_close);
                }
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                mLlContainer.setTranslationX(slideOffset * drawerView.getWidth());
            }
        };

        mDrawerLayout.addDrawerListener(mDrawerToggle);
    }

    private void callFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.commit();
    }
}
