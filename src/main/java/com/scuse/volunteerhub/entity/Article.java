package com.scuse.volunteerhub.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author DL
 * @since 2024-03-06 04:26:42
 */
@Getter
@Setter
@TableName("m_article")
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String title;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate time;

    private String source;

    private String text;

    private String image;

    private LocalDateTime lastClickTime;

    private Long clickCount;

    private Double hotness;
}
