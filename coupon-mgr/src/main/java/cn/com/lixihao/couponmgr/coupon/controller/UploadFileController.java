package cn.com.lixihao.couponmgr.coupon.controller;

import cn.com.lixihao.couponmgr.coupon.constants.ApiConstants;
import cn.com.lixihao.couponmgr.coupon.entity.UploadResult;
import com.alibaba.fastjson.JSON;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Iterator;

@Controller
@RequestMapping(value = "/action")
public class UploadFileController {

    private Logger DATA = LoggerFactory.getLogger(UploadFileController.class);

    @RequestMapping(value = "/upload")
    @ResponseBody
    public UploadResult upload(HttpServletRequest request) {
        DATA.info("[action]upload: 开始上传!");
        UploadResult uploadResult = new UploadResult();
        //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        //检查form中是否有enctype="multipart/form-data"
        if (multipartResolver.isMultipart(request)) {
            //将request变成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            //获取multiRequest 中所有的文件名
            Iterator iter = multiRequest.getFileNames();
            while (iter.hasNext()) {
                //一次遍历所有文件
                MultipartFile file = multiRequest.getFile(iter.next().toString());
                if (file != null) {
                    String originalFilename = file.getOriginalFilename();
                    String fileName = this.makeFileName(originalFilename);
                    String saveDir = this.makePath(originalFilename, ApiConstants.COUPON_UPLOAD_PATH);
                    String saveRealPath = saveDir + File.separator + fileName;
                    uploadResult.result = saveRealPath;
                    uploadResult.code = UploadResult.SUCCESS;
                    //上传
                    try {
                        file.transferTo(new File(ApiConstants.COUPON_UPLOAD_PATH + saveRealPath));
                        DATA.error("[action]upload: success->{}", ApiConstants.COUPON_UPLOAD_PATH + saveRealPath);
                    } catch (Exception e) {
                        DATA.error("[action]upload: exception->{}", e.getMessage());
                        uploadResult.code = UploadResult.FAIL;
                        uploadResult.result = "上传失败!";
                    }
                }
            }
        }
        DATA.info("[action]upload: response->{}", JSON.toJSONString(uploadResult));
        return uploadResult;
    }

    private String makeFileName(String fileName) {

        return DateTime.now().toString("yyyyMMddHHmmss") + "_" + fileName;
    }

    private String makePath(String fileName, String saveFilePath) {
        int hashCode = fileName.hashCode();
        int dir1 = hashCode & 0xf;
        int dir2 = hashCode & 0xf0 >> 4;
        String year = DateTime.now().getYear() + "";
        String savaRealDir = year + File.separator + dir1 + File.separator + dir2;
        File file = new File(saveFilePath + savaRealDir);
        // 需要先检查目录是否已创建，如果目录不存在，需要先创建
        if (!file.exists()) {
            file.mkdirs();// 创建多级目录用这个
        }
        return savaRealDir;
    }
}