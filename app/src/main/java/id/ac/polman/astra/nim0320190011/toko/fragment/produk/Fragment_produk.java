package id.ac.polman.astra.nim0320190011.toko.fragment.produk;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import id.ac.polman.astra.nim0320190011.toko.R;
import id.ac.polman.astra.nim0320190011.toko.Utils.PictureUtils;
import id.ac.polman.astra.nim0320190011.toko.api.model.Produk;
import id.ac.polman.astra.nim0320190011.toko.api.model.Toko;
import id.ac.polman.astra.nim0320190011.toko.api.viewmodel.Produk_view_model;
import id.ac.polman.astra.nim0320190011.toko.api.viewmodel.Toko_view_model;
import id.ac.polman.astra.nim0320190011.toko.fragment.Fragment_menu_utama;
import id.ac.polman.astra.nim0320190011.toko.fragment.Fragment_setting;
import id.ac.polman.astra.nim0320190011.toko.fragment.dompet.Fragment_dompet;

public class Fragment_produk extends Fragment {
    private static final String TAG = "Fragment_produk";

    Produk_view_model mProdukViewModel;
    private List<Produk> mProdukList;
    Toko dataToko;
    PictureUtils mPictureUtils;

    private RecyclerView mProdukRecyclerView;
    private ProdukAdapter mAdapter;

    private Button mAddProdukButton;
    private Button mPutProdukButton;
    private TextView mNamaPemilik;
    private EditText mCariProduk;
    private ImageView mBack;
    private androidx.swiperefreshlayout.widget.SwipeRefreshLayout mRefreshLayout;

    DisplayMetrics displayMetrics;

    public static Fragment_produk newInstance(Toko in) {
        return new Fragment_produk(in);
    }

    private Fragment_produk(Toko t){
        dataToko = t;
    }


//    private void updateUI(List<Produk> produks)
    private void updateUI()
    {
        Log.i(TAG, "updateUI called");
        mAdapter = new ProdukAdapter(mProdukList);
        mProdukRecyclerView.setAdapter(mAdapter);
    }

