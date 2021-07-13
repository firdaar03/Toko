package id.ac.polman.astra.nim0320190011.toko.api.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import id.ac.polman.astra.nim0320190011.toko.api.model.Toko;
import id.ac.polman.astra.nim0320190011.toko.api.repository.Toko_repository;

public class Toko_view_model_list extends ViewModel {
    private final String TAG = "Toko_view_model_list";

    private MutableLiveData<List<Toko>> mTokoListMutableLiveData;
    private Toko_repository mTokoRepository;

    public Toko_view_model_list(){
        Log.i(TAG, "Toko_view_model_list: called");
        mTokoRepository = mTokoRepository.get();
        mTokoListMutableLiveData = mTokoRepository.getTokos();
    }

    public MutableLiveData<List<Toko>> getTokos(){
        return mTokoRepository.getTokos();
    }

    public void addToko(Toko toko){
        mTokoRepository.addToko(toko);
    }

//     ========================= fungsi-fungsi
public int checklogin(String username, String password){
    Log.i(TAG, "checklogin: Ini loh masuk size data : " + mTokoListMutableLiveData.getValue().size());
    for ( Toko tk : mTokoListMutableLiveData.getValue()) {
        if(tk.getUsername().equals(username) && tk.getPassword().equals(password)){
            return tk.getIdToko();
        }
        Log.i(TAG, "== : " + tk.getPassword());
    }
    return -1;
}
}
