package com.atrungroi.atrungroi.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.atrungroi.atrungroi.R;
import com.atrungroi.atrungroi.ui.menu.MenuActivity;

/**
 * Created by huyphamna.
 */

public class RegisterActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private TextView mTvToolbar;
    private EditText mEdtUsername;
    private EditText mEdtPassword;
    private EditText mEdtEmail;
    private EditText mEdtAddress;
    private EditText mEdtCompany;
    private EditText mEdtAge;
    private Button mBtnSignin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
        initToolbar();
        setButtonSignin();
    }

    private void initView() {
        mToolbar = findViewById(R.id.toobar);
        mTvToolbar = findViewById(R.id.tvToolbar);
        mEdtUsername = findViewById(R.id.edtUserNameRegister);
        mEdtPassword = findViewById(R.id.edtPassRegister);
        mEdtEmail = findViewById(R.id.edtEmailRegister);
        mEdtAddress = findViewById(R.id.edtAddressRegister);
        mEdtCompany = findViewById(R.id.edtCompanyRegister);
        mEdtAge = findViewById(R.id.edtAgeRegister);
        mBtnSignin = findViewById(R.id.btnSignin);
    }

    private void initToolbar() {
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        }
        mTvToolbar.setText("Đăng ký");
    }

    private void setButtonSignin() {
        mBtnSignin.setEnabled(false);
        mEdtUsername.addTextChangedListener(textWatcher);
        mEdtPassword.addTextChangedListener(textWatcher);
        mEdtEmail.addTextChangedListener(textWatcher);
        mEdtAddress.addTextChangedListener(textWatcher);
        mEdtCompany.addTextChangedListener(textWatcher);
        mEdtAge.addTextChangedListener(textWatcher);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (mEdtUsername.getText().toString().length() == 0 || mEdtPassword.getText().toString().length() == 0
                    || mEdtAddress.getText().toString().length() == 0 || mEdtEmail.getText().toString().length() == 0
                    || mEdtAge.getText().toString().length() == 0) {
                mBtnSignin.setEnabled(false);
                mBtnSignin.setBackground(getResources().getDrawable(R.drawable.bg_button_close));
            } else {
                mBtnSignin.setEnabled(true);
                mBtnSignin.setBackground(getResources().getDrawable(R.drawable.bg_button_open));
                mBtnSignin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(RegisterActivity.this, MenuActivity.class));
                    }
                });
            }
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
