package id.ac.polman.astra.nim0320190011.toko.api.viewmodel;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import id.ac.polman.astra.nim0320190011.toko.api.model.Produk_aktivitas;
import id.ac.polman.astra.nim0320190011.toko.api.repository.Aktivitas_produk_repository;

public class Aktivitas_produk_view_model extends ViewModel {
    private static final String TAG = "Aktivitas_produk_view_model";

    private LiveData<Produk_aktivitas> mProdukAktivitasLiveData;
    private Aktivitas_produk_repository mAktivitasProdukRepository;
    private LiveData<List<Produk_aktivitas>> mAktivitasListData;

    public Aktivitas_produk_view_model(){
        mAktivitasProdukRepository = Aktivitas_produk_repository.get();
    }

    public LiveData<Produk_aktivitas> loadAktivitas(int id){
        mProdukAktivitasLiveData = mAktivitasProdukRepository.getAktivitas(id);
        return mProdukAktivitasLiveData;
    }

    @SuppressLint("LongLogTag")
    public LiveData<List<Produk_aktivitas>> getAktivitasByIdToko(int id){
        Log.i(TAG, "onClick: id " + id);
        mAktivitasListData = mAktivitasProdukRepository.getAktivitasByIdToko(id);
        return mAktivitasListData;
    }

    @SuppressLint("LongLogTag")
    public LiveData<List<Produk_aktivitas>> getAktivitasByIdTokoandTanggal(int id, String tanggal1, String tanggal2){
        Log.i(TAG, "onClick: id " + id);
        mAktivitasListData = mAktivitasProdukRepository.getAktByIdAndTanggal(id, tanggal1, tanggal2);
        return mAktivitasListData;
    }
}
