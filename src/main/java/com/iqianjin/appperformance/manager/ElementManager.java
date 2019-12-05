package com.iqianjin.appperformance.manager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iqianjin.appperformance.db.mapper.ElementLocationMapper;
import com.iqianjin.appperformance.db.model.Device;
import com.iqianjin.appperformance.db.model.ElementLocation;
import com.iqianjin.appperformance.db.model.Param;
import com.iqianjin.appperformance.service.DeviceService;
import com.iqianjin.appperformance.util.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;


@Component
@Slf4j
public class ElementManager {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CopyOnWriteArrayList<ElementLocation> elementLocationList = new CopyOnWriteArrayList<>();

    @Autowired
    private ElementLocationMapper elementLocationMapper;

    /**
     * 初始化
     */
    @PostConstruct
    public void init() {
        buildQuestionCache(true);
    }

    /**
     * 构建元素定位库缓存
     *
     * @param init
     */
    public void buildQuestionCache(boolean init) {
        logger.info("定位元素初始化开始, init:{}", init);

        //查询数据库中的定位元素
        List<ElementLocation> list = elementLocationMapper.selectElementList();
        logger.info("定位元素信息, 查询数据库中的列表:{}", JSON.toJSON(list));
        if (CollectionUtils.isEmpty(list)) {
            logger.warn("定位元素信息, 查询数据库中的列表为空");
            return;
        }
        elementLocationList.addAll(list);
        logger.info("初始化定位元素结束：{}", JSON.toJSON(elementLocationList));
    }

    public List<ElementLocation> getAllElementList() {
        return elementLocationList;
    }

    /**
     * 根据platform 和 name 获取value
     */
    public List<Param> getElementList(String name, Integer platform) {
        String value = elementLocationMapper.selectByNameAndPlatform(name, platform);
        return JSONObject.parseArray(value, Param.class);
    }

    /**
     * 将mapAndroid和mapIos初始化到数据库
     *
     * @param map
     */
    public void setSql(Map<String, List<String>> map, Integer platform) {
        for (String key : map.keySet()) {
            ElementLocation elementLocation = new ElementLocation();
            List<String> sb = new ArrayList<>();
            List<String> value = map.get(key);
            String[] valueArray;
            Param param = new Param();
            for (String temp : value) {
                valueArray = temp.split(":");
                param.setParamBy(valueArray[0]);
                param.setParamValue(valueArray[1]);
                sb.add(JSON.toJSONString(param));
            }
            elementLocation.setValue(sb.toString());
            elementLocation.setName(key);
            elementLocation.setPlatform(platform);
            elementLocation.setCreate_time(new Date());
            elementLocationMapper.insert(elementLocation);
        }
    }

}
