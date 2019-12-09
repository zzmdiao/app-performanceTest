package com.iqianjin.appperformance.service;

import com.iqianjin.appperformance.core.BaseAction;
import com.iqianjin.appperformance.manager.AlertManager;
import com.iqianjin.appperformance.util.TerminalUtils;
import com.iqianjin.lego.contracts.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class BrowMyAssertsService {

    private String myTab = "我的tab";
    private String tvYesterdayEarnings = "昨日出借回报";
    private String tvTotalProfit = "累计出借回报";
    private String tvTotalAssets = "我的资产";
    private String tvRedBagAmount = "优惠劵";
    private String tvAddInterestNum = "奖励劵";
    private String tv_redbag_new_expired = "查看过期优惠劵";
    private String tv_reward_new_expired = "查看过期奖励劵";
    private String tv_add_redbag_guide_confirm = "优惠劵弹窗-知道了按钮";
    private String tv_add_interest_guide_confirm = "奖励劵弹窗-知道了按钮";
    private String addInterestInvalidExpired = "奖励劵已过期tab";
    private String couponInvalidExpired = "优惠劵已过期tab";

    @Autowired
    private BaseAction baseAction;
    @Resource
    private AlertManager alertManager;

    public Result browMyAsserts(Integer number) {
        try {
            log.info("浏览我的资产执行开始，执行次数：{}", number);
            browseAsserts(number);
            browserRedBagReward(number, tvRedBagAmount, tv_add_redbag_guide_confirm, tv_redbag_new_expired, couponInvalidExpired);
            browserRedBagReward(number, tvAddInterestNum, tv_add_interest_guide_confirm, tv_reward_new_expired, addInterestInvalidExpired);
            baseAction.goBack();
            return Result.ok();
        } catch (Exception e) {
            log.error("浏览我的资产执行出错:{}", e);
            String msg = "性能测试，浏览我的资产执行出错";
            alertManager.alert(msg, e);
        }
        return Result.failure(-1, "浏览我的资产执行出错");
    }


    public void browseAsserts(Integer number) {
        baseAction.click(myTab);
        for (int i = 0; i < number; i++) {
            baseAction.click(tvYesterdayEarnings);
            baseAction.swipeToNum(0.5, 0.1, 2);
            baseAction.goBack();
            baseAction.click(tvTotalProfit);
            baseAction.swipeToNum(0.5, 0.1, 2);
            baseAction.goBack();
            baseAction.click(tvTotalAssets);
            baseAction.swipeToNum(0.8, 0.3, 1);
            baseAction.goBack();

        }
    }

    public void browserRedBagReward(Integer number, String red1, String red2, String red3, String red4) {
        baseAction.click(red1);
        TerminalUtils.sleep(2);
        if (baseAction.getPageSource().contains("知道了")) {
            baseAction.click(red2);
        }
        for (int i = 0; i < number; i++) {
            baseAction.swipUpWaitElement(0.5, 0.1, red3);
            baseAction.click(red3);
            baseAction.swipeToNum(0.5, 0.1, 5);
            baseAction.click(red4);
            baseAction.swipeToNum(0.5, 0.1, 5);
            baseAction.goBack();
            baseAction.goBack();
            baseAction.click(red1);
        }
        baseAction.goBack();
    }

}
