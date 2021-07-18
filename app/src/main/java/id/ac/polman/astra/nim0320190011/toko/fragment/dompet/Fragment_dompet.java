package id.ac.polman.astra.nim0320190011.toko.fragment.dompet;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import id.ac.polman.astra.nim0320190011.toko.R;


public class Fragment_dompet extends Fragment {
    private static final String TAG = "Fragment_dompet";

    private Button btnAktvitas;
    private Button btnUangMasuk;
    private Button btnUangKeluar;

    public static Fragment_dompet newInstance() {
        return new Fragment_dompet();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: Called ");
        View v = inflater.inflate(R.layout.fragment_dompet, container, false);

        btnAktvitas = (Button) v.findViewById(R.id.button_aktifitas);
        btnAktvitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new Fragment_dompet_aktivitas());
            }
        });

        btnUangMasuk= (Button) v.findViewById(R.id.button_uang_masuk);
        btnUangMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new Fragment_dompet_uang_masuk());
            }
        });

        btnUangKeluar= (Button) v.findViewById(R.id.button_uang_keluar);
        btnUangKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new Fragment_dompet_uang_keluar());
            }
        });

        return v;
    }

    private void loadFragment(Fragment fragment) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.menus, fragment);
            fragmentTransaction.commit(); // save the changes

    }
}
