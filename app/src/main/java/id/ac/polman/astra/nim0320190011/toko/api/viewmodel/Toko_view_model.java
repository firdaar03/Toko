package id.ac.polman.astra.nim0320190011.toko.api.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import id.ac.polman.astra.nim0320190011.toko.api.model.Toko;
import id.ac.polman.astra.nim0320190011.toko.api.repository.Toko_repository;

public class Toko_view_model extends ViewModel {
    private static final String TAG = "Toko_view_model";

    private LiveData<Toko> mTokoLiveData;
    private Toko_repository mToko_repository;
    private MutableLiveData<String> mIdMutableLiveData;

    public Toko_view_model(){
        mToko_repository = Toko_repository.get();
        mIdMutableLiveData = new MutableLiveData<>();
        mTokoLiveData = Transformations.switchMap(mIdMutableLiveData,
                idToko -> mToko_repository.getToko(idToko));
    }

    public void loadToko(String idToko){
        Log.i(TAG, "loadToko: called");
        mIdMutableLiveData.setValue(idToko);
    }

    public LiveData<Toko> getTokoLiveData(){
        Log.i(TAG, "getTokoLiveData: called");
        return mTokoLiveData;
    }

    public void save(Toko t){
        mToko_repository.addToko(t);
    }
}
