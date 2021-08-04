package id.ac.polman.astra.nim0320190011.toko.api.service;

import java.util.List;

import id.ac.polman.astra.nim0320190011.toko.api.model.Dt_Produk_aktivitas;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Dt_Produk_Aktivitas_Service {
    @GET("dt_aktivitas_produks")
    Call<List<Dt_Produk_aktivitas>> getAkivitasByidAkt(@Query("id") int id);
}
