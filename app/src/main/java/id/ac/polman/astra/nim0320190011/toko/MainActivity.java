package id.ac.polman.astra.nim0320190011.toko;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.List;

import id.ac.polman.astra.nim0320190011.toko.api.model.Dompet;
import id.ac.polman.astra.nim0320190011.toko.api.model.Produk;
import id.ac.polman.astra.nim0320190011.toko.api.model.Toko;
import id.ac.polman.astra.nim0320190011.toko.api.viewmodel.Login_view_model;
import id.ac.polman.astra.nim0320190011.toko.api.viewmodel.Toko_view_model;
import id.ac.polman.astra.nim0320190011.toko.fragment.Fragment_daftar_toko;
import id.ac.polman.astra.nim0320190011.toko.fragment.Fragment_login;
import id.ac.polman.astra.nim0320190011.toko.fragment.Fragment_menu_utama;
import id.ac.polman.astra.nim0320190011.toko.fragment.dompet.Fragment_dompet;
import id.ac.polman.astra.nim0320190011.toko.fragment.produk.Fragment_produk;
import id.ac.polman.astra.nim0320190011.toko.fragment.produk.Fragment_put_produk;
import id.ac.polman.astra.nim0320190011.toko.fragment.produk.Fragment_tambah_produk;
import id.ac.polman.astra.nim0320190011.toko.fragment.produk.Fragment_tambah_stok;
import id.ac.polman.astra.nim0320190011.toko.fragment.user.Fragment_produk_user;

public class MainActivity extends AppCompatActivity
    implements Fragment_login.Callbacks, Fragment_menu_utama.Callbacks,
    Fragment_tambah_produk.Callbacks, Fragment_tambah_stok.Callbacks,
    Fragment_put_produk.Callbacks{

    private Fragment_produk fragment_produk;

    private static final String TAG = "MainActivity";
    private Toko toko_user;
    Login_view_model mLoginViewModel;

    SharedPreferences mSharedPreferences;
    Activity context;
    private final static String NAME_APP = "Toko";
    private final static String USERNAME = "username";
    private final static String PASSWORD = "password";


    @Override
    public void onBackPressed(){
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            Log.i("MainActivity", "popping backstack");
            fm.popBackStack();
        } else {
            new AlertDialog
                    .Builder(this)
                    .setTitle(getText(R.string.keluar))
                    .setMessage(getText(R.string.yakin_keluar))
                    .setPositiveButton(getText(R.string.ya), (dialogInterface, i) -> {
                        context= this;
                        super.onBackPressed();
                    })
                    .setNegativeButton(getText(R.string.tidak), null)
                    .show();
        }
    }

    public Login_view_model getLoginViewModel(){
        Log.i(TAG, "getTokoViewModelList: called");
        if(mLoginViewModel == null){
            mLoginViewModel = new ViewModelProvider(this)
                    .get(Login_view_model.class);
        }

        return mLoginViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mLoginViewModel = getLoginViewModel();

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String spid= mSharedPreferences.getString("idtoko", null);

        if (spid != null) {
            mLoginViewModel.loadToko(spid).observe(
                    this, new Observer<Toko>() {
                        @Override
                        public void onChanged(Toko toko) {
                            Log.i(TAG, "checkPreferences: Ini loh masuk size data : " + toko.getNama_pemilik());
                            toko_user = toko;
                            onSharedPreferences();
                        }
                    }
            );

        } else {
            if(fragment == null){
                fragment = Fragment_login.newInstance();
                fm.beginTransaction()
                        .add(R.id.fragment_container, fragment)
                        .commit();
            }
        }
    }

    public void onSharedPreferences(){
        Fragment fragment = Fragment_menu_utama.newInstance(toko_user);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
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
        toko_user = user;
        String id = String.valueOf(toko_user.getIdToko());
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString("idtoko", id);
        editor.apply();

        Fragment fragment = Fragment_menu_utama.newInstance(toko_user);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();

    }

    @Override
    public void onProdukButtonClicked() {
        fragment_produk = Fragment_produk.newInstance(toko_user);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment_produk)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onDompetButtonClicked() {
        Fragment fragment = Fragment_dompet.newInstance(toko_user);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onProdukBaru(Produk pr) {
        fragment_produk.produk_tambah(pr);
    }

    @Override
    public void onPenyetokan(List<Produk> pr) {
        fragment_produk.penyetokan(pr);
    }

    @Override
    public void onPutProduk(List<Produk> pr) {
        fragment_produk.putproduk(pr);
    }
}