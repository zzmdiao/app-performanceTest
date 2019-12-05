package com.iqianjin.appperformance.db.model;

import lombok.Data;

@Data
public class UploadPerformanceTestReportDTO {
    private String version;
    //1:Android, 2:IOS
    private Integer platForm;
    //1:debug包（可切换host）, 2:V3包（不可切换host）, 3:生产包（不可切换host
    private Integer packageType;

    private String uploadUser;

    private String remark;
}
