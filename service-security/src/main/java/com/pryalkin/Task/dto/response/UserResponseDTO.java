package com.pryalkin.Task.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class UserResponseDTO {

    private Long id;
    private String username;
    private String role;
    private String[] authorities;

}
