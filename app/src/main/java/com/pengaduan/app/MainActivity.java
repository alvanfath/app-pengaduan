package com.pengaduan.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private EditText textNama, emailText, phoneText, passwordText;
    private Button buttonRegis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //setting variable
        // Menghubungkan komponen UI
        textNama = findViewById(R.id.textNama);
        emailText = findViewById(R.id.emailText);
        phoneText = findViewById(R.id.phoneText);
        passwordText = findViewById(R.id.passwordText);
        buttonRegis = findViewById(R.id.buttonRegis);

        // Menambahkan TextWatcher untuk setiap EditText
        textNama.addTextChangedListener(textWatcher);
        emailText.addTextChangedListener(textWatcher);
        phoneText.addTextChangedListener(textWatcher);
        passwordText.addTextChangedListener(textWatcher);
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

    // Fungsi untuk mengecek apakah semua form terisi
    private void checkForm() {
        String name = textNama.getText().toString();
        String email = emailText.getText().toString();
        String phone = phoneText.getText().toString();
        String password = passwordText.getText().toString();

        // Jika semua EditText terisi, ubah warna tombol menjadi primary dan aktifkan tombol
        if (!name.isEmpty() && !email.isEmpty() && !phone.isEmpty() && !password.isEmpty()) {
            buttonRegis.setBackgroundTintList(getResources().getColorStateList(R.color.primary));  // Ganti dengan warna tombol aktif
            buttonRegis.setEnabled(true);  // Tombol aktif
        } else {
            buttonRegis.setBackgroundTintList(getResources().getColorStateList(android.R.color.darker_gray));  // Tombol abu-abu
            buttonRegis.setEnabled(false);  // Tombol tidak aktif
        }
    }


    public void onRegister(View view) {
        // Mengambil nilai dari EditText
        String nama = textNama.getText().toString();
        String email = emailText.getText().toString();
        String phone = phoneText.getText().toString();
        String password = passwordText.getText().toString();

        // Menampilkan Toast dengan semua nilai
        Toast.makeText(this, "Nama: " + nama + "\nEmail: " + email + "\nNo.Telp: " + phone + "\nPassword: " + password, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}