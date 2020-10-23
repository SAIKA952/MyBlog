package yjb.bysj.service;

import org.springframework.web.multipart.MultipartFile;

public interface OssService {
    // 上传头像到oss服务器
    String uploadFileAvatar(MultipartFile file);
}
