package com.scuse.volunteerhub.controller;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.scuse.volunteerhub.common.dto.LoginDto;
import com.scuse.volunteerhub.common.dto.RegisterDto;
import com.scuse.volunteerhub.common.lang.Result;
import com.scuse.volunteerhub.entity.User;
import com.scuse.volunteerhub.service.UserService;
import com.scuse.volunteerhub.util.JwtUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class AccountController {
    @Autowired
    UserService userService;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public Result login(@Validated @RequestBody LoginDto loginDto, HttpServletResponse response){
        User user = userService.getOne(new QueryWrapper<User>().eq("username", loginDto.getUsername()));
        Assert.notNull(user, "用户不存在");
        if(!user.getPassword().equals(SecureUtil.md5(loginDto.getPassword()))){
            return Result.fail("密码不正确");
        }

        // 一般把Jwt放在head里面，方便延期刷新
        String jwt = jwtUtils.generateToken(user.getId());

        response.setHeader("Authorization", jwt);
        response.setHeader("Access-control-Expose-Headers", "Authorization");
        userService.updateById(user);

        return Result.success("登录成功", MapUtil.builder()
                .put("user", MapUtil.builder()
                    .put("id", user.getId())
                    .put("username", user.getUsername())
                    .put("avatar", user.getAvatar())
                    .put("maritalStatus", user.getMaritalStatus())
                    .put("sex", user.getSex())
                    .put("birthdate", user.getBirthdate())
                    .put("email", user.getEmail())
                    .put("tel", user.getTel())
                    .put("address", user.getAddress())
                    .put("statement", user.getStatement())
                    .put("token", jwt)
                    .put("lastUpdate", user.getLastUpdate())
                    .put("admin", user.getAdmin())
                    .map()
                ).map());
    }

    @PostMapping("/register")
    public Result register(@Validated @RequestBody RegisterDto registerDto, HttpServletResponse response){
        User user = userService.getOne(new QueryWrapper<User>().eq("username", registerDto.getUsername()));
        if(user != null){
            return Result.fail("用户名已存在");
        }
        user = new User();
        user.setUsername(registerDto.getUsername());
        user.setPassword(SecureUtil.md5(registerDto.getPassword()));
        user.setEmail(registerDto.getEmail());
        user.setCreated(LocalDateTime.now());
        user.setLastUpdate(LocalDateTime.now().toLocalDate());
        userService.save(user);
        return Result.success("注册成功", MapUtil.builder()
                .put("user", MapUtil.builder()
                        .put("id", user.getId())
                        .put("username", user.getUsername())
                        .put("avatar", user.getAvatar())
                        .put("maritalStatus", user.getMaritalStatus())
                        .put("sex", user.getSex())
                        .put("birthdate", user.getBirthdate())
                        .put("email", user.getEmail())
                        .put("tel", user.getTel())
                        .put("address", user.getAddress())
                        .put("statement", user.getStatement())
                        .map()
                ).map());
    }

}
