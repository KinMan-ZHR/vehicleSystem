package com.scuse.volunteerhub.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scuse.volunteerhub.entity.Videomap;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author DL
 * @since 2024-03-04 04:01:37
 */
public interface VideomapService extends IService<Videomap> {
    Videomap preprocessVideo(MultipartFile file) throws IOException;
    String getVideoCover() throws IOException;

    Page<Videomap> getUserPageList(Page<Videomap> page);
}
