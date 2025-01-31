package com.pengaduan.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class PengaduanAdapter extends RecyclerView.Adapter<PengaduanAdapter.ViewHolder> {

    private Context context;
    private List<PengaduanDetail> pengaduanList;

    public PengaduanAdapter(Context context,List<PengaduanDetail> pengaduanList) {
        this.context = context;
        this.pengaduanList = pengaduanList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PengaduanDetail pengaduan = pengaduanList.get(position);
        String isMe = pengaduan.getIsMe() ? " (Saya)" : "";
        holder.namaUser.setText(String.format("%s%s", pengaduan.getNamaUser(), isMe));

        holder.deskripsiAduan.setText(String.format("Deskripsi: %s", pengaduan.getDeskripsiAduan()));
        holder.lokasiAduan.setText(String.format("Lokasi: %s", pengaduan.getLokasiAduan()));

        String tanggalAduan = DateUtils.formatTanggal(pengaduan.getTanggalAduan());
        holder.tanggalAduan.setText(String.format("Tanggal Pengaduan: %s", tanggalAduan));
        holder.status.setText(String.format("Status: %s", pengaduan.getStatus()));

        // Null check untuk data opsional
        // Cek apakah tanggapan tidak null
        if (pengaduan.getTanggapan() != null && !pengaduan.getTanggapan().isEmpty()) {
            holder.tanggapan.setVisibility(View.VISIBLE);
            holder.tanggapan.setText(String.format("Tanggapan Diproses: %s", pengaduan.getTanggapan()));
        } else {
            holder.tanggapan.setVisibility(View.GONE);  // Menyembunyikan tampilan jika null
        }

        // Cek apakah tanggal tanggapan tidak null
        if (pengaduan.getTanggalDitanggapi() != null && !pengaduan.getTanggalDitanggapi().isEmpty()) {
            holder.tanggalDitanggapi.setVisibility(View.VISIBLE);
            String tglTnggpi = DateUtils.formatTanggal(pengaduan.getTanggalDitanggapi());

            holder.tanggalDitanggapi.setText(String.format("Tanggal Ditanggapi: %s", tglTnggpi));
        } else {
            holder.tanggalDitanggapi.setVisibility(View.GONE);  // Menyembunyikan tampilan jika null
        }

        // Cek apakah tanggapan selesai tidak null
        if (pengaduan.getTanggapanSelesai() != null && !pengaduan.getTanggapanSelesai().isEmpty()) {
            holder.tanggapanSelesai.setVisibility(View.VISIBLE);
            holder.tanggapanSelesai.setText(String.format("Tanggapan Penyelesaian: %s", pengaduan.getTanggapanSelesai()));
        } else {
            holder.tanggapanSelesai.setVisibility(View.GONE);  // Menyembunyikan tampilan jika null
        }

        // Cek apakah tanggal selesai tidak null
        if (pengaduan.getTanggalSelesai() != null && !pengaduan.getTanggalSelesai().isEmpty()) {
            holder.tanggalSelesai.setVisibility(View.VISIBLE);
            String tglSelesai = DateUtils.formatTanggal(pengaduan.getTanggalSelesai());
            holder.tanggalSelesai.setText(String.format("Tanggal Selesai: %s", tglSelesai));
        } else {
            holder.tanggalSelesai.setVisibility(View.GONE);  // Menyembunyikan tampilan jika null
        }
    }

    @Override
    public int getItemCount() {
        return pengaduanList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView namaUser, deskripsiAduan, lokasiAduan, tanggalAduan, status, tanggapan, tanggalDitanggapi, tanggapanSelesai, tanggalSelesai;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            namaUser = itemView.findViewById(R.id.nama_user);
            deskripsiAduan = itemView.findViewById(R.id.deskripsi_aduan);
            lokasiAduan = itemView.findViewById(R.id.lokasi_aduan);
            tanggalAduan = itemView.findViewById(R.id.tanggal_aduan);
            status = itemView.findViewById(R.id.status);
            tanggapan = itemView.findViewById(R.id.tanggapan);
            tanggalDitanggapi = itemView.findViewById(R.id.tanggal_ditanggapi);
            tanggapanSelesai = itemView.findViewById(R.id.tanggapan_selesai);
            tanggalSelesai = itemView.findViewById(R.id.tanggal_selesai);

        }
    }
}
