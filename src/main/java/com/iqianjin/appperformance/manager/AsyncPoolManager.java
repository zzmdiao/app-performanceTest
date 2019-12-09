package com.iqianjin.appperformance.manager;

import com.iqianjin.lego.concurrent.DefaultThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

public class AsyncPoolManager {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ThreadPoolExecutor pool;
    private AsyncPoolManager() {
        this.pool = new ThreadPoolExecutor(2, 5, 2, TimeUnit.MINUTES, new LinkedBlockingQueue<>(200),
                new DefaultThreadFactory("lego-alert"), new ThreadPoolExecutor.DiscardOldestPolicy());
    }

    public void execute(Runnable task) {
        if (isPoolShutdown()) {
            return;
        }
        pool.execute(task);
    }

    /**
     * 提交任务并忽略报错
     * @param task
     */
    public void executeQuietly(Runnable task) {
        if (isPoolShutdown()) {
            return;
        }
        try {
            pool.execute(task);
        } catch (Exception e) {
            //ignore
            if (logger.isDebugEnabled()) {
                logger.debug("提交报警信息异常");
            }
        }
    }

    public <V> Future<V> submit(Callable<V> task) {
        return pool.submit(task);
    }

    /**
     * 关闭线程池
     */
    public void shutdown() {
        if (logger.isDebugEnabled()) {
            logger.debug("shutdown");
        }
        if (!pool.isShutdown()) {
            pool.shutdown();
        }
    }

    private boolean isPoolShutdown() {
        return pool.isShutdown();
    }

    public static AsyncPoolManager getMgr() {
        return AsyncPoolManagerHolder.INSTANCE;
    }

    private static class AsyncPoolManagerHolder {
        private static final AsyncPoolManager INSTANCE = new AsyncPoolManager();
    }
}
