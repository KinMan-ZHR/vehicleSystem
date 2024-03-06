package com.scuse.volunteerhub.common.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
public class PasswordDto implements Serializable {
    @NotNull(message = "id不能为空")
    private Long id;
    private String oldPassword;
    private String newPassword;
}
