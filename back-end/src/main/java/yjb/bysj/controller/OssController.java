package yjb.bysj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import yjb.bysj.common_utils.Res;
import yjb.bysj.service.OssService;

@RestController
@RequestMapping("/oss")
@CrossOrigin
public class OssController {

    @Autowired
    private OssService ossService;

    //上传头像的方法，MultipartFile获取上传文件
    @PostMapping
    public Res uploadOssFile(MultipartFile file){
        //返回上传到oss的路径
        String url = ossService.uploadFileAvatar(file);
        System.out.println(url);

        return Res.ok().data("url", url);
    }

}
