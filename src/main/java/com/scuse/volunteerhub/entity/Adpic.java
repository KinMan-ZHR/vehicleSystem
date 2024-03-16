package com.scuse.volunteerhub.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@TableName("adpic")
public class Adpic implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String adpicTitle;
    private String adpicImage;
    private LocalDate adpicTime;
    private String adpicSource;
    private String adpicText;

    public Adpic(Long id, String adpicTitle, String adpicImage, LocalDate adpicTime, String adpicSource, String adpicText) {
        this.id = id;
        this.adpicTitle = adpicTitle;
        this.adpicImage = adpicImage;
        this.adpicTime = adpicTime;
        this.adpicSource = adpicSource;
        this.adpicText = adpicText;
    }
    public Adpic(String adpicTitle, String adpicImage, LocalDate adpicTime, String adpicSource, String adpicText) {
        this.adpicTitle = adpicTitle;
        this.adpicImage = adpicImage;
        this.adpicTime = adpicTime;
        this.adpicSource = adpicSource;
        this.adpicText = adpicText;
    }

}
