package id.ac.polman.astra.nim0320190011.toko.model;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
public class Toko {
    @PrimaryKey
    @NonNull
    private UUID mId_toko;
    private String mUsername;
    private String mNama_pemilik;
    private String mEmail;
    private String mJenis_kelamin;
    private String mTempat_lahir;
    private Date mTanggal_lahir;
    private String mAlamat;
    private String mNIK;
    private String mFoto_KTP;
    private String mFoto_diri;
    private String mFoto_toko;
    private String mCreaby;
    private LocalDateTime mCreadate;
    private String mModiby;
    private LocalDateTime mModidate;
    private LocalDateTime mLast_login;
    private int mStatus;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Toko(){
        mId_toko = UUID.randomUUID();
        mUsername = "";
        mNama_pemilik  = "";
        mEmail = "";
        mJenis_kelamin = "";
        mTempat_lahir = "";
        mTanggal_lahir = new Date();
        mAlamat = "";
        mNIK = "";
        mFoto_KTP = "";
        mFoto_diri = "";
        mFoto_toko = "";
        mCreaby  = "";
        mCreadate = LocalDateTime.now();
        mModiby = "";
        mModidate = LocalDateTime.now();
        mLast_login = LocalDateTime.now();
        mStatus = 1;
    }

    @NonNull
    public UUID getId_toko() {
        return mId_toko;
    }

    public void setId_toko(@NonNull UUID id_toko) {
        mId_toko = id_toko;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getNama_pemilik() {
        return mNama_pemilik;
    }

    public void setNama_pemilik(String nama_pemilik) {
        mNama_pemilik = nama_pemilik;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getJenis_kelamin() {
        return mJenis_kelamin;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        mJenis_kelamin = jenis_kelamin;
    }

    public String getTempat_lahir() {
        return mTempat_lahir;
    }

    public void setTempat_lahir(String tempat_lahir) {
        mTempat_lahir = tempat_lahir;
    }

    public Date getTanggal_lahir() {
        return mTanggal_lahir;
    }

    public void setTanggal_lahir(Date tanggal_lahir) {
        mTanggal_lahir = tanggal_lahir;
    }

    public String getAlamat() {
        return mAlamat;
    }

    public void setAlamat(String alamat) {
        mAlamat = alamat;
    }

    public String getNIK() {
        return mNIK;
    }

    public void setNIK(String NIK) {
        mNIK = NIK;
    }

    public String getFoto_KTP() {
        return mFoto_KTP;
    }

    public void setFoto_KTP(String foto_KTP) {
        mFoto_KTP = foto_KTP;
    }

    public String getFoto_diri() {
        return mFoto_diri;
    }

    public void setFoto_diri(String foto_diri) {
        mFoto_diri = foto_diri;
    }

    public String getFoto_toko() {
        return mFoto_toko;
    }

    public void setFoto_toko(String foto_toko) {
        mFoto_toko = foto_toko;
    }

    public String getCreaby() {
        return mCreaby;
    }

    public void setCreaby(String creaby) {
        mCreaby = creaby;
    }

    public LocalDateTime getCreadate() {
        return mCreadate;
    }

    public void setCreadate(LocalDateTime creadate) {
        mCreadate = creadate;
    }

    public String getModiby() {
        return mModiby;
    }

    public void setModiby(String modiby) {
        mModiby = modiby;
    }

    public LocalDateTime getModidate() {
        return mModidate;
    }

    public void setModidate(LocalDateTime modidate) {
        mModidate = modidate;
    }

    public LocalDateTime getLast_login() {
        return mLast_login;
    }

    public void setLast_login(LocalDateTime last_login) {
        mLast_login = last_login;
    }

    public int getStatus() {
        return mStatus;
    }

    public void setStatus(int status) {
        mStatus = status;
    }
}
