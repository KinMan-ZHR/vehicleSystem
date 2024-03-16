package com.scuse.volunteerhub.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.db.Db;
import cn.hutool.db.ds.simple.SimpleDSFactory;
import com.scuse.volunteerhub.common.lang.Result;
import com.scuse.volunteerhub.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.Map;

@Slf4j
@RestController
public class DatabackupController {
    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest request;

    @Value("${web.backupSql-path}")
    private String backupfolder;

    private static void existsFile(File file) {
        // 判断文件路径是否存在,不存在新建
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @GetMapping("/beifen")
    public Result databackup() throws IOException, InterruptedException {
        log.warn("数据库备份");
        String pathFileName = backupfolder + "backup.sql";
        PrintWriter printWriter = null;
        BufferedReader bufferedReader = null;
        existsFile(new File(pathFileName));
        printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(pathFileName), "utf8"));
        Process process = null;

        String command = "mysqldump -uroot -proot --host=127.0.0.1 --port=3306 --databases volunteerhub";
        // 替换 <> 中的内容为你实际的用户名、密码、数据库名和输出路径
        process = Runtime.getRuntime().exec(command);
        InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream(), "utf8");
        bufferedReader = new BufferedReader(inputStreamReader);

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            printWriter.println(line);
        }

        printWriter.flush();
        printWriter.close();
        //0 表示线程正常终止。
        if (process.waitFor() == 0) {
            // 线程正常执行
            log.info("【备份数据库】SUCCESS，SQL文件：{}", pathFileName);
        } else {
            // 线程终止
            throw new RuntimeException("【备份数据库】ERROR");
        }

        String fileURL = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/backup.sql";
        return Result.success("备份成功", MapUtil.builder()
                .put("fileURL", fileURL)
                .map());
    }

    @GetMapping("/log")
    public Result getLogs() throws IOException {
        log.warn("获取日志");

        String logURL = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/volunteerhub-service.log";
        return Result.success("获取日志成功", MapUtil.builder()
                .put("logURL", logURL)
                .map());
    }
}
