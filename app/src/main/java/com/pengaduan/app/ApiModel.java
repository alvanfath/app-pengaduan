package com.pengaduan.app;

public class ApiModel<T> {
    private int status;
    private String message;
    private Object data; // Menggunakan Object untuk menampung data dinamis

    // Constructor untuk inisialisasi semua field
    public ApiModel(int status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    // Getter dan Setter untuk status
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    // Getter dan Setter untuk message
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // Getter dan Setter untuk data
    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
