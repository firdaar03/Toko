package id.ac.polman.astra.nim0320190011.toko.api.model;

import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;

public class Dompet_aktivitas {
    @PrimaryKey
    @NonNull
    private int idAkt;
    private int idDompet;
    private int kode_akt;
    private int jumlah;
    private String keterangan;
    private String creaby;
    private String creadate;

    public Dompet_aktivitas(int idAkt, int idDompet, int kode_akt, int jumlah, String keterangan, String creaby, String creadate) {
        this.idAkt = idAkt;
        this.idDompet = idDompet;
        this.kode_akt = kode_akt;
        this.jumlah = jumlah;
        this.keterangan = keterangan;
        this.creaby = creaby;
        this.creadate = creadate;
    }

    public Dompet_aktivitas(){

    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public int getIdAkt() {
        return idAkt;
    }

    public void setIdAkt(int idAkt) {
        this.idAkt = idAkt;
    }

    public int getIdDompet() {
        return idDompet;
    }

    public void setIdDompet(int idDompet) {
        this.idDompet = idDompet;
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
