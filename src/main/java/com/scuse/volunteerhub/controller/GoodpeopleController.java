package com.scuse.volunteerhub.controller;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scuse.volunteerhub.common.dto.GoodPeopleListDto;
import com.scuse.volunteerhub.common.dto.ProjectListDto;
import com.scuse.volunteerhub.common.lang.Result;
import com.scuse.volunteerhub.entity.Goodpeople;
import com.scuse.volunteerhub.mapper.GoodpeopleMapper;
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
public class GoodpeopleController {
    @Autowired
    private GoodpeopleMapper goodpeopleMapper;

    @GetMapping("/goodPeopleList")
        public Result getGoodPeopleList(@RequestParam(name = "currPage", required = false, defaultValue = "1") int currPage,@RequestParam(name = "pageSize", required = false, defaultValue = "100") int pageSize){
        log.warn("执行（分页）查询道德好人操作");
        int total = goodpeopleMapper.selectList(null).size();
        // 创建分页对象，设置当前页和每页大小
            Page<Goodpeople> page = new Page<>(currPage, pageSize);
            // 执行分页查询
            IPage<Goodpeople> result = goodpeopleMapper.selectPage(page,null);
            if(!result.getRecords().isEmpty()) {//如果不是空页
                // 使用 Java 8 Stream API 来转换数据
                List<Map<String, Object>> transformedList = result.getRecords().stream()
                        .map(entity -> {
                            Map<String, Object> map = new HashMap<>();
                            map.put("id",entity.getId());
                            map.put("name", entity.getGoodpeopleName());
                            map.put("title", entity.getGoodpeopleTitle());
                            map.put("image", entity.getGoodpeopleImage());
                            map.put("time", entity.getGoodpeopleTime());
                            map.put("source", entity.getGoodpeopleSource());
                            map.put("text", entity.getGoodpeopleText());
                            return map;
                        })
                        .collect(Collectors.toList());
                return Result.success("刷新成功", MapUtil.builder()
                        .put("goodPeopleList", transformedList)
                        .put("total", total).map());
            }else {
                return Result.fail("刷新失败",MapUtil.builder()
                        .put("goodPeopleList",new String[0])
                        .put("total", 0).map());
            }
        }
    @DeleteMapping("/goodPeopleList")
        public Result deleteGoodPeopleList(@RequestParam Long id){
        log.warn("执行删除道德好人操作");
        if (goodpeopleMapper.deleteById(id)==1) {
            return Result.success("删除成功", null);
        } else {
            return Result.fail("删除失败");
        }
    }
    @PutMapping("/goodPeopleList")
        public Result updateGoodPeopleList(@Validated @RequestBody GoodPeopleListDto goodPeopleListDto){
        log.warn("执行更新道德好人操作");
        Goodpeople goodpeople = new Goodpeople((long) parseInt(goodPeopleListDto.getId()),goodPeopleListDto.getName(),goodPeopleListDto.getTitle(),goodPeopleListDto.getImage(),LocalDate.parse(goodPeopleListDto.getTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),goodPeopleListDto.getSource(),goodPeopleListDto.getText());
        if (goodpeopleMapper.updateById(goodpeople)==1) {
            return Result.success("更新成功", null);
        } else {
            return Result.fail("更新失败");
        }
    }
    @PostMapping("/goodPeopleList")
    public Result addGoodPeopleList(@Validated @RequestBody GoodPeopleListDto goodPeopleListDto){
        log.warn("执行添加道德好人操作");
        Goodpeople goodpeople = new Goodpeople(goodPeopleListDto.getName(),goodPeopleListDto.getTitle(),goodPeopleListDto.getImage(),LocalDate.parse(goodPeopleListDto.getTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),goodPeopleListDto.getSource(),goodPeopleListDto.getText());
        if (goodpeopleMapper.insert(goodpeople)==1) {
            return Result.success("添加成功", null);
        } else {
            return Result.fail("添加失败");
        }
    }
}


