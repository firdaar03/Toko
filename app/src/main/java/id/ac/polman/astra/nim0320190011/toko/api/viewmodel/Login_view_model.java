package id.ac.polman.astra.nim0320190011.toko.api.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.List;

import id.ac.polman.astra.nim0320190011.toko.api.model.Toko;
import id.ac.polman.astra.nim0320190011.toko.api.repository.Toko_repository;

public class Login_view_model extends ViewModel {
    private static final String TAG = "Login_view_model";

    private LiveData<Toko> mTokoLiveData;
    private Toko_repository mToko_repository;
    private MutableLiveData<List<Toko>> mTokoListMutableLiveData;

    public Login_view_model(){
        mToko_repository = Toko_repository.get();
        mTokoListMutableLiveData = mToko_repository.getTokos();
    }
    //    ================================================
    public Toko checklogin(String username, String password){
        Log.i(TAG, "checklogin: Ini loh masuk size data : " + mTokoListMutableLiveData.getValue().size());
        for ( Toko tk : mTokoListMutableLiveData.getValue()) {
            if(tk.getUsername().equals(username) && tk.getPassword().equals(password) && tk.getStatus() == 1){
                return tk;
            }
        }
        return null;
    }


    public MutableLiveData<List<Toko>> getTokos(){
        mTokoListMutableLiveData = mToko_repository.getTokos();
        return mTokoListMutableLiveData;
    }

    public LiveData<Toko> loadToko(String idToko){
        Log.i(TAG, "loadToko: called");
        mTokoLiveData = mToko_repository.getToko(idToko);
        return mTokoLiveData;
    }
}
