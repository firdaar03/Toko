package id.ac.polman.astra.nim0320190011.toko.fragment.produk;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import id.ac.polman.astra.nim0320190011.toko.R;
import id.ac.polman.astra.nim0320190011.toko.api.model.Produk;
import id.ac.polman.astra.nim0320190011.toko.api.model.Toko;
import id.ac.polman.astra.nim0320190011.toko.api.viewmodel.Produk_view_model;
import id.ac.polman.astra.nim0320190011.toko.api.viewmodel.Toko_view_model;
import id.ac.polman.astra.nim0320190011.toko.fragment.Fragment_menu_utama;
import id.ac.polman.astra.nim0320190011.toko.fragment.dompet.Fragment_dompet;

public class Fragment_produk extends Fragment {
    private static final String TAG = "Fragment_produk";

    Toko_view_model mTokoViewModel;
    Toko dataToko;

    private String mProdukId;

    private Button mAddProdukButton;
    private Button mPutProdukButton;

    public static Fragment_produk newInstance(Toko in) {
        return new Fragment_produk(in);
    }

//    public Fragment_produk(){
//
//    }

    private Fragment_produk(Toko t){
        dataToko = t;
    }

    public Toko_view_model getTokoViewModel(){
        Log.i(TAG, "getTokoViewModelList: called");
        if(mTokoViewModel == null){
            mTokoViewModel = new ViewModelProvider(this)
                    .get(Toko_view_model.class);
        }
        Log.i(TAG, "getTokoViewModelList : called idtoko: " + dataToko.getIdToko());
//        Log.i(TAG, "getTokoViewModelList: called 2");

        return mTokoViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTokoViewModel = getTokoViewModel();

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
                Fragment fragment = Fragment_tambah_produk.newInstance(String.valueOf(dataToko.getIdToko()));
                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit(); // save the changes
            }
        });

        mPutProdukButton = (Button) v.findViewById(R.id.button_put);
        mPutProdukButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = Fragment_put_produk.newInstance();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit(); // save the changes
            }
        });
        return v;

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
