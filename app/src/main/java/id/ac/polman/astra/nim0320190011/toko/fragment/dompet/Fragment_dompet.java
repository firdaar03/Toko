package id.ac.polman.astra.nim0320190011.toko.fragment.dompet;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import id.ac.polman.astra.nim0320190011.toko.MyCallBack;
import id.ac.polman.astra.nim0320190011.toko.R;
import id.ac.polman.astra.nim0320190011.toko.api.model.Dompet;
import id.ac.polman.astra.nim0320190011.toko.api.model.Dompet_aktivitas;
import id.ac.polman.astra.nim0320190011.toko.api.model.Produk;
import id.ac.polman.astra.nim0320190011.toko.api.model.Toko;
import id.ac.polman.astra.nim0320190011.toko.api.viewmodel.Aktivitas_dompet_view_model;
import id.ac.polman.astra.nim0320190011.toko.api.viewmodel.Dompet_view_model;
import id.ac.polman.astra.nim0320190011.toko.fragment.Fragment_setting;
import id.ac.polman.astra.nim0320190011.toko.fragment.produk.Fragment_edit_produk;
import id.ac.polman.astra.nim0320190011.toko.fragment.produk.Fragment_produk;


public class Fragment_dompet extends Fragment{
    private static final String TAG = "Fragment_dompet";

    Toko dataToko;
    Dompet dataDompet;

    private TextView mTotalDompet;
    private TextView mPemasukkan;
    private TextView mPengeluaran;


    private Button btnAktvitas;
    private Button btnUangMasuk;
    private Button btnUangKeluar;
    private RelativeLayout mDalamKasir;
    private ImageView mBack;

    private List<Dompet_aktivitas> mDompetAktivitas;
    private Dompet_view_model mDompetViewModel;

    private Aktivitas_dompet_view_model mAktivitasDompetViewModel;

    public Aktivitas_dompet_view_model getAktivitasDompetViewModel(){
        Log.i(TAG, "getTokoViewModelList: called");
        if(mAktivitasDompetViewModel == null){
            mAktivitasDompetViewModel = new ViewModelProvider(this)
                    .get(Aktivitas_dompet_view_model.class);
        }
        Log.i(TAG, "getTokoViewModelList: called 2");
        return mAktivitasDompetViewModel;
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFragment(Fragment_dompet_aktivitas.newInstance(dataToko));
        mAktivitasDompetViewModel = getAktivitasDompetViewModel();

        mDompetViewModel = getDompetViewModel();
        mDompetViewModel.loadDompet(dataToko.getIdToko() + "");
        dataDompet = new Dompet();
        mDompetAktivitas = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: Called ");
        View v = inflater.inflate(R.layout.fragment_dompet, container, false);

        mPemasukkan = v.findViewById(R.id.pemasukkan);
        mPengeluaran = v.findViewById(R.id.pengeluaran);

        mTotalDompet = v.findViewById(R.id.total_dompet);
        mDalamKasir = v.findViewById(R.id.dalam_kasir);
        mDalamKasir.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Fragment_dompet_kasir fragment = Fragment_dompet_kasir.newInstance(dataDompet);
                        FragmentManager fm = getFragmentManager();
                        fragment.show(fm,"Fragment Setting");
                    }
                }
        );

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
                    }
                }
        );
    }
    public void updateUI(){
        Log.i(TAG, "NGUPDATE UI NIH ");
        mTotalDompet.setText( "Rp " + String.format("%,d", dataDompet.getUang()).replace(',', '.') + ",-");

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
    }
    private void loadFragment(Fragment fragment) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.menus, fragment);
            fragmentTransaction.commit(); // save the changes
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: ");
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
}

