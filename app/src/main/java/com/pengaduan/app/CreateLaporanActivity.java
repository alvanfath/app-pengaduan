package com.pengaduan.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.HashMap;
import java.util.Map;

public class CreateLaporanActivity extends AppCompatActivity {

    private EditText deskripsi, alamat;
    private Button buttonCreate;
    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_create_laporan);
        deskripsi = findViewById(R.id.deskripsi);
        alamat = findViewById(R.id.alamat);
        buttonCreate = findViewById(R.id.createButton);

        deskripsi.addTextChangedListener(textWatcher);
        alamat.addTextChangedListener(textWatcher);
    }
    private final android.text.TextWatcher textWatcher = new android.text.TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            // Memeriksa apakah ada EditText yang kosong
            checkForm();
        }

        @Override
        public void afterTextChanged(android.text.Editable editable) {}
    };

    private void checkForm() {
        String desc = deskripsi.getText().toString();
        String address = alamat.getText().toString();

        // Jika semua EditText terisi, ubah warna tombol menjadi primary dan aktifkan tombol
        if (!desc.isEmpty() && !address.isEmpty()) {
            buttonCreate.setBackgroundTintList(getResources().getColorStateList(R.color.primary));  // Ganti dengan warna tombol aktif
            buttonCreate.setEnabled(true);  // Tombol aktif
        } else {
            buttonCreate.setBackgroundTintList(getResources().getColorStateList(R.color.gray));  // Tombol abu-abu
            buttonCreate.setEnabled(false);  // Tombol tidak aktif
        }
    }

    public void onCreateLaporan(View view) {

        ApiService apiService = new ApiService();
        //setting header
        LocalStorage localStorage = new LocalStorage(CreateLaporanActivity.this);
        String token = localStorage.getToken();
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + token);
        headers.put("Accept", "application/json");

        //setting payload
        Map<String, Object> requestData = new HashMap<>();
        requestData.put("aduan", deskripsi.getText().toString());
        requestData.put("lokasi", alamat.getText().toString());

        apiService.postRequest("api/user/create-pengaduan", headers,requestData, new ApiService.ApiCallback() {
            @Override
            public void onSuccess(ApiModel apiModel) {

                Intent intent = new Intent(CreateLaporanActivity.this, HomePageActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(ApiModel apiModel) {
                // Pastikan pemanggilan Toast di thread UI
                runOnUiThread(() -> {
                    Toast.makeText(CreateLaporanActivity.this, apiModel.getMessage(), Toast.LENGTH_SHORT).show();
                });

                System.out.println("Login gagal: " + apiModel.getMessage());
            }
        });
    }
}
