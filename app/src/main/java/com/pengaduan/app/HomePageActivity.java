package com.pengaduan.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomePageActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PengaduanAdapter aduanAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Fetch data
        fetchPengaduanData();
    }

    public void onAddButton(View view){
        System.out.println("masuk bang");
        Intent intent = new Intent(HomePageActivity.this, CreateLaporanActivity.class);
        startActivity(intent);
    }
    public void navigateProfile(View view){
        System.out.println("masuk bang");
        Intent intent = new Intent(HomePageActivity.this, ProfileActivity.class);
        startActivity(intent);
    }

    private void fetchPengaduanData() {
        Gson gson = new Gson();
        ApiService apiService = new ApiService();
        LocalStorage localStorage = new LocalStorage(HomePageActivity.this);
        String token = localStorage.getToken();
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + token);
        headers.put("Accept", "application/json");

        apiService.getRequest("api/user/all-pengaduan", headers, new ApiService.ApiCallback() {
            @Override
            public void onSuccess(ApiModel apiModel) {
                try {
                    if (apiModel.getStatus() == 200) {
                        // Ambil data response
                        Object responseData = apiModel.getData();
                        if (responseData instanceof List) {
                            List<Map<String, Object>> dataList = (List<Map<String, Object>>) responseData;

                            // Log untuk memastikan datanya benar
                            Log.d("API Response", dataList.toString());

                            // Jika menggunakan PengaduanDetail, konversi ke List<PengaduanDetail>
                            List<PengaduanDetail> pengaduanDetails = new ArrayList<>();
                            for (Map<String, Object> item : dataList) {
                                String namaUser = (String) item.get("nama_user");
                                String deskripsiAduan = (String) item.get("deskripsi_aduan");
                                String lokasiAduan = (String) item.get("lokasi_aduan");
                                String tanggalAduan = (String) item.get("tanggal_aduan");
                                String tanggapan = (String) item.get("tanggapan");
                                String status = (String) item.get("status");
                                String tanggalDitanggapi = (String) item.get("tanggal_ditanggapi");
                                String tanggapanSelesai = (String) item.get("tanggapan_selesai");
                                String tanggalSelesai = (String) item.get("tanggal_selesai");
                                boolean isMe = (boolean) item.get("is_me");


                                // Buat objek PengaduanDetail
                                PengaduanDetail pengaduanDetail = new PengaduanDetail(
                                        namaUser, deskripsiAduan, lokasiAduan, tanggalAduan,
                                        tanggapan, status, tanggalDitanggapi, tanggapanSelesai, tanggalSelesai,isMe
                                );
                                pengaduanDetails.add(pengaduanDetail);
                            }

                            // Update UI
                            runOnUiThread(() -> {
                                aduanAdapter = new PengaduanAdapter(HomePageActivity.this, pengaduanDetails);
                                recyclerView.setAdapter(aduanAdapter);
                            });
                        }
                    } else {
                        runOnUiThread(() -> {
                            Toast.makeText(HomePageActivity.this, apiModel.getMessage(), Toast.LENGTH_SHORT).show();
                        });
                    }
                } catch (Exception e) {
                    runOnUiThread(() -> {
                        Toast.makeText(HomePageActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
                }
            }

            @Override
            public void onFailure(ApiModel apiModel) {
                runOnUiThread(() -> {
                    Toast.makeText(HomePageActivity.this, apiModel.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
        });
    }
}
