package com.now.awesome.api.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter @Setter
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty(message = "이름을 입력하세요.")
    private String userId;

    @NotEmpty(message = "비밀번호를 입력하세요.")
    private String password;

    @NotEmpty(message = "이름을 입력하세요.")
    private String name;

    @NotEmpty(message = "이메일을 입력하세요.")
    private String email;

    private String regDate;

    @Builder
    Member(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public Member() {

    }
}
