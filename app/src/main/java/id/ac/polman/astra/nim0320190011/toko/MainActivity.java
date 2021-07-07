package id.ac.polman.astra.nim0320190011.toko;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import id.ac.polman.astra.nim0320190011.toko.fragment.Fragment_daftar_toko;
import id.ac.polman.astra.nim0320190011.toko.fragment.Fragment_login;
import id.ac.polman.astra.nim0320190011.toko.fragment.Fragment_tambah_produk;

public class MainActivity extends AppCompatActivity
    implements Fragment_login.Callbacks{
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        tes tes
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if(fragment == null){
//            fragment = Fragment_daftar_toko.newInstance();
//<<<<<<< HEAD
//            fragment = Fragment_login.newInstance();
//=======
            fragment = Fragment_tambah_produk.newInstance();
//>>>>>>> 34ed59b10d770d70330ded9924afa391d0d62f8c
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    @Override
    public void onCreateAkunClicked() {
        Fragment fragment = Fragment_daftar_toko.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}