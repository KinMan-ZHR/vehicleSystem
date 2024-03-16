package com.scuse.volunteerhub.controller;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.scuse.volunteerhub.common.dto.LoginDto;
import com.scuse.volunteerhub.common.dto.ProjectListDto;
import com.scuse.volunteerhub.common.dto.ProjectListDto;
import com.scuse.volunteerhub.common.dto.UpdateDto;
import com.scuse.volunteerhub.common.lang.Result;
import com.scuse.volunteerhub.entity.*;
import com.scuse.volunteerhub.entity.Project;
import com.scuse.volunteerhub.mapper.ProjectMapper;
import com.scuse.volunteerhub.service.UserService;
import com.scuse.volunteerhub.util.JwtUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.StringValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;
@Slf4j
@RestController
public class ProjectController {
    @Autowired
    ProjectMapper projectMapper;

    @PostMapping("/projectList")
    public Result getProjectList(@Validated @RequestBody ProjectListDto projectListDto){
        QueryWrapper<Project> queryWrapper = new QueryWrapper<>();
//         添加第一个代码段中的条件
        queryWrapper.and(wrapper -> wrapper
                .apply( "(" +projectListDto.getRegion().stream().map(num -> "project_region LIKE'%," + num + ",%'").collect(Collectors.joining(" OR "))+ ")")
                .apply("("+projectListDto.getType().stream().map(num -> "project_type LIKE'%," + num + ",%'").collect(Collectors.joining(" OR "))+ ")")
                .apply("("+projectListDto.getTarget().stream().map(num -> "project_target LIKE'%," + num + ",%'").collect(Collectors.joining(" OR "))+ ")")
                .apply("("+projectListDto.getScale().stream().map(num -> "project_scale LIKE'%," + num + ",%'").collect(Collectors.joining(" OR "))+ ")")
                .apply("("+projectListDto.getSize().stream().map(num -> "project_size LIKE'%," + num + ",%'").collect(Collectors.joining(" OR "))+ ")")
        );
        if(!projectListDto.getId().equals(""))
            queryWrapper.and(wrapper -> wrapper.eq("id",parseInt(projectListDto.getId()))); //
//        if(projectListDto.getState().get(0)!=1)
//            queryWrapper.and(wrapper -> wrapper.like("project_state", String.valueOf(projectListDto.getState().get(0))));
//         添加第二个代码段中的条件
        queryWrapper.and(wrapper ->
                wrapper.like("project_name", projectListDto.getName())
//                        .like("project_location", projectListDto.getLocation())
//                        .like("project_pub_date", projectListDto.getPbu_date())
                        .like("project_time_range", String.join(",", projectListDto.getTime_range()))
//                        .like("project_region", "," + projectListDto.getRegion() + ",")
//                        .like("project_type", "," + projectListDto.getType() + ",")
//                        .like("project_target", "," + projectListDto.getTarget() + ",")
//                        .like("project_scope", "," + projectListDto.getScope() + ",")
//                        .like("project_team_size", "," + projectListDto.getTeamSize() + ",")
//                        .like("project_cover", projectListDto.getCover())
//                        .like("project_description", projectListDto.getDescription())
//                        .like("project_address", projectListDto.getAddress())
        );
        List<Project> firstList = projectMapper.selectList(queryWrapper);//仅仅根据部分条件获得的第一个表,用于修改数据库的数据
//        System.out.println("firstList.size()="+firstList.size());
        if(firstList.size()!=0) {
            for (Project entity : firstList) {
                String[] timeRangeArray = entity.getProjectTimeRange().replaceAll("^[,]|[,]$", "").split(",");
                LocalDate today = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate startDate = LocalDate.parse(timeRangeArray[0], formatter);
                LocalDate endDate = LocalDate.parse(timeRangeArray[1], formatter);
//                System.out.println("today="+today);
//                System.out.println("endDate="+endDate);
//                System.out.println("1entity.getProjectState()="+entity.getProjectState());
                if (today.isBefore(startDate)) {
                    entity.setProjectState("2");
                } else if (today.isAfter(endDate)) {
//                    System.out.println("置为4了啊");
                    entity.setProjectState("4");
                }else{
                    entity.setProjectState("3");
                }
                projectMapper.updateById(entity);
//                System.out.println("projectListDto.getState().get(0)="+projectListDto.getState().get(0));
//                System.out.println("entity.getProjectState()="+entity.getProjectState());
//                System.out.println("tru?"+entity.getProjectState().equals(String.valueOf((projectListDto.getState().get(0)))));
            }
            if(projectListDto.getState().get(0) != 1) //如果state不为1，则得加上一个条件
                queryWrapper.and(wrapper -> wrapper.like("project_state", String.valueOf(projectListDto.getState().get(0))));
            Page<Project> page = new Page<>(projectListDto.getCurrPage(), projectListDto.getPageSize());  // 创建分页对象，设置当前页和每页大小
            IPage<Project> secondList = projectMapper.selectPage(page,queryWrapper);  // 此时条件已经是全部了
            int total = projectMapper.selectList(queryWrapper).size();
//            System.out.println("total="+total);
            if (total!=0) {
                // 使用 Java 8 Stream API 来转换数据
                List<Map<String, Object>> transformedList = secondList.getRecords().stream()
                        .map(entity -> {
                            Map<String, Object> map = new HashMap<>();
                            map.put("name", entity.getProjectName());
                            map.put("id", entity.getId());
                            map.put("location", entity.getProjectLocation());
                            map.put("pub_date", entity.getProjectPubDate());
                            String[] timeRangeArray = entity.getProjectTimeRange().replaceAll("^[,]|[,]$", "").split(",");
                            map.put("time_range", timeRangeArray);
                            int[] typeArray = Arrays.stream(entity.getProjectType().replaceAll("^[,]|[,]$", "").split(","))
                                    .mapToInt(Integer::parseInt)
                                    .toArray();
                            map.put("type", typeArray);
                            int[] targetArray = Arrays.stream(entity.getProjectTarget().replaceAll("^[,]|[,]$", "").split(","))
                                    .mapToInt(Integer::parseInt)
                                    .toArray();
                            map.put("target", targetArray);
                            int[] stateArray = Arrays.stream(entity.getProjectState().replaceAll("^[,]|[,]$", "").split(","))
                                    .mapToInt(Integer::parseInt)
                                    .toArray();
                            map.put("state", stateArray);
                            map.put("cover", entity.getProjectCover());
                            map.put("description", entity.getProjectDescription());
                            map.put("address", entity.getProjectAddress());
                            return map;
                        })
                        .collect(Collectors.toList());
                return Result.success("检索成功", MapUtil.builder()
                        .put("projectList", transformedList)
                        .put("total", total).map());
            }else{
                return Result.fail("检索失败",MapUtil.builder()
                        .put("projectList",new String[0])
                        .put("total", 0).map());
            }
        }else{
            return Result.fail("检索失败",MapUtil.builder()
                    .put("projectList",new String[0])
                    .put("total", 0).map());
        }
    }
    @GetMapping("/mapList")
    public Result getMapList(@RequestParam(name = "currPage", required = false, defaultValue = "1") int currPage,@RequestParam(name = "pageSize", required = false, defaultValue = "500") int pageSize){
        log.warn("执行（分页）查询志愿项目操作");
        int total = projectMapper.selectList(null).size();
        // 创建分页对象，设置当前页和每页大小
        Page<Project> page = new Page<>(currPage, pageSize);
        // 执行分页查询
        IPage<Project> result = projectMapper.selectPage(page,null);
        if(!result.getRecords().isEmpty()) {//如果不是空页
            // 使用 Java 8 Stream API 来转换数据
            List<Map<String, Object>> transformedList = result.getRecords().stream()
                    .map(entity -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("id",entity.getId());
                        map.put("name", entity.getProjectName());
                        map.put("location", entity.getProjectLocation());
                        map.put("pub_date", entity.getProjectPubDate());
                        String[] timeRangeArray = entity.getProjectTimeRange().replaceAll("^[,]|[,]$", "").split(",");
                        map.put("time_range", timeRangeArray);
                        int[] regionArray = Arrays.stream(entity.getProjectRegion().replaceAll("^[,]|[,]$", "").split(","))
                                .mapToInt(Integer::parseInt)
                                .toArray();
                        map.put("region", regionArray);
                        int[] typeArray = Arrays.stream(entity.getProjectType().replaceAll("^[,]|[,]$", "").split(","))
                                .mapToInt(Integer::parseInt)
                                .toArray();
                        map.put("type", typeArray);
                        int[] targetArray = Arrays.stream(entity.getProjectTarget().replaceAll("^[,]|[,]$", "").split(","))
                                .mapToInt(Integer::parseInt)
                                .toArray();
                        map.put("target", targetArray);

                        LocalDate today = LocalDate.now();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        LocalDate startDate = LocalDate.parse( timeRangeArray[0], formatter);
                        LocalDate endDate = LocalDate.parse(timeRangeArray[1], formatter);
                        if (today.isBefore(startDate)) {
                            entity.setProjectState("2");
                        } else if (today.isAfter(endDate)) {
                            entity.setProjectState("4");
                        }else{
                            entity.setProjectState("3");
                        }
                        int[] stateArray = Arrays.stream(entity.getProjectState().replaceAll("^[,]|[,]$", "").split(","))
                                .mapToInt(Integer::parseInt)
                                .toArray();
                        map.put("state", stateArray);
                        int[] scaleArray = Arrays.stream(entity.getProjectScale().replaceAll("^[,]|[,]$", "").split(","))
                                .mapToInt(Integer::parseInt)
                                .toArray();
                        map.put("scale", scaleArray);
                        int[] sizeArray = Arrays.stream(entity.getProjectSize().replaceAll("^[,]|[,]$", "").split(","))
                                .mapToInt(Integer::parseInt)
                                .toArray();
                        map.put("size", sizeArray);
                        map.put("cover", entity.getProjectCover());
                        map.put("description", entity.getProjectDescription());
                        map.put("address", entity.getProjectAddress());
                        double[] markerArray = Arrays.stream(entity.getProjectMarker().replaceAll("^[,]|[,]$", "").split(","))
                                .mapToDouble(Double::parseDouble)
                                .toArray();
                        map.put("marker", markerArray);
                        projectMapper.updateById(entity);
                        return map;
                    })
                    .collect(Collectors.toList());
            return Result.success("刷新成功", MapUtil.builder()
                    .put("mapList", transformedList)
                    .put("total", total).map());
        }else {
            return Result.fail("刷新失败",MapUtil.builder()
                    .put("mapList",new String[0])
                    .put("total", 0).map());
        }
    }
    @GetMapping("/projectList")
    public Result getProjectList(@RequestParam Long currPage,@RequestParam Long pageSize){
        int total = projectMapper.selectList(null).size();
        // 创建分页对象，设置当前页和每页大小
        Page<Project> page = new Page<>(currPage, pageSize);
        // 执行分页查询
        IPage<Project> result = projectMapper.selectPage(page,null);
            if (total!=0) {
                // 使用 Java 8 Stream API 来转换数据
                List<Map<String, Object>> transformedList = result.getRecords().stream()
                        .map(entity -> {
                            Map<String, Object> map = new HashMap<>();
                            map.put("name", entity.getProjectName());
                            map.put("id", entity.getId());
                            map.put("location", entity.getProjectLocation());
                            map.put("pub_date", entity.getProjectPubDate());
                            String[] timeRangeArray = entity.getProjectTimeRange().replaceAll("^[,]|[,]$", "").split(",");
                            map.put("time_range", timeRangeArray);
                            int[] typeArray = Arrays.stream(entity.getProjectType().replaceAll("^[,]|[,]$", "").split(","))
                                    .mapToInt(Integer::parseInt)
                                    .toArray();
                            map.put("type", typeArray);
                            int[] targetArray = Arrays.stream(entity.getProjectTarget().replaceAll("^[,]|[,]$", "").split(","))
                                    .mapToInt(Integer::parseInt)
                                    .toArray();
                            map.put("target", targetArray);

                            LocalDate today = LocalDate.now();
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                            LocalDate startDate = LocalDate.parse(timeRangeArray[0], formatter);
                            LocalDate endDate = LocalDate.parse(timeRangeArray[1], formatter);
                            if (today.isBefore(startDate)) {
                                entity.setProjectState("2");
                            }
                            else if (today.isAfter(endDate)) {
                                entity.setProjectState("4");
                            }
                            else  {
                                entity.setProjectState("3");
                            }
                            projectMapper.updateById(entity);
                            int[] stateArray = Arrays.stream(entity.getProjectState().replaceAll("^[,]|[,]$", "").split(","))
                                    .mapToInt(Integer::parseInt)
                                    .toArray();
                            map.put("state", stateArray);
                            map.put("cover", entity.getProjectCover());
                            map.put("description", entity.getProjectDescription());
                            map.put("address", entity.getProjectAddress());
                            return map;
                        })
                        .collect(Collectors.toList());
                return Result.success("检索成功", MapUtil.builder()
                        .put("projectList", transformedList)
                        .put("total", total).map());
            }else{
                return Result.fail("检索失败",MapUtil.builder()
                        .put("projectList",new String[0])
                        .put("total", 0).map());
            }
        }
    @DeleteMapping("/mapList")
    public Result deleteProjectList(@RequestParam Long id){
        log.warn("执行删除志愿项目操作");
        if (projectMapper.deleteById(id)==1) {
            return Result.success("删除成功", null);
        } else {
            return Result.fail("删除失败");
        }
    }
    @PutMapping("/mapList")
    public Result updateProjectList(@Validated @RequestBody ProjectListDto projectListDto){
        log.warn("执行更新志愿项目操作");
        Project project = new Project(
                (long) parseInt(projectListDto.getId()),
                projectListDto.getName(),
                projectListDto.getLocation(),
                LocalDate.parse(projectListDto.getPub_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                "," + String.join(",", projectListDto.getTime_range()) + ",",
                "," + String.join(",", projectListDto.getRegion().stream().map(Object::toString).collect(Collectors.toList())) + ",",
                "," + String.join(",", projectListDto.getType().stream().map(Object::toString).collect(Collectors.toList())) + ",",
                "," + String.join(",", projectListDto.getTarget().stream().map(Object::toString).collect(Collectors.toList())) + ",",
                String.join(",", projectListDto.getState().stream().map(Object::toString).collect(Collectors.toList())),
                "," + String.join(",", projectListDto.getScale().stream().map(Object::toString).collect(Collectors.toList())) + ",",
                "," + String.join(",", projectListDto.getSize().stream().map(Object::toString).collect(Collectors.toList())) + ",",
                projectListDto.getCover(),
                projectListDto.getDescription(),
                projectListDto.getAddress(),
                "," + projectListDto.getMarker().stream().map(Object::toString).collect(Collectors.joining(",")) + ",");
        if (projectMapper.updateById(project)==1) {
            return Result.success("更新成功", null);
        } else {
            return Result.fail("更新失败");
        }
    }
    @PostMapping("/mapList")
    public Result ProjectList(@Validated @RequestBody ProjectListDto projectListDto){
        log.warn("执行添加志愿项目操作");
        Project project = new Project(
                projectListDto.getName(),
                projectListDto.getLocation(),
                LocalDate.parse(projectListDto.getPub_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                "," + String.join(",", projectListDto.getTime_range()) + ",",
                "," + String.join(",", projectListDto.getRegion().stream().map(Object::toString).collect(Collectors.toList())) + ",",
                "," + String.join(",", projectListDto.getType().stream().map(Object::toString).collect(Collectors.toList())) + ",",
                "," + String.join(",", projectListDto.getTarget().stream().map(Object::toString).collect(Collectors.toList())) + ",",
                String.join(",", projectListDto.getState().stream().map(Object::toString).collect(Collectors.toList())),
                "," + String.join(",", projectListDto.getScale().stream().map(Object::toString).collect(Collectors.toList())) + ",",
                "," + String.join(",", projectListDto.getSize().stream().map(Object::toString).collect(Collectors.toList())) + ",",
                projectListDto.getCover(),
                projectListDto.getDescription(),
                projectListDto.getAddress(),
                "," + projectListDto.getMarker().stream().map(Object::toString).collect(Collectors.joining(",")) + ",");
        if (projectMapper.insert(project)==1) {
            return Result.success("添加成功", null);
        } else {
            return Result.fail("添加失败");
        }
    }
}
