package com.scuse.volunteerhub.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author DL
 * @since 2024-03-01 04:15:22
 */
@Getter
@Setter
@TableName("m_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @NotBlank(message = "昵称不能为空")
    private String username;

    private String password;

    private String avatar;

    private String sex;

    private String maritalStatus;

    /**
     * 将全局日期格式的数据在发送给前段时转为字符串
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthdate;

    @Email(message = "邮箱格式不正确")
    private String email;

    private String tel;

    private String address;

    private String statement;

    private LocalDateTime created;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate lastUpdate;
}
