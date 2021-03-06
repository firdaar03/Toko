package id.ac.polman.astra.nim0320190011.toko.fragment.dompet;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import id.ac.polman.astra.nim0320190011.toko.R;
import id.ac.polman.astra.nim0320190011.toko.api.model.Dompet;
import id.ac.polman.astra.nim0320190011.toko.api.model.Dompet_aktivitas;
import id.ac.polman.astra.nim0320190011.toko.api.model.Produk;
import id.ac.polman.astra.nim0320190011.toko.api.model.Toko;
import id.ac.polman.astra.nim0320190011.toko.api.viewmodel.Aktivitas_dompet_view_model;
import id.ac.polman.astra.nim0320190011.toko.fragment.DatePickerFragment;
import id.ac.polman.astra.nim0320190011.toko.fragment.Fragment_setting;
import id.ac.polman.astra.nim0320190011.toko.fragment.detail.Detail_aktivitas_dompet;
import id.ac.polman.astra.nim0320190011.toko.fragment.produk.Fragment_edit_produk;
import id.ac.polman.astra.nim0320190011.toko.fragment.produk.Fragment_produk;
import id.ac.polman.astra.nim0320190011.toko.fragment.profil.Fragment_profile_edit;

public class Fragment_dompet_aktivitas extends Fragment
    implements DatePickerFragment.Callbacks{
    private static final String TAG = "Fragment_dompet_aktivitas";
    private static final String DIALOG_DATE = "DialogDate";

    private static final int REQUEST_DATE = 0;

    private RecyclerView mRecyclerView;
    private AktivitasDompetAdapter mAktivitasDompetAdapter;
    private List<Dompet_aktivitas> mDompetAktivitasList;
    private Toko dataToko;

    private LinearLayout mTanggal1Button;
    private LinearLayout mTanggal2Button;
    private TextView mTanggal1View;
    private TextView mTanggal2View;

    private Aktivitas_dompet_view_model mAktivitasDompetViewModel;

    private androidx.swiperefreshlayout.widget.SwipeRefreshLayout mRefreshLayout;

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    public static Fragment_dompet_aktivitas newInstance(Toko in){
        return new Fragment_dompet_aktivitas(in);
    }

    private Fragment_dompet_aktivitas(Toko t){
        dataToko = t;
    }

    @SuppressLint("LongLogTag")
    private Aktivitas_dompet_view_model getAktivitasDompetViewModel(){
        Log.i(TAG, "getAktivitasDompetViewModel: ");
        if(mAktivitasDompetViewModel == null){
            mAktivitasDompetViewModel = new ViewModelProvider(this)
                    .get(Aktivitas_dompet_view_model.class);
        }
        return mAktivitasDompetViewModel;
    }

    @SuppressLint("LongLogTag")
    private void updateUI(){
        Log.i(TAG, "updateUI: ");
        mAktivitasDompetAdapter = new AktivitasDompetAdapter(mDompetAktivitasList);
        mRecyclerView.setAdapter(mAktivitasDompetAdapter);
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        mAktivitasDompetViewModel = getAktivitasDompetViewModel();
        mAktivitasDompetAdapter = new AktivitasDompetAdapter(new ArrayList<>());
        mDompetAktivitasList = new ArrayList<>();
    }

    @SuppressLint("LongLogTag")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: ");
        View v = inflater.inflate(R.layout.fragment_dompet_aktifitas, container, false);
        
        mRecyclerView = v.findViewById(R.id.aktivitas_dompet_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAktivitasDompetAdapter);

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
                //DatePickerFragment dialog = new DatePickerFragment();
                DatePickerFragment dialog =
                        DatePickerFragment.newInstance(date, 1);
                dialog.setTargetFragment(Fragment_dompet_aktivitas.this,REQUEST_DATE);
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
                //DatePickerFragment dialog = new DatePickerFragment();
                DatePickerFragment dialog =
                        DatePickerFragment.newInstance(date, 2);
                dialog.setTargetFragment(Fragment_dompet_aktivitas.this,REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });

        mRefreshLayout = v.findViewById(R.id.swiperefresh);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRefreshLayout.setRefreshing(false);
                refresh();
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
        mAktivitasDompetViewModel.getAktivitasByIdToko(dataToko.getIdToko(), mTanggal1View.getText().toString(), mTanggal2View.getText().toString())
                .observe(
                        getViewLifecycleOwner(), new Observer<List<Dompet_aktivitas>>() {
                            @Override
                            public void onChanged(List<Dompet_aktivitas> aktivitas) {
                                mDompetAktivitasList = aktivitas;
                                updateUI();
                            }
                        }
                );
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


    private class AktivitasDompetHolder extends RecyclerView.ViewHolder{

        private TextView mKeterangan;
        private TextView mNominal;
        private TextView mJam;
        private TextView mTanggal;
        private RelativeLayout mItemAktivitas;


        public AktivitasDompetHolder (LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.fragment_item_aktivitas_dompet, parent, false));

            mKeterangan = itemView.findViewById(R.id.keterangan_aktivitas_dompet);
            mNominal = itemView.findViewById(R.id.nominal);
            mJam = itemView.findViewById(R.id.jam_aktivitas_dompet);
            mTanggal = itemView.findViewById(R.id.tanggal_aktivitas_dompet);
            mItemAktivitas = itemView.findViewById(R.id.item_aktivitas);
        }

        @SuppressLint("LongLogTag")
        public void bind(Dompet_aktivitas aktivitas){
            switch (aktivitas.getKode_akt()){
                case 1 :
                    mKeterangan.setText(R.string.uang_masuk);
                    break;
                case 2 :
                    mKeterangan.setText(R.string.uang_keluar);
                    break;
                case 3 :
                    mKeterangan.setText(R.string.perubahan_kasir);
                    break;
                case 4 :
                    mKeterangan.setText(R.string.kosong_kasir);
                    break;
                case 5 :
                    mKeterangan.setText(R.string.penjualan);
                    break;
            }
            mNominal.setText("Rp " + String.format("%,d", aktivitas.getJumlah()).replace(',', '.') + ",-");

            mJam.setText(aktivitas.getCreadate().substring(11,16) + " WIB");
            mTanggal.setText(aktivitas.getCreadate().substring(0,10));

            mItemAktivitas.setOnClickListener(
                    new View.OnClickListener() {
                        @SuppressLint("LongLogTag")
                        @Override
                        public void onClick(View v) {
                            Detail_aktivitas_dompet fragment = Detail_aktivitas_dompet.newInstance(aktivitas);
                            FragmentManager fm = getFragmentManager();
                            fragment.show(fm,"Fragment Setting");
                        }
                    }
            );
        }

    }

    private class AktivitasDompetAdapter extends RecyclerView.Adapter<Fragment_dompet_aktivitas.AktivitasDompetHolder>{

        private List<Dompet_aktivitas> mAktivitas;

        public AktivitasDompetAdapter(List<Dompet_aktivitas> aktivitas){
            mAktivitas = aktivitas;
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public Fragment_dompet_aktivitas.AktivitasDompetHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new Fragment_dompet_aktivitas.AktivitasDompetHolder(layoutInflater, parent);
        }


        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onBindViewHolder(Fragment_dompet_aktivitas.AktivitasDompetHolder holder, int position){
            Dompet_aktivitas aktivitas = mAktivitas.get(position);
            holder.bind(aktivitas);
        }

        @Override
        public int getItemCount(){
            return mAktivitas.size();
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

