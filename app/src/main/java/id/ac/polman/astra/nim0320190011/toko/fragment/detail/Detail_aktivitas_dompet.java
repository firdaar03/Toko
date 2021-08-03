package id.ac.polman.astra.nim0320190011.toko.fragment.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.text.SimpleDateFormat;

import id.ac.polman.astra.nim0320190011.toko.R;
import id.ac.polman.astra.nim0320190011.toko.api.model.Dompet_aktivitas;

public class Detail_aktivitas_dompet extends DialogFragment {
    private static final String TAG = "detail_aktivitas";

    private Dompet_aktivitas mAktivitas;

    private TextView mKeteranganAktivitas;
    private TextView mTanggal;
    private TextView mWaktu;
    private TextView mNominal;
    private TextView mKeterangan;

    private Button mButtonKembali;

    public static Detail_aktivitas_dompet newInstance(Dompet_aktivitas aktivitas){
        return new Detail_aktivitas_dompet(aktivitas);
    }

    private Detail_aktivitas_dompet(Dompet_aktivitas a){
        mAktivitas = a;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dompet_aktifitas_detail, container, false);

        mKeteranganAktivitas = v.findViewById(R.id.keterangan_aktivitas);
        mTanggal = v.findViewById(R.id.tanggal);
        mWaktu = v.findViewById(R.id.waktu);
        mNominal = v.findViewById(R.id.nominal);
        mKeterangan = v.findViewById(R.id.keterangan);

        mButtonKembali = v.findViewById(R.id.button_kembali);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mButtonKembali.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getDialog().dismiss();
                    }
                }
        );
        updateUI();
    }

    private void updateUI(){

        switch (mAktivitas.getKode_akt()){
            case 1 :
                mKeteranganAktivitas.setText(R.string.uang_masuk);
                break;
            case 2 :
                mKeteranganAktivitas.setText(R.string.uang_keluar);
                break;
            case 3 :
                mKeteranganAktivitas.setText(R.string.perubahan_kasir);
                break;
            case 4 :
                mKeteranganAktivitas.setText(R.string.kosong_kasir);
                break;
            case 5 :
                mKeteranganAktivitas.setText(R.string.penjualan);
                break;
        }
        mWaktu.setText(mAktivitas.getCreadate().substring(11,16) + " WIB");
        mTanggal.setText(mAktivitas.getCreadate().substring(0,10));
        mNominal.setText("Rp " + String.format("%,d", mAktivitas.getJumlah()).replace(',', '.') + ",-");
        mKeterangan.setText(mAktivitas.getKeterangan());
    }
}
