package com.scuse.volunteerhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scuse.volunteerhub.entity.User;
import com.scuse.volunteerhub.mapper.UserMapper;
import com.scuse.volunteerhub.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author DL
 * @since 2024-03-01 04:15:22
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public Page<User> getUserPageList(Page<User> page, QueryWrapper<User> queryWrapper) {
        return userMapper.selectPage(page, queryWrapper);
    }
}
