package id.ac.polman.astra.nim0320190011.toko.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import id.ac.polman.astra.nim0320190011.toko.R;
import id.ac.polman.astra.nim0320190011.toko.Utils.PictureUtils;
import id.ac.polman.astra.nim0320190011.toko.api.model.Dompet;
import id.ac.polman.astra.nim0320190011.toko.api.model.Toko;
import id.ac.polman.astra.nim0320190011.toko.api.viewmodel.Dompet_view_model;
import id.ac.polman.astra.nim0320190011.toko.api.viewmodel.Toko_view_model;
import id.ac.polman.astra.nim0320190011.toko.api.viewmodel.Toko_view_model_list;
import id.ac.polman.astra.nim0320190011.toko.fragment.produk.Fragment_tambah_produk;

public class Fragment_menu_utama extends Fragment
    {
    private static final String TAG = "Fragment_menu_utama";

    private Toko dataToko;
    private Dompet dataDompet;
    private PictureUtils mPictureUtils;

    private RelativeLayout mButtonProduk;
    private RelativeLayout mButtonDompet;

    private TextView mNamaPemilik;
    private com.mikhaellopez.circularimageview.CircularImageView mFotoDiri;
    private ImageView mSettingButton;
    private TextView mJumlahProduk;
    private TextView mJumlahUang;
    private TextView mTotalDompet;
    private TextView mPemasukkan;
    private TextView mPengeluaran;


    private Toko_view_model mTokoViewModel;
    private Dompet_view_model mDompetViewModel;

    public Toko_view_model getTokoViewModel(){
        Log.i(TAG, "getTokoViewModelList: called");
        if(mTokoViewModel == null){
            mTokoViewModel = new ViewModelProvider(this)
                    .get(Toko_view_model.class);
        }
        Log.i(TAG, "getTokoViewModelList: called 2");
        return mTokoViewModel;
    }
    public Dompet_view_model getDompetViewModel(){
        Log.i(TAG, "getTokoViewModelList: called");
        if(mDompetViewModel == null){
            mDompetViewModel = new ViewModelProvider(this)
                    .get(Dompet_view_model.class);
        }
        Log.i(TAG, "getTokoViewModelList: called 2");
        return mDompetViewModel;
    }

    public static Fragment_menu_utama newInstance(Toko model) {
        return new Fragment_menu_utama(model);
    }

    private Fragment_menu_utama(Toko model){
        dataToko = model;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTokoViewModel = getTokoViewModel();
        mDompetViewModel = getDompetViewModel();
        mPictureUtils = new PictureUtils();
        dataDompet = new Dompet();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: Called ");
        View v = inflater.inflate(R.layout.fragment_menu_utama, container, false);

        mNamaPemilik = v.findViewById(R.id.nama_pemilik);
        mJumlahProduk = v.findViewById(R.id.jumlah_produk);
        mJumlahUang = v.findViewById(R.id.jumlah_uang);
        mTotalDompet = v.findViewById(R.id.total_dompet);
        mPemasukkan = v.findViewById(R.id.pemasukkan);
        mPengeluaran = v.findViewById(R.id.pengeluaran);

        mFotoDiri = v.findViewById(R.id.foto_diri);

        mSettingButton = v.findViewById(R.id.setting);
        mSettingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Setting clicked", Toast.LENGTH_SHORT)
                        .show();

                Fragment_setting fragment = Fragment_setting.newInstance(dataToko);
                FragmentManager fm = getFragmentManager();
                fragment.show(fm,"Fragment Setting");
            }
        });
        mButtonProduk = v.findViewById(R.id.button_produk);
        mButtonProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getContext(),"Produk button clicked", Toast.LENGTH_SHORT)
//                        .show();
                mCallbacks.onProdukButtonClicked();
            }
        });

        mButtonDompet = v.findViewById(R.id.button_dompet);
        mButtonDompet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallbacks.onDompetButtonClicked();
            }
        });

        updateUI();
        return v;
    }

    private void updateUI(){
        try{
            mFotoDiri.setImageBitmap(mPictureUtils.convertToImage(dataToko.getFoto_diri()));
        }catch (Exception e){
            Log.e(TAG, "onCreateView: ERROR PASANG PP", e);
        }
        mNamaPemilik.setText(dataToko.getNama_pemilik().toUpperCase());
        mTotalDompet.setText("Rp " + String.format("%,d", dataDompet.getUang()).replace(',', '.') + ",-");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        UPDATE DATA DOMPET
        mDompetViewModel.loadDompet(dataToko.getIdToko() + "").observe(
                getViewLifecycleOwner(),
                new Observer<Dompet>() {
                    @Override
                    public void onChanged(Dompet dompet) {
                        dataDompet = dompet;
                        updateUI();
                    }
                }
        );
    }

    public interface Callbacks{
        public void onProdukButtonClicked();
        public void onDompetButtonClicked();
    }

    private Fragment_menu_utama.Callbacks mCallbacks = null;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mCallbacks = (Fragment_menu_utama.Callbacks) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }
}
