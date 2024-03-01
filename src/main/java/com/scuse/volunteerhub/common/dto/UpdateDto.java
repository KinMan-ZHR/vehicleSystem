package com.scuse.volunteerhub.common.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class UpdateDto implements Serializable {
    @NotBlank(message = "昵称不能为空")
    private String username;

    private String sex;

    private String maritalStatus;

    private String avatar;

    private LocalDateTime dateOfBirth;

    @Email(message = "邮箱格式不正确")
    private String email;

    private String tel;

    private String address;

    private String password;
}
