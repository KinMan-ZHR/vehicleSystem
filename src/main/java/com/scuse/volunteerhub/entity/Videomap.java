package com.scuse.volunteerhub.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author DL
 * @since 2024-03-04 04:01:37
 */
@Getter
@Setter
@TableName("m_videomap")
public class Videomap implements Serializable {

    private static final long serialVersionUID = 1L;

    private String title;

    private String cover;

    private String path;

    @TableId("link")
    private String link;

    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
}
