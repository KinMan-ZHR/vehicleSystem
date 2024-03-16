package com.scuse.volunteerhub.controller;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scuse.volunteerhub.common.dto.HeadlineListDto;
import com.scuse.volunteerhub.common.lang.Result;
import com.scuse.volunteerhub.entity.Headline;
import com.scuse.volunteerhub.entity.Headline;
import com.scuse.volunteerhub.entity.Headline;
import com.scuse.volunteerhub.mapper.HeadlineMapper;
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
public class HeadlineController {
    @Autowired
    private HeadlineMapper headlineMapper;

    @GetMapping("/headlineList")
    public Result getHeadlineList(@RequestParam(name = "currPage", required = false, defaultValue = "1") int currPage,@RequestParam(name = "pageSize", required = false, defaultValue = "100") int pageSize){
        log.warn("执行（分页）查询头条操作");
        int total = headlineMapper.selectList(null).size();
        // 创建分页对象，设置当前页和每页大小
        Page<Headline> page = new Page<>(currPage, pageSize);
        // 执行分页查询
        IPage<Headline> result = headlineMapper.selectPage(page,null);
        if(!result.getRecords().isEmpty()) {//如果不是空页
            // 使用 Java 8 Stream API 来转换数据
            List<Map<String, Object>> transformedList = result.getRecords().stream()
                    .map(entity -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("id",entity.getId());
                        map.put("title", entity.getHeadlineTitle());
                        map.put("image", entity.getHeadlineImage());
                        map.put("time", entity.getHeadlineTime());
                        map.put("source", entity.getHeadlineSource());
                        map.put("text", entity.getHeadlineText());
                        return map;
                    })
                    .collect(Collectors.toList());
            return Result.success("刷新成功", MapUtil.builder()
                    .put("headlineList", transformedList)
                    .put("total", total).map());
        }else {
            return Result.fail("刷新失败",MapUtil.builder()
                    .put("headlineList",new String[0])
                    .put("total", 0).map());
        }
    }
    @DeleteMapping("/headlineList")
    public Result deleteHeadlineList(@RequestParam Long id){
        log.warn("执行删除头条操作");
        if (headlineMapper.deleteById(id)==1) {
            return Result.success("删除成功", null);
        } else {
            return Result.fail("删除失败");
        }
    }
    @PutMapping("/headlineList")
    public Result updateHeadlineList(@Validated @RequestBody HeadlineListDto headlineListDto){
        log.warn("执行更新头条操作");
        Headline headline = new Headline((long) parseInt(headlineListDto.getId()),headlineListDto.getTitle(),headlineListDto.getImage(), LocalDate.parse(headlineListDto.getTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),headlineListDto.getSource(),headlineListDto.getText());
        if (headlineMapper.updateById(headline)==1) {
            return Result.success("更新成功", null);
        } else {
            return Result.fail("更新失败");
        }
    }
    @PostMapping("/headlineList")
    public Result addHeadlineList(@Validated @RequestBody HeadlineListDto headlineListDto){
        log.warn("执行添加头条操作");
        Headline headline = new Headline(headlineListDto.getTitle(),headlineListDto.getImage(),LocalDate.parse(headlineListDto.getTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),headlineListDto.getSource(),headlineListDto.getText());
        if (headlineMapper.insert(headline)==1) {
            return Result.success("添加成功", null);
        } else {
            return Result.fail("添加失败");
        }
    }
}












