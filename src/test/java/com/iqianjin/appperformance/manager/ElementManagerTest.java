package com.iqianjin.appperformance.manager;

import com.iqianjin.appperformance.db.model.Param;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.iqianjin.appperformance.config.ElementTypeEnum.mapAndroid;
import static com.iqianjin.appperformance.config.ElementTypeEnum.mapIos;


@RunWith(SpringRunner.class)
@SpringBootTest
class ElementManagerTest {

    @Autowired
    ElementManager elementManager;
    @Test
    @Ignore
    void getElementList() {
        List<Param> list = elementManager.getElementList("首页tab",1);
        for (Param param:list){
            System.out.println("param的定位方式是："+param.getParamBy());
            System.out.println("param的定位值是："+param.getParamValue());
        }
    }
    @Test
    @Ignore
    void pushMysql(){
        elementManager.setSql(mapAndroid,1);
        elementManager.setSql(mapIos,2);

    }
}
