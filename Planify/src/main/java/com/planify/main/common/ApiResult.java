package com.planify.main.common;

import org.springframework.http.HttpStatus;
/**
 * API 통신 Response 유틸
 * @param <T>
 */
public class ApiResult<T> {

    private final int status;
    private final String message;
    private final T data;

    private ApiResult(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    // 기본 통신 성공
    public static <T> ApiResult<T> success(T data) {
        return new ApiResult<>(HttpStatus.OK.value(), "Success", data);
    }

    // 성공 ( 메세지 내용 변경 ) 
    public static <T> ApiResult<T> success(String message, T data) {
        return new ApiResult<>(HttpStatus.OK.value(), message, data);
    }

    // 실패 
    public static <T> ApiResult<T> failure(HttpStatus status, String message) {
        return new ApiResult<>(status.value(), message, null);
    }

    // 메시지를 포함한 실패
    public static <T> ApiResult<T> failure(HttpStatus status) {
        return new ApiResult<>(status.value(), status.getReasonPhrase(), null);
    }

    // 커스텀 통신
    public static <T> ApiResult<T> custom(int status, String message, T data) {
        return new ApiResult<>(status, message, data);
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

}