package com.now.awesome.api.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@ToString
public class Login {

    @NotBlank(message = "아이디를 입력하세요.")
    private String userId;
    @NotBlank(message = "비밀번호를 입력하세요.")
    private String password;

    @Builder
    public Login(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public Login() {

    }


}
