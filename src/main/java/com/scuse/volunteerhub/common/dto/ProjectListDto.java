package com.scuse.volunteerhub.common.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ProjectListDto  implements Serializable {
    private String id;
    private Long currPage;
    private Long pageSize;

    private String name;
    private String location;
    private String pub_date;
    private List<String> time_range;
    private List<Integer> region;
    private List<Integer> type;
    private List<Integer> target;
    private List<Integer> state;
    private List<Integer> scale;
    private List<Integer> size;
    private String cover;
    private String description;
    private String address;
    private List<Double> marker;

}
