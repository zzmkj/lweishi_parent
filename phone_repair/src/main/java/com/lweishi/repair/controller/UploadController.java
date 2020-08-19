package com.lweishi.repair.controller;

import com.lweishi.repair.service.UploadService;
import com.lweishi.utils.UnifyResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author geek
 * @CreateTime 2020/8/18 21:04
 * @Description 上传接口
 */
@RestController
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @PostMapping("/image")
    public UnifyResult uploadImage(MultipartFile file) {
        String imageUrl = uploadService.uploadImage(file);
        return UnifyResult.ok().data("imageUrl", imageUrl);
    }
}
