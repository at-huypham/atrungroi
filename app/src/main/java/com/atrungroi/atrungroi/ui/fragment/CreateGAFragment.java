package com.atrungroi.atrungroi.ui.fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;

import com.atrungroi.atrungroi.R;
import com.atrungroi.atrungroi.models.Event;
import com.atrungroi.atrungroi.pref.ConstantUtils;
import com.atrungroi.atrungroi.pref.ToastUtil;
import com.atrungroi.atrungroi.ui.MainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

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
    private EditText mEdtContent;
    private ImageView mImgCreateGA;
    private Uri mImageUri;
    private ProgressDialog mProgressDialog;
    private Button mBtnPostGA;
    private EditText mEdtTimeStart;
    private EditText mEdtDateStart;
    private EditText mEdtTimeEnd;
    private EditText mEdtDateEnd;
    private Button mBtnDatePickerStart;
    private Button mBtnTimePickerStart;
    private Button mBtnDatePickerEnd;
    private Button mBtnTimePickerEnd;
    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_ga, container, false);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference();
        mStorage = FirebaseStorage.getInstance().getReference();
        initUI(view);
        loadData(view);
        return view;
    }

    public void initUI(View view) {
        mImgCreateGA = view.findViewById(R.id.imgIllustrationCreateGA);
        mBtnPostGA = view.findViewById(R.id.btnPostGA);

        mEdtTitleCreateGA = view.findViewById(R.id.edtTitleCreateGA);
        mEdtContent = view.findViewById(R.id.edtContentCreateGA);

        mEdtTimeStart = view.findViewById(R.id.edtTimeGAStart);
        mEdtDateStart = view.findViewById(R.id.edtDateGAStart);

        mEdtTimeEnd = view.findViewById(R.id.edtTimeGAEnd);
        mEdtDateEnd = view.findViewById(R.id.edtDateGAEnd);
        mBtnTimePickerStart = view.findViewById(R.id.btnTimeStart);
        mBtnDatePickerStart = view.findViewById(R.id.btnDateStart);
        mBtnTimePickerEnd = view.findViewById(R.id.btnTimeEnd);
        mBtnDatePickerEnd = view.findViewById(R.id.btnDateEnd);

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
                uploadOnFirebase();
            }
        });
        mBtnDatePickerStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                String dayFormat = "";
                                String monthFormat = "";
                                if (dayOfMonth < 10) {
                                    dayFormat = "0" + dayOfMonth;
                                } else dayFormat = String.valueOf(dayOfMonth);
                                if ((monthOfYear + 1) < 10) {
                                    monthFormat = "0" + (monthOfYear + 1);
                                } else monthFormat = String.valueOf(monthOfYear + 1);
                                mEdtDateStart.setText(dayFormat + "/" + monthFormat + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        mBtnTimePickerStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);
                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                String minuteFomat = "";
                                if (minute < 10) {
                                    minuteFomat = "0" + minute;
                                } else minuteFomat = String.valueOf(minute);
                                String hourFomat = "";
                                if (hourOfDay < 10) {

                                    hourFomat = "0" + hourOfDay;
                                } else hourFomat = String.valueOf(hourOfDay);
                                mEdtTimeStart.setText(hourFomat + ":" + minuteFomat);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });

        mBtnDatePickerEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                String dayFormat = "";
                                String monthFormat = "";
                                if (dayOfMonth < 10) {
                                    dayFormat = "0" + dayOfMonth;
                                } else dayFormat = String.valueOf(dayOfMonth);
                                if ((monthOfYear + 1) < 10) {
                                    monthFormat = "0" + (monthOfYear + 1);
                                } else monthFormat = String.valueOf(monthOfYear + 1);
                                mEdtDateEnd.setText(dayFormat + "/" + monthFormat + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        mBtnTimePickerEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                String minuteFomat = "";
                                if (minute < 10) {
                                    minuteFomat = "0" + minute;
                                } else minuteFomat = String.valueOf(minute);
                                String hourFomat = "";
                                if (hourOfDay < 10) {
                                    hourFomat = "0" + hourOfDay;
                                } else hourFomat = String.valueOf(hourOfDay);
                                mEdtTimeEnd.setText(hourFomat + ":" + minuteFomat);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
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

    private void uploadOnFirebase() {
        if (mImageUri != null) {
            showProgressDialog();
            StorageReference filepath = mStorage.child(ConstantUtils.EVENT_IMAGE).child(mImageUri.getLastPathSegment());
            filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @SuppressWarnings("VisibleForTests")
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    final Uri downloadURL = taskSnapshot.getDownloadUrl();
                    String idUser = mFirebaseAuth.getCurrentUser().getUid();
                    String idEvent = UUID.randomUUID().toString();
                    String title = mEdtTitleCreateGA.getText().toString().trim();
                    String dateTimeStart = mEdtTimeStart.getText().toString() + "-" + mEdtDateStart.getText().toString();
                    String dateTimeEnd = mEdtTimeEnd.getText().toString() + "-" + mEdtDateEnd.getText().toString();
                    String content = mEdtContent.getText().toString().trim();
                    String imagesEvent = downloadURL.toString();
                    hideProgressDialog();
                    createEventGiveAway(idEvent, title, dateTimeStart, dateTimeEnd, content, imagesEvent, idUser);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    ToastUtil.showShort(getContext(), "Error");
                    hideProgressDialog();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100) * (taskSnapshot.getBytesTransferred());
                    progress = Math.round(progress);
                    int currentProgress = (int) progress;
                    mProgressDialog.setMessage("Đang xử lý...");
                }
            });
        } else ToastUtil.showShort(getActivity(), "Bạn chưa chọn ảnh!");
    }

    private void createEventGiveAway(String idEvent, String title, String dateTimeStart, String dateTimeEnd, String content, String imagesEvent, String idUser) {

        Event event = new Event(idEvent, title, dateTimeStart, dateTimeEnd, getTimeCurrent(), content, imagesEvent, idUser);
        mFirebaseDatabase.child(ConstantUtils.TREE_EVENT).child(idEvent).setValue(event);
        startActivity(new Intent(getActivity(), MainActivity.class));
        getActivity().finish();
        ToastUtil.showShort(getContext(), "Đăng bài thành công!");
    }

    private String getTimeCurrent() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("HH:mm-dd/MM/yyyy");
        return df.format(calendar.getTime());
    }
}
