package id.ac.polman.astra.nim0320190011.toko.fragment.detail;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import id.ac.polman.astra.nim0320190011.toko.R;
import id.ac.polman.astra.nim0320190011.toko.api.model.Dt_Produk_aktivitas;
import id.ac.polman.astra.nim0320190011.toko.api.model.Produk;
import id.ac.polman.astra.nim0320190011.toko.api.model.Produk_aktivitas;
import id.ac.polman.astra.nim0320190011.toko.api.viewmodel.Aktivitas_produk_view_model;
import id.ac.polman.astra.nim0320190011.toko.api.viewmodel.Dt_aktivitas_produk_view_model;
import id.ac.polman.astra.nim0320190011.toko.api.viewmodel.Produk_view_model;

public class Detail_aktivitas_produk extends DialogFragment {
    private static final String TAG = "Detail_aktivitas_produk";

    private Produk_aktivitas mProdukAktivitas;

    private TextView mKeteranganAktivitas;
    private TextView mTanggal;
    private TextView mWaktu;
    private TextView mNominal;
    private TextView mKeterangan;

    private Button mButtonKembali;

    private List<Dt_Produk_aktivitas> mDtProdukAktivitas;
    private List<Produk> mProdukList;
    private Dt_aktivitas_produk_view_model mDtAktivitasProdukViewModel;
    private Produk_view_model mProdukViewModel;

    private DtAktivitasAdapter mDtAktivitasAdapter;
    private RecyclerView mRecyclerView;

    public Detail_aktivitas_produk(Produk_aktivitas aktivitas) {
        mProdukAktivitas = aktivitas;
    }

    public static Detail_aktivitas_produk newInstance(Produk_aktivitas aktivitas){
        return new Detail_aktivitas_produk(aktivitas);
    }

    public Dt_aktivitas_produk_view_model getDtAktivitasProdukViewModel(){
        Log.i(TAG, "getDtAktivitasProdukViewModelList: called");
        if(mDtAktivitasProdukViewModel == null){
            mDtAktivitasProdukViewModel = new ViewModelProvider(this)
                    .get(Dt_aktivitas_produk_view_model.class);
        }
        Log.i(TAG, "getDtAktivitasProdukViewModelList: called 2");
        return mDtAktivitasProdukViewModel;
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDtAktivitasProdukViewModel = getDtAktivitasProdukViewModel();
        mProdukViewModel = getProdukViewModel();
        mDtProdukAktivitas = new ArrayList<>();
        mProdukList = new ArrayList<>();
        mDtAktivitasAdapter = new DtAktivitasAdapter(new ArrayList<>());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_produk_aktivitas_detail, container, false);

        mKeteranganAktivitas = v.findViewById(R.id.keterangan_aktivitas);
        mTanggal = v.findViewById(R.id.tanggal);
        mWaktu = v.findViewById(R.id.waktu);
        mNominal = v.findViewById(R.id.nominal);
        mKeterangan = v.findViewById(R.id.keterangan);

        mButtonKembali = v.findViewById(R.id.button_kembali);

        mRecyclerView = v.findViewById(R.id.dt_produk_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mButtonKembali.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getDialog().dismiss();
                    }
                }
        );

        mProdukViewModel.getProduks().observe(
                getViewLifecycleOwner(),
                new Observer<List<Produk>>() {
                    @Override
                    public void onChanged(List<Produk> produks) {
                        mProdukList = produks;
                        refresh();
                    }
                }
        );

        updateUI();
    }

    private void refresh(){
        mDtAktivitasProdukViewModel.getDtAktivitastByIdAkt(mProdukAktivitas.getIdAkt()).observe(
                getViewLifecycleOwner(),
                new Observer<List<Dt_Produk_aktivitas>>() {
                    @Override
                    public void onChanged(List<Dt_Produk_aktivitas> dt_produk_aktivitas) {
                        mDtProdukAktivitas = dt_produk_aktivitas;
                        for(Dt_Produk_aktivitas a : mDtProdukAktivitas){
                            Log.i(TAG, "onChanged: ID : " + a.getId());
                            Log.i(TAG, "onChanged: Jumlah : " + a.getJumlah());
                            Log.i(TAG, "onChanged: Harga : " + a.getHarga());
                        }
                        mDtAktivitasAdapter = new DtAktivitasAdapter(mDtProdukAktivitas);
                        mRecyclerView.setAdapter(mDtAktivitasAdapter);
                    }
                }
        );

    }
    private void updateUI(){
        switch (mProdukAktivitas.getKode_akt()){
            case 0 :
                mKeteranganAktivitas.setText(R.string.pengambilan);
                break;
            case 1 :
                mKeteranganAktivitas.setText(R.string.penjualan);
                break;
        }
        mWaktu.setText(mProdukAktivitas.getCreadate().substring(11,16) + " WIB");
        mTanggal.setText(mProdukAktivitas.getCreadate().substring(0,10));
        mNominal.setText("Rp " + String.format("%,d", mProdukAktivitas.getJumlah()).replace(',', '.') + ",-");
        mKeterangan.setText(mProdukAktivitas.getKeterangan());
    }

    private class DtAktivitasHolder extends RecyclerView.ViewHolder{
        private TextView mNamaProduk;
        private TextView mJumlahProduk;
        private TextView mHargaProduk;
        private TextView mTotalProduk;
        private ConstraintLayout mItemPutProduk;
        private Dt_Produk_aktivitas mDt_produk_aktivitas;

        public DtAktivitasHolder (LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.fragment_item_put_produk, parent, false));

            mNamaProduk = itemView.findViewById(R.id.nama_produk);
            mJumlahProduk = itemView.findViewById(R.id.jumlah_produk);
            mHargaProduk =  itemView.findViewById(R.id.harga_produk);
            mItemPutProduk = itemView.findViewById(R.id.fragment_item_put_produk);
            mTotalProduk = itemView.findViewById(R.id.total_produk);
        }

        public void bind(Dt_Produk_aktivitas dt){
            for (Produk p : mProdukList){
                if (p.getIdProduk() == dt.getIdProduk()){
                    mNamaProduk.setText(p.getNama());
                }
            }
            mJumlahProduk.setText(dt.getJumlah() + "");
            mHargaProduk.setText("Rp. " + String.format("%,d", dt.getHarga()).replace(',', '.') + ",-");
            mTotalProduk.setText("Rp. " + String.format("%,d", (dt.getHarga() * dt.getJumlah())).replace(',', '.') + ",-");
        }
    }

    private class DtAktivitasAdapter extends RecyclerView.Adapter<DtAktivitasHolder>{

        private List<Dt_Produk_aktivitas> mDtProdukAktivitas;

        public DtAktivitasAdapter(List<Dt_Produk_aktivitas> dt){
            mDtProdukAktivitas = dt;
        }

        @NonNull
        @Override
        public DtAktivitasHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new DtAktivitasHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull DtAktivitasHolder holder, int position) {
            Dt_Produk_aktivitas dt = mDtProdukAktivitas.get(position);
            holder.bind(dt);
        }

        @Override
        public int getItemCount() {
            return mDtProdukAktivitas.size();
        }
    }

}
