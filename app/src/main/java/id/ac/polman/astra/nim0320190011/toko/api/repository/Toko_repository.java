package id.ac.polman.astra.nim0320190011.toko.api.repository;

import android.content.Context;
import android.util.Log;


import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import java.util.List;

import id.ac.polman.astra.nim0320190011.toko.api.ApiUtils;
import id.ac.polman.astra.nim0320190011.toko.api.service.Toko_service;
import id.ac.polman.astra.nim0320190011.toko.api.model.Toko;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Toko_repository {
    private static final String TAG = "Toko_repository";
    private static Toko_repository INSTANCE;
    private Toko_service mToko_service;

    public Toko_repository(Context context){
        mToko_service = ApiUtils.getTokoService();
    }

    public static void initialize(Context context){
        Log.i(TAG, "initialize: Called");
        if(INSTANCE == null){
            INSTANCE = new Toko_repository(context);
        }
    }

    public static Toko_repository get(){
        Log.i(TAG, "get: called repository");
        return INSTANCE;
    }
    
    public MutableLiveData<List<Toko>> getTokos(){
        Log.i(TAG, "getTokos: ini masuk get tokos");
        MutableLiveData<List<Toko>> tokos = new MutableLiveData<>();

        Call<List<Toko>> call = mToko_service.getTokos();
        call.enqueue(new Callback<List<Toko>>() {
            @Override
            public void onResponse(Call<List<Toko>> call, Response<List<Toko>> response) {
                if(response.isSuccessful()){
                    Log.e(TAG, "onResponse: " + new Gson().toJson(response.body()));
                    tokos.setValue(response.body());
                    Log.i(TAG, "onResponse: getToko called() size " + tokos.getValue().size());
                }
            }

            @Override
            public void onFailure(Call<List<Toko>> call, Throwable t) {
                Log.e(TAG, "onFailure: getToko called failed", t );
            }
        });

        return tokos;
    }

    public MutableLiveData<Toko> getToko(String idToko){
        MutableLiveData<Toko> toko = new MutableLiveData<>();

        Call<Toko> call = mToko_service.getTokoById(idToko);
        call.enqueue(new Callback<Toko>() {
            @Override
            public void onResponse(Call<Toko> call, Response<Toko> response) {
                if(response.isSuccessful()){
                    toko.setValue(response.body());
                    Log.i(TAG, "onResponse: getToko()");
                }
            }

            @Override
            public void onFailure(Call<Toko> call, Throwable t) {
                Log.e(TAG, "onFailure: getToko()", t );
            }
        });
        return toko;
    }
    
    public void updateToko(Toko t){
        Log.i(TAG, "updateToko: ID Toko " + t.getIdToko());
        Call<Toko> call = mToko_service.updateToko(t);
        call.enqueue(new Callback<Toko>() {
            @Override
            public void onResponse(Call<Toko> call, Response<Toko> response) {
                if(response.isSuccessful()){
                    Log.i(TAG, "onResponse: Telah terupdate Id " + t.getIdToko());
                }
            }

            @Override
            public void onFailure(Call<Toko> call, Throwable t) {
                Log.e(TAG, "onFailure: updateToko",t );
            }
        });
    }
    
    public void addToko(Toko t){
        Log.i(TAG, "addToko: Nama Pemilik : "+t.getNama_pemilik());
        Call<Toko> call = mToko_service.addToko(t);
        call.enqueue(new Callback<Toko>() {
            @Override
            public void onResponse(Call<Toko> call, Response<Toko> response) {
                if(response.isSuccessful()){
                    Log.d(TAG, "onResponse: add Toko success");
                }
            }

            @Override
            public void onFailure(Call<Toko> call, Throwable t) {
                Log.e(TAG, "onFailure: add Toko failur", t);
            }
        });
    }

    public void deleteToko(String idToko){
        Log.i(TAG, "deleteToko: call");
        Call<Toko> call = mToko_service.deleteUserById(idToko);
        call.enqueue(new Callback<Toko>() {
            @Override
            public void onResponse(Call<Toko> call, Response<Toko> response) {
                if(response.isSuccessful()){
                    Log.d(TAG, "onResponse: delete User");
                }
            }

            @Override
            public void onFailure(Call<Toko> call, Throwable t) {
                Log.e(TAG, "onFailure: delete User failed",t );
            }
        });
    }
}
