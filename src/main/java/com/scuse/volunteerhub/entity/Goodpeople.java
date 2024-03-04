package com.scuse.volunteerhub.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
@TableName("goodpeople")
public class Goodpeople implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @NotBlank(message = "道德好人的名字不能为空")
    private String goodpeopleName;

    @NotBlank(message = "道德好人的照片不能为空")
    private String goodpeopleImage;

}