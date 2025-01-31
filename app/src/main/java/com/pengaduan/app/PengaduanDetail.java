package com.pengaduan.app;

import com.google.gson.annotations.SerializedName;

public class PengaduanDetail {
    @SerializedName("nama_user")
    private String namaUser;

    @SerializedName("deskripsi_aduan")
    private String deskripsiAduan;

    @SerializedName("lokasi_aduan")
    private String lokasiAduan;

    @SerializedName("tanggal_aduan")
    private String tanggalAduan;

    @SerializedName("tanggapan")
    private String tanggapan;

    @SerializedName("status")
    private String status;

    @SerializedName("tanggal_ditanggapi")
    private String tanggalDitanggapi;

    @SerializedName("tanggapan_selesai")
    private String tanggapanSelesai;

    @SerializedName("tanggal_selesai")
    private String tanggalSelesai;
    @SerializedName("is_me")
    private boolean isMe;

    // Default Constructor (diperlukan oleh Gson)
    public PengaduanDetail() {}

    // Constructor Lengkap
    public PengaduanDetail(String namaUser, String deskripsiAduan, String lokasiAduan,
                           String tanggalAduan, String tanggapan, String status,
                           String tanggalDitanggapi, String tanggapanSelesai, String tanggalSelesai, boolean isMe) {
        this.namaUser = namaUser;
        this.deskripsiAduan = deskripsiAduan;
        this.lokasiAduan = lokasiAduan;
        this.tanggalAduan = tanggalAduan;
        this.tanggapan = tanggapan;
        this.status = status;
        this.tanggalDitanggapi = tanggalDitanggapi;
        this.tanggapanSelesai = tanggapanSelesai;
        this.tanggalSelesai = tanggalSelesai;
        this.isMe = isMe;
    }

    // Getters
    public String getNamaUser() {
        return namaUser;
    }

    public String getDeskripsiAduan() {
        return deskripsiAduan;
    }

    public String getLokasiAduan() {
        return lokasiAduan;
    }

    public String getTanggalAduan() {
        return tanggalAduan;
    }

    public String getTanggapan() {
        return tanggapan;
    }

    public String getStatus() {
        return status;
    }

    public String getTanggalDitanggapi() {
        return tanggalDitanggapi;
    }

    public String getTanggapanSelesai() {
        return tanggapanSelesai;
    }

    public String getTanggalSelesai() {
        return tanggalSelesai;
    }
    public boolean getIsMe() {
        return isMe;
    }

    @Override
    public String toString() {
        return "PengaduanDetail{" +
                "namaUser='" + namaUser + '\'' +
                ", deskripsiAduan='" + deskripsiAduan + '\'' +
                ", lokasiAduan='" + lokasiAduan + '\'' +
                ", tanggalAduan='" + tanggalAduan + '\'' +
                ", tanggapan='" + tanggapan + '\'' +
                ", status='" + status + '\'' +
                ", tanggalDitanggapi='" + tanggalDitanggapi + '\'' +
                ", tanggapanSelesai='" + tanggapanSelesai + '\'' +
                ", tanggalSelesai='" + tanggalSelesai + '\'' +
                ", isMe='" + isMe + '\'' +
                '}';
    }
}
