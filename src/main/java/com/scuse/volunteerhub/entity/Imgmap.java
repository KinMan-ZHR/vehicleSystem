package com.scuse.volunteerhub.entity;

/**
 * created by KinMan谨漫 on 2024/4/13 16:59
 *
 * @author KinMan谨漫
 */
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@TableName("m_imgmap")
public class Imgmap implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String url;

    private String path;
}