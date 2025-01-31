package com.pengaduan.app;

import static java.security.AccessController.getContext;

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

public class LoginActivity extends AppCompatActivity {
    private EditText nikTextt, passwordText;
    private Button buttonLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_login);
        nikTextt = findViewById(R.id.nikLogin);
        passwordText = findViewById(R.id.passwordLogin);
        buttonLogin = findViewById(R.id.loginButton);

        nikTextt.addTextChangedListener(textWatcher);
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

    private void checkForm() {
        String name = nikTextt.getText().toString();
        String password = passwordText.getText().toString();

        // Jika semua EditText terisi, ubah warna tombol menjadi primary dan aktifkan tombol
        if (!name.isEmpty() && !password.isEmpty()) {
            buttonLogin.setBackgroundTintList(getResources().getColorStateList(R.color.primary));  // Ganti dengan warna tombol aktif
            buttonLogin.setEnabled(true);  // Tombol aktif
        } else {
            buttonLogin.setBackgroundTintList(getResources().getColorStateList(R.color.gray));  // Tombol abu-abu
            buttonLogin.setEnabled(false);  // Tombol tidak aktif
        }
    }
    public void onLogin(View view) {
        EditText nikText = findViewById(R.id.nikLogin);
        EditText password = findViewById(R.id.passwordLogin);

        ApiService apiService = new ApiService();
        Map<String, Object> requestData = new HashMap<>();
        Map<String, String> header = new HashMap<>();

        requestData.put("nik", nikText.getText().toString());
        requestData.put("password", password.getText().toString());

        apiService.postRequest("api/user/login-user", header, requestData, new ApiService.ApiCallback() {
            @Override
            public void onSuccess(ApiModel apiModel) {
                // Pastikan tidak terjadi loop pemanggilan API atau callback
                LocalStorage localStorage = new LocalStorage(LoginActivity.this);
                if (apiModel.getData() instanceof Map){
                    Map<String, Object> data = (Map<String, Object>) apiModel.getData();
                    String token = (String) data.get("access_token");
                    localStorage.saveToken(token);
                    Intent intent = new Intent(LoginActivity.this, HomePageActivity.class);
                    startActivity(intent);
                    finish();
                }
                System.out.println("Login sukses: " + apiModel.getData());
            }

            @Override
            public void onFailure(ApiModel apiModel) {
                // Ganti getContext() dengan this untuk Activity context
                CharSequence message = apiModel.getMessage();

                // Pastikan pemanggilan Toast di thread UI
                runOnUiThread(() -> {
                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                });

                System.out.println("Login gagal: " + apiModel.getMessage());
            }
        });
    }

}
