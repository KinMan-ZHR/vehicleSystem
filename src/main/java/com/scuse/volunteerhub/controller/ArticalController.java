package com.scuse.volunteerhub.controller;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scuse.volunteerhub.common.dto.ArticleListDto;
import com.scuse.volunteerhub.common.lang.Result;
import com.scuse.volunteerhub.entity.Artical;
import com.scuse.volunteerhub.service.ArticalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author DL
 * @since 2024-03-05 04:23:35
 */
@RestController
public class ArticalController {
    @Autowired
    private ArticalService articalService;

    @GetMapping("/articalList")
    public Result getArticalByPage(@RequestBody ArticleListDto articleListDto){
        if (articleListDto.getType().equals("time")) {
            QueryWrapper<Artical> wrapper = new QueryWrapper<>();
            wrapper.orderByDesc("time");
            wrapper.select("id", "title", "time");
            Page<Artical> page = new Page<>(articleListDto.getCurrPage(), articleListDto.getPageSize());
            Page<Artical> resultPage = articalService.getArticalPageList(page, wrapper);

            return Result.success("查询成功", MapUtil.builder()
                    .put("articalList", resultPage.getRecords())
                    .put("total", resultPage.getTotal())
                    .map());
        } else {
            return Result.fail("查询失败，type错误");
        }


    }

}
