package com.scuse.volunteerhub.controller;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scuse.volunteerhub.common.dto.AdpicListDto;
import com.scuse.volunteerhub.common.lang.Result;
import com.scuse.volunteerhub.entity.Adpic;
import com.scuse.volunteerhub.entity.Adpic;
import com.scuse.volunteerhub.mapper.AdpicMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

@Slf4j

@RestController
public class AdpicController {
    @Autowired
    private AdpicMapper adpicMapper;
    @GetMapping("/adPicList")
    public Result getAdPicList(@RequestParam Long currPage, @RequestParam Long pageSize){
        int total = adpicMapper.selectList(null).size();
        // 创建分页对象，设置当前页和每页大小
        Page<Adpic> page = new Page<>(currPage, pageSize);
        // 执行分页查询
        IPage<Adpic> result = adpicMapper.selectPage(page,null);
        if(!result.getRecords().isEmpty()) {//如果不是空页
            // 使用 Java 8 Stream API 来转换数据
            List<Map<String, Object>> transformedList = result.getRecords().stream()
                    .map(entity -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("id", String.valueOf(entity.getId()));
                        map.put("title", entity.getAdpicTitle());
                        map.put("image", entity.getAdpicImage());
                        map.put("time", entity.getAdpicTime());
                        map.put("source", entity.getAdpicSource());
                        map.put("text", entity.getAdpicText());
                        return map;
                    })
                    .collect(Collectors.toList());
            return Result.success("刷新成功", MapUtil.builder()
                    .put("adPicList", transformedList)
                    .put("total", total).map());
        }else{
            return Result.fail("刷新失败",MapUtil.builder()
                    .put("adPicList",new String[0])
                    .put("total", 0).map());
        }
    }
    @DeleteMapping("/adPicList")
    public Result deleteAdpicList(@RequestParam Long id){
        log.warn("执行删除活动广告图片操作");
        if (adpicMapper.deleteById(id)==1) {
            return Result.success("删除成功", null);
        } else {
            return Result.fail("删除失败");
        }
    }
    @PutMapping("/adPicList")
    public Result updateAdpicList(@Validated @RequestBody AdpicListDto adpicListDto){
        log.warn("执行更新活动广告图片操作");
        Adpic adpic = new Adpic((long) parseInt(adpicListDto.getId()),adpicListDto.getTitle(),adpicListDto.getImage(), LocalDate.parse(adpicListDto.getTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),adpicListDto.getSource(),adpicListDto.getText());
        if (adpicMapper.updateById(adpic)==1) {
            return Result.success("更新成功", null);
        } else {
            return Result.fail("更新失败");
        }
    }
    @PostMapping("/adPicList")
    public Result addAdpicList(@Validated @RequestBody AdpicListDto adpicListDto){
        log.warn("执行添加活动广告图片操作");
        Adpic adpic = new Adpic(adpicListDto.getTitle(),adpicListDto.getImage(),LocalDate.parse(adpicListDto.getTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),adpicListDto.getSource(),adpicListDto.getText());
        if (adpicMapper.insert(adpic)==1) {
            return Result.success("添加成功", null);
        } else {
            return Result.fail("添加失败");
        }
    }
}


