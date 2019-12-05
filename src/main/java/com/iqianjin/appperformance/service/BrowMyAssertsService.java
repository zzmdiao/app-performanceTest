package com.iqianjin.appperformance.service;

import com.iqianjin.appperformance.core.BaseAction;
import com.iqianjin.appperformance.util.TerminalUtils;
import com.iqianjin.lego.contracts.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    private BaseAction baseAction;

    public Result browMyAsserts(Integer number) {
        try {
            log.info("浏览我的资产执行开始，执行次数：{}", number);
            browseAsserts(number);
            browserRedBag(number);
            browseReward(number);
            return Result.ok();
        } catch (Exception e) {
            log.error("浏览我的资产执行出错:{}", e);
            return Result.failure(-1, "浏览我的资产执行出错");
        }

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

    public void browserRedBag(Integer number) {
        baseAction.click(tvRedBagAmount);
        TerminalUtils.sleep(2);
        if (baseAction.getPageSource().contains("知道了")) {
            baseAction.click(tv_add_redbag_guide_confirm);
        }
        for (int i = 0; i < number; i++) {
            baseAction.swipUpWaitElement(0.5, 0.1, tv_redbag_new_expired);
            baseAction.click(tv_redbag_new_expired);

            baseAction.swipeToNum(0.5, 0.1, 5);
            baseAction.goBack();
            baseAction.goBack();
            baseAction.click(tvRedBagAmount);
        }
        baseAction.goBack();
    }

    public void browseReward(Integer number) {
        baseAction.click(tvAddInterestNum);
        TerminalUtils.sleep(2);
        if (baseAction.getPageSource().contains("知道了")) {
//            baseAction.click(tv_add_interest_guide_confirm);
            baseAction.goBack();
        }
        for (int i = 0; i < number; i++) {
            baseAction.swipUpWaitElement(0.5, 0.1, tv_reward_new_expired);
            baseAction.click(tv_reward_new_expired);

            baseAction.swipeToNum(0.5, 0.1, 5);
            baseAction.goBack();
            baseAction.goBack();
            baseAction.click(tvAddInterestNum);
        }
        baseAction.goBack();
    }
}
