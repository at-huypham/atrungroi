package com.atrungroi.atrungroi.ui.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.atrungroi.atrungroi.R;
import com.atrungroi.atrungroi.pref.ConstantUtils;
import com.atrungroi.atrungroi.pref.ToastUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

/**
 * Created by huyphamna.
 */

public class CreateGAFragment extends Fragment {
    @Nullable
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mFirebaseDatabase;
    private StorageReference mStorage;
    private EditText mEdtTitleCreateGA;
    private ImageView mImgCreateGA;
    private Uri mImageUri;
    private ProgressDialog mProgressDialog;
    private Button mBtnPostGA;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_ga, container, false);
        initUI(view);
        loadData(view);
        return view;
    }

    public void initUI(View view) {
        mImgCreateGA = (ImageView) view.findViewById(R.id.imgIllustrationCreateGA);
        mBtnPostGA = (Button) view.findViewById(R.id.btnPostGA);
    }

    public void loadData(View view) {
        mImgCreateGA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, ConstantUtils.RESULT_LOAD_IMAGE);
            }
        });
        mBtnPostGA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.showShort(getContext(), "test");
//                uploadImagesOnFirebase();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ConstantUtils.RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            mImageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), mImageUri);
                bitmap = getResizedBitmap(bitmap, 400);
                mImgCreateGA.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            mImageUri = null;
        }
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getContext());
            mProgressDialog.setMessage("Đang upload ảnh đại diện mới... ");
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setIndeterminate(false);
        }
        mProgressDialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        hideProgressDialog();
        super.onDestroy();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    private void uploadImagesOnFirebase() {
        if (mImageUri != null) {
            showProgressDialog();
            StorageReference filepath = mStorage.child(ConstantUtils.EVENT_IMAGE).child(mImageUri.getLastPathSegment());
            filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @SuppressWarnings("VisibleForTests")
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    final Uri downloadURL = taskSnapshot.getDownloadUrl();
//                    String key = mFirebaseAuth.getCurrentUser().getUid();
                    String key = "sadsadsadsadsadsadsa";
                    if (key != null) {
                        mFirebaseDatabase.child(ConstantUtils.TREE_EVENT).child(key).getRef().child(ConstantUtils.URL_IMAGES_EVENT).setValue(downloadURL.toString());
//                        final FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                        ToastUtil.showShort(getContext(), "Upload thành công");
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    ToastUtil.showShort(getContext(), "Error" + e);
                    hideProgressDialog();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @SuppressWarnings("VisibleForTests")
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100) * (taskSnapshot.getBytesTransferred());
                    progress = Math.round(progress);
                    int currentProgress = (int) progress;
                    mProgressDialog.setMessage("Đang upload ảnh...");
                }
            });
        }
    }

}
