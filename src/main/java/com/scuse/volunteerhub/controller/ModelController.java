package com.scuse.volunteerhub.controller;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scuse.volunteerhub.common.lang.Result;
import com.scuse.volunteerhub.entity.Model;
import com.scuse.volunteerhub.mapper.ModelMapper;
import com.scuse.volunteerhub.service.ModelService;
import com.scuse.volunteerhub.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class ModelController {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ModelService modelService;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("/modelList")
    public Result getModelList(){
        // 创建分页对象，设置当前页和每页大小
        Page<Model> page = new Page<>(1, 10);
        // 执行分页查询
        IPage<Model> result = modelMapper.selectPage(page,null);
        if(!result.getRecords().isEmpty()) {//如果不是空页
            // 使用 Java 8 Stream API 来转换数据
            List<Map<String, Object>> transformedList = result.getRecords().stream()
                    .map(entity -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("name", entity.getModelName());
                        map.put("image", entity.getModelImage());
                        return map;
                    })
                    .collect(Collectors.toList());
            return Result.success("刷新成功", MapUtil.builder()
                    .put("modelList", transformedList).map());
        }else{
            return Result.fail("刷新失败");
        }
    }
}


