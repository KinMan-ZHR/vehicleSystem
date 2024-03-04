package com.scuse.volunteerhub.controller;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scuse.volunteerhub.common.lang.Result;
import com.scuse.volunteerhub.entity.Headline;
import com.scuse.volunteerhub.mapper.HeadlineMapper;
import com.scuse.volunteerhub.service.HeadlineService;
import com.scuse.volunteerhub.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class HeadlineController {
    @Autowired
    private HeadlineMapper headlineMapper;
    @Autowired
    private HeadlineService headlineService;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("/headlineList")
    public Result getHeadlineList(){
        // 创建分页对象，设置当前页和每页大小
        Page<Headline> page = new Page<>(1, 10);
        // 执行分页查询
        IPage<Headline> result = headlineMapper.selectPage(page,null);
        if(!result.getRecords().isEmpty()) {//如果不是空页
            // 使用 Java 8 Stream API 来转换数据
            List<Map<String, Object>> transformedList = result.getRecords().stream()
                    .map(entity -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("title", entity.getHeadlineTitle());
                        map.put("image", entity.getHeadlineImage());
                        map.put("link", entity.getHeadlineLink());
                        return map;
                    })
                    .collect(Collectors.toList());
            return Result.success("刷新成功", MapUtil.builder()
                    .put("headlineList", transformedList).map());
        }else{
            return Result.fail("刷新失败");
        }
    }
}


