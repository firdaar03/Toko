package id.ac.polman.astra.nim0320190011.toko.api.model;

import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;

public class Produk_aktivitas {
    @PrimaryKey
    @NonNull
    private int idAkt;
    private int idProduk;
    private int kode_akt;
    private int jumlah;
    private String keterangan;
    private String creaby;
    private String creadate;

    public Produk_aktivitas() {
    }

    public Produk_aktivitas(int idAkt, int idProduk, int kode_akt, int jumlah, String keterangan, String creaby, String creadate) {
        this.idAkt = idAkt;
        this.idProduk = idProduk;
        this.kode_akt = kode_akt;
        this.jumlah = jumlah;
        this.keterangan = keterangan;
        this.creaby = creaby;
        this.creadate = creadate;
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

    public int getKode_akt() {
        return kode_akt;
    }

    public void setKode_akt(int kode_akt) {
        this.kode_akt = kode_akt;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getCreaby() {
        return creaby;
    }

    public void setCreaby(String creaby) {
        this.creaby = creaby;
    }

    public String getCreadate() {
        return creadate;
    }

    public void setCreadate(String creadate) {
        this.creadate = creadate;
    }
}
