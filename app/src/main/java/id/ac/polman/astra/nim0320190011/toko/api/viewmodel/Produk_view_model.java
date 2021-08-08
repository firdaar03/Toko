package id.ac.polman.astra.nim0320190011.toko.api.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import id.ac.polman.astra.nim0320190011.toko.api.model.Produk;
import id.ac.polman.astra.nim0320190011.toko.api.model.Produk_aktivitas;
import id.ac.polman.astra.nim0320190011.toko.api.repository.Produk_repository;

public class Produk_view_model extends ViewModel {
    private static final String TAG = "Produk_view_model";

    private LiveData<Produk> mProdukLiveData;
    private Produk_repository mProduk_repository;
    private MutableLiveData<String> mIdMutableLiveData;
    private LiveData<List<Produk>> mProdukListMutableLiveData;

    public Produk_view_model(){
        mProduk_repository = Produk_repository.get();
        mIdMutableLiveData = new MutableLiveData<>();

    }

    public void loadProduk(String idProduk){
        Log.i(TAG, "loadProduk: called");
        mIdMutableLiveData.setValue(idProduk);
    }

    public LiveData<Produk> getProdukLiveData(){
        Log.i(TAG, "getProdukLiveData: called");
        return mProdukLiveData;
    }

    public LiveData<List<Produk>> getProduks(){
//        mProdukListMutableLiveData = mProduk_repository.getProduks();
        mProdukListMutableLiveData = mProduk_repository.getProduks();
        return mProdukListMutableLiveData;
    }

    public LiveData<List<Produk>> getProduksByIdToko(int idToko){
//        mProdukListMutableLiveData = mProduk_repository.getProdukByIdToko(idToko);
        return mProduk_repository.getProdukByIdToko(idToko);
    }



    public void save(Produk p){
        mProduk_repository.addProduk(p);
    }

    public void update(Produk p){
        mProduk_repository.updateProduk(p);
    }

    public void delete(String id){
        mProduk_repository.deleteProduk(id);
    }

    public void trAmbilAktivitasProduk(Produk_aktivitas p){
        mProduk_repository.aktivitas_ambil_produk(p);
    }

    public void ambil_produk(Produk p){
        mProduk_repository.ambil_produk(p);
    }

    public void jual_produk(Produk p){
        mProduk_repository.jual_produk(p);
    }

    public void tambah_stok(Produk p){
        mProduk_repository.tambah_stok(p);
    }
}
