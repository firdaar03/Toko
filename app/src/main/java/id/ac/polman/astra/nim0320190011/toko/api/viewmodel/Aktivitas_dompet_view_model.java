package id.ac.polman.astra.nim0320190011.toko.api.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import id.ac.polman.astra.nim0320190011.toko.api.model.Dompet_aktivitas;
import id.ac.polman.astra.nim0320190011.toko.api.repository.Aktivitas_dompet_repository;

public class Aktivitas_dompet_view_model extends ViewModel {
    private static final String TAG = "Aktivitas_dompet_view_model";

    private LiveData<Dompet_aktivitas> mDompetAktivitasLiveData;
    private Aktivitas_dompet_repository mAktivitasDompetRepository;
    private LiveData<List<Dompet_aktivitas>> mAktivitasListData;

    public Aktivitas_dompet_view_model(){
        mAktivitasDompetRepository = Aktivitas_dompet_repository.get();
    }

    public LiveData<Dompet_aktivitas> loadAktivitas(int id){
        mDompetAktivitasLiveData = mAktivitasDompetRepository.getAktivitas(id);
        return mDompetAktivitasLiveData;
    }

    public LiveData<List<Dompet_aktivitas>> getAktivitasByIdToko(int id){
        mAktivitasListData = mAktivitasDompetRepository.getAktivitasByIdToko(id);
        return mAktivitasListData;
    }
    public LiveData<List<Dompet_aktivitas>> getAktivitasByIdToko(
            int id, String tanggal1, String tanggal2){
        mAktivitasListData = mAktivitasDompetRepository.getAktivitasByIdAndTanggalToko(id, tanggal1, tanggal2);
        return mAktivitasListData;
    }
}
