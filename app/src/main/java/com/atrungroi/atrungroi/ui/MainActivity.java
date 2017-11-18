package com.atrungroi.atrungroi.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.atrungroi.atrungroi.R;
import com.atrungroi.atrungroi.pref.ToastUtil;
import com.atrungroi.atrungroi.ui.menu.MenuActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by huyphamna.
 */
public class MainActivity extends AppCompatActivity {
    private Button mBtnRegister;
    private Button mBtnLogin;
    private EditText mEdtEmail;
    private EditText mEdtPassword;
    private FirebaseAuth mAuth;
    private DatabaseReference mFirebaseDatabase;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            startActivity(new Intent(MainActivity.this, MenuActivity.class));
            finish();
        }
        initView();
        setButtonLoginAndRegister();
    }

    private void initView() {
        mBtnRegister = findViewById(R.id.btnRegister);
        mBtnLogin = findViewById(R.id.btnLogin);
        mEdtEmail = findViewById(R.id.edtEmail);
        mEdtPassword = findViewById(R.id.edtPassword);
    }

    private void setButtonLoginAndRegister() {
        mBtnLogin.setEnabled(false);
        mEdtEmail.addTextChangedListener(textWatcher);
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
            if (mEdtPassword.getText().toString().length() == 0 || mEdtEmail.getText().toString().length() == 0) {
                mBtnLogin.setEnabled(false);
                mBtnLogin.setBackground(getResources().getDrawable(R.drawable.bg_button_close));
            } else {
                mBtnLogin.setEnabled(true);
                mBtnLogin.setBackground(getResources().getDrawable(R.drawable.bg_button_open));
                mBtnLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        checkLogin();
                    }
                });
            }
        }
    };

    private void checkLogin() {
        String email = mEdtEmail.getText().toString().trim();
        String password = mEdtPassword.getText().toString().trim();
        showProgressDialog();
        if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            ToastUtil.showShort(getApplicationContext(), "Email và password không được rỗng");
            hideProgressDialog();
        } else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        hideProgressDialog();
                        startActivity(new Intent(MainActivity.this, MenuActivity.class));
                        finish();
                    } else {
                        hideProgressDialog();
                        mEdtPassword.getText().clear();
                        Toast.makeText(MainActivity.this, "Đăng nhập thất bại!\nError: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public void showProgressDialog() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Đang xử lý...");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_BACK && keyEvent.getAction() == KeyEvent.ACTION_UP)
                    finish();
                return false;
            }
        });
        mProgressDialog.show();
    }
}
