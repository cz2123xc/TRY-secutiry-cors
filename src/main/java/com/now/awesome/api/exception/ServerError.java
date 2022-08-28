package com.now.awesome.api.exception;

import lombok.Getter;

@Getter
public class ServerError extends DefaultException{

        private static final String MESSAGE = "서버 에러입니다.";

        public ServerError() {
            super(MESSAGE);
        }

        public ServerError(String fieldName, String message) {
            super(MESSAGE);
            addValidation(fieldName, message);
        }

        @Override
        public int getStatusCode() {
            return 500;
        }
}
