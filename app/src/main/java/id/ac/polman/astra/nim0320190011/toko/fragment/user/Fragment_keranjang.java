package id.ac.polman.astra.nim0320190011.toko.fragment.user;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.ac.polman.astra.nim0320190011.toko.R;
import id.ac.polman.astra.nim0320190011.toko.api.model.Produk;

public class Fragment_keranjang extends Fragment {

    private Button mHapus;
    private Button mSimpan;

    



    private class KeranjangHolder extends RecyclerView.ViewHolder{

        private TextView mNamaProduk;
        private TextView mJumlahProduk;
        private TextView mHargaProduk;
        private TextView mTotalProduk;
        private ConstraintLayout mItemPutProduk;
        private Produk mProduk;
        private int totalProduk;

        public KeranjangHolder (LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.fragment_item_put_produk, parent, false));

            mNamaProduk = (TextView) itemView.findViewById(R.id.nama_produk);
            mJumlahProduk = (TextView) itemView.findViewById(R.id.jumlah_produk);
            mHargaProduk = (TextView) itemView.findViewById(R.id.harga_produk);
            mTotalProduk = (TextView) itemView.findViewById(R.id.total_produk);

            mItemPutProduk = itemView.findViewById(R.id.fragment_item_put_produk);
        }

        public void bind(Produk produk) {
            mProduk = produk;
            mNamaProduk.setText(mProduk.getNama());
            mJumlahProduk.setText(mProduk.getJumlah() + "");
            mHargaProduk.setText("Rp. " + String.format("%,d", mProduk.getHarga()).replace(',', '.') + ",-");
            totalProduk = mProduk.getJumlah() * mProduk.getHarga();
            mTotalProduk.setText("Rp. " + String.format("%,d", totalProduk).replace(',', '.') + ",-");
        }
    }

    private class KeranjangAdapter extends RecyclerView.Adapter<KeranjangHolder> {

        private List<Produk> mProdukList;

        public KeranjangAdapter(List<Produk> produks) {
            mProdukList = produks;
        }


        @NonNull
        @Override
        public KeranjangHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new KeranjangHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull KeranjangHolder holder, int position) {
            Produk produk = mProdukList.get(position);
            holder.bind(produk);
        }

        @Override
        public int getItemCount() {
            return mProdukList.size();
        }
    }
}
