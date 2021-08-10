package id.ac.polman.astra.nim0320190011.toko.fragment.dompet;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import id.ac.polman.astra.nim0320190011.toko.R;
import id.ac.polman.astra.nim0320190011.toko.api.model.Dompet;
import id.ac.polman.astra.nim0320190011.toko.api.model.Toko;
import id.ac.polman.astra.nim0320190011.toko.api.viewmodel.Dompet_view_model;

public class Fragment_dompet_uang_keluar extends Fragment {

    private static final String TAG = "Fragment_dompet_uang_keluar";

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

    public static Fragment_dompet_uang_keluar newInstance(Toko in) {
        return new Fragment_dompet_uang_keluar(in);
    }

    private Fragment_dompet_uang_keluar(Toko t) {
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
        View v = inflater.inflate(R.layout.fragment_dompet_uang_keluar, container, false);

        mNominal = v.findViewById(R.id.nominal);
        mKeterangan = v.findViewById(R.id.keterangan_uang_keluar);
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
                    mDompetViewModel.uang_keluar(dompet);

                    callbacks = (Callbacks) getTargetFragment();
                    callbacks.onSimpanKeluar(Integer.valueOf(mNominal.getText().toString()));

                    mNominal.setText("");
                    mKeterangan.setText("");
                }
            }
        });
        return v;
    }

    private Callbacks callbacks;

    public interface Callbacks{
        public void onSimpanKeluar(int keluar);
    }
}
