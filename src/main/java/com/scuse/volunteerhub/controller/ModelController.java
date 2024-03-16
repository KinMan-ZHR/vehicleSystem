package com.scuse.volunteerhub.controller;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scuse.volunteerhub.common.dto.ModelListDto;
import com.scuse.volunteerhub.common.lang.Result;
import com.scuse.volunteerhub.entity.Model;
import com.scuse.volunteerhub.entity.Model;
import com.scuse.volunteerhub.entity.Model;
import com.scuse.volunteerhub.mapper.ModelMapper;
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
public class ModelController {
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/modelList")
    public Result getModelList(@RequestParam(name = "currPage", required = false, defaultValue = "1") int currPage,@RequestParam(name = "pageSize", required = false, defaultValue = "100") int pageSize){
        log.warn("执行（分页）查询道德模范操作");
        int total = modelMapper.selectList(null).size();
        // 创建分页对象，设置当前页和每页大小
        Page<Model> page = new Page<>(currPage, pageSize);
        // 执行分页查询
        IPage<Model> result = modelMapper.selectPage(page,null);
        if(!result.getRecords().isEmpty()) {//如果不是空页
            // 使用 Java 8 Stream API 来转换数据
            List<Map<String, Object>> transformedList = result.getRecords().stream()
                    .map(entity -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("id",entity.getId());
                        map.put("name", entity.getModelName());
                        map.put("title", entity.getModelTitle());
                        map.put("image", entity.getModelImage());
                        map.put("time", entity.getModelTime());
                        map.put("source", entity.getModelSource());
                        map.put("text", entity.getModelText());
                        return map;
                    })
                    .collect(Collectors.toList());
            return Result.success("刷新成功", MapUtil.builder()
                    .put("modelList", transformedList)
                    .put("total", total).map());
        }else {
            return Result.fail("刷新失败",MapUtil.builder()
                    .put("modelList",new String[0])
                    .put("total", 0).map());
        }
    }

    @DeleteMapping("/modelList")
    public Result deleteModelList(@RequestParam Long id){
        log.warn("执行删除道德模范操作");
        if (modelMapper.deleteById(id)==1) {
            return Result.success("删除成功", null);
        } else {
            return Result.fail("删除失败");
        }
    }
    @PutMapping("/modelList")
    public Result updateModelList(@Validated @RequestBody ModelListDto modelListDto){
        log.warn("执行更新道德模范操作");
        Model model = new Model((long) parseInt(modelListDto.getId()),modelListDto.getName(),modelListDto.getTitle(),modelListDto.getImage(), LocalDate.parse(modelListDto.getTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),modelListDto.getSource(),modelListDto.getText());
        if (modelMapper.updateById(model)==1) {
            return Result.success("更新成功", null);
        } else {
            return Result.fail("更新失败");
        }
    }
    @PostMapping("/modelList")
    public Result addModelList(@Validated @RequestBody ModelListDto modelListDto){
        log.warn("执行添加道德模范操作");
        Model model = new Model(modelListDto.getName(),modelListDto.getTitle(),modelListDto.getImage(),LocalDate.parse(modelListDto.getTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),modelListDto.getSource(),modelListDto.getText());
        if (modelMapper.insert(model)==1) {
            return Result.success("添加成功", null);
        } else {
            return Result.fail("添加失败");
        }
    }
}


