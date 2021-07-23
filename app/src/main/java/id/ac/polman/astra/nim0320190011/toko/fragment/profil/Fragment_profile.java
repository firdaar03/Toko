package id.ac.polman.astra.nim0320190011.toko.fragment.profil;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.text.SimpleDateFormat;
import java.util.Date;

import id.ac.polman.astra.nim0320190011.toko.R;
import id.ac.polman.astra.nim0320190011.toko.Utils.PictureUtils;
import id.ac.polman.astra.nim0320190011.toko.api.model.Toko;
import id.ac.polman.astra.nim0320190011.toko.api.viewmodel.Toko_view_model;

public class Fragment_profile extends Fragment {
    private static final String TAG = "Fragment_profile";

    private Toko mToko;
    private Toko_view_model mTokoViewModel;
    private PictureUtils mPictureUtils;

    private TextView mNama;
    private TextView mEmail;
    private TextView mTelefon;
    private TextView mJenisKelamin;
    private TextView mTempatLahir;
    private TextView mTanggalLahir;
    private TextView mAlamat;
    private TextView mAlamatToko;
    private TextView mNIK;
    private TextView mUsername;

    private com.mikhaellopez.circularimageview.CircularImageView mFotoDiriView;
    private ImageView mFotoKTPView;
    private ImageView mFotoTokoView;

    private TextView mEditButton;
    private TextView mHapusButton;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static Fragment_profile newInstance(Toko toko){
        return new Fragment_profile(toko);
    }

    private Fragment_profile(Toko toko){
        mToko = toko;
        Log.i(TAG, "Fragment_profile: " + mToko.getNama_pemilik());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPictureUtils = new PictureUtils();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        mNama = v.findViewById(R.id.nama_pemilik);
        mEmail = v.findViewById(R.id.email);
        mTelefon = v.findViewById(R.id.telepon);
        mJenisKelamin = v.findViewById(R.id.jenis_kelamin);
        mTempatLahir = v.findViewById(R.id.tempat_lahir);
        mTanggalLahir = v.findViewById(R.id.tanggal_lahir);
        mAlamat = v.findViewById(R.id.alamat);
        mAlamatToko = v.findViewById(R.id.alamat_toko);
        mNIK = v.findViewById(R.id.NIK);
        mUsername = v.findViewById(R.id.username);

        mFotoDiriView = v.findViewById(R.id.foto_diri);
        mFotoKTPView = v.findViewById(R.id.foto_ktp);
        mFotoTokoView = v.findViewById(R.id.foto_toko);

        mEditButton = v.findViewById(R.id.edit_button);
        mEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = Fragment_profile_edit.newInstance(mToko);
                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit(); // save the changes
            }
        });
        mHapusButton = v.findViewById(R.id.hapus_button);
        mHapusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        updateUI();
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void updateUI() {
        mNama.setText(mToko.getNama_pemilik());
        mEmail.setText(mToko.getEmail());
        mTelefon.setText(mToko.getNo_telfon());
        if(mToko.getJenis_kelamin().equals("1")){
            mJenisKelamin.setText("Laki-laki");
        }else{
            mJenisKelamin.setText("Perempuan");
        }

        Date tanggallahir = new Date();
        try {
            tanggallahir = sdf.parse(mToko.getTanggal_lahir());
        }catch (Exception e){

        }
        String tanggal = sdf.format(tanggallahir);
        mTempatLahir.setText(mToko.getTempat_lahir());
        mTanggalLahir.setText(tanggal);
        mAlamat.setText(mToko.getAlamat());
        mAlamatToko.setText(mToko.getAlamatToko());
        mNIK.setText(mToko.getNIK());
        mUsername.setText(mToko.getUsername());

        mFotoDiriView.setImageBitmap(mPictureUtils.convertToImage(mToko.getFoto_diri()));
        mFotoKTPView.setImageBitmap(mPictureUtils.convertToImage(mToko.getFoto_KTP()));
        mFotoTokoView.setImageBitmap(mPictureUtils.convertToImage(mToko.getFoto_toko()));
    }
}
