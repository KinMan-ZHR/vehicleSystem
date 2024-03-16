package com.scuse.volunteerhub.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@TableName("project")
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String projectName;
    private String projectLocation;
    private LocalDate projectPubDate;
    private String projectTimeRange;
    private String projectRegion;
    private String projectType;
    private String projectTarget;
    private String projectState;
    private String projectScale;
    private String projectSize;
    private String projectCover;
    private String projectDescription;
    private String projectAddress;
    private String projectMarker;

    public Project(Long id, String projectName, String projectLocation, LocalDate projectPubDate, String projectTimeRange, String projectRegion, String projectType, String projectTarget, String projectState, String projectScale, String projectSize, String projectCover, String projectDescription, String projectAddress, String projectMarker) {
        this.id = id;
        this.projectName = projectName;
        this.projectLocation = projectLocation;
        this.projectPubDate = projectPubDate;
        this.projectTimeRange = projectTimeRange;
        this.projectRegion = projectRegion;
        this.projectType = projectType;
        this.projectTarget = projectTarget;
        this.projectState = projectState;
        this.projectScale = projectScale;
        this.projectSize = projectSize;
        this.projectCover = projectCover;
        this.projectDescription = projectDescription;
        this.projectAddress = projectAddress;
        this.projectMarker = projectMarker;
    }
    public Project(String projectName, String projectLocation, LocalDate projectPubDate, String projectTimeRange, String projectRegion, String projectType, String projectTarget, String projectState, String projectScale, String projectSize, String projectCover, String projectDescription, String projectAddress, String projectMarker) {
        this.projectName = projectName;
        this.projectLocation = projectLocation;
        this.projectPubDate = projectPubDate;
        this.projectTimeRange = projectTimeRange;
        this.projectRegion = projectRegion;
        this.projectType = projectType;
        this.projectTarget = projectTarget;
        this.projectState = projectState;
        this.projectScale = projectScale;
        this.projectSize = projectSize;
        this.projectCover = projectCover;
        this.projectDescription = projectDescription;
        this.projectAddress = projectAddress;
        this.projectMarker = projectMarker;
    }
}
