package com.atrungroi.atrungroi.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.atrungroi.atrungroi.R;
import com.atrungroi.atrungroi.ui.menu.MenuActivity;

/**
 * Created by huyphamna.
 */
public class MainActivity extends AppCompatActivity {
    private Button mBtnRegister;
    private Button mBtnLogin;
    private EditText mEdtUserName;
    private EditText mEdtPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        setButtonLoginAndRegister();
    }

    private void initView() {
        mBtnRegister = findViewById(R.id.btnRegister);
        mBtnLogin = findViewById(R.id.btnLogin);
        mEdtUserName = findViewById(R.id.edtUserName);
        mEdtPassword = findViewById(R.id.edtPassword);
    }

    private void setButtonLoginAndRegister() {
        mBtnLogin.setEnabled(false);
        mEdtUserName.addTextChangedListener(textWatcher);
        mEdtPassword.addTextChangedListener(textWatcher);
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });
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
            if (mEdtPassword.getText().toString().length() == 0 || mEdtUserName.getText().toString().length() == 0) {
                mBtnLogin.setEnabled(false);
                mBtnLogin.setBackground(getResources().getDrawable(R.drawable.bg_button_close));
            } else {
                mBtnLogin.setEnabled(true);
                mBtnLogin.setBackground(getResources().getDrawable(R.drawable.bg_button_open));
                mBtnLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(MainActivity.this, MenuActivity.class));
                    }
                });
            }
        }
    };
}
