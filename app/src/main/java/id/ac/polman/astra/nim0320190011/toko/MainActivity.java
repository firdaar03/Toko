package id.ac.polman.astra.nim0320190011.toko;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import id.ac.polman.astra.nim0320190011.toko.api.model.Toko;
import id.ac.polman.astra.nim0320190011.toko.fragment.Fragment_daftar_toko;
import id.ac.polman.astra.nim0320190011.toko.fragment.Fragment_login;
import id.ac.polman.astra.nim0320190011.toko.fragment.Fragment_menu_utama;
import id.ac.polman.astra.nim0320190011.toko.fragment.dompet.Fragment_dompet;
import id.ac.polman.astra.nim0320190011.toko.fragment.produk.Fragment_produk;
import id.ac.polman.astra.nim0320190011.toko.fragment.produk.Fragment_put_produk;
import id.ac.polman.astra.nim0320190011.toko.fragment.produk.Fragment_tambah_produk;

public class MainActivity extends AppCompatActivity
    implements Fragment_login.Callbacks, Fragment_menu_utama.Callbacks, Fragment_produk.Callbacks{
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if(fragment == null){
            fragment = Fragment_login.newInstance();
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

    @Override
    public void onLoginButtonClicked(Toko user) {
        Fragment fragment = Fragment_menu_utama.newInstance(user);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onProdukButtonClicked() {
        Fragment fragment = Fragment_produk.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onDompetButtonClicked() {
        Fragment fragment = Fragment_dompet.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onPutProdukButtonClicked() {
        Fragment fragment = Fragment_put_produk.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onAddProdukButtonClicked() {
        Fragment fragment = Fragment_tambah_produk.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}