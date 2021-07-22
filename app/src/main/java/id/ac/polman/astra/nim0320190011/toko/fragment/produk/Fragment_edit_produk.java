package id.ac.polman.astra.nim0320190011.toko.fragment.produk;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import id.ac.polman.astra.nim0320190011.toko.R;
import id.ac.polman.astra.nim0320190011.toko.Utils.PictureUtils;
import id.ac.polman.astra.nim0320190011.toko.api.model.Produk;
import id.ac.polman.astra.nim0320190011.toko.api.viewmodel.Produk_view_model;
import id.ac.polman.astra.nim0320190011.toko.api.viewmodel.Toko_view_model;

public class Fragment_edit_produk extends Fragment {
    private static final String TAG = "Fragment_edit_produk";

    private static final int REQUEST_PHOTO_PRODUK = 1;

    private Produk mProduk;
    private Produk_view_model mProdukViewModel;
    private PictureUtils mPictureUtils;
    private String mProdukId;

    private ImageView mFotoProdukView;
    private Uri mFotoProdukURI;
    private File mFotoProdukFile;

    private ImageButton mFotoProdukButton;

    private EditText mNama_produk;
    private EditText mMerk_pruduk;
    private EditText mHarga_produk;
    private EditText mJumlah_produk;

    private Button mInput_button;


    SimpleDateFormat detil = new SimpleDateFormat("yyyyMMddHHmm");

    public Fragment_edit_produk(Produk model) {
        mProduk = model;
    }

    public Produk_view_model getProdukViewModel(){
        Log.i(TAG, "getProdukViewModelList: called");
        if(mProdukViewModel == null){
            mProdukViewModel = new ViewModelProvider(this)
                    .get(Produk_view_model.class);
        }
        Log.i(TAG, "getProdukViewModelList: called 2");
        return mProdukViewModel;
    }

    public static Fragment_edit_produk newInstance(Produk model) {
        return new Fragment_edit_produk(model);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProdukId = String.valueOf(mProduk.getIdProduk());
        Log.i(TAG, "UserFragment.onCreate() called idProduk " + mProdukId);
        mProduk = new Produk();
        mProdukViewModel = getProdukViewModel();
    }

    private void updateUI(){
        Log.i(TAG, "update UI () called");
        mNama_produk.setText(mProduk.getNama());
        mMerk_pruduk.setText(mProduk.getMerk());
        mHarga_produk.setText(mProduk.getHarga());
        mJumlah_produk.setText(mProduk.getJumlah());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: Called ");
        View v = inflater.inflate(R.layout.fragment_edit_produk, container, false);

        mNama_produk = (EditText) v.findViewById(R.id.nama_produk);
        mNama_produk.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mProduk.setNama(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mMerk_pruduk = (EditText) v.findViewById(R.id.merk_produk);
        mMerk_pruduk.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mProduk.setMerk(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mHarga_produk = (EditText) v.findViewById(R.id.harga_produk);
        mHarga_produk.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mProduk.setHarga(Integer.parseInt(s.toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mJumlah_produk = (EditText) v.findViewById(R.id.jumlah_produk);
        mJumlah_produk.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mProduk.setJumlah(Integer.parseInt(s.toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mFotoProdukView = v.findViewById(R.id.foto_produk_view);

        PackageManager packageManager = getActivity().getPackageManager();
        mFotoProdukButton = (ImageButton) v.findViewById(R.id.produk_camera_button);

        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(packageManager.resolveActivity(captureImage,
                PackageManager.MATCH_DEFAULT_ONLY) == null){
            mFotoProdukButton.setEnabled(false);
        }

        mFotoProdukButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImage.putExtra(MediaStore.EXTRA_OUTPUT, mFotoProdukURI);
                List<ResolveInfo> cameraActivities = getActivity()
                        .getPackageManager().queryIntentActivities(captureImage,
                                PackageManager.MATCH_DEFAULT_ONLY);
                for(ResolveInfo activity : cameraActivities){
                    getActivity().grantUriPermission(activity.activityInfo.packageName,
                            mFotoProdukURI, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                }
                startActivityForResult(captureImage, REQUEST_PHOTO_PRODUK);
            }
        });


        mInput_button = v.findViewById(R.id.input_button);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFotoProdukFile = new File(getContext().getApplicationContext().getFilesDir(), "produk_" + detil.format(new Date()));
        mFotoProdukURI = FileProvider.getUriForFile(requireActivity(),
                "id.ac.polman.astra.nim0320190011.toko.fileprovider",
                mFotoProdukFile
        );

        Log.i(TAG, "onViewCreated: Called ");
        mProdukViewModel.getProdukLiveData().observe(
                getViewLifecycleOwner(), new Observer<Produk>() {
                    @Override
                    public void onChanged(Produk produk) {
                        mProduk = produk;
                        updateUI();
                    }
                }
        );
        mProdukViewModel.loadProduk(mProdukId);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != Activity.RESULT_OK){
            return;
        }
        if(requestCode == REQUEST_PHOTO_PRODUK){
            requireActivity().revokeUriPermission(mFotoProdukURI,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            updatePhotoView();
        }
    }

    private void updatePhotoView(){
        if(mFotoProdukFile == null || !mFotoProdukFile.exists()){
            mFotoProdukView.setImageDrawable(null);
        }else{
            Bitmap bitmap = PictureUtils.getScaleBitmap(
                    mFotoProdukFile.getPath(), requireActivity()
            );
            mFotoProdukView.setImageBitmap(bitmap);
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        try{
            requireActivity().revokeUriPermission(mFotoProdukURI,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }catch (Exception e){
            Log.e(TAG, "onDetach: ", e);
        }
    }
}
