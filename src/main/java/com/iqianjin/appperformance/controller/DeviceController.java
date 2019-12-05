package com.iqianjin.appperformance.controller;

import com.iqianjin.appperformance.db.model.Device;
import com.iqianjin.appperformance.service.DeviceService;
import com.iqianjin.lego.contracts.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/device")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    /**
     * 保存Device信息
     * @param device
     * @return
     */
    @PostMapping("/save")
    public Result save(@RequestBody @Valid Device device) {
        return deviceService.save(device);
    }


}
