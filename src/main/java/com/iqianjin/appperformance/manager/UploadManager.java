package com.iqianjin.appperformance.manager;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableMap;
import com.iqianjin.appperformance.App;
import com.iqianjin.appperformance.common.constant.PackageTypeEnum;
import com.iqianjin.appperformance.core.BaseAction;
import com.iqianjin.appperformance.core.MobileDevice;
import com.iqianjin.appperformance.core.android.AndroidUtil;
import com.iqianjin.appperformance.core.ios.IosUtil;
import com.iqianjin.appperformance.exception.ApplicationException;
import com.iqianjin.appperformance.util.FileMUtils;
import com.iqianjin.appperformance.util.TerminalUtils;
import com.iqianjin.appperformance.util.UUIDUtils;
import com.iqianjin.lego.contracts.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Component
@Slf4j
public class UploadManager {

    @Value("${uploadAndroidResultPath}")
    private String uploadAndroidResultPath;
    @Value("${iosIfusePath}")
    private String iosIfusePath;
    @Value("${uploadIosResultPath}")
    private String uploadIosResultPath;

    @Value("${web.upload-img-path}")
    private String uploadImgPath;
    @Value("${web.upload-video-path}")
    private String uploadVideoPath;
    @Value("${web.upload-apk-path}")
    private String uploadApkPath;
    @Value("${web.upload-ipa-path}")
    private String uploadIpaPath;
    @Value("${server.port}")
    private String serverPort;

    @Autowired
    private BaseAction baseAction;
    @Resource
    private AlertManager alertManager;

    private static final String UPLOAD_URL = "http://10.10.201.39:9002/newTestStage/testReport/uploadMore";
    private static UploadManager uploadManager;

    public synchronized static UploadManager getInstance() {
        if (uploadManager == null) {
            uploadManager = App.getBean(UploadManager.class);
        }
        return uploadManager;
    }

    public Result uploadFile(File uploadFile) {

        MultipartFile file = FileMUtils.fileToMultipartFile(uploadFile);
        if (file == null) {
            return Result.failure(-1, "文件不能为空");
        }

        String newFileName = UUIDUtils.getUUID() + "." + StringUtils.unqualify(file.getOriginalFilename());
        try {
            if (newFileName.endsWith(".jpg") || newFileName.endsWith(".png") || newFileName.endsWith(".tiff")) {
                // 不用绝对路径会报错
                file.transferTo(new File(new File(uploadImgPath).getAbsolutePath() + File.separator + newFileName));
            } else if (newFileName.endsWith(".mp4")) {
                file.transferTo(new File(new File(uploadVideoPath).getAbsolutePath() + File.separator + newFileName));
            } else if (newFileName.endsWith(".apk")) {
                file.transferTo(new File(new File(uploadApkPath).getAbsolutePath() + File.separator + newFileName));
            } else if (newFileName.endsWith(".ipa")) {
                file.transferTo(new File(new File(uploadIpaPath).getAbsolutePath() + File.separator + newFileName));
            } else {
                return Result.failure(-1, "暂不支持该格式文件上传");
            }
        } catch (IOException e) {
            log.error("transfer err", e);
            return Result.failure(-99, e.getMessage());
        }

        String downloadURL = "http://localhost:" + serverPort + "/" + newFileName;
        return Result.ok(ImmutableMap.of("downloadURL", downloadURL));
    }


