package com.iqianjin.appperformance.service;

import com.iqianjin.appperformance.core.BaseAction;
import com.iqianjin.appperformance.core.MobileDevice;
import com.iqianjin.appperformance.util.TerminalUtils;
import com.iqianjin.lego.contracts.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BrowFundFlowService {

    @Autowired
    private BaseAction baseAction;

    private String myTab = "我的tab";
    private String fundFlowTab = "资金流水";
    private String recharge = "资金流水-充值";
    private String cash = "资金流水-提现";
    private String lend = "资金流水-出借";
    private String recover = "资金流水-回收";
    private String asignment = "资金流水-转让债权";
    private String frozen = "资金流水-冻结";

    public Result browFundFlow(Integer num) {

        try {
            log.info("浏览资金流水执行开始，执行次数：{}", num);
            fundTab(num);
            return Result.ok();
        } catch (Exception e) {
            log.error("浏览资金流水执行出错:{}", e);
            return Result.failure(-1, "浏览资金流水执行出错");
        }

    }

    public void fundTab(Integer num) {
        baseAction.click(myTab);
        TerminalUtils.sleep(1);
        if (baseAction.getPageSource().contains("立即续期")) {
            baseAction.swipeUpOrDown(0.5, 0.1);
        }
        for (int i = 0; i < num; i++) {
            baseAction.click(fundFlowTab);
            swipFund(recharge);
            swipFund(cash);
            swipFund(lend);
            swipFund(recover);
            if (MobileDevice.ANDROID == baseAction.getPlatform().get()) {
                swipFund(asignment);
                swipFund(frozen);
            }
            baseAction.goBack();
        }
    }

    public void swipFund(String str) {
        baseAction.swipeToNum(0.5, 0.1, 5);
        baseAction.click(str);
    }
}
