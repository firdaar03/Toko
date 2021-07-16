package id.ac.polman.astra.nim0320190011.toko.fragment.produk;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import id.ac.polman.astra.nim0320190011.toko.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_tambah_produk#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_tambah_produk extends Fragment {
    private static final String TAG = "Fragment_tambah_produk";

    public static Fragment_tambah_produk newInstance() {
        return new Fragment_tambah_produk();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: Called ");
        View v = inflater.inflate(R.layout.fragment_tambah_produk, container, false);
        return v;
    }
}