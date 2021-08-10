package id.ac.polman.astra.nim0320190011.toko.fragment.user;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import id.ac.polman.astra.nim0320190011.toko.R;
import id.ac.polman.astra.nim0320190011.toko.Utils.PictureUtils;
import id.ac.polman.astra.nim0320190011.toko.api.model.Produk;
import id.ac.polman.astra.nim0320190011.toko.api.model.Toko;
import id.ac.polman.astra.nim0320190011.toko.api.viewmodel.Produk_view_model;
import id.ac.polman.astra.nim0320190011.toko.api.viewmodel.Toko_view_model;
import id.ac.polman.astra.nim0320190011.toko.fragment.DatePickerFragment;
import id.ac.polman.astra.nim0320190011.toko.fragment.Fragment_daftar_toko;
import id.ac.polman.astra.nim0320190011.toko.fragment.Fragment_setting;
import id.ac.polman.astra.nim0320190011.toko.fragment.produk.Fragment_edit_produk;
import id.ac.polman.astra.nim0320190011.toko.fragment.produk.Fragment_product_option;
import id.ac.polman.astra.nim0320190011.toko.fragment.produk.Fragment_put_produk;

public class Fragment_produk_user extends Fragment implements  Fragment_keranjang.Callbacks{
    private static final String TAG = "Fragment_produk_user";

    Produk_view_model mProdukViewModel;
    private List<Produk> mProdukList;
    private List<Produk> mKeranjangs;

    Toko dataToko;
    PictureUtils mPictureUtils;

    private RecyclerView mProdukRecyclerView;
    private ProdukAdapter mAdapter;

    private TextView mNamaPemilik;
    private EditText mCariProduk;
    private ImageView mBack;
    private ImageView mButton_keranjang;

//    ITEM TOKO
    private ImageView foto_toko;
    private TextView text_nama_toko;
    private TextView text_address;
    private TextView telepon;
    private TextView jumlah_produk;

    Fragment_keranjang fragment;

    public static Fragment_produk_user newInstance(Toko in) {
        return new Fragment_produk_user(in);
    }

    private Fragment_produk_user (Toko t){
        dataToko = t;
    }

    //    private void updateUI(List<Produk> produks)
    private void updateUI()
    {
        Log.i(TAG, "updateUI called");
        mAdapter = new ProdukAdapter(mProdukList, mKeranjangs);
        mProdukRecyclerView.setAdapter(mAdapter);
        jumlah_produk.setText(mProdukList.size() + " PRODUK");
    }

