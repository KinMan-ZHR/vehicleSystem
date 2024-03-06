package com.scuse.volunteerhub.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scuse.volunteerhub.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author DL
 * @since 2024-03-01 04:15:22
 */
public interface UserService extends IService<User> {
    Page<User> getUserPageList(Page<User> page, QueryWrapper<User> queryWrapper);
}
