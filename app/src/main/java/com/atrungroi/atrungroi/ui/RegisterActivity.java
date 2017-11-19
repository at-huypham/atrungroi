package com.atrungroi.atrungroi.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.atrungroi.atrungroi.R;
import com.atrungroi.atrungroi.models.User;
import com.atrungroi.atrungroi.pref.ConstantUtils;
import com.atrungroi.atrungroi.pref.ToastUtil;
import com.atrungroi.atrungroi.ui.menu.MenuActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by huyphamna.
 */

public class RegisterActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private TextView mTvToolbar;
    private EditText mEdtName;
    private EditText mEdtPassword;
    private EditText mEdtEmail;
    private EditText mEdtAddress;
    private EditText mEdtAge;
    private EditText mEdtHomeTown;
    private EditText mEdtHobby;
    private RadioGroup radioGroupGender;
    private Button mBtnSignIn;
    private ProgressDialog mProgressDialog;
    private FirebaseAuth auth;
    private DatabaseReference mFirebaseDatabase;
    private User user;
    private String imagesAvatarUrl;

    //    private ProgressDialog mProgressDialog = new ProgressDialog(this);
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference();

        auth = FirebaseAuth.getInstance();
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
        mEdtName = findViewById(R.id.edtName);
        mEdtPassword = findViewById(R.id.edtPassRegister);
        mEdtEmail = findViewById(R.id.edtEmailRegister);
        mEdtAddress = findViewById(R.id.edtAddressRegister);
        mEdtAge = findViewById(R.id.edtAgeRegister);
        mBtnSignIn = findViewById(R.id.btnSignin);
        mEdtHomeTown = findViewById(R.id.edtHomeTown);
        mEdtHobby = findViewById(R.id.edtHobby);
        radioGroupGender = findViewById(R.id.radioGender);
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
        mEdtName.addTextChangedListener(textWatcher);
        mEdtPassword.addTextChangedListener(textWatcher);
        mEdtEmail.addTextChangedListener(textWatcher);
        mEdtAddress.addTextChangedListener(textWatcher);
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
            if (mEdtName.getText().toString().length() == 0 || mEdtPassword.getText().toString().length() == 0
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
                        setupRegisterFirebase();
                    }
                });
            }
        }
    };

    private void setupRegisterFirebase() {
        final String name = mEdtName.getText().toString().trim();
        final String password = mEdtPassword.getText().toString().trim();
        final String email = mEdtEmail.getText().toString().trim();
        final String address = mEdtAddress.getText().toString().trim();
        final int age = Integer.parseInt(mEdtAge.getText().toString().trim());
        final String hownTown = mEdtHomeTown.getText().toString().trim();
        final String hobby = mEdtHobby.getText().toString().trim();
        final String gender = getValueGender();
        showProgressDialog();
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    if (firebaseUser != null) {
                        createNewUser(firebaseUser.getUid(), name, password, email, address, hownTown, hobby, age, gender);
                        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                                .setDisplayName(name)
                                .setPhotoUri(Uri.parse(imagesAvatarUrl))
                                .build();
                        firebaseUser.updateProfile(profileUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    hideProgressDialog();
                                    ToastUtil.showShort(getApplicationContext(), "Đăng ký tài khoản thành công!");
                                    startActivity(new Intent(RegisterActivity.this, MenuActivity.class));
                                    finish();

                                } else {
                                    hideProgressDialog();
                                    ToastUtil.showShort(getApplicationContext(), "Error" + task.getException().getLocalizedMessage());
                                }
                            }
                        });
                    }
                } else {
                    hideProgressDialog();
                    ToastUtil.showLong(getApplicationContext(), "Đăng ký thất bại! \n Error: " + task.getException().getMessage());
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void hideProgressDialog() {
        mProgressDialog.dismiss();
    }

    public void showProgressDialog() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Đang xử lý...");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
    }

    public void createNewUser(String idUser, String name, String password, String email, String address, String hownTown, String hobby, int age, String gender) {
        imagesAvatarUrl = "https://firebasestorage.googleapis.com/v0/b/bloodbank-1e50e.appspot.com/o/avatar_default.jpg?alt=media&token=045117c4-b4b0-45a4-a034-b2f74e3d522c";
        user = new User(idUser, name, password, email, address, hownTown, hobby, age, gender, imagesAvatarUrl);
        mFirebaseDatabase.child(ConstantUtils.TREE_USER).child(idUser).setValue(user);
    }

    private String getValueGender() {
        int selectId = radioGroupGender.getCheckedRadioButtonId();
        RadioButton radioSexButton = findViewById(selectId);
        return radioSexButton.getText().toString();
    }


}
