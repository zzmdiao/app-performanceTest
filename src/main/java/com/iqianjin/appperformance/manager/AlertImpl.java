package com.iqianjin.appperformance.manager;

import com.iqianjin.appperformance.common.constant.Env;
import com.iqianjin.appperformance.db.model.RequestDataVO;
import com.iqianjin.lego.core.util.JsonUtils;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class AlertImpl {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    private Env env;
    private long timeoutInSeconds;
    private String serverUrl;

    private final OkHttpClient client;

    public AlertImpl(Env env, long timeoutInSeconds) {
        this.env = env;
        this.timeoutInSeconds = timeoutInSeconds;

        //优先读取启动参数 -Dlego.alert.server=xxx
        String preferUrl = System.getProperty("lego.alert.server");
        logger.info("初始化参数 env:{}, preferUrl:{}", env, preferUrl);

        if (StringUtils.isEmpty(preferUrl)) {
            preferUrl = env.getServiceUrl();
        }
        this.serverUrl = preferUrl;
        this.client = new OkHttpClient.Builder()
                .connectTimeout(this.timeoutInSeconds, TimeUnit.SECONDS)
                .readTimeout(this.timeoutInSeconds, TimeUnit.SECONDS)
                .writeTimeout(this.timeoutInSeconds, TimeUnit.SECONDS)
                .build();

        logger.info("初始化参数 env:{}, serverUrl:{}, timeoutInSeconds:{}", env, serverUrl, timeoutInSeconds);
    }

    public void send(RequestDataVO req) {
        try {
            String response = post(serverUrl, JsonUtils.toJson(req));
            logger.info("请求企业微信接口:{}, env:{}, 响应的结果:{}", serverUrl, env, response);
        } catch (IOException e) {
            logger.error(String.format("请求企业微信接口异常, env:%s, serverUrl:%s", env, serverUrl), e);
        }
    }

    String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}
