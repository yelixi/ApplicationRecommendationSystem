package com.example.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author whz
 * @date 2021/3/16 上午10:26
 **/
@Slf4j
@Service
public class ImageService {
    @Value("${media.dir}")
    String mediaDir;

    @Value("${spring.mvc.static-path-pattern}")
    String requestPath;

    @Deprecated
    public String saveFileToLocalServe(MultipartFile srcFile) {
        return saveFileToLocalServeWithDir(srcFile, null);
    }

    /**
     * 保存文件到本地文件夹
     * @param srcFile
     * @param dirname
     * @return java.lang.String
     */
    public String saveFileToLocalServeWithDir(MultipartFile srcFile, String dirname) {
        if (srcFile.isEmpty()) {
            return null;
        }
        if (dirname == null) {
            dirname = "";
        } else if (!dirname.endsWith("/")) {
            dirname = dirname + "/";
        }
        String fileName = srcFile.getOriginalFilename();
        assert fileName != null;
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        String newNameWithoutDir = UUID.randomUUID().toString().replace("-", "") + suffix;
        File dest = new File(new File(mediaDir + dirname).getAbsolutePath() + "/" + newNameWithoutDir);
        if (!dest.getParentFile().exists()) {
            if (!dest.getParentFile().mkdirs()) {
                log.error("写入文件时创建父目录异常，请检查权限");
                return null;
            }
        }
        try {
            srcFile.transferTo(dest);
        } catch (IOException e) {
            log.error("文件转存失败");
            e.printStackTrace();
            return null;
        }
        return requestPath.replace("**", "") + dirname + newNameWithoutDir;
    }
}
