package id.ac.polman.astra.nim0320190011.toko.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import id.ac.polman.astra.nim0320190011.toko.R;
import id.ac.polman.astra.nim0320190011.toko.api.model.Toko;
import id.ac.polman.astra.nim0320190011.toko.fragment.profil.Fragment_profile;

public class Fragment_setting extends DialogFragment {
    private static final String TAG = "Fragment_setting";

    Toko mDataToko;

    TextView mProfilButton;
    TextView mTentang;
    TextView mBantuan;
    TextView mLaporkanError;
    TextView mKeluar;
    Activity context;

    
    
    public static Fragment_setting newInstance(Toko toko) {
        Fragment_setting frag = new Fragment_setting(toko);
        Bundle args = new Bundle();
        frag.setArguments(args);
        return frag;
    }

    private Fragment_setting(Toko mtoko){
        mDataToko = mtoko;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: ");
        View v = inflater.inflate(R.layout.fragment_setting, container, false);

        mProfilButton = v.findViewById(R.id.profil_button);
        mProfilButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: Profile");
                getDialog().dismiss();
                Fragment fragment = Fragment_profile.newInstance(mDataToko);
                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit(); // save the changes
            }
        });

        mTentang = v.findViewById(R.id.about_button);
        mTentang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mBantuan = v.findViewById(R.id.help_button);
        mBantuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mLaporkanError = v.findViewById(R.id.report_error_button);
        mLaporkanError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mKeluar = v.findViewById(R.id.logout_button);
        mKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog
                        .Builder(getContext())
                        .setTitle("Keluar")
                        .setMessage("Apakah anda yakin untuk keluar?")
                        .setPositiveButton("Ya", (dialogInterface, i) -> {
                            getDialog().dismiss();
                            context=getActivity();
                            PreferenceManager.getDefaultSharedPreferences(context.getBaseContext()).edit().clear().apply();
                            Fragment fragment = Fragment_login.newInstance();
                            FragmentManager fm = getFragmentManager();
                            FragmentTransaction fragmentTransaction = fm.beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_container, fragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit(); // save the changes
                        })
                        .setNegativeButton("Tidak", null)
                        .show();

            }
        });
        return v;
    }
}
