package id.ac.polman.astra.nim0320190011.toko.fragment.produk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import id.ac.polman.astra.nim0320190011.toko.R;
import id.ac.polman.astra.nim0320190011.toko.Utils.PictureUtils;
import id.ac.polman.astra.nim0320190011.toko.api.model.Produk;
import id.ac.polman.astra.nim0320190011.toko.api.model.Toko;
import id.ac.polman.astra.nim0320190011.toko.api.viewmodel.Produk_view_model;
import id.ac.polman.astra.nim0320190011.toko.api.viewmodel.Toko_view_model;
import id.ac.polman.astra.nim0320190011.toko.fragment.dompet.Fragment_dompet_uang_keluar;

public class Fragment_tambah_produk extends Fragment {
    private static final String TAG = "Fragment_tambah_produk";

    private static final int REQUEST_PHOTO_PRODUK = 1;

    private Toko_view_model mTokoViewModel;
    private Toko dataToko;
    private PictureUtils mPictureUtils;

    private Produk_view_model mProdukViewModel;
    private Produk mProduk;

    private ImageView mFotoProdukView;
    private Uri mFotoProdukURI;
    private File mFotoProdukFile;

    private ImageButton mFotoProdukButton;

    private EditText mNama_produk;
    private EditText mMerk_pruduk;
    private EditText mHarga_produk;
    private EditText mJumlah_produk;
    private ImageView mBack;

    private Button mInput_button;


    SimpleDateFormat detil = new SimpleDateFormat("yyyyMMddHHmm");

    public static Fragment_tambah_produk newInstance(Toko t) {
        return new Fragment_tambah_produk(t);
    }

    private Fragment_tambah_produk(Toko t){
        dataToko = t;
    }

    public Toko_view_model getTokoViewModel(){
        Log.i(TAG, "getTokoViewModelList: called");
        if(mTokoViewModel == null){
            mTokoViewModel = new ViewModelProvider(this)
                    .get(Toko_view_model.class);
        }
        Log.i(TAG, "getTokoViewModelList : called idtoko: " + dataToko.getIdToko());
        return mTokoViewModel;
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

//    public static Fragment_tambah_produk newInstance() {
//        return new Fragment_tambah_produk();
//    }

//    private Fragment_tambah_produk(){
//        dataToko = model;
//    }

    private void updateUI(){
        Log.i(TAG, "update UI () called");
        mNama_produk.setText(mProduk.getNama());
        mMerk_pruduk.setText(mProduk.getMerk());
        mHarga_produk.setText(mProduk.getHarga() + "");
        mJumlah_produk.setText(mProduk.getJumlah() + "");
        mFotoProdukView.setImageBitmap(mPictureUtils.convertToImage(mProduk.getFoto()));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTokoViewModel = getTokoViewModel();
        Log.i(TAG, "onCreate: ");
        mProduk = new Produk();
        Log.i(TAG, "onCreate: 2");
        mProdukViewModel = getProdukViewModel();
        mPictureUtils = new PictureUtils();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: Called ");
        View v = inflater.inflate(R.layout.fragment_tambah_produk, container, false);

        mNama_produk = (EditText) v.findViewById(R.id.nama_produk);
        mMerk_pruduk = (EditText) v.findViewById(R.id.merk_produk);
        mHarga_produk = (EditText) v.findViewById(R.id.harga_produk);
        mJumlah_produk = (EditText) v.findViewById(R.id.jumlah_produk);

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

        mBack = v.findViewById(R.id.back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });


        mInput_button = v.findViewById(R.id.input_button);
        mInput_button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if (mHarga_produk.getText().toString().length() == 0){
                    mHarga_produk.setError(getText(R.string.tidak_boleh_kosong));
                }
                if(mJumlah_produk.getText().toString().length() == 0){
                    mJumlah_produk.setError(getText(R.string.tidak_boleh_kosong));
                }
                if(mMerk_pruduk.getText().toString().length() == 0){
                    mMerk_pruduk.setError(getText(R.string.tidak_boleh_kosong));
                }
                if(mNama_produk.getText().toString().length() == 0){
                    mNama_produk.setError(getText(R.string.tidak_boleh_kosong));
                } else if(mHarga_produk.getText().toString().length() != 0 && mJumlah_produk.getText().toString().length() != 0
                            && mMerk_pruduk.getText().toString().length() != 0 && mNama_produk.getText().toString().length() != 0){
                    mProduk.setIdToko(dataToko.getIdToko());

                    try{
                        Bitmap bitmap = mPictureUtils.Compress(((BitmapDrawable)mFotoProdukView.getDrawable()).getBitmap(),50);
                        mProduk.setFoto(mPictureUtils.convertToString(bitmap));
                    }catch (Exception e){
                        mProduk.setFoto("");
                    }

                    mProduk.setHarga(Integer.parseInt(mHarga_produk.getText().toString()));
                    mProduk.setJumlah(Integer.parseInt(mJumlah_produk.getText().toString()));
                    mProduk.setMerk(mMerk_pruduk.getText().toString());
                    mProduk.setNama(mNama_produk.getText().toString());
                    mProduk.setCreaby(dataToko.getEmail());
                    mProdukViewModel.save(mProduk);
                    Toast.makeText(getContext(), "Add", Toast.LENGTH_SHORT)
                            .show();

                    callbacks.onProdukBaru(mProduk);

                    getFragmentManager().popBackStack();
                }

            }
        });
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

        Log.i(TAG, "Fragment_Tambah_Produk.onViewCreated: Called ");
//        mProdukViewModel.getProdukLiveData().observe(
//                getViewLifecycleOwner(), new Observer<Produk>() {
//                    @Override
//                    public void onChanged(Produk produk) {
//                        mProduk = produk;
//                        updateUI();
//                    }
//                }
//        );
//        mProdukViewModel.loadProduk(mProdukId);
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

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        callbacks = (Callbacks) context;
    }

    private Callbacks callbacks;

    public interface Callbacks{
        public void onProdukBaru(Produk pr);
    }
}