package id.ac.polman.astra.nim0320190011.toko.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.DisplayMetrics;
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
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import id.ac.polman.astra.nim0320190011.toko.R;
import id.ac.polman.astra.nim0320190011.toko.Utils.PictureUtils;
import id.ac.polman.astra.nim0320190011.toko.api.model.Dompet;
import id.ac.polman.astra.nim0320190011.toko.api.model.Dompet_aktivitas;
import id.ac.polman.astra.nim0320190011.toko.api.model.Produk;
import id.ac.polman.astra.nim0320190011.toko.api.model.Produk_aktivitas;
import id.ac.polman.astra.nim0320190011.toko.api.model.Toko;
import id.ac.polman.astra.nim0320190011.toko.api.viewmodel.Aktivitas_dompet_view_model;
import id.ac.polman.astra.nim0320190011.toko.api.viewmodel.Aktivitas_produk_view_model;
import id.ac.polman.astra.nim0320190011.toko.api.viewmodel.Dompet_view_model;
import id.ac.polman.astra.nim0320190011.toko.api.viewmodel.Produk_view_model;
import id.ac.polman.astra.nim0320190011.toko.api.viewmodel.Toko_view_model;
import id.ac.polman.astra.nim0320190011.toko.api.viewmodel.Toko_view_model_list;
import id.ac.polman.astra.nim0320190011.toko.fragment.detail.Detail_aktivitas_dompet;
import id.ac.polman.astra.nim0320190011.toko.fragment.detail.Detail_aktivitas_produk;
import id.ac.polman.astra.nim0320190011.toko.fragment.dompet.Fragment_dompet_aktivitas;
import id.ac.polman.astra.nim0320190011.toko.fragment.produk.Fragment_produk_aktivitas;
import id.ac.polman.astra.nim0320190011.toko.fragment.produk.Fragment_tambah_produk;

public class Fragment_menu_utama extends Fragment
    {
        private static final String TAG = "Fragment_menu_utama";

        private Toko dataToko;
        private Dompet dataDompet;
        private List<Produk> dataProduk;
        private List<Dompet_aktivitas> mDompetAktivitas;
        private List<Produk_aktivitas> mProdukAktivitas;
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
        private RelativeLayout mAktivitasProduk;
        private RelativeLayout mAktivitasDompet;
        private androidx.swiperefreshlayout.widget.SwipeRefreshLayout mRefreshLayout;

        DisplayMetrics displayMetrics;

        private RecyclerView mRecyclerView;
        private RecyclerView mRecyclerView_1;

        private Toko_view_model mTokoViewModel;
        private Dompet_view_model mDompetViewModel;
        private Produk_view_model mProdukViewModel;
        private Aktivitas_dompet_view_model mAktivitasDompetViewModel;
        private Aktivitas_produk_view_model mAktivitasProdukViewModel;


        private AktivitasDompetAdapter mAktivitasDompetAdapter;
        private AktivitasProdukAdapter mAktivitasProdukAdapter;

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
        public Produk_view_model getProdukViewModel(){
            Log.i(TAG, "getProdukViewModelList: called");
            if(mProdukViewModel == null){
                mProdukViewModel = new ViewModelProvider(this)
                        .get(Produk_view_model.class);
            }
            Log.i(TAG, "getProdukViewModelList: called 2");
            return mProdukViewModel;
        }
        public Aktivitas_dompet_view_model getAktivitasDompetViewModel(){
            Log.i(TAG, "getTokoViewModelList: called");
            if(mAktivitasDompetViewModel == null){
                mAktivitasDompetViewModel = new ViewModelProvider(this)
                        .get(Aktivitas_dompet_view_model.class);
            }
            Log.i(TAG, "getTokoViewModelList: called 2");
            return mAktivitasDompetViewModel;
        }
        private Aktivitas_produk_view_model getAktivitasProdukViewModel(){
            Log.i(TAG, "getAktivitasProdukViewModel: ");
            if (mAktivitasProdukViewModel == null){
                mAktivitasProdukViewModel = new ViewModelProvider(this)
                        .get(Aktivitas_produk_view_model.class);
            }
            return mAktivitasProdukViewModel;
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
            mProdukViewModel = getProdukViewModel();
            mAktivitasDompetViewModel = getAktivitasDompetViewModel();
            mAktivitasProdukViewModel = getAktivitasProdukViewModel();

            mPictureUtils = new PictureUtils();

            mAktivitasDompetAdapter = new AktivitasDompetAdapter(new ArrayList<>());
            mAktivitasProdukAdapter = new AktivitasProdukAdapter(new ArrayList<>());

            dataDompet = new Dompet();
            dataProduk = new ArrayList<>();
            mDompetAktivitas = new ArrayList<>();
            mProdukAktivitas = new ArrayList<>();

            displayMetrics = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

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

            mRecyclerView = v.findViewById(R.id.aktivitas_dompet_recycler_view);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            mRecyclerView_1 = v.findViewById(R.id.aktivitas_produk_recycler_view);
            mRecyclerView_1.setLayoutManager(new LinearLayoutManager(getActivity()));


            mFotoDiri = v.findViewById(R.id.foto_diri);

            mSettingButton = v.findViewById(R.id.setting);
            mSettingButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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

            mAktivitasProduk = v.findViewById(R.id.aktivitas_produk);
            mAktivitasProduk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment_produk_aktivitas fragment = Fragment_produk_aktivitas.newInstance(dataToko);
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit(); // save the changes
                }
            });

            mAktivitasDompet = v.findViewById(R.id.aktivitas_dompet);
            mAktivitasDompet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallbacks.onDompetButtonClicked();
                }
            });

            mRefreshLayout = v.findViewById(R.id.swiperefresh);
            mRefreshLayout.setProgressViewOffset(false, 0, (displayMetrics.heightPixels/2) - (mRefreshLayout.getProgressCircleDiameter() / 2));
            mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    refresh();
                }
            });

            if (dataProduk.size() == 0 ){
                mRefreshLayout.setRefreshing(true);
            }

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
            mJumlahProduk.setText(dataProduk.size() + "");

