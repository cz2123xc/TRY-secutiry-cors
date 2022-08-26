package com.now.awesome.api.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
public class TokenDataResponse {

    private String code;
    private String message;
    private String token;
    private String subject;
    private String issuedTime;
    private String expiredTime;

    @Builder
    public TokenDataResponse(String code, String message, String token, String subject, String issuedTime, String expiredTime) {
        this.code = code;
        this.message = message;
        this.token = token;
        this.subject = subject;
        this.issuedTime = issuedTime;
        this.expiredTime = expiredTime;
    }

    public TokenDataResponse() {

    }


}
