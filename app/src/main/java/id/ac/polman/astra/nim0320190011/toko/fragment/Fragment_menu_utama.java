package id.ac.polman.astra.nim0320190011.toko.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import id.ac.polman.astra.nim0320190011.toko.R;
import id.ac.polman.astra.nim0320190011.toko.api.model.Toko;
import id.ac.polman.astra.nim0320190011.toko.api.viewmodel.Toko_view_model_list;

public class Fragment_menu_utama extends Fragment {
    private static final String TAG = "Fragment_menu_utama";
    private static int id = -1;

    Toko dataToko;

    RelativeLayout mButtonProduk;
    RelativeLayout mButtonDompet;

    TextView mNamaPemilik;

    private Toko_view_model_list mTokoViewModelList;

    public Toko_view_model_list getTokoViewModelList(){
        Log.i(TAG, "getTokoViewModelList: called");
        if(mTokoViewModelList == null){
            mTokoViewModelList = new ViewModelProvider(this)
                    .get(Toko_view_model_list.class);
        }
        Log.i(TAG, "getTokoViewModelList: called 2");
        return mTokoViewModelList;
    }

    public static Fragment_menu_utama newInstance(Toko user) {
        return new Fragment_menu_utama(user);
    }

    private Fragment_menu_utama(Toko toko){
        dataToko = toko;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: toko name " + dataToko.getNama_pemilik());
        mTokoViewModelList = getTokoViewModelList();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: Called ");
        View v = inflater.inflate(R.layout.fragment_menu_utama, container, false);

        mNamaPemilik = (TextView) v.findViewById(R.id.nama_pemilik);
        mNamaPemilik.setText(dataToko.getNama_pemilik().toUpperCase());

        mButtonProduk = v.findViewById(R.id.button_produk);
        mButtonProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getContext(),"Produk button clicked", Toast.LENGTH_SHORT)
//                        .show();
                mCallbacks.onProdukButtonClicked();
            }
        });

        mButtonDompet = v.findViewById(R.id.button_dompet);
        mButtonDompet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallbacks.onDompetButtonClicked();
            }
        });
        return v;
    }

    public interface Callbacks{
        public void onProdukButtonClicked();
        public void onDompetButtonClicked();
    }

    private Fragment_menu_utama.Callbacks mCallbacks = null;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mCallbacks = (Fragment_menu_utama.Callbacks) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }
}
