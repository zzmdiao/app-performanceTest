package com.iqianjin.appperformance.service;

import com.iqianjin.appperformance.core.BaseAction;
import com.iqianjin.appperformance.manager.AlertManager;
import com.iqianjin.lego.contracts.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Slf4j
@Service
public class SwtichTabService {

    private String homeTab = "首页tab";
    private String productTab = "产品tab";
    private String findTab = "发现tab";
    private String myTab = "我的tab";

    @Autowired
    private BaseAction baseAction;
    @Resource
    private AlertManager alertManager;

    public Result swtichTab(Integer num) {
        try {
            //切换tab
            log.info("切换tab执行开始，执行次数：{}", num);

            for (int i = 0; i < num; i++) {
                baseAction.click(homeTab);
                baseAction.click(productTab);
                baseAction.click(findTab);
                baseAction.click(myTab);
            }
            return Result.ok();
        } catch (Exception e) {
            log.error("切换tab执行失败:{}", e);
            String msg = "性能测试，切换tab执行失败";
            alertManager.alert(msg, e);

        }
        return Result.failure(-1, "切换tab执行失败");
    }
}
