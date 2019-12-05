package com.iqianjin.appperformance.controller;

import com.iqianjin.appperformance.manager.ElementManager;
import com.iqianjin.lego.contracts.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/app")
public class DoorController {

    @Resource
    private ElementManager elementManager;
    /**
     * 重新构建元素定位库缓存
     * @return
     */
    @GetMapping("/initElement")
    public Result change(){
        elementManager.buildQuestionCache(false);
        return Result.ok();
    }
}
