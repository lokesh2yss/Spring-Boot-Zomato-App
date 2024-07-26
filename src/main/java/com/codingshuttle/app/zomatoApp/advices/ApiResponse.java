package com.codingshuttle.app.zomatoApp.advices;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiResponse<T> {
    private T data;
    @JsonFormat(pattern = "hh:mm:ss dd-MM-yyyy")
    private LocalDateTime timeStamp;
    private ApiError error;
    public ApiResponse() {
        this.timeStamp = LocalDateTime.now();
    }
    public ApiResponse(ApiError error) {
        this();
        this.error = error;
    }
    public ApiResponse(T data) {
        this();
        this.data = data;
    }
}
