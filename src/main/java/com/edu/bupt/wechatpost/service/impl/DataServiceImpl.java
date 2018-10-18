package com.edu.bupt.wechatpost.service.impl;

import com.edu.bupt.wechatpost.service.DataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

@Service
public class DataServiceImpl implements DataService {
    private static final Logger logger = LoggerFactory.getLogger(DataServiceImpl.class);

    private final String serverIp = "http://47.104.8.164:80/"; // ip域名（端口号）
    private final String imageBasePath = System.getProperty("user.dir").replace("\\","/")+"/image/";

    @Override
    public String uploadImage(MultipartFile image) throws IOException {
        logger.info("正在上传图片...");
        String imageName = "";
        if(image != null){
            try {
                //  图片名字
                imageName = image.getOriginalFilename();
                //  图片类型
                String suffixName = imageName.substring(imageName.lastIndexOf("."));
                //  随机字符串重命名图片，解决liunx下中文路径的图片显示问题
                imageName = UUID.randomUUID() + suffixName;
                logger.info("上传的图片名为：" + imageName);
                String imageUrl = imageBasePath + imageName;
                //  打开文件流，若目录不存在则新建
                File downloadFile = new File(imageUrl);
                if (!downloadFile.getParentFile().exists()) {
                    downloadFile.getParentFile().mkdirs();
                }
                //  保存文件到后台服务器
                image.transferTo(downloadFile);
                logger.info("上传图片成功，保存路径为: " + imageUrl + "\n访问路径为: " + serverIp+ "api/v1/wechatPost/download?imageName=" + imageName);
            } catch (IOException e) {
                logger.info("上传文件失败\n" + e.getMessage());
            }
        }
        return serverIp+ "api/v1/wechatPost/download?imageName=" + imageName;
    }

    @Override
    public void downloadImage(String imageName, HttpServletRequest request, HttpServletResponse response) throws IOException{
//        if(imageName == "") return 0;  // 空文件名，返回false
        if(imageName != "") {
            String imageUrl = imageBasePath + imageName;
            File image = new File(imageUrl);
            if (image.exists()) {
                logger.info("正在加载图片...");
                response.setContentType("application/force-download");// 设置强制下载不打开
                // response.addHeader("Content-Disposition","attachment;fileName=" + imageName);// 设置文件名
                byte[] buffer = new byte[1024 * 1024 * 8];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(imageUrl);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    logger.info("加载图片成功");
//                    return  1;
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.info("加载图片失败\n" + e.getMessage());
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
