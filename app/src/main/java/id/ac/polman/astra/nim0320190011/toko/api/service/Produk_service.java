package id.ac.polman.astra.nim0320190011.toko.api.service;

import java.util.List;


import id.ac.polman.astra.nim0320190011.toko.api.model.Produk;
import id.ac.polman.astra.nim0320190011.toko.api.model.Produk_aktivitas;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface Produk_service {

    @GET("produk")
    Call<Produk> getProdukById(@Query("id") String id);

    @GET("produks")
    Call<List<Produk>> getProduks();

    @GET("produkbytoko")
    Call<List<Produk>> getProdukByIdToko(@Query("id") int id);

    @POST("produk")
    Call<Produk> addProduk(@Body Produk p);

    @PUT("produk")
    Call<Produk> updateProduk(@Body Produk p );

    @DELETE("produk")
    Call<Produk> deleteProdukById(@Query("id") String id);

    @PUT("ambil_aktivitas_produk")
    Call<Produk_aktivitas> trAmbilAktivitasProduk(@Body Produk_aktivitas p);

    @PUT("ambil_produk")
    Call<Produk> ambilProduk(@Body Produk p);

    @PUT("jual_produk")
    Call<Produk> jualProduk(@Body Produk p);

    @PUT("tambah_stok")
    Call<Produk> tambahStok(@Body Produk p);
}
