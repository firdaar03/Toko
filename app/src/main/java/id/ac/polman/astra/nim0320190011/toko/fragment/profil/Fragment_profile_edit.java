package id.ac.polman.astra.nim0320190011.toko.fragment.profil;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import id.ac.polman.astra.nim0320190011.toko.R;
import id.ac.polman.astra.nim0320190011.toko.Utils.PictureUtils;
import id.ac.polman.astra.nim0320190011.toko.api.model.Toko;
import id.ac.polman.astra.nim0320190011.toko.api.viewmodel.Toko_view_model;
import id.ac.polman.astra.nim0320190011.toko.fragment.DatePickerFragment;

public class Fragment_profile_edit extends Fragment
        implements DatePickerFragment.Callbacks{

    private static final String TAG = "Fragment_profile_edit";

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
    private TextView mTanggalLahir;
    private EditText mAlamat;
    private EditText mAlamatToko;
    private EditText mNIK;
    private ImageView mBack;

    private com.mikhaellopez.circularimageview.CircularImageView mFotoDiriView;
    private ImageView mFotoKTPView;
    private ImageView mFotoTokoView;

    private ImageView mFotoDiriButton;
    private ImageView mFotoKTPButton;
    private ImageView mFotoTokoButton;

    private File mFotoDiriFile;
    private File mFotoKTPFile;
    private File mFotoTokoFile;

    private Uri mFotoDiriUri;
    private Uri mFotoKTPUri;
    private Uri mFotoTokoUri;

    private EditText mUsername;
    private EditText mPassword;
    private EditText mPasswordVer;
    private EditText mPasswordLama;

    private TextView mSimpanButton;

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat detil = new SimpleDateFormat("yyyyMMddHHmm");

    public static Fragment_profile_edit newInstance(Toko toko){
        return new Fragment_profile_edit(toko);
    }

    public Fragment_profile_edit(Toko toko){
        mToko = toko;
    }

    public Toko_view_model getTokoViewModel(){
        Log.i(TAG, "getTokoViewModelList: called");
        if(mTokoViewModel == null){
            mTokoViewModel = new ViewModelProvider(this)
                    .get(Toko_view_model.class);
        }
        Log.i(TAG, "getTokoViewModelList: called 2");
        return mTokoViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        mTokoViewModel = getTokoViewModel();
        mPictureUtils = new PictureUtils();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile_edit, container, false);
        Log.i(TAG, "onCreateView: ");
        mNama = v.findViewById(R.id.nama_pemilik);
        mEmail = v.findViewById(R.id.email);
        mTelefon = v.findViewById(R.id.telepon);

        mJenisKelamin = v.findViewById(R.id.jenis_kelamin);
        mMale = v.findViewById(R.id.radioMale);
        mFemale = v.findViewById(R.id.radioFemale);

        mBack = v.findViewById(R.id.back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        mTempatLahir = v.findViewById(R.id.tempat_lahir);
        mTanggalLahir = v.findViewById(R.id.tanggal_lahir);
        mTanggalLahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date tanggallahir = new Date();
                try {
                    tanggallahir = formatter.parse(mToko.getTanggal_lahir());
                }catch (Exception e){

                }
                FragmentManager manager = getParentFragmentManager();
                //DatePickerFragment dialog = new DatePickerFragment();
                DatePickerFragment dialog =
                        DatePickerFragment.newInstance(tanggallahir);
                dialog.setTargetFragment(Fragment_profile_edit.this,REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });

        mAlamat = v.findViewById(R.id.alamat);
        mAlamatToko = v.findViewById(R.id.alamat_toko);
        mNIK = v.findViewById(R.id.NIK);
        mUsername = v.findViewById(R.id.username);
        mPassword = v.findViewById(R.id.password);
        mPasswordVer = v.findViewById(R.id.password_verif);
        mPasswordLama = v.findViewById(R.id.password_lama);

        PackageManager packageManager = getActivity().getPackageManager();
        mFotoDiriButton = v.findViewById(R.id.foto_diri_button);
        mFotoKTPButton = v.findViewById(R.id.foto_ktp_button);
        mFotoTokoButton =  v.findViewById(R.id.foto_toko_button);

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

        mFotoDiriView = v.findViewById(R.id.foto_diri_view);
        mFotoKTPView = v.findViewById(R.id.foto_ktp_view);
        mFotoTokoView = v.findViewById(R.id.foto_toko_view);

        mSimpanButton = v.findViewById(R.id.save_button);
        mSimpanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mUsername.getText().toString().length() == 0){
                    mUsername.setError("Username Harus Diisi");
                }
                if (mPassword.getText().toString().length() == 0){
                    mPassword.setError("Password Harus Diisi");
                }
                if (mNama.getText().toString().length() == 0){
                    mNama.setError("Nama Harus Diisi");
                }
                if (mTelefon.getText().toString().length() == 0){
                    mTelefon.setError("No.Telepon Harus Diisi");
                }
                if (mEmail.getText().toString().length() == 0){
                    mEmail.setError("Email Harus Diisi");
                }
                if (mTempatLahir.getText().toString().length() == 0){
                    mTempatLahir.setError("Tempat lahir Harus Diisi");
                }
                if (mTanggalLahir.getText().toString().length() == 0){
                    mTanggalLahir.setError("Tanggal lahir Harus Diisi");
                }
                if (mAlamat.getText().toString().length() == 0){
                    mAlamat.setError("Alamat Harus Diisi");
                }
                if (mAlamatToko.getText().toString().length() == 0){
                    mAlamatToko.setError("Alamat Toko Harus Diisi");
                } if (mNIK.getText().toString().length() == 0){
                    mNIK.setError("NIK Harus Diisi");
                } if (mPasswordVer.getText().toString().length() == 0){
                    mPasswordVer.setError("Verifikasi Password Harus Diisi");
                } if (mPasswordLama.getText().toString().length() == 0){
                    mPasswordLama.setError("Mohon isikan password lama dengan benar");
                } else if (mNIK.getText().toString().length() != 0 && mAlamatToko.getText().toString().length() != 0 &&
                        mAlamat.getText().toString().length() != 0 && mTanggalLahir.getText().toString().length() != 0 &&
                        mTanggalLahir.getText().toString().length() != 0 && mEmail.getText().toString().length() != 0 &&
                        mTelefon.getText().toString().length() != 0 && mNama.getText().toString().length() != 0 &&
                        mPassword.getText().toString().length() != 0 && mUsername.getText().toString().length() != 0) {

                    if(!mPasswordLama.getText().toString().equals(mToko.getPassword())) {
                        Toast.makeText(getContext(), "Password lama tidak benar",
                                Toast.LENGTH_SHORT).show();
                    }
                    else if(mPassword.getText().toString().equals(mPasswordVer.getText().toString())){
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
                            mToko.setFoto_diri(mPictureUtils.convertToString(mFotoDiriFile.getPath()));
                        }catch (Exception e){

                        }
                        try{
                            mToko.setFoto_KTP(mPictureUtils.convertToString(mFotoKTPFile.getPath()));
                        }catch (Exception e){

                        }
                        try{
                            mToko.setFoto_toko(mPictureUtils.convertToString(mFotoTokoFile.getPath()));
                        }catch (Exception e){

                        }

                        mTokoViewModel.update(mToko);
                        Toast.makeText(getContext(), "Pembaruan Berhasil!",
                                Toast.LENGTH_SHORT).show();
                        clearToko();
                    }
                    else{
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

        updateUI();
        return v;
    }

    private void updateUI() {
        Log.i(TAG, "updateUI: ");
        mNama.setText(mToko.getNama_pemilik());
        mEmail.setText(mToko.getEmail());
        mTelefon.setText(mToko.getNo_telfon());
        if(mToko.getJenis_kelamin().equals("1")){
            mMale.setChecked(true);
        }else{
            mFemale.setChecked(true);
        }

        Date tanggallahir = new Date();
        try {
            tanggallahir = formatter.parse(mToko.getTanggal_lahir());
        }catch (Exception e){

        }
        String tanggal = formatter.format(tanggallahir);
        mTempatLahir.setText(mToko.getTempat_lahir());
        mTanggalLahir.setText(tanggal);
        mAlamat.setText(mToko.getAlamat());
        mAlamatToko.setText(mToko.getAlamatToko());
        mNIK.setText(mToko.getNIK());
        mUsername.setText(mToko.getUsername());
        mPassword.setText(mToko.getPassword());
        mPasswordVer.setText(mToko.getPassword());

        try{
            mFotoDiriView.setImageBitmap(mPictureUtils.convertToImage(mToko.getFoto_diri()));
            mFotoKTPView.setImageBitmap(mPictureUtils.convertToImage(mToko.getFoto_KTP()));
            mFotoTokoView.setImageBitmap(mPictureUtils.convertToImage(mToko.getFoto_toko()));
        }catch (Exception e){

        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onViewCreated: ");
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
        Log.i(TAG, "onActivityResult: ");
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != Activity.RESULT_OK){
            return;
        }
        if(requestCode == REQUEST_PHOTO_DIRI){
            requireActivity().revokeUriPermission(mFotoDiriUri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            Bitmap bitmap = PictureUtils.getScaleBitmap(
                    mFotoDiriFile.getPath(), requireActivity()
            );
            mFotoDiriView.setImageBitmap(bitmap);
        }
        if(requestCode == REQUEST_PHOTO_KTP){
            requireActivity().revokeUriPermission(mFotoKTPUri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            Bitmap bitmap = PictureUtils.getScaleBitmap(
                    mFotoKTPFile.getPath(), requireActivity()
            );
            mFotoKTPView.setImageBitmap(bitmap);
        }
        if(requestCode == REQUEST_PHOTO_TOKO){
            requireActivity().revokeUriPermission(mFotoTokoUri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            Bitmap bitmap = PictureUtils.getScaleBitmap(
                    mFotoTokoFile.getPath(), requireActivity()
            );
            mFotoTokoView.setImageBitmap(bitmap);
        }
    }

    private void updatePhotoView(){
        Log.i(TAG, "updatePhotoView: ");
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
        Log.i(TAG, "onDetach: ");
        super.onDetach();
        try{
            requireActivity().revokeUriPermission(mFotoDiriUri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            requireActivity().revokeUriPermission(mFotoKTPUri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            requireActivity().revokeUriPermission(mFotoTokoUri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }catch (Exception e){
            Log.e(TAG, "onDetach: ", e);
        }
    }
    @Override
    public void onDateSelected(Date date) {
        String newDate = formatter.format(date);
        mTanggalLahir.setText(newDate);
    }

    @Override
    public void onDateSelected(Date date, int key) {

    }
}
