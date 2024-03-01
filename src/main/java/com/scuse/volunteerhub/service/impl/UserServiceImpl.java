package com.scuse.volunteerhub.service.impl;

import com.scuse.volunteerhub.entity.User;
import com.scuse.volunteerhub.mapper.UserMapper;
import com.scuse.volunteerhub.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author DL
 * @since 2024-02-29 10:06:06
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
