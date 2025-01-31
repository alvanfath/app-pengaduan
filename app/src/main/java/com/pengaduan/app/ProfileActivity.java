package com.pengaduan.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
    private TextView nama, nik,provinsi,kota;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        kota = findViewById(R.id.profile_city);
        provinsi = findViewById(R.id.profile_province);
        nama = findViewById(R.id.profile_name);
        nik = findViewById(R.id.profile_nik);
        fetchProfile();
    }

    private void fetchProfile(){
        Gson gson = new Gson();
        ApiService apiService = new ApiService();
        LocalStorage localStorage = new LocalStorage(ProfileActivity.this);
        String token = localStorage.getToken();
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + token);
        headers.put("Accept", "application/json");

        apiService.getRequest("api/user/profile-user", headers, new ApiService.ApiCallback() {
            @Override
            public void onSuccess(ApiModel apiModel) {
                try {
                    Object responseData = apiModel.getData();
                    if (responseData instanceof Map){
                        Map<String,Object> data = (Map<String,Object>) responseData;
                        runOnUiThread(() -> {
                            nik.setText((String) data.get("nik"));
                            nama.setText((String) data.get("nama"));
                            provinsi.setText((String) data.get("provinsi"));
                            kota.setText((String) data.get("kota"));
                        });
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    runOnUiThread(() -> {
                        Toast.makeText(ProfileActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
                }
            }

            @Override
            public void onFailure(ApiModel apiModel) {
                runOnUiThread(() -> {
                    Toast.makeText(ProfileActivity.this, apiModel.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    public void onLogout(View view){
        LocalStorage localStorage = new LocalStorage(ProfileActivity.this);
        localStorage.removeToken();
        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
