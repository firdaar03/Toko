package id.ac.polman.astra.nim0320190011.toko.api.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Toko {
    @PrimaryKey
    @NonNull
    @SerializedName("idToko")
    private int idToko;
    @SerializedName("username")
    private String mUsername;
    @SerializedName("password")
    private String mPassword;
    @SerializedName("nama_pemiliki")
    private String mNama_pemilik;
    @SerializedName("no_telfon")
    private String mNo_telfon;
    @SerializedName("email")
    private String mEmail;
    @SerializedName("jenis_kelamin")
    private String mJenis_kelamin;
    @SerializedName("tempat_lahir")
    private String mTempat_lahir;
    @SerializedName("tanggal_lahir")
    private String mTanggal_lahir;
    @SerializedName("alamat")
    private String mAlamat;
    @SerializedName("alamat_toko")
    private String mAlamatToko;
    @SerializedName("nik")
    private String mNIK;
    @SerializedName("foto_KTP")
    private String mFoto_KTP;
    @SerializedName("foto_diri")
    private String mFoto_diri;
    @SerializedName("foto_toko")
    private String mFoto_toko;
    @SerializedName("creaby")
    private String mCreaby;
    @SerializedName("creadate")
    private String mCreadate;
    @SerializedName("modiby")
    private String mModiby;
    @SerializedName("modidate")
    private String mModidate;
    @SerializedName("status")
    private int mStatus;

    public Toko(){

    }

    public Toko(int idToko, String username, String password, String nama_pemilik, String no_telfon, String email, String jenis_kelamin, String tempat_lahir, String tanggal_lahir, String alamat, String alamatToko, String NIK, String foto_KTP, String foto_diri, String foto_toko, String creaby, String creadate, String modiby, String modidate, int status) {
        this.idToko = idToko;
        mUsername = username;
        mPassword = password;
        mNama_pemilik = nama_pemilik;
        mNo_telfon = no_telfon;
        mEmail = email;
        mJenis_kelamin = jenis_kelamin;
        mTempat_lahir = tempat_lahir;
        mTanggal_lahir = tanggal_lahir;
        mAlamat = alamat;
        mAlamatToko = alamatToko;
        mNIK = NIK;
        mFoto_KTP = foto_KTP;
        mFoto_diri = foto_diri;
        mFoto_toko = foto_toko;
        mCreaby = creaby;
        mCreadate = creadate;
        mModiby = modiby;
        mModidate = modidate;
        mStatus = status;
    }

    public int getIdToko() {
        return idToko;
    }

    public void setIdToko(int idToko) {
        this.idToko = idToko;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getNama_pemilik() {
        return mNama_pemilik;
    }

    public void setNama_pemilik(String nama_pemilik) {
        mNama_pemilik = nama_pemilik;
    }

    public String getNo_telfon() {
        return mNo_telfon;
    }

    public void setNo_telfon(String no_telfon) {
        mNo_telfon = no_telfon;
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

    public String getTanggal_lahir() {
        return mTanggal_lahir;
    }

    public void setTanggal_lahir(String tanggal_lahir) {
        mTanggal_lahir = tanggal_lahir;
    }

    public String getAlamat() {
        return mAlamat;
    }

    public void setAlamat(String alamat) {
        mAlamat = alamat;
    }

    public String getAlamatToko() {
        return mAlamatToko;
    }

    public void setAlamatToko(String alamatToko) {
        mAlamatToko = alamatToko;
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

    public String getCreadate() {
        return mCreadate;
    }

    public void setCreadate(String creadate) {
        mCreadate = creadate;
    }

    public String getModiby() {
        return mModiby;
    }

    public void setModiby(String modiby) {
        mModiby = modiby;
    }

    public String getModidate() {
        return mModidate;
    }

    public void setModidate(String modidate) {
        mModidate = modidate;
    }

    public int getStatus() {
        return mStatus;
    }

    public void setStatus(int status) {
        mStatus = status;
    }
}
