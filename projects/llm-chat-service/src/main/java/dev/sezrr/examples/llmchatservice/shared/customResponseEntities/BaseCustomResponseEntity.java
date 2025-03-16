package dev.sezrr.examples.llmchatservice.shared.customResponseEntities;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseCustomResponseEntity {
    private final String message;
    private final boolean success;

    public BaseCustomResponseEntity(boolean success) {
        this.message = null;
        this.success = success;
    }

    public BaseCustomResponseEntity(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public static BaseCustomResponseEntity success(String message) {
        return new BaseCustomResponseEntity(message, true);
    }

    public static BaseCustomResponseEntity failure(String message) {
        return new BaseCustomResponseEntity(message, false);
    }
}
