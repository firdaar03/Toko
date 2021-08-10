package id.ac.polman.astra.nim0320190011.toko.fragment.user;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import id.ac.polman.astra.nim0320190011.toko.MapsFragment;
import id.ac.polman.astra.nim0320190011.toko.R;
import id.ac.polman.astra.nim0320190011.toko.Utils.PictureUtils;
import id.ac.polman.astra.nim0320190011.toko.api.model.Produk;
import id.ac.polman.astra.nim0320190011.toko.api.model.Toko;
import id.ac.polman.astra.nim0320190011.toko.api.viewmodel.Produk_view_model;
import id.ac.polman.astra.nim0320190011.toko.api.viewmodel.Toko_view_model;

public class Fragment_toko_user extends Fragment implements LocationListener{
    private static final String TAG = "Fragment_toko_user";

    Toko_view_model mTokoViewModel;
    Produk_view_model mProdukViewModel;
    private List<Toko> mTokoList;
    private List<LatLng> mLatLngList;
    private List<String> mTitle;

    PictureUtils mPictureUtils;

    private LocationManager locationManager;
    private LocationListener locationListener;
    private LatLng mLatLng = null;

    private RecyclerView mTokoRecyclerview;
    private TokoAdapter mTokoAdapter;

    private EditText mCariToko;
    private ImageView mBack;
    private ImageView mButton_map;


    public Fragment_toko_user() {

    }

    public static Fragment_toko_user newInstance() {
        return new Fragment_toko_user();
    }

    private void updateUI(List<Toko> a) {
        Log.i(TAG, "updateUI called");
        mTokoAdapter = new TokoAdapter(a);
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

    public Toko_view_model getTokoViewModel() {
        Log.i(TAG, "getTokoViewModelList: called");
        if (mTokoViewModel == null) {
            mTokoViewModel = new ViewModelProvider(this)
                    .get(Toko_view_model.class);
        }
        Log.i(TAG, "getTokoViewModelList: called 2");
        return mTokoViewModel;
    }

    public Produk_view_model getProdukViewModel() {
        Log.i(TAG, "getProdukViewModelList: called");
        if (mProdukViewModel == null) {
            mProdukViewModel = new ViewModelProvider(this)
                    .get(Produk_view_model.class);
        }
        Log.i(TAG, "getProdukViewModelList: called 2");
        return mProdukViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(TAG, "onCreate: ");
        mTokoViewModel = getTokoViewModel();
        mProdukViewModel = getProdukViewModel();
        mTokoList = new ArrayList<>();
        mPictureUtils = new PictureUtils();
        mTokoAdapter = new TokoAdapter(new ArrayList<>());

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION);
        if(permissionCheck != PackageManager.PERMISSION_GRANTED) {
            // ask permissions here using below code
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }
        try{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }catch (Exception e){
        }
        mLatLngList = new ArrayList<>();
        mTitle = new ArrayList<>();
    }

    private void pengurutanTerdekat(List<Toko> in){
//        Log.i(TAG, "pengurutanTerdekat Latitude:" + mLatLng.latitude + ", Longitude:" + mLatLng.longitude);
        mLatLngList = new ArrayList<>();
        mTitle = new ArrayList<>();
        for(Toko t : in){
            LatLng lat = new LatLng(0,0);
            if(Geocoder.isPresent()){
                try {
                    String location = t.getAlamatToko();
                    Geocoder gc = new Geocoder(getContext());
                    List<Address> addresses= gc.getFromLocationName(location, 1); // get the found Address Objects

                    List<LatLng> ll = new ArrayList<LatLng>(addresses.size()); // A list to save the coordinates if they are available
                    for(Address a : addresses){
                        if(a.hasLatitude() && a.hasLongitude()){
                            lat = new LatLng(a.getLatitude(), a.getLongitude());
                        }
                    }
                } catch (Exception e) {
                    // handle the exception
                }
            }
//            Log.i(TAG, "pengurutanTerdekat: x lat " + lat.latitude);
//            Log.i(TAG, "pengurutanTerdekat: y lat " + lat.longitude);
            long x = (long) Math.sqrt(Math.pow(lat.latitude - mLatLng.latitude, 2));
            long y = (long) Math.sqrt(Math.pow(lat.longitude - mLatLng.longitude, 2));
            long miring = (long) Math.sqrt(x+y);
//            Log.i(TAG, "pengurutanTerdekat: x " + x);
//            Log.i(TAG, "pengurutanTerdekat: y " + y);
//            Log.i(TAG, "pengurutanTerdekat: Miringnya " + miring );
//            Log.i(TAG, "pengurutanTerdekat: Toko alamat   " + t.getAlamatToko());
            t.setStatus((int) miring);

            mLatLngList.add(lat);
            mTitle.add(t.getNama_pemilik());
        }
        Toko[] output = new Toko[in.size()];
        Toko temp;
//        Log.i(TAG, "pengurutanTerdekat: size " + in.size());
//        Log.i(TAG, "pengurutanTerdekat: output size " + output.length);
        int x = 0;
        for(Toko t : in){
            output[x] = t;
            x++;
        }

        for(int i = 0; i < output.length; i++){
//            Log.i(TAG, "pengurutanTerdekat: Perulangan i ke " + i);
            for(int j = 0; j < output.length; j++){
//                Log.i(TAG, "pengurutanTerdekat: perulangan j " + j);
                if(output[i].getStatus() < output[j].getStatus()) {
                    temp = output[j];
                    output[j] = output[i];
                    output[i] = temp;
                }
            }
        }

        mTokoList = new ArrayList<>();
        for(int i = 0; i < output.length; i++){
            Log.i(TAG, "pengurutanTerdekat: asd  i ke " + i + " : " + output[i].getStatus());
            mTokoList.add(output[i]);
        }
        if(mLatLng != null){
            mLatLngList.add(mLatLng);
            mButton_map.setEnabled(true);
        }
        updateUI(mTokoList);
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

        mButton_map = v.findViewById(R.id.button_map);
        mButton_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mLatLng == null || mLatLngList.size() == 0){
                    Toast.makeText(getContext(), "Mohon tunggu", Toast.LENGTH_LONG)
                            .show();
                    return;
                }
                MapsFragment fragment = MapsFragment.newInstance(mLatLngList, mTitle);
                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit(); // save the changes
            }
        });
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
                        if(mLatLng != null){
                            pengurutanTerdekat(mTokoList);
                        }
//                        updateUI();
                        Log.i(TAG, "Got toko: " + tokos.size());
                    }
                }
        );
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        if(mLatLng == null){
           mLatLng = new LatLng(location.getLatitude(), location.getLongitude());
            pengurutanTerdekat(mTokoList);
            Log.i(TAG, "Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

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
                Log.e(TAG, "onCreateView: ERROR PASANG PP");
            }
            mNamaPemilik.setText(mToko.getNama_pemilik().toUpperCase() + "'S STORE");

            mAlamat.setText(mToko.getAlamatToko());
            mAlamat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse("https://www.google.com/maps/place/" + mToko.getAlamatToko()));
                    startActivity(intent);
                }
            });

            mTelepon.setText(mToko.getNo_telfon());
            mTelepon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "Telepon");
                    Uri number = Uri.parse("tel:"+mToko.getNo_telfon());
                    final Intent dial = new Intent(Intent.ACTION_DIAL,
                            number);
                    startActivity(dial);
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

    @Override
    public void onPause() {
        Log.i(TAG, "onPause: ");
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
    }
}
