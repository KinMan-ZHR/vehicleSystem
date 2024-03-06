package com.scuse.volunteerhub.controller;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scuse.volunteerhub.common.lang.Result;
import com.scuse.volunteerhub.entity.Imgmap;
import com.scuse.volunteerhub.entity.Videomap;
import com.scuse.volunteerhub.service.ImgmapService;
import com.scuse.volunteerhub.service.VideomapService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author DL
 * @since 2024-03-04 04:01:37
 */
@RestController
public class VideomapController {
    @Autowired
    private VideomapService videomapService;

    @Autowired
    private ImgmapService imgmapService;

    @Autowired
    private HttpServletRequest request;

    @GetMapping("/videoList/{pageCurr}/{pageSize}")
    public Result getVideoByPage(@PathVariable Integer currPage, @PathVariable Integer pageSize){
        Page<Videomap> page = new Page<>(currPage, pageSize);
        Page<Videomap> resultPage = videomapService.getUserPageList(page);
        return Result.success("查询成功", MapUtil.builder()
                .put("videoList", resultPage.getRecords())
                .map());
    }

    @PostMapping("/uploadVideo")
    public Result uploadVideo(@RequestParam MultipartFile file) throws IOException {
        Videomap videomap = videomapService.preprocessVideo(file);
        String coverRelativePath = videomapService.getVideoCover();

        String videoURL = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + videomap.getPath();
        String coverURL = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + coverRelativePath;
        videomap.setLink(videoURL);
        videomap.setCover(coverURL);
        videomapService.save(videomap);

        Imgmap imgmap = new Imgmap();
        imgmap.setPath(coverRelativePath);
        imgmap.setUrl(coverURL);
        imgmapService.save(imgmap);

        return Result.success("上传成功", MapUtil.builder()
                .put("video", videomap)
                .map());
    }

}
