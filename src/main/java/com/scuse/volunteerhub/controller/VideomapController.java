package com.scuse.volunteerhub.controller;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.scuse.volunteerhub.common.lang.Result;
import com.scuse.volunteerhub.entity.Imgmap;
import com.scuse.volunteerhub.entity.Videomap;
import com.scuse.volunteerhub.service.ImgmapService;
import com.scuse.volunteerhub.service.VideomapService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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

    @GetMapping("/changeVideoList")
    public Result changeVideoList(@RequestParam Integer id1, @RequestParam Integer id2) throws IOException{
        Videomap video1 = videomapService.getById(id1);
        Videomap video2 = videomapService.getById(id2);

        Long temp = video1.getSeqId();
        video1.setSeqId(video2.getSeqId());
        video2.setSeqId(temp);

        videomapService.updateById(video1);
        videomapService.updateById(video2);
        return Result.success("置换成功");
    }

    @GetMapping("/videoList")
    public Result getVideoByPage(@RequestParam Long id){
//        Page<Videomap> page = new Page<>(1, 10);
//        Page<Videomap> page = new Page<>(currPage, pageSize);
//        Page<Videomap> resultPage = videomapService.getVideoPageList(page);

        QueryWrapper<Videomap> wrapper = new QueryWrapper<>();
        wrapper.select("id", "date", "title", "link", "cover", "description", "userid", "seq_id");
        wrapper.eq("userid", id);
        wrapper.orderByAsc("seq_id");
        List<Videomap> resultlist =  videomapService.getVideoList(wrapper);
        return Result.success("查询成功", MapUtil.builder()
                .put("videoList", resultlist)
                .map());
    }

    @PostMapping("/uploadVideo")
    public Result uploadVideo(@RequestParam MultipartFile file, @RequestParam Long id) throws IOException {
        Videomap videomap = videomapService.preprocessVideo(file);
        String coverRelativePath = videomapService.getVideoCover();

        String videoURL = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + videomap.getPath();
        String coverURL = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + coverRelativePath;
        videomap.setLink(videoURL);
        videomap.setCover(coverURL);
        videomap.setUserid(id);

        Imgmap imgmap = new Imgmap();
        imgmap.setPath(coverRelativePath);
        imgmap.setUrl(coverURL);
        imgmapService.save(imgmap);

        // 外键约束，必须放在imgmap后面
        videomapService.save(videomap);

        Videomap videoseq_set = videomapService.getOne(new QueryWrapper<Videomap>().eq("link", videoURL));
        videoseq_set.setSeqId(videoseq_set.getId());

        videomapService.updateById(videoseq_set);

        return Result.success("上传成功", MapUtil.builder()
                .put("video", videoseq_set)
                .map());
    }

    @DeleteMapping("/videoList")
    public Result deleteVideo(@RequestParam Long id){
        videomapService.removeById(id);
        return Result.success("删除成功");
    }

//    @GetMapping("/cloudVideoList")
//    public Result getCloudVideoList(){
//        QueryWrapper<Videomap> wrapper = new QueryWrapper<>();
//        wrapper.select("date", "title", "link", "cover", "description");
//        wrapper.eq("userid", -1);
//        List<Videomap> resultlist =  videomapService.getVideoList(wrapper);
//        return Result.success("查询成功", MapUtil.builder()
//                .put("videoList", resultlist)
//                .map());
//    }

}
