package id.ac.polman.astra.nim0320190011.toko.fragment;

import android.net.Uri;
import android.os.Bundle;
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
import androidx.fragment.app.Fragment;

import java.io.File;

import id.ac.polman.astra.nim0320190011.toko.R;

public class Fragment_daftar_toko extends Fragment {
    private static final String TAG = "Fragment_daftar_toko";

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


    public static Fragment_daftar_toko newInstance(){
        return new Fragment_daftar_toko();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pendaftaran_toko, container, false);

        return view;
    }
}