    private void filter(String text) {
        ArrayList<Produk> filteredList = new ArrayList<>();
        for (Produk item : mProdukList) {
            if (item.getNama().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        mAdapter.filterList(filteredList);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "Fragment_Produk.onCreate() called");
        mProdukViewModel = new ViewModelProvider(this)
                .get(Produk_view_model.class);
        mAdapter = new  ProdukAdapter(Collections.<Produk>emptyList());
        mPictureUtils = new PictureUtils();
        mProdukList = new ArrayList<>();

        displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: Called ");
        View v = inflater.inflate(R.layout.fragment_produk, container, false);


        mCariProduk = (EditText) v.findViewById(R.id.cari_produk);
        mCariProduk.addTextChangedListener(new TextWatcher() {
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

        mNamaPemilik = (TextView) v.findViewById(R.id.textView3);
        mNamaPemilik.setText(dataToko.getNama_pemilik().toUpperCase());

        mAddProdukButton = (Button) v.findViewById(R.id.button_add);
        mAddProdukButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_product_option fragment = Fragment_product_option.newInstance(dataToko);
                FragmentManager fm = getFragmentManager();
                fragment.show(fm,"Fragment Setting");
            }
        });

        mPutProdukButton = (Button) v.findViewById(R.id.button_put);
        mPutProdukButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = Fragment_put_produk.newInstance(dataToko);
                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit(); // save the changes
            }
        });

        mRefreshLayout = v.findViewById(R.id.swiperefresh);
        mRefreshLayout.setProgressViewOffset(false, 0, (displayMetrics.heightPixels/2) - (mRefreshLayout.getProgressCircleDiameter() / 2));
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });

        if (mProdukList.size() == 0){
            mRefreshLayout.setRefreshing(true);
        }

        mProdukRecyclerView = (RecyclerView) v.findViewById(R.id.produk_recycler_view);
        mProdukRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mProdukRecyclerView.setAdapter(mAdapter);

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
        Log.i(TAG, "Fragment_Produk.onViewCreated() called");
        if (mProdukList.size() == 0) {
            mProdukViewModel.getProduksByIdToko(dataToko.getIdToko()).observe(
                    getViewLifecycleOwner(),
                    new Observer<List<Produk>>() {
                        @Override
                        public void onChanged(List<Produk> produks) {
                            mProdukList = produks;
                            updateUI();
                            mRefreshLayout.setRefreshing(false);
                            Log.i(TAG, "Got Produk: " + produks.size());
                        }
                    }
            );
        }
    }
    private void refresh(){
        Log.i(TAG, "Fragment_Produk.onViewCreated() called");
        mProdukViewModel.getProduksByIdToko(dataToko.getIdToko()).observe(
                getViewLifecycleOwner(),
                new Observer<List<Produk>>() {
                    @Override
                    public void onChanged(List<Produk> produks) {
                        mProdukList = produks;
                        updateUI();
                        mRefreshLayout.setRefreshing(false);
                        Log.i(TAG, "Got Produk: " + produks.size());
                    }
             });
    }

    public void produk_tambah(Produk p){
        mProdukList.add(p);
        updateUI();
    }

    public void penyetokan(List<Produk> pr) {
        for(Produk a : mProdukList){
            for(Produk b : pr){
                if(a.getIdProduk() == b.getIdProduk()){
                    a.setJumlah(a.getJumlah() + b.getJumlah());
                }
            }
        }
        updateUI();
    }

    public void putproduk(List<Produk> pr) {
        for(Produk a : mProdukList){
            for(Produk b : pr){
                if(a.getIdProduk() == b.getIdProduk()){
                    a.setJumlah(a.getJumlah() - b.getJumlah());
                }
            }
        }
        updateUI();
    }

    private class ProdukHolder extends RecyclerView.ViewHolder{

        private ImageView mFotoProduk;
        private TextView mNamaProduk;
        private TextView mHargaProduk;
        private TextView mJumlahProduk;
        private TextView mMerkProduk;
        private ImageView mEditProduk;
        private ImageView mDeleteProduk;
        private ConstraintLayout mItemProdukLayout;
        private Produk mProduk;

        public ProdukHolder (LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.fragment_item_produk, parent, false));

            mFotoProduk = (ImageView) itemView.findViewById(R.id.foto_produk);
            mNamaProduk = (TextView) itemView.findViewById(R.id.text_nama_produk);
            mHargaProduk = (TextView) itemView.findViewById(R.id.text_harga_produk);
            mJumlahProduk = (TextView) itemView.findViewById(R.id.text_jumlah_produk);
            mMerkProduk = (TextView) itemView.findViewById(R.id.text_merk_produk);
            mEditProduk = (ImageView) itemView.findViewById(R.id.edit_produk);

            mDeleteProduk = (ImageView) itemView.findViewById(R.id.delete_produk);
            mItemProdukLayout = itemView.findViewById(R.id.fragment_item_produk);
        }

        public void bind(Produk produk){
            mProduk = produk;
            mNamaProduk.setText(mProduk.getNama());
            try{
                mFotoProduk.setImageBitmap(mPictureUtils.convertToImage(produk.getFoto()));
            }catch (Exception e){
            }
            mHargaProduk.setText("Rp. " + String.format("%,d", mProduk.getHarga()).replace(',', '.') + ",-");
            mJumlahProduk.setText("stok : " + mProduk.getJumlah());
            mMerkProduk.setText("(" + mProduk.getMerk() + ")");
            mEditProduk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = Fragment_edit_produk.newInstance(mProduk, dataToko);
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit(); // save the changes
                }
            });
            mDeleteProduk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog
                            .Builder(getContext())
                            .setTitle(getText(R.string.hapus_data))
                            .setMessage(getText(R.string.yakin_hapus_produk))
                            .setPositiveButton(getText(R.string.ya), (dialogInterface, i) -> {
                                mProdukViewModel.delete(String.valueOf(mProduk.getIdProduk()));
                                mProdukList.remove(mProduk);
                                updateUI();
                            })
                            .setNegativeButton(getText(R.string.tidak), null)
                            .show();
                }
            });
        }

    }

    private class ProdukAdapter extends RecyclerView.Adapter<ProdukHolder>{

        private List<Produk> mProdukList;

        public ProdukAdapter(List<Produk> produks){
            mProdukList = produks;
        }

        @Override
        public ProdukHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new ProdukHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(ProdukHolder holder, int position){
            Produk produk = mProdukList.get(position);
            holder.bind(produk);
        }

        @Override
        public int getItemCount(){
            return mProdukList.size();
        }

        public void filterList(ArrayList<Produk> filteredList) {
            mProdukList = filteredList;
            notifyDataSetChanged();
        }
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit(); // save the changes
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
