package com.now.awesome.api.response;

import lombok.Getter;

@Getter
public class JoinResult {

    private final Long id;

    public JoinResult(Long id) {
        this.id = id;
    }
}
