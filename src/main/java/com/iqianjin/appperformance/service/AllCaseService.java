package com.iqianjin.appperformance.service;

import com.iqianjin.appperformance.common.constant.FilesTypeEnum;
import com.iqianjin.appperformance.core.MobileDevice;
import com.iqianjin.appperformance.core.MobileDeviceHolder;
import com.iqianjin.appperformance.manager.InitManger;
import com.iqianjin.appperformance.manager.LoginManager;
import com.iqianjin.lego.contracts.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    private MobileDevice mobileDevice;

    public Result allCase(Integer num, String deviceId, String proUser, String proPass, String proEnv
            , String testUser, String testPass, String testEnv) {
        mobileDevice = MobileDeviceHolder.get(deviceId);
        try {
            initManger.init(deviceId, proEnv, proUser, proPass);
            swtichTabService.swtichTab(num);
            browLendRecordService.browLendRecord(num);
            browFundFlowService.browFundFlow(num);
            browMyAssertsService.browMyAsserts(num);
            initManger.changeEnv(testEnv);
            loginManager.login(testUser, testPass);
            investProductService.investProduct(num);
            initManger.stopIosMonitor();
            initManger.stopAndroidMonitor();
            return Result.ok();
        } catch (Exception e){
            log.error("执行失败：{}",e);
           return Result.failure(-1,"执行失败");
        } finally {
            initManger.afterAll(FilesTypeEnum.ALL.getValue());
        }

    }
}
