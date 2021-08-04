package id.ac.polman.astra.nim0320190011.toko.api.repository;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import java.util.List;

import id.ac.polman.astra.nim0320190011.toko.api.ApiUtils;
import id.ac.polman.astra.nim0320190011.toko.api.model.Produk_aktivitas;
import id.ac.polman.astra.nim0320190011.toko.api.service.Produk_aktivitas_service;
import id.ac.polman.astra.nim0320190011.toko.api.service.Produk_service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Aktivitas_produk_repository {
    private static final String TAG = "Aktivitas_produk_repository";
    private static Aktivitas_produk_repository INSTANCE;
    private Produk_aktivitas_service mProduk_aktivitas_service;

    @SuppressLint("LongLogTag")
    public Aktivitas_produk_repository(Context context){
        Log.i(TAG, "initialize: Called");
        mProduk_aktivitas_service = ApiUtils.getProdukAktivitasService();
    }

    @SuppressLint("LongLogTag")
    public static void initialize(Context context){
        if(INSTANCE == null){
            INSTANCE = new Aktivitas_produk_repository(context);
        }
    }

    @SuppressLint("LongLogTag")
    public static Aktivitas_produk_repository get(){
        Log.i(TAG, "get: ");
        return INSTANCE;
    }

    @SuppressLint("LongLogTag")
    public MutableLiveData<Produk_aktivitas> getAktivitas(int id){
        Log.i(TAG, "getAktivitas: ");
        MutableLiveData<Produk_aktivitas> output = new MutableLiveData<>();

        Call<Produk_aktivitas> call = mProduk_aktivitas_service.getAktivitas(id);
        call.enqueue(new Callback<Produk_aktivitas>() {
            @Override
            public void onResponse(Call<Produk_aktivitas> call, Response<Produk_aktivitas> response) {
                if(response.isSuccessful()){
                    Log.e(TAG, "onResponse: ini " + new Gson().toJson(response.body()));
                    output.setValue(response.body());
                }else{
                    Log.e(TAG, "onResponse: ERROR NIH");
                }
            }

            @Override
            public void onFailure(Call<Produk_aktivitas> call, Throwable t) {
                Log.e(TAG, "onFailure: ERROR NIH", t);
            }
        });

        return output;
    }


    @SuppressLint("LongLogTag")
    public MutableLiveData<List<Produk_aktivitas>> getAktivitasByIdToko(int id){
        Log.i(TAG, "getAktivitas: ");
        MutableLiveData<List<Produk_aktivitas>> output = new MutableLiveData<>();

        Call<List<Produk_aktivitas>> call = mProduk_aktivitas_service.getAkivitasByidToko(id);
        call.enqueue(new Callback<List<Produk_aktivitas>>() {
            @Override
            public void onResponse(Call<List<Produk_aktivitas>> call, Response<List<Produk_aktivitas>> response) {
                if(response.isSuccessful()){
                    Log.e(TAG, "onResponse: " + new Gson().toJson(response.body()));
                    output.setValue(response.body());
                }else{
                    Log.e(TAG, "onResponse: ERROR MAMANK");
                }
            }

            @Override
            public void onFailure(Call<List<Produk_aktivitas>> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });

        return output;
    }
}
