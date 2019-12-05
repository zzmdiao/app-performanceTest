package com.iqianjin.appperformance.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import com.alibaba.fastjson.JSONObject;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
public class AlertUtils {
    public static void alertQYWX(String name) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://qywx.okd.iqianjin.test/api/v1/sendText";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        InetAddress addr=null;
        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        // 添加邮箱
        String body22 = "{'emailList':['zhangzhiming@iqianjin.com'], 'content':'from:"+addr+name+"，性能测试已完成，请查看结果！'}";
        JSONObject jo = JSONObject.parseObject(body22);
        HttpEntity<JSONObject> request = new HttpEntity<>(jo, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        log.info("企业微信接口返回结果:{}", response);
    }

    public static void main(String[] args) {
        alertQYWX("android");
    }
}
