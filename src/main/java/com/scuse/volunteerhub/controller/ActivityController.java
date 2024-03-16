package com.scuse.volunteerhub.controller;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scuse.volunteerhub.common.dto.ActivityListDto;
import com.scuse.volunteerhub.common.lang.Result;
import com.scuse.volunteerhub.entity.Activity;
import com.scuse.volunteerhub.entity.Activity;
import com.scuse.volunteerhub.entity.Model;
import com.scuse.volunteerhub.mapper.ActivityMapper;
import com.scuse.volunteerhub.util.JwtUtils;
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
public class ActivityController {
    @Autowired
    private ActivityMapper activityMapper;
    @GetMapping("/activityList")
    public Result getActivityList(@RequestParam Long currPage, @RequestParam Long pageSize){
        int total = activityMapper.selectList(null).size();

        // 创建分页对象，设置当前页和每页大小
        Page<Activity> page = new Page<>(currPage, pageSize);
        // 执行分页查询
        IPage<Activity> result = activityMapper.selectPage(page,null);
        if(!result.getRecords().isEmpty()) {//如果不是空页
            // 使用 Java 8 Stream API 来转换数据
            List<Map<String, Object>> transformedList = result.getRecords().stream()
                    .map(entity -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("id", entity.getId());
                        map.put("title", entity.getActivityTitle());
                        map.put("image", entity.getActivityImage());
                        map.put("time", entity.getActivityTime());
                        map.put("source", entity.getActivitySource());
                        map.put("text", entity.getActivityText());
                        return map;
                    })
                    .collect(Collectors.toList());
            return Result.success("刷新成功", MapUtil.builder()
                    .put("activityList", transformedList)
                    .put("total", total).map());
        }else{
            return Result.fail("刷新失败",MapUtil.builder()
                    .put("activityList",new String[0])
                    .put("total", 0).map());
        }
    }
    @DeleteMapping("/activityList")
    public Result deleteActivityList(@RequestParam Long id){
        log.warn("执行删除志愿活动操作");
        if (activityMapper.deleteById(id)==1) {
            return Result.success("删除成功", null);
        } else {
            return Result.fail("删除失败");
        }
    }
    @PutMapping("/activityList")
    public Result updateActivityList(@Validated @RequestBody ActivityListDto activityListDto){
        log.warn("执行更新志愿活动操作");
        Activity activity = new Activity((long) parseInt(activityListDto.getId()),activityListDto.getTitle(),activityListDto.getImage(), LocalDate.parse(activityListDto.getTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),activityListDto.getSource(),activityListDto.getText());
        if (activityMapper.updateById(activity)==1) {
            return Result.success("更新成功", null);
        } else {
            return Result.fail("更新失败");
        }
    }
    @PostMapping("/activityList")
    public Result addActivityList(@Validated @RequestBody ActivityListDto activityListDto){
        log.warn("执行添加志愿活动操作");
        Activity activity = new Activity(activityListDto.getTitle(),activityListDto.getImage(),LocalDate.parse(activityListDto.getTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),activityListDto.getSource(),activityListDto.getText());
        if (activityMapper.insert(activity)==1) {
            return Result.success("添加成功", null);
        } else {
            return Result.fail("添加失败");
        }
    }
}


