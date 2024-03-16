package com.scuse.volunteerhub.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
@TableName("headline")
public class Headline implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String headlineTitle;
    private String headlineImage;
    private LocalDate headlineTime;
    private String headlineSource;
    private String headlineText;

    public Headline(Long id, String headlineTitle, String headlineImage, LocalDate headlineTime, String headlineSource, String headlineText) {
        this.id = id;
        this.headlineTitle = headlineTitle;
        this.headlineImage = headlineImage;
        this.headlineTime = headlineTime;
        this.headlineSource = headlineSource;
        this.headlineText = headlineText;
    }
    public Headline(String headlineTitle, String headlineImage, LocalDate headlineTime, String headlineSource, String headlineText) {
        this.headlineTitle = headlineTitle;
        this.headlineImage = headlineImage;
        this.headlineTime = headlineTime;
        this.headlineSource = headlineSource;
        this.headlineText = headlineText;
    }
}