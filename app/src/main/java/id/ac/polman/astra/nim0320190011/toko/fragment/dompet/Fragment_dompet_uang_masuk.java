package id.ac.polman.astra.nim0320190011.toko.fragment.dompet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import id.ac.polman.astra.nim0320190011.toko.R;
import id.ac.polman.astra.nim0320190011.toko.api.model.Dompet;
import id.ac.polman.astra.nim0320190011.toko.api.model.Toko;
import id.ac.polman.astra.nim0320190011.toko.api.viewmodel.Dompet_view_model;
import id.ac.polman.astra.nim0320190011.toko.api.viewmodel.Toko_view_model;
import id.ac.polman.astra.nim0320190011.toko.fragment.Fragment_login;
import id.ac.polman.astra.nim0320190011.toko.fragment.Fragment_menu_utama;
import id.ac.polman.astra.nim0320190011.toko.fragment.produk.Fragment_produk;

public class Fragment_dompet_uang_masuk extends Fragment {
    private static final String TAG = "Fragment_dompet_uang_masuk";

    Dompet_view_model mDompetViewModel;
    Toko dataToko;

    EditText mNominal;
    EditText mKeterangan;
    Button mButtonSimpan;

    private Dompet_view_model getDompetViewModel() {
        if (mDompetViewModel == null) {
            mDompetViewModel = new ViewModelProvider(this)
                    .get(Dompet_view_model.class);
        }
        return mDompetViewModel;
    }

    public static Fragment_dompet_uang_masuk newInstance(Toko in) {
        return new Fragment_dompet_uang_masuk(in);
    }

    private Fragment_dompet_uang_masuk(Toko t) {
        dataToko = t;
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        mDompetViewModel = getDompetViewModel();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dompet_uang_masuk, container, false);

        mNominal = v.findViewById(R.id.nominal);
        mKeterangan = v.findViewById(R.id.keterangan_uang_masuk);
        mButtonSimpan = v.findViewById(R.id.button_simpan);
        mButtonSimpan.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View v) {
                if (mNominal.getText().toString().equals("")) {
                    mNominal.setError("Mohon isi field nominal dengan benar");
                } else {
                    Dompet dompet = new Dompet();
                    dompet.setCreaby(dataToko.getEmail());
                    dompet.setIdToko(dataToko.getIdToko());
                    dompet.setUang(Integer.valueOf(mNominal.getText().toString()));
                    dompet.setModiby(mKeterangan.getText().toString());
                    mDompetViewModel.uang_masuk(dompet);

                    getFragmentManager().popBackStack();
                    Fragment fragment = Fragment_dompet.newInstance(dataToko);
                    getFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, fragment)
                            .addToBackStack(null)
                            .commit();

                    mNominal.setText("");
                    mKeterangan.setText("");
                }
            }
        });
        return v;
    }
}

