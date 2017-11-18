package com.atrungroi.atrungroi.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.atrungroi.atrungroi.R;
import com.atrungroi.atrungroi.ui.menu.MenuActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
    private Button mBtnSignIn;

//    private ProgressDialog mProgressDialog = new ProgressDialog(this);
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initFirebase();
        initView();
        initToolbar();
        setButtonSignIn();
    }

    private void initFirebase() {
        mAuth = FirebaseAuth.getInstance();
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
        mBtnSignIn = findViewById(R.id.btnSignin);
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

    private void setButtonSignIn() {
        mBtnSignIn.setEnabled(false);
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
                mBtnSignIn.setEnabled(false);
                mBtnSignIn.setBackground(getResources().getDrawable(R.drawable.bg_button_close));
            } else {
                mBtnSignIn.setEnabled(true);
                mBtnSignIn.setBackground(getResources().getDrawable(R.drawable.bg_button_open));
                mBtnSignIn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setFirebase();
                        Toast.makeText(RegisterActivity.this, "da nhan", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    };

    private void setFirebase() {
        String email = mEdtEmail.getText().toString();
        String password = mEdtPassword.getText().toString();

        Toast.makeText(this, "" + email + password, Toast.LENGTH_SHORT).show();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(RegisterActivity.this, "" + task, Toast.LENGTH_SHORT).show();
                        if (task.isSuccessful()) {
                            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                        } else {
                            Toast.makeText(RegisterActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterActivity.this, "Loi auth" + e, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

//    private void showDialog() {
//        mProgressDialog.setMessage("Loading...");
//        mProgressDialog.setCanceledOnTouchOutside(false);
//        mProgressDialog.show();
//    }
//
//    private void hideDialog() {
//        mProgressDialog.dismiss();
//    }
}
