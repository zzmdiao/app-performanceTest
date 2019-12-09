package com.iqianjin.appperformance.service;

import com.iqianjin.appperformance.core.BaseAction;
import com.iqianjin.appperformance.manager.AlertManager;
import com.iqianjin.lego.contracts.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class BrowLendRecordService {

    private String myTab = "我的tab";
    private String lendRecord = "出借记录";
    private String aybRecord = "出借记录-爱盈宝";
    private String zcbRecord = "出借记录-整存宝+";
    private String sanbiaoRecord = "出借记录-散标";
    private String inRecord = "出借记录-转让中";
    private String offecord = "出借记录-已转让";
    private String overRecord = "出借记录-已结束";
    private String noMore = "没有更多";
    private String yjbRecord = "出借记录-月进宝";

    @Autowired
    private BaseAction baseAction;
    @Resource
    private AlertManager alertManager;

    public Result browLendRecord(Integer num) {
        try {
            log.info("浏览出借记录开始执行，执行次数：{}", num);
            isRenewed();
            aybProductRecord(num);
            zcbProductRecord(num);
            yjbProductRecord(num);
            sanbiaoRecord(num);
            baseAction.goBack();
            return Result.ok();
        } catch (Exception e) {
            log.error("浏览出借记录执行出错:{}", e);
            String msg = "性能测试，浏览出借记录执行出错";
            alertManager.alert(msg, e);
        }
        return Result.failure(-1, "浏览资金流水执行出错");
    }


    public void isRenewed() {
        baseAction.click(myTab);
        for (int i = 0; i < 2; i++) {
            log.info("当前页面是否包含立即续期");
            if (baseAction.getPageSource().contains("立即续期")) {
                baseAction.swipeUpOrDown(0.5, 0.1);
            }
        }
        baseAction.click(lendRecord);
    }

    /**
     * num 循环次数
     *
     * @param num
     */
    public void aybProductRecord(Integer num) {

        productRecrod(aybRecord, num);
    }

    public void zcbProductRecord(Integer num) {
        productRecrod(zcbRecord, num);
    }

    public void yjbProductRecord(Integer num) {
        productRecrod(yjbRecord, num);
    }

    public void productRecrod(String by, Integer num) {
        for (int i = 0; i < num; i++) {
            baseAction.click(by);
            log.info("开始滑动");
            baseAction.swipeToNum(0.5, 0.1, 5);
            baseAction.swipeLeftOrRight(0.8, 0.1);
            baseAction.swipeToNum(0.5, 0.1, 5);
            baseAction.goBack();
        }
    }

    public void sanbiaoRecord(Integer num) {
        baseAction.swipUpWaitElement(0.5, 0.1, sanbiaoRecord);
        for (int i = 0; i < num; i++) {
            baseAction.click(sanbiaoRecord);
            baseAction.swipeToNum(0.5, 0.1, 5);
            baseAction.click(inRecord);
            baseAction.swipeToNum(0.5, 0.1, 5);
            baseAction.click(offecord);
            baseAction.swipeToNum(0.5, 0.1, 5);
            baseAction.click(overRecord);
            baseAction.swipeToNum(0.5, 0.1, 5);
            baseAction.goBack();
        }
    }
}
