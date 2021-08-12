package id.ac.polman.astra.nim0320190011.toko.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import id.ac.polman.astra.nim0320190011.toko.MapsFragment;
import id.ac.polman.astra.nim0320190011.toko.R;
import id.ac.polman.astra.nim0320190011.toko.api.model.Toko;
import id.ac.polman.astra.nim0320190011.toko.api.viewmodel.Login_view_model;
import id.ac.polman.astra.nim0320190011.toko.api.viewmodel.Toko_view_model;
import id.ac.polman.astra.nim0320190011.toko.api.viewmodel.Toko_view_model_list;
import id.ac.polman.astra.nim0320190011.toko.fragment.produk.Fragment_put_produk;
import id.ac.polman.astra.nim0320190011.toko.fragment.user.Fragment_toko_user;

import static android.content.Context.MODE_PRIVATE;

public class Fragment_login extends Fragment {
    private static final String TAG = "Fragment_login";

    private TextView mCreate_akun;
    private TextView mLoginButton;
    private TextView mGuest;
    private EditText mUsername;
    private EditText mPassword;

    private Toko user;

    private Login_view_model mLoginViewModel;
    private Toko_view_model mTokoViewModel;
    private List<Toko> dataToko;

    public Login_view_model getLoginViewModel(){
        Log.i(TAG, "getTokoViewModelList: called");
        if(mLoginViewModel == null){
            mLoginViewModel = new ViewModelProvider(this)
                    .get(Login_view_model.class);
        }
        Log.i(TAG, "getTokoViewModelList: called 2");
        return mLoginViewModel;
    }


    public static Fragment_login newInstance(){
        return new Fragment_login();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: called");
        super.onCreate(savedInstanceState);

        mLoginViewModel = getLoginViewModel();
        user = new Toko();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: called");
        View view = inflater.inflate(R.layout.fragment_login, container, false);


        mUsername = (EditText) view.findViewById(R.id.username);
        mPassword = (EditText) view.findViewById(R.id.password);

        mCreate_akun = (TextView) view.findViewById(R.id.text_create_akun);
        mCreate_akun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallbacks.onCreateAkunClicked();
                Log.i(TAG, "onClick: Create akun");
            }
        });

        mLoginButton = view.findViewById(R.id.login_button);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mUsername.getText().toString();
                String password = mPassword.getText().toString();
                try {
                    try {
                        Thread.sleep(100);
                    }catch (InterruptedException ie){
                        Thread.currentThread().interrupt();
                    }
                    mLoginViewModel.login(username, password).observe(
                            getViewLifecycleOwner(), new Observer<Toko>() {
                                @Override
                                public void onChanged(Toko toko) {
                                    user = toko;
                                    check();
                                }
                            }

                    );
                }catch (Exception e){
                    Toast.makeText(getContext(), "Mohon tunggu!",
                            Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onClick: error", e);
                }

            }
        });

        mGuest = view.findViewById(R.id.masuk_guest);
        mGuest.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Fragment fragment = Fragment_toko_user.newInstance();
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fm.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit(); // save the changes
                    }
                }
        );
        return view;
    }

    private void check(){
        if(user == null){
            Toast.makeText(getContext(), "Username atau password tidak ditemukan",
                    Toast.LENGTH_SHORT).show();
            Log.i(TAG, "onClick: Login TeError");
        }else{
            mUsername.setText("");
            mPassword.setText("");
            mCallbacks.onLoginButtonClicked(user);
        }
    }
    public interface Callbacks{
        public void onCreateAkunClicked();
        public void onLoginButtonClicked(Toko user);
    }

    private Callbacks mCallbacks = null;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mCallbacks = (Callbacks) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }
}
