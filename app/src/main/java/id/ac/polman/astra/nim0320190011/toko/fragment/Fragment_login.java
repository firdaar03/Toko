package id.ac.polman.astra.nim0320190011.toko.fragment;

import android.content.Context;
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
import androidx.lifecycle.ViewModelProvider;

import id.ac.polman.astra.nim0320190011.toko.R;
import id.ac.polman.astra.nim0320190011.toko.api.model.Toko;
import id.ac.polman.astra.nim0320190011.toko.api.viewmodel.Toko_view_model;
import id.ac.polman.astra.nim0320190011.toko.api.viewmodel.Toko_view_model_list;

public class Fragment_login extends Fragment {
    private static final String TAG = "Fragment_login";

    private TextView mCreate_akun;
    private Button mLoginButton;
    private EditText mUsername;
    private EditText mPassword;

    private Toko_view_model mTokoViewModel;

    public Toko_view_model getTokoViewModel(){
        Log.i(TAG, "getTokoViewModelList: called");
        if(mTokoViewModel == null){
            mTokoViewModel = new ViewModelProvider(this)
                    .get(Toko_view_model.class);
        }
        Log.i(TAG, "getTokoViewModelList: called 2");
        return mTokoViewModel;
    }

    public static Fragment_login newInstance(){
        return new Fragment_login();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: called");
        super.onCreate(savedInstanceState);

//        mTokoViewModel = getTokoViewModel();
        mTokoViewModel = getTokoViewModel();
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

        mLoginButton = (Button) view.findViewById(R.id.login_button);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mUsername.getText().toString();
                String password = mPassword.getText().toString();
                Toko user = mTokoViewModel.checklogin(username, password);
                if(user != null){
                    mCallbacks.onLoginButtonClicked(user);
                }else {
                    Toast.makeText(getContext(), "Username atau password tidak ditemukan",
                            Toast.LENGTH_SHORT).show();
                }
                Log.i(TAG, "onClick: Login");
            }
        });
        return view;
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
