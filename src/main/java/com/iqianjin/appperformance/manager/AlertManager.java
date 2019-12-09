package com.iqianjin.appperformance.manager;

import com.iqianjin.appperformance.common.ArrayJoiner;
import com.iqianjin.appperformance.common.constant.Env;
import com.iqianjin.appperformance.common.constant.ReceiverType;
import com.iqianjin.appperformance.db.model.RequestDataVO;
import com.iqianjin.appperformance.db.model.SimpleMessage;
import com.iqianjin.appperformance.util.MessageUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AlertManager {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String appId;
    private final Env env;
    private final ReceiverType receiverType;
    private final List<String> receiverList;

    private final AlertImpl alertImpl;

    private AlertManager(Builder builder) {
        this.appId = builder.appId;
        this.env = builder.env;
        this.receiverType = builder.receiverType;
        this.receiverList = builder.receiverList;

        long timeoutInSeconds = builder.timeoutInSeconds;
        //
        this.alertImpl = new AlertImpl(env, timeoutInSeconds);

        logger.info("初始化完成, env:{}, receiverType:{}, receiverList:{}, timeoutInSeconds:{}",
                env, receiverType, ArrayJoiner.join(receiverList), timeoutInSeconds);
    }

    public void alert(String title) {
        try {
            SimpleMessage message = new SimpleMessage.Builder()
                    .appId(appId)
                    .env(env)
                    .title(title)
                    .callerClass(new RuntimeException())
                    .build();
            alertInner(message);
        } catch (Exception e) {
            logger.error("同步发送报警信息异常", e);
        }
    }

    /**
     * 同步调用
     *
     * @param title   标题
     * @param content 详细内容
     */
    public void alert(String title, String content) {
        try {
            SimpleMessage message = new SimpleMessage.Builder()
                    .appId(appId)
                    .env(env)
                    .title(title)
                    .content(content)
                    .callerClass(new RuntimeException())
                    .build();
            alertInner(message);
        } catch (Exception e) {
            logger.error("同步发送报警信息异常", e);
        }
    }

    /**
     * @param title     标题
     * @param throwable 异常
     */
    public void alert(String title, Throwable throwable) {
        try {
            SimpleMessage message = new SimpleMessage.Builder()
                    .appId(appId)
                    .env(env)
                    .title(title)
                    .throwable(throwable)
                    .callerClass(new RuntimeException())
                    .build();
            alertInner(message);
        } catch (Exception e) {
            logger.error("同步发送报警信息异常", e);
        }
    }

    //------------异步调用

    public void alertAsync(String title) {
        try {
            SimpleMessage message = new SimpleMessage.Builder()
                    .appId(appId)
                    .env(env)
                    .title(title)
                    .callerClass(new RuntimeException())
                    .build();
            //异步执行
            AsyncPoolManager.getMgr().executeQuietly(() -> alertInner(message));
        } catch (Exception e) {
            logger.error("异步发送报警信息异常", e);
        }
    }

    /**
     * 异步调用
     *
     * @param title   标题
     * @param content 详细内容
     */
    public void alertAsync(String title, String content) {
        try {
            SimpleMessage message = new SimpleMessage.Builder()
                    .appId(appId)
                    .env(env)
                    .title(title)
                    .content(content)
                    .callerClass(new RuntimeException())
                    .build();
            //异步执行
            AsyncPoolManager.getMgr().executeQuietly(() -> alertInner(message));
        } catch (Exception e) {
            logger.error("异步发送报警信息异常", e);
        }
    }

    /**
     * 标题异常
     *
     * @param title
     * @param throwable
     */
    public void alertAsync(String title, Throwable throwable) {
        try {
            SimpleMessage message = new SimpleMessage.Builder()
                    .appId(appId)
                    .env(env)
                    .title(title)
                    .throwable(throwable)
                    .callerClass(new RuntimeException())
                    .build();
            //异步执行
            AsyncPoolManager.getMgr().executeQuietly(() -> alertInner(message));
        } catch (Exception e) {
            logger.error("异步发送报警信息异常", e);
        }
    }

    /**
     * 关闭线程池
     */
    public void destroy() {
        AsyncPoolManager.getMgr().shutdown();
    }

    //--------
    private void alertInner(SimpleMessage message) {
        RequestDataVO req = new RequestDataVO();
        req.setContent(MessageUtils.buildWechatBody(message));
        if (receiverType == ReceiverType.PHONE) {
            req.setPhoneNumberList(receiverList);
        } else {
            req.setEmailList(receiverList);
        }
        alertImpl.send(req);
    }

    //--------

    public static class Builder {
        /**
         * 应用id
         */
        String appId;
        Env env;
        ReceiverType receiverType;
        List<String> receiverList;
        long timeoutInSeconds;

        public Builder() {
            this.timeoutInSeconds = 2;
            this.receiverType = ReceiverType.MAIL;
        }

        public Builder receiverList(List<String> receiverList) {
            this.receiverList = receiverList;
            return this;
        }

        public Builder receiverType(ReceiverType receiverType) {
            this.receiverType = receiverType;
            return this;
        }

        public Builder appId(String appId) {
            this.appId = appId;
            return this;
        }

        public Builder env(Env env) {
            this.env = env;
            return this;
        }

        public Builder timeoutInSeconds(long timeoutInSeconds) {
            this.timeoutInSeconds = timeoutInSeconds;
            return this;
        }

        public AlertManager build() {
            if (StringUtils.isEmpty(appId)) {
                throw new IllegalArgumentException("appId为空");
            }
            if (env == null) {
                throw new IllegalArgumentException("env为空");
            }
            if (receiverList == null || receiverList.isEmpty()) {
                throw new IllegalArgumentException("receiverList为空");
            }
            return new AlertManager(this);
        }
    }

}
