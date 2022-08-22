package com.now.awesome.api.exception;

import lombok.Getter;

@Getter
public class ServerError extends DefaultException{

        private static final String message = "서버 에러입니다.";

        public ServerError() {
            super(message);
        }

        @Override
        public int getStatusCode() {
            return 500;
        }
}
