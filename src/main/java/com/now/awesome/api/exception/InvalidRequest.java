package com.now.awesome.api.exception;

import lombok.Getter;

@Getter
public class InvalidRequest extends DefaultException {

    private static final String message = "잘못된 요청 입니다.";

    public InvalidRequest() {
        super(message);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
