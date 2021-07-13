package id.ac.polman.astra.nim0320190011.toko.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import id.ac.polman.astra.nim0320190011.toko.R;

public class Fragment_menu_utama extends Fragment {
    private static final String TAG = "Fragment_menut_utama";

    public static Fragment_menu_utama newInstance() {
        return new Fragment_menu_utama();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: Called ");
        View v = inflater.inflate(R.layout.fragment_menu_utama, container, false);

        return v;
    }
}
