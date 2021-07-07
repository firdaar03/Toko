package id.ac.polman.astra.nim0320190011.toko.fragment;

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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import id.ac.polman.astra.nim0320190011.toko.R;
import id.ac.polman.astra.nim0320190011.toko.Utils.PictureUtils;

import static androidx.core.view.ViewCompat.jumpDrawablesToCurrentState;

public class Fragment_daftar_toko extends Fragment
    implements DatePickerFragment.Callbacks{
    private static final String TAG = "Fragment_daftar_toko";

    private static final String DIALOG_DATE = "DialogDate";

    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_PHOTO_DIRI = 1;
    private static final int REQUEST_PHOTO_KTP = 2;
    private static final int REQUEST_PHOTO_TOKO = 3;

    private EditText mNama;
    private EditText mEmail;
    private RadioGroup mJenisKelamin;
    private RadioButton mJenisKelaminSelected;
    private EditText mTempatLahir;
    private EditText mTanggalLahir;
    private EditText mAlamat;
    private EditText mAlamatToko;

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

    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    public void onDateSelected(Date date) {
        String newDate = formatter.format(date);

        updateUI(newDate);
    }

    private void updateUI(String newDate){
        mTanggalLahir.setText(newDate);
    }

    public static Fragment_daftar_toko newInstance(){
        return new Fragment_daftar_toko();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pendaftaran_toko, container, false);

        mNama = (EditText) view.findViewById(R.id.nama_pemilik);
        mEmail = (EditText) view.findViewById(R.id.email);

        mJenisKelamin = (RadioGroup) view.findViewById(R.id.jenis_kelamin);
        int selectedId = mJenisKelamin.getCheckedRadioButtonId();
        mJenisKelaminSelected = (RadioButton) view.findViewById(selectedId);

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

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFotoDiriFile = new File(getContext().getApplicationContext().getFilesDir(), "foto_diri");
        mFotoDiriUri = FileProvider.getUriForFile(requireActivity(),
                "id.ac.polman.astra.nim0320190011.toko.fileprovider",
                mFotoDiriFile
        );

        mFotoKTPFile = new File(getContext().getApplicationContext().getFilesDir(), "foto_ktp");
        mFotoKTPUri = FileProvider.getUriForFile(requireActivity(),
                "id.ac.polman.astra.nim0320190011.toko.fileprovider",
                mFotoKTPFile
        );

        mFotoTokoFile = new File(getContext().getApplicationContext().getFilesDir(), "foto_toko");
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

    }
}
