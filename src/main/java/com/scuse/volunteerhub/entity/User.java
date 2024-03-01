package com.scuse.volunteerhub.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author DL
 * @since 2024-03-01 02:51:38
 */
@Getter
@Setter
@TableName("m_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String username;

    private String password;

    private String avatar;

    private String sex;

    private String maritalStatus;

    private LocalDate dateOfBirth;

    private String email;

    private String tel;

    private String address;

    private String statement;

    private LocalDateTime created;

    private LocalDate lastUpdate;
}
