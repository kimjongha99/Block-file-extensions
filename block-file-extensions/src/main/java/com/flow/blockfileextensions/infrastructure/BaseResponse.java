package com.flow.blockfileextensions.infrastructure;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BaseResponse<T> {
    private boolean success;
    private String message;
    private T data;

    public BaseResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(true, "요청 성공", data);
    }

    public static BaseResponse<Void> success() {
        return new BaseResponse<>(true, "요청 성공", null);
    }
    public static <T> BaseResponse<T> failure(String message) {
        return new BaseResponse<>(false, message, null);
    }
    public BaseResponse<T> setData(T data) {
        this.data = data;
        return this;
    }
}