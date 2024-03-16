package com.scuse.volunteerhub.common.dto;

import lombok.Data;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Data
public class CloudvideoDto implements Serializable {
    private List<Integer> type = Arrays.asList(1, 2, 3, 4);
    private Integer currPage = 1;
    private Integer pageSize = 100;
}
