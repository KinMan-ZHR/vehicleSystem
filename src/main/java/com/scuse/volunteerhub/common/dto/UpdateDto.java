package com.scuse.volunteerhub.common.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class UpdateDto implements Serializable {
    @NotNull(message = "id不能为空")
    private Long id;

    private String username;

    private String sex;

    private String maritalStatus;

    private String avatar;

    private LocalDate birthdate;

    @Email(message = "邮箱格式不正确")
    private String email;

    private String tel;

    private String address;

    private String statement;
}
