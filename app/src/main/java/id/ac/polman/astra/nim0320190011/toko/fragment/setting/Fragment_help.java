package id.ac.polman.astra.nim0320190011.toko.fragment.setting;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import id.ac.polman.astra.nim0320190011.toko.R;

public class Fragment_help extends  Fragment{

    private ImageView mButtonBack;
    private TextView mInstagram_1;
    private TextView mInstagram_2;

    Uri uri;
    Intent likeIng;

    public static Fragment_help newInstance() {
        return new Fragment_help();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_help, container,false);

        mButtonBack = v.findViewById(R.id.back);
        mButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        mInstagram_1 = v.findViewById(R.id.ig_firda);
        mInstagram_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uri = Uri.parse("http://instagram.com/_u/" + mInstagram_1.getText().toString().substring(1));
                likeIng = new Intent(Intent.ACTION_VIEW, uri);

                likeIng.setPackage("com.instagram.android");

                try {
                    startActivity(likeIng);
                } catch (Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://instagram.com/"+ mInstagram_1.getText().toString().substring(1))));
                }
            }
        });

        mInstagram_2 = v.findViewById(R.id.ig_teddy);
        mInstagram_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uri = Uri.parse("http://instagram.com/_u/" + mInstagram_2.getText().toString().substring(1));
                likeIng = new Intent(Intent.ACTION_VIEW, uri);

                likeIng.setPackage("com.instagram.android");

                try {
                    startActivity(likeIng);
                } catch (Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://instagram.com/"+ mInstagram_2.getText().toString().substring(1))));
                }
            }
        });

        return v;
    }
}
