package com.now.awesome.api.domain;

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
    private String userPw;

    @NotEmpty(message = "이름을 입력하세요.")
    private String name;

    @NotEmpty(message = "이메일을 입력하세요.")
    private String email;

    private String regDate;

}
