package com.scuse.volunteerhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scuse.volunteerhub.entity.Article;
import com.scuse.volunteerhub.mapper.ArticleMapper;
import com.scuse.volunteerhub.service.ArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author DL
 * @since 2024-03-05 04:23:35
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public Page<Article> getArticlePageList(Page<Article> page, Wrapper<Article> queryWrapper) {
        return articleMapper.selectPage(page, queryWrapper);
    }

    @Override
    public void updateHotness(Long id) {
        Article article = this.getById(id);
        article.setClickCount(article.getClickCount() + 1);

        LocalDateTime lastClickTime = article.getLastClickTime();
        LocalDateTime nowClickTime = LocalDateTime.now();

        article.setLastClickTime(nowClickTime);

        Duration duration = Duration.between(lastClickTime, nowClickTime);
        double durationSeconds = duration.getSeconds() + duration.getNano() / 1000000000.0;
        double hotness = getNewtonCoolingValue(article.getClickCount(), durationSeconds, 0.0001);

        article.setHotness(hotness);
        this.updateById(article);
    }

    /**
     * 改进牛顿冷却法求热度
     * @param clickCount    累计点击量
     * @param duration      单位：秒
     * @param alpha         系数常量
     * @return
     */
    @Override
    public double getNewtonCoolingValue(Long clickCount, double duration, double alpha) {
        return (double) ( clickCount * Math.exp( - alpha * clickCount * duration ) );
    }
}
