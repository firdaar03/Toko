package id.ac.polman.astra.nim0320190011.toko;

import android.app.Application;
import android.util.Log;

import id.ac.polman.astra.nim0320190011.toko.api.repository.Aktivitas_dompet_repository;
import id.ac.polman.astra.nim0320190011.toko.api.repository.Aktivitas_produk_repository;
import id.ac.polman.astra.nim0320190011.toko.api.repository.Dompet_repository;
import id.ac.polman.astra.nim0320190011.toko.api.repository.Produk_repository;
import id.ac.polman.astra.nim0320190011.toko.api.repository.Toko_repository;

public class TokoApplication extends Application {
    private static final String TAG = "TokoApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate: called");
        Toko_repository.initialize(this);
        Produk_repository.initialize(this);
        Dompet_repository.initialize(this);
        Aktivitas_dompet_repository.initialize(this);
        Aktivitas_produk_repository.initialize(this);
    }
}
