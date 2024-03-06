package com.scuse.volunteerhub.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scuse.volunteerhub.entity.Artical;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author DL
 * @since 2024-03-05 04:23:35
 */
public interface ArticalService extends IService<Artical> {
    Page<Artical> getArticalPageList(Page<Artical> page, Wrapper<Artical> queryWrapper);
}
