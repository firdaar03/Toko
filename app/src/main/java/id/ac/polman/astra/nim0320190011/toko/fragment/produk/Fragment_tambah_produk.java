package id.ac.polman.astra.nim0320190011.toko.fragment.produk;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

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
import id.ac.polman.astra.nim0320190011.toko.api.model.Toko;
import id.ac.polman.astra.nim0320190011.toko.api.viewmodel.Toko_view_model;

public class Fragment_tambah_produk extends Fragment {
    private static final String TAG = "Fragment_tambah_produk";

    private static final int REQUEST_PHOTO_PRODUK = 1;

    private Toko_view_model mTokoViewModel;
    private Toko dataToko;

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

//    public static Fragment_tambah_produk newInstance(Toko model) {
//        return new Fragment_tambah_produk(model);
//    }
//
//    private Fragment_tambah_produk(Toko model){
//        dataToko = model;
//    }

    public static Fragment_tambah_produk newInstance() {
        return new Fragment_tambah_produk();
    }

    private Fragment_tambah_produk(){
//        dataToko = model;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: Called ");
        View v = inflater.inflate(R.layout.fragment_tambah_produk, container, false);

        mNama_produk = v.findViewById(R.id.nama_produk);
        mMerk_pruduk = v.findViewById(R.id.merk_produk);
        mHarga_produk = v.findViewById(R.id.harga_produk);
        mJumlah_produk = v.findViewById(R.id.jumlah_produk);

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
        mInput_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), "Add", Toast.LENGTH_SHORT)
                        .show();
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