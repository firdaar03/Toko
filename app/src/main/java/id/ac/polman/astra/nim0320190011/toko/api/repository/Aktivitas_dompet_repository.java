package id.ac.polman.astra.nim0320190011.toko.api.repository;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import java.util.List;

import id.ac.polman.astra.nim0320190011.toko.api.ApiUtils;
import id.ac.polman.astra.nim0320190011.toko.api.model.Dompet;
import id.ac.polman.astra.nim0320190011.toko.api.model.Dompet_aktivitas;
import id.ac.polman.astra.nim0320190011.toko.api.service.Dompet_aktivitas_service;
import id.ac.polman.astra.nim0320190011.toko.api.service.Dompet_service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Aktivitas_dompet_repository {
    private static final String TAG = "Aktivitas_dompet_repository";
    private static Aktivitas_dompet_repository INSTANCE;
    private Dompet_aktivitas_service mDompet_aktivitas_service;

    public Aktivitas_dompet_repository(Context context){
        mDompet_aktivitas_service = ApiUtils.getDompetAktivitasService();
    }

    @SuppressLint("LongLogTag")
    public static void initialize(Context context){
        Log.i(TAG, "initialize: Called");
        if(INSTANCE == null){
            INSTANCE = new Aktivitas_dompet_repository(context);
        }
    }
    @SuppressLint("LongLogTag")
    public static Aktivitas_dompet_repository get(){
        Log.i(TAG, "get: ");
        return INSTANCE;
    }

    @SuppressLint("LongLogTag")
    public MutableLiveData<Dompet_aktivitas> getAktivitas(int id){
        Log.i(TAG, "getAktivitas: ");
        MutableLiveData<Dompet_aktivitas> output = new MutableLiveData<>();

        Call<Dompet_aktivitas> call = mDompet_aktivitas_service.getAktivitas(id);
        call.enqueue(new Callback<Dompet_aktivitas>() {
            @Override
            public void onResponse(Call<Dompet_aktivitas> call, Response<Dompet_aktivitas> response) {
                if(response.isSuccessful()){
                    Log.e(TAG, "onResponse: ini " + new Gson().toJson(response.body()));
                    output.setValue(response.body());
                }else{
                    Log.e(TAG, "onResponse: ERROR MAMANK");
                }
            }

            @Override
            public void onFailure(Call<Dompet_aktivitas> call, Throwable t) {
                Log.e(TAG, "onFailure: ERROR MAMANK ", t);
            }
        });

        return output;
    }
    @SuppressLint("LongLogTag")
    public MutableLiveData<List<Dompet_aktivitas>> getAktivitasByIdToko(int id){
        Log.i(TAG, "getAktivitas: ");
        MutableLiveData<List<Dompet_aktivitas>> output = new MutableLiveData<>();

        Call<List<Dompet_aktivitas>> call = mDompet_aktivitas_service.getAkivitasByidToko(id);
        call.enqueue(new Callback<List<Dompet_aktivitas>>() {
            @Override
            public void onResponse(Call<List<Dompet_aktivitas>> call, Response<List<Dompet_aktivitas>> response) {
                if(response.isSuccessful()){
                    Log.e(TAG, "onResponse: " + new Gson().toJson(response.body()));
                    output.setValue(response.body());
                }else{
                    Log.e(TAG, "onResponse: ERROR MAMANK");
                }
            }

            @Override
            public void onFailure(Call<List<Dompet_aktivitas>> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });

        return output;
    }
}
