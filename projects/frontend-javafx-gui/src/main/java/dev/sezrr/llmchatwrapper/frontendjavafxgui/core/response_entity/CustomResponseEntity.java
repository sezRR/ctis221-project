package dev.sezrr.llmchatwrapper.frontendjavafxgui.core.response_entity;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomResponseEntity<T> {
    private boolean success;
    private String message;
    private T data;

    public CustomResponseEntity() {
    }

    public CustomResponseEntity(boolean success) {
        this.success = success;
    }

    public CustomResponseEntity(String message, boolean success) {
        this(success);
        this.message = message;
    }

    private CustomResponseEntity(boolean success, T data) {
        this(success);
        this.data = data;
    }
    
    public CustomResponseEntity(String message, boolean success, T data) {
        this(message, success);
        this.data = data;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static CustomResponseEntity<?> success(String message) {
        return new CustomResponseEntity<>(message, true);
    }

    public static CustomResponseEntity<?> failure(String message) {
        return new CustomResponseEntity<>(message, false);
    }

    public static <T> CustomResponseEntity<T> success(T data) {
        return new CustomResponseEntity<>(true, data);
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
