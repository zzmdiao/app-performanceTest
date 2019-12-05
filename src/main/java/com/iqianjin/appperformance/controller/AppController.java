package com.iqianjin.appperformance.controller;

import com.iqianjin.appperformance.common.constant.FilesTypeEnum;
import com.iqianjin.appperformance.manager.InitManger;
import com.iqianjin.appperformance.service.*;
import com.iqianjin.lego.contracts.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app")
public class AppController {

    @Autowired
    private SwtichTabService swtichTabService;
    @Autowired
    private BrowMyAssertsService browMyAssertsService;
    @Autowired
    private BrowFundFlowService browFundFlowService;
    @Autowired
    private BrowLendRecordService browLendRecordService;
    @Autowired
    private InvestProductService investProductService;
    @Autowired
    private AllCaseService allCaseService;
    @Autowired
    private InitManger initManger;


    @GetMapping("/{deviceId}/changeTab")
    public Result changeTab(@RequestParam("num") Integer num, @PathVariable String deviceId, @RequestParam("userName")  String userName,
                            @RequestParam("passWord") String passWord, @RequestParam("env") String env) {
        Result result;
        try {
            initManger.init(deviceId, env, userName, passWord);
            result = swtichTabService.swtichTab(num);
            initManger.stopAndroidMonitor();
            initManger.stopIosMonitor();
        } finally {
            initManger.afterAll(FilesTypeEnum.SWITCH_TAB.getValue());
        }
        return result;
    }

    @GetMapping("/{deviceId}/browAsserts")
    public Result browAsserts(@RequestParam("num") Integer num, @PathVariable String deviceId,@RequestParam("userName")  String userName,
                              @RequestParam("passWord") String passWord, @RequestParam("env") String env){
        Result result;
        try {
            initManger.init(deviceId, env, userName, passWord);
            result = browMyAssertsService.browMyAsserts(num);
            initManger.stopAndroidMonitor();
            initManger.stopIosMonitor();
        } finally {
            initManger.afterAll( FilesTypeEnum.BROW_MY_ASSERTS.getValue());
        }
        return result;
    }

    @GetMapping("/{deviceId}/browFundFlow")
    public Result browFundFlow(@RequestParam("num") Integer num, @PathVariable String deviceId,@RequestParam("userName")  String userName,
                               @RequestParam("passWord") String passWord, @RequestParam("env") String env) {
        Result result;
        try {
            initManger.init(deviceId, env, userName, passWord);
            result = browFundFlowService.browFundFlow(num);
            initManger.stopAndroidMonitor();
            initManger.stopIosMonitor();
        } finally {
            initManger.afterAll( FilesTypeEnum.BROW_FUNDFLOW.getValue());
        }
        return result;
    }

    @GetMapping("/{deviceId}/browLendRecord")
    public Result browLendRecord(@RequestParam("num") Integer num, @PathVariable String deviceId,@RequestParam("userName")  String userName,
                                 @RequestParam("passWord") String passWord, @RequestParam("env") String env) {
        Result result;
        try {
            initManger.init(deviceId, env, userName, passWord);
            result = browLendRecordService.browLendRecord(num);
            initManger.stopAndroidMonitor();
            initManger.stopIosMonitor();
        } finally {
            initManger.afterAll(FilesTypeEnum.BROW_LENDRECORD.getValue());
        }
        return result;
    }

    @GetMapping("/{deviceId}/investProduct")
    public Result buyAYB(@RequestParam("num") Integer num, @PathVariable String deviceId,@RequestParam("userName")  String userName,
                         @RequestParam("passWord") String passWord, @RequestParam("env") String env){
        Result result;
        try {
            initManger.init(deviceId, env, userName, passWord);
            result = investProductService.investProduct(num);
            initManger.stopAndroidMonitor();
            initManger.stopIosMonitor();
        } finally {
            initManger.afterAll( FilesTypeEnum.INVEST_PRODUCT.getValue());
        }
        return result;
    }

    @GetMapping("/{deviceId}/allCase")
    public Result allCase(@RequestParam("num") Integer num, @PathVariable String deviceId,@RequestParam("proUser") String proUser,
                          @RequestParam("proPass") String proPass, @RequestParam("proEnv") String proEnv,
                          @RequestParam("testUser") String testUser, @RequestParam("testPass") String testPass,
                          @RequestParam("testEnv") String testEnv) {

        Result result = allCaseService.allCase(num, deviceId, proUser, proPass, proEnv, testUser, testPass, testEnv);
        return result;
    }

}
