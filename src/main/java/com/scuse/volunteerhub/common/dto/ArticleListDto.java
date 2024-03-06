package com.scuse.volunteerhub.common.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ArticleListDto implements Serializable {
    String type;
    Integer pageSize;
    Integer currPage;

}
