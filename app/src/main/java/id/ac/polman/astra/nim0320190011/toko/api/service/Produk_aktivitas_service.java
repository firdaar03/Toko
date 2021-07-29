package id.ac.polman.astra.nim0320190011.toko.api.service;

import java.util.List;

import id.ac.polman.astra.nim0320190011.toko.api.model.Produk_aktivitas;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Produk_aktivitas_service {
    @GET("aktivitas_produk")
    Call<Produk_aktivitas> getAktivitas(@Query("id") int id);

    @GET("aktivitas_produks")
    Call<List<Produk_aktivitas>> getAkivitasByidToko(@Query("id") int id);
}
