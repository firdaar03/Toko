package id.ac.polman.astra.nim0320190011.toko.fragment.produk;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import id.ac.polman.astra.nim0320190011.toko.R;

public class Fragment_put_produk extends Fragment {
    private static final String TAG = "Fragment_put_produk";

    public static Fragment_put_produk newInstance() {
        return new Fragment_put_produk();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: Called ");
        View v = inflater.inflate(R.layout.fragment_put_produk, container, false);
        return v;
    }
}
