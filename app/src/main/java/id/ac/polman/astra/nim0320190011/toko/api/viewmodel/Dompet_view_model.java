package id.ac.polman.astra.nim0320190011.toko.api.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import id.ac.polman.astra.nim0320190011.toko.api.model.Dompet;
import id.ac.polman.astra.nim0320190011.toko.api.repository.Dompet_repository;

public class Dompet_view_model extends ViewModel {
    private static final String TAG = "Dompet_view_model";

    private LiveData<Dompet> mDompetLiveData;
    private Dompet_repository mDompet_repository;
    private MutableLiveData<String> mIdMutableLiveData;
    private LiveData<List<Dompet>> mDompetListMutableLiveData;

    public Dompet_view_model(){
        mDompet_repository = Dompet_repository.get();
        mIdMutableLiveData = new MutableLiveData<>();
    }

    public LiveData<Dompet> loadDompet(String idDompet){
        Log.i(TAG, "loadDompet: called ");
        mDompetLiveData = mDompet_repository.getDompetToko(idDompet);
        return mDompet_repository.getDompetToko(idDompet);
    }


    public LiveData<Dompet> getDompetLiveData(){
        Log.i(TAG, "getDompetLiveData: called");
        return mDompetLiveData;
    }

    public LiveData<List<Dompet>> getDompets(){
        mDompetListMutableLiveData = mDompet_repository.getDompets();
//        mDompetListMutableLiveData = mDompet_repository.getDompets();
        return mDompetListMutableLiveData;
    }

    public LiveData<Dompet> getDompetToko(int idToko){
        return mDompet_repository.getDompetToko(idToko + "");
    }

    public Dompet getDompetsByIdToko(int idToko){
        for(Dompet a : mDompetListMutableLiveData.getValue()){
            if(a.getIdToko() == idToko){
                loadDompet(idToko + "");
                return a;
            }
        }
        return null;
    }

    public void save(Dompet p){
        mDompet_repository.addDompet(p);
    }

    public void uang_masuk(Dompet p){
        mDompet_repository.uang_masuk(p);
    }

    public void uang_keluar(Dompet p){
        mDompet_repository.uang_keluar(p);
    }

    public void perbarui(Dompet p){
        mDompet_repository.perbarui(p);
    }

    public void kosongkan(Dompet p){
        mDompet_repository.kosongkan(p);
    }
}
