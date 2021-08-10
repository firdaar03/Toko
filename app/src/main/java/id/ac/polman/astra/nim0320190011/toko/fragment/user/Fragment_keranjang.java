package id.ac.polman.astra.nim0320190011.toko.fragment.user;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import id.ac.polman.astra.nim0320190011.toko.R;
import id.ac.polman.astra.nim0320190011.toko.api.model.Produk;
import id.ac.polman.astra.nim0320190011.toko.api.model.Toko;
import id.ac.polman.astra.nim0320190011.toko.fragment.DatePickerFragment;

public class Fragment_keranjang extends DialogFragment {
    private static final String TAG = "Fragment_keranjang";

    private List<Produk> mProdukList;
    private Toko dataToko;
    
    private Button mHapus;
    private Button mSimpan;

    private KeranjangAdapter mKeranjangAdapter;
    private RecyclerView mProdukRecyclerView;

    private TextView mJumlahTotal;
    private TextView mHargaTotal;

    private int jumlahTotal;
    private int hargaTotal;

    private boolean hapus;

    public static Fragment_keranjang newInstance(List<Produk> p, Toko t){
        return new Fragment_keranjang(p, t);
    }
    
    private Fragment_keranjang(List<Produk> a, Toko t){
        mProdukList = a;
        dataToko = t;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mKeranjangAdapter = new KeranjangAdapter(new ArrayList<>());
        hapus = false;
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

                        callbacks = (Callbacks) getTargetFragment();
                        callbacks.onDelete(mProdukList);
                        updateUI();
                    }
                }
        );
        
        mSimpan = v.findViewById(R.id.btn_simpan_keranjang);
        mSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog
                        .Builder(getContext())
                        .setTitle(getString(R.string.simpan_keranjang))
                        .setMessage(getString(R.string.keranjang_message))
                        .setPositiveButton("Ya, simpan", (dialogInterface, i) -> {
                            simpanKeranjang();
                        })
                        .setNegativeButton("Tidak", null)
                        .show();
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

    public void simpanKeranjang() {
        hargaTotal = 0;
        jumlahTotal = 0;
        int i = 0;
        String tanggal = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        String message =  "```-- LIST KERANJANG  --```\n\n"
                + "Tanggal\t: " + tanggal + "\n\n";
        String message2 = "";

        Intent n = new Intent(Intent.ACTION_SEND);
        for(Produk asdw : mProdukList){
            i += 1;
            message2 += i + ". " + asdw.getNama() + "\n"
                        + "    Jumlah\t: " + asdw.getJumlah() + "\n"
                        + "    Harga\t: " + "Rp "  + String.format("%,d", asdw.getHarga()).replace(',', '.') + ",-" + "\n";
            hargaTotal += asdw.getHarga() * asdw.getJumlah();
            jumlahTotal += asdw.getJumlah();
        }

        String message3 = "\nJumlah Total\t: " + jumlahTotal + "\n"
                        + "Harga Total\t: " + "Rp " + String.format("%,d", hargaTotal).replace(',', '.') + ",-";

        n.setType("text/plain");
        n.putExtra(Intent.EXTRA_TEXT, message + message2 + message3);
        n = Intent.createChooser(n, getString(R.string.pilih_simpan_text));
        startActivity(n);

    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Log.i(TAG, "onDismiss: ");
//        if(hapus){
//            getFragmentManager().popBackStack();
//
//            Fragment fragment = Fragment_produk_user.newInstance(dataToko);
//            FragmentManager fm = getFragmentManager();
//            FragmentTransaction fragmentTransaction = fm.beginTransaction();
//            fragmentTransaction.replace(R.id.fragment_container, fragment);
//            fragmentTransaction.addToBackStack(null);
//            fragmentTransaction.commit(); // save the changes
//        }
    }

    private Callbacks callbacks;

    public interface Callbacks{
        public void onDelete(List<Produk> a);
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
