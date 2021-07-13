package id.ac.polman.astra.nim0320190011.toko.api.service;

import java.util.List;

import id.ac.polman.astra.nim0320190011.toko.api.model.Toko;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface Toko_service {

    @GET("toko")
    Call<Toko> getTokoById(@Query("id") String id);

    @GET("tokos")
    Call<List<Toko>> getTokos();

    @POST("toko")
    Call<Toko> addToko(@Body Toko t);

    @PUT("toko")
    Call<Toko> updateToko(@Body Toko t);

    @DELETE("toko")
    Call<Toko> deleteUserById(@Query("id") String id);
}
