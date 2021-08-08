package id.ac.polman.astra.nim0320190011.toko.fragment.produk;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import id.ac.polman.astra.nim0320190011.toko.R;
import id.ac.polman.astra.nim0320190011.toko.api.model.Toko;

public class Fragment_product_option extends DialogFragment {
    private static final String TAG = "Fragment_product_option";

    Toko dataToko;

    TextView mButton_add_barang;
    TextView mButton_penyetokan;

    public static Fragment_product_option newInstance(Toko t){
        return new Fragment_product_option(t);
    }

    private Fragment_product_option(Toko t){
        dataToko = t;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_product_option, container, false);

        mButton_add_barang = v.findViewById(R.id.button_produk_baru);
        mButton_add_barang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
                Fragment fragment = Fragment_tambah_produk.newInstance(dataToko);
                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit(); // save the changes
            }
        });

        mButton_penyetokan = v.findViewById(R.id.button_penyetokan);
        mButton_penyetokan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Apakek", Toast.LENGTH_SHORT)
                        .show();
            }
        });
        return v;
    }
}
