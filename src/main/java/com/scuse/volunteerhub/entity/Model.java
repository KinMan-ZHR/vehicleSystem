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
@TableName("model")
public class Model implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String modelName;
    private String modelTitle;
    private String modelImage;
    private LocalDate modelTime;
    private String modelSource;
    private String modelText;

    public Model(Long id, String modelName, String modelTitle, String modelImage, LocalDate modelTime, String modelSource, String modelText) {
        this.id = id;
        this.modelName = modelName;
        this.modelTitle = modelTitle;
        this.modelImage = modelImage;
        this.modelTime = modelTime;
        this.modelSource = modelSource;
        this.modelText = modelText;
    }
    public Model( String modelName, String modelTitle, String modelImage, LocalDate modelTime, String modelSource, String modelText) {
        this.modelName = modelName;
        this.modelTitle = modelTitle;
        this.modelImage = modelImage;
        this.modelTime = modelTime;
        this.modelSource = modelSource;
        this.modelText = modelText;
    }
}
