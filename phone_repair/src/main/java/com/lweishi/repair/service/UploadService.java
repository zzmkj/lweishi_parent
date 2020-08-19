package com.lweishi.repair.service;

import com.google.gson.Gson;
import com.lweishi.exception.GlobalException;
import com.lweishi.utils.ResultCode;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Random;

/**
 * @Author geek
 * @CreateTime 2020/8/18 20:55
 * @Description 上传业务类
 */
@Slf4j
@Service
public class UploadService {

    public String uploadImage(MultipartFile file) {
        String qiniuUrl = "http://qiniu.lweishi.com/";
        Configuration configuration = new Configuration(Region.region2());
        UploadManager uploadManager = new UploadManager(configuration);
        String accessKey = "wZmeUUMFKMBInwI303vOCEr81JaDJErXXQpZOvPX";
        String secretKey = "kfTJOI0mVLwEkXNZ_-7FKkAoA7LzdyaBIFM3VQRX";
        String bucket = "zzm-blog";
        String key = getRandomCharacterAndNumber(10) + ".png";//生成随机文件名
        Auth auth = Auth.create(accessKey,secretKey);
        String uptoken = auth.uploadToken(bucket);
        String responseUrl = "";
        try{
            byte[] localFile = file.getBytes();
            Response response = uploadManager.put(localFile,key,uptoken);
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            responseUrl = responseUrl + qiniuUrl + putRet.key;
        }catch (Exception e){
            System.out.println(e);
            throw new GlobalException(ResultCode.ERROR, "上传图片失败！");
        }
        return responseUrl;
    }

    public static String getRandomCharacterAndNumber(int length) {
        String val = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // 输出字母还是数字
            // 字符串
            if ("char".equalsIgnoreCase(charOrNum)) {
                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; // 取得大写字母还是小写字母
                val += (char) (choice + random.nextInt(26));
                // int choice = 97; // 指定字符串为小写字母
                val += (char) (choice + random.nextInt(26));
            } else if ("num".equalsIgnoreCase(charOrNum)) { // 数字
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }
}