    private void filter(String text) {
        ArrayList<Produk> filteredList = new ArrayList<>();
        for (Produk item : mProdukList) {
            if (item.getNama().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        mAdapter.filterList(filteredList);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mProdukViewModel = new ViewModelProvider(this)
                .get(Produk_view_model.class);
        mAdapter = new ProdukAdapter(Collections.<Produk>emptyList(), new ArrayList<>());
        mPictureUtils = new PictureUtils();
        mProdukList = new ArrayList<>();
        mKeranjangs = new ArrayList<>();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: Called ");
        View v = inflater.inflate(R.layout.fragment_produk_user ,container, false);

        foto_toko = v.findViewById(R.id.foto_toko);
        text_nama_toko = v.findViewById(R.id.text_nama_toko);
        text_address= v.findViewById(R.id.text_address);
        telepon= v.findViewById(R.id.telepon);
        jumlah_produk= v.findViewById(R.id.jumlah_produk);

//        UPDATE UI
        try{
            foto_toko.setImageBitmap(mPictureUtils.convertToImage(dataToko.getFoto_diri()));
        }catch (Exception e){
            Log.e(TAG, "onCreateView: ERROR PASANG PP", e);
        }
        text_nama_toko.setText(dataToko.getNama_pemilik().toUpperCase());
        text_address.setText(dataToko.getAlamatToko());
        telepon.setText(dataToko.getNo_telfon());

        mCariProduk = (EditText) v.findViewById(R.id.cari_produk);
        mCariProduk.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });


        mBack = v.findViewById(R.id.back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        
        mButton_keranjang = v.findViewById(R.id.button_keranjang);
        mButton_keranjang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = Fragment_keranjang.newInstance(mKeranjangs, dataToko);
                fragment.setTargetFragment(Fragment_produk_user.this, 1);
                FragmentManager fm = getFragmentManager();

                fragment.show(fm,"Fragment Keranjang");
            }
        });

        mNamaPemilik = (TextView) v.findViewById(R.id.header_title);
        mNamaPemilik.setText(dataToko.getNama_pemilik().toUpperCase());

        mProdukRecyclerView = (RecyclerView) v.findViewById(R.id.produk_recycler_view);
        mProdukRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mProdukRecyclerView.setAdapter(mAdapter);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            Thread.sleep(100);
        }catch (InterruptedException ie){
            Thread.currentThread().interrupt();
        }
        Log.i(TAG, "onViewCreated() called");
        mProdukViewModel.getProduksByIdToko(dataToko.getIdToko()).observe(
                getViewLifecycleOwner(),
                new Observer<List<Produk>>() {
                    @Override
                    public void onChanged(List<Produk> produks) {
                        mProdukList = produks;
                        updateUI();
                        Log.i(TAG, "Got Produk: " + produks.size());
                    }
                }
        );
    }

    @Override
    public void onDelete(List<Produk> a) {
        mKeranjangs = a;
        mAdapter = new ProdukAdapter(mProdukList, mKeranjangs);
        mProdukRecyclerView.setAdapter(mAdapter);
    }

    private class ProdukHolder extends RecyclerView.ViewHolder{

        private ImageView mFotoProduk;
        private TextView mNamaProduk;
        private TextView mHargaProduk;
        private TextView mJumlahProduk;
        private TextView mMerkProduk;

        private TextView mKeranjang;
        private ImageView mButton_tambah;
        private ImageView mButton_kurang;

        private Produk mProduk;

        public ProdukHolder (LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.fragment_item_produk_user, parent, false));

            mFotoProduk = (ImageView) itemView.findViewById(R.id.foto_produk);
            mNamaProduk = (TextView) itemView.findViewById(R.id.text_nama_produk);
            mHargaProduk = (TextView) itemView.findViewById(R.id.text_harga_produk);
            mJumlahProduk = (TextView) itemView.findViewById(R.id.text_jumlah_produk);
            mMerkProduk = (TextView) itemView.findViewById(R.id.text_merk_produk);

            mKeranjang = itemView.findViewById(R.id.jumlah_keranjang);
            mButton_kurang = itemView.findViewById(R.id.button_minus);
            mButton_tambah = itemView.findViewById(R.id.button_plus);
        }

        public void bind(Produk produk, List<Produk> keranjang){
            mProduk = produk;
            mKeranjang.setText(getText(R.string.keranjang) + " : 0");
            mNamaProduk.setText(mProduk.getNama());
            try{
                mFotoProduk.setImageBitmap(mPictureUtils.convertToImage(produk.getFoto()));
            }catch (Exception e){
            }
            mHargaProduk.setText("Rp. " + String.format("%,d", mProduk.getHarga()).replace(',', '.') + ",-");
            mJumlahProduk.setText("stok : " + mProduk.getJumlah());
            mMerkProduk.setText("(" + mProduk.getMerk() + ")");

            if(produk.getJumlah() == 0){
                mButton_tambah.setEnabled(false);
            }
            mButton_tambah.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "onClick: Apakaek ini ");
                    Produk p = new Produk();
                    boolean ada = false;
                    for(Produk pr : keranjang){
                        if(pr.getIdProduk() == produk.getIdProduk()){
                            if(produk.getJumlah() != pr.getJumlah()){
                                pr.setJumlah(pr.getJumlah()+1);
                            }
                            ada = true;
                            p = pr;
                            break;
                        }
                    }
                    if(!ada){
                        p.setNama(produk.getNama());
                        p.setIdProduk(produk.getIdProduk());
                        p.setJumlah(1);
                        p.setHarga(produk.getHarga());
                        keranjang.add(p);
                    }
                    mKeranjang.setText(getText(R.string.keranjang) + " : " + p.getJumlah());
                }
            });

            mButton_kurang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "onClick: Apakek ini juga");
                    Produk p = new Produk();
                    boolean ada = false;
                    for(Produk pr : keranjang){
                        if(pr.getIdProduk() == produk.getIdProduk()){
                            if(pr.getJumlah() > 0){
                                pr.setJumlah(pr.getJumlah()-1);
                            }else{
                                keranjang.remove(pr);
                            }
                            ada = true;
                            p = pr;
                            break;
                        }
                    }
                    mKeranjang.setText(getText(R.string.keranjang) + " : " + p.getJumlah());
                }
            });

            for(Produk pr : keranjang){
                if(pr.getIdProduk() == produk.getIdProduk()){
                    mKeranjang.setText(getText(R.string.keranjang) + " : " + pr.getJumlah());
                    break;
                }
            }
        }
    }

    private class ProdukAdapter extends RecyclerView.Adapter<ProdukHolder>{

        private List<Produk> mProdukList;
        private List<Produk> keranjang;

        public ProdukAdapter(List<Produk> produks, List<Produk> keranjang){
            mProdukList = produks;
            this.keranjang = keranjang;
        }

        @Override
        public ProdukHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new ProdukHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(ProdukHolder holder, int position){
            Produk produk = mProdukList.get(position);
            holder.bind(produk, keranjang);
        }

        @Override
        public int getItemCount(){
            return mProdukList.size();
        }

        public void filterList(ArrayList<Produk> filteredList) {
            mProdukList = filteredList;
            notifyDataSetChanged();
        }
    }

}
