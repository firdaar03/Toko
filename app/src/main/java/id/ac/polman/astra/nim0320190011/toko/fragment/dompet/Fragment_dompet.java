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
import id.ac.polman.astra.nim0320190011.toko.api.model.Produk;
import id.ac.polman.astra.nim0320190011.toko.api.model.Toko;
import id.ac.polman.astra.nim0320190011.toko.api.viewmodel.Dompet_view_model;
import id.ac.polman.astra.nim0320190011.toko.fragment.Fragment_setting;
import id.ac.polman.astra.nim0320190011.toko.fragment.produk.Fragment_edit_produk;
import id.ac.polman.astra.nim0320190011.toko.fragment.produk.Fragment_produk;


public class Fragment_dompet extends Fragment{
    private static final String TAG = "Fragment_dompet";

    Toko dataToko;
    Dompet dataDompet;

    private TextView mTotalDompet;

    private Button btnAktvitas;
    private Button btnUangMasuk;
    private Button btnUangKeluar;
    private RelativeLayout mDalamKasir;

    private Dompet_view_model mDompetViewModel;

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
        mDompetViewModel = getDompetViewModel();
        mDompetViewModel.loadDompet(dataToko.getIdToko() + "");
        dataDompet = new Dompet();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: Called ");
        View v = inflater.inflate(R.layout.fragment_dompet, container, false);

        mTotalDompet = v.findViewById(R.id.total_dompet);
        mDalamKasir = v.findViewById(R.id.dalam_kasir);
        mDalamKasir.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Fragment_setting fragment = Fragment_setting.newInstance(dataToko);
                        FragmentManager fm = getFragmentManager();
                        fragment.show(fm,"Fragment Setting");
                    }
                }
        );

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
        refresh();
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void updateUI(){
        Log.i(TAG, "NGUPDATE UI NIH ");
        mTotalDompet.setText( "Rp " + String.format("%,d", dataDompet.getUang()).replace(',', '.') + ",-");
    }
    private void loadFragment(Fragment fragment) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.menus, fragment);
            fragmentTransaction.commit(); // save the changes
    }

    public void refresh(){
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


    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        refresh();
    }
}

