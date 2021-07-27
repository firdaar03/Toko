package id.ac.polman.astra.nim0320190011.toko.api;

import id.ac.polman.astra.nim0320190011.toko.api.model.Produk;
import id.ac.polman.astra.nim0320190011.toko.api.service.Dompet_service;
import id.ac.polman.astra.nim0320190011.toko.api.service.Produk_service;
import id.ac.polman.astra.nim0320190011.toko.api.service.Toko_service;
import retrofit2.Retrofit;

public class ApiUtils {
//    public static final String API_URL = "http://192.168.43.149:8080/";
    public static final String API_URL = "http://10.0.2.2:8080/";
//    public static final String API_URL ="http://192.168.1.4:8080/";

    private ApiUtils(){

    }

    public static Toko_service getTokoService(){
        return RetrofitClient.getClient(API_URL).create(Toko_service.class);
    }

    public static Produk_service getProdukService(){
        return RetrofitClient.getClient(API_URL).create(Produk_service.class);
    }

    public static Dompet_service getDompetService(){
        return RetrofitClient.getClient(API_URL).create(Dompet_service.class);
    }
}
