package com.scuse.volunteerhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scuse.volunteerhub.entity.Artical;
import com.scuse.volunteerhub.mapper.ArticalMapper;
import com.scuse.volunteerhub.service.ArticalService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author DL
 * @since 2024-03-05 04:23:35
 */
@Service
public class ArticalServiceImpl extends ServiceImpl<ArticalMapper, Artical> implements ArticalService {

    @Autowired
    private ArticalMapper articalMapper;

    @Override
    public Page<Artical> getArticalPageList(Page<Artical> page, Wrapper<Artical> queryWrapper) {
        return articalMapper.selectPage(page, queryWrapper);
    }
}
