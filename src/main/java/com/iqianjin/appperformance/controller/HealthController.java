package com.iqianjin.appperformance.controller;

import com.iqianjin.lego.contracts.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 健康检查接口(不能修改)
 */
@RestController
public class HealthController {

    @RequestMapping(path = {"/healthCheck", "/health"})
    public Result healthCheck() {
        return Result.ok();
    }

    @GetMapping("/")
    public Result index() {
        return Result.ok();
    }
}
