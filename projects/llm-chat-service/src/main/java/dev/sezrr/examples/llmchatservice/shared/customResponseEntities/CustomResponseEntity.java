package dev.sezrr.examples.llmchatservice.shared.customResponseEntities;

import lombok.Getter;

@Getter
public class CustomResponseEntity<T> extends BaseCustomResponseEntity {
    private final T data;

    private CustomResponseEntity(boolean success, T data) {
        super(success);
        this.data = data;
    }
    
    // Factory methods for success and failure
    public static <T> CustomResponseEntity<T> success(T data) {
        return new CustomResponseEntity<>(true, data);
    }

    public static <T> CustomResponseEntity<T> failureWithData(String error) {
        return new CustomResponseEntity<>(false, null);
    }
}