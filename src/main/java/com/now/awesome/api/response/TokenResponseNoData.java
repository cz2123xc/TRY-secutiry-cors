package com.now.awesome.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenResponseNoData {

    private String code;
    private String message;

}
