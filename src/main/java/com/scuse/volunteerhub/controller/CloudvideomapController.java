package com.scuse.volunteerhub.controller;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scuse.volunteerhub.common.dto.CloudvideoDto;
import com.scuse.volunteerhub.common.lang.Result;
import com.scuse.volunteerhub.entity.Cloudvideomap;
import com.scuse.volunteerhub.service.CloudvideomapService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author DL
 * @since 2024-03-09 03:50:57
 */
@Slf4j
@RestController
public class CloudvideomapController {
    @Autowired
    private CloudvideomapService cloudvideomapService;

    @Autowired
    private HttpServletRequest request;

    @PostMapping("/addCloudVideoList")
    public Result addCloudVideo(@RequestBody Cloudvideomap cloudvideomap){
        log.warn("添加云视频：" + cloudvideomap);

        cloudvideomapService.save(cloudvideomap);
        return Result.success("添加成功");
    }

    @DeleteMapping("/cloudVideoList")
    public Result deleteCloudVideo(@RequestParam Long id){
        log.warn("删除云视频：" + id);

        cloudvideomapService.removeById(id);
        return Result.success("删除成功");
    }

    @PutMapping("/cloudVideoList")
    public Result updateCloudVideo(@RequestBody Cloudvideomap cloudvideomap){
        log.warn("更新云视频：" + cloudvideomap);

        cloudvideomapService.updateById(cloudvideomap);
        return Result.success("更新成功");
    }

    @PostMapping("/cloudVideoList")
    public Result getCloudVideoByPage(@RequestBody CloudvideoDto cloudvideoDto){
        List<Cloudvideomap> list = cloudvideomapService.list();
        for (Cloudvideomap cloudvideomap : list) {
            String new_link = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/" + cloudvideomap.getTitle() + ".mp4";
            cloudvideomap.setLink(new_link);
            cloudvideomapService.updateById(cloudvideomap);
        }

        QueryWrapper<Cloudvideomap> wrapper = new QueryWrapper<>();
        wrapper.in("type", cloudvideoDto.getType());
        Page<Cloudvideomap> page = new Page<>(cloudvideoDto.getCurrPage(), cloudvideoDto.getPageSize());
        Page<Cloudvideomap> resultPage = cloudvideomapService.getCloudVideoPageList(page, wrapper);
        return Result.success("检索成功", MapUtil.builder()
                .put("videoList", resultPage.getRecords())
                .put("total", resultPage.getTotal())
                .map());
    }

    @GetMapping("/searchCloudVideoList")
    public Result searchCloudVideoByPage(@RequestParam String keyword,
                                         @RequestParam(defaultValue = "1") Integer currPage,
                                         @RequestParam(defaultValue = "100") Integer pageSize){

        QueryWrapper<Cloudvideomap> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(!StringUtils.isEmpty(keyword), "title", keyword)
                .or().like(!StringUtils.isEmpty(keyword), "description", keyword);

        String select_sql = "*, (LENGTH(title) - LENGTH(REPLACE(title, '" + keyword + "', ''))) * 5 + LENGTH(description) - LENGTH(REPLACE(description, '" + keyword + "', '')) AS match_length";
        queryWrapper.select(select_sql)
                .orderByDesc("match_length");

        Page<Cloudvideomap> page = new Page<>(currPage, pageSize);
        Page<Cloudvideomap> resultPage = cloudvideomapService.getCloudVideoPageList(page, queryWrapper);
        return Result.success("检索成功", MapUtil.builder()
                .put("videoList", resultPage.getRecords())
                .put("total", resultPage.getTotal())
                .map());
    }

    @GetMapping("/changeURL")
    public Result changeUrl() {
        List<Cloudvideomap> list = cloudvideomapService.list();
        for (Cloudvideomap cloudvideomap : list) {
            cloudvideomap.setLink(cloudvideomap.getTitle() + ".mp4");
            cloudvideomapService.updateById(cloudvideomap);
        }
        return Result.success("修改成功");
    }
    //将前端传来的视频列表绑定到用户，即将视频列表的userid字段改为用户id
    //@PostMapping("/bindVideoList")

}
