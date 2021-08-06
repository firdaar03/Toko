package id.ac.polman.astra.nim0320190011.toko.fragment.produk;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import id.ac.polman.astra.nim0320190011.toko.R;
import id.ac.polman.astra.nim0320190011.toko.api.model.Produk_aktivitas;
import id.ac.polman.astra.nim0320190011.toko.api.model.Toko;
import id.ac.polman.astra.nim0320190011.toko.api.viewmodel.Aktivitas_produk_view_model;
import id.ac.polman.astra.nim0320190011.toko.fragment.DatePickerFragment;
import id.ac.polman.astra.nim0320190011.toko.fragment.detail.Detail_aktivitas_dompet;
import id.ac.polman.astra.nim0320190011.toko.fragment.detail.Detail_aktivitas_produk;
import id.ac.polman.astra.nim0320190011.toko.fragment.dompet.Fragment_dompet_aktivitas;

public class Fragment_produk_aktivitas extends Fragment
        implements DatePickerFragment.Callbacks {
    private static final String TAG = "Fragment_produk_aktivitas";
    private static final String DIALOG_DATE = "DialogDate";

    private static final int REQUEST_DATE = 0;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    private RecyclerView mRecyclerView;
    private List<Produk_aktivitas> mProdukAktivitasList;
    private AktivitasProdukAdapter mAktivitasProdukAdapter;
    private Toko dataToko;

    private LinearLayout mTanggal1Button;
    private LinearLayout mTanggal2Button;
    private TextView mTanggal1View;
    private TextView mTanggal2View;

    private Aktivitas_produk_view_model mAktivitasProdukViewModel;

    public static Fragment_produk_aktivitas newInstance(Toko t){
        return new Fragment_produk_aktivitas(t);
    }

    private Fragment_produk_aktivitas(Toko t){
        dataToko = t;
    }

    @SuppressLint("LongLogTag")
    private Aktivitas_produk_view_model getAktivitasProdukViewModel(){
        Log.i(TAG, "getAktivitasProdukViewModel: ");
        if (mAktivitasProdukViewModel == null){
            mAktivitasProdukViewModel = new ViewModelProvider(this)
                    .get(Aktivitas_produk_view_model.class);
        }
        return mAktivitasProdukViewModel;
    }

    @SuppressLint("LongLogTag")
    private void updateUI() {
        Log.i(TAG, "updateUI: ");
        mAktivitasProdukAdapter = new AktivitasProdukAdapter(mProdukAktivitasList);
        mRecyclerView.setAdapter(mAktivitasProdukAdapter);
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        mAktivitasProdukViewModel = getAktivitasProdukViewModel();
        mProdukAktivitasList = new ArrayList<>();
        mAktivitasProdukAdapter = new AktivitasProdukAdapter(new ArrayList<>());
    }

    @SuppressLint("LongLogTag")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: ");
        View v = inflater.inflate(R.layout.fragment_produk_aktivitas, container, false);

        mRecyclerView = v.findViewById(R.id.aktivitas_produk_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        mTanggal1View = v.findViewById(R.id.search_tanggal1_view);
        mTanggal1View.setText(formatter.format(cal.getTime()));

        mTanggal2View = v.findViewById(R.id.search_tanggal2_view);
        mTanggal2View.setText(formatter.format(new Date()));

        mTanggal1Button = v.findViewById(R.id.search_tanggal1);
        mTanggal1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date = new Date();
                try {
                    date = formatter.parse(mTanggal1View.getText().toString());
                }catch (Exception e){

                }
                FragmentManager manager = getParentFragmentManager();
                DatePickerFragment dialog =
                        DatePickerFragment.newInstance(date, 1);
                dialog.setTargetFragment(Fragment_produk_aktivitas.this,REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });

        mTanggal2Button = v.findViewById(R.id.search_tanggal2);
        mTanggal2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date = new Date();
                try {
                    date = formatter.parse(mTanggal2View.getText().toString());
                }catch (Exception e){

                }
                FragmentManager manager = getParentFragmentManager();
                DatePickerFragment dialog =
                        DatePickerFragment.newInstance(date, 2);
                dialog.setTargetFragment(Fragment_produk_aktivitas.this,REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });

        return v;

    }

    @SuppressLint("LongLogTag")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "onViewCreated: ");

        refresh();
    }

    public void refresh(){
        mAktivitasProdukViewModel.getAktivitasByIdTokoandTanggal(dataToko.getIdToko(), mTanggal1View.getText().toString(), mTanggal2View.getText().toString())
                .observe(getViewLifecycleOwner(), new Observer<List<Produk_aktivitas>>() {
                    @Override
                    public void onChanged(List<Produk_aktivitas> aktivitas) {
                        mProdukAktivitasList = aktivitas;
                        updateUI();
                    }
                });
    }

    @Override
    public void onDateSelected(Date date) {

    }

    @Override
    public void onDateSelected(Date date, int key) {
        try{
            if(key == 1){
                if(date.compareTo(formatter.parse(mTanggal2View.getText().toString())) <= 0){
                    mTanggal1View.setText(formatter.format(date));
                }else{
                    Toast.makeText(getContext(), "Pengisian field tidak tepat", Toast.LENGTH_SHORT)
                            .show();
                }
            }else{
                if(date.compareTo(formatter.parse(mTanggal1View.getText().toString())) >= 0){
                    mTanggal2View.setText(formatter.format(date));
                }else {
                    Toast.makeText(getContext(), "Pengisian field tidak tepat", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        }catch (Exception e){

        }
        refresh();
    }

    private class AktivitasProdukHolder extends RecyclerView.ViewHolder{
        private TextView mKeterangan;
        private TextView mJumlah;
        private TextView mJam;
        private TextView mTanggal;
        private RelativeLayout mItemAktivitas;

        public AktivitasProdukHolder (LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.fragment_item_aktivitas_produk, parent, false));

            mKeterangan = itemView.findViewById(R.id.keterangan_aktivitas_produk);
            mJumlah = itemView.findViewById(R.id.jumlah_nominal);
            mJam = itemView.findViewById(R.id.jam_aktivitas_produk);
            mTanggal = itemView.findViewById(R.id.tanggal_aktivitas_produk);
            mItemAktivitas = itemView.findViewById(R.id.item_aktivitas);
        }

        @SuppressLint("LongLogTag")
        public void bind(Produk_aktivitas aktivitas) {
            switch (aktivitas.getKode_akt()) {
                case 0:
                    mKeterangan.setText(R.string.pengambilan);
                    break;
                case 1:
                    mKeterangan.setText(R.string.penjualan);
                    break;
            }

            mJumlah.setText("Rp " + String.format("%,d", aktivitas.getJumlah()).replace(',', '.') + ",-");

            mJam.setText(aktivitas.getCreadate().substring(11,16) + " WIB");
            mTanggal.setText(aktivitas.getCreadate().substring(0,10));

            mItemAktivitas.setOnClickListener(
                    new View.OnClickListener() {
                        @SuppressLint("LongLogTag")
                        @Override
                        public void onClick(View v) {
                            Log.i(TAG, "Detail: ");
                            Detail_aktivitas_produk fragment = Detail_aktivitas_produk.newInstance(aktivitas);
                            FragmentManager fm = getFragmentManager();
                            fragment.show(fm,"Fragment Detail_aktivitas_produk");
                        }
                    }
            );
        }

    }

    private class AktivitasProdukAdapter extends RecyclerView.Adapter<Fragment_produk_aktivitas.AktivitasProdukHolder>{

        private List<Produk_aktivitas> mProduk_aktivitas;

        public AktivitasProdukAdapter(List<Produk_aktivitas> aktivitas){
            mProduk_aktivitas = aktivitas;
        }

        @NonNull
        @Override
        public Fragment_produk_aktivitas.AktivitasProdukHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new Fragment_produk_aktivitas.AktivitasProdukHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull Fragment_produk_aktivitas.AktivitasProdukHolder holder, int position) {
            Produk_aktivitas aktivitas = mProduk_aktivitas.get(position);
            holder.bind(aktivitas);
        }

        @Override
        public int getItemCount() {
            return mProduk_aktivitas.size();
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

