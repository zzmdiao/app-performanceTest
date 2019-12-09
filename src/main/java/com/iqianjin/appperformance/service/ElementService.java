package com.iqianjin.appperformance.service;


import com.iqianjin.appperformance.db.mapper.ElementLocationMapper;
import com.iqianjin.appperformance.db.model.ElementLocation;
import com.iqianjin.appperformance.manager.ElementManager;
import com.iqianjin.lego.contracts.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class ElementService {

    @Resource
    private ElementLocationMapper elementLocationMapper;

    @Autowired
    private ElementManager elementManager;

    public Result query() {
        List<ElementLocation> elementList = elementManager.getAllElementList();
        log.info("元素列表:{}", elementList);
        if (elementList != null) {
            return Result.ok();
        }
        return Result.failure(-1, "元素列表为空");
    }

    public Result update(ElementLocation elementLocation) {
        int i;
        try {
            i = elementLocationMapper.updateByPrimaryKey(elementLocation);
            if (i == 1) {
                elementManager.buildQuestionCache(false);
                return Result.ok();
            }
            return Result.failure(-1, "更新元素失败");
        } catch (Exception e) {
            return Result.systemError("系统异常");
        }

    }

    public Result del(Long id) {
        int i;
        i = elementLocationMapper.deleteByPrimaryKey(id);
        if (i == 1) {
            elementManager.buildQuestionCache(false);
            return Result.ok();
        }
        return Result.failure(-1, "删除元素失败");

    }

}
