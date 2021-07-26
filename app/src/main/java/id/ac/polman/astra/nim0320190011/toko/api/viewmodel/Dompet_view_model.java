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
    private MutableLiveData<List<Dompet>> mDompetListMutableLiveData;

    public Dompet_view_model(){
        mDompet_repository = Dompet_repository.get();
        mIdMutableLiveData = new MutableLiveData<>();
        mDompetLiveData = Transformations.switchMap(mIdMutableLiveData,
                idDompet -> mDompet_repository.getDompet(idDompet));
        mDompetListMutableLiveData = mDompet_repository.getDompets();
    }

    public void loadDompet(String idDompet){
        Log.i(TAG, "loadDompet: called");
        mIdMutableLiveData.setValue(idDompet);
    }

    public LiveData<Dompet> getDompetLiveData(){
        Log.i(TAG, "getDompetLiveData: called");
        return mDompetLiveData;
    }

    public MutableLiveData<List<Dompet>> getDompets(){
        mDompetListMutableLiveData = mDompet_repository.getDompets();
//        mDompetListMutableLiveData = mDompet_repository.getDompets();
        return mDompetListMutableLiveData;
    }

    public List<Dompet> getDompetsByIdToko(int idToko){
        List<Dompet> Dompets = new ArrayList<>();
        for(Dompet a : mDompetListMutableLiveData.getValue()){
            if(a.getIdToko() == idToko){
                Dompets.add(a);
            }
        }
        return Dompets;
    }

    public void save(Dompet p){
        mDompet_repository.addDompet(p);
    }

    public void update(Dompet p){
        mDompet_repository.updateDompet(p);
    }

    public void delete(String id){
        mDompet_repository.deleteDompet(id);
    }
}
