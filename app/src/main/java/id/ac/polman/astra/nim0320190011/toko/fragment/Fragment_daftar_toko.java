package id.ac.polman.astra.nim0320190011.toko.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import id.ac.polman.astra.nim0320190011.toko.R;

public class Fragment_daftar_toko extends Fragment {
    private static final String TAG = "Fragment_daftar_toko";

    public static Fragment_daftar_toko newInstance(){
        return new Fragment_daftar_toko();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pendaftaran_toko, container, false);

        return view;
    }
}
