package id.ac.polman.astra.nim0320190011.toko.fragment.dompet;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import id.ac.polman.astra.nim0320190011.toko.R;
import id.ac.polman.astra.nim0320190011.toko.api.model.Dompet;
import id.ac.polman.astra.nim0320190011.toko.api.model.Dompet_aktivitas;
import id.ac.polman.astra.nim0320190011.toko.api.model.Produk;
import id.ac.polman.astra.nim0320190011.toko.api.model.Produk_aktivitas;
import id.ac.polman.astra.nim0320190011.toko.api.model.Toko;
import id.ac.polman.astra.nim0320190011.toko.api.viewmodel.Aktivitas_dompet_view_model;
import id.ac.polman.astra.nim0320190011.toko.api.viewmodel.Aktivitas_produk_view_model;
import id.ac.polman.astra.nim0320190011.toko.api.viewmodel.Dompet_view_model;
import id.ac.polman.astra.nim0320190011.toko.api.viewmodel.Produk_view_model;
import id.ac.polman.astra.nim0320190011.toko.fragment.user.Fragment_produk_user;


public class Fragment_dompet extends Fragment
    implements Fragment_dompet_uang_masuk.Callbacks, Fragment_dompet_uang_keluar.Callbacks{
    private static final String TAG = "Fragment_dompet";

    Toko dataToko;
    Dompet dataDompet;


    private TextView mTotalDompet;
    private TextView mPemasukkan;
    private TextView mPengeluaran;
    private TextView mPembukuan;
    private TextView mHeader;

    private androidx.swiperefreshlayout.widget.SwipeRefreshLayout mRefreshLayout;
    DisplayMetrics displayMetrics;

    private boolean pembukuan;

    private Button btnAktvitas;
    private Button btnUangMasuk;
    private Button btnUangKeluar;
    private RelativeLayout mDalamKasir;
    private ImageView mBack;

    private List<Dompet_aktivitas> mDompetAktivitas;
    private List<Produk> mProdukList;
    private List<Produk_aktivitas> mProdukAktivitas;


    private Dompet_view_model mDompetViewModel;
    private Produk_view_model mProdukViewModel;

    private Aktivitas_dompet_view_model mAktivitasDompetViewModel;
    private Aktivitas_produk_view_model mAktivitasProdukViewModel;

    long pemasukkan;
    long pengeluaran;

    public Aktivitas_dompet_view_model getAktivitasDompetViewModel(){
        Log.i(TAG, "getTokoViewModelList: called");
        if(mAktivitasDompetViewModel == null){
            mAktivitasDompetViewModel = new ViewModelProvider(this)
                    .get(Aktivitas_dompet_view_model.class);
        }
        Log.i(TAG, "getTokoViewModelList: called 2");
        return mAktivitasDompetViewModel;
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

    public static Fragment_dompet newInstance(Toko t) {
        return new Fragment_dompet(t);
    }

    private Fragment_dompet(Toko t){
        dataToko = t;
    }

    public Dompet_view_model getDompetViewModel(){
        Log.i(TAG, "getTokoViewModelList: called");
        if(mDompetViewModel == null){
            mDompetViewModel = new ViewModelProvider(this)
                    .get(Dompet_view_model.class);
            mDompetViewModel.loadDompet(dataToko.getIdToko() + "");
        }
        Log.i(TAG, "getTokoViewModelList: called 2");
        return mDompetViewModel;
    }

    private Aktivitas_produk_view_model getAktivitasProdukViewModel(){
        Log.i(TAG, "getAktivitasProdukViewModel: ");
        if (mAktivitasProdukViewModel == null){
            mAktivitasProdukViewModel = new ViewModelProvider(this)
                    .get(Aktivitas_produk_view_model.class);
        }
        return mAktivitasProdukViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFragment(Fragment_dompet_aktivitas.newInstance(dataToko));
        mAktivitasDompetViewModel = getAktivitasDompetViewModel();
        mAktivitasProdukViewModel = getAktivitasProdukViewModel();

        mProdukList = null;
        mProdukAktivitas = null;

        mProdukViewModel = getProdukViewModel();

        pembukuan = false;

        mDompetViewModel = getDompetViewModel();
        mDompetViewModel.loadDompet(dataToko.getIdToko() + "");
        dataDompet = new Dompet();
        mDompetAktivitas = new ArrayList<>();

        pemasukkan = 0;
        pengeluaran = 0;

        displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: Called ");
        View v = inflater.inflate(R.layout.fragment_dompet, container, false);

        mHeader = v.findViewById(R.id.text_header);

        mPemasukkan = v.findViewById(R.id.pemasukkan);
        mPengeluaran = v.findViewById(R.id.pengeluaran);

        mTotalDompet = v.findViewById(R.id.total_dompet);
        mDalamKasir = v.findViewById(R.id.dalam_kasir);
        mDalamKasir.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Fragment_dompet_kasir fragment = Fragment_dompet_kasir.newInstance(dataDompet);
                        FragmentManager fm = getFragmentManager();
                        fragment.show(fm,"Fragment Setting");
                    }
                });

        mPembukuan = v.findViewById(R.id.pembukuan);
        mPembukuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog
                        .Builder(getContext())
                        .setTitle(getString(R.string.pembukuan_tahunan))
                        .setMessage(getString(R.string.pembukuan_message))
                        .setPositiveButton(getText(R.string.ya_simpan), (dialogInterface, i) -> {

                            new AlertDialog
                                    .Builder(getContext())
                                    .setTitle(getString(R.string.pembukuan_tahunan_1))
                                    .setMessage(getString(R.string.pembukuan_message_2))
                                    .setPositiveButton(getText(R.string.ya), (a, c) -> {
                                        startPembukuan();
                                    })
                                    .setNegativeButton(getText(R.string.tidak), null)
                                    .show();
                        })
                        .setNegativeButton(getText(R.string.tidak), null)
                        .show();
            }
        });

        mBack = v.findViewById(R.id.back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        btnAktvitas = (Button) v.findViewById(R.id.button_aktifitas);
        btnAktvitas.setActivated(true);
        btnAktvitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnUangMasuk.setActivated(false);
                btnUangKeluar.setActivated(false);
                btnAktvitas.setActivated(true);
                loadFragment(Fragment_dompet_aktivitas.newInstance(dataToko));
            }
        });

        btnUangMasuk= (Button) v.findViewById(R.id.button_uang_masuk);
        btnUangMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnUangMasuk.setActivated(true);
                btnUangKeluar.setActivated(false);
                btnAktvitas.setActivated(false);
                loadFragment(Fragment_dompet_uang_masuk.newInstance(dataToko));
            }
        });

        btnUangKeluar= (Button) v.findViewById(R.id.button_uang_keluar);
        btnUangKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnUangMasuk.setActivated(false);
                btnUangKeluar.setActivated(true);
                btnAktvitas.setActivated(false);
                loadFragment(Fragment_dompet_uang_keluar.newInstance(dataToko));
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

        if (mDompetAktivitas.size() == 0 ){
            mRefreshLayout.setRefreshing(true);
        }

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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

        refresh();
    }

    private void refresh(){
        mDompetViewModel.loadDompet(dataToko.getIdToko() + "").observe(
                getViewLifecycleOwner(),
                new Observer<Dompet>() {
                    @Override
                    public void onChanged(Dompet dompet) {
                        Log.i(TAG, "onChanged: 'refresggg");
                        dataDompet = dompet;
                        updateUI();
                        mRefreshLayout.setRefreshing(false);
                    }
                }
        );
    }

    public void updateUI(){
        Log.i(TAG, "NGUPDATE UI NIH ");
        mTotalDompet.setText( "Rp " + String.format("%,d", dataDompet.getUang()).replace(',', '.') + ",-");

        //        Hitung pemasukkan dan pengeluaran
        if(pemasukkan == 0 && pengeluaran == 0){
            for(Dompet_aktivitas d : mDompetAktivitas){
                switch (d.getKode_akt()){
                    case 1 :
                        pemasukkan += d.getJumlah();
                        break;
                    case 2 :
                        pengeluaran += d.getJumlah();
                        break;
                    case 5 :
                        pemasukkan += d.getJumlah();
                        break;
                }
            }
        }
        mPemasukkan.setText("Rp " + String.format("%,d", pemasukkan).replace(',', '.') + ",-");
        mPengeluaran.setText("Rp " + String.format("%,d", pengeluaran).replace(',', '.') + ",-");

        mHeader.setText(dataToko.getNama_pemilik().toUpperCase());
    }

    private void loadFragment(Fragment fragment) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragment.setTargetFragment(Fragment_dompet.this, 1);
            fragmentTransaction.replace(R.id.menus, fragment);
            fragmentTransaction.commit(); // save the changes
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
        if(pembukuan){
            getFragmentManager().popBackStack();
            pembukuan = false;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: ");
        if(pembukuan){
            mDompetViewModel.pembukuan(dataToko.getIdToko());
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: ");
        try{
            refresh();
        }catch (Exception e){
            
        }
    }

    public void startPembukuan(){
        //            UPDATE AKTIVITAS PRODUK
//        mAktivitasProdukViewModel.getAktivitasByIdToko(dataToko.getIdToko()).observe(
//                getViewLifecycleOwner(),
//                new Observer<List<Produk_aktivitas>>() {
//                    @Override
//                    public void onChanged(List<Produk_aktivitas> produk_aktivitas) {
//                        mProdukAktivitas = produk_aktivitas;
//                        getPembukuan();
//                    }
//                }
//        );
        mProdukViewModel.getProduksByIdToko(dataToko.getIdToko()).observe(
                getViewLifecycleOwner(),
                new Observer<List<Produk>>() {
                    @Override
                    public void onChanged(List<Produk> produks) {
                        mProdukList = produks;
                        getPembukuan();
                    }
                }
        );
    }

    public void getPembukuan(){
        String tanggal_pembukuan = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        String pemasukkan_text = mPemasukkan.getText().toString();
        String pengeluaran_text = mPengeluaran.getText().toString();
        String dalam_kasir_text = mTotalDompet.getText().toString();

//        if(mProdukList == null || mProdukAktivitas == null){
//            return;
//        }
//      Menghitung barang paling sering dibeli
//        String jumlah_transaksi_text = mProdukAktivitas.size() + "";

//      Menghitung total uang dalam asset
        long uang = 0;
        for(Produk p : mProdukList){
            uang += p.getHarga() * p.getJumlah();
        }
        String dalam_asset_text = "Rp " + String.format("%,d", uang).replace(',', '.') + ",-";
        String total_uang_text = "Rp " + String.format("%,d", uang + dataDompet.getUang()).replace(',', '.') + ",-";

        String message =
                "```-- PEMBUKUAN TAHUN " + new SimpleDateFormat("yyyy").format(new Date()) + " --```\n\n"
                + "Toko\t\t\t: *" + dataToko.getNama_pemilik().toUpperCase() + "*\n"
                + "Tanggal\t: " + tanggal_pembukuan + "\n\n"
//                + "Total transaksi\t\t: " + jumlah_transaksi_text + "\n\n"
                + "Pemasukkan\t: " + pemasukkan_text  + "\n"
                + "Pengeluaran\t\t: " + pengeluaran_text  + "\n\n"
                + "Total dalam kasir\t: " + dalam_kasir_text  + "\n"
                + "Total dalam asset\t: " + dalam_asset_text + "\n\n"
                + "Total uang keseluruhan\t: " + total_uang_text;

        Intent n = new Intent(Intent.ACTION_SEND);
        n.setType("text/plain");
        n.putExtra(Intent.EXTRA_TEXT, message);
        n.putExtra(Intent.EXTRA_SUBJECT,
                getString(R.string.pembukuan_tahunan));
        n = Intent.createChooser(n, getString(R.string.pilih_simpan_text));
        startActivity(n);
        pembukuan = true;
    }

    @Override
    public void onSimpanMasuk(int masuk) {
        dataDompet.setUang(dataDompet.getUang() + masuk);
        pemasukkan += masuk;
        updateUI();
    }

    @Override
    public void onSimpanKeluar(int keluar) {
        dataDompet.setUang(dataDompet.getUang() - keluar);
        pengeluaran += keluar;
        updateUI();
    }

}

