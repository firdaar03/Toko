package id.ac.polman.astra.nim0320190011.toko.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import id.ac.polman.astra.nim0320190011.toko.R;
import id.ac.polman.astra.nim0320190011.toko.Utils.PictureUtils;
import id.ac.polman.astra.nim0320190011.toko.api.model.Toko;
import id.ac.polman.astra.nim0320190011.toko.api.viewmodel.Toko_view_model;
import id.ac.polman.astra.nim0320190011.toko.api.viewmodel.Toko_view_model_list;

import static androidx.core.view.ViewCompat.jumpDrawablesToCurrentState;

public class Fragment_daftar_toko extends Fragment
    implements DatePickerFragment.Callbacks{
    private static final String TAG = "Fragment_daftar_toko";

    private static final String DIALOG_DATE = "DialogDate";

    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_PHOTO_DIRI = 1;
    private static final int REQUEST_PHOTO_KTP = 2;
    private static final int REQUEST_PHOTO_TOKO = 3;

    private Toko mToko;
    private Toko_view_model mTokoViewModel;
    private PictureUtils mPictureUtils;

    private EditText mNama;
    private EditText mEmail;
    private EditText mTelefon;
    private RadioGroup mJenisKelamin;
    private RadioButton mMale;
    private RadioButton mFemale;
    private EditText mTempatLahir;
    private EditText mTanggalLahir;
    private EditText mAlamat;
    private EditText mAlamatToko;
    private EditText mNIK;

    private ImageView mFotoDiriView;
    private ImageView mFotoKTPView;
    private ImageView mFotoTokoView;

    private ImageButton mFotoDiriButton;
    private ImageButton mFotoKTPButton;
    private ImageButton mFotoTokoButton;

    private File mFotoDiriFile;
    private File mFotoKTPFile;
    private File mFotoTokoFile;

    private Uri mFotoDiriUri;
    private Uri mFotoKTPUri;
    private Uri mFotoTokoUri;

    private EditText mUsername;
    private EditText mPassword;
    private EditText mPasswordVer;

    private Button mLoginButton;

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat detil = new SimpleDateFormat("yyyyMMddHHmm");

    @Override
    public void onDateSelected(Date date) {
        String newDate = formatter.format(date);

        updateUI(newDate);
    }

    @Override
    public void onDateSelected(Date date, int key) {

    }

    public Toko_view_model getTokoViewModel(){
        Log.i(TAG, "getTokoViewModel: called");
        if(mTokoViewModel == null){
            mTokoViewModel = new ViewModelProvider(this)
                    .get(Toko_view_model.class);
        }
        Log.i(TAG, "getTokoViewModel: called 2");
        return mTokoViewModel;
    }

    private void updateUI(String newDate){
        mTanggalLahir.setText(newDate);
    }

    public static Fragment_daftar_toko newInstance(){
        return new Fragment_daftar_toko();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToko = new Toko();
        Log.i(TAG, "onCreate: ");
        mTokoViewModel = getTokoViewModel();
        Log.i(TAG, "onCreate:  2");
        mPictureUtils = new PictureUtils();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pendaftaran_toko, container, false);

        mNama = (EditText) view.findViewById(R.id.nama_pemilik);
        mEmail = (EditText) view.findViewById(R.id.email);
        mTelefon = (EditText) view.findViewById(R.id.telpon);
        mNIK = (EditText) view.findViewById(R.id.NIK);

        mJenisKelamin = (RadioGroup) view.findViewById(R.id.jenis_kelamin);
        mMale = (RadioButton) view.findViewById(R.id.radioMale);
        mFemale = (RadioButton) view.findViewById(R.id.radioFemale);

        mTempatLahir = (EditText) view.findViewById(R.id.tempat_lahir);

        mTanggalLahir = (EditText) view.findViewById(R.id.tanggal_lahir);
        mTanggalLahir.setText(formatter.format(new Date()));
        mTanggalLahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getParentFragmentManager();
                //DatePickerFragment dialog = new DatePickerFragment();
                DatePickerFragment dialog =
                        DatePickerFragment.newInstance(new Date());
                dialog.setTargetFragment(Fragment_daftar_toko.this,REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });

        mAlamat = (EditText) view.findViewById(R.id.alamat);
        mAlamatToko = (EditText) view.findViewById(R.id.alamat_toko);

        PackageManager packageManager = getActivity().getPackageManager();
        mFotoDiriButton = (ImageButton) view.findViewById(R.id.open_came_1);
        mFotoKTPButton = (ImageButton) view.findViewById(R.id.open_came_2);
        mFotoTokoButton =  (ImageButton) view.findViewById(R.id.open_came_3);

        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(packageManager.resolveActivity(captureImage,
                PackageManager.MATCH_DEFAULT_ONLY) == null){
            mFotoDiriButton.setEnabled(false);
            mFotoKTPButton.setEnabled(false);
            mFotoTokoButton.setEnabled(false);
        }

        mFotoDiriButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImage.putExtra(MediaStore.EXTRA_OUTPUT, mFotoDiriUri);
                List<ResolveInfo> cameraActivities = getActivity()
                        .getPackageManager().queryIntentActivities(captureImage,
                                PackageManager.MATCH_DEFAULT_ONLY);
                for(ResolveInfo activity : cameraActivities){
                    getActivity().grantUriPermission(activity.activityInfo.packageName,
                            mFotoDiriUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                }
                startActivityForResult(captureImage, REQUEST_PHOTO_DIRI);
            }
        });
        mFotoKTPButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImage.putExtra(MediaStore.EXTRA_OUTPUT, mFotoKTPUri);
                List<ResolveInfo> cameraActivities = getActivity()
                        .getPackageManager().queryIntentActivities(captureImage,
                                PackageManager.MATCH_DEFAULT_ONLY);
                for(ResolveInfo activity : cameraActivities){
                    getActivity().grantUriPermission(activity.activityInfo.packageName,
                            mFotoKTPUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                }
                startActivityForResult(captureImage, REQUEST_PHOTO_KTP);
            }
        });
        mFotoTokoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImage.putExtra(MediaStore.EXTRA_OUTPUT, mFotoTokoUri);
                List<ResolveInfo> cameraActivities = getActivity()
                        .getPackageManager().queryIntentActivities(captureImage,
                                PackageManager.MATCH_DEFAULT_ONLY);
                for(ResolveInfo activity : cameraActivities){
                    getActivity().grantUriPermission(activity.activityInfo.packageName,
                            mFotoTokoUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                }
                startActivityForResult(captureImage, REQUEST_PHOTO_TOKO);
            }
        });

        mFotoDiriView = (ImageView) view.findViewById(R.id.foto_diri);
        mFotoKTPView = (ImageView) view.findViewById(R.id.foto_ktp);
        mFotoTokoView = (ImageView) view.findViewById(R.id.foto_toko);


        mUsername = (EditText) view.findViewById(R.id.username);
        mPassword = (EditText) view.findViewById(R.id.password);
        mPasswordVer = (EditText) view.findViewById(R.id.password_verif);

        mLoginButton = (Button) view.findViewById(R.id.button_daftar);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                if(mUsername.getText().toString().length() == 0){
                    mUsername.setError(getText(R.string.tidak_boleh_kosong));
                }
                if (mPassword.getText().toString().length() == 0)
                    mPassword.setError(getText(R.string.tidak_boleh_kosong));
                if (mNama.getText().toString().length() == 0){
                    mNama.setError(getText(R.string.tidak_boleh_kosong));
                }
                if (mTelefon.getText().toString().length() == 0){
                    mTelefon.setError(getText(R.string.tidak_boleh_kosong));
                }
                if (mEmail.getText().toString().length() == 0){
                    mEmail.setError(getText(R.string.tidak_boleh_kosong));
                }
                if (mTempatLahir.getText().toString().length() == 0){
                    mTempatLahir.setError(getText(R.string.tidak_boleh_kosong));
                }
                if (mTanggalLahir.getText().toString().length() == 0){
                    mTanggalLahir.setError(getText(R.string.tidak_boleh_kosong));
                }
                if (mAlamat.getText().toString().length() == 0){
                    mAlamat.setError(getText(R.string.tidak_boleh_kosong));
                }
                if (mAlamatToko.getText().toString().length() == 0){
                    mAlamatToko.setError(getText(R.string.tidak_boleh_kosong));
                } if (mNIK.getText().toString().length() == 0){
                    mNIK.setError(getText(R.string.tidak_boleh_kosong));
                } if (mPasswordVer.getText().toString().length() == 0){
                    mPasswordVer.setError(getText(R.string.tidak_boleh_kosong));
                } else if (mNIK.getText().toString().length() != 0 && mAlamatToko.getText().toString().length() != 0 &&
                            mAlamat.getText().toString().length() != 0 && mTanggalLahir.getText().toString().length() != 0 &&
                            mTanggalLahir.getText().toString().length() != 0 && mEmail.getText().toString().length() != 0 &&
                            mTelefon.getText().toString().length() != 0 && mNama.getText().toString().length() != 0 &&
                            mPassword.getText().toString().length() != 0 && mUsername.getText().toString().length() != 0) {

                    if(mPassword.getText().toString().equals(mPasswordVer.getText().toString())){
                        mToko.setUsername(mUsername.getText().toString());
                        mToko.setPassword(mPassword.getText().toString());
                        mToko.setNama_pemilik(mNama.getText().toString());
                        mToko.setNo_telfon(mTelefon.getText().toString());
                        mToko.setEmail(mEmail.getText().toString());
                        int id = mJenisKelamin.getCheckedRadioButtonId();
                        switch (id) {
                            case R.id.radioMale :
                                mToko.setJenis_kelamin("1");
                                break;
                            case R.id.radioFemale :
                                mToko.setJenis_kelamin("0");
                                break;
                        }
                        mToko.setTempat_lahir(mTempatLahir.getText().toString());
                        mToko.setTanggal_lahir(mTanggalLahir.getText().toString());
                        mToko.setAlamat(mAlamat.getText().toString());
                        mToko.setAlamatToko(mAlamatToko.getText().toString());
                        mToko.setNIK(mNIK.getText().toString());

                        try{
                            Bitmap bitmap = mPictureUtils.Compress(((BitmapDrawable)mFotoDiriView.getDrawable()).getBitmap(),50);
                            mToko.setFoto_diri(mPictureUtils.convertToString(bitmap));
                            bitmap = mPictureUtils.Compress(((BitmapDrawable)mFotoKTPView.getDrawable()).getBitmap(),50);
                            mToko.setFoto_KTP(mPictureUtils.convertToString(bitmap));
                            bitmap = mPictureUtils.Compress(((BitmapDrawable)mFotoTokoView.getDrawable()).getBitmap(),50);
                            mToko.setFoto_toko(mPictureUtils.convertToString(bitmap));
                        }catch (Exception e){
                            mToko.setFoto_diri("");
                            mToko.setFoto_KTP("");
                            mToko.setFoto_toko("");
                        }


                        mTokoViewModel.save(mToko);
                        Toast.makeText(getContext(), "Registrasi Berhasil!",
                                Toast.LENGTH_SHORT).show();
                        clearToko();
                    } else {

                        Toast.makeText(getContext(), "Password dan Verifikasi harus sama",
                                Toast.LENGTH_SHORT).show();
                    }

                }
            }

            public void clearToko(){
                mPasswordVer.getText().clear();
                mPassword.getText().clear();
                mNIK.getText().clear();
                mAlamatToko.getText().clear();
                mAlamat.getText().clear();
                mTempatLahir.getText().clear();
                mEmail.getText().clear();
                mTelefon.getText().clear();
                mNama.getText().clear();
                mUsername.getText().clear();
                getFragmentManager().popBackStack();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFotoDiriFile = new File(getContext().getApplicationContext().getFilesDir(), "diri_" + detil.format(new Date()));
        mFotoDiriUri = FileProvider.getUriForFile(requireActivity(),
                "id.ac.polman.astra.nim0320190011.toko.fileprovider",
                mFotoDiriFile
        );

        mFotoKTPFile = new File(getContext().getApplicationContext().getFilesDir(), "ktp_" + detil.format(new Date()));
        mFotoKTPUri = FileProvider.getUriForFile(requireActivity(),
                "id.ac.polman.astra.nim0320190011.toko.fileprovider",
                mFotoKTPFile
        );

        mFotoTokoFile = new File(getContext().getApplicationContext().getFilesDir(), "toko_" + detil.format(new Date()));
        mFotoTokoUri = FileProvider.getUriForFile(requireActivity(),
                "id.ac.polman.astra.nim0320190011.toko.fileprovider",
                mFotoTokoFile
        );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != Activity.RESULT_OK){
            return;
        }
        if(requestCode == REQUEST_PHOTO_DIRI){
            requireActivity().revokeUriPermission(mFotoDiriUri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            updatePhotoView();
        }
        if(requestCode == REQUEST_PHOTO_KTP){
            requireActivity().revokeUriPermission(mFotoKTPUri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            updatePhotoView();
        }
        if(requestCode == REQUEST_PHOTO_TOKO){
            requireActivity().revokeUriPermission(mFotoTokoUri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            updatePhotoView();
        }
    }

    private void updatePhotoView(){
        if(mFotoDiriFile == null || !mFotoDiriFile.exists()){
            mFotoDiriView.setImageDrawable(null);
        }else{
            Bitmap bitmap = PictureUtils.getScaleBitmap(
                    mFotoDiriFile.getPath(), requireActivity()
            );
            mFotoDiriView.setImageBitmap(bitmap);
        }

        if(mFotoKTPFile == null || !mFotoKTPFile.exists()){
            mFotoKTPView.setImageDrawable(null);
        }else{
            Bitmap bitmap = PictureUtils.getScaleBitmap(
                    mFotoKTPFile.getPath(), requireActivity()
            );
            mFotoKTPView.setImageBitmap(bitmap);
        }

        if(mFotoTokoFile == null || !mFotoTokoFile.exists()){
            mFotoTokoView.setImageDrawable(null);
        }else{
            Bitmap bitmap = PictureUtils.getScaleBitmap(
                    mFotoTokoFile.getPath(), requireActivity()
            );
            mFotoTokoView.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try{
            requireActivity().revokeUriPermission(mFotoDiriUri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }catch (Exception e){
            Log.e(TAG, "onDetach: ", e);
        }
        mFotoDiriFile = null;
        mFotoTokoFile = null;
        mFotoKTPFile = null;
    }


}
