package id.ac.polman.astra.nim0320190011.toko.api.service;

import java.util.List;

import id.ac.polman.astra.nim0320190011.toko.api.model.Dompet;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface Dompet_service {
    @GET("dompet")
    Call<Dompet> getDompet(@Query("id") String id);

    @GET("dompet_toko")
    Call<Dompet> getDompetToko(@Query("id") String id);

    @GET("dompets")
    Call<List<Dompet>> getDompets();

    @POST("dompet")
    Call<Dompet> addDompet(@Body Dompet p);

    @PUT("dompet")
    Call<Dompet> updateDompet(@Body Dompet p);

    @PUT("dompet_")
    Call<Dompet> deleteDompetById(@Body Dompet p);

    @PUT("dompet_perbarui")
    Call<Dompet> perbaruiDompet(@Body Dompet p);

    @PUT("dompet_kosongkan")
    Call<Dompet> kosongkanDompet(@Body Dompet p);
}
