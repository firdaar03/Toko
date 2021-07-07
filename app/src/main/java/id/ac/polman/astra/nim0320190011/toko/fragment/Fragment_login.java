package id.ac.polman.astra.nim0320190011.toko.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import id.ac.polman.astra.nim0320190011.toko.R;

public class Fragment_login extends Fragment {
    private static final String TAG = "Fragment_login";

    public static Fragment_login newInstance(){
        return new Fragment_login();
    }
    
    private TextView mCreate_akun;
    private Button mLoginButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login, container, false);

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
                mCallbacks.onLoginButtonClicked();
                Log.i(TAG, "onClick: Login");
            }
        });
        return view;
    }

    public interface Callbacks{
        public void onCreateAkunClicked();
        public void onLoginButtonClicked();
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
