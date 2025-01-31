package com.pengaduan.app;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    // Format tanggal jika valid
    public static String formatTanggal(String tanggalInput) {
        if (tanggalInput == null || tanggalInput.isEmpty()) {
            return "Tanggal tidak valid"; // Menangani jika tanggal kosong atau null
        }

        try {
            // Mengonversi string tanggal ke objek Date menggunakan format asli
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            Date date = inputFormat.parse(tanggalInput);

            // Memformat objek Date menjadi string sesuai format yang diinginkan
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault());
            return outputFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return "Tanggal tidak dapat diparse"; // Menangani error saat parsing
        }
    }
}
