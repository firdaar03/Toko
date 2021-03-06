package id.ac.polman.astra.nim0320190011.toko.fragment.produk;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import id.ac.polman.astra.nim0320190011.toko.R;
import id.ac.polman.astra.nim0320190011.toko.Utils.PictureUtils;
import id.ac.polman.astra.nim0320190011.toko.api.model.Dompet;
import id.ac.polman.astra.nim0320190011.toko.api.model.Produk;
import id.ac.polman.astra.nim0320190011.toko.api.model.Produk_aktivitas;
import id.ac.polman.astra.nim0320190011.toko.api.model.Toko;
import id.ac.polman.astra.nim0320190011.toko.api.viewmodel.Aktivitas_produk_view_model;
import id.ac.polman.astra.nim0320190011.toko.api.viewmodel.Dompet_view_model;
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
    private Button mDiambil;
    private Button mTerjual;
    private ImageView mBack;

    private Produk_view_model mProdukViewModel;
    private Aktivitas_produk_view_model mAktivitasProdukViewModel;
    private Dompet_view_model mDompetViewModel;

    private Produk mProduk;
    private Toko dataToko;
    private List<Produk> mProdukList;
    private List<Produk> mPutProdukList;

    private PutProdukAdapter mPutProdukAdapter;
    private RecyclerView mPutProdukRecyclerView;

    private int jumlahTotal;
    private int hargaTotal;

    public Produk_view_model getProdukViewModel(){
        Log.i(TAG, "getProdukViewModelList: called");
        if(mProdukViewModel == null){
            mProdukViewModel = new ViewModelProvider(this)
                    .get(Produk_view_model.class);
        }
        Log.i(TAG, "getProdukViewModelList: called 2");
        return mProdukViewModel;
    }

    public Dompet_view_model getDompetViewModel(){
        Log.i(TAG, "getDompetViewModel: ");
        if(mDompetViewModel == null){
            mDompetViewModel = new ViewModelProvider(this)
                    .get(Dompet_view_model.class);
        }
        return mDompetViewModel;
    }

    public Aktivitas_produk_view_model getAktivitasProdukViewModel(){
        Log.i(TAG, "getAktivitasProdukViewModelList: called");
        if(mAktivitasProdukViewModel == null){
            mAktivitasProdukViewModel = new ViewModelProvider(this)
                    .get(Aktivitas_produk_view_model.class);
        }
        Log.i(TAG, "getAktivitasProdukViewModelList: called 2");
        return mAktivitasProdukViewModel;
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
        mProdukViewModel = getProdukViewModel();
        mAktivitasProdukViewModel = getAktivitasProdukViewModel();
        mDompetViewModel = getDompetViewModel();

        mProdukList = new ArrayList<>();
        mPutProdukAdapter = new PutProdukAdapter(new ArrayList<>());
        mPutProdukList = new ArrayList<>();

        mPictureUtils = new PictureUtils();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: Called ");
        View v = inflater.inflate(R.layout.fragment_produk_put, container, false);

        mBack = v.findViewById(R.id.back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

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
                                (getContext(), R.layout.layout_item_autocomplete, R.id.search, nama_nama);
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
                try{
                    if (mCariProduk.getText().toString().equals("")) {
                        mCariProduk.setError(getText(R.string.tidak_boleh_kosong));
                    } else {
                        if (mNamaProduk.getText().toString().equals("")) {
                            Toast.makeText(getContext(), getText(R.string.tidak_boleh_kosong),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            if (!mJumlah.getText().toString().equals("") ){
                                if(Integer.parseInt(mJumlah.getText().toString()) > mProduk.getJumlah()){
                                    Toast.makeText(getContext(), getText(R.string.tidak_boleh_lebih),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    mProduk.setJumlah(Integer.parseInt(mJumlah.getText().toString()));

                                    Produk p = new Produk();
                                    p.setIdProduk(mProduk.getIdProduk());
                                    p.setNama(mProduk.getNama());
                                    p.setHarga(mProduk.getHarga());
                                    p.setJumlah(Integer.parseInt(mJumlah.getText().toString()));

                                    for(Produk x : mPutProdukList){
                                        Log.i(TAG, "onClick: " + x.getJumlah());
                                        if(x.getNama().equals(p.getNama())){
                                            mPutProdukList.remove(x);
                                            break;
                                        }
                                    }

                                    mPutProdukList.add(p);

                                    hargaTotal = 0;
                                    jumlahTotal = 0;
                                    for(Produk asdw : mPutProdukList){
                                        hargaTotal += asdw.getHarga() * asdw.getJumlah();
                                        jumlahTotal += asdw.getJumlah();
                                    }
                                    mHargaTotal.setText("Rp. " + String.format("%,d", hargaTotal).replace(',', '.') + ",-");
                                    mJumlahTotal.setText(jumlahTotal + "");


                                    mPutProdukAdapter = new PutProdukAdapter(mPutProdukList);
                                    mPutProdukRecyclerView.setAdapter(mPutProdukAdapter);

                                    mFotoProduk.setImageResource(0);
                                    mCariProduk.setText("");
                                    mNamaProduk.setText("");
                                    mJumlahProduk.setText("");
                                    mMerkProduk.setText("");
                                    mHargaProduk.setText("");
                                    mJumlah.setText("");
                                }
                            } else {
                                mJumlah.setError(getText(R.string.tidak_boleh_kosong));
                            }
                        }

                    }

                }catch (Exception e){

                }
            }
        });

        mDiambil = (Button) v.findViewById(R.id.btn_diambil);
        mDiambil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mPutProdukList.size() == 0){
                    Toast.makeText(getContext(), R.string.tidak_bisa_put, Toast.LENGTH_SHORT)
                            .show();
                    return;
                }

                Produk_aktivitas produk_aktivitas = new Produk_aktivitas();
                produk_aktivitas.setJumlah(hargaTotal);
                produk_aktivitas.setCreaby(dataToko.getEmail());
                produk_aktivitas.setKode_akt(0);
                produk_aktivitas.setKeterangan("karena rusak atau dicuri dan sebagainya");
                mProdukViewModel.trAmbilAktivitasProduk(produk_aktivitas);

                for(Produk x : mPutProdukList){
                    Log.i(TAG, "onClick: id " + x.getIdProduk());
                    x.setCreaby(dataToko.getEmail());
                    mProdukViewModel.ambil_produk(x);
                }
                Toast.makeText(getContext(), "Add Ambil Produk !",
                        Toast.LENGTH_SHORT).show();

                callbacks.onPutProduk(mPutProdukList);

                getFragmentManager().popBackStack();
            }

        });

        mTerjual = (Button) v.findViewById(R.id.btn_terjual);
        mTerjual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mPutProdukList.size() == 0){
                    Toast.makeText(getContext(), R.string.tidak_bisa_put, Toast.LENGTH_SHORT)
                            .show();
                    return;
                }

                Produk_aktivitas produk_aktivitas = new Produk_aktivitas();
                produk_aktivitas.setJumlah(hargaTotal);
                produk_aktivitas.setCreaby(dataToko.getEmail());
                produk_aktivitas.setKeterangan("Produk Dijual");
                produk_aktivitas.setKode_akt(1);
                mProdukViewModel.trAmbilAktivitasProduk(produk_aktivitas);

                Dompet dompet = new Dompet();
                dompet.setCreaby(dataToko.getEmail());
                dompet.setIdToko(dataToko.getIdToko());
                dompet.setUang(Integer.valueOf(hargaTotal));
                String keterangan = "Penjualan produk : ";

                for(Produk x : mPutProdukList){
                    Log.i(TAG, "onClick: id " + x.getIdProduk());
                    if(keterangan.equals("Penjualan produk : ")){
                        keterangan += x.getJumlah() + " x " + x.getNama();
                    }else{
                        keterangan += ", " + x.getJumlah() + " x " + x.getNama();
                    }
                    x.setCreaby(dataToko.getEmail());
                    mProdukViewModel.ambil_produk(x);
                }
                dompet.setModiby(keterangan);

                mDompetViewModel.penjualan(dompet);

                Toast.makeText(getContext(), "Add Jual Produk !",
                        Toast.LENGTH_SHORT).show();

                callbacks.onPutProduk(mPutProdukList);

                getFragmentManager().popBackStack();
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

    private Callbacks callbacks;

    public interface Callbacks{
        public void onPutProduk(List<Produk> pr);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        callbacks = (Callbacks) context;
    }
}
