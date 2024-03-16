package com.scuse.volunteerhub.controller;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scuse.volunteerhub.common.lang.Result;
import com.scuse.volunteerhub.entity.Article;
import com.scuse.volunteerhub.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author DL
 * @since 2024-03-05 04:23:35
 */
@Slf4j
@RestController
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping("/articleList")
    public Result addArticle(@RequestBody Article article){
        log.warn("添加信息动态：" + article);

        articleService.save(article);
        return Result.success("添加成功");
    }

    @DeleteMapping("/articleList")
    public Result deleteArticle(@RequestParam Long id){
        log.warn("删除信息动态：" + id);

        articleService.removeById(id);
        return Result.success("删除成功");
    }

    @PutMapping("/articleList")
    public Result updateArticle(@RequestBody Article article){
        log.warn("更新信息动态：" + article);

        articleService.updateById(article);
        return Result.success("更新成功");
    }

    @GetMapping("/articleList")
    public Result getArticleByPage(@RequestParam(defaultValue = "1") Integer currPage,
                                   @RequestParam(defaultValue = "100") Integer pageSize,
                                   @RequestParam(defaultValue = "all") String type){

        switch (type) {
            case "time" -> {
                QueryWrapper<Article> wrapper = new QueryWrapper<>();
                wrapper.orderByDesc("time");
                wrapper.select("id", "title", "time");
                Page<Article> page = new Page<>(currPage, pageSize);
                Page<Article> resultPage = articleService.getArticlePageList(page, wrapper);

                return Result.success("检索信息动态成功", MapUtil.builder()
                        .put("articleList", resultPage.getRecords())
                        .put("total", resultPage.getTotal())
                        .map());
            }
            case "hot" -> {
                QueryWrapper<Article> wrapper = new QueryWrapper<>();
                wrapper.orderByDesc("hotness");
                wrapper.select("id", "title");
                Page<Article> page = new Page<>(currPage, pageSize);
                Page<Article> resultPage = articleService.getArticlePageList(page, wrapper);

                return Result.success("检索热点新闻成功", MapUtil.builder()
                        .put("articleList", resultPage.getRecords())
                        .put("total", resultPage.getTotal())
                        .map());
            }
            case "photo" -> {
                QueryWrapper<Article> wrapper = new QueryWrapper<>();
                wrapper.orderByDesc("hotness");
                wrapper.select("id", "title", "image");
                Page<Article> page = new Page<>(currPage, pageSize);
                Page<Article> resultPage = articleService.getArticlePageList(page, wrapper);

                return Result.success("检索热点新闻成功", MapUtil.builder()
                        .put("articleList", resultPage.getRecords())
                        .put("total", resultPage.getTotal())
                        .map());
            }
            case "all" -> {
                Page<Article> page = new Page<>(currPage, pageSize);
                Page<Article> resultPage = articleService.getArticlePageList(page, null);

                return Result.success("检索信息动态成功", MapUtil.builder()
                        .put("articleList", resultPage.getRecords())
                        .put("total", resultPage.getTotal())
                        .map());
            }
            default -> {
                return Result.fail("查询失败");
            }
        }
    }

    @GetMapping("/article")
    public Result getArticleById(@RequestParam Long id){
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        wrapper.select("id", "title", "time", "source", "text", "image");
        Article article = articleService.getOne(wrapper);

        articleService.updateHotness(id);

        return Result.success("success", MapUtil.builder()
                .put("article", article)
                .map());
    }

}
