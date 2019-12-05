package com.iqianjin.appperformance.core.appium;

import com.iqianjin.appperformance.core.MobileDevice;
import io.appium.java_client.AppiumDriver;
import org.dom4j.Attribute;
import org.dom4j.Element;
import org.springframework.util.StringUtils;

import java.util.List;


public class IosPageSourceHandler extends AppiumPageSourceHandler {

    public IosPageSourceHandler(AppiumDriver appiumDriver) {
        super(appiumDriver);
    }

    /**
     * android / ios 复用一套前端inspector组件，将ios的布局转成android格式
     *
     * @param element
     */
    @Override
    public void handleElement(Element element) {
        if (element == null) {
            return;
        }

        String elementName = element.getName();
        if (StringUtils.isEmpty(elementName)) {
            return;
        }

        if ("AppiumAUT".equals(elementName)) {
            element.setName("hierarchy");
            element.addAttribute("platform", MobileDevice.IOS + "");
        } else {
            element.setName("node");

            Attribute xAttr = element.attribute("x");
            String startX = xAttr.getValue();
            element.remove(xAttr);

            Attribute yAttr = element.attribute("y");
            String startY = yAttr.getValue();
            element.remove(yAttr);

            Attribute widthAttr = element.attribute("width");
            String width = widthAttr.getValue();
            element.remove(widthAttr);

            Attribute heightAttr = element.attribute("height");
            String height = heightAttr.getValue();
            element.remove(heightAttr);

            String endX = String.valueOf(Integer.parseInt(startX) + Integer.parseInt(width));
            String endY = String.valueOf(Integer.parseInt(startY) + Integer.parseInt(height));

            String bounds = String.format("[%s,%s][%s,%s]", startX, startY, endX, endY);
            element.addAttribute("bounds", bounds);

            // 前端el-tree
            // defaultProps: {
            //   children: 'nodes',
            //   label: 'class'
            // }
            element.addAttribute("class", elementName);
        }

        List<Element> elements = element.elements();
        elements.forEach(e -> handleElement(e));
    }
}
