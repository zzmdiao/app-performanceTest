package com.iqianjin.appperformance.config;

import com.google.common.base.Splitter;
import com.iqianjin.appperformance.common.constant.Env;
import com.iqianjin.appperformance.common.constant.ReceiverType;
import com.iqianjin.appperformance.manager.AlertManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AlertManagerConfigure {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${lego.alert.appId}")
    private String appId;

    @Value("${lego.alert.env}")
    private String env;

    @Value("${lego.alert.receivers}")
    private String receivers;

    @Bean(destroyMethod = "destroy")
    public AlertManager alertManager() {
        logger.info("AlertManager init env:{}, receivers:{}", env, receivers);
        List<String> receiverList = Splitter.on(",").trimResults().splitToList(receivers);
        AlertManager alertManager = new AlertManager.Builder()
                .appId(appId)
                .env(Env.of(env))
                .receiverType(ReceiverType.MAIL)
                .receiverList(receiverList)
                .build();
        return alertManager;
    }

}
