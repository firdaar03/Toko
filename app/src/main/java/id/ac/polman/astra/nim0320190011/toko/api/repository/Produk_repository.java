package id.ac.polman.astra.nim0320190011.toko.api.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import java.util.List;


import id.ac.polman.astra.nim0320190011.toko.api.ApiUtils;
import id.ac.polman.astra.nim0320190011.toko.api.model.Produk;
import id.ac.polman.astra.nim0320190011.toko.api.model.Produk_aktivitas;
import id.ac.polman.astra.nim0320190011.toko.api.service.Produk_service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Produk_repository {
    private static final String TAG = "Produk_repository";
    private static Produk_repository INSTANCE;
    private Produk_service mProduk_service;

    public Produk_repository(Context context){
        mProduk_service = ApiUtils.getProdukService();
    }

    public static void initialize(Context context){
        Log.i(TAG, "initialize: Called");
        if(INSTANCE == null){
            INSTANCE = new Produk_repository(context);
        }
    }

    public static Produk_repository get(){
        Log.i(TAG, "get: called repository");
        return INSTANCE;
    }

    public MutableLiveData<List<Produk>> getProduks(){
        Log.i(TAG, "getProduks: ini masuk get produks");
        MutableLiveData<List<Produk>> produks = new MutableLiveData<>();

        Call<List<Produk>> call = mProduk_service.getProduks();
        call.enqueue(new Callback<List<Produk>>() {
            @Override
            public void onResponse(Call<List<Produk>> call, Response<List<Produk>> response) {
                if(response.isSuccessful()){
                    Log.e(TAG, "onResponse: " + new Gson().toJson(response.body()));
                    produks.setValue(response.body());
                    Log.i(TAG, "onResponse: getProduk called() size " + produks.getValue().size());
                }
            }

            @Override
            public void onFailure(Call<List<Produk>> call, Throwable t) {
                Log.e(TAG, "onFailure: getProduk called failed", t );
            }
        });

        return produks;
    }

    public MutableLiveData<List<Produk>> getProdukByIdToko(int id){
        Log.i(TAG, "getProdukByIdToko: ini masuk get produks");
        MutableLiveData<List<Produk>> produks = new MutableLiveData<>();

        Call<List<Produk>> call = mProduk_service.getProdukByIdToko(id);
        call.enqueue(new Callback<List<Produk>>() {
            @Override
            public void onResponse(Call<List<Produk>> call, Response<List<Produk>> response) {

                if(response.isSuccessful()){
                    Log.e(TAG, "onResponse: " + new Gson().toJson(response.body()));
                    produks.setValue(response.body());
                    Log.i(TAG, "onResponse: getProduk called() size " + produks.getValue().size());
                } else {
                    Log.e(TAG, "onResponse: Produk tidak ditemukan");
                }

            }

            @Override
            public void onFailure(Call<List<Produk>> call, Throwable t) {
                Log.e(TAG, "onFailure: getProduk called failed", t );
            }
        });

        return produks;
    }

    public MutableLiveData<Produk> getProduk(String idProduk){
        MutableLiveData<Produk> produk = new MutableLiveData<>();

        Call<Produk> call = mProduk_service.getProdukById(idProduk);
        call.enqueue(new Callback<Produk>() {
            @Override
            public void onResponse(Call<Produk> call, Response<Produk> response) {
                if(response.isSuccessful()){
                    produk.setValue(response.body());
                    Log.i(TAG, "onResponse: getProduk() " + new Gson().toJson(response.body()));
                }
            }

            @Override
            public void onFailure(Call<Produk> call, Throwable t) {
                Log.e(TAG, "onFailure: getProduk()", t );
            }
        });
        return produk;
    }

    public void updateProduk(Produk p){
        Log.i(TAG, "updateProduk: ID Produk " + p.getIdProduk());
        Call<Produk> call = mProduk_service.updateProduk(p);
        call.enqueue(new Callback<Produk>() {
            @Override
            public void onResponse(Call<Produk> call, Response<Produk> response) {
                if(response.isSuccessful()){
                    Log.i(TAG, "onResponse: Telah terupdate Id " + p.getIdProduk());
                }
            }

            @Override
            public void onFailure(Call<Produk> call, Throwable t) {
                Log.e(TAG, "onFailure: updateProduk",t );
            }
        });
    }

    public void addProduk(Produk p){
        Log.i(TAG, "addproduk: Nama produk : "+p.getNama());
        Call<Produk> call = mProduk_service.addProduk(p);
        call.enqueue(new Callback<Produk>() {
            @Override
            public void onResponse(Call<Produk> call, Response<Produk> response) {
                if(response.isSuccessful()){
                    Log.d(TAG, "onResponse: add Produk success");
                }
            }

            @Override
            public void onFailure(Call<Produk> call, Throwable t) {
                Log.e(TAG, "onFailure: add Produk failur", t);
            }
        });
    }

    public void deleteProduk(String idProduk){
        Log.i(TAG, "deleteProduk: call");
        Call<Produk> call = mProduk_service.deleteProdukById(idProduk);
        call.enqueue(new Callback<Produk>() {
            @Override
            public void onResponse(Call<Produk> call, Response<Produk> response) {
                if(response.isSuccessful()){
                    Log.d(TAG, "onResponse: delete Produk");
                }
            }

            @Override
            public void onFailure(Call<Produk> call, Throwable t) {
                Log.e(TAG, "onFailure: delete Produk failed",t );
            }
        });
    }

    public void aktivitas_ambil_produk(Produk_aktivitas p){
        Log.i(TAG, "ambil_produk: call" + p.getIdAkt());
        Call<Produk_aktivitas> call = mProduk_service.trAmbilAktivitasProduk(p);
        call.enqueue(new Callback<Produk_aktivitas>() {
            @Override
            public void onResponse(Call<Produk_aktivitas> call, Response<Produk_aktivitas> response) {
                if(response.isSuccessful()){
                    Log.i(TAG, "onResponse: Telah masuk " + p.getIdAkt());
                }
            }

            @Override
            public void onFailure(Call<Produk_aktivitas> call, Throwable t) {
                Log.e(TAG, "onFailure: aktivitasAmbilProduk",t );
            }
        });
    }

    public void ambil_produk(Produk p){
        Log.i(TAG, "ambil_produk: call " + p.getIdProduk());
        Call<Produk> call = mProduk_service.ambilProduk(p);
        call.enqueue(new Callback<Produk>() {
            @Override
            public void onResponse(Call<Produk> call, Response<Produk> response) {
                if(response.isSuccessful()){
                    Log.i(TAG, "onResponse: Telah terupdate Id " + p.getIdProduk());
                }
            }

            @Override
            public void onFailure(Call<Produk> call, Throwable t) {
                Log.e(TAG, "onFailure: updateProduk",t );
            }
        });
    }

    public void jual_produk(Produk p) {
        Log.i(TAG, "jual_produk: call" + p.getIdProduk());
        Call<Produk> call = mProduk_service.jualProduk(p);
        call.enqueue(new Callback<Produk>() {
            @Override
            public void onResponse(Call<Produk> call, Response<Produk> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "onResponse: Telah terupdate Id " + p.getIdProduk());
                }
            }

            @Override
            public void onFailure(Call<Produk> call, Throwable t) {
                Log.e(TAG, "onFailure: updateProduk", t);
            }
        });
    }

    public void tambah_stok(Produk p) {
        Log.i(TAG, "tambah_stok: call" + p.getIdProduk());
        Call<Produk> call = mProduk_service.tambahStok(p);
        call.enqueue(new Callback<Produk>() {
            @Override
            public void onResponse(Call<Produk> call, Response<Produk> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "onResponse: Telah terupdate Id " + p.getIdProduk());
                }
            }

            @Override
            public void onFailure(Call<Produk> call, Throwable t) {
                Log.e(TAG, "onFailure: updateProduk", t);
            }
        });
    }

}
