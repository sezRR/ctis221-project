package dev.sezrr.examples.llmchatservice.shared.customresponseentity.errors;

public class ErrorStatus {
    private String error;
    private final int errorCode;
    private String errorCategory;

    public ErrorStatus(String error, int errorCode, String errorCategory) {
        this.error = error;
        this.errorCode = errorCode;
        this.errorCategory = errorCategory;
    }

    public ErrorStatus(String error, int errorCode) {
        this.error = error;
        this.errorCode = errorCode;
    }

    public ErrorStatus(int errorCode, String errorCategory) {
        this.errorCode = errorCode;
        this.errorCategory = errorCategory;
    }

    public String getError() {
        return error;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorCategory() {
        return errorCategory;
    }
}
