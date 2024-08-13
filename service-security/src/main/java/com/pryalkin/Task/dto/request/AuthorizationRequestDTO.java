package com.pryalkin.Task.dto.request;

import lombok.Data;

@Data
public class AuthorizationRequestDTO {

    private String userToken;
    private String serviceToken;
    private Integer aValue;

}
