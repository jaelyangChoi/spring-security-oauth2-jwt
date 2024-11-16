package org.example.springsecurityoauth2jwt.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDTO {

    private String username; //생성한 id
    private String name;
    private String role;

}
