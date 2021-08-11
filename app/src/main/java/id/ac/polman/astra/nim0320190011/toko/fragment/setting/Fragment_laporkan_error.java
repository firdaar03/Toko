package id.ac.polman.astra.nim0320190011.toko.fragment.setting;

import android.os.Bundle;
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
import androidx.fragment.app.Fragment;

import id.ac.polman.astra.nim0320190011.toko.R;

public class Fragment_laporkan_error extends Fragment{

    private ImageView mButtonBack;
    private EditText mLaporan;
    private Button nKirim;

    public static Fragment_laporkan_error newInstance() {
        return new Fragment_laporkan_error();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_report_error, container,false);

        mButtonBack = v.findViewById(R.id.back);
        mButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        mLaporan = v.findViewById(R.id.laporan_text);

        nKirim = v.findViewById(R.id.btn_kirim);
        nKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mLaporan.getText().toString().equals("")){
                    mLaporan.setError(getText(R.string.tidak_boleh_kosong));
                }else{
                    mLaporan.setText(getText(R.string.behasil_mengirim));
                    Toast.makeText(getContext(),getText(R.string.behasil_mengirim), Toast.LENGTH_LONG)
                            .show();
                    Log.i("AA", "onClick: Apakek");
                }
            }
        });
        return v;
    }
}
