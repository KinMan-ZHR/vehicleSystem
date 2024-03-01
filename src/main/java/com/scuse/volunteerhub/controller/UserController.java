package com.scuse.volunteerhub.controller;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.scuse.volunteerhub.common.dto.UpdateDto;
import com.scuse.volunteerhub.common.lang.Result;
import com.scuse.volunteerhub.entity.User;
import com.scuse.volunteerhub.service.UserService;
import com.scuse.volunteerhub.util.JwtUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author DL
 * @since 2024-02-27 02:57:27
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("/userList")
    public Result getUserById(@RequestParam Long id){
        User user = userService.getById(id);

        if (user != null) {
            String jwt = jwtUtils.generateToken(user.getId());
            return Result.success("查询成功", MapUtil.builder()
                    .put("id", user.getId())
                    .put("username", user.getUsername())
                    .put("email", user.getEmail())
                    .put("token", jwt)
                    .map());
        } else {
            return Result.fail("查询失败");
        }
    }

    @DeleteMapping("/userList/{id}")
    public Result deleteUser(@PathVariable Long id) {
        boolean deleted = userService.removeById(id);

        if (deleted) {
            return Result.success("删除成功", null);
        } else {
            return Result.fail("删除失败");
        }
    }

    @PutMapping("/userList")
    public Result updateUser(@Validated @RequestBody UpdateDto updateDto) {
        User user = userService.getOne(new QueryWrapper<User>().eq("username", updateDto.getUsername()));
        BeanUtils.copyProperties(updateDto, user);

        boolean updated = userService.updateById(user);

        if (updated) {
            return Result.success("修改成功", MapUtil.builder()
                    .put("id", user.getId())
                    .put("username", user.getUsername())
                    .put("email", user.getEmail())
                    .put("token", jwtUtils.generateToken(user.getId()))
                    .map());
        } else {
            return Result.fail("修改失败");
        }
    }

}
