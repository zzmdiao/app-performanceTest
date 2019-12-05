package com.iqianjin.appperformance;

import com.iqianjin.appperformance.core.AgentCloseListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.iqianjin.appperformance")
public class AppPerformanceApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(AppPerformanceApplication.class);
        application.addListeners(new AgentCloseListener());
        application.run(args);
    }
}
