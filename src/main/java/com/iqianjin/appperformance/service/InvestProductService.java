package com.iqianjin.appperformance.service;


import com.iqianjin.appperformance.core.BaseAction;
import com.iqianjin.appperformance.core.MobileDevice;
import com.iqianjin.appperformance.util.TerminalUtils;
import com.iqianjin.lego.contracts.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class InvestProductService {

    @Autowired
    private BaseAction baseAction;
    private static final String AMOUNT = "1000";
    private String productTab = "产品tab";
    private String threeInvestButton = "三月期立即投资按钮";
    private String join_product_edit_text = "金额输入框";
    private String join_product_confirm = "购买页立即投资按钮";
    private String confirmBuySubmit = "弹框确认投资按钮";
    private String buySuccessCompplete = "购买成功页完成按钮";
    private String ayb_Title = "爱盈宝";
    private String yjb_Title = "月进宝";
    private String yjb_invest_button = "月进宝立即投资";
    private String yjb_product_amount = "月进宝金额输入框";
    private String yjb_product_confirm = "月进宝立即投资按钮";


    public Result investProduct(Integer num) {
        try {
            log.info("购买爱盈宝：{}次", num);
            buyAYB(num, AMOUNT);
            log.info("购买月进宝：{}次", num);
            buyYJB(num, AMOUNT);
            return Result.ok();
        } catch (Exception e) {
            log.info("执行购买产品出错：{}", e);
            return Result.failure(-1, "执行购买产品出错");
        }

    }

    public void buyAYB(Integer num, String amount) {
        baseAction.click(productTab);
        // 切换环境后无法点击  StaticText is not visible on the screen and thus is not interactable
        TerminalUtils.sleep(3);
        for (int i = 0; i < num; i++) {
            baseAction.click(ayb_Title);
            baseAction.click(threeInvestButton);
            baseAction.sendKeys(join_product_edit_text, amount);
            baseAction.click(join_product_confirm);
            baseAction.click(confirmBuySubmit);
            if (baseAction.isWebview()) {
                if (MobileDevice.ANDROID == baseAction.getPlatform().get()) {
                    baseAction.goBack();
                } else {
                    baseAction.clickWebview();
                }
            }
            baseAction.click(buySuccessCompplete);
            baseAction.click(productTab);
        }
    }

    public void buyYJB(Integer num, String amount) {

        baseAction.click(productTab);
        baseAction.swipUpWaitElement(0.5, 0.1, yjb_Title);
        for (int i = 0; i < num; i++) {
            baseAction.click(yjb_Title);
            baseAction.click(yjb_invest_button);
            baseAction.sendKeys(yjb_product_amount, amount);
            baseAction.click(yjb_product_confirm);
            baseAction.click(confirmBuySubmit);
            baseAction.click(buySuccessCompplete);
            baseAction.click(productTab);
        }
    }
}
