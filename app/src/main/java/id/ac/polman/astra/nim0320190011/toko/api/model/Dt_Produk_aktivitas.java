package id.ac.polman.astra.nim0320190011.toko.api.model;

import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;

public class Dt_Produk_aktivitas {
    @PrimaryKey
    @NonNull
    private int id;
    private int idAkt;
    private int idProduk;
    private int jumlah;
    private int harga;

    public Dt_Produk_aktivitas(int id, int idAkt, int idProduk, int jumlah, int harga) {
        this.id = id;
        this.idAkt = idAkt;
        this.idProduk = idProduk;
        this.jumlah = jumlah;
        this.harga = harga;
    }

    public Dt_Produk_aktivitas() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdAkt() {
        return idAkt;
    }

    public void setIdAkt(int idAkt) {
        this.idAkt = idAkt;
    }

    public int getIdProduk() {
        return idProduk;
    }

    public void setIdProduk(int idProduk) {
        this.idProduk = idProduk;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }
}
