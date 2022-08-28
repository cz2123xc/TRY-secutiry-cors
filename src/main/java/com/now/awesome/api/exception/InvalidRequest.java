package com.now.awesome.api.exception;

import lombok.Getter;

@Getter
public class InvalidRequest extends DefaultException {

    private static final String MESSAGE = "잘못된 요청 입니다.";

    public InvalidRequest() {
        super(MESSAGE);
    }

    public InvalidRequest(String fieldName, String message) {
        super(MESSAGE);
        addValidation(fieldName, message);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
