package id.ac.polman.astra.nim0320190011.toko.fragment.user;

import android.app.AlertDialog;
import android.content.Context;
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
import java.util.List;

import id.ac.polman.astra.nim0320190011.toko.R;
import id.ac.polman.astra.nim0320190011.toko.Utils.PictureUtils;
import id.ac.polman.astra.nim0320190011.toko.api.model.Produk;
import id.ac.polman.astra.nim0320190011.toko.api.model.Toko;
import id.ac.polman.astra.nim0320190011.toko.api.viewmodel.Produk_view_model;
import id.ac.polman.astra.nim0320190011.toko.api.viewmodel.Toko_view_model;
import id.ac.polman.astra.nim0320190011.toko.fragment.produk.Fragment_edit_produk;
import id.ac.polman.astra.nim0320190011.toko.fragment.produk.Fragment_product_option;
import id.ac.polman.astra.nim0320190011.toko.fragment.produk.Fragment_put_produk;

public class Fragment_produk_user extends Fragment {
    private static final String TAG = "Fragment_produk_user";

    Produk_view_model mProdukViewModel;
    private List<Produk> mProdukList;
    Toko dataToko;
    PictureUtils mPictureUtils;

    private RecyclerView mProdukRecyclerView;
    private ProdukAdapter mAdapter;

    private TextView mNamaPemilik;
    private EditText mCariProduk;
    private ImageView mBack;
    private ImageView mButton_keranjang;

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
        mAdapter = new ProdukAdapter(mProdukList);
        mProdukRecyclerView.setAdapter(mAdapter);
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
        mAdapter = new ProdukAdapter(Collections.<Produk>emptyList());
        mPictureUtils = new PictureUtils();
        mProdukList = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: Called ");
        View v = inflater.inflate(R.layout.fragment_produk_user ,container, false);
        
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
                Log.i(TAG, "onClick: Apakek ini button");
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

        public void bind(Produk produk){
            mProduk = produk;
            mNamaProduk.setText(mProduk.getNama());
            try{
                mFotoProduk.setImageBitmap(mPictureUtils.convertToImage(produk.getFoto()));
            }catch (Exception e){
            }
            mHargaProduk.setText("Rp. " + String.format("%,d", mProduk.getHarga()).replace(',', '.') + ",-");
            mJumlahProduk.setText("stok : " + mProduk.getJumlah());
            mMerkProduk.setText("(" + mProduk.getMerk() + ")");
            
            mButton_tambah.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "onClick: Apakaek ini ");
                }
            });
            
            mButton_kurang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "onClick: Apakek ini juga");
                }
            });
        }

    }

    private class ProdukAdapter extends RecyclerView.Adapter<ProdukHolder>{

        private List<Produk> mProdukList;

        public ProdukAdapter(List<Produk> produks){
            mProdukList = produks;
        }

        @Override
        public ProdukHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new ProdukHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(ProdukHolder holder, int position){
            Produk produk = mProdukList.get(position);
            holder.bind(produk);
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

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
