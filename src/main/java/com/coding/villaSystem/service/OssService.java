package com.coding.villaSystem.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * oss文件上传业务层接口
 */
public interface OssService {

    //用于文件上传！
    String addHeadImage(MultipartFile file);
}
