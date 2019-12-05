package com.iqianjin.appperformance.manager;

import com.iqianjin.appperformance.common.constant.FilesTypeEnum;
import com.iqianjin.appperformance.core.MobileDevice;
import com.iqianjin.appperformance.db.model.UploadPerformanceTestReportDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class test {

    @Autowired
    private UploadManager uploadManager;

    @Test
    public  void testObj(){
        File file = new File("/Users/finup/IdeaProjects/app-performanceTest/8b34eafef2f14a97a18795945860c30b.mp4");
        uploadManager.uploadFile(file);
    }

    @Test
    public void test022(){
        String headers = "app: ;appinfo:123";
        String[] header = headers.split(";");
        for (String temp: header){
            System.out.println(temp.split(":")[0]);
            System.out.println(temp.split(":")[1]);
        }
    }

    @Test
    public void testFile(){
        String[] statements = {"1","2","3"};
        uploadManager.uploadFile(statements,"123");
    }

    @Test
    public void upload(){
        UploadPerformanceTestReportDTO uploadPerformanceVO = new UploadPerformanceTestReportDTO();

        uploadPerformanceVO.setPlatForm(1);
        uploadPerformanceVO.setVersion("7.3.1");
        uploadPerformanceVO.setPackageType(MobileDevice.ANDROID);
        uploadPerformanceVO.setUploadUser("性能测试");
        try {
            UploadManager.getInstance().postForString(UploadManager.getInstance().getResultFiles(2),
                    FilesTypeEnum.SWITCH_TAB.getValue(),"222", 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void delIosFileTest(){
        uploadManager.delIosFile();
    }


}
