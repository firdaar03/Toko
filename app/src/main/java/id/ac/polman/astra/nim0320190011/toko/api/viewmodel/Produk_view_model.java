package id.ac.polman.astra.nim0320190011.toko.api.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.List;

import id.ac.polman.astra.nim0320190011.toko.api.model.Produk;
import id.ac.polman.astra.nim0320190011.toko.api.repository.Produk_repository;

public class Produk_view_model extends ViewModel {
    private static final String TAG = "Produk_view_model";

    private LiveData<Produk> mProdukLiveData;
    private Produk_repository mProduk_repository;
    private MutableLiveData<String> mIdMutableLiveData;
    private MutableLiveData<List<Produk>> mProdukListMutableLiveData;

    public Produk_view_model(){
        mProduk_repository = Produk_repository.get();
        mIdMutableLiveData = new MutableLiveData<>();
        mProdukLiveData = Transformations.switchMap(mIdMutableLiveData,
                idProduk -> mProduk_repository.getProduk(idProduk));
//        mProdukListMutableLiveData = mProduk_repository.getProduks();
    }

    public void loadProduk(String idProduk){
        Log.i(TAG, "loadProduk: called");
        mIdMutableLiveData.setValue(idProduk);
    }

    public LiveData<Produk> getProdukLiveData(){
        Log.i(TAG, "getProdukLiveData: called");
        return mProdukLiveData;
    }

    public MutableLiveData<List<Produk>> getProduks(){
        mProduk_repository = Produk_repository.get();
//        mProdukListMutableLiveData = mProduk_repository.getProduks();
        return mProduk_repository.getProduks();
    }

    public void save(Produk p){
        mProduk_repository.addProduk(p);
    }

    public void update(Produk p){
        mProduk_repository.updateProduk(p);
    }
    public void addProduk(Produk p){
        mProduk_repository.addProduk(p);
    }
}
