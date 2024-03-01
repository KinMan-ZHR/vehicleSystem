package com.scuse.volunteerhub.common.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class UpdateDto implements Serializable {
    private String username;

    private String avatar;

    @Email(message = "邮箱格式不正确")
    private String email;

    private String password;

    private LocalDateTime lastLogin;
}
