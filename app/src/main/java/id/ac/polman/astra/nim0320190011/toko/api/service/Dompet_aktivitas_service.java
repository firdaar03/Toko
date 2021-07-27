package id.ac.polman.astra.nim0320190011.toko.api.service;

import java.util.List;

import id.ac.polman.astra.nim0320190011.toko.api.model.Dompet;
import id.ac.polman.astra.nim0320190011.toko.api.model.Dompet_aktivitas;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface Dompet_aktivitas_service {
    @GET("aktivitas_dompet")
    Call<Dompet_aktivitas> getAktivitas(@Query("id") int id);

    @GET("aktivitas_dompets")
    Call<List<Dompet_aktivitas>> getAkivitasByidToko(@Query("id") int id);
}