//        Hitung total aset
            long uang = 0;
            for(Produk p : dataProduk){
                uang += p.getHarga() * p.getJumlah();
            }
            mJumlahUang.setText("Rp " + String.format("%,d", uang).replace(',', '.') + ",-");

//        Hitung pemasukkan dan pengeluaran
            long pemasukkan = 0;
            long pengeluaran = 0;
            for(Dompet_aktivitas d : mDompetAktivitas){
                switch (d.getKode_akt()){
                    case 1 :
                        pemasukkan += d.getJumlah();
                        break;
                    case 2 :
                        pengeluaran += d.getJumlah();
                        break;
                }
            }
            mPemasukkan.setText("Rp " + String.format("%,d", pemasukkan).replace(',', '.') + ",-");
            mPengeluaran.setText("Rp " + String.format("%,d", pengeluaran).replace(',', '.') + ",-");

//        Update aktivitas dompet dengan 5 terbaru
            List<Dompet_aktivitas> limaAktivitasDompet = new ArrayList<>();
            for(int i = 0; i < mDompetAktivitas.size(); i++) {
                if (i == 3) {
                    break;
                }
                limaAktivitasDompet.add(mDompetAktivitas.get(i));
            }
            mAktivitasDompetAdapter = new AktivitasDompetAdapter(limaAktivitasDompet);
            mRecyclerView.setAdapter(mAktivitasDompetAdapter);

//      Update aktivitas produk dengan 5 terbaru
            List<Produk_aktivitas> limaAktiviasProduk = new ArrayList<>();
            for(int i = 0; i < mProdukAktivitas.size(); i++){
                if(i==3){
                    break;
                }
                limaAktiviasProduk.add(mProdukAktivitas.get(i));
            }
            mAktivitasProdukAdapter = new AktivitasProdukAdapter(limaAktiviasProduk);
            mRecyclerView_1.setAdapter(mAktivitasProdukAdapter);
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

//        UPDATE DATA PRODUK
            mProdukViewModel.getProduksByIdToko(dataToko.getIdToko()).observe(
                    getViewLifecycleOwner(),
                    new Observer<List<Produk>>() {
                        @Override
                        public void onChanged(List<Produk> produks) {
                            dataProduk = produks;
                            Log.i(TAG, "Got Produk: " + produks.size());
                            updateUI();
                            mRefreshLayout.setRefreshing(false);
                        }
                    }
            );

