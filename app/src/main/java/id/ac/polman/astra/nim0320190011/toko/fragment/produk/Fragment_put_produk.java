package id.ac.polman.astra.nim0320190011.toko.fragment.produk;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class Fragment_put_produk extends Fragment {
    private static final String TAG = "Fragment_put_produk";

    private AutoCompleteTextView mCariProduk;
    private Button mButtonCariProduk;
    PictureUtils mPictureUtils;
    private ImageView mFotoProduk;
    private TextView mNamaProduk;
    private TextView mHargaProduk;
    private TextView mJumlahProduk;
    private TextView mMerkProduk;
    private TextView mJumlah;
    private Button mTambahProduk;
    private TextView mJumlahTotal;
    private TextView mHargaTotal;

    private Produk_view_model mProdukViewModel;
    private Produk mProduk;
    private Toko dataToko;
    private List<Produk> mProdukList;
    private List<Produk> mPutProdukList;

    private PutProdukAdapter mPutProdukAdapter;
    private RecyclerView mPutProdukRecyclerView;

    public Produk_view_model getProdukViewModel(){
        Log.i(TAG, "getProdukViewModelList: called");
        if(mProdukViewModel == null){
            mProdukViewModel = new ViewModelProvider(this)
                    .get(Produk_view_model.class);
        }
        Log.i(TAG, "getProdukViewModelList: called 2");
        return mProdukViewModel;
    }

    public static Fragment_put_produk newInstance(Toko t) {
        return new Fragment_put_produk(t);
    }

    private Fragment_put_produk(Toko t){
        dataToko = t;
    }

    private void updateSearch(Produk p)
    {
        Log.i(TAG, "updateSearch called");
        try{
            mFotoProduk.setImageBitmap(mPictureUtils.convertToImage(p.getFoto()));
        }catch (Exception e){
        }
        mNamaProduk.setText(p.getNama());
        mMerkProduk.setText("(" + p.getMerk() + ")");
        mHargaProduk.setText("Rp. " + String.format("%,d", p.getHarga()).replace(',', '.') + ",-");
        mJumlahProduk.setText("stok : " + p.getJumlah());

        mProduk = p;


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: called");
        super.onCreate(savedInstanceState);

        mProdukList = new ArrayList<>();
        mProdukViewModel = getProdukViewModel();
        mPictureUtils = new PictureUtils();
        mPutProdukAdapter = new PutProdukAdapter(new ArrayList<>());
        mPutProdukList = new ArrayList<>();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: Called ");
        View v = inflater.inflate(R.layout.fragment_put_produk, container, false);

        mCariProduk = v.findViewById(R.id.cari_nama_produk);

        mButtonCariProduk = (Button) v.findViewById(R.id.btn_cari);
        mButtonCariProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = mCariProduk.getText() + "";

                if (!nama.equals("")){
                    filter(nama);
                } else {
                    Toast.makeText(getContext(), "Produk tidak ditemukan !",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        mProdukViewModel.getProduksByIdToko(dataToko.getIdToko()).observe(
                getViewLifecycleOwner(),
                new Observer<List<Produk>>() {
                    @Override
                    public void onChanged(List<Produk> produks) {

                        ArrayList<String> nama_nama = new ArrayList<>();
                        mProdukList = produks;
                        for(Produk p : produks){
                            nama_nama.add(p.getNama());
                        }

                        ArrayAdapter<String> adapterNama = new ArrayAdapter<String>
                                (getContext(), android.R.layout.select_dialog_item, nama_nama);
                        mCariProduk.setAdapter(adapterNama);
                        mCariProduk.setThreshold(1);//will start working from first character

                        Log.i(TAG, "Got Produk: " + produks.size());
                    }
                }
        );

        mFotoProduk = (ImageView) v.findViewById(R.id.foto_produk);
        mNamaProduk = (TextView) v.findViewById(R.id.nama_produk);
        mHargaProduk = (TextView) v.findViewById(R.id.harga_produk);
        mJumlahProduk = (TextView) v.findViewById(R.id.jumlah_produk);
        mMerkProduk = (TextView) v.findViewById(R.id.merk_produk);
        mJumlah = (TextView) v.findViewById(R.id.jml_produk);

        mJumlahTotal = (TextView) v.findViewById(R.id.jumlah_total);
        mHargaTotal = (TextView) v.findViewById(R.id.harga_total);

        mTambahProduk = (Button) v.findViewById(R.id.btn_add);
        mTambahProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mJumlah.getText().toString().equals("") ){
                    mProduk.setJumlah(Integer.parseInt(mJumlah.getText().toString()));

//                    Produk p = new Produk();
//                    p.setNama(mProduk.getNama());
//                    p.setHarga(mProduk.getHarga());
//                    p.setJumlah(Integer.parseInt(mJumlah.getText().toString()));

                    mPutProdukList.add(mProduk);

//                    mPutProdukList.add(p);
                    for(Produk x : mPutProdukList){
                        Log.i(TAG, "onClick: " + x.getJumlah());
                    }
                    mPutProdukAdapter = new PutProdukAdapter(mPutProdukList);
                    mPutProdukRecyclerView.setAdapter(mPutProdukAdapter);

                } else {
                    mJumlah.setError("Jumlah harus diisi");
                }

            }
        });

        mPutProdukRecyclerView = (RecyclerView) v.findViewById(R.id.put_produk_recycler_view);
        mPutProdukRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return v;
    }

    private void filter(String text) {
        for (Produk p : mProdukList){
            if (p.getNama().toLowerCase().equals(text.toLowerCase())){
                updateSearch(p);
                break;
            }
        }
    }

    private class PutProdukHolder extends RecyclerView.ViewHolder{

        private TextView mNamaProduk;
        private TextView mJumlahProduk;
        private TextView mHargaProduk;
        private TextView mTotalProduk;
        private ConstraintLayout mItemPutProduk;
        private Produk mProduk;
        private int totalProduk;

        public PutProdukHolder (LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.fragment_item_put_produk, parent, false));

            mNamaProduk = (TextView) itemView.findViewById(R.id.nama_produk);
            mJumlahProduk = (TextView) itemView.findViewById(R.id.jumlah_produk);
            mHargaProduk = (TextView) itemView.findViewById(R.id.harga_produk);
            mTotalProduk = (TextView) itemView.findViewById(R.id.total_produk);

            mItemPutProduk = itemView.findViewById(R.id.fragment_item_put_produk);
        }

        public void bind(Produk produk) {
            mProduk = produk;
            mNamaProduk.setText(mProduk.getNama());
            mJumlahProduk.setText(mProduk.getJumlah() + "");
            mHargaProduk.setText("Rp. " + String.format("%,d", mProduk.getHarga()).replace(',', '.') + ",-");
            totalProduk = mProduk.getJumlah() * mProduk.getHarga();
            mTotalProduk.setText("Rp. " + String.format("%,d", totalProduk).replace(',', '.') + ",-");
        }
    }

    private class PutProdukAdapter extends RecyclerView.Adapter<PutProdukHolder> {

        private List<Produk> mProdukList;

        public PutProdukAdapter(List<Produk> produks) {
            mProdukList = produks;
        }

        @NonNull
        @Override
        public PutProdukHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new PutProdukHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull PutProdukHolder holder, int position) {
            Produk produk = mProdukList.get(position);
            holder.bind(produk);
        }

        @Override
        public int getItemCount() {
            return mProdukList.size();
        }
    }


}
