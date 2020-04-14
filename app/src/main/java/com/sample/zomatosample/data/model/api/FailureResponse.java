package com.sample.zomatosample.data.model.api;

public class FailureResponse {

    private int errorCode;
    private CharSequence errorMessage;

    public FailureResponse() {
    }

    public FailureResponse(int errorCode, CharSequence errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public CharSequence getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}