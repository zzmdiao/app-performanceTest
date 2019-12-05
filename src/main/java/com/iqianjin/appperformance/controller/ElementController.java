package com.iqianjin.appperformance.controller;

import com.iqianjin.appperformance.db.model.ElementLocation;
import com.iqianjin.appperformance.service.ElementService;
import com.iqianjin.lego.contracts.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app")
public class ElementController {

    @Autowired
    private ElementService elementService;

    @GetMapping("/element/list")
    public Result queryElement(){
        return  elementService.query();
    }

    @GetMapping("/element/update")
    public Result updateElement(@RequestParam(value = "elementLocation") ElementLocation elementLocation){
        return  elementService.update(elementLocation);
    }

    @GetMapping("/element/del")
    public Result delElement(@RequestParam(value = "id") Long id){
        return  elementService.del(id);
    }

}
