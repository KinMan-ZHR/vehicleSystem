package com.scuse.volunteerhub.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
@TableName("goodpeople")
public class Goodpeople implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String goodpeopleName;
    private String goodpeopleTitle;
    private String goodpeopleImage;
    private LocalDate goodpeopleTime;
    private String goodpeopleSource;
    private String goodpeopleText;

    public Goodpeople(Long id, String goodpeopleName, String goodpeopleTitle, String goodpeopleImage, LocalDate goodpeopleTime, String goodpeopleSource, String goodpeopleText) {
        this.id = id;
        this.goodpeopleName = goodpeopleName;
        this.goodpeopleTitle = goodpeopleTitle;
        this.goodpeopleImage = goodpeopleImage;
        this.goodpeopleTime = goodpeopleTime;
        this.goodpeopleSource = goodpeopleSource;
        this.goodpeopleText = goodpeopleText;
    }

    public Goodpeople(String goodpeopleName, String goodpeopleTitle, String goodpeopleImage, LocalDate goodpeopleTime, String goodpeopleSource, String goodpeopleText) {
        this.goodpeopleName = goodpeopleName;
        this.goodpeopleTitle = goodpeopleTitle;
        this.goodpeopleImage = goodpeopleImage;
        this.goodpeopleTime = goodpeopleTime;
        this.goodpeopleSource = goodpeopleSource;
        this.goodpeopleText = goodpeopleText;
    }
}