package com.lweishi.service;

import com.google.gson.Gson;
import com.lweishi.exception.GlobalException;
import com.lweishi.utils.ResultCode;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Random;

/**
 * @Author geek
 * @CreateTime 2020/8/18 20:55
 * @Description 上传业务类
 */
@Slf4j
@Service
public class UploadService implements InitializingBean {

    @Value("${qiniu.url}")
    private String url;

    @Value("${qiniu.bucket}")
    private String bucket;

    @Autowired
    private UploadManager uploadManager;

    @Autowired
    private BucketManager bucketManager;

    /**
     * 定义七牛云上传的相关策略
     */
    private StringMap putPolicy;

    @Autowired
    private Auth auth;

    public String uploadImage(MultipartFile file) {
        String key = getRandomCharacterAndNumber(10) + ".png";//生成随机文件名
        String responseUrl = "";
        try{
            byte[] localFile = file.getBytes();
            Response response = uploadManager.put(localFile, key, getUploadToken());
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            responseUrl = responseUrl + url + putRet.key;
        }catch (Exception e){
            System.out.println(e);
            throw new GlobalException(ResultCode.ERROR, "上传图片失败！");
        }
        return responseUrl;
    }

    public String deleteImage(String key) {
        try {
            Response response = bucketManager.delete(this.bucket, key);
            int retry = 0;
            while (response.needRetry() && retry++ < 3) {
                response = bucketManager.delete(bucket, key);
            }
            return response.statusCode == 200 ? "删除成功!" : "删除失败!";
        } catch (Exception e) {
            throw new GlobalException(30005, "删除图片失败！");
        }

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

    @Override
    public void afterPropertiesSet() throws Exception {
        this.putPolicy = new StringMap();
        putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"width\":$(imageInfo.width), \"height\":${imageInfo.height}}");
    }

    /**
     * 获取上传凭证
     */
    private String getUploadToken() {
        return this.auth.uploadToken(bucket, null, 3600, putPolicy);
    }

}
