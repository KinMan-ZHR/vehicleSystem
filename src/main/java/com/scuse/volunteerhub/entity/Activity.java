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
@TableName("activity")
public class Activity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String activityTitle;
    private String activityImage;
    private LocalDate activityTime;
    private String activitySource;
    private String activityText;

    public Activity(Long id, String activityTitle, String activityImage, LocalDate activityTime, String activitySource, String activityText) {
        this.id = id;
        this.activityTitle = activityTitle;
        this.activityImage = activityImage;
        this.activityTime = activityTime;
        this.activitySource = activitySource;
        this.activityText = activityText;
    }
    public Activity( String activityTitle, String activityImage, LocalDate activityTime, String activitySource, String activityText) {
        this.activityTitle = activityTitle;
        this.activityImage = activityImage;
        this.activityTime = activityTime;
        this.activitySource = activitySource;
        this.activityText = activityText;
    }
}
