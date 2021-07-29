package id.ac.polman.astra.nim0320190011.toko.api.viewmodel;

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

    public LiveData<List<Produk_aktivitas>> getAktivitasByIdToko(int id){
        mAktivitasListData = mAktivitasProdukRepository.getAktivitasByIdToko(id);
        return mAktivitasListData;
    }
}
