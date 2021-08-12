package id.ac.polman.astra.nim0320190011.toko.fragment.profil;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import id.ac.polman.astra.nim0320190011.toko.R;
import id.ac.polman.astra.nim0320190011.toko.api.model.Toko;
import id.ac.polman.astra.nim0320190011.toko.api.viewmodel.Toko_view_model;
import id.ac.polman.astra.nim0320190011.toko.fragment.Fragment_login;

public class Fragment_profile_hapus extends DialogFragment {
    private static final String TAG = "Fragment_profile_hapus";

    private Toko mToko;
    private Toko_view_model mTokoViewModel;

    EditText mPassword;
    Button mButtonHapus;
    
    public Toko_view_model getTokoViewModel(){
        Log.i(TAG, "getTokoViewModelList: called");
        if(mTokoViewModel == null){
            mTokoViewModel = new ViewModelProvider(this)
                    .get(Toko_view_model.class);
        }
        Log.i(TAG, "getTokoViewModelList: called 2");
        return mTokoViewModel;
    }

    public static Fragment_profile_hapus newInstance(Toko toko){
        return new Fragment_profile_hapus(toko);
    }

    private Fragment_profile_hapus(Toko toko){
        mToko = toko;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTokoViewModel = getTokoViewModel();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile_hapus, container, false);
        
        mPassword = v.findViewById(R.id.password);
        
        mButtonHapus = v.findViewById(R.id.button_hapus);
        mButtonHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = mPassword.getText().toString();
                if(mToko.getPassword().equals(password)){
                        mTokoViewModel.delete(mToko);
                        getDialog().dismiss();

                        PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext()).edit().clear().apply();
                        Fragment fragment = Fragment_login.newInstance();
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fm.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, fragment);
                        fragmentTransaction.commit(); // save the changes
                        getFragmentManager().popBackStack();
                        getFragmentManager().popBackStack();
                        getFragmentManager().popBackStack();

                }else{
                    mPassword.setError(getText(R.string.field_verif_password_salah));
                }
                Log.i(TAG, "onClick: Hapus");
            }
        });
        
        return v;
    }
}
