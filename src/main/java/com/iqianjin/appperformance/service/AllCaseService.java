package com.iqianjin.appperformance.service;

import com.iqianjin.appperformance.common.constant.FilesTypeEnum;
import com.iqianjin.appperformance.core.MobileDevice;
import com.iqianjin.appperformance.core.MobileDeviceHolder;
import com.iqianjin.appperformance.manager.AlertManager;
import com.iqianjin.appperformance.manager.InitManger;
import com.iqianjin.appperformance.manager.LoginManager;
import com.iqianjin.lego.contracts.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class AllCaseService {

    @Autowired
    private SwtichTabService swtichTabService;
    @Autowired
    private BrowLendRecordService browLendRecordService;
    @Autowired
    private BrowFundFlowService browFundFlowService;
    @Autowired
    private BrowMyAssertsService browMyAssertsService;
    @Autowired
    private InvestProductService investProductService;
    @Autowired
    private InitManger initManger;
    @Autowired
    private LoginManager loginManager;
    @Resource
    private AlertManager alertManager;

    private MobileDevice mobileDevice;

    public Result allCase(Integer num, String deviceId, String proUser, String proPass, String proEnv
            , String testUser, String testPass, String testEnv) {
        mobileDevice = MobileDeviceHolder.get(deviceId);
        try {
            initManger.init(deviceId, proEnv, proUser, proPass);
            swtichTabService.swtichTab(num);
            initManger.stopAndroidMonitor();
            initManger.stopIosMonitor();
            browLendRecordService.browLendRecord(num);
            initManger.stopAndroidMonitor();
            initManger.stopIosMonitor();
            browFundFlowService.browFundFlow(num);
            initManger.stopAndroidMonitor();
            initManger.stopIosMonitor();
            browMyAssertsService.browMyAsserts(num);
            initManger.stopAndroidMonitor();
            initManger.stopIosMonitor();
            initManger.changeEnv(testEnv);
            loginManager.login(testUser, testPass);
            investProductService.investProduct(num);
            return Result.ok();
        } catch (Exception e) {
            String msg = "性能测试，执行所有场景失败";
            log.error("所有场景执行失败：{}", e);
            alertManager.alert(msg, e);
        } finally {
            initManger.afterAll(FilesTypeEnum.ALL.getValue());
        }
        return Result.failure(-1, "执行失败");

    }
}
