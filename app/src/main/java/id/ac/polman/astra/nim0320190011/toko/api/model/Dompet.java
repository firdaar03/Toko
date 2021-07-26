package id.ac.polman.astra.nim0320190011.toko.api.model;

import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;

public class Dompet {
    @PrimaryKey
    @NonNull
    private int idDompet;
    private int idToko;
    private int uang;
    private String creaby;
    private String creadate;
    private String modiby;
    private String modidate;

    public Dompet(int idDompet, int idToko, int uang, String creaby, String creadate, String modiby, String modidate) {
        this.idDompet = idDompet;
        this.idToko = idToko;
        this.uang = uang;
        this.creaby = creaby;
        this.creadate = creadate;
        this.modiby = modiby;
        this.modidate = modidate;
    }

    public Dompet(){

    }

    public int getIdDompet() {
        return idDompet;
    }

    public void setIdDompet(int idDompet) {
        this.idDompet = idDompet;
    }

    public int getIdToko() {
        return idToko;
    }

    public void setIdToko(int idToko) {
        this.idToko = idToko;
    }

    public int getUang() {
        return uang;
    }

    public void setUang(int uang) {
        this.uang = uang;
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

    public String getModiby() {
        return modiby;
    }

    public void setModiby(String modiby) {
        this.modiby = modiby;
    }

    public String getModidate() {
        return modidate;
    }

    public void setModidate(String modidate) {
        this.modidate = modidate;
    }
}
