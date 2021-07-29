package id.ac.polman.astra.nim0320190011.toko.fragment.dompet;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import id.ac.polman.astra.nim0320190011.toko.R;
import id.ac.polman.astra.nim0320190011.toko.api.model.Dompet;
import id.ac.polman.astra.nim0320190011.toko.api.viewmodel.Dompet_view_model;

public class Fragment_dompet_kasir extends DialogFragment{
    private static final String TAG = "Fragment_setting";

    private Dompet mDompet;

    private TextView mTotalDompet;
    private EditText mNominal;
    private Button mKosongkan;
    private Button mPerbarui;

    Dompet_view_model mDompetViewModel;

    public static Fragment_dompet_kasir newInstance(Dompet d){
        return new Fragment_dompet_kasir(d);
    }

    private Fragment_dompet_kasir(Dompet d){
        mDompet = d;
    }

    private Dompet_view_model getDompetViewModel(){
        if(mDompetViewModel == null){
            mDompetViewModel = new ViewModelProvider(this)
                    .get(Dompet_view_model.class);
            mDompetViewModel.loadDompet(mDompet.getIdToko() + "");
        }
        Log.i(TAG, "getTokoViewModelList: called 2");
        return mDompetViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDompetViewModel = getDompetViewModel();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dompet_kasir, container, false);

        mTotalDompet = v.findViewById(R.id.total_dompet);
        mNominal = v.findViewById(R.id.nominal);
        
        mKosongkan = v.findViewById(R.id.button_kosongkan);
        mKosongkan.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new AlertDialog
                                .Builder(getContext())
                                .setTitle(getString(R.string.kosong_kasir))
                                .setMessage(getString(R.string.yakin_hapus))
                                .setPositiveButton("Ya", (dialogInterface, i) -> {
                                    getDialog().dismiss();

                                    Dompet dompet = new Dompet();
                                    dompet.setIdToko(mDompet.getIdToko());
                                    dompet.setCreaby(mDompet.getCreaby());
                                    dompet.setModiby("Uang pada kasir dikosongkan");
                                    dompet.setUang(0);

                                    mDompetViewModel.kosongkan(dompet);
                                    updateUI();
                                })
                                .setNegativeButton("Tidak", null)
                                .show();
                    }
                }
        );
        
        mPerbarui = v.findViewById(R.id.button_perbarui);
        mPerbarui.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!mNominal.getText().toString().equals("")){
                            int nominal = Integer.parseInt(mNominal.getText() + "");
                            new AlertDialog
                                    .Builder(getContext())
                                    .setTitle(getString(R.string.perbarui_kasir))
                                    .setMessage(getString(R.string.yakin_perbarui, "Rp " + String.format("%,d", nominal).replace(',', '.') + ",-"))
                                    .setPositiveButton("Ya", (dialogInterface, i) -> {
                                        getDialog().dismiss();


                                        mDompet.setUang(nominal);

                                        Dompet dompet = new Dompet();
                                        dompet.setIdToko(mDompet.getIdToko());
                                        dompet.setCreaby(mDompet.getCreaby());
                                        dompet.setModiby("Perubahan jumlah pada kasir");
                                        dompet.setUang(nominal);

                                        mDompetViewModel.perbarui(dompet);
                                        updateUI();
                                    })
                                    .setNegativeButton("Tidak", null)
                                    .show();
                        }else{
                            mNominal.setError("Mohon isi nominal terlebih dahulu");
                        }
                    }
                }
        );

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateUI();
        
    }

    private void updateUI(){
        mTotalDompet.setText("Rp " + String.format("%,d", mDompet.getUang()).replace(',', '.') + ",-");
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        getFragmentManager().popBackStack();
    }
}
