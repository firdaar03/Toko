package id.ac.polman.astra.nim0320190011.toko.api.viewmodel;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import id.ac.polman.astra.nim0320190011.toko.api.model.Dt_Produk_aktivitas;
import id.ac.polman.astra.nim0320190011.toko.api.repository.Dt_produk_aktivitas_repository;

public class Dt_aktivitas_produk_view_model extends ViewModel {
    private static final String TAG = "Dt_aktivitas_produk_view_model";

    private LiveData<Dt_Produk_aktivitas> mDtProdukAktivitasLiveData;
    private Dt_produk_aktivitas_repository mDtProdukAktivitasRepository;
    private LiveData<List<Dt_Produk_aktivitas>> mListLiveData;

    public Dt_aktivitas_produk_view_model(){
        mDtProdukAktivitasRepository = Dt_produk_aktivitas_repository.get();
    }

    @SuppressLint("LongLogTag")
    public LiveData<List<Dt_Produk_aktivitas>> getDtAktivitastByIdAkt(int id){
        Log.i(TAG, "onClick: id " + id);
        mListLiveData = mDtProdukAktivitasRepository.getDtAktivitasByIdAkt(id);
        return mListLiveData;
    }
}
