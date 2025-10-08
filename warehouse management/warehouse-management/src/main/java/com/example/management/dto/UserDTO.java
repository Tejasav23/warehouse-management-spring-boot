package com.example.management.dto;

import com.example.management.entities.enums.Roles;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private String username;
    private String password;
    private Roles role;
}
