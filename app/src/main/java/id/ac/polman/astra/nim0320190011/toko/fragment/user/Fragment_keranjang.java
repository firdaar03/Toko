package id.ac.polman.astra.nim0320190011.toko.fragment.user;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import id.ac.polman.astra.nim0320190011.toko.R;
import id.ac.polman.astra.nim0320190011.toko.api.model.Produk;

public class Fragment_keranjang extends DialogFragment {
    private static final String TAG = "Fragment_keranjang";

    private List<Produk> mProdukList;
    
    private Button mHapus;
    private Button mSimpan;

    private KeranjangAdapter mKeranjangAdapter;
    private RecyclerView mProdukRecyclerView;

    private TextView mJumlahTotal;
    private TextView mHargaTotal;

    private int jumlahTotal;
    private int hargaTotal;

    public static Fragment_keranjang newInstance(List<Produk> p){
        return new Fragment_keranjang(p);
    }
    
    private Fragment_keranjang(List<Produk> a){
        mProdukList = a;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mKeranjangAdapter = new KeranjangAdapter(new ArrayList<>());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_keranjang, container, false);
        
        mHapus = v.findViewById(R.id.btn_hapus);
        mHapus.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mProdukList = new ArrayList<>();
                        mKeranjangAdapter = new KeranjangAdapter(mProdukList);
                        mProdukRecyclerView.setAdapter(mKeranjangAdapter);
                        updateUI();
                    }
                }
        );
        
        mSimpan = v.findViewById(R.id.btn_simpan_keranjang);
        mSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: Apakek eheheh");
            }
        });

        mProdukRecyclerView = v.findViewById(R.id.keranjang_recycler_view);
        mProdukRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mJumlahTotal = (TextView) v.findViewById(R.id.jumlah_total);
        mHargaTotal = (TextView) v.findViewById(R.id.harga_total);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mKeranjangAdapter = new KeranjangAdapter(mProdukList);
        mProdukRecyclerView.setAdapter(mKeranjangAdapter);

        updateUI();

    }

    private void updateUI() {
        hargaTotal = 0;
        jumlahTotal = 0;
        for(Produk asdw : mProdukList){
            hargaTotal += asdw.getHarga() * asdw.getJumlah();
            jumlahTotal += asdw.getJumlah();
        }
        mHargaTotal.setText("Rp. " + String.format("%,d", hargaTotal).replace(',', '.') + ",-");
        mJumlahTotal.setText(jumlahTotal + "");
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    private class KeranjangHolder extends RecyclerView.ViewHolder{

        private TextView mNamaProduk;
        private TextView mJumlahProduk;
        private TextView mHargaProduk;
        private TextView mTotalProduk;
        private ConstraintLayout mItemPutProduk;
        private Produk mProduk;
        private int totalProduk;

        public KeranjangHolder (LayoutInflater inflater, ViewGroup parent) {
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

    private class KeranjangAdapter extends RecyclerView.Adapter<KeranjangHolder> {

        private List<Produk> mProdukList;

        public KeranjangAdapter(List<Produk> produks) {
            mProdukList = produks;
        }


        @NonNull
        @Override
        public KeranjangHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new KeranjangHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull KeranjangHolder holder, int position) {
            Produk produk = mProdukList.get(position);
            holder.bind(produk);
        }

        @Override
        public int getItemCount() {
            return mProdukList.size();
        }
    }
}
