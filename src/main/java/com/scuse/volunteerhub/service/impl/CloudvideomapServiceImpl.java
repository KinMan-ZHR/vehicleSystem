package com.scuse.volunteerhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scuse.volunteerhub.entity.Cloudvideomap;
import com.scuse.volunteerhub.mapper.CloudvideomapMapper;
import com.scuse.volunteerhub.service.CloudvideomapService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author DL
 * @since 2024-03-09 03:50:57
 */
@Service
public class CloudvideomapServiceImpl extends ServiceImpl<CloudvideomapMapper, Cloudvideomap> implements CloudvideomapService {
    @Autowired
    private CloudvideomapMapper cloudvideomapMapper;

    @Override
    public Page<Cloudvideomap> getCloudVideoPageList(Page<Cloudvideomap> page, QueryWrapper<Cloudvideomap> wrapper) {
        return cloudvideomapMapper.selectPage(page, wrapper);
    }
}
