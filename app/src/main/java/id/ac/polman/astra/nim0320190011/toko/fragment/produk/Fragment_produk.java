package id.ac.polman.astra.nim0320190011.toko.fragment.produk;

import android.content.Context;
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
import id.ac.polman.astra.nim0320190011.toko.fragment.Fragment_menu_utama;

public class Fragment_produk extends Fragment {
    private static final String TAG = "Fragment_produk";

    private Button mAddProdukButton;
    private Button mPutProdukButton;

    public static Fragment_produk newInstance() {
        return new Fragment_produk();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: Called ");
        View v = inflater.inflate(R.layout.fragment_produk, container, false);

        mAddProdukButton = (Button) v.findViewById(R.id.button_add);
        mAddProdukButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallbacks.onAddProdukButtonClicked();
            }
        });

        mPutProdukButton = (Button) v.findViewById(R.id.button_put);
        mPutProdukButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallbacks.onPutProdukButtonClicked();
            }
        });
        return v;

    }

    public interface Callbacks{
        public void onPutProdukButtonClicked();
        public void onAddProdukButtonClicked();
    }

    private Fragment_produk.Callbacks mCallbacks = null;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mCallbacks = (Fragment_produk.Callbacks) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }
}
