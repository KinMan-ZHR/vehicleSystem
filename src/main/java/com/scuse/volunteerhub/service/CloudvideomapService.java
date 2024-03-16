package com.scuse.volunteerhub.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scuse.volunteerhub.entity.Cloudvideomap;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author DL
 * @since 2024-03-09 03:50:57
 */
public interface CloudvideomapService extends IService<Cloudvideomap> {
    Page<Cloudvideomap> getCloudVideoPageList(Page<Cloudvideomap> page, QueryWrapper<Cloudvideomap> wrapper);
}
