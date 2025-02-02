package com.catalogar.common.dto;

public class ApiResponse<T> {
    private final T data;
    private final Metadata meta;

    public ApiResponse(T data) {
        this.data = data;
        this.meta = null;
    }

    public ApiResponse(String message) {
        this(null, message);
    }

    public ApiResponse(T data, String message) {
        this(data, new Metadata(message));
    }

    public ApiResponse(T data, Metadata meta) {
        this.data = data;
        this.meta = meta;
    }

    public T getData() {
        return data;
    }

    public Metadata getMeta() {
        return meta;
    }
}
