package com.scuse.volunteerhub.controller;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scuse.volunteerhub.common.lang.Result;
import com.scuse.volunteerhub.entity.Goodpeople;
import com.scuse.volunteerhub.mapper.GoodpeopleMapper;
import com.scuse.volunteerhub.service.GoodpeopleService;
import com.scuse.volunteerhub.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class GoodpeopleController {
    @Autowired
    private GoodpeopleMapper goodpeopleMapper;
    @Autowired
    private GoodpeopleService goodpeopleService;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("/goodPeopleList")
    public Result getGoodpeopleList(){
        // 创建分页对象，设置当前页和每页大小
        Page<Goodpeople> page = new Page<>(1, 10);
        // 执行分页查询
        IPage<Goodpeople> result = goodpeopleMapper.selectPage(page,null);
        if(!result.getRecords().isEmpty()) {//如果不是空页
            // 使用 Java 8 Stream API 来转换数据
            List<Map<String, Object>> transformedList = result.getRecords().stream()
                    .map(entity -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("name", entity.getGoodpeopleName());
                        map.put("image", entity.getGoodpeopleImage());
                        return map;
                    })
                    .collect(Collectors.toList());
            return Result.success("刷新成功", MapUtil.builder()
                    .put("goodpeopleList", transformedList).map());
        }else {
            return Result.fail("刷新失败");
        }
    }
}