//        UPDATE PEMASUKKAN
            mAktivitasDompetViewModel.getAktivitasByIdToko(dataToko.getIdToko()).observe(
                    getViewLifecycleOwner(),
                    new Observer<List<Dompet_aktivitas>>() {
                        @Override
                        public void onChanged(List<Dompet_aktivitas> aktivitas) {
                            mDompetAktivitas = aktivitas;
                            updateUI();
                        }
                    }
            );

//            UPDATE AKTIVITAS PRODUK
            mAktivitasProdukViewModel.getAktivitasByIdToko(dataToko.getIdToko()).observe(
                    getViewLifecycleOwner(),
                    new Observer<List<Produk_aktivitas>>() {
                        @Override
                        public void onChanged(List<Produk_aktivitas> produk_aktivitas) {
                            Log.i(TAG, "onChanged: berapa aktiivitas " + produk_aktivitas.size());
                            mProdukAktivitas = produk_aktivitas;
                            updateUI();
                        }
                    }
            );
        }

        private void refresh(){
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

//        UPDATE DATA PRODUK
            mProdukViewModel.getProduksByIdToko(dataToko.getIdToko()).observe(
                    getViewLifecycleOwner(),
                    new Observer<List<Produk>>() {
                        @Override
                        public void onChanged(List<Produk> produks) {
                            dataProduk = produks;
                            Log.i(TAG, "Got Produk: " + produks.size());
                            updateUI();
                            mRefreshLayout.setRefreshing(false);
                        }
                    }
            );

//        UPDATE PEMASUKKAN
            mAktivitasDompetViewModel.getAktivitasByIdToko(dataToko.getIdToko()).observe(
                    getViewLifecycleOwner(),
                    new Observer<List<Dompet_aktivitas>>() {
                        @Override
                        public void onChanged(List<Dompet_aktivitas> aktivitas) {
                            mDompetAktivitas = aktivitas;
                            updateUI();
                        }
                    }
            );

//            UPDATE AKTIVITAS PRODUK
            mAktivitasProdukViewModel.getAktivitasByIdToko(dataToko.getIdToko()).observe(
                    getViewLifecycleOwner(),
                    new Observer<List<Produk_aktivitas>>() {
                        @Override
                        public void onChanged(List<Produk_aktivitas> produk_aktivitas) {
                            Log.i(TAG, "onChanged: berapa aktiivitas " + produk_aktivitas.size());
                            mProdukAktivitas = produk_aktivitas;
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
        
        private class AktivitasDompetHolder extends RecyclerView.ViewHolder{

            private TextView mKeterangan;
            private TextView mNominal;
            private TextView mJam;
            private TextView mTanggal;
            private RelativeLayout mItemAktivitas;

//            SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
//            SimpleDateFormat formatJam  = new SimpleDateFormat("HH:mm");
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            public AktivitasDompetHolder (LayoutInflater inflater, ViewGroup parent){
                super(inflater.inflate(R.layout.fragment_item_aktivitas_dompet, parent, false));

                mKeterangan = itemView.findViewById(R.id.keterangan_aktivitas_dompet);
                mNominal = itemView.findViewById(R.id.nominal);
                mJam = itemView.findViewById(R.id.jam_aktivitas_dompet);
                mTanggal = itemView.findViewById(R.id.tanggal_aktivitas_dompet);
                mItemAktivitas = itemView.findViewById(R.id.item_aktivitas);
            }

            @SuppressLint("LongLogTag")
            public void bind(Dompet_aktivitas aktivitas){
                switch (aktivitas.getKode_akt()){
                    case 1 :
                        mKeterangan.setText(R.string.uang_masuk);
                        break;
                    case 2 :
                        mKeterangan.setText(R.string.uang_keluar);
                        break;
                    case 3 :
                        mKeterangan.setText(R.string.perubahan_kasir);
                        break;
                    case 4 :
                        mKeterangan.setText(R.string.kosong_kasir);
                        break;
                    case 5 :
                        mKeterangan.setText(R.string.penjualan);
                        break;
                }
                mNominal.setText("Rp " + String.format("%,d", aktivitas.getJumlah()).replace(',', '.') + ",-");

                mJam.setText(aktivitas.getCreadate().substring(11,16) + " WIB");
                mTanggal.setText(aktivitas.getCreadate().substring(0,10));

                mItemAktivitas.setOnClickListener(
                        new View.OnClickListener() {
                            @SuppressLint("LongLogTag")
                            @Override
                            public void onClick(View v) {
                                Detail_aktivitas_dompet fragment = Detail_aktivitas_dompet.newInstance(aktivitas);
                                FragmentManager fm = getFragmentManager();
                                fragment.show(fm,"Fragment Setting");
                            }
                        }
                );
            }

        }
        private class AktivitasDompetAdapter extends RecyclerView.Adapter<Fragment_menu_utama.AktivitasDompetHolder>{

            private List<Dompet_aktivitas> mAktivitas;

            public AktivitasDompetAdapter(List<Dompet_aktivitas> aktivitas){
                mAktivitas = aktivitas;
            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public Fragment_menu_utama.AktivitasDompetHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                return new Fragment_menu_utama.AktivitasDompetHolder(layoutInflater, parent);
            }


            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onBindViewHolder(Fragment_menu_utama.AktivitasDompetHolder holder, int position){
                Dompet_aktivitas aktivitas = mAktivitas.get(position);
                holder.bind(aktivitas);
            }

            @Override
            public int getItemCount(){
                return mAktivitas.size();
            }
        }

        private class AktivitasProdukHolder extends RecyclerView.ViewHolder{
            private TextView mKeterangan;
            private TextView mJumlah;
            private TextView mJam;
            private TextView mTanggal;
            private RelativeLayout mItemAktivitas;

            public AktivitasProdukHolder (LayoutInflater inflater, ViewGroup parent){
                super(inflater.inflate(R.layout.fragment_item_aktivitas_produk, parent, false));

                mKeterangan = itemView.findViewById(R.id.keterangan_aktivitas_produk);
                mJumlah = itemView.findViewById(R.id.jumlah_nominal);
                mJam = itemView.findViewById(R.id.jam_aktivitas_produk);
                mTanggal = itemView.findViewById(R.id.tanggal_aktivitas_produk);
                mItemAktivitas = itemView.findViewById(R.id.item_aktivitas);
            }

            @SuppressLint("LongLogTag")
            public void bind(Produk_aktivitas aktivitas) {
                switch (aktivitas.getKode_akt()) {
                    case 0:
                        mKeterangan.setText(R.string.pengambilan);
                        break;
                    case 1:
                        mKeterangan.setText(R.string.penjualan);
                        break;
                    case 2 :
                        mKeterangan.setText(R.string.tambah_stok);
                        break;
                }

                mJumlah.setText("Rp " + String.format("%,d", aktivitas.getJumlah()).replace(',', '.') + ",-");

                mJam.setText(aktivitas.getCreadate().substring(11,16) + " WIB");
                mTanggal.setText(aktivitas.getCreadate().substring(0,10));

                mItemAktivitas.setOnClickListener(
                        new View.OnClickListener() {
                            @SuppressLint("LongLogTag")
                            @Override
                            public void onClick(View v) {
                                Detail_aktivitas_produk fragment = Detail_aktivitas_produk.newInstance(aktivitas);
                                FragmentManager fm = getFragmentManager();
                                fragment.show(fm,"Fragment Detail_aktivitas_produk");
                            }
                        }
                );
            }

        }
        private class AktivitasProdukAdapter extends RecyclerView.Adapter<Fragment_menu_utama.AktivitasProdukHolder>{

            private List<Produk_aktivitas> mProduk_aktivitas;

            public AktivitasProdukAdapter(List<Produk_aktivitas> aktivitas){
                mProduk_aktivitas = aktivitas;
            }

            @NonNull
            @Override
            public Fragment_menu_utama.AktivitasProdukHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                return new Fragment_menu_utama.AktivitasProdukHolder(layoutInflater, parent);
            }

            @Override
            public void onBindViewHolder(@NonNull Fragment_menu_utama.AktivitasProdukHolder holder, int position) {
                Produk_aktivitas aktivitas = mProduk_aktivitas.get(position);
                holder.bind(aktivitas);
            }

            @Override
            public int getItemCount() {
                return mProduk_aktivitas.size();
            }
        }
}
