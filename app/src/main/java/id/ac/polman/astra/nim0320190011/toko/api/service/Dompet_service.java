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
    Call<Dompet> getDompetById(@Query("id") String id);

    @GET("dompets")
    Call<List<Dompet>> getDompets();

    @POST("dompet")
    Call<Dompet> addDompet(@Body Dompet p);

    @PUT("Dompet")
    Call<Dompet> updateDompet(@Body Dompet p );

    @DELETE("Dompet")
    Call<Dompet> deleteDompetById(@Query("id") String id);
}
