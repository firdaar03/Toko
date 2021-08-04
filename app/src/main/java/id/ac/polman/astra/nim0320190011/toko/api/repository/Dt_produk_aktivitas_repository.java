package id.ac.polman.astra.nim0320190011.toko.api.repository;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import java.util.List;

import id.ac.polman.astra.nim0320190011.toko.api.ApiUtils;
import id.ac.polman.astra.nim0320190011.toko.api.model.Dt_Produk_aktivitas;
import id.ac.polman.astra.nim0320190011.toko.api.service.Dt_Produk_Aktivitas_Service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Dt_produk_aktivitas_repository {
    private static final String TAG = "Dt_produk_aktivitas_repository";
    private static Dt_produk_aktivitas_repository INSTANCE;
    private Dt_Produk_Aktivitas_Service mDt_produk_aktivitas_service;

    @SuppressLint("LongLogTag")
    public Dt_produk_aktivitas_repository(Context context){
        Log.i(TAG, "initialize: Called");
        mDt_produk_aktivitas_service = ApiUtils.getDtProdukAktivitasService();
    }

    public static void initialize(Context context){
        if (INSTANCE == null){
            INSTANCE = new Dt_produk_aktivitas_repository(context);
        }
    }

    @SuppressLint("LongLogTag")
    public static Dt_produk_aktivitas_repository get(){
        Log.i(TAG, "get: ");
        return INSTANCE;
    }

    @SuppressLint("LongLogTag")
    public MutableLiveData<List<Dt_Produk_aktivitas>> getDtAktivitasByIdAkt(int id){
        Log.i(TAG, "getDtAktivitas: ");
        MutableLiveData<List<Dt_Produk_aktivitas>> output = new MutableLiveData<>();

        Call<List<Dt_Produk_aktivitas>> call = mDt_produk_aktivitas_service.getAkivitasByidAkt(id);
        call.enqueue(new Callback<List<Dt_Produk_aktivitas>>() {
            @Override
            public void onResponse(Call<List<Dt_Produk_aktivitas>> call, Response<List<Dt_Produk_aktivitas>> response) {
                if(response.isSuccessful()){
                    Log.e(TAG, "onResponse: " + new Gson().toJson(response.body()));
                    output.setValue(response.body());
                }else{
                    Log.e(TAG, "onResponse: error");
                }
            }

            @Override
            public void onFailure(Call<List<Dt_Produk_aktivitas>> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });

        return output;
    }

}
