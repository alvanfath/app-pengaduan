package com.pengaduan.app;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ApiService {
    private static final String BASE_URL = "https://daee-2001-448a-2020-7773-887e-cd7d-a7c7-46b2.ngrok-free.app/";
    private final OkHttpClient client;
    private final Gson gson;
    private final ExecutorService executorService;

    public ApiService() {
        // OkHttpClient with timeout configuration
        this.client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        // Initialize Gson
        this.gson = new Gson();

        // ExecutorService to handle network tasks in background threads
        this.executorService = Executors.newSingleThreadExecutor();
    }

    // GET Request - Network operation off main thread
    public void getRequest(String endpoint, Map<String, String> headers, ApiCallback callback) {
        executorService.execute(() -> {
            Request.Builder requestBuilder = new Request.Builder()
                    .url(BASE_URL + endpoint)
                    .get();

            // Log URL, method, and headers
            Log.d("ApiService", "GET Request:");
            Log.d("ApiService", "URL: " + BASE_URL + endpoint);
            Log.d("ApiService", "Method: GET");
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    Log.d("ApiService", "Header: " + entry.getKey() + ": " + entry.getValue());
                }
            }

            // Tambahkan header dari parameter Map
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    requestBuilder.addHeader(entry.getKey(), entry.getValue());
                }
            }

            Request request = requestBuilder.build();

            try (Response response = client.newCall(request).execute()) {
                // Log response
                if (response.isSuccessful() && response.body() != null) {
                    String responseBody = response.body().string();
                    Log.d("ApiService", "Response: " + responseBody);
                    ApiModel model = gson.fromJson(responseBody, ApiModel.class);
                    callback.onSuccess(model);
                } else {
                    if (response.body() != null) {
                        String responseBody = response.body().string();
                        ApiModel model = gson.fromJson(responseBody, ApiModel.class);
                        callback.onFailure(model);
                    } else {
                        callback.onFailure(new ApiModel(response.code(), "Failed", null));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                callback.onFailure(new ApiModel(500, "An error occurred: " + e.getMessage(), null));
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
                callback.onFailure(new ApiModel(500, "Invalid response format", null));
            }
        });
    }

    // POST Request - Network operation off main thread
    public void postRequest(String endpoint, Map<String, String> headers, Object body, ApiCallback callback) {
        executorService.execute(() -> {
            String jsonBody = gson.toJson(body);
            RequestBody requestBody = RequestBody.create(jsonBody, MediaType.get("application/json; charset=utf-8"));

            Request.Builder requestBuilder = new Request.Builder()
                    .url(BASE_URL + endpoint)
                    .post(requestBody);

            // Log URL, method, header, and payload
            Log.d("ApiService", "POST Request:");
            Log.d("ApiService", "URL: " + BASE_URL + endpoint);
            Log.d("ApiService", "Method: POST");
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    Log.d("ApiService", "Header: " + entry.getKey() + ": " + entry.getValue());
                }
            }
            Log.d("ApiService", "Payload: " + jsonBody);

            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    requestBuilder.addHeader(entry.getKey(), entry.getValue());
                }
            }
            Request request = requestBuilder.build();

            try (Response response = client.newCall(request).execute()) {
                // Log response
                if (response.isSuccessful() && response.body() != null) {
                    String responseBody = response.body().string();
                    Log.d("ApiService", "Response: " + responseBody);
                    ApiModel model = gson.fromJson(responseBody, ApiModel.class);
                    callback.onSuccess(model);
                } else {
                    if (response.body() != null) {
                        String responseBody = response.body().string();
                        ApiModel model = gson.fromJson(responseBody, ApiModel.class);
                        callback.onFailure(model);
                    } else {
                        callback.onFailure(new ApiModel(response.code(), "Failed", null));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                callback.onFailure(new ApiModel(500, "An error occurred: " + e.getMessage(), null));
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
                callback.onFailure(new ApiModel(500, "Invalid response format", null));
            }
        });
    }

    // Callback interface for API response handling
    public interface ApiCallback {
        void onSuccess(ApiModel apiModel);
        void onFailure(ApiModel apiModel);
    }
}