    //statements上传的每个文件对应的场景,0-购买爱盈宝，1-购买月进宝，2-切换tab，3-浏览资金流水，4-浏览我的资产、优惠券、加息券，5-浏览出借记录
    public void uploadFile(String[] statements, String startTime) {
        //baseAction.getPlatform().get()
        if ( 1 == MobileDevice.ANDROID) {
            try {
                log.info("开始上次android文件到测试平台:{}", Arrays.toString(statements));
                postForString(UploadManager.getInstance().getResultFiles(MobileDevice.ANDROID), statements, startTime, MobileDevice.ANDROID);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                log.info("开始上次IOS文件到测试平台:{}", Arrays.toString(statements));
                postForString(UploadManager.getInstance().getResultFiles(MobileDevice.IOS), statements, startTime, MobileDevice.IOS);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void postForString(MultipartFile[] files, String[] statements, String startTime, Integer type) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(UPLOAD_URL);

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        // 把文件加到HTTP的post请求中
        for (MultipartFile f : files) {
            builder.addBinaryBody("files",
                    f.getInputStream(),
                    ContentType.APPLICATION_OCTET_STREAM,
                    f.getOriginalFilename());//添加文件
        }
        //添加文本类型参数
        for (String statement : statements) {
            builder.addTextBody("statements", statement);
        }


        if (type == MobileDevice.ANDROID) {
            builder.addTextBody("platForm", String.valueOf(MobileDevice.ANDROID));
            builder.addTextBody("version", AndroidUtil.getAppVersion());
        } else {
            builder.addTextBody("platForm", String.valueOf(MobileDevice.IOS));
            builder.addTextBody("version", IosUtil.getAppVersion());
        }

        builder.addTextBody("uploadUser", "robot");
        builder.addTextBody("packageType", PackageTypeEnum.TEST_PACKAGE.getType());
        builder.addTextBody("startTime", startTime);
        builder.addTextBody("remark", "app-performance");

        httpPost.setEntity(builder.build());
        CloseableHttpResponse response;
        String sResponse = null;
        try {
            response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();
            sResponse = EntityUtils.toString(responseEntity, "UTF-8");
            log.info("上传文件到测试平台返回url:{},sResponse:{}", UPLOAD_URL, sResponse);
            JSONObject jsonData = JSONObject.parseObject(sResponse);
            Integer result = Integer.parseInt(jsonData.get("code").toString());
            if (result != 1){
                log.error("上传文件到测试平台发生错误:{}", sResponse);
                String msg = "性能测试，上传文件到测试平台发生错误";
                alertManager.alert(msg, sResponse);
            }
        } catch (Exception e) {
            log.error("上传文件到测试平台异常:{}", e);
            String msg = "性能测试，上传文件到测试平台异常";
            alertManager.alert(msg, e);
            throw new ApplicationException("上传文件到测试平台异常");
        }

    }

    public MultipartFile[] getResultFiles(int type) {
        List<MultipartFile> list = new ArrayList<>();
        File file = null;
        if (type == MobileDevice.ANDROID) {
            file = new File(uploadAndroidResultPath);
        } else {
            file = new File(iosIfusePath + uploadIosResultPath);
        }

        File[] files = file.listFiles();
        for (File f : files) {
            if (f.getName().endsWith(".txt")) {
                list.add(FileMUtils.fileToMultipartFile(f));
            }
        }
        MultipartFile[] multipartFiles = new MultipartFile[list.size()];
        return list.toArray(multipartFiles);
    }

    public void delIosFile() {
        String shpath1 = System.getProperty("user.dir") + "/Upload/Shell/rmFile.sh";
        String resultPath = System.getProperty("user.dir") + File.separator + iosIfusePath + uploadIosResultPath;
        try {
            TerminalUtils.execute("/bin/sh " + shpath1 + " " + resultPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void moveFile(int type) {
        //将上传完的文件备份到Result目录
        String shpath = System.getProperty("user.dir") + "/Upload/Shell/moveFile.sh";
        String path;
        String rootPath = System.getProperty("user.dir") + "/Upload/Result";
        if (type == MobileDevice.ANDROID) {
            path = System.getProperty("user.dir") + File.separator + uploadAndroidResultPath;
        } else {
            path = System.getProperty("user.dir") + File.separator + iosIfusePath + uploadIosResultPath;
        }
        try {
            log.info("将上传完的txt文件备份到Reslut目录");
            TerminalUtils.execute("/bin/sh " + shpath + " " + path +" " + rootPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
