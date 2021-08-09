package id.ac.polman.astra.nim0320190011.toko.fragment.user;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import id.ac.polman.astra.nim0320190011.toko.R;
import id.ac.polman.astra.nim0320190011.toko.Utils.PictureUtils;
import id.ac.polman.astra.nim0320190011.toko.api.model.Produk;
import id.ac.polman.astra.nim0320190011.toko.api.model.Toko;
import id.ac.polman.astra.nim0320190011.toko.api.viewmodel.Produk_view_model;
import id.ac.polman.astra.nim0320190011.toko.api.viewmodel.Toko_view_model;

public class Fragment_toko_user extends Fragment {
    private static final String TAG = "Fragment_toko_user";

    Toko_view_model mTokoViewModel;
    Produk_view_model mProdukViewModel;
    private List<Toko> mTokoList;
    Toko dataToko;
    PictureUtils mPictureUtils;

    private RecyclerView mTokoRecyclerview;
    private TokoAdapter mTokoAdapter;

    private EditText mCariToko;
    private ImageView mBack;


    public Fragment_toko_user() {

    }

    public static Fragment_toko_user newInstance(){
                return new Fragment_toko_user();
    }

    private void updateUI(){
        Log.i(TAG, "updateUI called");
        mTokoAdapter = new TokoAdapter(mTokoList);
        mTokoRecyclerview.setAdapter(mTokoAdapter);
    }

    private void filter(String text) {
        ArrayList<Toko> filteredList = new ArrayList<>();
        for (Toko item : mTokoList) {
            if (item.getNama_pemilik().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        mTokoAdapter.filterList(filteredList);
    }

    public Toko_view_model getTokoViewModel(){
        Log.i(TAG, "getTokoViewModelList: called");
        if(mTokoViewModel == null){
            mTokoViewModel = new ViewModelProvider(this)
                    .get(Toko_view_model.class);
        }
        Log.i(TAG, "getTokoViewModelList: called 2");
        return mTokoViewModel;
    }

    public Produk_view_model getProdukViewModel(){
        Log.i(TAG, "getProdukViewModelList: called");
        if(mProdukViewModel == null){
            mProdukViewModel = new ViewModelProvider(this)
                    .get(Produk_view_model.class);
        }
        Log.i(TAG, "getProdukViewModelList: called 2");
        return mProdukViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTokoViewModel = getTokoViewModel();
        mProdukViewModel = getProdukViewModel();
        mTokoList = new ArrayList<>();
        mPictureUtils = new PictureUtils();
        mTokoAdapter = new TokoAdapter(new ArrayList<>());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: Called ");
        View v = inflater.inflate(R.layout.fragment_list_toko ,container, false);

        mCariToko = (EditText) v.findViewById(R.id.cari_toko);
        mCariToko.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        mBack = v.findViewById(R.id.back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        mTokoRecyclerview = v.findViewById(R.id.toko_recycler_view);
        mTokoRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        mTokoRecyclerview.setAdapter(mTokoAdapter);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            Thread.sleep(100);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
        Log.i(TAG, "onViewCreated() called");
        mTokoViewModel.getTokos().observe(
                getViewLifecycleOwner(),
                new Observer<List<Toko>>() {
                    @Override
                    public void onChanged(List<Toko> tokos) {
                        mTokoList = tokos;
                        updateUI();
                        Log.i(TAG, "Got toko: " + tokos.size());
                    }
                }
        );

    }

    private class TokoHolder extends RecyclerView.ViewHolder {
        private ImageView mFotoPemilik;
        private TextView mNamaPemilik;
        private ImageView mImageAddress;
        private TextView mAlamat;
        private ImageView mImagePhone;
        private TextView mTelepon;
        private ImageView mImageProduk;
        private TextView mProduk;

        private TextView mPremium;
        private Button mBtnKunjungi;

        private Toko mToko;

        public TokoHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.fragment_item_list_toko, parent, false));

            mFotoPemilik = itemView.findViewById(R.id.foto_toko);
            mNamaPemilik = itemView.findViewById(R.id.text_nama_toko);
            mImageAddress = itemView.findViewById(R.id.address);
            mAlamat = itemView.findViewById(R.id.text_address);
            mImagePhone = itemView.findViewById(R.id.phone);
            mTelepon = itemView.findViewById(R.id.telepon);
            mImageProduk = itemView.findViewById(R.id.produk);
            mProduk = itemView.findViewById(R.id.jumlah_produk);

            mPremium = itemView.findViewById(R.id.premium);
            mBtnKunjungi = itemView.findViewById(R.id.button_kunjungi);
        }

        @SuppressLint("ResourceType")
        public void bind(Toko toko){
            mToko = toko;
            try{
                mFotoPemilik.setImageBitmap(mPictureUtils.convertToImage(toko.getFoto_diri()));
            }catch (Exception e){
                Log.e(TAG, "onCreateView: ERROR PASANG PP", e);
            }
            mNamaPemilik.setText(mToko.getNama_pemilik().toUpperCase() + "'S STORE");
            mAlamat.setText(mToko.getAlamat());
            mTelepon.setText(mToko.getNo_telfon());
            mTelepon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "Telepon");
                }
            });
            mProdukViewModel.getProduksByIdToko(mToko.getIdToko()).observe(
                    getViewLifecycleOwner(), new Observer<List<Produk>>() {
                        @Override
                        public void onChanged(List<Produk> produks) {
                            mProduk.setText(produks.size() + " PRODUK");
                        }
                    });
            mPremium.setText("PREMIUM STORE");
            mBtnKunjungi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = Fragment_produk_user.newInstance(mToko);
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit(); // save the changes
                }
            });
        }
    }

    private class TokoAdapter extends  RecyclerView.Adapter<TokoHolder> {
        private List<Toko> mTokoList;

        public TokoAdapter(List<Toko> tokoList) {
            mTokoList = tokoList;
        }


        @NonNull
        @Override
        public TokoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new TokoHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull TokoHolder holder, int position) {
            Toko toko = mTokoList.get(position);
            holder.bind(toko);
        }

        @Override
        public int getItemCount() {
            return mTokoList.size();
        }

        public void filterList(ArrayList<Toko> filteredList) {
            mTokoList = filteredList;
            notifyDataSetChanged();
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
