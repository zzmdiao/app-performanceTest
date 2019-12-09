package com.iqianjin.appperformance.util;

import com.iqianjin.appperformance.db.model.SimpleMessage;
import com.iqianjin.lego.core.util.DateUtils;

public abstract class MessageUtils {
    private static final int INITIAL_CAP = 512;
    private static final int DEFAULT_STACK_TRACE_DEPTH = 6;

    /**
     * 构造企业微信消息内容
     * @param message
     * @return
     */
    public static String buildWechatBody(SimpleMessage message) {
        boolean hasError = message.getThrowable()!=null;
        int cap = INITIAL_CAP;
        if (hasError) {
            cap = INITIAL_CAP << 1;
        }
        StringBuilder sb = new StringBuilder(cap);
        sb.append("应用: ").append(message.getAppId()).append("\n")
                .append("环境: ").append(message.getEnv()).append("\n")
                .append("标题: ").append(message.getTitle()).append("\n")
                .append("类: ").append(message.getCallerClass()).append("\n");
        if (hasError) {
            sb.append("异常栈信息: ").append(getStackTrace(message.getThrowable())).append("\n");
        } else {
            sb.append("详细信息: ").append(message.getContent()).append("\n");
        }

        //
        sb.append("发送时间: ").append(DateUtils.format(message.getSendTime()));
        return sb.toString();
    }

    private static String getStackTrace(final Throwable throwable) {
        //参考：e.printStackTrace();
        final StringBuilder sb = new StringBuilder(512);

        // Print our stack trace
        sb.append(throwable);

        int cnt = 0;
        StackTraceElement[] trace = throwable.getStackTrace();
        for (StackTraceElement traceElement : trace) {
            sb.append("\tat ").append(traceElement);
            cnt++;
            if(cnt >= DEFAULT_STACK_TRACE_DEPTH) {
                break;
            }
        }
        return sb.toString();
    }
}
