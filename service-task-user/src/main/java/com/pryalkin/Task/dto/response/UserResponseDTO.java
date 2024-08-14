package com.pryalkin.Task.dto.response;

import lombok.Data;

@Data
public class UserResponseDTO {

    private Long id;
    private String email;
    private String name;
    private String surname;
    private String role;
    private String[] authorities;

}
