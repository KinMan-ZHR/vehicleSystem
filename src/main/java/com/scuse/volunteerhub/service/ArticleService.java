package com.scuse.volunteerhub.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scuse.volunteerhub.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author DL
 * @since 2024-03-05 04:23:35
 */
public interface ArticleService extends IService<Article> {
    Page<Article> getArticlePageList(Page<Article> page, Wrapper<Article> queryWrapper);
    void updateHotness(Long id);
    double getNewtonCoolingValue(Long clickcount, double duration, double alpha);
}
