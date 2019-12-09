package com.iqianjin.appperformance.db.model;

import com.iqianjin.appperformance.common.constant.Env;
import org.joda.time.DateTime;


public class SimpleMessage {
    /**
     * 应用id标示
     */
    private String appId;
    /**
     * 环境标示
     */
    private Env env;
    /**
     * 标题
     */
    private String title;
    /**
     * 调用类信息
     */
    private String callerClass;
    /**
     * 异常信息
     */
    private Throwable throwable;
    /**
     * 消息内容
     */
    private String content;

    /**
     * 发送时间
     */
    private DateTime sendTime;

    public SimpleMessage(Builder builder) {
        this.appId = builder.appId;
        this.env = builder.env;
        this.title = builder.title;
        this.callerClass = builder.callerClass;
        this.throwable = builder.throwable;
        this.content = builder.content;
        this.sendTime = builder.sendTime;
    }

    public String getAppId() {
        return appId;
    }

    public Env getEnv() {
        return env;
    }

    public String getTitle() {
        return title;
    }

    public String getCallerClass() {
        return callerClass;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public String getContent() {
        return content;
    }

    public DateTime getSendTime() {
        return sendTime;
    }

    //--------

    public static class Builder {
        String appId;
        Env env;
        String title;
        String callerClass;
        Throwable throwable;
        String content;
        DateTime sendTime;

        public Builder() {
            this.sendTime = DateTime.now();
        }

        public Builder appId(String appId) {
            this.appId = appId;
            return this;
        }

        public Builder env(Env env) {
            this.env = env;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder callerClass(Exception stacktrace) {
            this.callerClass = deferCallerInfo(stacktrace);
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder throwable(Throwable throwable) {
            this.throwable = throwable;
            return this;
        }

        public SimpleMessage build() {
            return new SimpleMessage(this);
        }
    }

    private static String deferCallerInfo(Exception e) {
        StackTraceElement[] elements = e.getStackTrace();
        int index =-1;
        for (int i=0; i<elements.length; i++) {
            StackTraceElement ste = elements[i];
            if (!ste.getClassName().startsWith("com.iqianjin.appperformance")) {
                index = i;
                break;
            }
        }
        StackTraceElement target = elements[index];
        StringBuilder sb = new StringBuilder(80);
        sb.append(target.getClassName()).append('.').append(target.getMethodName())
                .append('(').append(target.getLineNumber()).append(')');
        return sb.toString();
    }

}
