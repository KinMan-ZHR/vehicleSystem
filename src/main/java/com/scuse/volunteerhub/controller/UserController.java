package com.scuse.volunteerhub.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scuse.volunteerhub.common.dto.PasswordDto;
import com.scuse.volunteerhub.common.dto.UpdateDto;
import com.scuse.volunteerhub.common.lang.Result;
import com.scuse.volunteerhub.entity.Imgmap;
import com.scuse.volunteerhub.entity.User;
import com.scuse.volunteerhub.service.ImgmapService;
import com.scuse.volunteerhub.service.UserService;
import com.scuse.volunteerhub.util.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author DL
 * @since 2024-02-27 02:57:27
 */
@Slf4j
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ImgmapService imgmapService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private HttpServletRequest request;

    @Value("${web.avatar-path}")
    private String uploadPath;

    @GetMapping("/userList")
    public Result getUserByPage(@RequestParam(defaultValue = "100") Integer pageSize,
                                @RequestParam(defaultValue = "1") Integer currPage){
        log.warn("获取用户分页列表：pageSize = " + pageSize + ", currPage = " + currPage);

        Page<User> page = new Page<>(currPage, pageSize);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.select("id", "username", "sex", "marital_status", "birthdate", "email", "tel", "address", "statement", "avatar", "password", "admin");
        Page<User> resultPage = userService.getUserPageList(page, wrapper);
        return Result.success("查询成功", MapUtil.builder()
                .put("userList", resultPage.getRecords())
                .put("total", resultPage.getTotal())
                .put("Number", pageSize)
                .map());
    }

    @DeleteMapping("/userList/{id}")
    public Result deleteUser(@PathVariable Long id) {
        log.warn("删除用户：id = " + id);
        boolean deleted = userService.removeById(id);

        if (deleted) {
            return Result.success("删除成功", null);
        } else {
            return Result.fail("删除失败");
        }
    }

    @PutMapping("/userList")
    public Result updateUser(@Validated @RequestBody UpdateDto updateDto) {
        log.warn("更新用户信息：id = " + updateDto.getId() + ", username = " + updateDto.getUsername());

        User user = userService.getById(updateDto.getId());

        BeanUtils.copyProperties(updateDto, user);
        user.setLastUpdate(LocalDateTime.now().toLocalDate());

        boolean updated = userService.updateById(user);

        if (updated) {
            User user_res = userService.getById(updateDto.getId());
            return Result.success("更新成功", MapUtil.builder()
                    .put("user", MapUtil.builder()
                            .put("id", user_res.getId())
                            .put("username", user_res.getUsername())
                            .put("avatar", user_res.getAvatar())
                            .put("maritalStatus", user_res.getMaritalStatus())
                            .put("sex", user_res.getSex())
                            .put("birthdate", user_res.getBirthdate())
                            .put("email", user_res.getEmail())
                            .put("tel", user_res.getTel())
                            .put("address", user_res.getAddress())
                            .put("statement", user_res.getStatement())
                            .put("lastUpdate", user_res.getLastUpdate())
                            .map()
                    ).map());
        } else {
            return Result.fail("修改失败");
        }
    }

    @PostMapping("/editAvatar")
    public Result uploadAvatar(@RequestParam MultipartFile avatar, @RequestParam Long id) throws IOException {
        String fileName = UUID.randomUUID().toString() + "_" + avatar.getOriginalFilename();
        String relativePath = "/" + fileName;

        // 保存文件到文件系统
        Files.write(Paths.get(uploadPath, fileName), avatar.getBytes());

        // 存储相对路径到数据库
        Imgmap imgmap = new Imgmap();
        imgmap.setPath(relativePath);

        String imageURL = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + imgmap.getPath();
        imgmap.setUrl(imageURL);
        imgmapService.save(imgmap);

        // 更新用户头像
        User user = userService.getById(id);
        user.setAvatar(imageURL);
        user.setLastUpdate(LocalDateTime.now().toLocalDate());
        userService.updateById(user);

        return Result.success("上传成功", MapUtil.builder()
                .put("url", imageURL)
                .put("avatar", MapUtil.builder()
                        .put("name", avatar.getOriginalFilename())
                        .put("percent", 0)
                        .put("raw", avatar.getBytes())
                        .put("size", avatar.getSize())
                        .put("status", "done")
                        .put("type", avatar.getContentType())
                        .put("lastUpdate", user.getLastUpdate())
                        .map())
                .map());
    }

    @PostMapping("/editPassword")
    public Result editPassword(@RequestBody PasswordDto passwordDto) {
        User user = userService.getById(passwordDto.getId());
        if (!user.getPassword().equals(SecureUtil.md5(passwordDto.getOldPassword()))) {
            return Result.fail("密码不正确");
        }

        user.setPassword(SecureUtil.md5(passwordDto.getNewPassword()));
        user.setLastUpdate(LocalDateTime.now().toLocalDate());
        userService.updateById(user);

        return Result.success("密码修改成功");
    }

}