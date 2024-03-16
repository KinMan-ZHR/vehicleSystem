package com.scuse.volunteerhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scuse.volunteerhub.entity.Videomap;
import com.scuse.volunteerhub.mapper.VideomapMapper;
import com.scuse.volunteerhub.service.ImgmapService;
import com.scuse.volunteerhub.service.VideomapService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.Data;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author DL
 * @since 2024-03-04 04:01:37
 */
@Service
public class VideomapServiceImpl extends ServiceImpl<VideomapMapper, Videomap> implements VideomapService {
    @Autowired
    private VideomapMapper videomapMapper;

    @Value("${web.video-path}")
    private String uploadPath;

    @Value("${web.videocover-path}")
    private String coverPath;

    private String videoPath;

    @Override
    public Videomap preprocessVideo(MultipartFile file) throws IOException {
        Videomap videomap = new Videomap();
        if (file.getOriginalFilename() != null) {
            videomap.setTitle(file.getOriginalFilename().split("\\.")[0]);
        }
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        String relativePath = "/" + fileName;

        // 保存文件到文件系统
        videoPath = uploadPath + fileName;
        Files.write(Paths.get(uploadPath, fileName), file.getBytes());

        videomap.setPath(relativePath);
        videomap.setDate(LocalDate.now());
        videomap.setDescription(file.getOriginalFilename());

        return videomap;
    }

    @Override
    public String getVideoCover() throws IOException {
        String[] coverNameArray = videoPath.split("\\.")[0].split("/");
        String coverFilename = coverNameArray[coverNameArray.length - 1] + ".jpg";
        String coverRelativePath = "/" + coverFilename;
        File outputCoverFile = new File(Paths.get(coverPath, coverFilename).toString());
        try (FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(videoPath)) {
            grabber.start();
            Frame frame = grabber.grabImage();
            if (frame != null) {
                Java2DFrameConverter converter = new Java2DFrameConverter();
                BufferedImage image = converter.getBufferedImage(frame);

                ImageIO.write(image, "jpg", outputCoverFile);
            } else {
                log.error("Failed to get video cover");
            }

            grabber.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return coverRelativePath;
    }

    @Override
    public Page<Videomap> getVideoPageList(Page<Videomap> page) {
        return videomapMapper.selectPage(page, null);
    }

    @Override
    public List<Videomap> getVideoList(QueryWrapper<Videomap> wrapper) {
        return videomapMapper.selectList(wrapper);
    }


}
