package dev.sezrr.examples.llmchatservice.shared.results;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Result<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;  // Add this line
    
    private boolean success;
    private T data;
    private ArrayList<String> errors;

    private Result(boolean success, T data) {
        this.success = success;
        this.data = data;
    }
    
    private Result(boolean success, T data, List<String> errors) {
        this(success, data);
        this.errors = new ArrayList<>(errors);
    }
    
    private Result(boolean success, T data, String errors) {
        this(success, data);
        this.errors = new ArrayList<>();
    }

    // Factory methods for success and failure
    public static <T> Result<T> success(T data) {
        return new Result<>(true, data);
    }

    public static <T> Result<T> failure(List<String> errors) {
        return new Result<>(false, null, errors);
    }

    public static <T> Result<T> failure(String error) {
        return new Result<>(false, null, error);
    }

    // Getters
    public boolean isSuccess() {
        return success;
    }

    public T getData() {
        return data;
    }

    public List<String> getErrors() {
        return errors;
    }
}