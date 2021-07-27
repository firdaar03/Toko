package id.ac.polman.astra.nim0320190011.toko.api.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import java.util.List;

import id.ac.polman.astra.nim0320190011.toko.api.ApiUtils;
import id.ac.polman.astra.nim0320190011.toko.api.model.Dompet;
import id.ac.polman.astra.nim0320190011.toko.api.service.Dompet_service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Dompet_repository {
    private static final String TAG = "Dompet_repository";
    private static Dompet_repository INSTANCE;
    private Dompet_service mDompet_service;

    public Dompet_repository(Context context){
        mDompet_service = ApiUtils.getDompetService();
    }

    public static void initialize(Context context){
        Log.i(TAG, "initialize: Called");
        if(INSTANCE == null){
            INSTANCE = new Dompet_repository(context);
        }
    }

    public static Dompet_repository get(){
        Log.i(TAG, "get: called repository");
        return INSTANCE;
    }

    public MutableLiveData<List<Dompet>> getDompets(){
        Log.i(TAG, "getDompets: ini masuk get ");
        MutableLiveData<List<Dompet>> Dompets = new MutableLiveData<>();

        Call<List<Dompet>> call = mDompet_service.getDompets();
        call.enqueue(new Callback<List<Dompet>>() {
            @Override
            public void onResponse(Call<List<Dompet>> call, Response<List<Dompet>> response) {
                if(response.isSuccessful()){
                    Log.e(TAG, "onResponse: ini " + new Gson().toJson(response.body()));
                    Dompets.setValue(response.body());
                    Log.i(TAG, "onResponse: getDompet called() size " + Dompets.getValue().size());
                }
            }

            @Override
            public void onFailure(Call<List<Dompet>> call, Throwable t) {
                Log.e(TAG, "onFailure: getDompet called failed", t );
            }
        });

        return Dompets;
    }

    public MutableLiveData<Dompet> getDompet(String idDompet){
        MutableLiveData<Dompet> Dompet = new MutableLiveData<>();

        Call<Dompet> call = mDompet_service.getDompet(idDompet);
        call.enqueue(new Callback<Dompet>() {
            @Override
            public void onResponse(Call<Dompet> call, Response<Dompet> response) {
                if(response.isSuccessful()){
                    Dompet.setValue(response.body());
                    Log.i(TAG, "onResponse: getDompet() " + new Gson().toJson(response.body()));
                }
            }

            @Override
            public void onFailure(Call<Dompet> call, Throwable t) {
                Log.e(TAG, "onFailure: getDompet()", t );
            }
        });
        return Dompet;
    }

    public MutableLiveData<Dompet> getDompetToko(String idToko){
        Log.i(TAG, "getDompetToko: ");
        MutableLiveData<Dompet> Dompet = new MutableLiveData<>();

        Call<Dompet> call = mDompet_service.getDompetToko(idToko);
        call.enqueue(new Callback<Dompet>() {
            @Override
            public void onResponse(Call<Dompet> call, Response<Dompet> response) {
                if(response.isSuccessful()){
                    Dompet.setValue(response.body());
                    Log.e(TAG, "onResponse: getDompet () " + new Gson().toJson(response.body()));
                }else{
                    Log.e(TAG, "onResponse: gagal () " + new Gson().toJson(response.body()));
                }
            }

            @Override
            public void onFailure(Call<Dompet> call, Throwable t) {
                Log.e(TAG, "onFailure: getDompet()", t );
            }
        });
        return Dompet;
    }

    public void uang_masuk(Dompet p){
        Log.i(TAG, "updateDompet: ID Dompet " + p.getIdDompet());
        Call<Dompet> call = mDompet_service.updateDompet(p);
        call.enqueue(new Callback<Dompet>() {
            @Override
            public void onResponse(Call<Dompet> call, Response<Dompet> response) {
                if(response.isSuccessful()){
                    Log.i(TAG, "onResponse: Telah terupdate Id " + p.getIdDompet());
                }
            }

            @Override
            public void onFailure(Call<Dompet> call, Throwable t) {
                Log.e(TAG, "onFailure: updateDompet",t );
            }
        });
    }

    public void addDompet(Dompet p){
        Log.i(TAG, "addDompet: Nama Dompet : "+p.getUang());
        Call<Dompet> call = mDompet_service.addDompet(p);
        call.enqueue(new Callback<Dompet>() {
            @Override
            public void onResponse(Call<Dompet> call, Response<Dompet> response) {
                if(response.isSuccessful()){
                    Log.d(TAG, "onResponse: add Dompet success");
                }
            }

            @Override
            public void onFailure(Call<Dompet> call, Throwable t) {
                Log.e(TAG, "onFailure: add Dompet failur", t);
            }
        });
    }

    public void uang_keluar(Dompet dompet){
        Log.i(TAG, "updateDompet: ID Dompet " + dompet.getIdDompet());
        Call<Dompet> call = mDompet_service.deleteDompetById(dompet);
        call.enqueue(new Callback<Dompet>() {
            @Override
            public void onResponse(Call<Dompet> call, Response<Dompet> response) {
                if(response.isSuccessful()){
                    Log.i(TAG, "onResponse: Telah terupdate Id " + dompet.getIdDompet());
                }
            }

            @Override
            public void onFailure(Call<Dompet> call, Throwable t) {
                Log.e(TAG, "onFailure: updateDompet",t );
            }
        });
    }
}
